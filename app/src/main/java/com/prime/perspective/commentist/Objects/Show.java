package com.prime.perspective.commentist.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rsteller on 1/13/2017.
 */

public class Show implements Parcelable{
    String name;
    String description;
    int image;
    String feed;

    public Show(String name, String description, int image, String feed){
        this.name = name;
        this.description = description;
        this.image = image;
        this.feed = feed;
    }
    private Show(Parcel in){
        name = in.readString();
        description = in.readString();
        image = in.readInt();
        feed = in.readString();
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel out, int flags){
        out.writeString(name);
        out.writeString(description);
        out.writeInt(image);
        out.writeString(feed);
    }

    public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>(){

        @Override
        public Show createFromParcel(Parcel parcel) {
            return new Show(parcel);
        }

        @Override
        public Show[] newArray(int i) {
            return new Show[i];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

}
