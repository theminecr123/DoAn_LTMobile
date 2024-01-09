package com.DoAn_Mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.DoAn_Mobile.Adapters.FriendRequestAdapter;
import com.DoAn_Mobile.Models.FriendRequest;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendActivity extends AppCompatActivity implements FriendRequestAdapter.OnFriendRequestListener {
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<FriendRequest> friendRequests;
    private FirebaseFirestore db;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        recyclerView = findViewById(R.id.recyclerView_friend_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        friendRequests = new ArrayList<>();
        adapter = new FriendRequestAdapter(this, friendRequests, this);
        recyclerView.setAdapter(adapter);

        loadFriendRequests();
    }

    private void loadFriendRequests() {
        db.collection("friend_requests")
                .whereEqualTo("toUserId", currentUserId)
                .whereEqualTo("status", "sent")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        friendRequests.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            FriendRequest request = document.toObject(FriendRequest.class);
                            friendRequests.add(request);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(FriendActivity.this, "Error getting friend requests: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onAcceptClicked(FriendRequest request) {
        // Cập nhật trạng thái yêu cầu kết bạn thành "accepted"
        acceptFriendRequest(request);
    }

    @Override
    public void onDeclineClicked(FriendRequest request) {
        // Xóa yêu cầu kết bạn hoặc cập nhật trạng thái thành "declined"
        declineFriendRequest(request);
    }

    private void acceptFriendRequest(FriendRequest request) {
        // Thêm cả hai người dùng vào danh sách bạn bè của nhau
        db.collection("users").document(currentUserId)
                .collection("friends").document(request.getFromUserId())
                .set(new Object()) // Sử dụng object trống hoặc một object có dữ liệu bạn muốn lưu
                .addOnSuccessListener(aVoid -> {
                    // Làm tương tự với người dùng khác
                    db.collection("users").document(request.getFromUserId())
                            .collection("friends").document(currentUserId)
                            .set(new Object())
                            .addOnSuccessListener(aVoid2 -> {
                                // Xóa yêu cầu kết bạn sau khi đã xử lý
                                db.collection("friend_requests").document(request.getRequestId())
                                        .delete()
                                        .addOnSuccessListener(aVoid3 -> Toast.makeText(FriendActivity.this, "Friend request accepted", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(FriendActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            })
                            .addOnFailureListener(e -> Toast.makeText(FriendActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(FriendActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void declineFriendRequest(FriendRequest request) {
        db.collection("friend_requests").document(request.getRequestId())
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(FriendActivity.this, "Friend request declined", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(FriendActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}

