package Adapters;

/**
 * Created by rsteller on 1/23/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import Objects.Host;

import android.widget.TextView;


/**
 * Created by rsteller on 1/13/2017.
 */

public class HostAdapter extends ArrayAdapter<Host> {

    public HostAdapter(Context context, ArrayList<Host> hosts) {
        super(context, 0, hosts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.host_item, parent, false);
        }

        // Get the host and load the name and image
        Host currentHost = getItem(position);

        TextView titleTV = (TextView) listItemView.findViewById(R.id.hostName);
        titleTV.setText(currentHost.getName());

        ImageView hostImage = (ImageView) listItemView.findViewById(R.id.hostImage);
        hostImage.setImageResource(currentHost.getImage());

        return listItemView;
    }
}
