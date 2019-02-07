package com.example.csulb.wecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.internal.Utility;

//Change the theme of the app according to users preference.

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {

//Define the buttons
    Button duetanope, protanope, tirtanope, duetannomaly, normal;

//onCreate is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //Set the ID of the buttons
        duetanope = (Button)findViewById(R.id.duetranope);
        protanope = (Button)findViewById(R.id.protanope);
        tirtanope = (Button)findViewById(R.id.tirtanope);
        duetannomaly = (Button)findViewById(R.id.duetranomaly);
        normal = (Button)findViewById(R.id.normal);

        //Set the onClickListerners to provide functionality.
        duetanope.setOnClickListener(this);
        protanope.setOnClickListener(this);
        tirtanope.setOnClickListener(this);
        duetannomaly.setOnClickListener(this);
        normal.setOnClickListener(this);
    }


//When a button is clicked, the view action is executed.
    //Set the theme according to the option selected.
    @Override
    public void onClick(View view) {
        if(view == duetanope){
            SharedPrefManager.getmInstance(this).setColor("1");
            Toast.makeText(this, "Theme changed to: Duetranope", Toast.LENGTH_SHORT).show();
            goToHome();
        } else if (view == protanope){
            SharedPrefManager.getmInstance(this).setColor("2");
            Toast.makeText(this, "Theme changed to: Protanope", Toast.LENGTH_SHORT).show();
            goToHome();
        } else if (view == tirtanope){
            SharedPrefManager.getmInstance(this).setColor("3");
            Toast.makeText(this, "Theme changed to: Tirtanope", Toast.LENGTH_SHORT).show();
            goToHome();
        } else if (view == duetannomaly){
            SharedPrefManager.getmInstance(this).setColor("4");
            Toast.makeText(this, "Theme changed to: Duetranomaly", Toast.LENGTH_SHORT).show();
            goToHome();
        } else if (view == normal){
            SharedPrefManager.getmInstance(this).setColor("0");
            Toast.makeText(this, "Theme changed to: Normal", Toast.LENGTH_SHORT).show();
            goToHome();
        }
    }

    public void goToHome(){
        finish();
        Intent intent = new Intent(ThemeActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}