package com.DoAn_Mobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.DoAn_Mobile.Adapters.ChatAdapter;
import com.DoAn_Mobile.Models.Message;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private Button buttonSend;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private FirebaseFirestore db;

    private String currentUserId, matchedUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList, currentUserId);
        recyclerViewChat.setAdapter(chatAdapter);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        matchedUserId = getIntent().getStringExtra("matchedUserId");

        buttonSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString();
            if (!messageText.isEmpty()) {
                sendMessage(messageText);
                editTextMessage.setText("");
            }
        });

        loadMessages();
    }

    private void loadMessages() {
        // Assuming a 'messages' collection in Firestore with fields: 'senderId', 'receiverId', 'message', 'timestamp'
        db.collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .whereEqualTo("receiverId", matchedUserId)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("ChatActivity", "Listen failed.", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Message message = dc.getDocument().toObject(Message.class);
                                messageList.add(message);
                            }
                        }

                        chatAdapter.notifyDataSetChanged();
                        recyclerViewChat.scrollToPosition(messageList.size() - 1);
                    }
                });
    }


    private void sendMessage(String messageText) {
        Message newMessage = new Message(currentUserId, matchedUserId, messageText, new Date());
        db.collection("messages").add(newMessage)
                .addOnSuccessListener(documentReference -> Log.d("ChatActivity", "Message sent successfully"))
                .addOnFailureListener(e -> Log.w("ChatActivity", "Error sending message", e));
    }

}