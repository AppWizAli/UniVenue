package com.enfotrix.unibooking.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.enfotrix.unibooking.R;

import java.text.DateFormat;
import java.util.Calendar;

public class ActivityBooking extends AppCompatActivity {

    private Button dateButton, timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dateButton = findViewById(R.id.date);
        timeButton = findViewById(R.id.time);

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
    }
}
