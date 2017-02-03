package layout;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.studio111.commentist.MainActivity;
import com.example.studio111.commentist.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.FeedAdapter;
import Adapters.HostAdapter;
import Adapters.RecyclerAdapter;
import Data.FeedContract;
import Data.FeedContract.FeedEntry;
import Data.FeedDbHelper;
import Objects.FeedItem;
import Objects.Host;
import Objects.Show;
import Utilities.ReadRss;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.view.View.GONE;


/**
 * Created by rsteller on 1/17/2017.
 */

public class ShowPage extends Fragment implements RecyclerAdapter.AdapterCallback, RecyclerAdapter.EpisodeCallback {
    View myFragmentView;
    Show selectedShow;
    GridView hostGrid;
    RecyclerView recyclerView;
    OnEpisodeSelectedListener episodeCallback;
    OnEpisodePlay playEpisodeCallback;

    // connects fragment to MainPage to pass selected episode
    public interface OnEpisodeSelectedListener {
        public void OnEpisodeSelected(FeedItem feedItem);
    }

    // connects fragment to MainPage to play selected episode
    public interface OnEpisodePlay {
        public void OnEpisodePlay(FeedItem feedItem);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_show_page, container, false);

        setRetainInstance(true);

        //gets selected show
        Bundle bundle = this.getArguments();
        selectedShow = bundle.getParcelable("show");

//        ImageView imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
//       // imageView.setTransitionName("test");
//         imageView.setImageResource(selectedShow.getImage());

        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.showDescription);
        showDescription.setText(selectedShow.getDescription());

        ArrayList<Host> hosts = new ArrayList<Host>();
        if (selectedShow.getName() == getResources().getString(R.string.UnwindName)) {
            hosts.add(new Host("Rob", R.drawable.rob));
            hosts.add(new Host("Paul", R.drawable.paul));
        } else if (selectedShow.getName() == getResources().getString(R.string.BVName)) {
            hosts.add(new Host("Paul", R.drawable.paul));
        } else if (selectedShow.getName() == getResources().getString(R.string.RollName)) {
            hosts.add(new Host("Rob", R.drawable.rob));
        } else {
            hosts.add(new Host("Paul", R.drawable.paul));
            hosts.add(new Host("Paul", R.drawable.paul));
        }
        //put shows in grid adapter
        HostAdapter adapter = new HostAdapter(this.getActivity(), hosts);
        hostGrid = (GridView) myFragmentView.findViewById(R.id.hostGrid);
        hostGrid.setAdapter(adapter);

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.showListView);

        ArrayList<FeedItem> feedItems = new ArrayList<>();
        feedItems = getSavedFeed(selectedShow);

        if (feedItems != null) {
            RecyclerAdapter feedAdapter = new RecyclerAdapter(getContext(), feedItems, ShowPage.this, ShowPage.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.getLayoutManager().isSmoothScrolling();
            recyclerView.setAdapter(feedAdapter);
        }


        //kicks off getting RSS feed for show
      // ReadRss readRss = new ReadRss(this.getContext(), recyclerView, ShowPage.this);
      //  readRss.execute(selectedShow.getFeed());

        return myFragmentView;
    }

    private ArrayList<FeedItem> getSavedFeed(Show selectedShow) {
        FeedDbHelper mDbHelper = new FeedDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME + " WHERE " + FeedEntry.COLUMN_SHOW_NAME
                + " =?";

        Cursor cursor = db.rawQuery(query, new String[] {selectedShow.getName()});

        int nameColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_TITLE);
        int linkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_LINK);
        int descColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_DESCRIPTION);
        int dateColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_DATE);
        int lengthColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_LENGTH);
        int audioColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_AUDIO);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FeedItem item = new FeedItem();
            item.setTitle(cursor.getString(nameColumnIndex));
            item.setLink(cursor.getString(linkColumnIndex));
            item.setDescription(cursor.getString(descColumnIndex));
            item.setPubDate(cursor.getString(dateColumnIndex));
            item.setLength(cursor.getString(lengthColumnIndex));
            item.setAudioUrl(cursor.getString(audioColumnIndex));
            feedItems.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return feedItems;
    }


    //passes episode to MainActivity
    @Override
    public void onItemClicked(FeedItem feedItem) {
        episodeCallback.OnEpisodeSelected(feedItem);
    }

    //attaches callback to OnEpisodeSelected in MainActivity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            episodeCallback = (OnEpisodeSelectedListener) activity;
            playEpisodeCallback = (OnEpisodePlay) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShowSelectedListener");
        }
    }

    @Override
    public void onEpisodePlay(FeedItem feedItem) {
        playEpisodeCallback.OnEpisodePlay(feedItem);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}

