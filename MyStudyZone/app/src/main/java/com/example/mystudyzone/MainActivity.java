package com.example.mystudyzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    private FloatingActionButton mFab;
    private BottomNavigationView bottomNavView;
    private Toolbar toolbar;
    private static boolean logged_in = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if(!logged_in){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            logged_in=true;
            startActivity(loginIntent);
        }*/


        //EDIT BY SHOURBAGY

        //END OF EDIT

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        bottomAppBar = (BottomAppBar) findViewById(R.id.bottomAppBar);
        bottomNavView = (BottomNavigationView) findViewById(R.id.BottomNavigationView);
        bottomNavView.setBackground(null);
        bottomNavView.getMenu().getItem(2).setEnabled(false);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        mFab = (FloatingActionButton) findViewById(R.id.main_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    String tag = "";
                    ActionBar actionbar = getSupportActionBar();
                    switch(item.getItemId()){
                        case R.id.action_home:
                            fragment = new HomeFragment();
                            tag = "home";
                            actionbar.setTitle("Home Page");
                            break;
                        case R.id.action_network:
                            fragment = new MyPairsFragment();
                            tag = "network";
                            actionbar.setTitle("My Pairs");
                            break;
                        case R.id.action_StudyMode:
                            fragment = new StudyFragment();
                            tag = "studyMode";
                            actionbar.setTitle("Study Mode");
                            break;
                        case R.id.action_planner:
                            fragment = new PlannerFragment();
                            tag = "planner";
                            actionbar.setTitle("Planner");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment, tag).commit();
                    return true;
                }
            };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.action_notification:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationFragment(), "notification").commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overflow_button, menu);
        return true;
    }
}