package com.prime.perspective.commentist.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rsteller on 1/11/2017.
 */

public class FeedItem implements Parcelable {
    String title;
    String link;
    String description;
    String pubDate;
    String audioUrl;
    String length;
    String show;

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate.substring(0,17);
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int i) {
        out.writeString(show);
        out.writeString(title);
        out.writeString(description);
        out.writeString(link);
        out.writeString(pubDate);
        out.writeString(audioUrl);
        out.writeString(length);
    }

    public FeedItem(){}

    public FeedItem(String show, String title, String description, String link, String pubDate, String audioUrl, String length){
        this.show = show;
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.audioUrl = audioUrl;
        this.length = length;
    }

    private FeedItem(Parcel in){
        show = in.readString();
        title = in.readString();
        description = in.readString();
        link = in.readString();
        pubDate = in.readString();
        audioUrl = in.readString();
        length = in.readString();
    }

    public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>(){

        @Override
        public FeedItem createFromParcel(Parcel parcel) {
            return new FeedItem(parcel);
        }

        @Override
        public FeedItem[] newArray(int i) {
            return new FeedItem[i];
        }
    };
}
