package com.aharoldk.iak_final;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aharoldk.iak_final.model.GenresItem;
import com.aharoldk.iak_final.model.Trailer;
import com.aharoldk.iak_final.pojo.ResultsItem;
import com.aharoldk.iak_final.service.APIClient;
import com.aharoldk.iak_final.service.APIInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerMovieFragment extends Fragment {

    private static final String API_KEY_YOUTUBE = "AIzaSyB8CaHocw7_BmM9LkRLpOPRWmq3tdsVDBk";
    private static final String API_KEY_MOVIE = "3ee47da55c8dae070eb764306712efc3";

    private TextView tvTagLine;
    private TextView tvGenres;
    private TextView tvPopularity;
    private TextView tvLink;

    private String video_id;

    private static final String VIDEOS = "videos";

    public static TrailerMovieFragment newInstance(String text) {
        TrailerMovieFragment f = new TrailerMovieFragment();
        Bundle b = new Bundle();
        b.putString("json", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_trailer_movie, container, false);

        tvTagLine = rootview.findViewById(R.id.tvTagLine);
        tvGenres = rootview.findViewById(R.id.tvGenres);
        tvPopularity = rootview.findViewById(R.id.tvPopularity);
        tvLink = rootview.findViewById(R.id.tvLink);

        String jsonString = getArguments().getString("json");
        ResultsItem resultsItem = new ResultsItem().fromJson(jsonString);
        apiClient(resultsItem);
        youtubeFragment();
        adMethod(rootview);

        return rootview;
    }

    private void adMethod(View rootview) {
        AdView mAdView = rootview.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    private void apiClient(ResultsItem resultsItem) {
        APIInterface apiInterface = APIClient.getTrailerClient().create(APIInterface.class);

        Call<Trailer> trailerCall = apiInterface.getAPITrailer(resultsItem.getId(), API_KEY_MOVIE, VIDEOS);

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
        final Trailer listTrailer = response.body();
        List<com.aharoldk.iak_final.model.ResultsItem> list = response.body().getVideos().getResults();
        List<GenresItem> listGenres = response.body().getGenres();
        video_id = list.get(0).getKey();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Genre : ");
        for (int i = 0; i < listGenres.size(); i++) {
            strBuilder.append(listGenres.get(i).getName());

            if(i+1 == listGenres.size()){
                strBuilder.append(".");
            } else {
                strBuilder.append(", ");
            }
        }

        String sGenres = strBuilder.toString();

        tvTagLine.setText(""+ listTrailer.getTagline());
        tvPopularity.setText("Popularity : "+ String.format("%.2f", listTrailer.getPopularity())+" | Vote Count : "+ listTrailer.getVoteCount());
        tvGenres.setText(sGenres);
        tvLink.setText(""+ listTrailer.getHomepage());

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(""+ listTrailer.getHomepage()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void youtubeFragment() {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.pause();
                    player.loadVideo(video_id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });
    }
}
