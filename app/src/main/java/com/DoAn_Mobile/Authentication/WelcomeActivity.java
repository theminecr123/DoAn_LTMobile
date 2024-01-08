package com.DoAn_Mobile.Authentication;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.DoAn_Mobile.MainActivity;
import com.DoAn_Mobile.R;
import com.DoAn_Mobile.databinding.ActivityWelcomeBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReferences;
    private ActivityWelcomeBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderSpinner.setAdapter(adapter);


        loadUserData();

        binding.btnChangeAVT.setOnClickListener(v -> openFileChooser());

        binding.btnNext.setOnClickListener(v -> {

            if (isUserDataValid()) {
                updateUserInfo();
            }
        });
    }
    private void loadUserData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("name").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);

                    binding.username.setText(username);

                    if (gender != null) {
                        int genderPosition = getGenderPosition(gender);
                        if (genderPosition != -1) {
                            binding.genderSpinner.setSelection(genderPosition);
                        }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private int getGenderPosition(String gender) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            if (adapter.getItem(i).toString().equals(gender)) {
                return i;
            }
        }
        return -1;
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void updateUserInfo() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        String uid = mAuth.getCurrentUser().getUid();
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("name", binding.username.getText().toString());
        userUpdates.put("gender", binding.genderSpinner.getSelectedItem().toString());
        userUpdates.put("active", true);

        usersRef.child(uid).updateChildren(userUpdates)
                .addOnSuccessListener(aVoid -> {
                    if (imageUri != null) {
                        uploadImageToFirebase(imageUri);
                    } else {
                        startMainActivity();
                    }
                })
                .addOnFailureListener(e -> {

                });
    }
    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = FirebaseStorage.getInstance().getReference("uploads").child(System.currentTimeMillis() + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveImageUrlToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {

                    });
        }
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        Map<String, Object> updates = new HashMap<>();
        updates.put("imgProfile", imageUrl);

        databaseRef.child(uid).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    startMainActivity();
                })
                .addOnFailureListener(e -> {

                });
    }

    private void startMainActivity() {
        Intent newIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(newIntent);
        finish();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private boolean isUserDataValid() {

        String username = binding.username.getText().toString().trim();
        String gender = binding.genderSpinner.getSelectedItem().toString();

        if (username.isEmpty()) {
            binding.username.setError("Vui lòng nhập tên của bạn");
            return false;
        }

        if (gender.equals("Chọn giới tính")) {

            return false;
        }

        return true;
    }
}
