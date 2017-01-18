package Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studio111.commentist.R;

import java.util.ArrayList;

import Objects.FeedItem;

/**
 * Created by robbi on 1/17/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;
    public RecyclerAdapter(Context context,ArrayList<FeedItem>feedItems){
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
        //YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        FeedItem current=feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        //holder.Length.setText(current.getLength());
       // holder.Url.setText(current.getAudioUrl());
        //holder.Link.setText(current.getLink());
        
        //Picasso.with(context).load(current.getThumbnailUrl()).into(holder.Thumbnail);

    }



    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description,Date,Length,Url,Link;
        ImageView Thumbnail;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title= (TextView) itemView.findViewById(R.id.episodeName);
            Description= (TextView) itemView.findViewById(R.id.episodeDescription);
            Date= (TextView) itemView.findViewById(R.id.episodeDate);
            cardView= (CardView) itemView.findViewById(R.id.episodeCard);
        }
    }
}

