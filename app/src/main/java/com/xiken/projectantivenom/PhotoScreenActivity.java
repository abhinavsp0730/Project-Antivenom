package com.xiken.projectantivenom;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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

import java.io.IOException;

import static com.xiken.projectantivenom.MainActivity.findLargestIndexNumber;
import static com.xiken.projectantivenom.ui.home.HomeFragment.hasCamera;
import static com.xiken.projectantivenom.ui.home.HomeFragment.intentNUmber;


public class PhotoScreenActivity extends AppCompatActivity {
    Bitmap bitmap;
    Uri imageUri;

    ImageView snakeImageView;
    public  static TextView snakeName;
    public  static TextView isPoisinous;
    Button findHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_screen);
        snakeImageView = findViewById(R.id.snakeImageView);
        snakeName = findViewById(R.id.snakeName);
        isPoisinous = findViewById(R.id.poisionus);
        findHospital = findViewById(R.id.find_hospital);
        getSupportActionBar().setTitle("Project AntiVenom");
        findHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoScreenActivity.this,ForrestDepartment.class);
                startActivity(intent);
            }
        });
        if (intentNUmber == 1) {
            getCameraActivity();
        } else {
            Intent intent = getIntent();
            String uri = intent.getStringExtra("Uri");


            imageUri = Uri.parse(uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);


            snakeImageView.setImageBitmap(bitmap);


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
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 192, 192, 3})//1,192,192,3
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 13})//1,13
                        .build();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
//                    Bitmap bitmap1 = getYourInputImage();
            bitmap = Bitmap.createScaledBitmap(bitmap, 192, 192, true);
            int batchNum = 0;
            float[][][][] input = new float[1][192][192][3];
            for (int i = 0; i < 192; i++) {
                for (int j = 0; j < 192; j++) {
                    int pixel = bitmap.getPixel(i, j);
                    input[batchNum][i][j][0] = (Color.red(pixel) - 127) / 128.0f;
                    input[batchNum][i][j][1] = (Color.green(pixel) - 127) / 128.0f;
                    input[batchNum][i][j][2] = (Color.blue(pixel) - 127) / 128.0f;

                }
            }
            FirebaseModelInputs inputs = null;
            try {
                inputs = new FirebaseModelInputs.Builder().add(input).build();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
            firebaseModelInterpreter.run(inputs, firebaseModelInputOutputOptions).addOnSuccessListener(new OnSuccessListener<FirebaseModelOutputs>() {
                @Override
                public void onSuccess(FirebaseModelOutputs firebaseModelOutputs) {

                    float[][] output = firebaseModelOutputs.getOutput(0);


                    float[] probabilities = output[0];


                    selectSnakeName(findLargestIndexNumber(probabilities));
                    for (int i = 0; i < probabilities.length; i++) {
//                                    String label = bufferedReader.readLine();

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
        public static void selectSnakeName ( int index){
            switch (index) {
                case 0:
                    snakeName.setText("Banded Racer");
                    isPoisinous.setText("No");
                    break;
                case 1:
                    snakeName.setText("Checkered keelback");
                    isPoisinous.setText("No");
                    break;
                case 2:
                    snakeName.setText("Common Krait");
                    isPoisinous.setText("Yes");
                    break;
                case 3:
                    snakeName.setText("Common Rat snake");
                    isPoisinous.setText("No");
                    break;
                case 4:
                    snakeName.setText("Common sand Boa");
                    isPoisinous.setText("No");
                    break;
                case 5:
                    snakeName.setText("Common Trinket");
                    isPoisinous.setText("No");
                    break;
                case 6:
                    snakeName.setText("Green Tree Vine");
                    isPoisinous.setText("No");
                    break;
                case 7:
                    snakeName.setText("Indian Rock Python");
                    isPoisinous.setText("No");
                    break;
                case 8:
                    snakeName.setText("King cobra");
                    isPoisinous.setText("Yes");
                    break;
                case 9:
                    snakeName.setText("Monocled Cobra");
                    isPoisinous.setText("Yes");
                    break;
                case 10:
                    snakeName.setText("Russell's Viper");
                    isPoisinous.setText("Yes");
                    break;
                case 11:
                    snakeName.setText("Saw-scaled Viper");
                    isPoisinous.setText("Yes");
                    break;
                case 12:
                    snakeName.setText("Spectacled Cobra");
                    isPoisinous.setText("Yes");
                    break;
            }



        }
        public void getCameraActivity () {
            Intent intent = getIntent();
            String data = intent.getStringExtra("Uri1");
            Bundle uri = intent.getExtras();

           bitmap = (Bitmap) uri.get("data");
           snakeImageView.setImageBitmap(bitmap);


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
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 192, 192, 3})//1,192,192,3
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 13})//1,13
                        .build();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
//                    Bitmap bitmap1 = getYourInputImage();
            bitmap = Bitmap.createScaledBitmap(bitmap, 192, 192, true);
            int batchNum = 0;
            float[][][][] input = new float[1][192][192][3];
            for (int i = 0; i < 192; i++) {
                for (int j = 0; j < 192; j++) {
                    int pixel = bitmap.getPixel(i, j);
                    input[batchNum][i][j][0] = (Color.red(pixel) - 127) / 128.0f;
                    input[batchNum][i][j][1] = (Color.green(pixel) - 127) / 128.0f;
                    input[batchNum][i][j][2] = (Color.blue(pixel) - 127) / 128.0f;

                }
            }
            FirebaseModelInputs inputs = null;
            try {
                inputs = new FirebaseModelInputs.Builder().add(input).build();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }
            firebaseModelInterpreter.run(inputs, firebaseModelInputOutputOptions).addOnSuccessListener(new OnSuccessListener<FirebaseModelOutputs>() {
                @Override
                public void onSuccess(FirebaseModelOutputs firebaseModelOutputs) {
                    float[][] output = firebaseModelOutputs.getOutput(0);


//                            Log.d(TAG, "onSuccess: OUuuutput" + output[0]);

                    float[] probabilities = output[0];





                    selectSnakeName(findLargestIndexNumber(probabilities));
                    for (int i = 0; i < probabilities.length; i++) {


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









