package com.DoAn_Mobile.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;


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

    private VideoView videoView;
    private ProgressBar progressBar;
    ProgressBar loadingProgressBar;
    private SeekBar seekBar;
    private String videoUrl;
    private String videoTitle;
    private String videoDescription;
    private TextView timeTextView;
    private ImageView playPauseIcon;
    private boolean isVideoPlaying = false;
    private Handler handler = new Handler();

    ViewPager2 viewPager2;


    public WatchFragment() {
        // Required empty public constructor
    }

    public static WatchFragment newInstance(String url, String title, String description) {
        WatchFragment fragment = new WatchFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        args.putString("description", description);
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

        View view = inflater.inflate(R.layout.item_watch, container, false);
        //progressBar = view.findViewById(R.id.progressBar);
        seekBar = view.findViewById(R.id.seekBar);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        //timeTextView = view.findViewById(R.id.timeTextView);
        playPauseIcon = view.findViewById(R.id.playPauseIcon);

        //viewPager2 = view.findViewById(R.id.);


        videoView = view.findViewById(R.id.videoView);
        seekBar = view.findViewById(R.id.seekBar);

//        TextView titleTextView = view.findViewById(R.id.videoTitle);
//        TextView descriptionTextView = view.findViewById(R.id.videoDescription);
//
//
//        List<VideoInfo> videoList = createVideoList();
//        WatchAdapter adapter = new WatchAdapter(getActivity(), videoList);
//        viewPager2.setAdapter(adapter);


        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    toggleVideoPlayback();
                }
                return true;
            }
        });

        if (getArguments() != null) {
            videoUrl = getArguments().getString("url");
            videoTitle = getArguments().getString("title");
            videoDescription = getArguments().getString("description");

//            titleTextView.setText(videoTitle);
//            descriptionTextView.setText(videoDescription);
        }

        setupVideoView();
        return view;
    }



    private void toggleVideoPlayback() {
        if (videoView.isPlaying()) {
            if (videoView.canPause()) {
                videoView.pause();
                playPauseIcon.setImageResource(R.drawable.ic_play);
                playPauseIcon.setVisibility(View.VISIBLE);
            }
        } else {
            videoView.start();
            playPauseIcon.setVisibility(View.GONE);
        }
        isVideoPlaying = !isVideoPlaying;
    }


    private void setupVideoView() {
        loadingProgressBar.setVisibility(View.VISIBLE);

        //videoUrl = "https://scontent.fsgn2-8.fna.fbcdn.net/v/t42.1790-2/10000000_889525049222572_2743062092036994499_n.mp4?_nc_cat=102&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=sNxEkgogVfAAX9iv8m0&tn=tC3GIEFrzqYfo1EY&_nc_rml=0&_nc_ht=scontent.fsgn2-8.fna&oh=00_AfAYP7XCxzTzFdHnoNMSPskd1bc0V2dM94XuGktm8cbsjQ&oe=65A0B7F2";
        videoView.setVideoURI(Uri.parse(videoUrl));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progressBar.setVisibility(View.GONE);
                loadingProgressBar.setVisibility(View.GONE);
                setupSeekBar();

                mp.setLooping(true);
                videoView.start();
                startUpdatingProgressBar();

                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        playPauseIcon.setVisibility(View.GONE);
                        mediaPlayer.start();
                    }
                });
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    progressBar.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }


    private void setupSeekBar() {
        seekBar.setMax(videoView.getDuration());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(videoView.getDuration());
                videoView.start();
                startUpdatingProgressBar();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopUpdatingProgressBar();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopUpdatingProgressBar();
                return false;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
                updateTimeTextView();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdatingProgressBar();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startUpdatingProgressBar();
            }

        });
    }

    private void startUpdatingProgressBar() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView.isPlaying()) {
                    int currentPosition = videoView.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    updateTimeTextView();
                }
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    private void updateTimeTextView() {
        int currentPosition = videoView.getCurrentPosition();
        int duration = videoView.getDuration();

        String currentPositionStr = formatTime(currentPosition);
        String durationStr = formatTime(duration);

        String timeText = currentPositionStr + " / " + durationStr;
        timeTextView.setText(timeText);
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void stopUpdatingProgressBar() {
        handler.removeCallbacks(updateProgressBarTask);
    }

    private Runnable updateProgressBarTask = new Runnable() {
        @Override
        public void run() {
            if (videoView.isPlaying()) {
                int currentPosition = videoView.getCurrentPosition();
                seekBar.setProgress(currentPosition);
            }
            handler.postDelayed(this, 100);
        }
    };
    private List<VideoInfo> createVideoList() {

        List<VideoInfo> videoList = new ArrayList<>();
        videoList.add(new VideoInfo("https://scontent.fsgn2-7.fna.fbcdn.net/v/t42.1790-2/414814228_340362632187643_8354208551586456545_n.mp4?_nc_cat=108&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=XKbPt0JeFSYAX8w8u6m&_nc_rml=0&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfCB6C0Yctf_IuesAjs9VjxzXJzqUNVPsS0H0yJtd9Qt-A&oe=65A196F5", "Video 1", "Mô tả Video 1"));
        videoList.add(new VideoInfo("https://mbasic.facebook.com/video_redirect/?src=https%3A%2F%2Fscontent.fsgn2-7.fna.fbcdn.net%2Fv%2Ft42.1790-2%2F416867649_345164178465637_2589608992327700614_n.mp4%3F_nc_cat%3D100%26ccb%3D1-7%26_nc_sid%3D55d0d3%26efg%3DeyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9%26_nc_ohc%3D3m0YDLjwi3kAX8qh8GJ%26_nc_rml%3D0%26_nc_ht%3Dscontent.fsgn2-7.fna%26oh%3D00_AfBfzNjM6wxO8S2_xsZ_gU-pvW3qKV67gV47QyaiSyLKSw%26oe%3D65A1025D&source=misc&id=6862444223853462&noredirect=0&watermark=0&__tn__=FH&paipv=0&eav=AfbJ5k9wcvHK8bOoBAFcmyYKMLkIn5pLyY4WMSwiylwnQgTGAGDslDb6hj_2AP2afdo", "Video 2", "Mô tả Video 2"));
        //videoList.add(new VideoInfo(" ", "Video 3", "Mô tả Video 3"));

        return videoList;
    }

}