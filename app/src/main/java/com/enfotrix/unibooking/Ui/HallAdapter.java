package com.enfotrix.unibooking.Ui;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.unibooking.R;
import com.google.gson.Gson;

import java.util.List;

public class HallAdapter extends RecyclerView.Adapter<HallAdapter.MyViewHolder> {


    private List<HallModel> hallList;
    private Context mContext;

    public HallAdapter(List<HallModel> hallList, Context mContext) {
        this.hallList=hallList;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_halls, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HallModel hallModel = hallList.get(position);
        holder.name.setText(hallModel.getHallName());
        holder.capacity.setText(hallModel.getHallCapacity());


        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert HallModel to JSON string
                String hallModelJson = new Gson().toJson(hallModel);

                Intent intent = new Intent(mContext, ActivityBookingDetail.class);
                intent.putExtra("selectedHall", hallModelJson);
                mContext.startActivity(intent);  // Use mContext here
            }
        });

    }



    @Override
    public int getItemCount() {
        return hallList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView capacity;
        public TextView btnBook;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvname);
            capacity = itemView.findViewById(R.id.tvcapacity);
            btnBook=itemView.findViewById(R.id.btnbooking);
        }
    }
}