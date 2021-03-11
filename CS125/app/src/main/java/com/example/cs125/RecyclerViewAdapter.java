package com.example.cs125;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private SearchActivity.Restroom mRestroom = new SearchActivity.Restroom();
    private Context mContext;

    public RecyclerViewAdapter(Context context, SearchActivity.Restroom restroom) {
        mRestroom.mRanks= restroom.mRanks;
        mRestroom.mNames = restroom.mNames;
        mRestroom.mAddresses = restroom.mAddresses;
        mRestroom.mRatings = restroom.mRatings;
        mContext = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rank.setText(mRestroom.mRanks.get(position));
        holder.name.setText(mRestroom.mNames.get(position));
        holder.address.setText(mRestroom.mAddresses.get(position));
        holder.rating.setText(mRestroom.mRatings.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestroom.mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rank;
        TextView name;
        TextView address;
        TextView rating;

        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            Random r = new Random();
            int red=r.nextInt(253 - 0 + 1)+0;
            int green=r.nextInt(253 - 0 + 1)+0;
            int blue=r.nextInt(253 - 0 + 1)+0;

            GradientDrawable draw = new GradientDrawable();
            draw.setShape(GradientDrawable.OVAL);
            draw.setColor(Color.rgb(red,green,blue));


            rank = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            rating = itemView.findViewById(R.id.rating);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            rank.setBackground(draw);
        }
    }
}


