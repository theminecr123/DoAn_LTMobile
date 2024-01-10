package com.DoAn_Mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.DoAn_Mobile.Adapters.FriendRequestAdapter;
import com.DoAn_Mobile.Models.FriendRequest;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendRequestActivity extends AppCompatActivity implements FriendRequestAdapter.OnFriendRequestListener {
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<FriendRequest> friendRequests;
    private FirebaseFirestore db;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        recyclerView = findViewById(R.id.recyclerView_friend_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getUid();
        friendRequests = new ArrayList<>();

        // Khởi tạo adapter và đặt nó cho recyclerView
        adapter = new FriendRequestAdapter(this, friendRequests, this);
        recyclerView.setAdapter(adapter);

        loadFriendRequests();



    }

    private void loadFriendRequests() {
        db.collection("friend_requests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        friendRequests.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentId = document.getId();
                            if (documentId.contains(currentUserId)) {
                                String to = document.getString("to");
                                if (to != null && to.equals(currentUserId)) {
                                    String status = document.getString("status");
                                    String from = document.getString("from");
                                    if ("pending".equals(status)) {
                                        FriendRequest request = new FriendRequest();
                                        if (request != null) {
                                            request.setRequestId(documentId);
                                            request.setFromUserId(from);
                                            friendRequests.add(request);
                                        }
                                    }
                                }
                            }
                        }
                        // Thông báo cho adapter về sự thay đổi trong danh sách
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(FriendRequestActivity.this, "Error getting friend requests: " + task.getException(), Toast.LENGTH_SHORT).show();
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
        db.collection("friend_requests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        friendRequests.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentId = document.getId();
                            if (documentId.contains(currentUserId)) {
                                String to = document.getString("to");
                                if(to.equals(currentUserId)){
                                    document.getReference().update("status","accepted");
                                    db.collection("users").document(currentUserId)
                                            .collection("friends").document(request.getFromUserId())
                                            .set(new HashMap<>(), SetOptions.merge())
                                            .addOnSuccessListener(aVoid1 -> {
                                                // Làm tương tự với người dùng khác
                                                db.collection("users").document(request.getFromUserId())
                                                        .collection("friends").document(currentUserId)
                                                        .set(new HashMap<>(), SetOptions.merge())
                                                        .addOnSuccessListener(aVoid2 ->{
                                                            int position = friendRequests.indexOf(request);
                                                            if (position != -1) {
                                                                // Xoá FriendRequest khỏi danh sách
                                                                friendRequests.remove(position);

                                                                // Thông báo cho Adapter biết đã có sự thay đổi để cập nhật giao diện
                                                                adapter.notifyItemRemoved(position);
                                                            }
                                                        })
                                                        .addOnFailureListener(e ->
                                                                Toast.makeText(FriendRequestActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                            })
                                            .addOnFailureListener(e ->
                                                    Toast.makeText(FriendRequestActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());


                                    // Document chứa currentUserId
                                }



                            } else {
                                // Document không chứa currentUserId
                            }
                        }
                    } else {
                        Toast.makeText(FriendRequestActivity.this, "Error getting friend requests: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private void declineFriendRequest(FriendRequest request) {
        db.collection("friend_requests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        friendRequests.clear();  // Move this line outside of the loop
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentId = document.getId();
                            if (documentId.contains(currentUserId)) {
                                String to = document.getString("to");
                                if(to.equals(currentUserId)) {
                                    document.getReference().update("status","denied");
                                    int position = friendRequests.indexOf(request);
                                    if (position != -1) {
                                        // Xoá FriendRequest khỏi danh sách
                                        Log.d("FriendActivity", "Removing item at position: " + position);
                                        friendRequests.remove(position);
                                        adapter.notifyItemRemoved(position);
                                    }
                                }
                            } else {
                                // Document không chứa currentUserId
                            }
                        }
                    } else {
                        Toast.makeText(FriendRequestActivity.this, "Error getting friend requests: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}

