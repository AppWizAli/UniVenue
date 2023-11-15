package com.enfotrix.unibooking.Ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityHistoryBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistory extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    private SharedPrefManager sharedPrefManager;
    private HistoryAdapter historyAdapter;
    private List<BookingModel> bookingList;
    private List<HallModel> hallList; // Add this list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        sharedPrefManager = new SharedPrefManager(this);
        setContentView(binding.getRoot());

        // Initialize the RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();
        hallList = new ArrayList<>(); // Initialize the hallList
        historyAdapter = new HistoryAdapter(bookingList, hallList, this); // Pass hallList to the adapter
        binding.recyclerView.setAdapter(historyAdapter);

        // Retrieve all halls from Firestore
        FirebaseFirestore.getInstance().collection("hall")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Clear the previous data
                        hallList.clear();

                        // Iterate through the result set and add halls to the list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HallModel hallModel = document.toObject(HallModel.class);
                            hallList.add(hallModel);
                        }

                        // Now that you have the hallList, you can fetch bookings
                        fetchBookings();
                    } else {
                        // Handle the error if needed
                    }
                });
    }

    private void fetchBookings() {
        // Get the user's email
        String userEmail = sharedPrefManager.getUser().getEmail();

        // Retrieve bookings from Firestore where studentEmail is equal to user's email
        FirebaseFirestore.getInstance().collection("booking")
                .whereEqualTo("studentEmail", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Clear the previous data
                        bookingList.clear();

                        // Iterate through the result set and add bookings to the list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BookingModel bookingModel = document.toObject(BookingModel.class);
                            bookingList.add(bookingModel);
                        }

                        // Notify the adapter that the data set has changed
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the error if needed
                    }
                });
    }
}
