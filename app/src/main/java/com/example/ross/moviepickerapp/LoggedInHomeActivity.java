package com.example.ross.moviepickerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;


public class LoggedInHomeActivity extends AppCompatActivity {

    GridLayout MainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_home);

        Toolbar menu_Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(menu_Toolbar);

        MainGrid = findViewById(R.id.mainGrid2);
        //Create method single event for Clicked items

        if(MainGrid == null){
            Log.e("LOGGED IN", "null");
        }else{
            Log.e("LOGGED IN", "not null");
        }
        setSingleEvent(MainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Create For loop to loop through all child items in grid
        for(int i=0; i<mainGrid.getChildCount();i++){
            //Casting to all cardView items
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //Getting new Activity when cardView is clicked
                    if(finalI==0){
                        MovieDatabaseController.randomMovie(LoggedInHomeActivity.this);
                    }else if (finalI == 1){
                        Intent intent = new Intent(LoggedInHomeActivity.this, MovieFilter.class);
                        startActivity(intent);
                    }else if(finalI == 2){
                        //Intent spin = new Intent (HomeActivity.this, SpinActivity.class);
                        //startActivity(spin);
                    }else if(finalI == 3){
                        Intent search = new Intent (LoggedInHomeActivity.this, Search.class);
                        startActivity(search);
                    }else if (finalI == 4){
                        //Intent account = new Intent(LoggedInHomeActivity.this, AccountActivity.class);
                       // startActivity(account);
                    }else if (finalI == 5) {
                        //Intent settings = new Intent(LoggedInHomeActivity.this,SettingsActivity.class);
                        //startActivity(settings);
                    }
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menu_inflater = getMenuInflater();
        menu_inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_home){
            Intent option_home_clicked = new Intent(LoggedInHomeActivity.this, HomeActivity.class);
            startActivity(option_home_clicked);
        }else if(item.getItemId()==R.id.menu_about){
           // Intent option_about_clicked = new Intent(LoggedInHomeActivity.this, AboutActivity.class);
           // startActivity(option_about_clicked);
        }else if (item.getItemId()==R.id.menu_account){
            Intent option_account_clicked = new Intent(LoggedInHomeActivity.this, AccountActivity.class);
            startActivity(option_account_clicked);
        }else if (item.getItemId()==R.id.menu_rate){
            Intent option_rate_clicked =  new Intent (LoggedInHomeActivity.this, RateActivity.class);
            startActivity(option_rate_clicked);
        }

        return super.onOptionsItemSelected(item);
    }

    }

