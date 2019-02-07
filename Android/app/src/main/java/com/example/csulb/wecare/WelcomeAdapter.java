package com.example.csulb.wecare;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

// A Test page to check the working of ViewPager
//This adapter works closely to load the ViewPages layouts in the app. 
public class WelcomeAdapter extends PagerAdapter{

//Define the context, layoutInflater service and integer array containing layout ID.
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] layouts = {R.layout.signup_screen_1, R.layout.signup_screen_2,
            R.layout.signup_screen_3, R.layout.signup_screen_4, R.layout.signup_screen_5};


//Set global context to page context.
    public WelcomeAdapter(Context context) {
        this.context = context;
    }

//Get count of the pages to scroll.
    public int getCount(){
        return layouts.length;
    }

//Define the view of the object
    public boolean isViewFromObject(View view, Object object){
        return view == object;
    }

//Instantiate the objects from the page.
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layouts[position],null);
        ViewPager loginViewPager = (ViewPager) container;
        loginViewPager.addView(view,0);
        return view;
    }

//Destroy the items on completion.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}