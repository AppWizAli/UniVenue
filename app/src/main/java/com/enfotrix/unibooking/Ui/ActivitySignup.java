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

        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.passowrd);
        EditText confirmPasswordEditText = findViewById(R.id.cpassword);
        EditText Name = findViewById(R.id.StudentName);

        Button signUpButton = findViewById(R.id.signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = emailEditText.getText().toString().trim();
                String userPassword = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String name = Name.getText().toString().trim();


                // Validate data
                if (userEmail.isEmpty() || userPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ActivitySignup.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!userPassword.equals(confirmPassword)) {
                    Toast.makeText(ActivitySignup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a HashMap to store user data
                Map<String, Object> userData = new HashMap<>();
                userData.put("email", userEmail);
                userData.put("password", userPassword);
                userData.put("name", name);

                FirebaseFirestore.getInstance().collection("UserCollection")
                        .add(userData)
                        .addOnSuccessListener(documentReference -> {
                            String userId = documentReference.getId();
                            userData.put("userId", userId);
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
    }
}
