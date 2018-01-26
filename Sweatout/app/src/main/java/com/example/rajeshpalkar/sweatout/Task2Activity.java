package com.example.rajeshpalkar.sweatout;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class Task2Activity extends AppCompatActivity implements FragmentRecyclerView.OnBodyPartListItemSelectedListener,
FragmentDetails.ChangeTitleToMovie{

    Toolbar toolbar,toolbar2;
    ActionBar mActionBar;
    MovieData movieData;
    FragmentRecyclerView recyclerView =  FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        // mActionBar.setLogo(R.drawable.aboutmeicon);

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        toolbar2.inflateMenu(R.menu.activity2_bottom_toolbar);
        setupToolBarItemSelected();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container,  recyclerView)
                    .commit();
        } else {

        }

    }

    public void setupToolBarItemSelected()
    {
        toolbar2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id)
                {
                    case R.id.sortbyyear:
                        recyclerView.sortByYear();
                    default:
                        break;
                }
                return false;
            }
        });

        toolbar2.setNavigationIcon(R.drawable.ic_arrow_drop_down);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar2.setVisibility(View.GONE);
            }
        });
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
                .replace(R.id.main_container,FragmentDetails.newInstance(movie))
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
