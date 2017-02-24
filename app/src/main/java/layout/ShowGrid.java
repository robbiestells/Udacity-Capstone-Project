package layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.prime.perspective.commentist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import com.prime.perspective.commentist.Adapters.ShowAdapter;
import com.prime.perspective.commentist.Objects.Show;

public class ShowGrid extends Fragment {
    GridView gridView;
    View myFragmentView;
    OnShowSelectedListener mCallback;
    AdView mAdView;

    //Passes Show object to MainActivity
    public interface OnShowSelectedListener {
        public void OnShowSelected(Show show);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_show_grid, container, false);


        mAdView = (AdView) myFragmentView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //create the list of shows on network
        final ArrayList<Show> shows = new ArrayList<Show>();

        shows.add(new Show(getString(R.string.RollName), getString(R.string.RollDes), R.drawable.rth, getString(R.string.RollLink)));
        shows.add(new Show(getString(R.string.BVName), getString(R.string.BVDes), R.drawable.vegans, getString(R.string.BVLink)));
        shows.add(new Show(getString(R.string.UnwindName), getString(R.string.UnwindDes), R.drawable.unwind, getString(R.string.UnwindLink)));
        shows.add(new Show(getString(R.string.SkyName), getString(R.string.SkyDes), R.drawable.sky, getString(R.string.SkyLink)));

        //put shows in grid adapter
        ShowAdapter adapter = new ShowAdapter(this.getActivity(), shows);
        gridView = (GridView) myFragmentView.findViewById(R.id.showGrid);
        gridView.setAdapter(adapter);

        //send selected show back to MainActivity when clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Show show = (Show) adapterView.getItemAtPosition(i);
                mCallback.OnShowSelected(show);
            }
        });

        return myFragmentView;
    }

    //set up connection to MainActivity for select show callback
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
