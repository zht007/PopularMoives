package com.hongtao.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static String LOG_TAG = DetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("CURRENT_MOVIE");
        Log.v(LOG_TAG,movie.getTitle());

        TextView titleTextView = (TextView) findViewById(R.id.title_textview);
        TextView symopsisTextView = (TextView) findViewById(R.id.synopsis_textview);
        TextView ratingTextView = (TextView) findViewById(R.id.rating_textview);
        TextView releaseDateTextView = (TextView) findViewById(R.id.realese_date_textview);
        ImageView posterImageView = (ImageView) findViewById(R.id.poster_imageview);

        titleTextView.setText(movie.getTitle());
        symopsisTextView.setText("Synopsis: "+movie.getSynopsis());
        ratingTextView.setText("Rating: "+ String.valueOf(movie.getUserRating()));
        releaseDateTextView.setText("Release Date: "+movie.getReleaseDate());

        String imagePath = movie.getmPosterPath();
        String baseUrl = "http://image.tmdb.org/t/p/";
        String imagteUrl= baseUrl+"w500"+imagePath;
        Picasso.with(this).load(imagteUrl).into(posterImageView);

    }
}
