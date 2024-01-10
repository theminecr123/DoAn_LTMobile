package com.DoAn_Mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.DoAn_Mobile.Models.VideoInfo;
import com.DoAn_Mobile.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddvideoActivity extends AppCompatActivity {
    EditText url, noidung;
    Button them, huy;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    CollectionReference videoRef;
    private static final int PICK_VIDEO_REQUEST = 1;
    private Button btnPickVideo;
    private PlayerView videoPreview;
    private SimpleExoPlayer exoPlayer;
    private StorageReference storageRef;
    private boolean isUsingUrl = false;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvideo);

        FirebaseStorage storage = FirebaseStorage.getInstance();
         storageRef = storage.getReference();
        initView();
        firebaseAuth = FirebaseAuth.getInstance();
        initControll();
        db = FirebaseFirestore.getInstance();
        videoRef = db.collection("videos");
        btnPickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideoPicker();
            }
        });
    }
    private void openVideoPicker() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn video"), PICK_VIDEO_REQUEST);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();// Giải phóng nguồn lực của ExoPlayer khi Activity bị hủy
    }
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    private void addVideoToFirestore(String userId, String videoUrl, String videoDescription, Uri videoUri) {
        VideoInfo videoInfo = new VideoInfo(userId, videoUrl, videoDescription);
        // Nếu videoUri không null, tức là người dùng đã chọn video từ thiết bị
        if (videoUri != null) {
            String fileName = getFileName(videoUri);
            StorageReference videoStorageRef = storageRef.child("videos/" + fileName);
            // Upload video lên Firebase Storage
            videoStorageRef.putFile(videoUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Lấy link tải về của video
                        videoStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Lưu link vào đối tượng VideoInfo
                            videoInfo.setUrl(uri.toString());
                            // Thêm dữ liệu lên FirebaseFirestore
                            addVideoToFirestore(userId, videoInfo);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý khi upload video thất bại
                        Toast.makeText(AddvideoActivity.this, "Upload video thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Nếu videoUri là null, tức là người dùng nhập URL
            addVideoToFirestore(userId, videoInfo);
        }
    }
    private void initializePlayer(Uri videoUri) {
        // Tạo một đối tượng SimpleExoPlayer
        exoPlayer = new SimpleExoPlayer.Builder(this).build();
        // Liên kết ExoPlayer với PlayerView để hiển thị video
        videoPreview.setPlayer(exoPlayer);
        // Tạo MediaItem từ Uri
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        // Đặt MediaItem cho ExoPlayer
        exoPlayer.setMediaItem(mediaItem);
        // Chuẩn bị ExoPlayer
        exoPlayer.prepare();
        // Bắt đầu phát video
        exoPlayer.setPlayWhenReady(true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Xử lý video đã chọn
            videoUri = data.getData();
            // Hiển thị videoPreview bằng ExoPlayer
            initializePlayer(videoUri);
            Toast.makeText(this, "Selected Video: " + getFileName(videoUri), Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileName(Uri uri) {
        String result = null;
        Cursor cursor = null;
        try {
            if (uri.getScheme().equals("content")) {
                cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex >= 0) {
                        result = cursor.getString(displayNameIndex);
                    } else {
                        Log.e("Error", "Column DISPLAY_NAME not found in the cursor");
                    }
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        } catch (Exception e) {
            Log.e("Error", "Failed to get file name: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
    private void initControll() {
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth != null) {
                    currentUser = firebaseAuth.getCurrentUser();
                }
                if (currentUser != null) {
                    String userId = currentUser.getUid();

                        String videoUrl = url.getText().toString();
                        if (!videoUrl.isEmpty()) {
                            String videoDescription = noidung.getText().toString();
                            isUsingUrl = true;
                        } else {
                            isUsingUrl = false;
                        }
                        checkUserChoice(userId);
                }
            }
        });
    }
    private void checkUserChoice(String userId) {
        if (isUsingUrl) {
            // Nếu sử dụng URL, lấy dữ liệu từ trường nhập URL và mô phỏng việc chọn video từ thiết bị
            String videoUrl = url.getText().toString();
            String videoDescription = noidung.getText().toString();
            addVideoToFirestore(userId, videoUrl, videoDescription, null);
        } else if (videoUri != null) {
            // Nếu không sử dụng URL và có videoUri, tức là người dùng đã chọn một video từ thiết bị
            String videoDescription = noidung.getText().toString();
            addVideoToFirestore(userId, "", videoDescription, videoUri);
        } else {
            // Ngược lại, thông báo cho người dùng chọn video hoặc nhập URL
            Toast.makeText(this, "Vui lòng chọn một video hoặc nhập URL trước khi thêm", Toast.LENGTH_SHORT).show();
        }
    }
    private void addVideoToFirestore(String userId, VideoInfo videoInfo) {
        videoRef.add(videoInfo)
                .addOnSuccessListener(documentReference -> {
                    // Xử lý khi thêm dữ liệu thành công
                    Toast.makeText(AddvideoActivity.this, "Thêm video thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi thêm dữ liệu thất bại
                    Toast.makeText(AddvideoActivity.this, "Thêm video thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void initView() {
        url = findViewById(R.id.add_url);
        noidung = findViewById(R.id.add_nd);
        them = findViewById(R.id.add_them);
        huy = findViewById(R.id.add_huy);
        btnPickVideo = findViewById(R.id.btn_pick_video);
        videoPreview = findViewById(R.id.video_preview);

    }
}