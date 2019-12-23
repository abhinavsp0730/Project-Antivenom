package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.Toast;

public class ForrestDepartment extends AppCompatActivity {
    ImageView callForestDepartment;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:12345"));
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            startActivity(intent);
        }
        else {
            Toast.makeText(ForrestDepartment.this,"Permission denied",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forrest_department);
        callForestDepartment = findViewById(R.id.callForestDepartment);
        callForestDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(ForrestDepartment.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                    }else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:9337295142"));
                        startActivity(intent);


                    }


                }else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:12345"));
                    startActivity(intent);

                }
            }
        });
    }


}
