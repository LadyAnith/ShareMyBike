package com.example.sharemybike.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserBooking {
    public String userId;
    public String userEmail;
    public String bikeEmail;
    public String bikeCity;
    public String bookDate;

    public UserBooking(String userId, String userEmail, String bikeEmail, String bikeCity, String bookDate) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.bikeEmail = bikeEmail;
        this.bikeCity = bikeCity;
        this.bookDate = bookDate;
    }

    public UserBooking(String userEmail, String bikeEmail, String bikeCity, String bookDate) {
        this.userEmail = userEmail;
        this.bikeEmail = bikeEmail;
        this.bikeCity = bikeCity;
        this.bookDate = bookDate;
    }

    public UserBooking(){

    }

    public void addToDatabase(){
        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        String key= database.child("booking_requests").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        database.child("booking_requests/"+key).setValue(this);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBikeEmail() {
        return bikeEmail;
    }

    public void setBikeEmail(String bikeEmail) {
        this.bikeEmail = bikeEmail;
    }

    public String getBikeCity() {
        return bikeCity;
    }

    public void setBikeCity(String bikeCity) {
        this.bikeCity = bikeCity;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
}
