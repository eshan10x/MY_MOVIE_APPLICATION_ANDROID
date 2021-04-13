package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class UpdateViewActivity extends AppCompatActivity {

    EditText title,year,director,actresses,ratings,description;
    Button updateBtn;
    DBHelper dbHelper;
    RatingBar rateBar;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_view);

        setTitle("My Movies");

        title = findViewById(R.id.updateNameTxtInput);
        year = findViewById(R.id.updateYearTxtInput);
        director = findViewById(R.id.updateDirectorTxtInput);
        actresses = findViewById(R.id.updateActressTxtInput);
        description = findViewById(R.id.updateDescriptionTxtInput);
        updateBtn = findViewById(R.id.updateRegisterBtn);
        rateBar = findViewById(R.id.RatingBar);
        dbHelper = new DBHelper(this);

        Bundle data = getIntent().getExtras();
        String mKey = data.get("key").toString();
        String mData = data.get("movieData").toString();
        String mTitle = (String) data.get("title");
        String mYear = data.get("year").toString();
        String mDirector = (String) data.get("director");
        String mAct = (String) data.get("actresses");
        String mRatings = data.get("ratings").toString();
        String mDescription = (String) data.get("description");
        String  mIsFav = data.get("isFavourite").toString();

        if (mData!=null) {
            title.setText(mTitle);
            year.setText(mYear);
            director.setText(mDirector);
            actresses.setText(mAct);
            description.setText(mDescription);
            rateBar.setRating(Float.parseFloat(mRatings));
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateViewActivity.this, "Name Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (year.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateViewActivity.this, "Weight Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (director.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateViewActivity.this, "Price Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (actresses.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateViewActivity.this, "Description Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateViewActivity.this, "Description Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                int yearm = Integer.parseInt(year.getText().toString());

                if (yearm <= 1895) {
                    Toast.makeText(UpdateViewActivity.this, "Can't add before 1895 movies"  , Toast.LENGTH_SHORT).show();
                }else {
                    movieData = new MovieData();
                    movieData.setMovieKey(Integer.parseInt(mKey));
                    movieData.setMovieTitle(title.getText().toString());
                    movieData.setYear(Integer.parseInt(year.getText().toString()));
                    movieData.setDirector(director.getText().toString());
                    movieData.setActresses(actresses.getText().toString());
                    movieData.setDescription(description.getText().toString());
                    movieData.setRatings((int) rateBar.getRating());
                    movieData.setIsFavourite(Integer.parseInt(mIsFav));
                    dbHelper.updateProduct(movieData);
                    Toast.makeText(UpdateViewActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}