package com.aharoldk.iak_final;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class TheaterFragment extends Fragment {
    ProgressBar mProgressBar;
    WebView mWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_playing, container, false);

        mWebView = rootview.findViewById(R.id.wvCinema);
        mWebView.clearCache(true);
        mWebView.clearView();
        mWebView.reload();
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }

        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl("https://m.21cineplex.com/gui.list_theater.php?city_id=9");

        return rootview;
    }

}
