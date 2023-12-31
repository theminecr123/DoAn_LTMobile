package com.DoAn_Mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DoAn_Mobile.Adapters.HomeAdapter;
import com.DoAn_Mobile.Adapters.Model;
import com.DoAn_Mobile.Adapters.ProfileAdapter;
import com.DoAn_Mobile.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.rv);




//        ProfileAdapter profileAdapter = new ProfileAdapter(homelist);
//        recyclerView.setAdapter(profileAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}