package com.aharoldk.iak_final;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aharoldk.iak_final.adapter.HomeAdapter;
import com.aharoldk.iak_final.pojo.Movie.Home;
import com.aharoldk.iak_final.pojo.Movie.ResultsItem;
import com.aharoldk.iak_final.service.APIClient;
import com.aharoldk.iak_final.service.APIInterface;
import com.aharoldk.iak_final.service.DetailClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoonFragment extends Fragment implements DetailClickListener {
    private static final String TAG = "SoonFragment";

    private static final String API_KEY = "3ee47da55c8dae070eb764306712efc3";

    private HomeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<ResultsItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_soon, container, false);
        rootview.setTag(TAG);

        recyclerDeclarate(rootview);

        return rootview;
    }

    private void recyclerDeclarate(View rootview) {
        adapter = new HomeAdapter(list);
        RecyclerView recyclerView = rootview.findViewById(R.id.rvSoon);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(this);
        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);

        Call<Home> call = apiInterface.getAPITopRated(API_KEY);

        call.enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> call, Response<Home> response) {

                int code = response.code();

                if (code >= 200 && code < 300) {
                    Log.i("response", ""+response.body());

                    list = response.body().getResults();
                    adapter.setData(list);

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
            public void onFailure(Call<Home> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemDetailClicked(ResultsItem item) {
        DetailActivity.start(getActivity(), item.toJson());
    }

}
