package com.example.sharemybike.bikes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import edu.pmdm.sharemybike.placeholder.PlaceholderContent;

public class BikesContent {

    //List of all the bikes to be listed in the RecyclerView
    public static List<Bike> ITEMS = new ArrayList<>();
    private static DatabaseReference database;


    public static void loadBikesFromFirebase(){
        database = FirebaseDatabase.getInstance().getReference();

        database.child("bikes_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Bike bike = productSnapshot.getValue(Bike.class);
                    //operar con el objeto Bike descargado de Firebase
                    ITEMS.add(bike);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //public static String selectedDate;
    /*
    public static void loadBikesFromJSON(Context c) {

        String json = null;
        try {
            InputStream is =
                    c.getAssets().open("bikeList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray couchList = jsonObject.getJSONArray("bike_list");
            for (int i = 0; i < couchList.length(); i++) {
                JSONObject jsonCouch = couchList.getJSONObject(i);
                String owner = jsonCouch.getString("owner");
                String description = jsonCouch.getString("description");
                String city=jsonCouch.getString("city");
                String location=jsonCouch.getString("location");
                String email=jsonCouch.getString("email");
                Bitmap photo=null;
                try {
                    photo= BitmapFactory.decodeStream(
                            c.getAssets().open("images/"+
                                    jsonCouch.getString("image")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ITEMS.add(new Bike(photo,owner,description,city,location,email));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
*/
    public static class Bike {
            //atributos miembros
            private String image;
            private String owner;
            private String description;
            private String city;
            private Double longitude;
            private Double latitude;
            private String location;
            private String email;
            //setters y getters
            public Double getLatitude() {
                return latitude;
            }
            public void setLatitude(Double latitude) {
                this.latitude = latitude;
            }
            public Double getLongitude() {
                return longitude;
            }
            public String getLocation() {
                return location;
            }
            public void setLocation(String location) {
                this.location = location;
            }
            public void setLongitude(Double longitude) {
                this.longitude = longitude;
            }
            public String getImage() {
                return image;
            }
            public void setImage(String image) {
                this.image = image;
            }
            public String getEmail() {
                return email;
            }
            public void setEmail(String email) {
                this.email = email;
            }
            public String getOwner() {
                return owner;
            }
            public void setOwner(String owner) {
                this.owner = owner;
            }
            public String getDescription() {
                return description;
            }
            public void setDescription(String description) {
                this.description = description;
            }
            public String getCity() {
                return city;
            }
            public void setCity(String city) {
                this.city = city;
            }
            //constructor con los miembros
            public Bike(String image, String owner, String description,
                        String city, Double longitude, Double latitude,
                        String location, String email) {
                this.image = image;
                this.owner = owner;
                this.description = description;
                this.city = city;
                this.longitude = longitude;
                this.latitude = latitude;
                this.location = location;
                this.email = email;
            }
            //constructor por defecto
            public Bike(){
            }
        }
}
