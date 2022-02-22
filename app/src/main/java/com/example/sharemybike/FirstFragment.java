package com.example.sharemybike;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sharemybike.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private CalendarView calendario;
    private TextView fecha;
    public static String nuevaFecha = "";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        calendario = binding.calendarView;
        fecha = binding.txtFechaSeleccionada;
        /*
        Al calendario se le añade el evento onSelectedDayChange que lleva como parámetro un CalendarView y 3 enteros que van a guardar
        los datos seleccionados en el calendario, con su orden correspondiente (primero el año, luego mes y luego el día).
        Le añado la anotación @NonNull para obligar que el usuario tenga que seleccionar una fecha antes de hacer la reserva
        */
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Al mes hay que sumarle 1 porque comienza desde 0 y da mal el resultado
                month = month + 1;
                //Se guarda en un String el resultado, y se settea al TextView
                nuevaFecha = "Date: " + dayOfMonth + "/" + month + "/" + year;
                fecha.setText(nuevaFecha);
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}