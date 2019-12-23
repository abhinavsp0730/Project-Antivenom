package com.xiken.projectantivenom.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xiken.projectantivenom.R;

public class Emergency extends AppCompatActivity {
    ImageView callAmbulance;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 18 ){
            if (grantResults.length >0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (checkCallPermission()){
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:108"));
                        startActivity(intent);
                    }

                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        callAmbulance =findViewById(R.id.callAmbulance);
        callAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCallPermission()){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:108"));
                    startActivity(intent);
                }else {
                    checkCallPermission();
                }
            }
        });


    }
    public boolean checkCallPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(Emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.CALL_PHONE}, 18);
                return false;

            } else {
                return true;
            }

        }
        return true;

    }
}
