package com.hongtao.popularmovies;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.hongtao.popularmovies.QueryUtils.extractMovies;
import static com.hongtao.popularmovies.R.id.imageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * create an instance of this fragment.
 */
public class  MainFragment extends Fragment {

    private static String LOG_TAG = MainFragment.class.getName();
//    private ArrayList<Movie> mMovies = new ArrayList<>();
    private String mImagteUrl="";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootView =inflater.inflate(R.layout.fragment_main, container, false);

        FetchMovieTask task = new FetchMovieTask();
        task.execute();

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        Log.v(LOG_TAG+"Oncreate",mImagteUrl);
//        String imagePath = mMovies.get(0).getmPosterPath();
//        String baseUrl = "http://image.tmdb.org/t/p/";
//        String imagteUrl= baseUrl+"w185"+imagePath;
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185///z6BP8yLwck8mN9dtdYKkZ4XGa3D.jpg").into(imageView);


        return rootView;


    }

    public class FetchMovieTask extends AsyncTask<Void,Void,ArrayList<Movie>>{
        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            ArrayList<Movie> movies = QueryUtils.extractMovies();
//            for(int i=0; i< movies.size(); i++){
//                Log.v(LOG_TAG,movies.get(i).getTitle());
//            }
            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
                    String imagePath = movies.get(0).getmPosterPath();
        String baseUrl = "http://image.tmdb.org/t/p/";
        String imagteUrl= baseUrl+"w185"+imagePath;
            mImagteUrl += imagteUrl;
          Log.v(LOG_TAG,mImagteUrl);
            super.onPostExecute(movies);
        }
    }

}
