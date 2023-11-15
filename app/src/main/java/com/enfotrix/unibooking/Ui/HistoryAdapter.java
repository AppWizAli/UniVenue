package com.enfotrix.unibooking.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.unibooking.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<BookingModel> bookingList;
    private List<HallModel> hallList;
    private Context mContext;

    public HistoryAdapter(List<BookingModel> bookingList, List<HallModel> hallList, Context mContext) {
        this.bookingList = bookingList;
        this.hallList = hallList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingModel bookingModel = bookingList.get(position);

        holder.date.setText(bookingModel.getDate());
        holder.time.setText(bookingModel.getTime());
        holder.status.setText(bookingModel.getStatus());

        String hallId = bookingModel.getHallid();

        // Find the corresponding HallModel based on hallId in hallList
        HallModel matchingHallModel = findHallModelByHallId(hallId);

        if (matchingHallModel != null) {
            // Set hall name from the found HallModel
            Toast.makeText(mContext, ""+matchingHallModel.getHallName(), Toast.LENGTH_SHORT).show();
            holder.hall.setText(matchingHallModel.getHallName());
        } else {
            // Handle the case where HallModel is not found
            holder.hall.setText("Unknown Hall");
        }

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
    }

    // Helper method to find HallModel by hallId in hallList
    private HallModel findHallModelByHallId(String hallId) {
        for (HallModel hallModel : hallList) {
            if (hallId.equals(hallModel.getHallId())) {
                return hallModel;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView time;
        public TextView hall;
        public TextView status;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.Date);
            time = itemView.findViewById(R.id.Time);
            hall = itemView.findViewById(R.id.Hall);
            status = itemView.findViewById(R.id.Status);
        }
    }
}
