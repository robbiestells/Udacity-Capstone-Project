package com.example.studio111.commentist;

import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import Objects.FeedItem;
import Objects.Show;
import Utilities.PlayerControls;
import layout.EpisodePage;
import layout.ShowGrid;
import layout.ShowPage;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity implements ShowGrid.OnShowSelectedListener, ShowPage.OnEpisodeSelectedListener {

    ImageView logoImage;
    PlayerControls controls;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mediaPlayer = new MediaPlayer();
         controls = new PlayerControls();
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);

        //load Show listing and load fragment
        Fragment fragment = new ShowGrid();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();

        logoImage = (ImageView) findViewById(R.id.logo);
    }

    //pass Show object and change fragment when show selected
    @Override
    public void OnShowSelected(Show show) {
        Log.d("Working", "OnShowSelected: " + show.getName());

        logoImage.setImageResource(show.getImage());
        logoImage.setMinimumHeight(400);

        //replace fragment and send Show object
        ShowPage showFragment = new ShowPage();
        Bundle args = new Bundle();
        args.putParcelable("show", show);
        showFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, showFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //when episode is selected, load fragment with selected episode information
    @Override
    public void OnEpisodeSelected(FeedItem feedItem) {

        //replace fragment
        EpisodePage episdoeFragment = new EpisodePage();
        Bundle args = new Bundle();
        args.putParcelable("episode", feedItem);
        episdoeFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, episdoeFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }


    public void playEpisode(String url){
        //controls.Pause(mediaPlayer);
        controls.LoadUrl(url);
        controls.Play(mediaPlayer);
    }
}
