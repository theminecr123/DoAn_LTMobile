//package com.DoAn_Mobile.Adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.DoAn_Mobile.R;
//
//import java.util.ArrayList;
//
//public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
//
//
//    public ProfileAdapter(ArrayList<Model2> khoahoclist) {
//        this.khoahoclist = khoahoclist;
//    }
//
//    @NonNull
//    @Override
//    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_layout,parent,false);
//        return new ProfileAdapter.ProfileViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return khoahoclist.size();
//    }
//
//    public class ProfileViewHolder extends RecyclerView.ViewHolder{
//
//        ImageView image;
//        TextView textView1,textView2;
//
//        public ProfileViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            image = itemView.findViewById(R.id.circularImage);
//            textView1 = itemView.findViewById(R.id.textView1);
//            textView2 = itemView.findViewById(R.id.textView2);
//        }
//    }
//}
