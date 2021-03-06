package com.aharoldk.iak_final;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aharoldk.iak_final.pojo.Movie.ResultsItem;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.blurry.Blurry;

public class DetailMovieFragment extends Fragment {
    private ImageView ivBackground;
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvLanguage;
    private RatingBar rbRate;
    private TextView tvOverview;

    private ResultsItem resultsItem;

    public static DetailMovieFragment newInstance(String text) {
        DetailMovieFragment f = new DetailMovieFragment();
        Bundle b = new Bundle();
        b.putString("json", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonString = getArguments().getString("json");
        resultsItem = new ResultsItem().fromJson(jsonString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        ivBackground = rootview.findViewById(R.id.ivBackground);
        ivPoster = rootview.findViewById(R.id.ivPoster);
        tvTitle = rootview.findViewById(R.id.tvTitle);
        tvDate = rootview.findViewById(R.id.tvDate);
        tvLanguage = rootview.findViewById(R.id.tvLanguange);
        rbRate = rootview.findViewById(R.id.ratingBar);
        tvOverview = rootview.findViewById(R.id.tvOverview);

        declarateContent(resultsItem);

        return rootview;
    }

    private void declarateContent(ResultsItem resultsItem) {
        Bitmap mIcon1 = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url_value = new URL("https://image.tmdb.org/t/p/w92"+resultsItem.getPosterPath());
            if (url_value != null) {
                mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Blurry.with(getContext())
                .radius(1)
                .sampling(1)
                .from(mIcon1)
                .into(ivBackground);

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w185"+resultsItem.getPosterPath())
                .into(ivPoster);

        tvTitle.setText(""+resultsItem.getTitle());
        tvDate.setText(" "+getdate(resultsItem.getReleaseDate())+"  |  ");
        tvLanguage.setText(" "+resultsItem.getOriginalLanguage());
        rbRate.setRating(Float.parseFloat(""+(resultsItem.getVoteAverage() / 2)));
        tvOverview.setText(""+resultsItem.getOverview());
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

}
