package com.example.csulb.wecare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

//Display the Stretching exercise page.
public class StretchActivity extends AppCompatActivity {

//Define the recyclerView (scrolling page), adapter to load the items and the list of items.
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Exercises> list;

//onCreate is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercise);

//Initialize the values by setting the ID.
        recyclerView = (RecyclerView) findViewById(R.id.exerciseRecyclerView);
        recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper1 = new PagerSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StretchActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();

//Add exercises to the list of exercises.

        Exercises exercises = new Exercises("Shoulder/ Pectoralis Stretch",R.drawable.shoulder_stretch,"Set: 2","Reps: 20",
                "1. Clasp hands behind your back.\n" +
                        "2. Push the chest outward, and raise the chin.\n" +
                        "3. Hold the pose for 1 to 3 seconds.");
        list.add(exercises);
        Exercises exercises1 = new Exercises("Hip and Knee Flexion Stretch",R.drawable.hip_knee_stretch,"Set: 1","Reps: 10",
                "1. Hug one knee at a time, pulling it toward your chest.\n" +
                        "2. Hold the pose for 1 to 3 seconds.\n" +
                        "3. Alternate.");
        list.add(exercises1);
        Exercises exercises2 = new Exercises("Neck Stretches",R.drawable.neck_stretch,"Set: 2","Reps: 20",
                "1. Relax and lean your head forward.\n" +
                        "2. Slowly roll toward one side and hold for 10 seconds.\n" +
                        "3. Repeat on other side.\n" +
                        "4. Relax again and lift your chin back to starting position.\n" +
                        "5. Do this 10 times for each direction.");
        list.add(exercises2);
        Exercises exercises3 = new Exercises("Triceps Stretch",R.drawable.triceps_stretch,"Set: 2","Reps: N/A",
                "1. Raise your arm and bend it so that your hand reaches toward the opposite side.\n" +
                "2. Use your other hand and pull the elbow toward your head.\n" +
                "3. Hold for 10 to 30 seconds.\n" +
                "4. Repeat on the other side.");
        list.add(exercises3);
        Exercises exercises4 = new Exercises("Forward Stretch",R.drawable.forward_stretch,"Set: 2","Reps: 20",
                "This stretch is also known as the rhomboid upper or upper back stretch.\n" +
                        "\n" +
                        "1. Clasp your hands in front of you and lower your head in line with your arms.\n" +
                        "2. Press forward and hold for 1 to 3 seconds.");
        list.add(exercises4);
        adapter = new ExerciseAdapter(list,this);
    //Set the recycler adapter.
        recyclerView.setAdapter(adapter);

    }

}
