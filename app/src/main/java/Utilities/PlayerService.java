package Utilities;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studio111.commentist.MainActivity;
import com.example.studio111.commentist.R;

import java.io.IOException;

import static android.media.session.PlaybackState.ACTION_PLAY;
import static android.os.Build.VERSION_CODES.M;
import static com.example.studio111.commentist.R.id.playerEpisodeName;
import static com.example.studio111.commentist.R.id.totalTime;
import static com.example.studio111.commentist.R.layout.player;

/**
 * Created by rsteller on 1/20/2017.
 */

public class PlayerService extends Service {

    MediaPlayer mediaPlayer = null;
    ImageButton playPauseButton;
    SeekBar mSeekBar;
    Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void LoadUrl(String url, SeekBar seekBar, Activity activity) {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();

        }
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mSeekBar = seekBar;
        seekBar.setMax(mediaPlayer.getDuration() / 1000);

        Activity mainActivity = activity;
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    mSeekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b){
                    mediaPlayer.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void Play() {
        mediaPlayer.start();
    }

    public void Pause() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
           mediaPlayer.start();
        }

    }

}
