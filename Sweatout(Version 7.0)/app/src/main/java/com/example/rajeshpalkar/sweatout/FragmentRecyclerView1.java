package com.example.rajeshpalkar.sweatout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;

//import android.support.v7.view.ActionMode;

//import android.app.Fragment;


public class FragmentRecyclerView1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private int total =0,sizeList;
    HashMap movie;
    private DatabaseReference myRef;


    private OnExerciseListItemSelectedListener mListenerExercise;

    private RecyclerView mRecyclerView;
    MovieData movieData = new MovieData();


    LinearLayoutManager mLayoutManager;
    MyFirebaseRecyclerViewAdapter myFirebaseRecyclerViewAdapter;

    public FragmentRecyclerView1() {
        // Required empty public constructor
    }


    public static FragmentRecyclerView1 newInstance(HashMap<String,?> movie) {
        System.out.println("FR1 newInstance: "+movie.get("id"));
        FragmentRecyclerView1 fragment = new FragmentRecyclerView1();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mListenerExercise = (OnExerciseListItemSelectedListener) getContext();


        if(savedInstanceState!=null)
        {
            total = savedInstanceState.getInt("Total");
        }
    }


    public void onSavedInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        String abc = getActivity().getLocalClassName();
        //System.out.println(abc);

        if(abc.equals("Task1Activity") )
        {
            if(menu.findItem(R.id.actionSearch)==null) {
                inflater.inflate(R.menu.search_view, menu);

                SearchView search = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
                if (search != null) {
                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            int position = movieData.findMovie(query);
                            if (position >= 0)
                                mRecyclerView.scrollToPosition(position);

                            return true;

                        }

                        @Override
                        public boolean onQueryTextChange(String query) {
                            return true;
                        }
                    });
                }
            }

        }

/*
             if(menu.findItem(R.id.sortbyname)==null)
            inflater.inflate(R.menu.activity2_top_toolbar, menu);

*/

        super.onCreateOptionsMenu(menu,inflater);

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.sortbyname:
                sortByName();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  movieData = new MovieData();
       // sizeList = movieData.getSize();

        if(getArguments()!=null)
        {
            movie = (HashMap<String,?>)getArguments().getSerializable(ARG_PARAM1);
        }

        String mv = (String) movie.get("id");

        final View rootView = inflater.inflate(R.layout.fragment_recycler_view,container,false);

        myRef = FirebaseDatabase.getInstance().getReference().child(mv).getRef();
        System.out.println("myreff: "+myRef);

        myFirebaseRecyclerViewAdapter = new MyFirebaseRecyclerViewAdapter(MovieDetails.class,R.layout.card_layout1,
                MyFirebaseRecyclerViewAdapter.ViewHolder.class,myRef,getContext());

        movieData = new MovieData();


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        //mLayoutManager = new GridLayoutManager(getActivity(),2);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(myFirebaseRecyclerViewAdapter);
        myFirebaseRecyclerViewAdapter.notifyDataSetChanged();
        if (movieData.getSize() == 0) {
            movieData.setAdapter(myFirebaseRecyclerViewAdapter);
            //getApplicationContext()-activity is used movieData.initializeDataFromCloud();
            movieData.setContext(getActivity());
            movieData.initializeDataFromCloud();
        }


        myFirebaseRecyclerViewAdapter.setOnItemClickListener(new MyFirebaseRecyclerViewAdapter.OnItemClickListener(){
            public void onItemClick( View v,final int position)
            {
              //  Toast.makeText(getActivity(), "inadshjashbdhska", Toast.LENGTH_SHORT).show();
            //  final  HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                System.out.println("pos:"+position);
                 String id = (String) movie.get("id");
                System.out.println("id:"+ id);
               // DatabaseReference ref = movieData.getFireBaseRef();
                myRef.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //HashMap<String, String> movie =  (HashMap<String, String>) dataSnapshot.getValue();
                        System.out.println("movie :"+movie);
                        mListenerExercise.onExerciseListItemSelected(position,movie);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("My Test", "The read failed: " + databaseError.getMessage());
                    }
                });
                // HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(position);

            }

            public void onItemLongClick(View v, int position)
            {
                getActivity().startActionMode(new ActionBarCallBack(position));

            }

            public void onItemOverflowMenuClick(View v, final int position)
            {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        HashMap movie;
                        switch (item.getItemId())
                        {
                            case R.id.delete:
                                movie = (HashMap) ((HashMap) movieData.getItem(position));
                                movieData.removeItemFromServer(movie);
                                //   mRecyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                            case R.id.duplicate:
                                movie = (HashMap) ((HashMap) movieData.getItem(position)).clone();
                                movie.put("id", ((String) movie.get("id") + "_new"));
                                movieData.addItemToServer(movie);
                                //  mRecyclerViewAdapter.notifyItemInserted(position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.contextual_menu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

        myFirebaseRecyclerViewAdapter.setOnItemCheckListener(new MyFirebaseRecyclerViewAdapter.OnItemCheckListener(){
            public void onItemCheck(View v ,int position){
                HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(position);
                CheckBox  vCheckbox = (CheckBox) v.findViewById(R.id.selection);
                if(vCheckbox.isChecked()) {
                    item.put("selection", true);
                }
                else {
                    item.put("selection",true);
                }

            }
        });

        String title = "Movie List";
        //  mListenerExercise.changeTileToDefault(title);

        itemAnimation();
        adapterAnimation();
        return rootView;
    }


    private void itemAnimation()
    {

        FadeInRightAnimator fadeInRightAnimator = new FadeInRightAnimator();
        fadeInRightAnimator.setInterpolator(new OvershootInterpolator());

        fadeInRightAnimator.setAddDuration(800);
        fadeInRightAnimator.setRemoveDuration(800);

        mRecyclerView.setItemAnimator(fadeInRightAnimator);

    }

    public void adapterAnimation()
    {
        ScaleInAnimationAdapter Adapter = new ScaleInAnimationAdapter(myFirebaseRecyclerViewAdapter);
        Adapter.setDuration(400);
        mRecyclerView.setAdapter(Adapter);

    }


    public interface OnExerciseListItemSelectedListener {
        public void onExerciseListItemSelected(int position, HashMap<String, ?> movie);

    }


    public void sortByYear()
    {

        Collections.sort(movieData.moviesList, new Comparator<Map<String, ?>>() {
            @Override
            public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                return o2.get("year").toString().compareTo(o1.get("year").toString());
            }
        });

       // mRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void sortByName()
    {
        Collections.sort(movieData.moviesList, new Comparator<Map<String, ?>>() {
            @Override
            public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                return o1.get("name").toString().compareTo(o2.get("name").toString());
            }
        });

      //  mRecyclerViewAdapter.notifyDataSetChanged();
    }

    class ActionBarCallBack implements ActionMode.Callback {
        int position;

        public ActionBarCallBack(int position) {
            this.position = position;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            HashMap movie;
            switch (id) {
                case R.id.delete:
                    movie = (HashMap) ((HashMap) movieData.getItem(position));
                    movieData.removeItemFromServer(movie);
                    //     mRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.duplicate:
                    movie = (HashMap) ((HashMap) movieData.getItem(position)).clone();
                    movie.put("id", ((String) movie.get("id") + "_new"));
                    movieData.addItemToServer(movie);
                    //     mRecyclerViewAdapter.notifyItemInserted(position);
                    mode.finish();
                    break;
                default:
                    break;
            }
            return true;
        }


        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
            return true;
        }

        public void onDestroyActionMode(ActionMode mode){

        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu){
            return false;
        }
    }
}
