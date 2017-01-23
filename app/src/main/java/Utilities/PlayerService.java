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

import static android.R.attr.max;
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
    TextView currentTimeText;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void LoadUrl(String url, final SeekBar seekBar, Activity activity) {

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
//        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        seekBar.setMax(100);

        Activity mainActivity = activity;
        currentTimeText  = (TextView) activity.findViewById(R.id.currentTime);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
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

    private String getTimeString(long millis){
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
        }
        else  {
            buf
                    .append(String.format("%02d", minutes))
                    .append(":")
                    .append(String.format("%02d", seconds));
        }
        return buf.toString();
    }

}
