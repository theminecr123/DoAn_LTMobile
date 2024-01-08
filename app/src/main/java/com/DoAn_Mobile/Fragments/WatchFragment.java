package com.DoAn_Mobile.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
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

        View view = inflater.inflate(R.layout.watch_layout, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        seekBar = view.findViewById(R.id.seekBar);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        timeTextView = view.findViewById(R.id.timeTextView);
        playPauseIcon = view.findViewById(R.id.playPauseIcon);


        videoView = view.findViewById(R.id.videoView);
        seekBar = view.findViewById(R.id.seekBar);

        TextView titleTextView = view.findViewById(R.id.videoTitle);
        TextView descriptionTextView = view.findViewById(R.id.videoDescription);


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

            titleTextView.setText(videoTitle);
            descriptionTextView.setText(videoDescription);
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
        isVideoPlaying = !isVideoPlaying; // Đảo ngược trạng thái
    }


    private void setupVideoView() {
        loadingProgressBar.setVisibility(View.VISIBLE);

        videoUrl = "https://scontent.fsgn2-8.fna.fbcdn.net/v/t42.1790-2/10000000_889525049222572_2743062092036994499_n.mp4?_nc_cat=102&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=sNxEkgogVfAAX9iv8m0&tn=tC3GIEFrzqYfo1EY&_nc_rml=0&_nc_ht=scontent.fsgn2-8.fna&oh=00_AfAYP7XCxzTzFdHnoNMSPskd1bc0V2dM94XuGktm8cbsjQ&oe=65A0B7F2";
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
        videoList.add(new VideoInfo("https://scontent.fsgn2-4.fna.fbcdn.net/v/t42.1790-2/185365283_756162445060285_2963631205927964585_n.mp4?_nc_cat=101&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=x4czlqljXW8AX-7Uac_&_nc_rml=0&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfDELiX08oczpcmqhgTj2hdIT0kf8XCnUkO_9ljNYrq9WA&oe=657DF068", "Video 3", "Mô tả Video 3"));
        videoList.add(new VideoInfo("https://scontent.fsgn2-9.fna.fbcdn.net/v/t42.1790-2/409755617_1486017978909611_1664535265145000459_n.mp4?_nc_cat=103&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=pWNgB5dJqFsAX8G9sM1&tn=tC3GIEFrzqYfo1EY&_nc_rml=0&_nc_ht=scontent.fsgn2-9.fna&oh=00_AfBvtULHoCxpJVIiS1cQTldXecKpLIPdnTes6Uvmh1NCeA&oe=657D4661", "Video 3", "Mô tả Video 3"));
        videoList.add(new VideoInfo("https://scontent.fsgn2-11.fna.fbcdn.net/v/t42.1790-2/408881755_358735403370075_4480679822387902793_n.mp4?_nc_cat=105&ccb=1-7&_nc_sid=55d0d3&efg=eyJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCJ9&_nc_ohc=moiwiCQpnygAX8rtV8L&_nc_rml=0&_nc_ht=scontent.fsgn2-11.fna&oh=00_AfDLcvNZPAKG9pAAD1dQY6PGkd6JT8uV3uB3AO-S9VtOvg&oe=657D00F1", "Video 4", "Mô tả Video 4"));
        return videoList;
    }

}