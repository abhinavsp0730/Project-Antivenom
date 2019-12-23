package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;

import android.widget.Toast;



import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    CardView button;
    Uri imageUri;
    CardView button1;
    Bitmap bitmap = null;

    public static final String TAG ="volley";
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);

            }else {
                Toast.makeText(MainActivity.this,"Permission denied Cann't open camera",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.snake_is_near_me);
        button1 = findViewById(R.id.forest_department);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)  == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},10);
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    isCamera = true;

                    startActivityForResult(intent, 0);
                }

            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
                return true;
            }
        });
        button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public  static int findLargestIndexNumber(float[] array){
        int max =0;

        for (int i =0;i < array.length;i++){
            if (array[max] < array[i]){
                max  = i;
            }
        }
        return max;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(true){//isCamera should there
            if (requestCode ==0 && resultCode  == Activity.RESULT_OK && data != null){
                Intent intent = new Intent(MainActivity.this,PhotoScreenActivity.class);
                intent.putExtras(data.getExtras());
                startActivity(intent);
            }
        }else {

        if (requestCode ==0 && resultCode == Activity.RESULT_OK && data != null) {
            Intent intent = new Intent(MainActivity.this, PhotoScreenActivity.class);
            intent.putExtra("Uri", data.getData().toString());

            startActivity(intent);
            imageUri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);

//            snakeImageView.setImageBitmap(bitmap);
//            try {
//                  fireBaseVisionImage = FirebaseVisionImage.fromFilePath(MainActivity.this,imageUri);
//            } catch (IOException e) {


        }
        
        }
    }
}
