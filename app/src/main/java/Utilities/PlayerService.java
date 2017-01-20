package Utilities;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by rsteller on 1/20/2017.
 */

public class PlayerService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();

    }

    public void LoadUrl(String url){

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
        //mediaPlayer.start();
        Play(mediaPlayer);
    }

    public void Play(MediaPlayer player) {
        player.start();
    }

    public void Pause(MediaPlayer player) {
        player.pause();
    }
}
