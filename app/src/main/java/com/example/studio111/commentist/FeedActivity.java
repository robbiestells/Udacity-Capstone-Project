package com.example.studio111.commentist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

/**
 * Created by robbi on 1/11/2017.
 */

public class FeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridView = (GridView) findViewById(R.id.gridView);
        // ReadRss readRss = new ReadRss(this, recyclerView);
        ReadRss readRss = new ReadRss(this, gridView);
        readRss.execute();
    }
}
