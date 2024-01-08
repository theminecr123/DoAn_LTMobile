package com.DoAn_Mobile.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.DoAn_Mobile.Activities.EditInfoActivity;
import com.DoAn_Mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int EDIT_INFO_REQUEST_CODE = 100;

    private CircleImageView profileImage;
    private TextView usernameTextView;
    private TextView statusTextView;
    private TextView followersTextView;
    private TextView descriptionTextView;
    private ViewPager2 viewpagerprofile;
    private Button editbutton;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = view.findViewById(R.id.profile_image);
        usernameTextView = view.findViewById(R.id.username_text_view);
        statusTextView = view.findViewById(R.id.status);
        followersTextView = view.findViewById(R.id.followers);
        descriptionTextView = view.findViewById(R.id.description);
        viewpagerprofile = view.findViewById(R.id.viewpagerprofile);
        editbutton = view.findViewById(R.id.editbutton);

        mAuth = FirebaseAuth.getInstance();

        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openImageChooser();
                return true;
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                startActivityForResult(intent, EDIT_INFO_REQUEST_CODE);
            }
        });

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("name").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);

                    usernameTextView.setText(username);
                    statusTextView.setText(status);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == EDIT_INFO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("updatedName");
            String updatedGender = data.getStringExtra("updatedGender");

            usernameTextView.setText(updatedName);

        }
    }
}
