package com.aharoldk.iak_final;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aharoldk.iak_final.adapter.FavouriteViewHolder;
import com.aharoldk.iak_final.pojo.Favourite.Favourite;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FavouriteFragment extends Fragment {

    private RecyclerView rvFavourite;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    Query query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Double Click To Open", Toast.LENGTH_SHORT).show();
        View rootview = inflater.inflate(R.layout.fragment_favourite, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth == null){
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        };

        databaseReference = FirebaseDatabase.getInstance().getReference().child("likes").child(firebaseAuth.getCurrentUser().getUid());

        rvFavourite = rootview.findViewById(R.id.rvFavourite);

        rvFavourite.setLayoutManager(new LinearLayoutManager(getContext()));


        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);

        FirebaseRecyclerAdapter<Favourite, FavouriteViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Favourite, FavouriteViewHolder>(
                Favourite.class,
                R.layout.row_fragment_favourite,
                FavouriteViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(FavouriteViewHolder viewHolder, final Favourite model, int position) {
                viewHolder.setTitle(model.getTitleDetail());
                viewHolder.setBackGround(model.getBackGroundPath());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        View mView = getActivity().getLayoutInflater().inflate(R.layout.detail_favourite, null);

                        ImageView ivPoster = mView.findViewById(R.id.ivPoster);
                        TextView tvTitle = mView.findViewById(R.id.tvTitle);
                        RatingBar ratingBar = mView.findViewById(R.id.ratingBar);
                        TextView tvOverview = mView.findViewById(R.id.tvOverview);

                        Picasso.with(getContext())
                                .load("https://image.tmdb.org/t/p/w185"+model.getPosterImage())
                                .into(ivPoster);

                        tvTitle.setText(""+model.getTitleDetail());
                        ratingBar.setRating(Float.parseFloat(""+(model.getRate() / 2)));
                        tvOverview.setText(""+model.getOverview());

                        builder.setCancelable(true);
                        builder.setView(mView);
                        builder.show();
                    }
                });
            }
        };

        recyclerAdapter.notifyDataSetChanged();
        rvFavourite.setAdapter(recyclerAdapter);
    }

}
