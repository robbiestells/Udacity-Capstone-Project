package com.example.studio111.commentist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridView = (GridView) findViewById(R.id.gridView);
       // ReadRss readRss = new ReadRss(this, recyclerView);
        ReadRss readRss = new ReadRss(this, gridView);
        readRss.execute();
    }


}
