package com.prime.perspective.commentist;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;


import java.util.ArrayList;

import com.prime.perspective.commentist.Objects.FeedItem;
import com.prime.perspective.commentist.Objects.Show;
import com.prime.perspective.commentist.Utilities.PlayerService;
import com.prime.perspective.commentist.Utilities.Rss;

import layout.EpisodePage;
import layout.ShowGrid;
import layout.ShowPage;

import static android.view.View.GONE;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity implements ShowGrid.OnShowSelectedListener, ShowPage.OnEpisodeSelectedListener, ShowPage.OnEpisodePlay, EpisodePage.OnEpisodePlayListener {

//    @BindView(R.id.logo)
//    ImageView logoImage;

    Show selectedShow;
    FeedItem selectedEpisode;
    PlayerService playerService;
    BottomSheetBehavior bottomSheetBehavior;

    TextView playerEpisodeName;
    ImageView playerShowLogo;

    FloatingActionButton playPauseButton;

    View bottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            //get PlayerService,  logo image, player fragment
        } else {
            Intent startPlayer = new Intent(this, PlayerService.class);
            startService(startPlayer);

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

        playPauseButton = (FloatingActionButton) findViewById(R.id.playpause);
        playerEpisodeName = (TextView) findViewById(R.id.playerEpisodeName);

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        playerShowLogo = (ImageView) findViewById(R.id.playerShowLogo);

        playPauseButton.setVisibility(GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //pass Show object and change fragment when show selected
    @Override
    public void OnShowSelected(Show show) {

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
        playerService = PlayerService.get();
        playerService.LoadUrl(selectedItem, MainActivity.this);

        playPauseButton.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setPeekHeight(350);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        int showLogo;
        switch (selectedItem.getShow()){
            case "The Bearded Vegans":
                showLogo = R.drawable.vegans;
                break;
            case "Roll to Hit (5th Ed. Dungeons and Dragons)":
                showLogo = R.drawable.rth;
                break;
            case "The Unwind (Tech, Games, Gadgets, and Geek Culture)":
                showLogo = R.drawable.unwind;
                break;
            case "Sky on Fire: A Star Wars RPG":
                showLogo = R.drawable.sky;
                break;
            default:
                showLogo = R.drawable.unwind;
        }
        playerShowLogo.setImageResource(showLogo);
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
        playerService = PlayerService.get();
        playerService.LoadUrl(selectedItem, MainActivity.this);

        if (playPauseButton.getVisibility() == GONE) {
            playPauseButton.setVisibility(View.VISIBLE);
        }
        if (bottomSheetBehavior.getPeekHeight() == 0) {
            bottomSheetBehavior.setPeekHeight(350);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName myappWidget = new ComponentName(this.getPackageName(), CommentistWidgetProvider.class.getName());

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(myappWidget);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.appwidget_layout);
            views.setTextViewText(R.id.widgetEpisodeName, "Play an episode to display here");
            views.setImageViewResource(R.id.widgetLogo, R.mipmap.the_commentist);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
