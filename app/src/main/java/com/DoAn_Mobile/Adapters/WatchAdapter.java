package com.DoAn_Mobile.Adapters;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.DoAn_Mobile.Authentication.User;
import com.DoAn_Mobile.Fragments.WatchFragment;
import com.DoAn_Mobile.Models.VideoInfo;
import com.DoAn_Mobile.R;
import com.bumptech.glide.Glide;


import java.util.List;


public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.WatchViewHolder> {



    private List<VideoInfo> videoList;


    public WatchAdapter(List<VideoInfo> videoList) {
        this.videoList = videoList;

    }
    public List<VideoInfo> getVideoList() {
        return videoList;
    }
    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch, parent,false);
        return new WatchAdapter.WatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchAdapter.WatchViewHolder holder, int position) {
        VideoInfo video = videoList.get(position);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder{
         VideoView videoView;
         ProgressBar progressBar;
        ProgressBar loadingProgressBar;
         SeekBar seekBar;
         String videoUrl;
         String videoTitle;
         String videoDescription;
         TextView timeTextView;
         ImageView playPauseIcon;
         boolean isVideoPlaying = false;
         Handler handler = new Handler();



        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
            seekBar = itemView.findViewById(R.id.seekBar);
            loadingProgressBar = itemView.findViewById(R.id.loadingProgressBar);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            playPauseIcon = itemView.findViewById(R.id.playPauseIcon);
            videoView = itemView.findViewById(R.id.videoView);
            seekBar = itemView.findViewById(R.id.seekBar);



        }

    }
}
