package com.example.studio111.commentist;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import Objects.FeedItem;
import Objects.Show;
import Utilities.PlayerService;
import Utilities.Rss;
import butterknife.BindView;
import butterknife.OnClick;
import layout.EpisodePage;
import layout.ShowGrid;
import layout.ShowPage;

import static android.view.View.GONE;
import static com.example.studio111.commentist.R.layout.player;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity implements ShowGrid.OnShowSelectedListener, ShowPage.OnEpisodeSelectedListener, ShowPage.OnEpisodePlay, EpisodePage.OnEpisodePlayListener {

    @BindView(R.id.logo)
    ImageView logoImage;

    Show selectedShow;
    FeedItem selectedEpisode;
    PlayerService playerService;
    BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.playerEpisodeName)
    TextView playerEpisodeName;

    @BindView(R.id.playpause)
    FloatingActionButton playPauseButton;

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @BindView(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (savedInstanceState != null) {
            //get PlayerService,  logo image, player fragment


        } else {
            playerService = new PlayerService();
            Intent startPlayer = new Intent(this, PlayerService.class);
            startService(startPlayer);
            //mToolbar = (Toolbar) findViewById(R.id.toolBar);

            //load Show listing and load fragment
            Fragment fragment = new ShowGrid();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }

        final ArrayList<Show> shows = new ArrayList<Show>();

        shows.add(new Show(getString(R.string.RollName), getString(R.string.RollDes), R.drawable.rth, getString(R.string.RollLink)));
        shows.add(new Show(getString(R.string.BVName), getString(R.string.BVDes), R.drawable.vegans, getString(R.string.BVLink)));
        shows.add(new Show(getString(R.string.UnwindName), getString(R.string.UnwindDes), R.drawable.unwind, getString(R.string.UnwindLink)));
        shows.add(new Show(getString(R.string.SkyName), getString(R.string.SkyDes), R.drawable.sky, getString(R.string.SkyLink)));

        Rss rss = new Rss(this);
        rss.execute(shows);

        //logoImage = (ImageView) findViewById(R.id.logo);
//        playPauseButton = (FloatingActionButton) findViewById(R.id.playpause);
//        playerEpisodeName = (TextView) findViewById(R.id.playerEpisodeName);

//        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        playPauseButton.setVisibility(GONE);

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
        mToolbar.setVisibility(GONE);
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
        playerService.LoadUrl(selectedItem, MainActivity.this);

        playPauseButton.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setPeekHeight(350);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onEpisodeSelected(FeedItem feedItem) {
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
        playerService.LoadUrl(selectedItem, MainActivity.this);

        if (playPauseButton.getVisibility() == GONE){
            playPauseButton.setVisibility(View.VISIBLE);
        }
        if (bottomSheetBehavior.getPeekHeight() == 0){
            bottomSheetBehavior.setPeekHeight(350);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}
