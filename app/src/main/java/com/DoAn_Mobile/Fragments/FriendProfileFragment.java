package com.DoAn_Mobile.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.DoAn_Mobile.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendProfileFragment extends Fragment {

    private CircleImageView profileImage;
    private TextView usernameTextView;
    private TextView statusTextView;
    private TextView friendTextView;
    private TextView descriptionTextView;
    private Button followButton;
    private RecyclerView recyclerViewPosts;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        usernameTextView = view.findViewById(R.id.username_text_view);
        statusTextView = view.findViewById(R.id.status);
        friendTextView = view.findViewById(R.id.followers);
        descriptionTextView = view.findViewById(R.id.description);
        followButton = view.findViewById(R.id.follow_button);
        recyclerViewPosts = view.findViewById(R.id.recycler_view_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPosts.setLayoutManager(layoutManager);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}