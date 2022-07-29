package org.icddrb.standard_v3;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import Common.Connection;
import Common.ProjectSetting;
import Utility.MySharedPreferences;

public class Fragment_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Connection C;
    MySharedPreferences sp;
    Fragment selectedFragment = null;
    NavigationView nav_view;
    Context mContext;

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        mContext = this;

        nav_view = findViewById(R.id.nav_view);

        Activity_Load();
    }

    boolean networkAvailable = false;
    private void Activity_Load()
    {
        C = new Connection(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.bringToFront();
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.color_white));

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawers();

                /*switch (item.getItemId()) {

                    case R.id.nav_10:
                        setTransaction(AboutUsFragment.newInstance());
                        return true;
                    case R.id.nav_11:
                        setTransaction(FeedbackFragment.newInstance());
                        return true;
                    default:
                        return true;
                }*/
                return true;
            }
        });

        BottomNavigationView bottom_navView = findViewById(R.id.bottom_nav_view);
        FloatingActionButton floView = findViewById(R.id.fab);
        floView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*selectedFragment =  HomeFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_bottom_navigation_mainactivity, selectedFragment);
                transaction.commit();*/
            }
        });

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        if(ProjectSetting.Show_Bottom_Navigation_Bar) {
            bottomAppBar.setVisibility(View.VISIBLE);
            floView.setVisibility(View.VISIBLE);
        }else {
            bottomAppBar.setVisibility(View.GONE);
            floView.setVisibility(View.GONE);
        }
        if(ProjectSetting.Show_Floating_Button_Navigation_Bar) {
            floView.setVisibility(View.VISIBLE);
        }else {
            floView.setVisibility(View.GONE);
        }

        bottom_navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*switch (item.getItemId()) {
                    case R.id.action_one:
                        selectedFragment =  LandingPageFragment.newInstance();
                        break;
                    case R.id.action_four:
                        selectedFragment =  HomeFragment2.newInstance();
                        break;
                    case R.id.action_two:
                        selectedFragment =  SurveyListFragment.newInstance();
                        break;
                    case R.id.action_three:
                        selectedFragment =  QuickStatListFragment.newInstance();
                        break;
                    case R.id.action_more:
                        selectedFragment =  MoreFragment.newInstance();
                        break;
                    default:
                        selectedFragment =  LandingPageFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_bottom_navigation_mainactivity, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
                return true;
            }
        });
        selectedFragment =  Fragment_Home.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_bottom_navigation_mainactivity, selectedFragment);
        transaction.commit();
    }

    private void setTransaction(Fragment fragment){
        try
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_bottom_navigation_mainactivity, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
        catch(Exception ex)
        {
            Connection.MessageBox(mContext, ex.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}