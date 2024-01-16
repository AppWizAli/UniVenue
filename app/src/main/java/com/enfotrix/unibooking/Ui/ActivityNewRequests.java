package com.enfotrix.unibooking.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityNewRequestsBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityNewRequests extends AppCompatActivity {
    private ActivityNewRequestsBinding binding;
    private SharedPrefManager sharedPrefManager;

    // Create a list to store users
    private List<ModelUser> userList;

    // Firebase Firestore
    private FirebaseFirestore db;
    private CollectionReference userCollection;

    // Create a list to store bookings
    private List<BookingModel> bookingList;
    private List<HallModel> hallsList;

    // Firebase Firestore for bookings
    private CollectionReference bookingCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewRequestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager = new SharedPrefManager(this);

        // Initialize Firestore for users
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("UserCollection");

        // Initialize Firestore for bookings
        bookingCollection = db.collection("booking");

        // Initialize the userList
        userList = new ArrayList<>();

        // Initialize the bookingList
        bookingList = new ArrayList<>();
        hallsList = new ArrayList<>();

        fetchUsers();

        binding.btnAddhalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNewRequests.this, ActivityAddHalls.class);
                startActivity(intent);
            }
        });
    }

    private void fetchUsers() {
        userCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    ModelUser user = document.toObject(ModelUser.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                // Save the userList to SharedPreferences
                sharedPrefManager.saveUserList(userList);
                Toast.makeText(ActivityNewRequests.this, "userList " + userList.size(), Toast.LENGTH_SHORT).show();

                // After fetching users, fetch bookings
                fetchBookings();
            } else {
                // Handle the error
                Toast.makeText(ActivityNewRequests.this, "Error fetching users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchBookings() {
        bookingCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    BookingModel booking = document.toObject(BookingModel.class);
                    if (booking != null) {
                        bookingList.add(booking);
                    }
                }

                FirebaseFirestore.getInstance().collection("hall")
                        .get()
                        .addOnCompleteListener(tasks -> {
                            if (tasks.isSuccessful()) {
                                // Loop through the documents and add them to the list
                                for (QueryDocumentSnapshot document : tasks.getResult()) {
                                    HallModel hall = document.toObject(HallModel.class);

                                    hallsList.add(hall);
                                }

                                // Notify the adapter that the data set has changed

                            }
                        });



                // Save the bookingList to SharedPreferences
                sharedPrefManager.saveBookingList(bookingList);
                Toast.makeText(ActivityNewRequests.this, "booking " + bookingList.size(), Toast.LENGTH_SHORT).show();

                // Call the adapter after fetching bookings
                setupRecyclerView();
            } else {
                // Handle the error
                Toast.makeText(ActivityNewRequests.this, "Error fetching bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        RequestsAdapter requestsAdapter = new RequestsAdapter(userList, bookingList,hallsList, ActivityNewRequests.this);

        // Set up your RecyclerView with the adapter, layout manager, etc.
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityNewRequests.this));
        recyclerView.setAdapter(requestsAdapter);

        // Continue with any other logic...


    }
}
