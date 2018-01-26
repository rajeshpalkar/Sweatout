package com.example.rajeshpalkar.sweatout;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Task3Activity extends AppCompatActivity implements
        FragmentRecyclerView5.OnExerciseListItemSelectedListener,
        FragmentRecyclerView4.OnExerciseListItemSelectedListener,
        FragmentRecyclerView2.OnExerciseListItemSelectedListener,
        FragmentRecyclerView3.OnExerciseListItemSelectedListener,
        FragmentDetailsViewPager.nestedFragmentListener{

    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager mViewPager;
    MovieData movieData;
    Toolbar toolbar;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        toolbar = (Toolbar) findViewById(R.id.toolbar1Task3);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);

        movieData = new MovieData();

        myPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
      //  mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(myPagerAdapter);

/*
       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                getFragmentManager().popBackStack();

            }

            @Override
            public void onPageSelected(int position) {


                getFragmentManager().popBackStack();



            }

            @Override
            public void onPageScrollStateChanged(int state) {

                getFragmentManager().popBackStack();


            }
        });

*/

        customizeViewPager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

      /*  if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview))
                    .commit();
        } else {

        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void customizeViewPager() {

        mViewPager.setPageTransformer(false,new ViewPager.PageTransformer(){

            public void transformPage(View page, float position){
                final float normalized_position = Math.abs(Math.abs(position)-1);
                page.setScaleX(normalized_position/2+ 0.5f);
                page.setScaleY(normalized_position/2+ 0.5f);

                page.setRotationY(position * -50);
            }

        });
    }


    public void onExerciseListItemSelected(int position)
    {

        int currentItem = (int) mViewPager.getCurrentItem();
        System.out.println("current item" +currentItem);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pagercontainer,FragmentDetailsViewPager.newInstance(currentItem,position))
                .addToBackStack(null)
                .commit();

    }

    public void changeToParentFragment(int position)
    {

    }


}