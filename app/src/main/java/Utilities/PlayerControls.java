package Utilities;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by rsteller on 1/13/2017.
 */

public class PlayerControls {
    Context context;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public PlayerControls (){

    }

    public void LoadUrl(String url){
        // mediaPlayer.release();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
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
