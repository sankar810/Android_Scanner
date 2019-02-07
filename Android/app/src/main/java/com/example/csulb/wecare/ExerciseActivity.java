package com.example.csulb.wecare;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

//Start the exercise page displaying the various category of exercises.
public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mBalance, mCardio, mStretch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
//Initialize the items and set the on click listener on them.
        mBalance = (LinearLayout)findViewById(R.id.exerciseBalanceTab);
        mBalance.setOnClickListener(this);
        mCardio = (LinearLayout)findViewById(R.id.exerciseCardioTab);
        mCardio.setOnClickListener(this);
        mStretch = (LinearLayout)findViewById(R.id.exerciseStretchTab);
        mStretch.setOnClickListener(this);
    }

//On click start the respective activity.
    @Override
    public void onClick(View v) {
        if(v == mBalance){
            startActivity(new Intent(ExerciseActivity.this,BalanceActivity.class));
        }
        else if(v == mCardio){
            startActivity(new Intent(ExerciseActivity.this,CardioActivity.class));
        }
        else if(v == mStretch){
            startActivity(new Intent(ExerciseActivity.this,StretchActivity.class));
        }
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

        mBalance.setBackgroundColor(Color.parseColor(color[2]));
        mCardio.setBackgroundColor(Color.parseColor(color[0]));
        mStretch.setBackgroundColor(Color.parseColor(color[1]));

    }

}
