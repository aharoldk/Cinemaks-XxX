package com.aharoldk.iak_final;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aharoldk.iak_final.pojo.ResultsItem;


public class TicketMovieFragment extends Fragment {

    public static TicketMovieFragment newInstance(String text) {
        TicketMovieFragment f = new TicketMovieFragment();
        Bundle b = new Bundle();
        b.putString("json", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ticket_movie_fragmenet, container, false);

        String jsonString = getArguments().getString("json");
        ResultsItem resultsItem = new ResultsItem().fromJson(jsonString);

        return rootview;
    }

}
