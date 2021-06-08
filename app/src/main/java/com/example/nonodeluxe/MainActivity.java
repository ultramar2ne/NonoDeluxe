package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.nonodeluxe.fragment.CalendarFragment;
import com.example.nonodeluxe.fragment.MainEmpFragment;
import com.example.nonodeluxe.fragment.MainAdminFragment;
import com.example.nonodeluxe.fragment.MainStoreFragment;
import com.example.nonodeluxe.fragment.MainYangFragment;
import com.example.nonodeluxe.fragment.PrdListFragment;
import com.example.nonodeluxe.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static int currentStoreCode = 0;
    Fragment HomeFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        switch (Preferences.getString(this,"grade")){
            case "admin":
                HomeFragment = new MainAdminFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MainAdminFragment()).commit();
                break;
            case "emp":
                HomeFragment = new MainEmpFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MainEmpFragment()).commit();
                break;
            case "partic":
                HomeFragment = new MainEmpFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MainStoreFragment()).commit();
                bottomNavigationView.setVisibility(View.GONE);
                break;
            case "yang":
                HomeFragment = new MainYangFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new MainYangFragment()).commit();
            default:
                break;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = HomeFragment;
                            break;

                        case R.id.nav_prd:
                            selectedFragment = new PrdListFragment();
                            break;

                        case R.id.nav_calendar:
                            selectedFragment = new CalendarFragment();
                            break;

                        case R.id.nav_menu:
                            selectedFragment = new SettingFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}