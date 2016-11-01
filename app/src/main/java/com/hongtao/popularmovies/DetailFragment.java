package com.hongtao.popularmovies;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.os.Build.VERSION_CODES.M;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Movie movie = ((DetailActivity)getActivity()).getMovie();


        TextView titleTextView = (TextView) rootView.findViewById(R.id.title_textview);
        TextView symopsisTextView = (TextView) rootView.findViewById(R.id.synopsis_textview);
        TextView ratingTextView = (TextView) rootView.findViewById(R.id.rating_textview);
        TextView releaseDateTextView = (TextView) rootView.findViewById(R.id.realese_date_textview);
        ImageView posterImageView = (ImageView) rootView.findViewById(R.id.poster_imageview);

        titleTextView.setText(movie.getTitle());
        symopsisTextView.setText("Synopsis: "+movie.getSynopsis());
        ratingTextView.setText("Rating: "+ String.valueOf(movie.getUserRating()));
        releaseDateTextView.setText("Release Date: "+movie.getReleaseDate());

        String imagePath = movie.getmPosterPath();
        String baseUrl = "http://image.tmdb.org/t/p/";
        String imagteUrl= baseUrl+"w500"+imagePath;
        Picasso.with(getActivity()).load(imagteUrl).into(posterImageView);

        return rootView;

    }

}
