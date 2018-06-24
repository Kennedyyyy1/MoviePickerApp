package com.example.ross.moviepickerapp;

 import android.app.Activity;
 import android.os.Bundle;
 import android.support.v7.widget.Toolbar;
 import android.util.Log;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Spinner;

 import java.util.ArrayList;

public class MovieFilter extends Activity implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> genres = new ArrayList<String>();

    Spinner genreSpinner;
    Spinner subGenreSpinner;
    Spinner languageSpinner;
    Spinner ratingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_filter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        genreSpinner = (Spinner) findViewById(R.id.Spinner_Genre);
        subGenreSpinner = (Spinner) findViewById(R.id.Spinner_SubGenre);
        languageSpinner = (Spinner) findViewById(R.id.Spinner_Language);
        ratingSpinner = (Spinner) findViewById(R.id.Spinner_Rating);

        genreSpinner.setOnItemSelectedListener(this);

        genres.add("Action");genres.add("Adventure");genres.add("Animation");
        genres.add("Comedy");genres.add("Crime");genres.add("Documentary");
        genres.add("Drama");genres.add("Family");genres.add("Fantasy");
        genres.add("History");genres.add("Horror");genres.add("Music");
        genres.add("Mystery");genres.add("Romance");genres.add("Science Fiction");
        genres.add("TV Movie");genres.add("Thriller");genres.add("War");
        genres.add("Western");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres);
        genreSpinner.setAdapter(adapter);

        ArrayList<String> languagesList = new ArrayList<String>();
        languagesList.add("English");
        languagesList.add("French");
        languagesList.add("German");
        languagesList.add("Italian");
        languagesList.add("Spanish");
        ArrayAdapter<String> adapter_lang = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languagesList);
        languageSpinner.setAdapter(adapter_lang);

        ArrayList<String> ratingList = new ArrayList<String>();
        ratingList.add("Any");
        ratingList.add("1");
        ratingList.add("2");
        ratingList.add("3");
        ratingList.add("4");
        ratingList.add("5");
        ratingList.add("6");
        ratingList.add("7");
        ratingList.add("8");
        ratingList.add("9");
        ArrayAdapter<String> adapter_rating = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ratingList);
        ratingSpinner.setAdapter(adapter_rating);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.e("onItemSelected", "Item was selected");
        ArrayList<String> subGenres = new ArrayList<String>(genres);
        subGenres.add(0,"None");
        String mainGenre=(String)parent.getItemAtPosition(pos);
        Log.e("GenreSelected", mainGenre);
        switch (mainGenre) {
            case "Action" : subGenres.remove("Action");
                break;
            case "Adventure" : subGenres.remove("Adventure");
                break;
            case "Animation" : subGenres.remove("Animation");
                break;
            case "Crime" : subGenres.remove("Crime");
                break;
            case "Documentary" : subGenres.remove("Documentary");
                break;
            case "Drama" : subGenres.remove("Drama");
                break;
            case "Family" : subGenres.remove("Family");
                break;
            case "Fantasy" : subGenres.remove("Fantasy");
                break;
            case "History" : subGenres.remove("History");
                break;
            case "Horror" : subGenres.remove("Horror");
                break;
            case "Music" : subGenres.remove("Music");
                break;
            case "Mystery" : subGenres.remove("Mystery");
                break;
            case "Romance" : subGenres.remove("Romance");
                break;
            case "Science Fiction" : subGenres.remove("Science Fiction");
                break;
            case "TV Movie" : subGenres.remove("TV Movie");
                break;
            case "Thriller" : subGenres.remove("Thriller");
                break;
            case "War" : subGenres.remove("War");
                break;
            case "Western" : subGenres.remove("Western");
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subGenres);
        subGenreSpinner.setAdapter(adapter);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private int getGenreId (String genre) {
        int id=0;

        switch (genre) {
            case"Action":
                id=28;
                break;
            case"Adventure":
                id=12;
                break;
            case"Animation":
                id=16;
                break;
            case"Comedy":
                id=35;
                break;
            case"Crime":
                id=80;
                break;
            case"Documentary":
                id=99;
                break;
            case"Drama":
                id=18;
                break;
            case"Family":
                id=10751;
                break;
            case"Fantasy":
                id=14;
                break;
            case"History":
                id=36;
                break;
            case"Horror":
                id=27;
                break;
            case"Music":
                id=10402;
                break;
            case"Mystery":
                id=9648;
                break;
            case"Romance":
                id=10749;
                break;
            case"Science Fiction":
                id=878;
                break;
            case"TV Movie":
                id=10770;
                break;
            case"Thriller":
                id=53;
                break;
            case"War":
                id=10752;
                break;
            case"Western":
                id=37;
                break;
        }
        return id;
    }

    public void onFilterSearchBtnClicked(View v) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder query = new StringBuilder();

        stringBuilder.append("Main Genre = "+genreSpinner.getSelectedItem().toString()+", ");
        query.append("&with_genres="+getGenreId(genreSpinner.getSelectedItem().toString()));

        stringBuilder.append("Sub-Genre = "+subGenreSpinner.getSelectedItem().toString()+", ");
        if (!subGenreSpinner.getSelectedItem().toString().equals("None")) {
            query.append(","+getGenreId(subGenreSpinner.getSelectedItem().toString()));
        }

        String value; String temp="";

        value = languageSpinner.getSelectedItem().toString();
        switch (value) {
            case "English":
                temp="en";
                break;
            case "French":
                temp="fr";
                break;
            case "German":
                temp="de";
                break;
            case "Italian":
                temp="it";
                break;
            case "Spanish":
                temp="es";
                break;
        }
        stringBuilder.append("Language = "+value+", ");
        query.append("&language="+temp);

        int rating=0;
        value = ratingSpinner.getSelectedItem().toString();
        switch (value) {
            case "Any":
                rating = 0;
                break;
            case "1":
                rating = 1;
                break;
            case "2":
                rating = 2;
                break;
            case "3":
                rating = 3;
                break;
            case "4":
                rating = 4;
                break;
            case "5":
                rating = 5;
                break;
            case "6":
                rating = 6;
                break;
            case "7":
                rating = 7;
                break;
            case "8":
                rating = 8;
                break;
            case "9":
                rating = 9;
                break;
        }
        stringBuilder.append("Rating < "+rating);
        query.append("&vote_average.gte="+rating);

        String finalString = stringBuilder.toString();
        String finalQuery = query.toString();
        Log.e("Filter Selected", finalString);
        Log.e("Filter Selected", finalQuery);

        MovieDatabaseController.discoverMovies(this, finalQuery);
    }
}
