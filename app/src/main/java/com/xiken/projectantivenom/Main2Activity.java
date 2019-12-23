package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.custom.FirebaseCustomLocalModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import static com.xiken.projectantivenom.MainActivity.findLargestIndexNumber;

public class Main2Activity extends AppCompatActivity {
    Button cameraButton;
    ImageView imageView;
    Button analyze;

    Uri uri;
    Bitmap bitmap = null;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else {
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==0 && resultCode == Activity.RESULT_OK && data != null) {

            bitmap  = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            final FirebaseCustomLocalModel firebaseLocalModel = new FirebaseCustomLocalModel.Builder().setAssetFilePath("model.tflite").build();
            FirebaseModelInterpreter firebaseModelInterpreter = null;
            FirebaseModelInterpreterOptions firebaseModelInterpreterOptions = new FirebaseModelInterpreterOptions.Builder(firebaseLocalModel).build();
            try {
                firebaseModelInterpreter = FirebaseModelInterpreter.getInstance(firebaseModelInterpreterOptions);
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
            FirebaseModelInputOutputOptions firebaseModelInputOutputOptions = null;
            try {
                firebaseModelInputOutputOptions = new FirebaseModelInputOutputOptions.Builder()
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32,new int[]{1,192,192,3})//1,192,192,3
                        .setOutputFormat(0,FirebaseModelDataType.FLOAT32,new int[]{1,13})//1,13
                        .build();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
//                    Bitmap bitmap1 = getYourInputImage();
            bitmap = Bitmap.createScaledBitmap(bitmap,192,192,true);
            int batchNum =0;
            float [][][][]  input = new float[1][192][192][3];
            for (int i =0;i < 192;i++){
                for (int j =0;j < 192;j++){
                    int pixel = bitmap.getPixel(i,j);
                    input[batchNum][i][j][0] = (Color.red(pixel ) -127)/128.0f;
                    input[batchNum][i][j][1] =(Color.green(pixel) -127) /128.0f;
                    input[batchNum][i][j][2] = (Color.blue(pixel) -127)/128.0f;

                }
            }
            FirebaseModelInputs inputs = null;
            try {
                inputs = new FirebaseModelInputs.Builder().add(input).build();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
            firebaseModelInterpreter.run(inputs,firebaseModelInputOutputOptions).addOnSuccessListener(new OnSuccessListener<FirebaseModelOutputs>() {
                @Override
                public void onSuccess(FirebaseModelOutputs firebaseModelOutputs) {
                    float[][] output = firebaseModelOutputs.getOutput(0);



                    float[] probabilities =  output[0];

                        for (int i =0; i < probabilities.length;i++) {
//                                String label =
                        }

                        }




            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();

                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cameraButton = findViewById(R.id.camera_button1);
        imageView = findViewById(R.id.imageView);
        analyze = findViewById(R.id.analyze);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)  == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.CAMERA},10);
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



                    startActivityForResult(intent, 0);
                }


            }

            
        });
        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {








            }






        });

//        

    }


}
