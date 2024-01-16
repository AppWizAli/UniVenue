package com.enfotrix.unibooking.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityLoginAuthBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ActivityAdminLoginAuth extends AppCompatActivity {

    private ActivityLoginAuthBinding binding;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

        binding.login.setOnClickListener(v -> validateCredentials());

        binding.back.setOnClickListener(v -> finish());
    }

    private void validateCredentials() {
        String userEmail = binding.email.getText().toString().trim();
        String userPassword = binding.passowrd.getText().toString().trim();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(ActivityAdminLoginAuth.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query Firestore to check if entered email exists and password matches
        Query query = db.collection("admin").whereEqualTo("email", userEmail).whereEqualTo("password", userPassword);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !Objects.requireNonNull(task.getResult()).isEmpty()) {
                    // User with entered email and password exists in the "admin" collection
                    // Move to the main activity
                    Intent intent = new Intent(ActivityAdminLoginAuth.this, ActivityNewRequests.class);
                    startActivity(intent);
                    finish();
                } else {
                    // User not found or incorrect password
                    Toast.makeText(ActivityAdminLoginAuth.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
