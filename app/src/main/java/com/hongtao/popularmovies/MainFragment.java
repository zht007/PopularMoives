package com.hongtao.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
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
   private MovieAdapter mMovieAdapter;

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
        GridView movieGridView = (GridView) rootView.findViewById(R.id.movies_gridview);

        mMovieAdapter = new MovieAdapter(getActivity(),new ArrayList<Movie>());

        movieGridView.setAdapter(mMovieAdapter);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                String title = mMovieAdapter.getItem(i).getTitle();
//                String posterPath = mMovieAdapter.getItem(i).getmPosterPath();
//                String synopsis= mMovieAdapter.getItem(i).getSynopsis();
//                Double userRating = mMovieAdapter.getItem(i).getUserRating();
//                String releaseDate = mMovieAdapter.getItem(i).getReleaseDate();
                Log.v(LOG_TAG,"item "+i+" Clicked");
                Intent intent = new Intent(getActivity(),DetailActivity.class)
                        .putExtra("CURRENT_MOVIE",mMovieAdapter.getItem(i));
                startActivity(intent);


            }
        });

        FetchMovieTask task = new FetchMovieTask();
        task.execute();


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

            mMovieAdapter.clear();
            if(movies != null && !movies.isEmpty()){
                mMovieAdapter.addAll(movies);
            }

        }
    }

}
