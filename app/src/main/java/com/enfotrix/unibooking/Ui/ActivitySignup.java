package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enfotrix.unibooking.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ActivitySignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Assume you have EditText fields for email and password
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.passowrd);

        Button signUpButton = findViewById(R.id.signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user-entered email and password
                String userEmail = emailEditText.getText().toString().trim();
                String userPassword = passwordEditText.getText().toString().trim();

                // Create a HashMap to store user data
                Map<String, Object> userData = new HashMap<>();
                userData.put("email", userEmail);
                userData.put("password", userPassword);

                // Access Firestore and add the data to the "UserCollection"
                FirebaseFirestore.getInstance().collection("UserCollection")
                        .add(userData)
                        .addOnSuccessListener(documentReference -> {
                            // Data added successfully to Firestore
                            String userId = documentReference.getId();
                            userData.put("userId", userId); // Add userId using document ID
                            // Update the document with userId
                            documentReference.set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(ActivitySignup.this, "Signup Successful!", Toast.LENGTH_SHORT).show();

                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(ActivitySignup.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ActivitySignup.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        });
                finish();
            }

        });

        // ... Other parts of your code
    }
}
