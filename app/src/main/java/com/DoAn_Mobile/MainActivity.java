package com.DoAn_Mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.DoAn_Mobile.Adapters.VpagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView navigationView;
    ViewPager pager2;
    FrameLayout layout;
    Button button;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

        });

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.BottomNav);
        pager2 = findViewById(R.id.pager2);
        layout = findViewById(R.id.fm);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Social Media");

        VpagerAdapter adapter = new VpagerAdapter(getSupportFragmentManager());
        pager2.setAdapter(adapter);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        pager2.setCurrentItem(0);
                        break;
                    case R.id.KhoaHoc:
                        pager2.setCurrentItem(1);
                        break;
                    case R.id.Song:
                        pager2.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });


        pager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    navigationView.getMenu().findItem(R.id.Home).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.KhoaHoc).setChecked(true);// setchecked will highlight the menu
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.Song).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}