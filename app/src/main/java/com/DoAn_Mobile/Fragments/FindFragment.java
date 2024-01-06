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
import android.widget.Toast;


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
import java.util.Objects;

public class FindFragment extends Fragment {


    ViewPager2 viewPager;
    double currentUserLatitude, currentUserLongitude;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 mét
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 phút
    private static final int PAGE_SIZE = 10; // Ví dụ: Tải 10 người dùng mỗi lần
    private int currentPageStart = 0;
    private FirebaseAuth mAuth;


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
        mAuth = FirebaseAuth.getInstance();

        setupLocationManagerAndListener();

        return view;
    }
    private void setupLocationManagerAndListener() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        requestLocationPermission();
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
        } catch (SecurityException e) {
            // Xử lý trường hợp không có quyền truy cập vị trí
        }
    }

    //Phân trang 10 người 1 lần
    private void fetchUsersAndUpdateViewPager() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.orderByKey().startAt(String.valueOf(currentPageStart))
                .limitToFirst(PAGE_SIZE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<User> newUserList = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                newUserList.add(user);
                            }

                            updateAdapterData(newUserList);
                            currentPageStart += PAGE_SIZE; // Chuẩn bị vị trí bắt đầu cho trang tiếp theo
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi
                    }
                });
    }
    private void updateAdapterData(List<User> newUserList) {
        FindAdapter adapter = (FindAdapter) viewPager.getAdapter();
        if (adapter != null) {
            List<User> existingUsers = adapter.getUserList();

            for (User user : newUserList) {
                if (user.getLocation() != null && !Objects.equals(user.getId(), mAuth.getUid())) {
                    existingUsers.add(user); // Chỉ thêm người dùng có trường location
                }
            }

            adapter.notifyDataSetChanged();
        } else {
            // Lọc và tạo danh sách chỉ bao gồm những người dùng có trường location
            List<User> usersWithLocation = new ArrayList<>();
            for (User user : newUserList) {
                if (user.getLocation() != null && !Objects.equals(user.getId(), mAuth.getUid())) {
                    usersWithLocation.add(user);
                }
            }

            adapter = new FindAdapter(usersWithLocation, currentUserLatitude, currentUserLongitude);
            viewPager.setAdapter(adapter);
        }
    }



    private void updateLocationInFirebase(double latitude, double longitude) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        com.DoAn_Mobile.Authentication.Location location = new com.DoAn_Mobile.Authentication.Location(latitude, longitude);
        databaseReference.child("location").setValue(location);
    }
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

}