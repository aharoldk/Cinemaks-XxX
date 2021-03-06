package com.aharoldk.iak_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aharoldk.iak_final.pojo.Movie.ResultsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_EXTRA_NEWS = "movies";
    private CoordinatorLayout main_content;
    private ResultsItem resultsItem;
    private ImageView ivShare;
    private ImageView ivFavourite;

    private String newsJson;

    private Boolean mLike = false;

    private FirebaseAuth mAuth;
    private DatabaseReference dfLike;

    private Snackbar snackBar;

    public DetailActivity() {
    }

    public static void start(Context context, String newsJson){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_NEWS, newsJson);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().hasExtra(KEY_EXTRA_NEWS)){
            newsJson = getIntent().getStringExtra(KEY_EXTRA_NEWS);
             resultsItem = new ResultsItem().fromJson(newsJson);

        } else {
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        dfLike = FirebaseDatabase.getInstance().getReference().child("likes");

        dfLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(mAuth.getCurrentUser().getUid()).hasChild(""+resultsItem.getId())) {
                    ivFavourite.setImageResource(R.drawable.ic_favorite_red_24px);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailActivity.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        setToolbar();
        declarate();
    }

    private void declarate() {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), newsJson);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ResultsItem resultsItem = new ResultsItem().fromJson(newsJson);
        getSupportActionBar().setTitle(""+resultsItem.getTitle());

        main_content = (CoordinatorLayout) findViewById(R.id.main_content);

        ivFavourite = (ImageView) findViewById(R.id.ivFavourite);
        ivShare = (ImageView) findViewById(R.id.ivShare);

        ivShare.setOnClickListener(this);
        ivFavourite.setOnClickListener(this);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.equals(ivFavourite)){
            mLike = true;

            if(mLike) {
                final String idDetail = String.valueOf(resultsItem.getId());
                final String titleDetail = resultsItem.getTitle();
                final String posterImage = resultsItem.getPosterPath();
                final String backgroundPath = resultsItem.getBackdropPath();
                final String date = resultsItem.getReleaseDate();
                final Double rate = resultsItem.getVoteAverage();
                final String language = resultsItem.getOriginalLanguage();
                final String overview = resultsItem.getOverview();

                dfLike.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild(""+resultsItem.getId())) {
                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .removeValue();

                            ivFavourite.setImageResource(R.drawable.ic_favorite_black_24px);

                            mLike = false;
                            Snackbar.make(main_content, "Remove from Favourite", Snackbar.LENGTH_LONG)
                                    .show();

                        } else {
                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("idDetail")
                                    .setValue(idDetail);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("titleDetail")
                                    .setValue(titleDetail);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("posterImage")
                                    .setValue(posterImage);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("backGroundPath")
                                    .setValue(backgroundPath);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("date")
                                    .setValue(date);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("rate")
                                    .setValue(rate);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("language")
                                    .setValue(language);

                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .child("overview")
                                    .setValue(overview);

                            ivFavourite.setImageResource(R.drawable.ic_favorite_red_24px);

                            mLike = false;

                            Snackbar.make(main_content, "Add New Favourite", Snackbar.LENGTH_LONG)
                                    .show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        } else if(view.equals(ivShare)){

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = ""+resultsItem.getTitle()+"\n\n\t\t\t\t"+resultsItem.getOverview()+"\n\nRelease Date : "+getdate(resultsItem.getReleaseDate())+"\nLanguange : "+resultsItem.getOriginalLanguage()+"\nRating : "+resultsItem.getVoteAverage()+"/10";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check Our Latest Movie in Cinemaks XxX");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

    }

    private String getdate(String dateRelease){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateConvert = null;

        try {
            Date date = simpleDateFormat.parse(dateRelease);
            DateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateConvert = finalFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();

        }

        return dateConvert;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        String json;

        private SectionsPagerAdapter(FragmentManager fm, String newsJson) {
            super(fm);
            json = newsJson;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: return DetailMovieFragment.newInstance(json);
                case 1: return TrailerMovieFragment.newInstance(json);

                default: return DetailMovieFragment.newInstance(json);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Detail";
                case 1:
                    return "Trailer";
            }
            return null;
        }

    }
}
