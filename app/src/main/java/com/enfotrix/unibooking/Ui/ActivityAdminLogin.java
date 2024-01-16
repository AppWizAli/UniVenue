package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityAdminLoginBinding;

public class ActivityAdminLogin extends AppCompatActivity {


    private ActivityAdminLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAdminLogin.this, ActivityAdminLoginAuth.class);
                startActivity(intent);
                finish();
            }
        });

        // Now you can use 'binding' to reference your views
        // For example: binding.myTextView.setText("Hello, View Binding!");
    }


    }
