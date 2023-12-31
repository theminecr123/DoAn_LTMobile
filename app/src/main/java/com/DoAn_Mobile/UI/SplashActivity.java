package com.DoAn_Mobile.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.DoAn_Mobile.Authentication.LoginActivity;
import com.DoAn_Mobile.Authentication.WelcomeActivity;
import com.DoAn_Mobile.MainActivity;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        String uid = mAuth.getUid(); // Thay thế với UID thực tế của người dùng
        DatabaseReference isActiveRef = usersRef.child(uid).child("active");

        isActiveRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Boolean isActive = dataSnapshot.getValue(Boolean.class);

                    if(isActive == false){
                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = getIntent();
                                if (intent != null) {
                                    String sourceActivity = intent.getStringExtra("source_activity");
                                    if (sourceActivity != null && sourceActivity.equals("toMain")) {
                                        Intent newIntent = new Intent(SplashActivity.this, MainActivity.class);
                                        startActivity(newIntent);
                                        finish();
                                    }
                                }
                            }
                        }, 1000);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi đọc dữ liệu
            }
        });

    }
}