package com.example.ross.moviepickerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.ross.moviepickerapp.R;
import com.example.ross.moviepickerapp.sql.DatabaseHelper;
import com.example.ross.moviepickerapp.Validation.UserInputValidation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout InputUserNameLayout;
    private TextInputLayout InputPassLayout;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPass;

    private AppCompatButton LoginButton;
    private AppCompatTextView RegisterText;

    private UserInputValidation userInputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView =(NestedScrollView)findViewById(R.id.nestedScrollView);

        InputUserNameLayout =(TextInputLayout)findViewById(R.id.InputUserNameLayout);
        InputPassLayout =(TextInputLayout)findViewById(R.id.InputPassLayout);

        textInputEditTextEmail = (TextInputEditText)findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPass =(TextInputEditText)findViewById(R.id.textInputEditTextPass);

        LoginButton = (AppCompatButton)findViewById(R.id.LoginButton);
        RegisterText =(AppCompatTextView)findViewById(R.id.RegisterText);

    }

    /**
     * Method to initialize listeners
     */
    private void initListeners(){
        LoginButton.setOnClickListener(this);
        RegisterText.setOnClickListener(this);
    }

    /**
     * Method to initialize objects
     */

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        userInputValidation = new UserInputValidation(activity);
    }

    /**
     * Method to listen to the clicks on the login view
     * @param view
     */

    public void onClick(View view){
        switch(view.getId()){
            case R.id.LoginButton:
            verifyFromSQLite();
            break;
            case R.id.RegisterText:
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    /**
     * Method to validate inputs from user text Fields and to validate against information saved on database
     */
    private void verifyFromSQLite(){
        //if(!userInputValidation.isInputEditTextFilled(textInputEditTextEmail,InputUserNameLayout, getString(R.string.error_message_email))){
          //  return;
        //}
        if(!userInputValidation.isInputEmailValid( textInputEditTextEmail, InputUserNameLayout, getString(R.string.error_message_email))){
            return;
        }
        if(!userInputValidation.isInputEditTextFilled( textInputEditTextPass, InputPassLayout, getString(R.string.error_password))){
            return;
        }

        if(databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPass.getText().toString().trim())){

            Intent userLoggedInHomeIntent = new Intent(activity, LoggedInHomeActivity.class);
            userLoggedInHomeIntent.putExtra("Email", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(userLoggedInHomeIntent);

        }else{

            Snackbar.make(nestedScrollView, getString(R.string.error_password), Snackbar.LENGTH_SHORT).show();

        }
        }
    /**
     * This method is to empty all input edit texts after login is attempted
     */
    public void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPass.setText(null);
}
}