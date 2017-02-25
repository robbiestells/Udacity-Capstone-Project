package layout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prime.perspective.commentist.Adapters.FeedAdapter;
import com.prime.perspective.commentist.R;

import java.util.ArrayList;

import com.prime.perspective.commentist.Adapters.HostAdapter;
import com.prime.perspective.commentist.Data.FeedContract.FeedEntry;
import com.prime.perspective.commentist.Data.FeedDbHelper;
import com.prime.perspective.commentist.Objects.FeedItem;
import com.prime.perspective.commentist.Objects.Host;
import com.prime.perspective.commentist.Objects.Show;


/**
 * Created by rsteller on 1/17/2017.
 */

public class ShowPage extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    View myFragmentView;
    Show selectedShow;
    GridView hostGrid;
    ImageView imageView;

    private static final int LOADER = 0;
    FeedAdapter mCursorAdapter;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_EPISODE_TITLE,
                FeedEntry.COLUMN_EPIOSDE_LINK,
                FeedEntry.COLUMN_EPISODE_DESCRIPTION,
                FeedEntry.COLUMN_EPISODE_DATE,
                FeedEntry.COLUMN_EPIOSDE_LENGTH,
                FeedEntry.COLUMN_EPIOSDE_AUDIO,
                FeedEntry.COLUMN_SHOW_NAME
        };
        String selection = FeedEntry.COLUMN_SHOW_NAME + " LIKE ? ";
        String[] selectionArgs = {selectedShow.getName()};

        return new CursorLoader(
                getContext(),
                FeedEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

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

        imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
        imageView.setImageResource(selectedShow.getImage());

        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.showDescription);
        showDescription.setText(selectedShow.getDescription());

        hostGrid = (GridView) myFragmentView.findViewById(R.id.hostGrid);

        ArrayList<Host> hosts = new ArrayList<Host>();
        switch (selectedShow.getName()) {
            case "The Bearded Vegans":
                hosts.add(new Host(getResources().getString(R.string.andy), R.drawable.andy));
                hosts.add(new Host(getResources().getString(R.string.paul), R.drawable.paul));
                break;
            case "Roll to Hit (5th Ed. Dungeons and Dragons)":
                hosts.add(new Host(getResources().getString(R.string.rob), R.drawable.rob));
                hosts.add(new Host(getResources().getString(R.string.shawn), R.drawable.shawn));
                hosts.add(new Host(getResources().getString(R.string.paul), R.drawable.paul));
                hosts.add(new Host(getResources().getString(R.string.josiah), R.drawable.josiah));
                hosts.add(new Host(getResources().getString(R.string.david), R.drawable.david));
                break;
            case "The Unwind (Tech, Games, Gadgets, and Geek Culture)":
                hosts.add(new Host(getResources().getString(R.string.rob), R.drawable.rob));
                hosts.add(new Host(getResources().getString(R.string.shawn), R.drawable.shawn));
                hosts.add(new Host(getResources().getString(R.string.josiah), R.drawable.josiah));
                break;
            case "Sky on Fire: A Star Wars RPG":
                hosts.add(new Host(getResources().getString(R.string.wren), R.drawable.wren));
                hosts.add(new Host(getResources().getString(R.string.zem), R.drawable.zem));
                hosts.add(new Host(getResources().getString(R.string.scrapper), R.drawable.scrapper));
                hosts.add(new Host(getResources().getString(R.string.hulo), R.drawable.hulo));
                hosts.add(new Host(getResources().getString(R.string.dewethar), R.drawable.dewie));
                hosts.add(new Host(getResources().getString(R.string.afagella), R.drawable.afagella));
                hosts.add(new Host(getResources().getString(R.string.sollar), R.drawable.sollar));
                break;
            default:
                hosts.add(new Host(getResources().getString(R.string.rob), R.drawable.rob));
                hosts.add(new Host(getResources().getString(R.string.shawn), R.drawable.shawn));
                hosts.add(new Host(getResources().getString(R.string.josiah), R.drawable.josiah));
        }

        //put shows in grid adapter
        HostAdapter adapter = new HostAdapter(this.getActivity(), hosts);
        hostGrid.setAdapter(adapter);

        mCursorAdapter = new FeedAdapter(getContext(), null);
        ListView showList = (ListView) myFragmentView.findViewById(R.id.showListView);
        showList.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(LOADER, null, this);

        return myFragmentView;
    }

    private ArrayList<FeedItem> getSavedFeed(Show selectedShow) {
        FeedDbHelper mDbHelper = new FeedDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME + " WHERE " + FeedEntry.COLUMN_SHOW_NAME
                + " =?";

        Cursor cursor = db.rawQuery(query, new String[]{selectedShow.getName()});

        int nameColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_TITLE);
        int linkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_LINK);
        int descColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_DESCRIPTION);
        int dateColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_DATE);
        int lengthColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_LENGTH);
        int audioColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_EPIOSDE_AUDIO);
        int showColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_SHOW_NAME);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FeedItem item = new FeedItem();
            item.setTitle(cursor.getString(nameColumnIndex));
            item.setLink(cursor.getString(linkColumnIndex));
            item.setDescription(cursor.getString(descColumnIndex));
            item.setPubDate(cursor.getString(dateColumnIndex));
            item.setLength(cursor.getString(lengthColumnIndex));
            item.setAudioUrl(cursor.getString(audioColumnIndex));
            item.setShow(cursor.getString(showColumnIndex));
            feedItems.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return feedItems;
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

