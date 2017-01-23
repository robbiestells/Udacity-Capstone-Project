package layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import Adapters.HostAdapter;
import Adapters.RecyclerAdapter;
import Adapters.ShowAdapter;
import Objects.FeedItem;
import Objects.Host;
import Objects.Show;
import Utilities.ReadRss;

import static android.R.attr.host;

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

        final ArrayList<Host> hosts = new ArrayList<Host>();

        hosts.add(new Host("Rob", R.drawable.rob));
        hosts.add(new Host("Paul", R.drawable.paul));

        //put shows in grid adapter
        HostAdapter adapter = new HostAdapter(this.getActivity(), hosts);
        hostGrid = (GridView) myFragmentView.findViewById(R.id.hostGrid);
        hostGrid.setAdapter(adapter);


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
        if (savedInstanceState != null){

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}

