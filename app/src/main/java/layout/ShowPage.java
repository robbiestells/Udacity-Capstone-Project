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

import Objects.FeedItem;
import Objects.Show;
import Utilities.ReadRss;

/**
 * Created by rsteller on 1/17/2017.
 */

public class ShowPage extends Fragment {
    View myFragmentView;
    Show selectedShow;
    RecyclerView recyclerView;
    OnEpisodeSelectedListener episodeCallback;

   // change this to pass a Show object, then tell the Main activity to load the show page fragment
    public interface OnEpisodeSelectedListener {
        public void OnEpisodeSelected(FeedItem feedItem);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_show_page, container, false);

        Bundle bundle = this.getArguments();
        selectedShow = bundle.getParcelable("show");

//        ImageView imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
//       // imageView.setTransitionName("test");
//         imageView.setImageResource(selectedShow.getImage());

        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.showDescription);
        showDescription.setText(selectedShow.getDescription());


        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.showListView);

        ReadRss readRss = new ReadRss(this.getContext(), recyclerView);
        readRss.execute(selectedShow.getFeed());

        return myFragmentView;

    }
    public void selectEpisode(FeedItem feedItem){
        //do something
//        FeedItem test = new FeedItem();
//        test = feedItem;

        episodeCallback.OnEpisodeSelected(feedItem);
    }

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

