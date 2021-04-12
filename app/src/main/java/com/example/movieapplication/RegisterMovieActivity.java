package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMovieActivity extends AppCompatActivity {

    EditText movieTitle, movieYear, movieDirector, movieActresses, movieRatings, movieDescription;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        Button submitBtn = findViewById(R.id.registerBtn);
        movieTitle = findViewById(R.id.nameTxtInput);
        movieYear = findViewById(R.id.yearTxtInput);
        movieDirector = findViewById(R.id.directorTxtInput);
        movieActresses = findViewById(R.id.actressTxtInput);
        movieRatings = findViewById(R.id.ratingsTxtInput);
        movieDescription = findViewById(R.id.descriptionTxtInput);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    MovieData movieData = new MovieData();
                    movieData.setMovieTitle(movieTitle.getText().toString());
                    movieData.setYear(Integer.parseInt(movieYear.getText().toString()));
                    movieData.setDirector(movieDirector.getText().toString());
                    movieData.setActresses(movieActresses.getText().toString());
                    movieData.setRatings(Integer.parseInt(movieRatings.getText().toString()));
                    movieData.setDescription(movieDescription.getText().toString());
                    movieData.setIsFavourite(0);
//                    Toast.makeText(RegisterMovieActivity.this, movieData.toString() , Toast.LENGTH_SHORT).show();
                    dbHelper = new DBHelper(RegisterMovieActivity.this);
                    dbHelper.addMovie(movieData);
                    Toast.makeText(RegisterMovieActivity.this, "Success"  , Toast.LENGTH_SHORT).show();
                    movieTitle.setText("");
                    movieYear.setText("");
                    movieDirector.setText("");
                    movieActresses.setText("");
                    movieRatings.setText("");
                    movieDescription.setText("");

                }catch (Exception e) {
                    Toast.makeText(RegisterMovieActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}