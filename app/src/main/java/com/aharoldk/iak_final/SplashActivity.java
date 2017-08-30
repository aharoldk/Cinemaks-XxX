package com.aharoldk.iak_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private Activity activity = this;
    private ImageView ivTranstion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ivTranstion = (ImageView) findViewById(R.id.ivTranstion);

        int splashInterval = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, ivTranstion, "profile").toBundle();
                startActivity(intent, options);
                finish();
            }
        }, splashInterval);
    }
}
