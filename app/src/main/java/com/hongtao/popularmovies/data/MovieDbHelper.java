package com.hongtao.popularmovies.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hongtao on 01/11/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE" + MovieContract.MovieEnry.TABLE_NAME+ " (" +
                MovieContract.MovieEnry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEnry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEnry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEnry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
                MovieContract.MovieEnry.COLUMN_USER_RATING + " REAL NOT NULL, " +
                MovieContract.MovieEnry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEnry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
