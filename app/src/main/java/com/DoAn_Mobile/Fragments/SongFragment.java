package com.DoAn_Mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.DoAn_Mobile.Adapters.Model3;
import com.DoAn_Mobile.Adapters.SongAdapter;
import com.DoAn_Mobile.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;

    public SongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongFragment newInstance(String param1, String param2) {
        SongFragment fragment = new SongFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView = view.findViewById(R.id.rv);

        ArrayList<Model3> songlist = new ArrayList<>();
        songlist.add(new Model3(R.drawable.img_noinaycoanh,"Nơi Này Có Anh","Em là ai từ đâu bước đến nơi đây dịu dàng chân phương"));
        songlist.add(new Model3(R.drawable.img_chacaidoseve,"Chắc Ai Đó Sẽ Về","Anh tìm nỗi nhớ anh tìm quá khứ\n" +
                "Nhớ lắm kí ức anh và em"));
        songlist.add(new Model3(R.drawable.img_amthambenem,"Âm Thầm Bên Em","Khi bên anh em thấy điều chi?\n" +
                "Khi bên anh em thấy điều gì?"));
        songlist.add(new Model3(R.drawable.img_conmuangangqua,"Cơn Mưa Ngang Qua","Cơn mưa đi ngang qua\n" +
                "Đừng làm rơi thêm thêm thêm nhiều giọt lệ"));
        songlist.add(new Model3(R.drawable.img_emcuangayhomqua,"Em Của Ngày Hôm Qua","Liệu rằng chia tay trong em có quên được câu ca\n" +
                "Tình yêu khi xưa em trao cho anh đâu nào phôi pha"));
        songlist.add(new Model3(R.drawable.img_cochacyeuladay,"Có Chắc Yêu Là Đây","Thấp thoáng ánh mắt đôi môi mang theo hương mê say\n" +
                "Em cho anh tan trong miên man quên luôn đi đêm ngày"));
        songlist.add(new Model3(R.drawable.img_lactroi,"Lạc Trôi","Người theo hương hoa mây mù giăng lối\n" +
                "Làn sương khói phôi phai đưa bước ai xa rồi"));
        songlist.add(new Model3(R.drawable.img_haytraochoanh,"Hãy Trao Cho Anh","Hình bóng ai đó nhẹ nhàng vụt qua nơi đây\n" +
                "Quyến rũ ngây ngất loạn nhịp làm tim mê say"));

        SongAdapter songgAdapter = new SongAdapter(songlist);
        recyclerView.setAdapter(songgAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}