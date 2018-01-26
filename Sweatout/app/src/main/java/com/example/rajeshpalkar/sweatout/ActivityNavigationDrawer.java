package com.example.rajeshpalkar.sweatout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class ActivityNavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }
        };


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer){

                    @Override
                    public void onDrawerClosed(View drawerView)
                    {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView)
                    {
                        super.onDrawerOpened(drawerView);
                    }
                };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview))
//                    .commit();
//        } else {
//
//        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WelcomeFragment())
                    .commit();
        } else {

        }



    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }


    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.item1:
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, new AboutMe()).addToBackStack(null)
                        .commit();
                break;

            case R.id.item2:
                intent = new Intent(this,Task1Activity.class);
                startActivity(intent);
                break;

            case R.id.item3:
                intent = new Intent(this,Task2Activity.class);
                startActivity(intent);
                break;
            case R.id.item4:
                intent = new Intent(this,Task3Activity.class);
                startActivity(intent);
                break;
            case R.id.item5:
                intent = new Intent(this,Photos.class);
                startActivity(intent);
                break;
            case R.id.item6:
                intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.item7:
                intent = new Intent(this,ReminderActivity.class);
                startActivity(intent);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}


