package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityAdminOrUserSelectBinding;

public class ActivityAdminOrUserSelect extends AppCompatActivity {


    private ActivityAdminOrUserSelectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminOrUserSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAdminOrUserSelect.this, ActivityLogin.class);
                startActivity(intent);

            }
        });


        binding.btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAdminOrUserSelect.this, ActivityAdminLogin.class);
                startActivity(intent);
            }
        });

   }
}