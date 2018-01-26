package com.example.rajeshpalkar.sweatout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by rajeshpalkar on 7/29/17.
 */

public class MyFirebaseRecyclerViewAdapter extends FirebaseRecyclerAdapter<MovieDetails,MyFirebaseRecyclerViewAdapter.ViewHolder> {

    private List<Map<String, ?>> mDataset;
    private Context mContext;

    private static OnItemClickListener mItemClickListener;
    private static OnItemCheckListener mItemCheckListener;

    public MyFirebaseRecyclerViewAdapter(Class<MovieDetails> modelClass, int modelLayout,
                                         Class<ViewHolder> holder, DatabaseReference ref, Context myContext) {
        super(modelClass, modelLayout, holder, ref);
        mContext = myContext;
    }

    @Override
    protected void populateViewHolder(ViewHolder mViewHolder, MovieDetails movie, int positions) {

        //TODO: Populate viewHolder by setting the movie attributes to cardview fields

        String name = (String) movie.getName();
        String rating = (String) movie.getRating();

        String year = (String) movie.getYear();
        String nameAndRating = name ;/* + " [" + year + "]" + "\t\t\t" + String.valueOf(rating) + "/10";*/
        mViewHolder.vTitle.setText((String) nameAndRating);
        //movieViewHolder.description.setText((String) movie.get("description"));
        mViewHolder.vDescription.setText((String) movie.getDescription());
        Picasso.with(mContext).load((String) movie.getUrl()).into(mViewHolder.vIcon);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckbox;
        public ImageView vImageview;

        public ViewHolder(View v) {
            super(v);

            vIcon = (ImageView) v.findViewById(R.id.icon);
            vTitle = (TextView) v.findViewById(R.id.title);
            vDescription = (TextView) v.findViewById(R.id.description);
            vCheckbox = (CheckBox) v.findViewById(R.id.selection);
            vImageview = (ImageView) v.findViewById(R.id.overflow);

            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getAdapterPosition());

                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }
            });


            vCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (mItemCheckListener != null) {
                        mItemCheckListener.onItemCheck(buttonView, getAdapterPosition());
                    }

                }
            });

            if(vImageview!=null)
            {
                vImageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItemClickListener!=null)
                            mItemClickListener.onItemOverflowMenuClick(v,getAdapterPosition());
                    }
                });
            }
        }


/*
        public void bindMovieData(Map<String, ?> movie) {
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            vIcon.setImageResource((Integer) movie.get("image"));
            vCheckbox.setChecked((Boolean) movie.get("selection"));
        }*/

    }


  /*
    public MyFirebaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=null;
        switch(viewType)
        {
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_layout, parent, false);

                break;

            case 2:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_layout, parent, false);

                break;

            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_layout, parent, false);

                break;
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public int getItemViewType(int position)
    {
        if(position<6)
            return 1;
        else if(position>25)
            return 2;
        else
            return 0;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String, ?> movie = mDataset.get(position);
        holder.bindMovieData(movie);
    }
*/

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onItemOverflowMenuClick(View view, int position);
    }

    public interface OnItemCheckListener{
        public void onItemCheck(View view, int position);
    }

    public void setOnItemCheckListener(final OnItemCheckListener mItemCheckListener)
    {
        this.mItemCheckListener = mItemCheckListener;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

   // public int getItemCount() {return mDataset.size();}
}
