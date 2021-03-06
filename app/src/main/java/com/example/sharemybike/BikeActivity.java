package com.example.sharemybike;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.sharemybike.bikes.BikesContent;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sharemybike.databinding.ActivityBikeBinding;

public class BikeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBikeBinding binding;

    public static CalendarView calendario;
    public static TextView fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bike);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Guardo cada uno de los componentes en una variable
        calendario = (CalendarView) findViewById(R.id.calendarView);
        fecha = (TextView) findViewById(R.id.txtFechaSeleccionada);
        /*
        Al calendario se le a??ade el evento onSelectedDayChange que lleva como par??metro un CalendarView y 3 enteros que van a guardar
        los datos seleccionados en el calendario, con su orden correspondiente (primero el a??o, luego mes y luego el d??a).
        Le a??ado la anotaci??n @NonNull para obligar que el usuario tenga que seleccionar una fecha antes de hacer la reserva
        */
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Al mes hay que sumarle 1 porque comienza desde 0 y da mal el resultado
                month = month + 1;
                //Se guarda en un String el resultado, y se settea al TextView
                String nuevaFecha = "Date: " + dayOfMonth + "/" + month + "/" + year;
                fecha.setText(nuevaFecha);
            }
        });

        //M??todo para leer un fichero JSON
        //BikesContent.loadBikesFromFirebase();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bike);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}