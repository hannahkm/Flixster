package com.example.flixster.models;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import okhttp3.Headers;

// POJO!!!!!! - plain old java object lol
@Parcel
public class Movie {

    // important variables we want
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    String release;
    Integer id;
    ArrayList<String> genreList;

    // constructor with no parameters
    public Movie(){

    }

    // constructor with inputs
    // but if any of the getString's inside fail, throw an exception
    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        release = jsonObject.getString("release_date");
        id = jsonObject.getInt("id");
        JSONArray genres = jsonObject.getJSONArray("genre_ids");
        genreList = new ArrayList<>();
        for(int i = 0; i<genres.length(); i++){
//            Log.i("i", String.valueOf(genres.getInt(i)));
            genreList.add(String.valueOf(genres.getInt(i)));
        }
    }

    // pulls movies from the JSON array we obtained and builds an array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    // wow generate these automatically! allows us to get these values from the Movie vars
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        // we use the configurations API response hint!
        // we get the base url and possible adaptions (i.e. width)
        // https://image.tmdb.org/t/p/[IMAGE_SIZE]
        // NOTE: we can also just pull from the API to get appropriate sizes and append, but we hard code here :)

        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return release;
    }

    public ArrayList getGenres() {
        return genreList;
    }

    public Integer getID(){
        return id;
    }


}
