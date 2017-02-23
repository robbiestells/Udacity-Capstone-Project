package com.prime.perspective.commentist.Utilities;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.prime.perspective.commentist.R;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.prime.perspective.commentist.Objects.FeedItem;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static com.prime.perspective.commentist.R.id.seekBar;

/**
 * Created by rsteller on 1/20/2017.
 */

public class PlayerService extends Service {

    private static PlayerService sInstance;

    public static PlayerService get() {
        return sInstance;
    }

    MediaPlayer mediaPlayer = null;
    SeekBar mSeekBar;
    Handler mHandler = new Handler();
    TextView currentTimeText;
    TextView totalTimeTV;
    TextView playerEpisodeDesc;
    FloatingActionButton playpauseButton;
    ImageButton forwardButton;
    ImageButton backButton;

    public static final String ACTION_DATA_UPDATED = "com.prime.perspective.commentist.ACTION_DATA_UPDATED";
    public static final String ACTION_PAUSE = "com.prime.perspective.commentist.ACTION_PAUSE";
    Activity mainActivity;

    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            episodeEnding();
        }
    };

    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (startId != 1) {
//    if (intent.getAction().equalsIgnoreCase(ACTION_PAUSE)) {
            Pause();
            // }
        } else {
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //public void LoadUrl(String url, final SeekBar seekBar, Activity activity) {
    public void LoadUrl(FeedItem feedItem, Activity activity) {
        AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // Permanent loss of audio focus
                    // Pause playback immediately
                    //  Pause();
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                    // Pause playback
                    if (mediaPlayer.isPlaying()) {
                        Pause();
                    }
                } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // Lower the volume, keep playing
                    if (mediaPlayer.isPlaying()) {
                        Pause();
                    }
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // Your app has been granted audio focus again
                    // Raise volume to normal, restart playback if necessary
                    if (!mediaPlayer.isPlaying()) {
                        Pause();
                    }
                }
            }
        };

        int result = am.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();

            }
            mediaPlayer = new MediaPlayer();


            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(feedItem.getAudioUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mCompletionListener);

            mainActivity = activity;

            mSeekBar = (SeekBar) activity.findViewById(seekBar);
            mSeekBar.setMax(100);

            totalTimeTV = (TextView) activity.findViewById(R.id.totalTime);
            totalTimeTV.setText(feedItem.getLength());

            playerEpisodeDesc = (TextView) activity.findViewById(R.id.playerEpisodeDesc);
            playerEpisodeDesc.setText(feedItem.getDescription());

            currentTimeText = (TextView) activity.findViewById(R.id.currentTime);
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        long mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        long maxPosition = mediaPlayer.getDuration() / 1000;

                        BigDecimal max = new BigDecimal(maxPosition);
                        BigDecimal mult = new BigDecimal(100);
                        BigDecimal current = new BigDecimal(mCurrentPosition);
                        BigDecimal progress = current.divide(max, 3, RoundingMode.CEILING);
                        BigDecimal percent = progress.multiply(mult);

                        mSeekBar.setProgress(percent.intValue());

                        currentTimeText.setText(getTimeString(mCurrentPosition * 1000));

                    }
                    mHandler.postDelayed(this, 1000);
                }
            });

            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer != null) {
                        //get percentage seeked
                        int i = seekBar.getProgress();
                        double maxPosition = mediaPlayer.getDuration() * .01;
                        //figure out new percentage and progress
                        double newPercentage = maxPosition * i;
                        int newPosition = (int) newPercentage;
                        //seek to new progress
                        mediaPlayer.seekTo(newPosition);
                    }
                }
            });

            playpauseButton = (FloatingActionButton) activity.findViewById(R.id.playpause);
            playpauseButton.setImageResource(R.drawable.pause);
            playpauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pause();
                }
            });

            forwardButton = (ImageButton) activity.findViewById(R.id.seekForwardButton);
            forwardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SkipForward();
                }
            });

            backButton = (ImageButton) activity.findViewById(R.id.seekBackButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SkipBack();
                }
            });

            //update widget
            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
            dataUpdatedIntent.putExtra("showTitle", feedItem.getTitle());
            dataUpdatedIntent.putExtra("show", feedItem.getShow());
            activity.sendBroadcast(dataUpdatedIntent);

        }
    }

    public void Pause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playpauseButton.setImageResource(R.drawable.play);
            } else {
                mediaPlayer.start();
                playpauseButton.setImageResource(R.drawable.pause);
            }
        }
    }

    public void SkipForward() {
        if (mediaPlayer != null) {
            //find current position
            int currentPosition = mediaPlayer.getCurrentPosition();

            //add 30 seconds
            int newPosition = currentPosition + 30000;

            //seek to new progress
            mediaPlayer.seekTo(newPosition);
        }
    }

    public void SkipBack() {
        if (mediaPlayer != null) {
            //find current position
            int currentPosition = mediaPlayer.getCurrentPosition();

            //go back 10 seconds
            int newPosition = currentPosition - 10000;

            //seek to new progress
            mediaPlayer.seekTo(newPosition);
        }
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        if (hours != 0) {
            buf
                    .append(hours)
                    .append(":")
                    .append(String.format("%02d", minutes))
                    .append(":")
                    .append(String.format("%02d", seconds));
        } else {
            buf
                    .append(String.format("%02d", minutes))
                    .append(":")
                    .append(String.format("%02d", seconds));
        }
        return buf.toString();
    }

    private void episodeEnding() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            playpauseButton.setImageResource(R.mipmap.the_commentist);
        }
    }

}