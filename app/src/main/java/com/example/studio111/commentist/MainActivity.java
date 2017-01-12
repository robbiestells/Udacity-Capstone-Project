package com.example.studio111.commentist;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

//tutorial https://www.youtube.com/watch?v=YuKtpnHT3j8&list=PLOvzGCa-rsH-9QjlFBVHfBNUzPGHGzj-5&index=5
//xml feed http://thecommentist.com/feed/rolltohitshow/

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public void rthClick(View v){
        Intent intent = new Intent(MainActivity.this, FeedActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, (View)findViewById(R.id.logo), "rthImage");
        startActivity(intent, options.toBundle());
    }

}
