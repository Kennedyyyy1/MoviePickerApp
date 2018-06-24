package com.example.ross.moviepickerapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ross.moviepickerapp.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ross on 13/04/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Version
    public static final int DATABASE_VERSION = 1;

    //Database Name
    public static final String DATABASE_NAME = "UserManager.db";

    //User Table Name
    public static final String TABLE_USER = "user";

    //User Table Column Names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_FNAME = "userF_name";
    public static final String COLUMN_USER_LNAME = "userL_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_UNAME = "user_username";
    public static final String COLUMN_USER_PASS = "user_pass";

    //creating com.example.ross.moviepickerapp.sql table query
    private String CREATE_USER_TABLE = " CREATE TABLE " + TABLE_USER + "(" +COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_FNAME + " TEXT," + COLUMN_USER_LNAME + " TEXT," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASS + " TEXT"+ ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     * @param context
     */
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion){

        //Drop user table if exists
        db.execSQL(DROP_USER_TABLE);

        onCreate(db);
    }

    /**
     * Method to add users to database
     */

    public  void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FNAME, user.getFirstname());
        values.put(COLUMN_USER_LNAME, user.getLastName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASS, user.getPassword());

        //Inserting Rows
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    /**
     * Method to fetch list of all users
     *
     * @return list
     */

    public List<User> getAllUser(){
        //array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_FNAME,
                COLUMN_USER_LNAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASS
        };
        //Sorting Strings
        String order =
                COLUMN_USER_ID +"ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();
        /**
         * Create Query to fetch user info from records
         * SELECT ALL FROM USER ORDER BY USER_NAME etc
         */
        Cursor cursor = db.query(TABLE_USER, columns,
                null, //columns for the WHERE clause
                null, //the values from the WHERE clause
                null, //group the rows
                null, //filter by row groups
                null);//order

        //Traversing all rows and adding to the list
        if(cursor.isFirst()){
            do {
                User user =  new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FNAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASS)));

                //adding users to list
                userList.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        //return the user list
        return userList;

    }

    /**
     * Method to Update Users
     * @param user
     */
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_FNAME, user.getFirstname());
        values.put(COLUMN_USER_LNAME, user.getLastName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASS, user.getPassword());

        //update the row in the database
        db.update(TABLE_USER, values, COLUMN_USER_ID+" = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Method to delete Users from db
     * @param user
     */
    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete user by id
        db.delete(TABLE_USER,COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Method to Check User exists or not
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email){

        String [] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        //String to select email column
        String emailSelection = COLUMN_USER_EMAIL + " = ?";

        //String to select the specific user email
        String [] selection = {email};

        //query to the database for the number of emails
        Cursor cursor = db.query(TABLE_USER, columns, emailSelection, selection, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        //if statement to check if the number of emails (ie number of users) is greater than 0
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUser(String email, String password){

        //array of columns to be returned
        String [] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String emailSelection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASS + " = ?";
        String [] selection = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, emailSelection, selection, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    public  User displayAccountInfo(String email, String password){
        String [] userInfo = {
                COLUMN_USER_ID,
                COLUMN_USER_FNAME,
                COLUMN_USER_LNAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASS
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String infoSelection = COLUMN_USER_EMAIL +" = ?"+" AND "+ COLUMN_USER_PASS +" = ?";
        String [] selection = {email, password};

        Cursor cursor = db.query(TABLE_USER, userInfo, infoSelection, selection, null, null, null);
        System.out.println(cursor.getCount());
       if(cursor.moveToFirst()){

            User user = new User();
           user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
            user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FNAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASS)));

            cursor.close();
            db.close();
            return user;

        }
            cursor.close();
            db.close();
            return null;

    }
}
