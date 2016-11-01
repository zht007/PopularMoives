package com.hongtao.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hongtao on 05/10/2016.
 */

public class Movie implements Parcelable{
    private String mTitle;
    private String mPosterPath;
    private String mSynopsis;
    private double mUserRating;
    private String mReleaseDate;
    private double mPopularity;

    public Movie(String title, String posterPath, String synopsis, double userRating, String releaseDate, double popularity) {
        this.mTitle = title;
        this.mPosterPath = posterPath;
        this.mSynopsis = synopsis;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mPopularity = popularity;
    }

    public Movie(Parcel parcel) {
        mTitle = parcel.readString();
        mPosterPath = parcel.readString();
        mSynopsis = parcel.readString();
        mReleaseDate = parcel.readString();
        mUserRating = parcel.readDouble();
        mPopularity = parcel.readDouble();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public double getmPopularity() {
        return mPopularity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mSynopsis);
        parcel.writeString(mReleaseDate);
        parcel.writeDouble(mUserRating);
        parcel.writeDouble(mPopularity);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
