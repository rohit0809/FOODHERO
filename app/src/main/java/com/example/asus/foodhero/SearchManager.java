package com.example.asus.foodhero;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.jar.Manifest;

public class SearchManager extends AppCompatActivity {

    Button button,b1;
    TextView textView,t;
    private static final int MY_PERMISSION_REQUEST_LOCATION=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_gps);
        button=(Button)findViewById(R.id.button);
        b1=(Button)findViewById(R.id.button2);
        t=(TextView)findViewById(R.id.textView14);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String str="You are logged in as "+user.getEmail();
        t.setText(str);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchManager.this, RequestManager
                        .class);
                i.putExtra("epuzzle",textView.getText().toString());
                startActivity(i);


            }
        });
        textView=(TextView)findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                if(ContextCompat.checkSelfPermission(SearchManager.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED)
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(SearchManager.this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION))
                    {
                        ActivityCompat.requestPermissions(SearchManager.this,
                                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);

                    }
                    else
                    {
                        ActivityCompat.requestPermissions(SearchManager.this,
                                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
                    }
                }
                else
                {
                    LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        textView.setText(hereLocation(location.getLatitude(), location.getLongitude()));

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(SearchManager.this,"Not found",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(SearchManager.this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            textView.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(SearchManager.this,"Not found",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(SearchManager.this,"No permission granted",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public String hereLocation(double lat, double lon)
    {
        String ourCity="";
        Geocoder geocoder=new Geocoder(SearchManager.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList=geocoder.getFromLocation(lat,lon,1);
            if(addressList.size()>0)
            {
                ourCity=addressList.get(0).getLocality()+" ";
                ourCity+=addressList.get(0).getSubLocality()+" ";
                ourCity+=addressList.get(0).getAddressLine(0)+" ";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ourCity;
    }

}
