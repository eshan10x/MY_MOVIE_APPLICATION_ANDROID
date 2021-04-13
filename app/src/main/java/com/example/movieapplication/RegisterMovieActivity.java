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

        setTitle("My Movies");

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
                if (movieTitle.getText().toString().isEmpty()){
                    Toast.makeText(RegisterMovieActivity.this,"Name Cannot be Empty" ,Toast.LENGTH_SHORT).show();
                    return;
                }if (movieYear.getText().toString().isEmpty()){
                    Toast.makeText(RegisterMovieActivity.this,"Year Cannot be Empty" ,Toast.LENGTH_SHORT).show();
                    return;
                }if (movieDirector.getText().toString().isEmpty()){
                    Toast.makeText(RegisterMovieActivity.this,"Director Cannot be Empty" ,Toast.LENGTH_SHORT).show();
                    return;
                }if (movieActresses.getText().toString().isEmpty()){
                    Toast.makeText(RegisterMovieActivity.this,"Actresses Cannot be Empty" ,Toast.LENGTH_SHORT).show();
                    return;
                }if (movieRatings.getText().toString().isEmpty()){
                    Toast.makeText(RegisterMovieActivity.this,"Ratings Cannot be Empty" ,Toast.LENGTH_SHORT).show();
                    return;
                }if (movieDescription.getText().toString().isEmpty()){
                    Toast.makeText(RegisterMovieActivity.this,"Description Cannot be Empty" ,Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(movieYear.getText().toString());
                int ratings = Integer.parseInt(movieRatings.getText().toString());
                if (year <= 1895) {
                    Toast.makeText(RegisterMovieActivity.this, "Can't add before 1895 movies"  , Toast.LENGTH_SHORT).show();
                }if (ratings >= 10) {
                    Toast.makeText(RegisterMovieActivity.this, "Ratings must be between 10"  , Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        MovieData movieData = new MovieData();
                        movieData.setMovieTitle(movieTitle.getText().toString());
                        movieData.setYear(Integer.parseInt(movieYear.getText().toString()));
                        movieData.setDirector(movieDirector.getText().toString());
                        movieData.setActresses(movieActresses.getText().toString());
                        movieData.setRatings(Integer.parseInt(movieRatings.getText().toString()));
                        movieData.setDescription(movieDescription.getText().toString());
                        movieData.setIsFavourite(0);
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


            }
        });

    }
}