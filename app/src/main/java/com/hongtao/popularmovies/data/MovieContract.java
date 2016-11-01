package com.hongtao.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hongtao on 01/11/2016.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.hongtao.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class MovieEnry implements BaseColumns{

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "tilte";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";

    }
}
