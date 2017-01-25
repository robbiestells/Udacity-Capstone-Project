package com.example.studio111.commentist;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import Objects.FeedItem;
import Objects.Show;
import Utilities.PlayerControls;
import Utilities.PlayerService;
import layout.EpisodePage;
import layout.ShowGrid;
import layout.ShowPage;

import static android.R.attr.fragment;
import static com.example.studio111.commentist.R.id.totalTime;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity implements ShowGrid.OnShowSelectedListener, ShowPage.OnEpisodeSelectedListener, ShowPage.OnEpisodePlay, EpisodePage.PlayEpisode {

    ImageView logoImage;
    Show selectedShow;
    FeedItem selectedEpisode;
    TextView playerEpisodeName;

    SeekBar seekBar;
    TextView totalTimeTV;

    ImageButton playpauseButton;

    PlayerService playerService;

    boolean isPlaying = false;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            //get PlayerService,  logo image, player fragment


        } else {
             playerService = new PlayerService();
            Intent startPlayer = new Intent(this, PlayerService.class);
            startService(startPlayer);
            //mToolbar = (Toolbar) findViewById(R.id.toolbar);

            //load Show listing and load fragment
            Fragment fragment = new ShowGrid();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }

            logoImage = (ImageView) findViewById(R.id.logo);

            totalTimeTV = (TextView) findViewById(totalTime);
            playpauseButton = (ImageButton) findViewById(R.id.playpause);
            playerEpisodeName = (TextView) findViewById(R.id.playerEpisodeName);

            playpauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isPlaying){
                        playpauseButton.setImageResource(R.drawable.play_circle);
                        isPlaying = false;
                    } else {
                        playpauseButton.setImageResource(R.drawable.pause_circle);
                        isPlaying = true;
                    }
                    playerService.Pause();
                }
            });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    //pass Show object and change fragment when show selected
    @Override
    public void OnShowSelected(Show show) {

        logoImage.setImageResource(show.getImage());
        logoImage.setMinimumHeight(400);
        logoImage.setVisibility(View.VISIBLE);
        //replace fragment and send Show object
        ShowPage showFragment = new ShowPage();
        Bundle args = new Bundle();
        args.putParcelable("show", show);
        showFragment.setArguments(args);

        selectedShow = show;

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, showFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //when episode is selected, load fragment with selected episode information
    @Override
    public void OnEpisodeSelected(FeedItem feedItem) {

        //replace fragment
        EpisodePage episodeFragment = new EpisodePage();
        Bundle args = new Bundle();
        args.putParcelable("episode", feedItem);
        episodeFragment.setArguments(args);

        selectedEpisode = feedItem;

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, episodeFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    //when episode is selected, load fragment with selected episode information
    @Override
    public void OnEpisodePlay(FeedItem feedItem) {
        playpauseButton.setImageResource(R.drawable.pause_circle);

        final FeedItem selectedItem = feedItem;
        playerEpisodeName.setText(feedItem.getTitle());
        playerEpisodeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //replace fragment
                EpisodePage episodeFragment = new EpisodePage();
                Bundle args = new Bundle();
                args.putParcelable("episode", selectedItem);
                episodeFragment.setArguments(args);

                selectedEpisode = selectedItem;

                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, episodeFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        totalTimeTV.setText(feedItem.getLength());

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        playerService.LoadUrl(feedItem.getAudioUrl(), seekBar, MainActivity.this);
        isPlaying = true;

    }

    @Override
    public void PlayEpisode(FeedItem feedItem) {
        playpauseButton.setImageResource(R.drawable.pause_circle);

        final FeedItem selectedItem = feedItem;
        playerEpisodeName.setText(feedItem.getTitle());
        playerEpisodeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //replace fragment
                EpisodePage episodeFragment = new EpisodePage();
                Bundle args = new Bundle();
                args.putParcelable("episode", selectedItem);
                episodeFragment.setArguments(args);

                selectedEpisode = selectedItem;

                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, episodeFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        totalTimeTV.setText(feedItem.getLength());

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        playerService.LoadUrl(feedItem.getAudioUrl(), seekBar, MainActivity.this);
        isPlaying = true;
    }
}
