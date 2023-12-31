package com.DoAn_Mobile.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.DoAn_Mobile.MainActivity;
import com.DoAn_Mobile.R;
import com.DoAn_Mobile.UI.SplashActivity;
import com.DoAn_Mobile.databinding.ActivityWelcomeBinding;
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

import de.hdodenhof.circleimageview.CircleImageView;

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


        binding.btnChangeAVT.setOnClickListener(v -> openFileChooser());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        binding.btnNext.setOnClickListener(v -> {
            
            Map<String, Object> userUpdates = new HashMap<>();
            userUpdates.put("name", binding.username.getText().toString());
            userUpdates.put("gender", binding.genderSpinner.getSelectedItem().toString());
            userUpdates.put("active",true);

            DatabaseReference usersRef = database.getReference("users");
            usersRef.orderByChild("name").equalTo(binding.username.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.username.setError("Tên đã tồn tại!");

                            } else {
                                usersRef.child(uid).updateChildren(userUpdates)
                                        .addOnSuccessListener(aVoid -> {
                                            if (imageUri != null) {
                                                uploadImageToFirebase(imageUri);
                                            } else {
                                                // Xử lý trường hợp khi không có ảnh được chọn
                                            }
                                            Intent newIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                                            startActivity(newIntent);
                                            finish();

                                        })
                                        .addOnFailureListener(e -> {
                                        });                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        });


    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.imgAvatar.setImageURI(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = FirebaseStorage.getInstance().getReference("uploads").child(System.currentTimeMillis() + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Lưu URL này vào Firebase Realtime Database
                            saveImageUrlToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý lỗi
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/" + uid);
        Map<String, Object> updates = new HashMap<>();
        updates.put("profileImageUrl", imageUrl);
        databaseRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    // Xử lý khi cập nhật thành công
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi có lỗi
                });
    }

}