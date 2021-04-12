package com.example.movieapplication;

public class MovieData {

    private int movieKey;
    private String movieTitle;
    private int year;
    private String director;
    private String actresses;
    private int ratings;
    private String description;
    private int isFavourite;

    public MovieData() {

    }

    public MovieData(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public MovieData(int movieKey, String movieTitle, int year, String director, String actresses, int ratings, String description, int isFavourite) {
        this.movieKey = movieKey;
        this.movieTitle = movieTitle;
        this.year = year;
        this.director = director;
        this.actresses = actresses;
        this.ratings = ratings;
        this.description = description;
        this.isFavourite = isFavourite;
    }

    public int getMovieKey() {
        return movieKey;
    }

    public void setMovieKey(int movieKey) {
        this.movieKey = movieKey;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getActresses() {
        return actresses;
    }

    public int getRatings() {
        return ratings;
    }

    public String getDescription() {
        return description;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActresses(String actresses) {
        this.actresses = actresses;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "movieKey=" + movieKey +
                ", movieTitle='" + movieTitle + '\'' +
                ", year=" + year +
                ", director='" + director + '\'' +
                ", actresses='" + actresses + '\'' +
                ", ratings=" + ratings +
                ", description='" + description + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
