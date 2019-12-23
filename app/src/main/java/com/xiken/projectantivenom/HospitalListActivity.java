package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HospitalListActivity extends AppCompatActivity {
    public static final int HOSPITAL_PERMISION_REQUEST =3;
    public static final String TAG ="volley";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(latitude,longitude,10);
                Log.d(TAG, "onCreate: " + addressList);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.d(TAG, "onCreate:  latitude " + latitude + "longtitude" + longitude);

        }
    }

    public  void checkPermission(){
        if(ContextCompat.checkSelfPermission(HospitalListActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HospitalListActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},HOSPITAL_PERMISION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == HOSPITAL_PERMISION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            checkPermission();
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.d(TAG, "onCreate:  latitude " + latitude + "longtitude" + longitude);

        }
    }
}
