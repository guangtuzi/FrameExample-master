package com.otcyan.frameexample_master.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 福利对象实例.
 */
public class Welfare implements Parcelable{

    public String desc;
    public String ganhuo_id;
    public String publishedAt;
    public String readability;
    public String type;
    public String url;
    public String who;

    protected Welfare(Parcel in) {
        desc = in.readString();
        ganhuo_id = in.readString();
        publishedAt = in.readString();
        readability = in.readString();
        type = in.readString();
        url = in.readString();
        who = in.readString();
    }

    public static final Creator<Welfare> CREATOR = new Creator<Welfare>() {
        @Override
        public Welfare createFromParcel(Parcel in) {
            return new Welfare(in);
        }

        @Override
        public Welfare[] newArray(int size) {
            return new Welfare[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(desc);
        dest.writeString(ganhuo_id);
        dest.writeString(publishedAt);
        dest.writeString(readability);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(who);
    }
}
