package com.example.csulb.wecare;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Display the Balance exercise page.

public class BalanceActivity extends AppCompatActivity {

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BalanceActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
//Add exercises to the list of exercises.

        Exercises exercises = new Exercises("Reverse Lunges",R.drawable.reverse_lunge_balance,"Set: 2","Reps: 15/ Each",
                "1. Step back with your left foot coming into a deep lunge, bending both knees to 90 degrees.\n" +
                        "2. Press the right heel into the ground as you push off with your left foot, kicking your left leg to touch your left toes to your right hand.\n" +
                        "3. With control, return to the lunge position. This completes one rep.");
        list.add(exercises);
        Exercises exercises1 = new Exercises("Single Leg Deadlifts",R.drawable.single_leg_bend_balance,"Set: 3","Reps: 10",
                "1. Stand with your arms at your sides, with your right foot a few inches off the floor.\n"
                        +"2. Keeping your left knee very slightly bent, lean forward onto your left leg, raising your right leg behind you and reaching toward the floor with your hands.\n"
                        +"3. Your head, back and right leg should form a straight line, parallel to the ground.\n"
                        +"4. Hold for a second and slowly return to the start position. ");
        list.add(exercises1);
        Exercises exercises2 = new Exercises("Single Leg Chair Squat",R.drawable.single_leg_squat_balance,"Set: 2","Reps: 4",
                "1. Start by standing about a foot in front of a sturdy chair, facing away from the chair. " +
                        "2. Lift your left leg off the floor, keeping it straight. " +
                        "3. Slowly, over a count of about 4 seconds, squat down using only your right leg, until you’re sitting in the chair. " +
                        "4. Stand up using both legs, then repeat the repetitions before switching to work the left leg.");
        list.add(exercises2);
        //Set the recycler adapter.
   
        adapter = new ExerciseAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

}