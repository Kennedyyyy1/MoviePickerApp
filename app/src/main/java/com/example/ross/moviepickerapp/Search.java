package com.example.ross.moviepickerapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final EditText editText = (EditText) findViewById(R.id.ET_MovieTitle);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onSearchBtnClick(editText);
                return true;
            }
        });
    }

    public void onSearchBtnClick(View v) {
        EditText movieTitle = (EditText) findViewById(R.id.ET_MovieTitle);
        String finalQuery = movieTitle.getText().toString();
        if (finalQuery.length()>0) {
            finalQuery = finalQuery.replaceAll(" ","+");
            Log.d("onSearch query",finalQuery);
            MovieDatabaseController.searchMovies(this, finalQuery);
        } else {
            Toast.makeText(this, "You must enter search query", Toast.LENGTH_SHORT).show();
        }
    }
}

