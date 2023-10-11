package com.example.simplemobilebanking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.simplemobilebanking.adapter.AdapterViewPager;
import com.example.simplemobilebanking.fragment.HomeFragment;
import com.example.simplemobilebanking.fragment.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity {
    BottomNavigationView botNavView;
    ViewPager2 viewPager;
    AdapterViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        viewPager = findViewById(R.id.pagerMain);
        ArrayList<Fragment> arr = new ArrayList<>();
        arr.add(new HomeFragment());
        arr.add(new ProfilFragment());
        adapter = new AdapterViewPager(this, arr);
        viewPager.setAdapter(adapter);

        botNavView = findViewById(R.id.nav_bot);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    botNavView.setSelectedItemId(R.id.home_bot);
                } else if (position == 1) {
                    botNavView.setSelectedItemId(R.id.profil_bot);
                }
            }
        });

        botNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home_bot) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.profil_bot) {
                    viewPager.setCurrentItem(1);
                    return true;
                }
                return false;
            }
        });
    }
}