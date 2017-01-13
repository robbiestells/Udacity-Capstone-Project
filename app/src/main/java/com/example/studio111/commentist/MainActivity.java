package com.example.studio111.commentist;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;

import Adapters.ShowAdapter;
import Objects.Show;
import layout.ShowGrid;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity implements ShowGrid.OnShowSelectedListener {

    private Toolbar mToolbar;
   // GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

//        ArrayList<Show> shows = new ArrayList<Show>();
//        shows.add(new Show("Roll to Hit", "description of RtH", R.drawable.rth, "http://thecommentist.com/feed/rolltohitshow/"));
//        shows.add(new Show("Bearded Vegans", "description of BV", R.drawable.vegans, "http://thecommentist.com/feed/thebeardedvegans/"));
//        shows.add(new Show("Unwind", "description of unwind", R.drawable.unwind, "http://thecommentist.com/feed/theunwindpodcast/"));
//        shows.add(new Show("Sky on Fire", "description of sky", R.drawable.sky, "http://thecommentist.com/feed/skyonfire/"));
//
//        ShowAdapter adapter = new ShowAdapter(this, shows);
//        gridView = (GridView) findViewById(R.id.showGrid);
//        gridView.setAdapter(adapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, FeedActivity.class);
//
//                Show show = (Show) adapterView.getItemAtPosition(i);
//                intent.putExtra("selectedShow", show);
//
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View)findViewById(R.id.showLogo), "logoImage");
//                startActivity(intent, options.toBundle());
//
//            }
//        });
    }

    @Override
    public void OnShowSelected(int position) {
        Log.d("Working", "OnShowSelected: " + position);
    }
}
