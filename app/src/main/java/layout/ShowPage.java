package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studio111.commentist.R;

import Objects.Show;
import Utilities.PlayerControls;
import Utilities.ReadRss;

import static com.example.studio111.commentist.R.id.listView;
import static com.example.studio111.commentist.R.id.showLogo;

/**
 * Created by rsteller on 1/17/2017.
 */

public class ShowPage extends Fragment {
    View myFragmentView;
    Show selectedShow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_show_page, container, false);

        Bundle bundle = this.getArguments();
        selectedShow = bundle.getParcelable("show");

        ImageView imageView = (ImageView) myFragmentView.findViewById(R.id.logo);
       // imageView.setTransitionName("test");
         imageView.setImageResource(selectedShow.getImage());

        TextView showDescription = (TextView) myFragmentView.findViewById(R.id.showDescription);
        showDescription.setText(selectedShow.getDescription());

//
//        listView = (ListView) findViewById(listView);
//
//        ReadRss readRss = new ReadRss(this, listView);
//        readRss.execute(selectedShow.getFeed());
//


        return myFragmentView;
    }
}

