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
import java.util.concurrent.ThreadLocalRandom;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private SearchActivity.Restroom mRestroom = new SearchActivity.Restroom();
    private Context mContext;

    public RecyclerViewAdapter(Context context, SearchActivity.Restroom restroom) {
        mRestroom = new SearchActivity.Restroom(restroom);
        mContext = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.rank.setText(mRestroom.mRanks.get(position));
        holder.name.setText(mRestroom.mNames.get(position));
        holder.address.setText(mRestroom.mAddresses.get(position));
        holder.rating.setText(mRestroom.mRatings.get(position));


        final int red= ThreadLocalRandom.current().nextInt(155,250);
        final int green=ThreadLocalRandom.current().nextInt(155,250);
        final int blue=ThreadLocalRandom.current().nextInt(155,250);

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.rank.setBackground(draw);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GalleryActivity.class);

                intent.putExtra("name", mRestroom.mNames.get(position));
                intent.putExtra("addr", mRestroom.mAddresses.get(position));
                intent.putExtra("busy", mRestroom.busy.get(position));
                intent.putExtra("clean", mRestroom.clean.get(position));
                intent.putExtra("accessTlt", mRestroom.accessTlt.get(position));
                intent.putExtra("genInclus", mRestroom.genInclus.get(position));
                intent.putExtra("soap", mRestroom.soap.get(position));
                intent.putExtra("paper", mRestroom.paper.get(position));
                intent.putExtra("red", red);
                intent.putExtra("green", green);
                intent.putExtra("blue", blue);
                mContext.startActivity(intent);
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

            rank = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            rating = itemView.findViewById(R.id.rating);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }
}


