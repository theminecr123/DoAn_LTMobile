package com.DoAn_Mobile.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DoAn_Mobile.MainActivity;
import com.DoAn_Mobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfPassword;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfPassword = findViewById(R.id.edtConfirmPassword);

        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v -> {

            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        });

        edtConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(edtPassword.getText().toString())){
                    if(s != edtPassword.getText().toString()){
                        edtConfPassword.setError("Password does not match!");

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}