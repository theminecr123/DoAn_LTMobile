package com.DoAn_Mobile.Adapters;

import android.media.MediaPlayer;
import android.net.Uri;
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
    ProgressBar loadingProgressBar;
    String videoUrl;
    String videoDescription;
    TextView tvDescription;
    SeekBar seekBar;
    Handler handler = new Handler();
    private boolean isPaused = false;

    public WatchAdapter(List<VideoInfo> videoList) {
        this.videoList = videoList;

    }
    public List<VideoInfo> getVideoList() {
        return videoList;
    }
    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch, parent, false);
        WatchViewHolder viewHolder = new WatchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WatchAdapter.WatchViewHolder holder, int position) {
        VideoInfo video = videoList.get(position);
        holder.bind(position, video);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder{

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            loadingProgressBar = itemView.findViewById(R.id.loadingProgressBar);
            playPauseIcon = itemView.findViewById(R.id.playPauseIcon);
            videoView = itemView.findViewById(R.id.videoView);
            seekBar = itemView.findViewById(R.id.seekBar);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }
        public void bind(int position, VideoInfo video) {
            if (position >= 0 && position < videoList.size()) {
                setupVideoView(position, video);
            }
        }
    }


    private void setupVideoView(int position, VideoInfo videoInfo) {

        if (videoList != null && !videoList.isEmpty() && position >= 0 && position < videoList.size()) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            videoInfo = videoList.get(position);

            videoDescription = videoInfo.getDescription();
            tvDescription.setText(videoDescription);
            videoUrl = videoInfo.getUrl();
            videoView.setVideoURI(Uri.parse(videoUrl));

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        toggleVideoPlayback();
                    }
                    return true;
                }
            });

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
    }
    private void toggleVideoPlayback() {
        if (isPaused) {
            videoView.start();
        } else {
            videoView.pause();
        }
        isPaused = !isPaused;
        updatePlayPauseIcon();
    }
    private void updatePlayPauseIcon() {
        if (isPaused) {
            playPauseIcon.setVisibility(View.VISIBLE);
        } else {
            playPauseIcon.setVisibility(View.GONE);
        }
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
        videoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView.isPlaying()) {
                    int currentPosition = videoView.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                }
                videoView.postDelayed(this, 100);
            }
        }, 100);
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
