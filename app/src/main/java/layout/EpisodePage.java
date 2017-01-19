package layout;

import android.os.Bundle;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_episode_page, container, false);

        Bundle bundle = this.getArguments();
        selectedEpisode = bundle.getParcelable("episode");

//        ImageView imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
//       // imageView.setTransitionName("test");
//         imageView.setImageResource(selectedShow.getImage());

        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.episodeDescription);
        showDescription.setText(selectedEpisode.getDescription());

        TextView showTitle = (TextView) myFragmentView.findViewById(R.id.episodeName);
        showTitle.setText(selectedEpisode.getTitle());

        TextView showLength = (TextView) myFragmentView.findViewById(R.id.episodeLength);
        showLength.setText(selectedEpisode.getLength());

        TextView showDate = (TextView) myFragmentView.findViewById(R.id.episodeDate);
        showDate.setText(selectedEpisode.getPubDate());


        return myFragmentView;
    }


}