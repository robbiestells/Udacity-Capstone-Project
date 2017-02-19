package Utilities;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studio111.commentist.MainActivity;
import com.example.studio111.commentist.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import Objects.FeedItem;

import static com.example.studio111.commentist.R.id.seekBar;

/**
 * Created by rsteller on 1/20/2017.
 */

public class PlayerService extends Service {

    MediaPlayer mediaPlayer = null;
    SeekBar mSeekBar;
    Handler mHandler = new Handler();
    TextView currentTimeText;
    TextView totalTimeTV;
    TextView playerEpisodeDesc;
    FloatingActionButton playpauseButton;
    ImageButton forwardButton;
    ImageButton backButton;

    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            episodeEnding();
        }
    };

    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //public void LoadUrl(String url, final SeekBar seekBar, Activity activity) {
    public void LoadUrl(FeedItem feedItem, Activity activity) {
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

        Activity mainActivity = activity;

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

    }

    public void Pause() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playpauseButton.setImageResource(R.drawable.play);
        } else {
            mediaPlayer.start();
            playpauseButton.setImageResource(R.drawable.pause);
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
            playpauseButton.setImageResource(R.drawable.play);

        }
    }

}
