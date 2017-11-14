package com.example.jeisson.pruebaandroidmerlyn.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by jeisson on 10/11/17.
 */

public class App implements Parcelable{

    private String banner_img;
    private String header_img;
    private String display_name;
    private String icon_img;
    private String description;

    public App() {
        this.banner_img = "";
        this.header_img = "";
        this.display_name = "";
        this.icon_img = "";
        this.description = "";
    }

    protected App(Parcel in) {
        banner_img = in.readString();
        header_img = in.readString();
        display_name = in.readString();
        icon_img = in.readString();
        description = in.readString();
    }

    public static final Creator<App> CREATOR = new Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getHeader_img() {
        return header_img;
    }

    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public String getDisplay_name() {return display_name;}

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(String icon_img) {
        this.icon_img = icon_img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(banner_img);
        parcel.writeString(header_img);
        parcel.writeString(display_name);
        parcel.writeString(icon_img);
        parcel.writeString(description);
    }
}
