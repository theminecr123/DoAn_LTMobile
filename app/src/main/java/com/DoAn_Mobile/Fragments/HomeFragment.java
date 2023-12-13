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
import com.DoAn_Mobile.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.rv);

        ArrayList<Model> homelist = new ArrayList<>();
        homelist.add(new Model(R.drawable.img_android,"THÀNH VIÊN NHÓM 03","  Lớp: 20DTHB2"));
        homelist.add(new Model(R.drawable.img_tien,"Huỳnh Vĩnh Tiến","  2011061897"));
        homelist.add(new Model(R.drawable.img_trieu,"Trần Lưu Đông Triều","  2011064067"));
        homelist.add(new Model(R.drawable.img_trung,"Nguyễn Quốc Trung","  2011061204"));
        homelist.add(new Model(R.drawable.img_quan,"Nguyễn Diệp Minh Quân","  2011062707"));



        HomeAdapter homeeAdapter = new HomeAdapter(homelist);
        recyclerView.setAdapter(homeeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}