package com.example.sharemybike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemybike.Models.Bike;
import com.example.sharemybike.Models.UserBooking;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterBikes extends RecyclerView.Adapter<AdapterBikes.MyViewHolder> {
    Context context;
    ArrayList<Bike> list;

    public AdapterBikes(Context context, ArrayList<Bike> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterBikes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_item, parent, false);
        return  new AdapterBikes.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBikes.MyViewHolder holder, int position) {
        Bike bike = list.get(position);
        holder.photo.setImageBitmap(bike.getPhoto());
        holder.city.setText(bike.getCity());
        holder.location.setText(bike.getLocation());
        holder.owner.setText(bike.getOwner());
        holder.description.setText(bike.getDescription());
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = "anithdeveloper@gmail.com";
                //UserBooking booking = new UserBooking(userEmail, bike.getEmail(), bike.getCity(), );

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView photo;
        public TextView city;
        public TextView location;
        public TextView owner;
        public TextView description;
        public ImageButton mail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.imgPhoto);
            city = itemView.findViewById(R.id.txtCity);
            location = itemView.findViewById(R.id.txtLocation);
            owner = itemView.findViewById(R.id.txtOwner);
            description =itemView.findViewById(R.id.txtDescription);
            mail = itemView.findViewById(R.id.btnMail);
        }
    }

    public void bookingBike(UserBooking booking){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        Map<String, Object> updates = new HashMap<>();
        updates.put("booking_request/"+booking, true);
        mDatabase.updateChildren(updates);
    }
}
