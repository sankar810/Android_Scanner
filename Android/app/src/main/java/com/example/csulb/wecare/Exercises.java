package com.example.csulb.wecare;

import android.graphics.drawable.Drawable;


//Exercise constructor class to get and set the respective items.
public class Exercises {

    public String title;
    public int photo;
    public String set;
    public String reps;
    public String description;

//Default constructor.
    public Exercises() {
    }

//Copy constructor.
    public Exercises(String title, int photo, String set, String reps, String description) {
        this.title = title;
        this.photo = photo;
        this.set = set;
        this.reps = reps;
        this.description = description;
    }

//getter for title
    public String getTitle() {
        return title;
    }

//setter function for title.
    public void setTitle(String title) {
        this.title = title;
    }

//getter for photo.
    public int getPhoto() {
        return photo;
    }

//setter for photo.
    public void setPhoto(int photo) {
        this.photo = photo;
    }

//Getter for number of sets
    public String getSet() {
        return set;
    }

//Setter for the number of sets.
    public void setSet(String set) {
        this.set = set;
    }

//getter for the number of reps
    public String getReps() {
        return reps;
    }

//setter for the number of reps.
    public void setReps(String reps) {
        this.reps = reps;
    }

//getter for description
    public String getDescription(){ return description;}
}
