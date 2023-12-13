package com.DoAn_Mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.DoAn_Mobile.R;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>  {

    ArrayList<Model3> songList = new ArrayList<>();

    public SongAdapter(ArrayList<Model3> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_layout, parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {

        holder.image.setImageResource(songList.get(position).image);
        holder.textView1.setText(songList.get(position).content);
        holder.textview2.setText(songList.get(position).Salary);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView textView1,textview2;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circularImage);
            textView1 = itemView.findViewById(R.id.textView1);
            textview2 = itemView.findViewById(R.id.textView);
        }

    }
}
