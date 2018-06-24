package com.example.ross.moviepickerapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;

import com.example.ross.moviepickerapp.R;
import com.example.ross.moviepickerapp.sql.DatabaseHelper;
import com.example.ross.moviepickerapp.Model.User;
import com.example.ross.moviepickerapp.Validation.UserInputValidation;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
        private final AppCompatActivity activity = RegisterActivity.this;

        private NestedScrollView nestedScrollView;

        private TextInputLayout textInputLayoutFName;
        private TextInputLayout textInputLayoutLName;
        private TextInputLayout textInputLayoutEmail;
        private TextInputLayout textInputLayoutPass;
        private TextInputLayout textInputLayoutConfirmPass;
        private TextInputLayout textInputLayoutAge;

        private TextInputEditText RegisterFirstName;
        private TextInputEditText RegisterLastName;
        private TextInputEditText RegisterEmail;
        private TextInputEditText RegisterPass;
        private TextInputEditText RegisterConPass;
        private InputType RegisterAge;

        private AppCompatButton RegisterButton;
        private AppCompatTextView LoginLinkText;

        private UserInputValidation userInputValidation;
        private DatabaseHelper databaseHelper;
        private User user;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);


            initViews();
            initListeners();
            initObjects();

        }

    /**
     * Method to initialize views
     */

    private void initViews(){
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);

        textInputLayoutFName = (TextInputLayout)findViewById(R.id.textInputLayoutFName);
        textInputLayoutLName = (TextInputLayout)findViewById(R.id.textInputLayoutLName);
        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPass = (TextInputLayout)findViewById(R.id.textInputLayoutPass);
        textInputLayoutConfirmPass = (TextInputLayout)findViewById(R.id.textInputLayoutConfirmPass);

        RegisterFirstName = (TextInputEditText)findViewById(R.id.RegisterFirstName);
        RegisterLastName = (TextInputEditText)findViewById(R.id.RegisterLastName);
        RegisterEmail = (TextInputEditText)findViewById(R.id.RegisterEmail);
        RegisterPass = (TextInputEditText)findViewById(R.id.RegisterPass);
        RegisterConPass = (TextInputEditText)findViewById(R.id.RegisterConPass);
        //RegisterAge = (InputType)findViewById(R.id.RegisterAge);

        RegisterButton = (AppCompatButton)findViewById(R.id.RegisterButton);
        LoginLinkText = (AppCompatTextView)findViewById(R.id.LoginLinkText);

    }

    /**
     * Method to listen to clicks on view
     */

    private void initListeners(){
        RegisterButton.setOnClickListener(this);
        LoginLinkText.setOnClickListener(this);
    }

    private void initObjects(){
        userInputValidation = new UserInputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    /**
     * Method to listen to Clicks on view
     * @param view
     */

    public void onClick(View view){
        switch(view.getId()){
            case R.id.RegisterButton:
                postDataToSQLite();
                break;

            case R.id.LoginLinkText:
                finish();
                break;

        }
    }

    /**
     * Method to validate user inputs and then post the data to SQLite
     */

    private void postDataToSQLite() {
        if (!userInputValidation.isInputEditTextFilled(RegisterFirstName, textInputLayoutFName, getString(R.string.error_message_first_name))) {

            return;
        }
        if (!userInputValidation.isInputEditTextFilled(RegisterLastName, textInputLayoutLName, getString(R.string.error_message_last_name)))
        {

            return;
        }
        if (!userInputValidation.isInputEmailValid(RegisterEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {

            return;
        }
        if (!userInputValidation.isInputEditTextFilled(RegisterPass, textInputLayoutPass, getString(R.string.error_password))) {

            return;
        }
        if (!userInputValidation.isInputEditTextMatch(RegisterPass, RegisterConPass, textInputLayoutConfirmPass, getString(R.string.error_password_match))){

        return;
        }
        if(!databaseHelper.checkUser(RegisterEmail.getText().toString().trim())){

            user.setFirstname(RegisterFirstName.getText().toString().trim());
            user.setLastName(RegisterLastName.getText().toString().trim());
            user.setEmail(RegisterEmail.getText().toString().trim());
            user.setPassword(RegisterPass.getText().toString().trim());

            databaseHelper.addUser(user);

            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        }
        else{
            Snackbar.make(nestedScrollView, getString(R.string.email_used), Snackbar.LENGTH_LONG).show();
        }
    }
        private void emptyInputEditText(){
        RegisterFirstName.setText(null);
        RegisterLastName.setText(null);
        RegisterEmail.setText(null);
        RegisterPass.setText(null);
        RegisterConPass.setText(null);
    }

}