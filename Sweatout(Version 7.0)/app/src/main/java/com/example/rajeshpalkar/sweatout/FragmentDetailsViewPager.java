package com.example.rajeshpalkar.sweatout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

//import android.app.Fragment;

public class FragmentDetailsViewPager extends Fragment {

    HashMap movie;
    private int total =0,pos,cItem;
    String oExe;
    private ViewPager mViewPager;
    HashMap movie1;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ShareActionProvider shareActionProvider;

   // private ChangeTitleToMovie changeTitleToMovie;

    public FragmentDetailsViewPager() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetailsViewPager newInstance(HashMap<String,?> movie) {
        FragmentDetailsViewPager fragment = new FragmentDetailsViewPager();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentDetailsViewPager newInstance(int currentItem,int position) {
        System.out.println("movieee:"+currentItem);
        FragmentDetailsViewPager fragment = new FragmentDetailsViewPager();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, currentItem);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        changeTitleToMovie = (ChangeTitleToMovie) getContext();


    /*    if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        if(savedInstanceState!=null)
        {
            total = savedInstanceState.getInt("Total");
        }


        //MovieData m = new MovieData();
        cItem = getArguments().getInt(ARG_PARAM1);
        pos = getArguments().getInt(ARG_PARAM2);

    }

    public void onSavedInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }

/*
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.detailed_fragment_menu,menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");

        intentShare.putExtra(Intent.EXTRA_TEXT,(String)movie.get("name"));
        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu,inflater);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View rootView = inflater.inflate(R.layout.fragment_details_viewpager, container, false);


       // String mv1 =  (String) movie.get("id");
        System.out.println("mv111 "+oExe);
        System.out.println("pos in detailed:"+pos);
        //   String name = (String) movie2.getName();
        // System.out.println("mv222" +n);

        switch (cItem)
        {
            case 0:
                oExe = "BodyweightExe";
                break;
            case 1:
                oExe = "BattleropeExe";
                break;
            case 2:
                oExe = "BoxExe";
                break;
            case 3:
                oExe = "SuspensionExe";
                break;

        }

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        System.out.println("data ref :"+databaseReference);
        //DatabaseReference childref = databaseReference.child(mv1);
     //   System.out.println("child ref :"+childref);
     //   DatabaseReference childchild = childref.child((String)movie.get("name"));
      //  System.out.println("child child "+childchild);


        databaseReference.child(oExe).addValueEventListener(new ValueEventListener() {
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

                    }
                }

                // movie1 =  (HashMap<String, String>) dataSnapshot.getValue();
                //  movie1 = (HashMap<String, ?>) getItem(pos);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



/*        String mv =  (String) movie.get("name");
        TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
        t2.setText(mv);

    //    changeTitleToMovie.changeTitle(mv);

        String dir =  (String) movie.get("director");
        TextView t = (TextView) rootView.findViewById(R.id.textView5);
        t.setText(dir);

        String cast =  (String) movie.get("stars");
        TextView t1 = (TextView) rootView.findViewById(R.id.textView6);
        t1.setText(cast);

        String des =  (String) movie.get("description");
        TextView t3 = (TextView) rootView.findViewById(R.id.textView8);
        t3.setText("\nDescription : "+des);*/

/*

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView4);
        Picasso.with(getActivity()).load((String) movie.get("url")).into(imageView);
        imageView.setCropToPadding(true);

        RatingBar rb = (RatingBar) rootView.findViewById(R.id.ratingBar);
        String movieRating = (String) movie.get("rating");
        float halfMovieRating = Float.valueOf(movieRating)/2;
        rb.setRating(halfMovieRating);*/

/*        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mnestedFragmentListener.changeToParentFragment(position);
            }

            @Override
            public void onPageSelected(int position) {
                mnestedFragmentListener.changeToParentFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mnestedFragmentListener.changeToParentFragment(state);
            }
        });*/

        return rootView;
    }

    public interface nestedFragmentListener {
        public void changeToParentFragment(int position);
    }

}