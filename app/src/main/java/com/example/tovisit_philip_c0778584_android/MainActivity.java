package com.example.tovisit_philip_c0778584_android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tovisit_philip_c0778584_android.DashboardFragment;
import com.example.tovisit_philip_c0778584_android.DistanceFragment;
import com.example.tovisit_philip_c0778584_android.MapFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Added first fragment as Home
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction mFragTrans = manager.beginTransaction();
        mFragTrans.add(R.id.container, new DashboardFragment());
        getSupportActionBar().setTitle("My List");
        mFragTrans.commit();
        setUpNavigationDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            } else {
                finish();
            }
        }
    }

    public void setUpNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction mFragTrans = manager.beginTransaction();
                getSupportActionBar().setTitle(menuItem.getTitle().toString());
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        mFragTrans.replace(R.id.container, new DashboardFragment());
                        break;
                    case R.id.nav_search:
                        mFragTrans.replace(R.id.container, new MapFragment());
                        break;
                    case R.id.nav_distance:
                        mFragTrans.replace(R.id.container, new DistanceFragment());
                        break;
                }
                mFragTrans.commit();
                drawer.closeDrawers();
                return true;
            }
        });
    }

    public void hideKeyboard() {
        try {
            InputMethodManager manager = (InputMethodManager) this.getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            View currentFocusedView = this.getCurrentFocus();
            if (currentFocusedView != null) {
                manager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}