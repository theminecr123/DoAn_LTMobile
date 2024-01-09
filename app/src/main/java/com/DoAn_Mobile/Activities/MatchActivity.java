package com.DoAn_Mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.DoAn_Mobile.Adapters.MatchAdapter;
import com.DoAn_Mobile.Authentication.User;
import com.DoAn_Mobile.Models.MatchItem;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        RecyclerView recyclerView = findViewById(R.id.rclMatch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MatchAdapter adapter = new MatchAdapter();
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        adapter.setOnItemClickListener(matchItem -> {
            // Here you'll handle the click event and navigate to the chat screen
            Intent chatIntent = new Intent(MatchActivity.this, ChatActivity.class);
            chatIntent.putExtra("matchedUserId", matchItem.getUserId()); // Now this method should be resolved
            startActivity(chatIntent);
        });

        db.collection("Matches").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot matchDocument : task.getResult()) {
                    Map<String, Object> uids = matchDocument.getData();
                    if (uids.containsKey(currentUserId)) {

                        uids.remove(currentUserId);
                        String matchedUserId = uids.keySet().iterator().next();

                        if (!uids.keySet().isEmpty()) {
                            // Truy vấn để lấy thông tin user
                            db.collection("users").document(matchedUserId).get().addOnSuccessListener(userDocument -> {
                                if (userDocument.exists()) {
                                    User matchedUser = userDocument.toObject(User.class);

                                    // Truy vấn để lấy last message từ sub-collection 'Messages'
                                    matchDocument.getReference().collection("Messages").orderBy("timestamp", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(messagesSnapshot -> {
                                        if (!messagesSnapshot.isEmpty()) {
                                            // Lấy thông tin last message
                                            DocumentSnapshot lastMessageDocument = messagesSnapshot.getDocuments().get(0);
                                            String lastMessage = lastMessageDocument.getString("message");
                                            Date timestamp = lastMessageDocument.getDate("timestamp");

                                            MatchItem matchItem = new MatchItem();
                                            matchItem.setUserId(matchedUserId);
                                            matchItem.setUsername(matchedUser.getName());
                                            matchItem.setLastMessage(lastMessage);
                                            // Format timestamp như mong muốn
                                            matchItem.setTimestamp(DateFormat.getDateTimeInstance().format(timestamp));
                                            matchItem.setAvatarUrl(matchedUser.getProfileImageUrl());

                                            // Thêm matchItem vào list và thông báo cho adapter
                                            adapter.addMatchItem(matchItem);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }).addOnFailureListener(e -> {
                                // Xử lý lỗi khi không lấy được thông tin người dùng
                            });
                        }
                    }
                }
            } else {
                // Xử lý lỗi khi không lấy được danh sách matches
            }
        });




    }
}