package com.example.asus.foodhero;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Double longit, lati, vlat, vlong;
    public  int lon, lat;
public  String vlati,vlongi,vlato,vlongo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       longit=getIntent().getDoubleExtra("longit",0);
        lati=getIntent().getDoubleExtra("lati",0);

lat=Integer.parseInt(getIntent().getStringExtra("vlat"));
        lon=Integer.parseInt(getIntent().getStringExtra("vlon"));
       // Toast.makeText(this,lat+"",Toast.LENGTH_LONG).show();
      //  Toast.makeText(this,lon+"",Toast.LENGTH_LONG).show();
        // Obtain the SupportapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Toast.makeText(MapsActivity.this,lat,Toast.LENGTH_LONG).show();
        Double laty=lati+0.012;

        LatLng volloc = new LatLng(laty,longit+0.012);

        LatLng donloc = new LatLng(lati, longit);
     mMap.addMarker(new MarkerOptions().position(volloc).title("Volunteer Location"));
        mMap.addMarker(new MarkerOptions().position(donloc).title("Donor LOCATION"));
   //   mMap.moveCamera(CameraUpdateFactory.newLatLng(volloc));
    //    mMap.moveCamera(CameraUpdateFactory.newLatLng(donloc));
        float zoomLevel = 14; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(volloc, zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(donloc, zoomLevel));

    }
}