package layout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import Adapters.ShowAdapter;
import Objects.Show;

public class ShowGrid extends Fragment {
    GridView gridView;
    Context context;
    View myFragmentView;
    OnShowSelectedListener mCallback;

    //change this to pass a Show object, then tell the Main activity to load the show page fragment
    public interface OnShowSelectedListener {
        public void OnShowSelected(Show show);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_show_grid, container, false);
        final ArrayList<Show> shows = new ArrayList<Show>();
        shows.add(new Show("Roll to Hit", "description of RtH", R.drawable.rth, "http://thecommentist.com/feed/rolltohitshow/"));
        shows.add(new Show("Bearded Vegans", "description of BV", R.drawable.vegans, "http://thecommentist.com/feed/thebeardedvegans/"));
        shows.add(new Show("Unwind", "description of unwind", R.drawable.unwind, "http://thecommentist.com/feed/theunwindpodcast/"));
        shows.add(new Show("Sky on Fire", "description of sky", R.drawable.sky, "http://thecommentist.com/feed/skyonfire/"));

        ShowAdapter adapter = new ShowAdapter(this.getActivity(), shows);
        gridView = (GridView) myFragmentView.findViewById(R.id.showGrid);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(context, FeedActivity.class);
//
                Show show = (Show) adapterView.getItemAtPosition(i);
                //intent.putExtra("selectedShow", show);
//
//                //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ShowGrid.this, (View) findViewById(R.id.showLogo), "logoImage");
//                // startActivity(intent, options.toBundle());
//                startActivity(intent);

                mCallback.OnShowSelected(show);

            }
        });

        return myFragmentView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnShowSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShowSelectedListener");
        }

    }
}
