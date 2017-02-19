package com.example.studio111.commentist.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rsteller on 1/23/2017.
 */

public class Host implements Parcelable {
    String name;
    int image;

    public Host(String name, int image){
        this.name = name;
        this.image = image;
    }
    private Host(Parcel in){
        name = in.readString();
        image = in.readInt();
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel out, int flags){
        out.writeString(name);
        out.writeInt(image);
    }

    public static final Parcelable.Creator<Host> CREATOR = new Parcelable.Creator<Host>(){

        @Override
        public Host createFromParcel(Parcel parcel) {
            return new Host(parcel);
        }

        @Override
        public Host[] newArray(int i) {
            return new Host[i];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}