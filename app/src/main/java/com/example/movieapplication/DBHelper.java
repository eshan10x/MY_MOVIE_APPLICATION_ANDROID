package com.example.movieapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //Constants
    public static final String TABLE_NAME = "movies" ;
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_MOVIE_KEY = "MOVIE_KEY";
    public static final String COLUMN_MOVIE_TITLE = "MOVIE_TITLE";
    public static final String COLUMN_MOVIE_YEAR = "MOVIE_YEAR";
    public static final String COLUMN_MOVIE_DIRECTOR = "MOVIE_DIRECTOR";
    public static final String COLUMN_MOVIE_ACTRESSES = "MOVIE_ACTRESSES";
    public static final String COLUMN_MOVIE_RATINGS = "MOVIE_RATINGS";
    public static final String COLUMN_MOVIE_DESCRIPTION = "MOVIE_DESCRIPTION";
    public static final String COLUMN_MOVIE_IS_FAVOURITE = "MOVIE_FAVOURITES";

    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create new Database
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_MOVIE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MOVIE_TITLE + " Text NOT NULL,"
                + COLUMN_MOVIE_YEAR + " INTEGER NOT NULL,"
                + COLUMN_MOVIE_DIRECTOR + " TEXT NOT NULL,"
                + COLUMN_MOVIE_ACTRESSES + " TEXT NOT NULL,"
                + COLUMN_MOVIE_RATINGS + " INTEGER NOT NULL,"
                + COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL,"
                + COLUMN_MOVIE_IS_FAVOURITE + " INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    //to add the data to DB
    public void addMovie(MovieData movieData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_MOVIE_KEY, movieData.getMovieKey());
        contentValues.put(COLUMN_MOVIE_TITLE, movieData.getMovieTitle());
        contentValues.put(COLUMN_MOVIE_YEAR, movieData.getYear());
        contentValues.put(COLUMN_MOVIE_DIRECTOR, movieData.getDirector());
        contentValues.put(COLUMN_MOVIE_ACTRESSES, movieData.getActresses());
        contentValues.put(COLUMN_MOVIE_RATINGS, movieData.getRatings());
        contentValues.put(COLUMN_MOVIE_DESCRIPTION, movieData.getDescription());
        contentValues.put(COLUMN_MOVIE_IS_FAVOURITE, movieData.getIsFavourite());

        db.insertOrThrow(TABLE_NAME, null, contentValues);
    }

    //get movie data from db
    public ArrayList<MovieData> getAllData() {

        ArrayList<MovieData> returnList = new ArrayList<>();

        //get data from DB
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                int movieKey = cursor.getInt(0);
                String movieTitle = cursor.getString(1);
                int movieYear = cursor.getInt(2);
                String movieDirector = cursor.getString(3);
                String movieActresses = cursor.getString(4);
                int movieRatings = cursor.getInt(5);
                String movieDescription = cursor.getString(6);
                int movieIsFavourite = cursor.getInt(7);

                MovieData movieData = new MovieData(movieKey,movieTitle,movieYear,movieDirector,movieActresses,movieRatings,movieDescription,movieIsFavourite);
                //add the new Movie to list
                returnList.add(movieData);

            }while (cursor.moveToNext());

        }else {
            //nothing add to the list
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public Cursor getContent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return data;
    }

    // code to update the single product
    public int updateProduct(MovieData movieData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_KEY, movieData.getMovieKey());
        values.put(COLUMN_MOVIE_TITLE, movieData.getMovieTitle());
        values.put(COLUMN_MOVIE_YEAR, movieData.getYear());
        values.put(COLUMN_MOVIE_DIRECTOR, movieData.getDirector());
        values.put(COLUMN_MOVIE_ACTRESSES, movieData.getActresses());
        values.put(COLUMN_MOVIE_RATINGS, movieData.getRatings());
        values.put(COLUMN_MOVIE_DESCRIPTION, movieData.getDescription());
        values.put(COLUMN_MOVIE_IS_FAVOURITE, movieData.getIsFavourite());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_MOVIE_KEY + " = ?", new String[] { String.valueOf(movieData.getMovieKey()) });
    }

    //Search movie
    public List<String> getSearchProducts(String searchString) {
        List<String> productList = new ArrayList<String>();
        // Select All Query

        String selectQuery = "SELECT "  +COLUMN_MOVIE_TITLE+ " FROM " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_TITLE + " like \"%" + searchString + "%\" UNION " + "SELECT "  +COLUMN_MOVIE_TITLE+ " FROM " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_DIRECTOR + " like \"%" + searchString + "%\" UNION " + "SELECT " +COLUMN_MOVIE_TITLE+ " FROM " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ACTRESSES + " like \"%" + searchString + "%\"";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String name;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding product to list
                name = cursor.getString(0);
                productList.add(name);
            } while (cursor.moveToNext());
        }

        // return product list
        return productList;
    }



}
