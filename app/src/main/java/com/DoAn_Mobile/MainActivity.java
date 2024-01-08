package com.DoAn_Mobile;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.DoAn_Mobile.Adapters.WatchAdapter;
import com.DoAn_Mobile.Authentication.LoginActivity;
import com.DoAn_Mobile.Models.VideoInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.DoAn_Mobile.Adapters.VpagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    ViewPager2 pager2;
    Button button;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        });

        navigationView = findViewById(R.id.BottomNav);
        pager2 = findViewById(R.id.pager2);

        VpagerAdapter adapter = new VpagerAdapter(this);
        pager2.setAdapter(adapter);

        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Home:
                    pager2.setCurrentItem(0, true);
                    return true;
                case R.id.Watch:
                    pager2.setCurrentItem(1, true);
                    return true;
                case R.id.Find:
                    pager2.setCurrentItem(2, true);
                    return true;
                case R.id.Profile:
                    pager2.setCurrentItem(3, true);
                    return true;
            }
            return false;
        });



        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    navigationView.setSelectedItemId(R.id.Home);
                } else if (position == 1) {
                    navigationView.setSelectedItemId(R.id.Watch);
                } else if (position == 2) {
                    navigationView.setSelectedItemId(R.id.Find);
                } else if (position == 3) {
                    navigationView.setSelectedItemId(R.id.Profile);
                }
            }
        });
    }
}