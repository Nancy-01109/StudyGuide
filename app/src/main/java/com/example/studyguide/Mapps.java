package com.example.studyguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Mapps extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{
    private static final String TAG = "Mapps";
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Location currentlocation;
    private Button add_loc;
    GoogleMap mmap;
    TextView desc,name;

    private static final int REQUESTCODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapps);
        name=findViewById(R.id.desc);
        desc=findViewById(R.id.name);
        add_loc=(Button) findViewById(R.id.add_location);
        add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Markers1Location.class);
                startActivity(intent);
            }
        });


        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(Mapps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            ActivityCompat.requestPermissions(Mapps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }


    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTCODE);
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentlocation = location;
                    Toast.makeText(getApplicationContext(), currentlocation.getLatitude() + " " + currentlocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync((OnMapReadyCallback) Mapps.this);

                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        LatLng latLng = new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude());
        MarkerOptions iAmHere = new MarkerOptions().position(latLng).title("I am here");


        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(iAmHere);

        MyHelper myDB = new MyHelper(this);
        final List<Markers> lls = myDB.listMarkers();





        for (Markers ll : lls) {
            LatLng coordinate = new LatLng(ll.splitLat(), ll.splitLng());
            Marker mymarker = googleMap.addMarker(new MarkerOptions().position(coordinate).title(ll.getName()));
            Toast.makeText(this, "lat " + String.valueOf(ll.splitLat()) + " ,  lng  " + String.valueOf(ll.splitLng()), Toast.LENGTH_SHORT).show();

        }
        googleMap.setOnMarkerClickListener(this);




    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
       MyHelper myDB = new MyHelper(this);
        final List<Markers> lls = myDB.listMarkers();
        for(Markers m:lls)
        {
            LatLng coordinate = new LatLng(m.splitLat(), m.splitLng());
            if(coordinate.equals(marker.getPosition()))
            {
                name.setText(m.getName());
                desc.setText(m.getDesription());


            }

        }

        return false;

    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            switch (requestCode) {
                case REQUESTCODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        fetchLocation();
                    }
                    break;

            }
        }



}




