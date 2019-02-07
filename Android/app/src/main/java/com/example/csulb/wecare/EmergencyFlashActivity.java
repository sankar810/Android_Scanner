package com.example.csulb.wecare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Show the 10 sec countdown timer and then call the emergency services.
public class EmergencyFlashActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mEmergencyFlash;
    Button mCancelEmergency;
    int secondsLeft = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call_flash);

//Initialize the items
        mEmergencyFlash = (TextView)findViewById(R.id.emergencyCallFlash);
        mCancelEmergency = (Button)findViewById(R.id.emergencyCancelButton);
        mCancelEmergency.setOnClickListener(this);

//Start the timer for 10 secs.
        timer = new CountDownTimer(10000, 100) {
            public void onTick(long ms) {
                if (Math.round((float)ms / 1000.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)ms / 1000.0f);
                    mEmergencyFlash.setText("Calling for Emergency Services in " +secondsLeft +" seconds!");
                }
            }

            public void onFinish() {
                finish();
                callEmergency();
            }
        }.start();
    }

//Call the emergency service
    private void callEmergency() {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setData(Uri.parse("tel:0123456789"));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.

        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        102);
            }
            startActivity(callIntent);  //call activity and make phone call
        }
        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"Error: Call cannot be placed.",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed(){}


    @Override
    public void onClick(View v) {
        if (v == mCancelEmergency){
            timer.cancel();
            finish();
        }
    }
}
