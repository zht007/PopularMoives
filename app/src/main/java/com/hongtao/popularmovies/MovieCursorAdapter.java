package com.hongtao.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.hongtao.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by hongtao on 07/11/2016.
 */

public class MovieCursorAdapter extends CursorAdapter {
    public MovieCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_movies,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        String imagePath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        String baseUrl = "http://image.tmdb.org/t/p/";
        String imagteUrl= baseUrl+"w185"+imagePath;

        ImageView moviePosterImageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(context).load(imagteUrl).into(moviePosterImageView);

    }
}
