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

public class KhoaHocAdapter extends RecyclerView.Adapter<KhoaHocAdapter.KhoaHocViewHolder> {

    ArrayList<Model2> khoahoclist = new ArrayList<>();

    public KhoaHocAdapter(ArrayList<Model2> khoahoclist) {
        this.khoahoclist = khoahoclist;
    }

    @NonNull
    @Override
    public KhoaHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.khoahoc_layout,parent,false);
        return new KhoaHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhoaHocViewHolder holder, int position) {
        holder.image.setImageResource(khoahoclist.get(position).image);
        holder.textView1.setText(khoahoclist.get(position).content);
        holder.textView2.setText(khoahoclist.get(position).content2);
    }

    @Override
    public int getItemCount() {
        return khoahoclist.size();
    }

    public class KhoaHocViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView textView1,textView2;

        public KhoaHocViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.circularImage);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
