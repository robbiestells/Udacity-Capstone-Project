package com.prime.perspective.commentist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.prime.perspective.commentist.R;

import java.util.ArrayList;

import com.prime.perspective.commentist.Objects.Show;

/**
 * Created by rsteller on 1/13/2017.
 */

public class ShowAdapter extends ArrayAdapter<Show> {

    public ShowAdapter(Context context, ArrayList<Show> shows) {
        super(context, 0, shows);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.show_item, parent, false);
        }

        // Get the Show and load the image
        Show currentShow = getItem(position);

        ImageView showImage = (ImageView) listItemView.findViewById(R.id.showLogo);

        showImage.setImageResource(currentShow.getImage());
        showImage.setTransitionName("test");

        return listItemView;
    }
}
