package com.DoAn_Mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DoAn_Mobile.Models.Model;
import com.DoAn_Mobile.Models.Post;
import com.DoAn_Mobile.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder> {
    private List<Post> postList;
    // ...
    public MyPostAdapter() {
        postList = new ArrayList<>(); // Initialize postList
    }
    public class MyPostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;

        public MyPostViewHolder(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postImage);
        }
    }


    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new MyPostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {
        // Load and display the post image using Glide or your preferred image loading library
        Glide.with(holder.itemView.getContext())
                .load(postList.get(position).getImageUrl())
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0; // Ensure postList is not null
    }

    public void addPost(Post post) {
        if (postList != null) {
            postList.add(post);
            notifyItemInserted(postList.size() - 1);
        }
    }
}

