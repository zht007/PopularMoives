package com.hongtao.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.hongtao.popularmovies.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by hongtao on 07/11/2016.
 */

public class FetchMovieTaskFromInternet extends AsyncTask<String,Void,Void>{

    private static String LOG_TAG = FetchMovieTaskFromInternet.class.getSimpleName();

    private final Context mContext;

    public FetchMovieTaskFromInternet(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected Void doInBackground(String... urls) {

        if (urls.length == 0) {
            return null;
        }
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of movie objects with the corresponding data.
            JSONObject jsonObject = new JSONObject(makeHttpRequest(createUrl(urls[0])));
            JSONArray results = jsonObject.getJSONArray("results");
            for(int i = 0 ; i< results.length(); i++){
                JSONObject movie = results.getJSONObject(i);
                int movieId = movie.getInt("id");
                String title = movie.getString("original_title");
                String poster_path = movie.getString("poster_path");
                String synopis = movie.getString("overview");
                double userRating = movie.getDouble("vote_average");
                String releaseDate = movie.getString("release_date");
//                double popularity = movie.getDouble("popularity");

                addMovie(movieId,title,poster_path,synopis,userRating,releaseDate);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Helper method to handle insertion of a new movie in the movie database
     *
     * @param movieId
     * @param title
     * @param poster_path
     * @param synopis
     * @param userRating
     * @param releaseDate
     */
    private void addMovie(int movieId, String title, String poster_path, String synopis, double userRating, String releaseDate) {


        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry.COLUMN_MOVIE_ID},
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(movieId)},
                null
        );
        if(cursor.moveToFirst()){
           return;
        }
        else {
            ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,movieId);
            values.put(MovieContract.MovieEntry.COLUMN_TITLE,title);
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,poster_path);
            values.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS,synopis);
            values.put(MovieContract.MovieEntry.COLUMN_USER_RATING,userRating);
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,releaseDate);

            mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,values);
            return;
        }
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
