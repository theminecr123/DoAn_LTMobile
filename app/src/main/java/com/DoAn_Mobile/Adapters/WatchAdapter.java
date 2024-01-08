package com.DoAn_Mobile.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.DoAn_Mobile.Fragments.WatchFragment;
import com.DoAn_Mobile.Models.VideoInfo;



import java.util.List;

public class WatchAdapter extends FragmentStateAdapter {

    private List<VideoInfo> videoList;


    public WatchAdapter(@NonNull FragmentActivity fragmentActivity, List<VideoInfo> videoList) {
        super(fragmentActivity);
        this.videoList = videoList;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        VideoInfo videoInfo = videoList.get(position);
        return WatchFragment.newInstance(videoInfo.getUrl(), videoInfo.getTitle(), videoInfo.getDescription());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
