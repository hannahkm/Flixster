package com.example.flixster.adapters;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    public static final String API_KEY = "7af48072f3a3b6500d8058e6bb3e089f";
    public static final String GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key="+API_KEY;
    // give context for where the adapter is being constructed from
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override
    // inflate a layout from XML and return the view holder
    // get the view from the inflater and wrap it in a view holder
    public ViewHolder onCreateViewHolder (@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    // populate data into items through the view holder
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // get the movie at the given position
        Movie movie = movies.get(position);

        // bind the movie data to the view holder
        holder.bind(movie);
    }

    @Override
    // return total count of items in the list
    public int getItemCount() {
        return movies.size();
    }

    // start by making an inner view holder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvGenre;
        ImageView ivPoster;
        RatingBar tvRating;

        public ViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvRating = itemView.findViewById(R.id.tvRating);

            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvRating.setRating((float) movie.getRating()/2);

            ArrayList<String> genres = movie.getGenres();
            AsyncHttpClient client = new AsyncHttpClient();
            // get request using the url we have
            client.get(GENRE_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Headers headers, JSON json) {
                    // here, the data we want is saved in our json var
                    Log.i("Movie.java", String.valueOf(json.jsonObject));
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        String genreList = "";
                        JSONArray results = jsonObject.getJSONArray("genres");
                        for (int j = 0; j<genres.size(); j++){
                            String currentGenre = genres.get(j);
                            for (int k = 0; k<results.length(); k++){
                                JSONObject IDValue = results.getJSONObject(k);
                                Log.i("id", String.valueOf(IDValue.get("id")));
                                if (currentGenre == String.valueOf(IDValue.get("id"))){
                                    Log.i("match", String.valueOf(IDValue.get("name")));
                                    genreList += IDValue.get("name") + ", ";
                                }
                            }
                        }
                        tvGenre.setText(genreList.substring(0, genreList.length()-2));

                    } catch (JSONException e) {
                        Log.w("Genre failed", e);
                    }
                }

                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                    Log.d("Movie.java", "onFailure");
                }
            });

            // we use the Glide library to make images
            // check orientation of the phone currently to identify which image and which placeholder to use
            String imageUrl;
            int placeholder;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.backdrop_placeholder;

            } else {
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.placeholder;
            }
            Glide.with(context).load(imageUrl).fitCenter().transform(new RoundedCornersTransformation(20, 0)).placeholder(placeholder).into(ivPoster);
        }

        @Override
        // let the user click on a view and view details about the movie
        public void onClick(View v) {
            // gets position of the selected item
            int position = getAdapterPosition();
            // make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position in the recycler view
                Movie movie = movies.get(position);
                // make intent for the new activity
                Intent intent = new Intent(context, DetailActivity.class);
                // serialize the movie using parceler
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
