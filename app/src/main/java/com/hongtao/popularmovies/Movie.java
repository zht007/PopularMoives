package com.hongtao.popularmovies;

import android.media.Image;

/**
 * Created by hongtao on 05/10/2016.
 */

public class Movie {
    private String mTitle;
    private String mPosterPath;
    private String mSynopsis;
    private double mUserRating;
    private String mReleaseDate;

    public Movie(String title, String posterPath, String synopsis, double userRating, String releaseDate) {
        this.mTitle = title;
        this.mPosterPath = posterPath;
        this.mSynopsis = synopsis;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
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
}
