package Utilities;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;

import java.io.IOException;

import static android.media.session.PlaybackState.ACTION_PLAY;
import static android.os.Build.VERSION_CODES.M;
import static com.example.studio111.commentist.R.layout.player;

/**
 * Created by rsteller on 1/20/2017.
 */

public class PlayerService extends Service  {

    MediaPlayer mediaPlayer = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void LoadUrl(String url){

        if (mediaPlayer != null){
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
        }  catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void Play() {
        mediaPlayer.start();
    }

    public void Pause() {
        mediaPlayer.pause();
    }

}
