package com.example.csulb.wecare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getTimeInstance;

/**
 * This sample demonstrates combining the Recording API and History API of the Google Fit platform
 * to record steps, and display the daily current step count. It also demonstrates how to
 * authenticate a user with Google Play Services.
 */
public class RunningActivityTest extends AppCompatActivity {


    final private int REQUEST_CODE_ASK_PERMISSION = 124;
    public static final String TAG = "StepCounter";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    public ArcProgress mRunningProgress;
    public TextView mRunningCalories, mRunningActive, mRunningDistance;

    GoogleApiClient mClient;

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







        //Request fine location permission



        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                        .build();
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

        }
    }


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
                                    final Handler handler = new Handler();
                                    final int delay = 500; //milliseconds


                                    handler.postDelayed(new Runnable(){
                                        public void run(){
                                            readStepData();
                                            handler.postDelayed(this, delay);
                                        }
                                    }, delay);



                                } else {
                                    Log.w(TAG, "There was a problem subscribing to step count data.", task.getException());
                                }
                            }
                        });
    }



    public void subscribeDistance() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_DISTANCE_DELTA)

                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                    final Handler handler = new Handler();
                                    final int delay = 500; //milliseconds

                                    handler.postDelayed(new Runnable(){
                                        public void run(){
                                            readDistance();
                                            handler.postDelayed(this, delay);
                                        }
                                    }, delay);

                                } else {
                                    Log.w(TAG, "There was a problem subscribing to distance.", task.getException());
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
                                    final Handler handler = new Handler();
                                    final int delay = 500; //milliseconds

                                    handler.postDelayed(new Runnable(){
                                        public void run(){
                                            readCalorieData();
                                            handler.postDelayed(this, delay);
                                        }
                                    }, delay);

                                } else {
                                    Log.w(TAG, "There was a problem subscribing to calorie.", task.getException());
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
                                    final Handler handler = new Handler();
                                    final int delay = 500; //milliseconds

                                    handler.postDelayed(new Runnable(){
                                        public void run(){
                                            readActiveTime();
                                            handler.postDelayed(this, delay);
                                        }
                                    }, delay);

                                } else {
                                    Log.w(TAG, "There was a problem subscribing to distance.", task.getException());
                                }
                            }
                        });
    }


    private void readActiveTime() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                int total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_ACTIVITY).asInt();
                                mRunningActive.setText(String.valueOf(total)+"\nmins");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RunningActivityTest.this, "There was a problem getting the active time.", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private void readDistance() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                                mRunningDistance.setText(String.valueOf(total)+"\nmi");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(RunningActivityTest.this, "There was a problem getting the distance.", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */
    private void readStepData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotalFromLocalDevice(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                int total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                                mRunningProgress.setMax(5000);
                                mRunningProgress.setTextColor(ContextCompat.getColor(RunningActivityTest.this, R.color.white));
                                mRunningProgress.setBottomText("Goal: 5000");
                                mRunningProgress.setProgress(total);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RunningActivityTest.this, "There was a problem getting the step count.", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private void readCalorieData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                                int total1 = (int)total;
                                mRunningCalories.setText(String.valueOf(total1)+"\ncalories");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RunningActivityTest.this, "There was a problem getting the calorie count.", Toast.LENGTH_SHORT).show();
                            }
                        });


    }


}