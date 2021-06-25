package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    TextView dvTitle;
    TextView dvOverview;
    ImageView dvPoster;
    Movie movie;
    String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String API_KEY = getResources().getString(R.string.API_KEY);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);

        dvTitle = findViewById(R.id.dvTitle);
        dvOverview = findViewById(R.id.dvOverview);
        dvPoster = findViewById(R.id.dvPoster);

        // get the selected movie from the Intent parcel
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("DetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        String VIDEO_URL = "https://api.themoviedb.org/3/movie/"+movie.getID()+"/videos?api_key="+API_KEY;

        AsyncHttpClient client = new AsyncHttpClient();
        // get request using the url we have
        client.get(VIDEO_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d("DetailVideo", "onSuccess");
                // here, the data we want is saved in our json var
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject results = (JSONObject) jsonObject.getJSONArray("results").get(0);
                    Log.i("DetailVideo", "Results: " + results.toString());
                    videoID = String.valueOf(results.getString("key"));
                    // redraw the recycler view with the updated values!
                } catch (JSONException e) {
                    Log.e("DetailVideo", "JSON exception: " + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("DetailVideo", "onFailure");
            }
        });

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(movie.getTitle());
        dvTitle.setText(movie.getTitle());
        dvOverview.setText(movie.getOverview());
        Glide.with(this).load(movie.getBackdropPath()).fitCenter().transform(new RoundedCornersTransformation(20, 0)).placeholder(R.drawable.placeholder).into(dvPoster);
    }

    public void videoOpener(View v){
        Intent intent = new Intent(this, MovieTrailerActivity.class);
        if (videoID != null){
            intent.putExtra("Video ID", videoID);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "No available trailers", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return false;
    }
}