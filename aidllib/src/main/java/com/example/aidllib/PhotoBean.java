package com.example.aidllib;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoBean implements Parcelable {
    private String path;

    public PhotoBean(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
    }

    public void readFromParcel(Parcel dest) {
        this.path = dest.readString();
    }

    public PhotoBean() {
    }

    protected PhotoBean(Parcel in) {
        this.path = in.readString();
    }

    public static final Parcelable.Creator<PhotoBean> CREATOR = new Parcelable.Creator<PhotoBean>() {
        @Override
        public PhotoBean createFromParcel(Parcel source) {
            return new PhotoBean(source);
        }

        @Override
        public PhotoBean[] newArray(int size) {
            return new PhotoBean[size];
        }
    };
}
