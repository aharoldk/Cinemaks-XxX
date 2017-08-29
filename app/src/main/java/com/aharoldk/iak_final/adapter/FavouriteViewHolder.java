package com.aharoldk.iak_final.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aharoldk.iak_final.R;
import com.squareup.picasso.Picasso;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    private View mView;

    public FavouriteViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTitle(String title){
        TextView titleDetail = mView.findViewById(R.id.tvTitle);
        titleDetail.setText(title);
    }

    public void setBackGround(String image){
        ImageView postImage = mView.findViewById(R.id.ivBackground);
        Picasso.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342"+image)
                .into(postImage);
    }
}
