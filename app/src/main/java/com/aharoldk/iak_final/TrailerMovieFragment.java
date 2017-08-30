package com.aharoldk.iak_final;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aharoldk.iak_final.pojo.Movie.ResultsItem;
import com.aharoldk.iak_final.pojo.Review.Review;
import com.aharoldk.iak_final.pojo.Trailer.Trailer;
import com.aharoldk.iak_final.service.APIClient;
import com.aharoldk.iak_final.service.APIInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import jp.wasabeef.blurry.Blurry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerMovieFragment extends Fragment {

    private static final String API_KEY_YOUTUBE = "AIzaSyB8CaHocw7_BmM9LkRLpOPRWmq3tdsVDBk";
    private static final String API_KEY_MOVIE = "3ee47da55c8dae070eb764306712efc3";
    private static final String LANGUANGE = "en-US";

    private ExpandableTextView expandableTextView;
    private ImageView ivBackground;
    private TextView tvReview;

    private String video_id;

    private Boolean fullScreen = false;

    private ResultsItem resultsItem;

    public static TrailerMovieFragment newInstance(String text) {
        TrailerMovieFragment f = new TrailerMovieFragment();
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

        apiMovie(resultsItem);
        apiReview(resultsItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_trailer_movie, container, false);

        expandableTextView = rootview.findViewById(R.id.expand_text_view);
        ivBackground = rootview.findViewById(R.id.ivBackground);
        blurbackground(resultsItem);
        return rootview;
    }

    private void blurbackground(ResultsItem resultsItem) {
        Bitmap mIcon1 = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url_value = new URL("https://image.tmdb.org/t/p/w92"+resultsItem.getBackdropPath());
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
    }

    private void apiReview(ResultsItem resultsItem) {
        APIInterface apiReview = APIClient.getApiClient().create(APIInterface.class);

        Call<Review> reviewCall = apiReview.getAPIReview(resultsItem.getId(), API_KEY_MOVIE, LANGUANGE);

        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                int code = response.code();

                Log.i("response", ""+response.body());

                if (code >= 200 && code < 300) {
                    dataReview(response);

                } else if (code == 401) {
                    Toast.makeText(getActivity(), "Error "+code+" : Response Unauthenticated", Toast.LENGTH_SHORT).show();

                } else if (code >= 400 && code < 500) {
                    Toast.makeText(getActivity(), "Error "+code+" : Response Client Error", Toast.LENGTH_SHORT).show();

                } else if (code >= 500 && code < 600) {
                    Toast.makeText(getActivity(), "Error "+code+" : Response Server Error", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Error "+code+" : Unexpected Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });


    }

    private void dataReview(Response<Review> response) {
        List<com.aharoldk.iak_final.pojo.Review.ResultsItem> list = response.body().getResults();

        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            strBuilder.append("Author : ").append(list.get(i).getAuthor()).append("\n");
            strBuilder.append("Content : ").append(list.get(i).getContent()).append("\n\n");

        }

        String sReview = strBuilder.toString();

        expandableTextView.setText(sReview);
    }

    private void apiMovie(ResultsItem resultsItem) {
        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);

        Call<Trailer> trailerCall = apiInterface.getAPITrailer(resultsItem.getId(), API_KEY_MOVIE, LANGUANGE);

        trailerCall.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                int code = response.code();

                Log.i("response", ""+response.body());

                if (code >= 200 && code < 300) {
                    dataThere(response);

                } else if (code == 401) {
                    Toast.makeText(getActivity(), "Error "+code+" : Response Unauthenticated", Toast.LENGTH_SHORT).show();

                } else if (code >= 400 && code < 500) {
                    Toast.makeText(getActivity(), "Error "+code+" : Response Client Error", Toast.LENGTH_SHORT).show();

                } else if (code >= 500 && code < 600) {
                    Toast.makeText(getActivity(), "Error "+code+" : Response Server Error", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Error "+code+" : Unexpected Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void dataThere(Response<Trailer> response) {
        List<com.aharoldk.iak_final.pojo.Trailer.ResultsItem> list = response.body().getResults();

        if(list.isEmpty()){
            video_id = "notfound";
        } else {
            video_id = list.get(0).getKey();
        }

        youtubeFragment();

    }

    private void youtubeFragment() {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.pause();
                    player.setFullscreen(false);
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.cueVideo(TrailerMovieFragment.this.video_id);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = error.toString();
                Log.d("errorMessage:", errorMessage);
            }
        });
    }

}
