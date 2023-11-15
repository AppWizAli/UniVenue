package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.enfotrix.unibooking.R;
import com.enfotrix.unibooking.databinding.ActivityBookingBinding;

import java.text.DateFormat;
import java.util.Calendar;

public class ActivityBooking extends AppCompatActivity {

    private Button dateButton, timeButton, bookingButton;
    private ActivityBookingBinding binding;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dateButton = findViewById(R.id.date);
        timeButton = findViewById(R.id.time);
        bookingButton = findViewById(R.id.booking);
        sharedPrefManager = new SharedPrefManager(this);

        // Initially, disable the "Next" button
        bookingButton.setEnabled(false);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBooking.this, ActivityHalls.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityBooking.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateLabel(calendar);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateDateLabel(Calendar calendar) {
        String dateFormat = "dd MMMM yyyy";
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
        dateButton.setText(df.format(calendar.getTime()));
        sharedPrefManager.putDate(df.format(calendar.getTime()));

        // Enable or disable the "Next" button based on date and time selection
        updateNextButtonState();
    }

    private void showTimePicker() {
        final Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityBooking.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        currentTime.set(Calendar.MINUTE, minute);
                        updateTimeLabel(currentTime);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void updateTimeLabel(Calendar calendar) {
        String timeFormat = "hh:mm a";
        DateFormat tf = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        timeButton.setText(tf.format(calendar.getTime()));
        sharedPrefManager.putTime(tf.format(calendar.getTime()));

        // Enable or disable the "Next" button based on date and time selection
        updateNextButtonState();
    }

    private void updateNextButtonState() {
        // Enable the "Next" button only if both date and time are selected
        bookingButton.setEnabled(sharedPrefManager.getDate() != null && sharedPrefManager.getTime() != null);
    }
}
