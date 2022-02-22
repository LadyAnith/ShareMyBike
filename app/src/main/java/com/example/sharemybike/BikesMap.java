package com.example.sharemybike;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharemybike.Models.Bike;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class BikesMap extends Fragment {
    private GoogleMap mMap;
    private ArrayList<Bike> list;

    /**
     * Método que muestra las ubicaciones de las bicicletas en el mapa con un marcador
     * y las une con polilíneas
     */
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            PolylineOptions poly=new PolylineOptions();

            if(!BikeFragment.list.isEmpty()){

            //for each bike in the list
            for (Bike c : BikeFragment.list) {

                //gets its latitude and longitude
                LatLng ll = new LatLng(Double.valueOf(c.getLatitude()), Double.valueOf(c.getLongitude()));
                //adds a marker on the map
                mMap.addMarker(new MarkerOptions().position(ll).title(c.getCity()).snippet(String.valueOf(c.getOwner())+"%"));
                builder.include(ll);
                //adds also a point in the polyline
                poly.add(ll);
            }
            mMap.addPolyline(poly);
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
            mMap.animateCamera(cu);
            }else{
                System.out.println("ERROR");
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_bikes_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}