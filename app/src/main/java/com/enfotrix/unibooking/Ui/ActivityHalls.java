package com.enfotrix.unibooking.Ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.unibooking.databinding.ActivityHallsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityHalls extends AppCompatActivity {

    private ActivityHallsBinding binding;
    private List<HallModel> hallsList;
    private HallAdapter hallAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHallsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the list and adapter
        hallsList = new ArrayList<>();
        hallAdapter = new HallAdapter(hallsList,ActivityHalls.this);

        // Set up the RecyclerView
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(hallAdapter);

        // Retrieve halls from Firebase
        FirebaseFirestore.getInstance().collection("hall")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Loop through the documents and add them to the list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HallModel hall = document.toObject(HallModel.class);

                            hallsList.add(hall);
                        }

                        // Notify the adapter that the data set has changed
                        hallAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                    }
                });
    }
}
