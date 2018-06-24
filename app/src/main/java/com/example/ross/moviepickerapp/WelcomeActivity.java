package com.example.ross.moviepickerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {
LinearLayout Linear;
Button Enterbtn;
Animation uptodown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.welcome_screen);
        Enterbtn = (Button)findViewById(R.id.enterbtn);
        Linear = (LinearLayout)findViewById(R.id.linear);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        Linear.setAnimation(uptodown);
        
        Enterbtn.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view){
                        Intent welcomeintent = new Intent(WelcomeActivity.this, HomeActivity.class);
                        startActivity(welcomeintent);
                    }

                });
    }
}
