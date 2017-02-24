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
import com.prime.perspective.commentist.MainActivity;
import com.prime.perspective.commentist.Objects.FeedItem;
import com.prime.perspective.commentist.R;

import java.util.ArrayList;

import static android.R.attr.description;
import static com.prime.perspective.commentist.R.drawable.play;

/**
 * Created by robbi on 2/24/2017.
 */

public class FeedAdapter extends CursorAdapter {

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
        int id_show = cursor.getColumnIndex(FeedEntry.COLUMN_SHOW_NAME);
        int id_link = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_LINK);
        int id_audio = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_AUDIO);
        int id_description = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_DESCRIPTION);

        //get values
        final String name = cursor.getString(id_title);
        final String date = cursor.getString(id_date);
        final String length = cursor.getString(id_length);
        final String show = cursor.getString(id_show);
        final String link = cursor.getString(id_link);
        final String description = cursor.getString(id_description);
        final String audio = cursor.getString(id_audio);


        //set views
        Title.setText(name);
        Date.setText(date);
        Length.setText(length);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.getInstance();
                FeedItem feedItem = new FeedItem(show, name, description, link, date, audio, length );
                mainActivity.OnEpisodeSelected(feedItem);
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MainActivity mainActivity = MainActivity.getInstance();
                FeedItem feedItem = new FeedItem(show, name, description, link, date, audio, length );
                mainActivity.PlayEpisode(feedItem);
            }
        });
    }
}
