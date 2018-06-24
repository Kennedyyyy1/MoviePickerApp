package com.example.ross.moviepickerapp.Validation;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ross on 17/04/2018.
 */

public class UserInputValidation {
    private Context context;

    public UserInputValidation(Context context){
        this.context = context;
    }

    /**
     * method to hide the keyboard
     * @param view
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Method to check the input fields
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     *@return
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText,TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
            if(value.isEmpty()){
                textInputLayout.setError(message);
                hideKeyboard(textInputEditText);
                return false;
            }
            else{
                textInputLayout.setErrorEnabled(false);
            }
            return true;
        }


/**
 * method to check if the Email field contains a vaild Email
 * @param textInputEditText;
 * @param textInputLayout;
 * @param message
 * @return
 */
    public boolean isInputEmailValid(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if(value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            textInputLayout.setError(message);
            hideKeyboard(textInputEditText);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }


    public boolean isInputEditTextMatch(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();

        if (!value1.contentEquals(value2)) {
            textInputLayout.setError(message);
            hideKeyboard(textInputEditText2);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }


}