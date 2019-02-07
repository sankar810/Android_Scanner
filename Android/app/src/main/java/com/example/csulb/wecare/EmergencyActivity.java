package com.example.csulb.wecare;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//Display the large emergency button.
public class EmergencyActivity extends AppCompatActivity implements View.OnClickListener {

    Button mEmergencyButton;
    TextView mEmergencyFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        //Initialize the items
        mEmergencyButton = (Button) findViewById(R.id.emergencyButton);
        mEmergencyButton.setOnClickListener(this);

        //check if calling permission is granted else request for permission.
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        102);
            }

        }
        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"Error: Call cannot be placed.",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        if(v == mEmergencyButton){
            startActivity(new Intent(EmergencyActivity.this,EmergencyFlashActivity.class));
        }
        else if(v == mEmergencyFlash){
            setContentView(R.layout.activity_emergency);
        }
    }


}
