package com.aharoldk.iak_final;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentTransaction transaction = manager.beginTransaction();

    private boolean doubleBackToExitPressedOnce = false;

    private FirebaseAuth firebaseAuth;

    private SwipeRefreshLayout swipeRefreshLayout;

    private int i = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, new HomeFragment(), "HomeFragment");
                    transaction.commit();
                    return true;

                case R.id.navigation_dashboard:
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, new SoonFragment(), "SoonFragment");
                    transaction.commit();
                    return true;

                case R.id.navigation_theater:
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, new TheaterFragment(), "TheaterFragment");
                    transaction.commit();
                    return true;

                case R.id.navigation_notifications:
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, new FavouriteFragment(), "FavouriteFragment");
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.textPrimary,
                R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        transaction.add(R.id.content, new HomeFragment(), "HomeFragment");
        transaction.commit();

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                firebaseAuth.signOut();
                getWindow().setExitTransition(new Explode());
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));

            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        i++;
        if(i == 1){
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                i = 0;
            }
        }, 3000);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setEnabled(false);

        Fragment frg = getSupportFragmentManager().findFragmentById(R.id.content);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();

        onLoaded();
    }

    private void onLoaded() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
    }

}
