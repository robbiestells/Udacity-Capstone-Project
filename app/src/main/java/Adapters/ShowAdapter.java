package Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import Objects.Show;

/**
 * Created by rsteller on 1/13/2017.
 */

public class ShowAdapter extends ArrayAdapter<Show> {

    private int mColorResourceId;

    public ShowAdapter(Activity context, ArrayList<Show> shows) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, shows);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.show_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Show currentShow = getItem(position);

        ImageView showImage = (ImageView) listItemView.findViewById(R.id.showLogo);

        showImage.setImageResource(currentShow.getImage());

        return listItemView;

    }
}
