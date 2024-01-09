package com.DoAn_Mobile.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DoAn_Mobile.Adapters.WatchAdapter;
import com.DoAn_Mobile.Models.VideoInfo;
import com.DoAn_Mobile.R;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchFragment extends Fragment {

    public WatchFragment() {
        // Required empty public constructor
    }

    public static WatchFragment newInstance() {  // Xóa các tham số url và description
        WatchFragment fragment = new WatchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);

        List<VideoInfo> videoList = createVideoList();

        WatchAdapter adapter = new WatchAdapter(createVideoList());
        viewPager2.setAdapter(adapter);

        return view;
    }

    private List<VideoInfo> createVideoList() {

        List<VideoInfo> videoList = new ArrayList<>();
        videoList.add(new VideoInfo("https://scontent.fsgn2-7.fna.fbcdn.net/v/t42.1790-2/414814228_340362632187643_8354208551586456545_n.mp4?_nc_cat=108&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=XKbPt0JeFSYAX8w8u6m&_nc_rml=0&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfCB6C0Yctf_IuesAjs9VjxzXJzqUNVPsS0H0yJtd9Qt-A&oe=65A196F5",  "Mô tả Video 1"));
        videoList.add(new VideoInfo("https://scontent.fsgn2-7.fna.fbcdn.net/v/t42.1790-2/369881772_1266211517416761_6939712292969977045_n.mp4?_nc_cat=108&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=VyCglhwjVxgAX-2yU1T&_nc_rml=0&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfDFPXTb4FfEddoluiZ1DbAn3YISMFHwbyR5i0t7TRZ3AQ&oe=65A200AF", "Mô tả Video 2"));
        videoList.add(new VideoInfo("https://scontent.fsgn2-10.fna.fbcdn.net/v/t42.1790-2/10000000_305137215603116_7597645409816507625_n.mp4?_nc_cat=109&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=TY16TDqP8MsAX_2IPKO&_nc_rml=0&_nc_ht=scontent.fsgn2-10.fna&oh=00_AfB0DUrd7Nw-mGznhsfg1tue1Cg3j8qCcKkgvbn3J7qf8w&oe=65A09935", "Mô tả Video 3"));
        videoList.add(new VideoInfo("https://scontent.fsgn2-7.fna.fbcdn.net/v/t42.1790-2/416464061_755481743141667_2535886987224370643_n.mp4?_nc_cat=108&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=JyZm_97JeIoAX_Xi3tO&_nc_rml=0&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfBr2beGB1al4rRdL_EbFGBLe8xTMNdzHphP8o5nmzFbYQ&oe=65A205FE", "Mô tả Video 4"));
        //videoList.add(new VideoInfo(" ", "Mô tả Video 3"));
        return videoList;
    }

}