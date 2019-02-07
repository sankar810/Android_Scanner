package com.example.csulb.wecare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.NumberFormat;

/**
 * This sample demonstrates combining the Recording API and History API of the Google Fit platform
 * to record steps, and display the daily current step count. It also demonstrates how to
 * authenticate a user with Google Play Services.
 */
public class RunningActivity extends AppCompatActivity {

    public ArcProgress mRunningProgress;
    public TextView mRunningCalories, mRunningActive, mRunningDistance;

    NumberFormat formatter = NumberFormat.getNumberInstance();
    NumberFormat formatter1 = NumberFormat.getNumberInstance();

    public static final String TAG = "StepCounter";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    final Handler handler = new Handler();
    Runnable runnable;

    final Handler handler1 = new Handler();
    Runnable runnable1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        // This method sets up our custom logger, which will print all log messages to the device
        // screen, as well as to adb logcat.
        mRunningProgress = (ArcProgress)findViewById(R.id.runningProgress);

        mRunningCalories = (TextView)findViewById(R.id.runningCaloriesTextView);
        mRunningActive = (TextView)findViewById(R.id.runningActiveTextView);
        mRunningDistance = (TextView)findViewById(R.id.runningDistanceTextView);
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(2);

        formatter1.setMaximumFractionDigits(0);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Create fitness options to be used from google fit.
        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY,FitnessOptions.ACCESS_READ)
                        .build();

                        //Check if permissions have been granted for the requested fitness api else subscribe to the options.
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);


        } else {

            subscribeStepCount();
            subscribeDistance();
            subscribeCalorie();
            subscribeActiveTime();

            final int delay = 1000; //milliseconds

            runnable = new Runnable() {
                @Override
                public void run() {
                    readData();
                    readCalorieData();
                    readDistanceData();
                    readTimeData();
                }
            };
            handler.postDelayed(runnable,delay);
            /*handler.postDelayed(new Runnable(){
                public void run(){

                    handler.postDelayed(this, delay);
                }
            }, delay);
*/
        }


    }

    //Validate the request code for security anf add to the subscription list.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribeStepCount();
                subscribeDistance();
                subscribeCalorie();
                subscribeActiveTime();
            }
        }
    }

    /** Records step data by requesting a subscription to background step data. */
    public void subscribeStepCount() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                } else {
                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    public void subscribeDistance() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_DISTANCE_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                } else {
                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    public void subscribeCalorie() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_CALORIES_EXPENDED)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                } else {
                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    public void subscribeActiveTime() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_ACTIVITY_SEGMENT)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                } else {
                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */
    private void readData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                long total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

                                progressBar.setVisibility(View.GONE);
                                mRunningProgress.setMax(5000);
                                mRunningProgress.setTextColor(ContextCompat.getColor(RunningActivity.this, R.color.white));
                                mRunningProgress.setBottomText("Goal: 5000");
                                mRunningProgress.setProgress(((int) total));
                                Log.i(TAG, "Total steps: " + total);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }

/**
     * Reads the current daily calorie total, computed from midnight of the current day on the device's
     * current timezone.
     */
    private void readCalorieData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.AGGREGATE_CALORIES_EXPENDED)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                                if(total < 1000){
                                    mRunningCalories.setText(String.valueOf(formatter1.format(total))+ "\ncalories");
                                    Log.i(TAG, "Total Calorie (cal): " + total);
                                }
                                else{
                                    float total1 = total/1000;
                                    mRunningCalories.setText(String.valueOf(formatter1.format(total1))+ "\nKcal");
                                    Log.i(TAG, "Total Calorie: " + total);
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }

/**
     * Reads the current daily distance total, computed from midnight of the current day on the device's
     * current timezone.
     */
    private void readDistanceData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.AGGREGATE_DISTANCE_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                                if(total < 160.93){
                                    float distance = (float) (total * 3.28);
                                    mRunningDistance.setText(String.valueOf(formatter.format(distance))+"\nfoot");
                                    Log.i(TAG, "Total Distance: (feet)" + distance);
                                }
                                else{
                                    float distanceMi = (float) (total * 0.0006);
                                    mRunningDistance.setText(String.valueOf(formatter.format(distanceMi))+"\nmiles");
                                    Log.i(TAG, "Total Distance: (miles)" + distanceMi);
                                }
                                Log.i(TAG,"Distance:"+ total);

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }

/**
     * Reads the total activity time, computed from midnight of the current day on the device's
     * current timezone.
     */
    private void readTimeData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                int total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DURATION).asInt();

                                double time = (double) total*0.00001667;
                                if(time <60){
                                    mRunningActive.setText(String.valueOf(formatter.format(time))+"\nminutes");
                                    Log.i(TAG, "Total Time: " + total);
                                }
                                else{
                                    double timeHr = (double)time/60;
                                    double timeMin = (double)time%60;
                                    mRunningActive.setText(String.valueOf(formatter1.format(timeHr))+" Hrs\n"+
                                    String.valueOf(formatter1.format(timeMin)+" mins"));
                                    Log.i(TAG, "Total Time: " + total);
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }

}
