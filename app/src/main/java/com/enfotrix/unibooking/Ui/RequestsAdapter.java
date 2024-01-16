package com.enfotrix.unibooking.Ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.unibooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder> {

    private List<BookingModel> bookingList;
    private List<ModelUser> usersList;
    private List<HallModel> hallList;
    private Context mContext;

    public RequestsAdapter(List<ModelUser> usersList, List<BookingModel> bookingList, List<HallModel> hallList, Context mContext) {
        this.bookingList = bookingList;
        this.usersList = usersList;
        this.hallList = hallList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrequests, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingModel bookingModel = bookingList.get(position);

        // Assuming there is a getUserEmail method in your BookingModel class
        String userEmail = bookingModel.getStudentEmail();

        // Find the corresponding ModelUser based on userEmail in usersList
        ModelUser matchingUser = findUserByUserEmail(userEmail);

        if (matchingUser != null) {
            // Set user name from the found ModelUser
            holder.name.setText(matchingUser.getName());
        } else {
            // Handle the case where ModelUser is not found
            holder.name.setText("Unknown User");
        }

        holder.status.setText(bookingModel.getStatus());

        // Set background color based on the status
        if ("rejected".equalsIgnoreCase(bookingModel.getStatus())) {
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
        } else if ("approved".equalsIgnoreCase(bookingModel.getStatus())) {
            holder.status.setBackgroundColor(mContext.getResources().getColor(R.color.skyBlue));
        } else if ("pending".equalsIgnoreCase(bookingModel.getStatus())) {
            holder.status.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.status.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }

        // Set a click listener for the "Detail" button
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the "Detail" button
                showDetailDialog(matchingUser, bookingModel);
            }
        });
    }

    // Helper method to find ModelUser by userEmail in usersList
    private ModelUser findUserByUserEmail(String userEmail) {
        for (ModelUser user : usersList) {
            if (userEmail.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    // Helper method to show a custom dialog with details and action buttons
    private void showDetailDialog(ModelUser user, BookingModel booking) {
        // Create a custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialoug_details_booking, null);

        // Find views in the custom dialog layout
        TextView personNameTextView = dialogView.findViewById(R.id.textPersonName);
        TextView hallNameTextView = dialogView.findViewById(R.id.textHallName);
        TextView dateTextView = dialogView.findViewById(R.id.textDate);
        TextView timeTextView = dialogView.findViewById(R.id.textTime);
        TextView reasonTextView = dialogView.findViewById(R.id.textReason);

        // Set text in the dialog views
        personNameTextView.setText(user.getName());
        String hallID = booking.getHallid();
        HallModel hallModel = findHallByHallId(hallID);

        if (hallModel != null) {
            hallNameTextView.setText(hallModel.getHallName());
        } else {
            hallNameTextView.setText("Unknown Hall");
        }

        dateTextView.setText(booking.getDate());
        timeTextView.setText(booking.getTime());
        reasonTextView.setText(booking.getReason());

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView)
                .setTitle("Booking Details")
                .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "Approve" button click
                        approveBooking(booking);
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "Reject" button click
                        rejectBooking(booking);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "Cancel" button click
                        dialog.dismiss();
                    }
                });

        // Show the dialog
        builder.create().show();
    }

    // Helper method to find hall by hallId in hallList
    private HallModel findHallByHallId(String hallId) {
        for (HallModel hall : hallList) {
            if (hallId.equals(hall.getHallId())) {
                return hall;
            }
        }
        return null;
    }

    // Method to handle the "Approve" button click
    private void approveBooking(BookingModel booking) {
        // Implement your logic to approve the booking
        // You can update the status in the database, show a message, etc.
        updateBookingStatus(booking, "approved");
        Toast.makeText(mContext, "Booking Approved", Toast.LENGTH_SHORT).show();
    }

    // Method to handle the "Reject" button click
    private void rejectBooking(BookingModel booking) {
        // Implement your logic to reject the booking
        // You can update the status in the database, show a message, etc.
        updateBookingStatus(booking, "rejected");
        Toast.makeText(mContext, "Booking Rejected", Toast.LENGTH_SHORT).show();
    }

    // Method to update the booking status in Firestore
    private void updateBookingStatus(BookingModel booking, String newStatus) {
        // Assuming you have a field named "status" in your BookingModel class
        booking.setStatus(newStatus);

        // Get the booking document ID
        String bookingId = booking.getBookingId();

        // Update the status in Firestore
        FirebaseFirestore.getInstance().collection("booking")
                .document(bookingId)
                .update("status", newStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle success, if needed
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure, if needed
                    }
                });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView detail;
        public TextView status;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            detail = itemView.findViewById(R.id.detail);
            status = itemView.findViewById(R.id.status);
        }
    }
}
