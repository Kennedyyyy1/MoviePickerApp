package com.example.ross.moviepickerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ross on 14/03/2018.
 */

public class RateActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Toolbar menu_Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(menu_Toolbar);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitButton = (Button)findViewById(R.id.Submit_button);
        final TextView ratingTextViewDisplay = (TextView)findViewById(R.id.rating_display_text);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ratingTextViewDisplay.setText("Your rating is : "+ratingBar.getRating());
            }
        });
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
            Intent option_home_clicked = new Intent(RateActivity.this, HomeActivity.class);
            startActivity(option_home_clicked);
        }else if(item.getItemId()==R.id.menu_about){
            //Intent option_about_clicked = new Intent(RateActivity.this, AboutActivity.class);
            //startActivity(option_about_clicked);
        }else if (item.getItemId()==R.id.menu_account){
            Intent option_account_clicked = new Intent(RateActivity.this, AccountActivity.class);
            startActivity(option_account_clicked);
        }else if (item.getItemId()==R.id.menu_rate){
            Intent option_rate_clicked =  new Intent (RateActivity.this, RateActivity.class);
            startActivity(option_rate_clicked);
        }

        return super.onOptionsItemSelected(item);
    }

}
