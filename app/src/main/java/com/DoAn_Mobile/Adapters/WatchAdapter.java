package com.DoAn_Mobile.Adapters;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.DoAn_Mobile.Models.VideoInfo;
import com.DoAn_Mobile.R;
import java.util.List;

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.WatchViewHolder> {
    private List<VideoInfo> videoList;
    private VideoView videoView;
    private ImageView playPauseIcon;
    boolean isVideoPlaying = false;
    ProgressBar loadingProgressBar;
    String videoUrl;
    String videoDescription;

    SeekBar seekBar;
    Handler handler = new Handler();

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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder{
        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            seekBar = itemView.findViewById(R.id.seekBar);
            loadingProgressBar = itemView.findViewById(R.id.loadingProgressBar);

            playPauseIcon = itemView.findViewById(R.id.playPauseIcon);
            videoView = itemView.findViewById(R.id.videoView);
            seekBar = itemView.findViewById(R.id.seekBar);

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        toggleVideoPlayback();
                    }
                    return true;
                }
            });
        }
        public void bind(int position) {
            // Gọi setupVideoView từ phương thức này
            setupVideoView(position);
        }

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
    private void setupVideoView(int position) {
        loadingProgressBar.setVisibility(View.VISIBLE);

        VideoInfo videoInfo = videoList.get(position);

        String videoUrl = videoInfo.getUrl();
        videoView.setVideoURI(Uri.parse(videoUrl));


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
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

                }
                handler.postDelayed(this, 100);
            }
        }, 100);
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
}
