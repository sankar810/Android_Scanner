package com.example.csulb.wecare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

//Display the Cardio exercise page.

public class CardioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Exercises> list;

//onCreate is called when the activity is created.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercise);

//Define the recyclerView (scrolling page), adapter to load the items and the list of items.

//Initialize the values by setting the ID.
        recyclerView = (RecyclerView) findViewById(R.id.exerciseRecyclerView);
        recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper1 = new PagerSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CardioActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
//Add exercises to the list of exercises.

        Exercises exercises = new Exercises("Boxing",R.drawable.boxing_cardio,"Set: 2","Reps: N/A",
                "1. Start with your feet centered (on a mat, if you need a visual guide) and hip-width distance apart. Step your left foot back behind the mat, and then bring your right foot to meet your left.\n" +
                        "2. Step your left foot forward back to center, and then your right.\n" +
                        "3. Step your left foot forward in front of the mat, and bring your right to meet it. Then step back with your left foot to the center of the mat, and bring your right foot to meet your left.\n" +
                        "4. Step out to the left side with your left foot, and out to the right with your right foot. Then return your left foot to center, followed by your right.\n" +
                        "5. Repeat the entire series, moving more quickly each time, focusing on agility and speed.\n" +
                        "6. Continue for 60 seconds.");
        list.add(exercises);
        Exercises exercises1 = new Exercises("180 Jump",R.drawable.jump_cardio,"Set: 1","Reps: 10",
                "1. Begin facing the left side of your mat (or space) with feet hip-distance apart, core tight, slightly hinged at the hips.\n" +
                "2. Jump 180 degrees to your right, keeping your elbows near your rib cage and hands in front of you for balance.\n" +
                "3. Jump 180 degrees to your left; make sure to be light on your feet, moving quickly.\n" +
                "4. Repeat the jumps back and forth 10 times.");
        list.add(exercises1);
        Exercises exercises2 = new Exercises("Lateral Shuffle",R.drawable.lateral_shuffle_cardio,"Set: 2","Reps: N/A",
                "1. Keeping a tight core and a soft bend in the knee, sit your hips back, and take a light sideways hop to the right.\n" +
                        "2. Reach your left hand toward your right foot, and touch the ground.\n" +
                        "3. Bring yourself back up and hop sideways to the left, reaching your right hand toward your left foot. Move quickly to intensify the cardio.\n" +
                        "This completes one rep. Do as many reps as you can for 60 seconds to complete a set.");
        list.add(exercises2);
        //Set the recycler adapter.
        
        adapter = new ExerciseAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

}
