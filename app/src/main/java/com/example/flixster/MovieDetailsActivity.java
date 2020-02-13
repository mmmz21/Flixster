package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    // the movie to display
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Glide.with(getApplicationContext()).load(movie.getBackdropPath()).into(ivBackdrop);

        // vote average is 0->10, convert to 0-> 5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        ivBackdrop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO: check if video has trailer
        if (movie.gethasTrailer()) {
            // create intent for the new activity
            Intent intent = new Intent(this, MovieTrailerActivity.class);
            // serialize the movie using parceler, use its short name as a key
            intent.putExtra("videoId", movie.getId());
            // show the activity
            this.startActivity(intent);
        } else {
            Toast.makeText(this, "No trailer", Toast.LENGTH_SHORT).show();
        }
    }
}
