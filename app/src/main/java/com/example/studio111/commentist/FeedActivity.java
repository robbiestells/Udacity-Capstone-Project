package com.example.studio111.commentist;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.MediaController;

/**
 * Created by robbi on 1/11/2017.
 */

public class FeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;
    ImageButton playButton;
    PlayMedia playMedia;
    MediaPlayer mediaPlayer;
    PlayerControls controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridView = (GridView) findViewById(R.id.gridView);
        // ReadRss readRss = new ReadRss(this, recyclerView);
        ReadRss readRss = new ReadRss(this, gridView);
        readRss.execute();

        playButton = (ImageButton) findViewById(R.id.playEpisode);

        controls = new PlayerControls();
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mediaPlayer = new MediaPlayer();
////                String url = "http://thecommentist.com/wp-content/uploads/2016/12/RtH301.mp3";
////                playMedia = new PlayMedia(url);
//                Intent intent = new Intent(FeedActivity.this, EpisodePageActivity.class);
//                startActivity(intent);
//            }
//        });

    }
    public void playEpisodeClick(View v){

        String url = "http://thecommentist.com/wp-content/uploads/2016/12/RtH301.mp3";
        //playMedia = new PlayMedia(url);
        controls.LoadUrl(url);
    }
}
