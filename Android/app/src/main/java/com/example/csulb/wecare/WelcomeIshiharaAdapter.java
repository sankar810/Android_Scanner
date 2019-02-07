package com.example.csulb.wecare;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//This adapter is used to load the Ishiharas's various pages smoothly.
public class WelcomeIshiharaAdapter extends PagerAdapter{

//Define the context, layoutInflater service and the integer array to support the pages.
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] layouts = {};

//Set global context with the context of this activity.
    public WelcomeIshiharaAdapter(Context context) {
        this.context = context;
    }

//Get the count of the layouts to be presented.
    public int getCount(){
        return layouts.length;
    }

//Set the view as the object of the layouts.
    public boolean isViewFromObject(View view, Object object){
        return view == object;
    }

//Instantiate the items in the model.
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layouts[position],null);
        ViewPager loginViewPager = (ViewPager) container;
        loginViewPager.addView(view,0);
        return view;
    }

//Destroy the item on completion.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}