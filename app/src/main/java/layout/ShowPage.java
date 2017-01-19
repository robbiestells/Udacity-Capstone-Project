package layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import Adapters.RecyclerAdapter;
import Objects.FeedItem;
import Objects.Show;
import Utilities.ReadRss;

/**
 * Created by rsteller on 1/17/2017.
 */

public class ShowPage extends Fragment implements RecyclerAdapter.AdapterCallback {
    View myFragmentView;
    Show selectedShow;
    RecyclerView recyclerView;
    OnEpisodeSelectedListener episodeCallback;

   // connects fragment to MainPage to pass selected episode
    public interface OnEpisodeSelectedListener {
        public void OnEpisodeSelected(FeedItem feedItem);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_show_page, container, false);

        //gets selected show
        Bundle bundle = this.getArguments();
        selectedShow = bundle.getParcelable("show");

//        ImageView imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
//       // imageView.setTransitionName("test");
//         imageView.setImageResource(selectedShow.getImage());

        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.showDescription);
        showDescription.setText(selectedShow.getDescription());

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.showListView);

        //kicks off getting RSS feed for show
        ReadRss readRss = new ReadRss(this.getContext(), recyclerView, ShowPage.this);
        readRss.execute(selectedShow.getFeed());

        return myFragmentView;
    }

    //passes episode to MainActivity
    @Override
    public void onItemClicked(FeedItem feedItem){
        episodeCallback.OnEpisodeSelected(feedItem);
    }

    //attaches callback to OnEpisodeSelected in MainActivity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            episodeCallback = (OnEpisodeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShowSelectedListener");
        }
    }
}

