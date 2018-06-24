package com.example.ross.moviepickerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ross.moviepickerapp.Model.User;
import com.example.ross.moviepickerapp.sql.DatabaseHelper;

import java.util.List;

/**
 * Created by Ross on 14/03/2018.
 */

public class AccountActivity extends AppCompatActivity{
    private final AppCompatActivity activity = AccountActivity.this;

    private DatabaseHelper dbhelper;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar menu_Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(menu_Toolbar);

        initObjects();

        /*User ui = dbhelper.displayAccountInfo("rossadam@gmail.com ", "adam");
        if(ui == null){
            Log.e("Null", " user is null");
        }else{
            Log.e ("Not null", " user is not null");
        }
        Log.e("Get user test", ui.getFirstname());*/

        List<User> userList = dbhelper.getAllUser();

        String s = "userList size = "+userList.size();

        Log.e("accountActivity create", s);
    }

    private void initObjects(){
        dbhelper = new DatabaseHelper(activity);
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
            Intent option_home_clicked = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(option_home_clicked);
        }else if(item.getItemId()==R.id.menu_about){
           // Intent option_about_clicked = new Intent(AccountActivity.this, AboutActivity.class);
            //startActivity(option_about_clicked);
        }else if (item.getItemId()==R.id.menu_account){
            Intent option_account_clicked = new Intent(AccountActivity.this, AccountActivity.class);
            startActivity(option_account_clicked);
        }else if (item.getItemId()==R.id.menu_rate){
            Intent option_rate_clicked =  new Intent (AccountActivity.this, RateActivity.class);
            startActivity(option_rate_clicked);
        }

        return super.onOptionsItemSelected(item);
    }


}
