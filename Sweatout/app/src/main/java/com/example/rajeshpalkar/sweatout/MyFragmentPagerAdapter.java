package com.example.rajeshpalkar.sweatout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;


public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Context ctx;
    HashMap movie;



    MovieData movieData = new MovieData();
    int count;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        // Required empty public constructor
        super(fm);
        this.ctx = ctx;
      //  count= size;
    }

    public Fragment getItem(int position){
        Fragment recycler = new Fragment();
        switch (position)
        {
            case 0:
                recycler = FragmentRecyclerView2.newInstance(R.id.fragmentrecyclerview);
                break;
            case 1:
                recycler= FragmentRecyclerView3.newInstance(R.id.fragmentrecyclerview);
                break;
            case 2:
                recycler = FragmentRecyclerView4.newInstance(R.id.fragmentrecyclerview);
                break;
            case 3:
                recycler = FragmentRecyclerView5.newInstance(R.id.fragmentrecyclerview);
                break;
        }
       return recycler;
       // return FragmentDetailsViewPager.newInstance();
    }


    public int getCount() {return 4;}

    public CharSequence getPageTitle(int position)
    {
        String name=null;
        if(position==0)
        {
            name="Kettlebell";
        }

        if(position==1)
        {
            name="Battle Rope";
        }

        if(position==2)
        {
        name="Box";
        }

        if(position==3)
        {
            name="Suspension";
        }

        return name;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


}


