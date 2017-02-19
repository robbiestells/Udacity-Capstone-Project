package com.example.studio111.commentist.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import com.example.studio111.commentist.Objects.FeedItem;

import static com.example.studio111.commentist.R.id.playEpisode;

/**
 * Created by robbi on 1/17/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;
    AdapterCallback callback;
    EpisodeCallback episodeCallback;

    //create interface to pass selected episode
    public interface AdapterCallback{
        void onItemClicked(FeedItem item);
    }

    public interface EpisodeCallback{
        void onEpisodePlay(FeedItem feedItem);
    }

    public RecyclerAdapter(Context context, ArrayList<FeedItem>feedItems, AdapterCallback callback, EpisodeCallback episodeCallback){
        this.feedItems=feedItems;
        this.context=context;
        this.callback = callback;
        this.episodeCallback = episodeCallback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.feed_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        final FeedItem current=feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        //holder.Length.setText(current.getLength());
       // holder.Url.setText(current.getAudioUrl());
        //holder.Link.setText(current.getLink());

        //Picasso.with(context).load(current.getThumbnailUrl()).into(holder.Thumbnail);

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (episodeCallback != null){
                    episodeCallback.onEpisodePlay(current);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title,Description,Date,Length,Url,Link;
        ImageView Thumbnail;
        ImageButton playButton;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            Title= (TextView) itemView.findViewById(R.id.episodeName);
            Description= (TextView) itemView.findViewById(R.id.episodeDescription);
            Date= (TextView) itemView.findViewById(R.id.episodeDate);
            playButton = (ImageButton) itemView.findViewById(playEpisode);
            cardView= (CardView) itemView.findViewById(R.id.episodeCard);
        }

        @Override
        public void onClick(View view) {
            //get episode that was clicked on
            FeedItem selected = feedItems.get(getPosition());

            //pass this episode to Episode Page fragment
            if (callback != null){
                callback.onItemClicked(selected);
            }
        }
    }
}

