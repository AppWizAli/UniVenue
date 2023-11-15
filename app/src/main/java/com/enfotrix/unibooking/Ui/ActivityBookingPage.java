package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityBookingPageBinding;
import com.enfotrix.unibooking.databinding.ActivityHallsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class ActivityBookingPage extends AppCompatActivity {
    private ActivityBookingPageBinding binding;

    SharedPrefManager sharedPrefManager;
    HallModel hallModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager = new SharedPrefManager(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back action
                finish();
            }
        });

        Intent intent = getIntent();

        // Retrieve the JSON string from the intent
        String hallModelJson = intent.getStringExtra("slectedHall");

        // Parse the JSON string back into a HallModel object
        hallModel = new Gson().fromJson(hallModelJson, HallModel.class);

        // Check if hallModel is not null before accessing its methods
        if (hallModel != null) {
            Toast.makeText(this, "" + hallModel.getHallCapacity(), Toast.LENGTH_SHORT).show();
            binding.datetv.setText(sharedPrefManager.getDate());
            binding.time.setText(sharedPrefManager.getTime());






            binding.bookingbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve values from EditText views
                    String reason = binding.capacity.getText().toString().trim();
                    String participant = binding.participant.getText().toString().trim();

                    // Check if both reason and participant are not empty
                    if (!reason.isEmpty() && !participant.isEmpty()) {
                        // Display a Toast with the values
                        Toast.makeText(ActivityBookingPage.this, "Reason: " + reason + ", Participant: " + participant, Toast.LENGTH_SHORT).show();

                        // Create a BookingModel object with the required data
                        BookingModel bookingModel = new BookingModel();
                        // Set the Hall ID from the hallModel
                        bookingModel.setHallid(hallModel.getHallId());
                        bookingModel.setStudentEmail(sharedPrefManager.getUser().getEmail());
                        bookingModel.setDate(sharedPrefManager.getDate());
                        bookingModel.setTime(sharedPrefManager.getTime());
                        bookingModel.setReason(reason);
                        bookingModel.setStatus("pending");
                        bookingModel.setParticipants(participant);

                        // Store data in Firestore collection "booking"
                        FirebaseFirestore.getInstance().collection("booking")
                                .add(bookingModel)
                                .addOnSuccessListener(documentReference -> {
                                    // Successfully added booking to Firestore
                                    // Set the generated booking ID back to the bookingModel
                                    String bookingId = documentReference.getId();
                                    bookingModel.setBookingId(bookingId);

                                    // Update the Firestore document with the booking ID
                                    documentReference.set(bookingModel)
                                            .addOnSuccessListener(aVoid -> {
                                                // Handle success if needed
                                                Toast.makeText(ActivityBookingPage.this, "Booking added successfully", Toast.LENGTH_SHORT).show();

                                                // Navigate to the booking detail activity
                                                Intent intent = new Intent(ActivityBookingPage.this, MainActivity.class);
                                                startActivity(intent);

                                                // Finish the current activity
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                // Handle failure if needed
                                                Toast.makeText(ActivityBookingPage.this, "Failed to update booking with ID", Toast.LENGTH_SHORT).show();
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    // Failed to add booking to Firestore
                                    // Handle failure if needed
                                    Toast.makeText(ActivityBookingPage.this, "Failed to add booking", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // Show an error message if any of the fields is empty
                        Toast.makeText(ActivityBookingPage.this, "Please fill out both Reason and Participant fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });







        } else {
            // Handle the case where hallModel is null (e.g., log an error, show a message, etc.)
            Toast.makeText(this, "Error: hallModel is null", Toast.LENGTH_SHORT).show();
            // You might want to finish the activity or take appropriate action based on your app's logic
            finish();
        }
    }

}