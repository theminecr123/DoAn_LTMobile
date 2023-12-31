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

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.FindViewHolder>  {

    ArrayList<Model3> songList = new ArrayList<>();

    public FindAdapter(ArrayList<Model3> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_layout, parent,false);
        return new FindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindViewHolder holder, int position) {

        holder.image.setImageResource(songList.get(position).image);
        holder.textView1.setText(songList.get(position).content);
        holder.textview2.setText(songList.get(position).Salary);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class FindViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView textView1,textview2;

        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circularImage);
            textView1 = itemView.findViewById(R.id.textView1);
            textview2 = itemView.findViewById(R.id.textView);
        }

    }
}
