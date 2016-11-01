package com.hongtao.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.hongtao.popularmovies.R.id.imageView;

/**
 * Created by hongtao on 05/10/2016.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static String LOG_TAG = MovieAdapter.class.getName();

    public MovieAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;


        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_movies, parent, false);
        }

        Movie currentMovie =getItem(position);

        String imagePath = currentMovie.getmPosterPath();
        String baseUrl = "http://image.tmdb.org/t/p/";
        String imagteUrl= baseUrl+"w185"+imagePath;

//        Log.v(LOG_TAG,imagteUrl);

        ImageView moviePosterImageView = (ImageView) listItemView.findViewById(imageView);
        Picasso.with(getContext()).load(imagteUrl).into(moviePosterImageView);



        return listItemView;
    }
}
