package com.example.csulb.wecare;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

/**
 * Created by Rohit on 03/06/2018.
 */

//Share the preferences with the android kernel to store the user defined settings

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "mySharedPrefMAnager";
    private static final String KEY_LOGIN = "";
    private static final String KEY_GOOGLE_LOGIN = "";
    private static final String KEY_FB_LOGIN = "";
    private static final String KEY_NAME = "userName";
    private static final String KEY_PHOTO = "userPhoto";
    private static final String KEY_COLOR = "color"; // 1= Duetranope ; 2= Protanope ; 3= Tirtanope; 4= Duetanomaly ; 5= No color
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_BODY = "body";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_LANG_CODE = "code";
    private static final String KEY_CHECK_BOX = "checkBox";

    SharedPreferences.Editor editor;
    private SharedPrefManager(Context context){
        mCtx = context;
    }

//Set the global context to local context.
    public static synchronized  SharedPrefManager getmInstance (Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

//Set the user login value as email to open the app with displaying login screen.
    public boolean setUserLoggedIn(String login){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN,login);
        editor.apply();
        return true;
    }

//set if the user logged in using google sign in.
    public boolean setGoogleLoggedIn(String login){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN,login);
        editor.apply();
        return true;
    }

//set if the user logged in using facebook login.
    public boolean setFbLoggedIn(String login){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN,login);
        editor.apply();
        return true;
    }

//Return the respective login method.
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_LOGIN, null) !=null){
            return true;
        }
        else if (sharedPreferences.getString(KEY_GOOGLE_LOGIN, null) !=null){
            return true;
        }
        else if (sharedPreferences.getString(KEY_FB_LOGIN, null) !=null){
            return true;
        }
        else{
            return false;
        }
    }

//logout function clears the stored memory for the application.
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            return true;
    }

//Store the username locally to identify different users.
    public boolean userName(String name){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
        return true;
    }

    //Getter method to return the specific user of the app.
    public String getUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
    }

    //Store the display photo of the user
    public boolean userPhoto(String photoUrl){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_PHOTO, photoUrl);
        editor.apply();
        return true;
    }

    //Getter method to get the user photo.
    public String getPhotoUrl() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO,null);
    }

    //Set the theme of the application according to the colorblindness detected.
    public boolean setColor(String color){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_COLOR,color);
        editor.apply();
        return true;
    }

    //Getter function to get the theme of the application.
    public String getColor(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String color= sharedPreferences.getString(KEY_COLOR,"0");
        return color;
    }

    //Setter for age
    public void setAge(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_AGE, id);
        editor.apply();
    }

    //getter for age
   public int getAge(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int age = sharedPreferences.getInt(KEY_AGE, 0);
        return age;
    }

    //Setter for gender
    public void setGender(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_GENDER, id);
        editor.apply();
    }

    //getter for gender
    public int getGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int age = sharedPreferences.getInt(KEY_GENDER, 0);
        return age;
    }

    //Setter for body
    public void setBody(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_BODY, id);
        editor.apply();
    }

    //getter for body
    public int getBody(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int age = sharedPreferences.getInt(KEY_BODY, 0);
        return age;
    }

    //Setter for language
    public void setLanguage(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_LANGUAGE, id);
        editor.apply();
    }

    //getter for language
    public int getLanguage(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int age = sharedPreferences.getInt(KEY_LANGUAGE, 0);
        return age;
    }


    //Setter for language code
    public void setLanguageCode(int code){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(KEY_LANG_CODE, code);
        editor.apply();
    }

    //getter for language
    public int getLanguageCode(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int code = sharedPreferences.getInt(KEY_LANG_CODE, 1);
        return code;
    }

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }


    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }


}
