package com.example.ross.moviepickerapp;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String posterPath;
    private double rating;
    private ArrayList<String> genres;
    private String overview;
    private int runtime;
    private int budget;
    private String releaseDate;
    private int revenue;
    private int voteCount;

    public Movie() {

    }

    public Movie(int id, String title, String posterPath, double rating, ArrayList<String> genres, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.rating = rating;
        this.genres = genres;
        this.overview = overview;
    }

    public Movie(int id, String title, String posterPath, double rating, ArrayList<String> genres, String overview, int runtime) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.rating = rating;
        this.genres = genres;
        this.overview = overview;
        this.runtime = runtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    // Parcelling part

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeDouble(rating);
        dest.writeList(genres);
        dest.writeString(overview);
        dest.writeInt(runtime);
        dest.writeInt(budget);
        dest.writeString(releaseDate);
        dest.writeInt(revenue);
        dest.writeInt(voteCount);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Parcel in){
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        rating = in.readDouble();
        genres = in.readArrayList(String.class.getClassLoader());
        overview = in.readString();
        runtime = in.readInt();
        budget = in.readInt();
        releaseDate = in.readString();
        revenue = in.readInt();
        voteCount = in.readInt();
    }

}

