package com.hongtao.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.*;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;



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

    private boolean mIsConnected;

    private TextView mEmptyStateView;

    private ProgressBar mProgressbar;

    private final String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";

    private String mBaseUrl = POPULAR_MOVIES_BASE_URL+"popular?";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies(mBaseUrl);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        //test internet connection.....

        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        mIsConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_ordered_by_popularity){
            mBaseUrl = POPULAR_MOVIES_BASE_URL+"popular?";
            updateMovies(mBaseUrl);

        }

        if(id == R.id.action_ordered_by_ratings){
            mBaseUrl = POPULAR_MOVIES_BASE_URL+"top_rated?";
            updateMovies(mBaseUrl);

        }

        return super.onOptionsItemSelected(item);
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

//                Log.v(LOG_TAG,"item "+i+" Clicked");
                Intent intent = new Intent(getActivity(),DetailActivity.class)
                        .putExtra("CURRENT_MOVIE",mMovieAdapter.getItem(i));
                startActivity(intent);


            }
        });

        mEmptyStateView = (TextView)rootView.findViewById(R.id.empty_view);
        movieGridView.setEmptyView(mEmptyStateView);

        updateMovies(mBaseUrl);
        mProgressbar = (ProgressBar)rootView.findViewById(R.id.loading_spinner);


        return rootView;


    }

    public class FetchMovieTask extends AsyncTask<String,Void,ArrayList<Movie>>{
        @Override
        protected ArrayList<Movie> doInBackground(String... param) {
            ArrayList<Movie> movies = QueryUtils.extractMovies(getContext(),param[0]);
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
            if(!mIsConnected){
                mEmptyStateView.setText(R.string.no_Internet);
            }
            mProgressbar.setVisibility(View.GONE);

        }
    }

    private void updateMovies(String baseUrl){

        FetchMovieTask task = new FetchMovieTask();

//        String baseUrl;
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String orderedBy = sharedPrefs.getString(getString(R.string.key_orded_by_list), getString(R.string.pref_ordered_by_popularity));
//        if(orderedBy.equals(getString(R.string.pref_ordered_by_ratings))){
//             baseUrl = POPULAR_MOVIES_BASE_URL+"top_rated?";
//        }
//        else {
//            baseUrl = POPULAR_MOVIES_BASE_URL+"popular?";
//        }
        Uri buitUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY).build();
        String url = buitUri.toString();
        task.execute(url);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("BaseUrl", mBaseUrl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mBaseUrl = savedInstanceState.getString("BaseUrl");
        }
    }
}
