package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.enfotrix.unibooking.R;

public class MainActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sharedPrefManager = new SharedPrefManager(this);

        Button historyButton = findViewById(R.id.history);
        Button bookingButton = findViewById(R.id.booking);
        TextView backButton = findViewById(R.id.back);
       TextView name=findViewById(R.id.userName);

       String names=name.getText().toString();
       name.setText(sharedPrefManager.getUser().getName());


        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityHistory.class);
                startActivity(intent);
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityBooking.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
