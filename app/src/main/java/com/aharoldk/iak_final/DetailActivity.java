package com.aharoldk.iak_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

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
    private ResultsItem resultsItem;
    private ImageView ivShare;
    private ImageView ivFavourite;

    private String newsJson;

    private Boolean mLike = false;

    private FirebaseAuth mAuth;
    private DatabaseReference dfLike;

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

                dfLike.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild(""+resultsItem.getId())) {
                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .removeValue();

                            ivFavourite.setImageResource(R.drawable.ic_favorite_black_24px);

                            mLike = false;

                        } else {
                            dfLike.child(mAuth.getCurrentUser().getUid())
                                    .child(idDetail)
                                    .setValue(titleDetail);

                            ivFavourite.setImageResource(R.drawable.ic_favorite_red_24px);

                            mLike = false;

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
                case 3: return DetailMovieFragment.newInstance(json);

                default: return DetailMovieFragment.newInstance(json);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Detail";
                case 1:
                    return "Trailer";
                case 2:
                    return "Review";
            }
            return null;
        }

    }
}
