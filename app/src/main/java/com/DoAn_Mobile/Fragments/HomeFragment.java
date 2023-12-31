package com.DoAn_Mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DoAn_Mobile.Adapters.HomeAdapter;
import com.DoAn_Mobile.Adapters.Model;
import com.DoAn_Mobile.Adapters.Post;
import com.DoAn_Mobile.Adapters.PostActivity;
import com.DoAn_Mobile.Adapters.PostAdapter;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerViewPosts;
    PostAdapter postAdapter;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewPosts = view.findViewById(R.id.recyclerview_posts);

        toolbar = view.findViewById(R.id.top_menu);

        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_post_image:
                    intent = new Intent(requireActivity(), PostActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("type", "picture");
                    startActivity(intent);
                    break;
                case R.id.nav_post_text:
                    intent = new Intent(requireActivity(), PostActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("type", "text");
                    startActivity(intent);
                    break;
//                case R.id.nav_chat:
//                    intent = new Intent(requireActivity(), ChatHomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    break;
            }
            return true;
        });

        postAdapter = new PostAdapter(requireContext());
        recyclerViewPosts.setHasFixedSize(true);
        recyclerViewPosts.setAdapter(postAdapter);
        readPosts();
        return view;
    }


    void readPosts() {
//        DocumentReference userReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
//        userReference.get().addOnSuccessListener(userSnapshot -> {
//            User user = userSnapshot.toObject(User.class);
//
//        });

//
//        CollectionReference feedReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("feed");
//        feedReference.get().addOnSuccessListener(feedSnapshots -> {
//            if (feedSnapshots.isEmpty()) {
//
//                return;
//            }
//            postAdapter.clearPosts();
//            for (DocumentSnapshot feedSnapshot : feedSnapshots) {
//                DocumentReference postReference = feedSnapshot.getDocumentReference("postReference");
//                boolean isVisited = Boolean.TRUE.equals(feedSnapshot.getBoolean("visited"));
//                assert postReference != null;
//                postReference.get().addOnSuccessListener(postSnapshot -> postAdapter.addPost(postSnapshot.toObject(Post.class)));
//            }
//        }).addOnFailureListener(e -> {
//
//            Log.e("Firestore Error", e.getMessage());
//
//        });

    }
}