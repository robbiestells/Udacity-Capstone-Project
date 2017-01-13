package com.example.studio111.commentist;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import Objects.Show;
import Utilities.PlayerControls;
import Utilities.ReadRss;

/**
 * Created by robbi on 1/11/2017.
 */

public class FeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;
    ImageButton playButton;
    MediaPlayer mediaPlayer;
    PlayerControls controls;
    Show selectedShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Intent intent = getIntent();
        selectedShow = (Show) intent.getParcelableExtra("selectedShow");

        TextView showDescription = (TextView) findViewById(R.id.showDescription);
        showDescription.setText(selectedShow.getDescription());

        setTitle(selectedShow.getName());


        gridView = (GridView) findViewById(R.id.gridView);

        ReadRss readRss = new ReadRss(this, gridView);
        readRss.execute(selectedShow.getFeed());

        playButton = (ImageButton) findViewById(R.id.playEpisode);

        controls = new PlayerControls();

    }
    public void playEpisodeClick(View v){

        String url = "http://thecommentist.com/wp-content/uploads/2016/12/RtH301.mp3";
        //playMedia = new PlayMedia(url);
        controls.LoadUrl(url);
    }
}
