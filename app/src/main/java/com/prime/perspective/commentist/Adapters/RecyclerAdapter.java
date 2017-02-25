package com.prime.perspective.commentist.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prime.perspective.commentist.MainActivity;
import com.prime.perspective.commentist.Objects.FeedItem;
import com.prime.perspective.commentist.R;

import java.util.ArrayList;

import static com.prime.perspective.commentist.R.id.playEpisode;

/**
 * Created by robbi on 2/25/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;

    public RecyclerAdapter(Context context, ArrayList<FeedItem>feedItems){
        this.feedItems=feedItems;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.feed_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //set feed item data
        final FeedItem current=feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Date.setText(current.getPubDate());
        holder.Length.setText(current.getLength());

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass item to mainactivity to paly
                MainActivity mainActivity = MainActivity.getInstance();
                mainActivity.PlayEpisode(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title,Date,Length;
        ImageButton playButton;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //get views
            Title= (TextView) itemView.findViewById(R.id.episodeName);
            Length = (TextView) itemView.findViewById(R.id.episodeLength);
            Date= (TextView) itemView.findViewById(R.id.episodeDate);
            playButton = (ImageButton) itemView.findViewById(playEpisode);
            cardView= (CardView) itemView.findViewById(R.id.episodeCard);
        }

        @Override
        public void onClick(View view) {
            //get episode that was clicked on and pass to main activity
            FeedItem selected = feedItems.get(getPosition());

            MainActivity mainActivity = MainActivity.getInstance();
            mainActivity.OnEpisodeSelected(selected);
        }
    }
}