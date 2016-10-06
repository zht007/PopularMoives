package com.hongtao.popularmovies;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.media.CamcorderProfile.get;

/**
 * Created by hongtao on 05/10/2016.
 */

public final class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getName();

    private static String TEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=1b3a0ed2a58e5a9b9d72714537be6979";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     * @param output
     */




    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Movie> extractMovies(Context context,String orderedBy,String url) {

//        Log.v(LOG_TAG,url);

        //Delay for 2 seconds
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // If the JSON string is empty or null, then return early.
//        if (TextUtils.isEmpty(jasonURL)) {
//            return null;
//        }

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<Movie> movies = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of movie objects with the corresponding data.
            JSONObject jsonObject = new JSONObject(makeHttpRequest(createUrl(url)));
            JSONArray results = jsonObject.getJSONArray("results");
            for(int i = 0 ; i< results.length(); i++){
                JSONObject movie = results.getJSONObject(i);
                String title = movie.getString("original_title");
                String poster_path = movie.getString("poster_path");
                String synopis = movie.getString("overview");
                double userRating = movie.getDouble("vote_average");
                String releaseDate = movie.getString("release_date");
                double popularity = movie.getDouble("popularity");
                movies.add(new Movie(title, poster_path, synopis,userRating,releaseDate,popularity));
            }
            //sort movies by ratings
            if(orderedBy.equals(context.getString(R.string.pref_ordered_by_ratings))){
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie one, Movie another) {
                        int result;
                        if(another.getUserRating() > one.getUserRating()){
                            result = 1;
                        }
                        else result = -1;

                        return result;
                    }
                });

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the list of movies

//        Log.v(LOG_TAG,"fetchPopularMoviesData()");
        return movies;
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
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
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
