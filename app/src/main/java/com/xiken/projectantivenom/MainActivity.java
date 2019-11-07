package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button button;
    Uri imageUri;
    Button button1;
    Bitmap bitmap = null;

    public static final String TAG ="volley";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.verify);
        button1 = findViewById(R.id.button1);
        Log.d(TAG, "onCreate: started");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseVisionImage fireBaseVisionImage  = null;
        if (requestCode ==0 && resultCode == Activity.RESULT_OK && data != null){
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//            try {
//                  fireBaseVisionImage = FirebaseVisionImage.fromFilePath(MainActivity.this,imageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
//                    .getOnDeviceImageLabeler();
//            labeler.processImage(fireBaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
//                @Override
//                public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
//                    Log.d(TAG, "onSuccess: success");
//                    for (FirebaseVisionImageLabel label : firebaseVisionImageLabels){
//                        String text = label.getText();
//                        String entityId = label.getEntityId();
//                        float confidence = label.getConfidence();
//                        Log.d(TAG, "onSuccess: text " + text);
//                        Log.d(TAG, "onSuccess: confidence "+ confidence);
//                        Log.d(TAG, "onSuccess: entityId "+ entityId);
//
//                    }
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "onFailure: failure");
//
//                }
//            });


                    //For cloud database
//                    FirebaseCustomRemoteModel remoteModel = new FirebaseCustomRemoteModel.Builder("projectav").build();
//                    FirebaseModelDownloadConditions downloadConditions  = new FirebaseModelDownloadConditions.Builder().requireWifi()
//                            .build();
//            Log.d(TAG, "onActivityResult: started");
//                    FirebaseModelManager.getInstance().download(remoteModel,downloadConditions)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: firebase Download manager");
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure: firebase download manager");
//                        }
//                    });
//            Log.d(TAG, "onActivityResult: finished");
//                    //For local database
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
                            Log.d(TAG, "onSuccess: firebaseModeOutputs");
                            float[][] output = firebaseModelOutputs.getOutput(0);


//                            Log.d(TAG, "onSuccess: OUuuutput" + output[0]);

                            float[] probabilities =  output[0];
                            Log.d(TAG, "onSuccess: output" + output);
//                            for (int i =0;i < probabilities.length;i++){
//                                Log.d(TAG, "onSuccess: probability"+ probabilities[i]);
//                            }

//                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("retrained_labels.txt")));
                                Log.d(TAG, "onSuccess: largestIndexNumber " + findLargestIndexNumber(probabilities));
                                for (int i =0; i < probabilities.length;i++){
//                                    String label = bufferedReader.readLine();
                                    Log.d(TAG, "onSuccess: " +probabilities[i]);

                                }



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: firebaseModelOutputs "+ e.getMessage());
                            e.printStackTrace();

                        }
                    });







            }



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




}
