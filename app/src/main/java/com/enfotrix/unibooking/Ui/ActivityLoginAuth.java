package com.enfotrix.unibooking.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enfotrix.unibooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ActivityLoginAuth extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView backButton;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_auth);

        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.passowrd);
        loginButton = findViewById(R.id.login);
        backButton = findViewById(R.id.back);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredentials();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back action
                finish();
            }
        });
    }

    private void validateCredentials() {
        String userEmail = emailEditText.getText().toString().trim();
        String userPassword = passwordEditText.getText().toString().trim();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(ActivityLoginAuth.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query Firestore to check if entered email exists and password matches
        Query query = db.collection("UserCollection").whereEqualTo("email", userEmail);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (Objects.requireNonNull(task.getResult()).isEmpty()) {
                        // User with entered email does not exist
                        Toast.makeText(ActivityLoginAuth.this, "User not found", Toast.LENGTH_SHORT).show();
                    } else {
                        // User with entered email exists
                        String storedPassword = Objects.requireNonNull(task.getResult().getDocuments().get(0).getString("password"));

                        if (userPassword.equals(storedPassword)) {
                            // Password matches, login successful
                            Intent intent = new Intent(ActivityLoginAuth.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            // Password does not match
                            Toast.makeText(ActivityLoginAuth.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Error fetching documents
                    Toast.makeText(ActivityLoginAuth.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
