package layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prime.perspective.commentist.MainActivity;
import com.prime.perspective.commentist.R;

import com.prime.perspective.commentist.Objects.FeedItem;

/**
 * Created by rsteller on 1/18/2017.
 */

public class EpisodePage extends Fragment {

    View myFragmentView;
    FeedItem selectedEpisode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_episode_page, container, false);

        //get selected episode
        Bundle bundle = this.getArguments();
        selectedEpisode = bundle.getParcelable("episode");

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
                MainActivity mainActivity = MainActivity.getInstance();
                mainActivity.PlayEpisode(selectedEpisode);
            }
        });

        return myFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}