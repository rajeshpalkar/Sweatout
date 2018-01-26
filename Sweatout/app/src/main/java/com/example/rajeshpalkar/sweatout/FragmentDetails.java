package com.example.rajeshpalkar.sweatout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.app.Fragment;

public class FragmentDetails extends Fragment {

    HashMap movie,movie1;
    private int total =0,sizeList,pos;
    MovieData movieData;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ShareActionProvider shareActionProvider;

    private ChangeTitleToMovie changeTitleToMovie;

    private FragmentActivity myContext;
    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyAow3D-q_mMKAyTykF0SC3HF-Dn0JDD_6I";


    public FragmentDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(HashMap<String,?> movie) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentDetails newInstance(HashMap<String,?> movie,int position) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        args.putSerializable(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        changeTitleToMovie = (ChangeTitleToMovie) getContext();

    /*    if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        if(savedInstanceState!=null)
        {
            total = savedInstanceState.getInt("Total");
        }
   //     MovieData m = new MovieData();
   //     movie = (HashMap<String,?>) getArguments().getSerializable(ARG_PARAM1);
    }

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);
    }


    public void onSavedInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }

/*
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailed_fragment_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");

        String dataToSend = "Exercise Details: " + (String) movie.get("name") + "\nYoutube url" + (String) movie1.get("yturl");

        intentShare.putExtra(Intent.EXTRA_TEXT, dataToSend);

        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu,inflater);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      final  View rootView = inflater.inflate(R.layout.fragment_details, container, false);


      //  movieData = new MovieData();
     //   sizeList = movieData.getSize();

        if(getArguments() != null) {
            movie = (HashMap<String, ?>) getArguments().getSerializable(ARG_PARAM1);
            pos= getArguments().getInt(ARG_PARAM2);
        }


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YoutubeDeveloperKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    //  YPlayer.setFullscreen(true);
                    YPlayer.loadVideo((String)movie1.get("yturl"));
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });



     //   HashMap<String, ?> movie1 = (HashMap<String, ?>) movie;
      //  System.out.println("mv111 "+movie1);

        String mv1 =  (String) movie.get("id");
        System.out.println("mv111 "+mv1);
        System.out.println("pos in detailed:"+pos);
     //   String name = (String) movie2.getName();
       // System.out.println("mv222" +n);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        System.out.println("data ref :"+databaseReference);
        DatabaseReference childref = databaseReference.child(mv1);
        System.out.println("child ref :"+childref);
        DatabaseReference childchild = childref.child((String)movie.get("name"));
        System.out.println("child child "+childchild);

/*

      childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movie1 =  (HashMap<String, String>) dataSnapshot.getValue();
                System.out.println(movie1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


      /*childref.child("BackDips").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            //    System.out.println("Datasnap :"+dataSnapshot);
               movie1 =  (HashMap<String, String>) dataSnapshot.getValue();
                System.out.println("final movie :"+movie1.get("name"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        databaseReference.child(mv1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //    System.out.println("Datasnap :"+dataSnapshot);
               int cnt = (int) dataSnapshot.getChildrenCount();
               // String ch = (String) dataSnapshot.child("id").getValue(String.class);
                System.out.println("cnt "+cnt);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                   String cmp = (String) child.child("id").getValue(String.class);
                    System.out.println("child value: "+cmp);
                    String spos = String.valueOf(pos);
                    System.out.println("spos:"+spos);
                    System.out.println("compare:"+cmp.equals(spos));
                   if(cmp.equals(spos)){
                       movie1 =  (HashMap<String, String>) child.getValue();
                        System.out.println("final movie :" + movie1.get("name"));
                     //  break;

                       ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView4);
                       Picasso.with(getActivity()).load((String) movie1.get("url")).into(imageView);
                       imageView.setCropToPadding(true);

                       String mov =  (String) movie1.get("name");
                       TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
                       t2.setText(mov);

                       String des =  (String) movie1.get("description");
                       TextView t3 = (TextView) rootView.findViewById(R.id.textView8);
                       t3.setText("\nDescription : "+des);

                    }
                }

               // movie1 =  (HashMap<String, String>) dataSnapshot.getValue();
                //  movie1 = (HashMap<String, ?>) getItem(pos);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        String mv =  (String) movie.get("name");
       // TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
        //t2.setText(mv);

        changeTitleToMovie.changeTitle(mv);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsingToolbar);
        // ctl.setTitleEnabled(false);
        ctl.setTitle(mv);

        String dir =  (String) movie.get("director");
        TextView t = (TextView) rootView.findViewById(R.id.textView5);
        t.setText(dir);

        String cast =  (String) movie.get("stars");
        TextView t1 = (TextView) rootView.findViewById(R.id.textView6);
        t1.setText(cast);

        /*String des =  (String) movie.get("description");
        TextView t3 = (TextView) rootView.findViewById(R.id.textView8);
        t3.setText("\nDescription : "+des);*/

        ImageView imageView1 = (ImageView) rootView.findViewById(R.id.toolbarImage);
        Picasso.with(getActivity()).load((String) movie.get("url")).into(imageView1);
        imageView1.setCropToPadding(true);

        /*ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView4);
        Picasso.with(getActivity()).load((String) movie.get("url")).into(imageView);
        imageView.setCropToPadding(true);*/

        RatingBar rb = (RatingBar) rootView.findViewById(R.id.ratingBar);
        String movieRating = (String) movie.get("rating");
        float halfMovieRating = Float.valueOf(movieRating)/2;
        rb.setRating(halfMovieRating);

        return rootView;
    }

    public interface ChangeTitleToMovie {
        public void changeTitle(String title);
    }



}