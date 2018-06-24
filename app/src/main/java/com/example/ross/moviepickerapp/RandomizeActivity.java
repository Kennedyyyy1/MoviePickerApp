package com.example.ross.moviepickerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class RandomizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);

        Toolbar menu_Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(menu_Toolbar);
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
            Intent option_home_clicked = new Intent(RandomizeActivity.this, HomeActivity.class);
            startActivity(option_home_clicked);
        }else if(item.getItemId()==R.id.menu_about){
            //Intent option_about_clicked = new Intent(RandomizeActivity.this, AboutActivity.class);
            //startActivity(option_about_clicked);
        }else if (item.getItemId()==R.id.menu_account){
            Intent option_account_clicked = new Intent(RandomizeActivity.this, AccountActivity.class);
            startActivity(option_account_clicked);
        }else if (item.getItemId()==R.id.menu_rate){
            Intent option_rate_clicked =  new Intent (RandomizeActivity.this, RateActivity.class);
            startActivity(option_rate_clicked);
        }

        return super.onOptionsItemSelected(item);
    }

}
