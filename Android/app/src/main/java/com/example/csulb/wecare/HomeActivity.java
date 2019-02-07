package com.example.csulb.wecare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


//Main screen of the application where all the tabs are available.

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private GoogleApiClient client;
    public ImageView mProfilePhoto;
    public TextView mName,mDate;
    public LinearLayout mRunningTab, mExerciseTab, mFitbitTab,
            mEmergengyTab, mHeartRateTab, mSettingsTab;
    private FirebaseAuth mFirebaseAuth;
    private LoginManager mLoginManager;
    private GoogleSignInClient googleSignInClient;

    public static final String TAG = "colorCalledBySharedPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//Initialize the items
        mName = (TextView) findViewById(R.id.homeNameTextView);
        mName.setText(SharedPrefManager.getmInstance(this).getUserName());
        // Display a date in day, month, year format
        mDate = (TextView) findViewById(R.id.homeDateTextView);
        mDate.setText(""+DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(new Date()));


        mProfilePhoto = (ImageView)findViewById(R.id.homeProfilePhoto);
        Glide.with(this).load(SharedPrefManager.getmInstance(this).getPhotoUrl()).into(mProfilePhoto);
        mProfilePhoto.setOnClickListener(this);

        mRunningTab = (LinearLayout)findViewById(R.id.homeRunningTab);
        mRunningTab.setOnClickListener(this);

        mExerciseTab = (LinearLayout)findViewById(R.id.homeExerciseTab);
        mExerciseTab.setOnClickListener(this);


        mFitbitTab = (LinearLayout)findViewById(R.id.homeMedicineTab);
        mFitbitTab.setOnClickListener(this);

        mEmergengyTab = (LinearLayout)findViewById(R.id.homeEmergencyTab);
        mEmergengyTab.setOnClickListener(this);

        mHeartRateTab = (LinearLayout)findViewById(R.id.homeHeartRateTab);
        mHeartRateTab.setOnClickListener(this);

        mSettingsTab = (LinearLayout)findViewById(R.id.homeSettingsTab);
        mSettingsTab.setOnClickListener(this);

        checkTheme();

//Check the necessary permission required for the application.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        }
        else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    102);
        }
    }

//Check the theme as per the color blindness test.
    private void checkTheme() {
        String i = SharedPrefManager.getmInstance(this).getColor();
        String[] color = {};

        switch (i){
            case "0":
                color = this.getResources().getStringArray(R.array.normal);
                break;
            case "1":
                color = this.getResources().getStringArray(R.array.duetranope);
                break;
            case "2":
                color = this.getResources().getStringArray(R.array.protanope);
                break;
            case "3":
                color = this.getResources().getStringArray(R.array.tirtanope);
                break;
            case "4":
                color = this.getResources().getStringArray(R.array.deuteranomly);
                break;
        }

        mRunningTab.setBackgroundColor(Color.parseColor(color[0]));
        mExerciseTab.setBackgroundColor(Color.parseColor(color[1]));
        mHeartRateTab.setBackgroundColor(Color.parseColor(color[2]));
        mFitbitTab.setBackgroundColor(Color.parseColor(color[3]));
        mSettingsTab.setBackgroundColor(Color.parseColor(color[4]));

    }


    @Override
    protected void onStart() {

        super.onStart();
        checkTheme();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkTheme();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkTheme();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTheme();
    }




    @Override
    public void onClick(View v) {
        if(v == mProfilePhoto){


        }
        else if(v==mRunningTab){
            startActivity(new Intent(HomeActivity.this, RunningActivity.class));
        }
        else if(v == mExerciseTab){
            startActivity(new Intent(HomeActivity.this,ExerciseActivity.class));

        }
        else if(v == mHeartRateTab){
            startActivity(new Intent(HomeActivity.this,HeartRateActivity.class));

        }
        else if(v == mFitbitTab){

        }
        else if(v == mEmergengyTab){
            startActivity(new Intent(HomeActivity.this,EmergencyActivity.class));

        }
        else if(v == mSettingsTab){
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        }
    }

}
