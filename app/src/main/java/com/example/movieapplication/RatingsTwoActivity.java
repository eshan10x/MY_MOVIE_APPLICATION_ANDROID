package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RatingsTwoActivity extends AppCompatActivity {

    String key = "k_kzd4x28s";
    String searchMovie;
    String url;
    String urlForRating;

    public class DownloadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            String result = "";

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();


                inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();

                while (data != -1) {
                    result += (char) data;

                    data = inputStreamReader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView imgResult) {
            this.imageView = imgResult;

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            movieImg.setImageBitmap(bitmap);

        }
    }

    TextView title,ratingTxt;
    ImageView movieImg,imdbLogo;
    String mTitle;
    String id;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_two);

        setTitle("My Movies");

        title = findViewById(R.id.movieTitleRating);
        ratingTxt = findViewById(R.id.ratingMovieTxt);
        movieImg = findViewById(R.id.imageRatingMovie);
        imdbLogo = findViewById(R.id.imdbLogo);

        Bundle data = getIntent().getExtras();
        mTitle = (String) data.get("title");
        title.setText(mTitle);
        imdbLogo.setImageResource(R.drawable.imdb_logo);

        getMovieData();
        getImdbRating();
        LoadImage loadImage = new LoadImage(movieImg);
        loadImage.execute(imgUrl);
    }

        public void getMovieData() {

            searchMovie = mTitle.trim();
            url = "https://imdb-api.com/en/API/SearchTitle/" + key + "/" + searchMovie;

            DownloadJSON downloadJSON = new DownloadJSON();

            try {
                String result = downloadJSON.execute(url).get();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray movies = jsonObject.getJSONArray("results");

                id = movies.getJSONObject(0).getString("id");
                imgUrl = movies.getJSONObject(0).getString("image");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void getImdbRating() {

            DownloadJSON downloadJSON = new DownloadJSON();
            try {
                urlForRating = "https://imdb-api.com/en/API/Ratings/k_kzd4x28s/" + id;

                String res = downloadJSON.execute(urlForRating).get();
                JSONObject jsonObject2 = new JSONObject(res);
                String rating = jsonObject2.getString("imDb");

                ratingTxt.setText(rating);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
}