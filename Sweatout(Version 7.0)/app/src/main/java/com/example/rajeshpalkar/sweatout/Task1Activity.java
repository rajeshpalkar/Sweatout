package com.example.rajeshpalkar.sweatout;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

public class Task1Activity extends AppCompatActivity implements FragmentRecyclerView.OnBodyPartListItemSelectedListener,
FragmentRecyclerView1.OnExerciseListItemSelectedListener,
FragmentDetails.ChangeTitleToMovie{

    Toolbar toolbar;
    ActionBar mActionBar;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
       // mActionBar.setLogo(R.drawable.aboutmeicon);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview))
                    .commit();
        } else {

        }

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

    public void onBodyPartListItemSelected(int position, HashMap<String,?> movie)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,FragmentRecyclerView1.newInstance(movie))
                .addToBackStack(null)
                .commit();

    }

    public void onExerciseListItemSelected(int position, HashMap<String,?> movie)
    {
        System.out.println("movie activity :"+movie.get("id"));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,FragmentDetails.newInstance(movie,position))
                .addToBackStack(null)
                .commit();

    }

    public void changeTileToDefault(String title)
    {
        TextView textView = (TextView) findViewById(R.id.toolbar1_title);
        textView.setText(title);
    }

    public void changeTitle(String title)
    {
        TextView textView = (TextView) findViewById(R.id.toolbar1_title);
        textView.setText(title);
    }



}
