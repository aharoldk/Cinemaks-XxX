package com.aharoldk.iak_final;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlayingFragment extends Fragment {

    public static PlayingFragment newInstance() {
        return new PlayingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_playing, container, false);

        WebView mWebView = rootview.findViewById(R.id.wvCinema);
        mWebView.loadUrl("https://m.21cineplex.com/gui.list_theater.php?city_id=9");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

        return rootview;
    }

}
