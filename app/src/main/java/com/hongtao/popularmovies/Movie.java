package com.hongtao.popularmovies;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by hongtao on 05/10/2016.
 */

public class Movie implements Serializable{
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


}
