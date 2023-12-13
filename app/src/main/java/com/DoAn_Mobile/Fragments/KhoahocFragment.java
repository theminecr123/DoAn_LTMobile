package com.DoAn_Mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.DoAn_Mobile.Adapters.KhoaHocAdapter;
import com.DoAn_Mobile.R;

import java.util.ArrayList;

import com.DoAn_Mobile.Adapters.Model2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhoahocFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhoahocFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KhoahocFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment jobsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KhoahocFragment newInstance(String param1, String param2) {
        KhoahocFragment fragment = new KhoahocFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_khoahoc, container, false);

        recyclerView = view.findViewById(R.id.rlv);
        ArrayList<Model2> khoahoclist = new ArrayList<>();
        khoahoclist.add(new Model2(R.drawable.c,"Lập Trình C","Nguyễn Huy Cường"));
        khoahoclist.add(new Model2(R.drawable.py,"Lập Trình Python","Nguyễn Huy Cường"));
        khoahoclist.add(new Model2(R.drawable.java,"Lập Trình Java","Nguyễn Huy Cường"));
        khoahoclist.add(new Model2(R.drawable.android,"Lập Trình Android","Nguyễn Mạng Hùng"));
        khoahoclist.add(new Model2(R.drawable.html,"Lập Trình HTML & CSS","Nguyễn Hữu Trung"));
        khoahoclist.add(new Model2(R.drawable.js,"Lập Trình JavaScript","Nguyễn Hữu Trung"));
        khoahoclist.add(new Model2(R.drawable.react,"Lập Trình React","Nguyễn Huy Cường"));
        khoahoclist.add(new Model2(R.drawable.kotlin,"Lập Trình Kotlin","Nguyễn Hữu Trung"));


        KhoaHocAdapter khoahoccAdapter = new KhoaHocAdapter(khoahoclist);
        recyclerView.setAdapter(khoahoccAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}