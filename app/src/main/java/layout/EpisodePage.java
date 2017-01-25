package layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studio111.commentist.R;

import Objects.FeedItem;
import Objects.Show;
import Utilities.ReadRss;

/**
 * Created by rsteller on 1/18/2017.
 */

public class EpisodePage extends Fragment {
    View myFragmentView;
    FeedItem selectedEpisode;
    PlayEpisode playEpisodeCallback;

    // connects fragment to MainPage to play selected episode
    public interface PlayEpisode {
        public void PlayEpisode(FeedItem feedItem);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_episode_page, container, false);

        //get selected episode
        Bundle bundle = this.getArguments();
        selectedEpisode = bundle.getParcelable("episode");

//        ImageView imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
//       // imageView.setTransitionName("test");
//         imageView.setImageResource(selectedShow.getImage());

        //set description
        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.episodeDescription);
        showDescription.setText(selectedEpisode.getDescription());

        //set title
        TextView showTitle = (TextView) myFragmentView.findViewById(R.id.episodeName);
        showTitle.setText(selectedEpisode.getTitle());

        //set length
        TextView showLength = (TextView) myFragmentView.findViewById(R.id.episodeLength);
        showLength.setText(selectedEpisode.getLength());

        //set date
        TextView showDate = (TextView) myFragmentView.findViewById(R.id.episodeDate);
        showDate.setText(selectedEpisode.getPubDate());

        FloatingActionButton playButton = (FloatingActionButton) myFragmentView.findViewById(R.id.playEpisodeButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayEpisode(selectedEpisode);
            }
        });

        return myFragmentView;
    }

    //attaches callback to OnEpisodeSelected in MainActivity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            playEpisodeCallback = (PlayEpisode) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShowSelectedListener");
        }
    }

    @Override
    public void PlayEpisode(FeedItem feedItem) {
        playEpisodeCallback.PlayEpisode(feedItem);
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