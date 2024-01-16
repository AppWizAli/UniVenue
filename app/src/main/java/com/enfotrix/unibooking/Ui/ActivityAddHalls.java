package com.enfotrix.unibooking.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.enfotrix.unibooking.databinding.ActivityAddHallsBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityAddHalls extends AppCompatActivity {
    private ActivityAddHallsBinding binding;
    SharedPrefManager sharedPrefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHallsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager = new SharedPrefManager(this);


        binding.btnAddhalls.setOnClickListener(v -> addHall());
        binding.back.setOnClickListener(v -> finish());
    }

    private void addHall() {
        // Retrieve data from EditText fields
        String hallCapacity = binding.etHallCapcity.getText().toString().trim();
        String hallVenue = binding.etHallVenue.getText().toString().trim();
        String hallType = binding.etHallType.getText().toString().trim();
        String etHallName = binding.etHallName.getText().toString().trim();

        // Validate data
        if (hallCapacity.isEmpty() || hallVenue.isEmpty() || hallType.isEmpty()|| etHallName.isEmpty()) {
            // Display an error message or handle the validation failure as needed
            Toast.makeText(ActivityAddHalls.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a unique hallId (you can use UUID or any other method)
        String hallId = generateUniqueHallId();

        // Create a HallModel object with hallId
        HallModel hallModel = new HallModel(hallId, hallCapacity, hallVenue, hallType,etHallName);

        // Store data in Firestore collection "hall"
        FirebaseFirestore.getInstance().collection("hall")
                .document(hallId)
                .set(hallModel)
                .addOnSuccessListener(aVoid -> {
                    // Successfully added hall to Firestore
                    // Handle success if needed
                    Toast.makeText(ActivityAddHalls.this, "Hall added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityAddHalls.this, ActivityNewRequests.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Failed to add hall to Firestore
                    // Handle failure if needed
                    Toast.makeText(ActivityAddHalls.this, "Failed to add hall", Toast.LENGTH_SHORT).show();
                });
    }

    // You can use any method to generate a unique hallId
    private String generateUniqueHallId() {
        // Implement your logic to generate a unique hallId (e.g., using UUID)
        // For simplicity, I'm using a simple concatenation of strings in this example
        return "hall_" + System.currentTimeMillis();
    }


}
