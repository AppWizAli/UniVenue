package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityBookingDetailBinding;
import com.enfotrix.unibooking.databinding.ActivityBookingPageBinding;
import com.google.gson.Gson;

public class ActivityBookingDetail extends AppCompatActivity {
    private ActivityBookingDetailBinding binding;
    SharedPrefManager sharedPrefManager;
    HallModel hallModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager = new SharedPrefManager(this);


        Intent intent = getIntent();

        // Retrieve the JSON string from the intent
        String hallModelJson = intent.getStringExtra("selectedHall");

        // Parse the JSON string back into a HallModel object
        hallModel = new Gson().fromJson(hallModelJson, HallModel.class);


        binding.hallname.setText(hallModel.getHallName());
        binding.venue.setText(hallModel.getHallVenue());
        binding.capacity.setText(hallModel.getHallCapacity());

      binding.booking.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String hallModelJson = new Gson().toJson(hallModel);

              Intent intent = new Intent(ActivityBookingDetail.this, ActivityBookingPage.class);
              intent.putExtra("slectedHall", hallModelJson);
              startActivity(intent);  // Use mContext here
          }
      });



        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back
                finish();
            }
        });



    }
}