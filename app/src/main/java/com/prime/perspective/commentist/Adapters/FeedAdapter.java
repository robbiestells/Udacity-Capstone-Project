package com.prime.perspective.commentist.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prime.perspective.commentist.Data.FeedContract;
import com.prime.perspective.commentist.Data.FeedContract.FeedEntry;
import com.prime.perspective.commentist.Objects.FeedItem;
import com.prime.perspective.commentist.R;

import java.util.ArrayList;

import static com.prime.perspective.commentist.R.drawable.play;

/**
 * Created by robbi on 2/24/2017.
 */

public class FeedAdapter extends CursorAdapter {

//    ArrayList<FeedItem> feedItems;
//    Context context;
//    AdapterCallback callback;
//    EpisodeCallback episodeCallback;
//    //create interface to pass selected episode
//    public interface AdapterCallback{
//        void onItemClicked(FeedItem item);
//    }
//
//    public interface EpisodeCallback{
//        void onEpisodePlay(FeedItem feedItem);
//    }
//
//    public FeedAdapter(Context context, ArrayList<FeedItem> feedItems, RecyclerAdapter.AdapterCallback callback, RecyclerAdapter.EpisodeCallback episodeCallback){
//        this.feedItems=feedItems;
//        this.context=context;
//        this.callback = callback;
//        this.episodeCallback = episodeCallback;
//    }

    public FeedAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       View view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView Title = (TextView) view.findViewById(R.id.episodeName);
        final TextView Date = (TextView) view.findViewById(R.id.episodeDate);
        final TextView Length = (TextView) view.findViewById(R.id.episodeLength);
        final ImageButton playButton = (ImageButton) view.findViewById(R.id.playEpisode);
        final CardView cardView = (CardView) view.findViewById(R.id.episodeCard);

        int id_title = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_TITLE);
        int id_date = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_DATE);
        int id_length = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_LENGTH);

        //get values
        String name = cursor.getString(id_title);
        String date = cursor.getString(id_date);
        String length = cursor.getString(id_length);

        //set views
        Title.setText(name);
        Date.setText(date);
        Length.setText(length);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
