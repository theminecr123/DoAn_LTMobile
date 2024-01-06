package com.DoAn_Mobile.Fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.DoAn_Mobile.Adapters.FindAdapter;
import com.DoAn_Mobile.Authentication.User;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {


    ViewPager2 viewPager;
    double currentUserLatitude, currentUserLongitude;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public FindFragment() {
        // Required empty public constructor
    }


    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        viewPager = view.findViewById(R.id.pagerFind);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        setupLocationManagerAndListener();
        requestLocationPermission();

        return view;
    }
    private void setupLocationManagerAndListener() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentUserLatitude = location.getLatitude();
                currentUserLongitude = location.getLongitude();
                updateLocationInFirebase(currentUserLatitude, currentUserLongitude);
                fetchUsersAndUpdateViewPager();
            }

            // ... (xử lý các phương thức khác của LocationListener)
        };
    }
    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                // Quyền bị từ chối, xử lý tình huống này
            }
        }
    }

    private void getCurrentLocation() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            // Xử lý trường hợp không có quyền truy cập vị trí
        }
    }

    private void fetchUsersAndUpdateViewPager() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }

                FindAdapter adapter = new FindAdapter(userList, currentUserLatitude, currentUserLongitude);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi
            }
        });
    }

    private void updateLocationInFirebase(double latitude, double longitude) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        com.DoAn_Mobile.Authentication.Location location = new com.DoAn_Mobile.Authentication.Location(latitude, longitude);
        databaseReference.child("location").setValue(location);
    }
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

}