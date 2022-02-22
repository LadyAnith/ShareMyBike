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
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdapterBikes extends RecyclerView.Adapter<AdapterBikes.MyViewHolder> {
    Context context;
    ArrayList<Bike> list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    /**
     * Método constructor
     * @param context
     * @param list
     */
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
                inicializarFirebase();
                //Variable donde guardo el e-mail
                String userEmail = "anithdeveloper@gmail.com";
                //Variable que guarda la fecha seleccionada en el FirstFragment
                String fecha = FirstFragment.nuevaFecha;
                //Objeto de la clase UserBooking
                UserBooking booking = new UserBooking(userEmail, bike.getEmail(), bike.getCity(),fecha);
                //Genero de manera automática una ID para el usuario
                booking.setUserId(UUID.randomUUID().toString());
                //Añado a la base de datos información de la reserva
                databaseReference.child("booking_request").child(booking.getUserId()).setValue(booking);

            }
        });

    }

    /**
     * Método que inicializa la conexión con el Firebase
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(context.getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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



}
