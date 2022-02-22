package com.example.sharemybike;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharemybike.Models.Bike;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBikeFragment extends Fragment {
    private FusedLocationProviderClient fusedLocationClient;
    Location ultimaUbicacion;
    int FINE_LOCATION_REQUEST_CODE = 101;
    Double longitud;
    Double latitud;
    EditText txtLongitud;
    EditText txtLatitud;
    EditText direccion;
    EditText ciudad;
    EditText descripcion;
    TextView nombre;
    Button addBike;
    ImageView imagen;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyBikeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyBikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyBikeFragment newInstance(String param1, String param2) {
        MyBikeFragment fragment = new MyBikeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Añado un objeto FusedLocationClient, que será el encargado de extraer las coordenadas
        fusedLocationClient = LocationServices.
                getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_REQUEST_CODE);
        } else {
            solicitaUbicacion();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_bike, container, false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_REQUEST_CODE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            solicitaUbicacion();
        }
    }

    /**
     * Este método indica la ubicación del dispositivo y la atualiza cada 10 segundos
     */
    @SuppressLint("MissingPermission")
    private void solicitaUbicacion(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) getContext(), locationOnSuccessListener);


    }

    /**
     * Listener que comprueba que la ubicación del dispositivo se obtuvo de forma correcta
     */
    OnSuccessListener<Location> locationOnSuccessListener = new OnSuccessListener<Location>() {
        @Override
        public void onSuccess(Location location) {
            if(location!=null) {
                latitud=location.getLatitude();
                longitud=location.getLongitude();
                updateIU();
            }
        }
    };

    /**
     * Listener que actualiza las coordenadas
     */
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);
                ultimaUbicacion = location;
                latitud=location.getLatitude();
                longitud=location.getLongitude();
                updateIU();
                if (fusedLocationClient != null) {
                    fusedLocationClient.removeLocationUpdates(mLocationCallback);
                }
            }
        }
    };

    /**
     * Actualizo la interfaz del usuario, mostrando las coordenadas en el lugar correspondiente del txt
     */
    private void updateIU(){
        txtLongitud = getActivity().findViewById(R.id.txtxLonguitude);
        txtLatitud = getActivity().findViewById(R.id.txtxLatitude);
        txtLongitud.setText(""+ longitud);
        txtLatitud.setText(""+ latitud);
        addBikeList();
    }

    public void addBikeList(){
        nombre = getView().findViewById(R.id.txtNombre);
        direccion = getView().findViewById(R.id.txtxAddress);
        ciudad = getView().findViewById(R.id.txtxCity);
        descripcion = getView().findViewById(R.id.txtxDescription);
        imagen = getView().findViewById(R.id.imgMyBike);
        imagen.setImageResource(R.drawable.bici);
        addBike = getView().findViewById(R.id.btnAddMyBike);
        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarFirebase();
                Bike b = new Bike();
                b.setOwner(nombre.getText().toString());
                b.setCity(direccion.getText().toString());
                b.setCity(ciudad.getText().toString());
                b.setDescription(descripcion.getText().toString());
                b.setLongitude(longitud);
                b.setLatitude(latitud);
                //Añado una imagen de la base de datos de manera manual
                b.setImage("gs://sharemybike-db97d.appspot.com/bici.jpg");
                //Creo de forma automática la id del elemento del listado
                b.setId(UUID.randomUUID().toString());
                databaseReference.child("bikes_list").child(b.getId()).setValue(b);
                //BikeFragment.list.add(b);

                //Una vez clicado el botón, elimino los datos escritos
                direccion.setText("");
                ciudad.setText("");
                descripcion.setText("");
            }
        });
    }

    /**
     * Método que inicializa el Firebase
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}