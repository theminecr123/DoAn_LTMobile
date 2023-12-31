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

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.WatchViewHolder> {

    ArrayList<Model2> khoahoclist = new ArrayList<>();

    public WatchAdapter(ArrayList<Model2> khoahoclist) {
        this.khoahoclist = khoahoclist;
    }

    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_layout,parent,false);
        return new WatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        holder.image.setImageResource(khoahoclist.get(position).image);
        holder.textView1.setText(khoahoclist.get(position).content);
        holder.textView2.setText(khoahoclist.get(position).content2);
    }

    @Override
    public int getItemCount() {
        return khoahoclist.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView textView1,textView2;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.circularImage);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
