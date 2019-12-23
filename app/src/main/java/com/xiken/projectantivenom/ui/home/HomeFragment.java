package com.xiken.projectantivenom.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;




import com.xiken.projectantivenom.PhotoScreenActivity;
import com.xiken.projectantivenom.R;


import java.io.IOException;
import java.security.acl.Permission;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CardView button;
    Uri imageUri;
    CardView button1;
    Bitmap bitmap = null;
    public static boolean hasCamera = false;
    CardView plant;
    public  static int intentNUmber;
//    DatabaseReference databaseReference;
    ImageView cardViewImageView;








    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.activity_main, container, false);

        //main method
        button = root.findViewById(R.id.snake_is_near_me);
        button1 = root.findViewById(R.id.forest_department);
//        plant = root.findViewById(R.id.plant);
//        cardViewImageView = root.findViewById(R.id.cardview_image_view);
//        cardViewImageView.setBackgroundResource(R.raw.one);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    new AlertDialog.Builder(getContext())
                            .setIcon(R.drawable.ic_warning_alert)
                            .setTitle("Alert")
                            .setMessage("Images without snakes develop random results.\nThis feature only works when the image contains a snake.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    hasCamera = true;
                                    intentNUmber = 1;



                                    startActivityForResult(intent, 0);

                                }
                            }).show();

                }

            }
        });
        final int[] arrayOfImage ={R.raw.one,R.raw.two,R.raw.three,R.raw.four,R.raw.five};
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                hasCamera = false;
                intentNUmber = 2;
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, 0);
                return true;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), Emergency.class);
                startActivity(intent);


            }
        });
//        plant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intentNUmber = 3;
//                if (ContextCompat.checkSelfPermission(root.getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 101);
//                }else{
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent,10);
//
//                }
//
//
//            }
//        });

//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            int i =0;
//            @Override
//            public void run() {
//                cardViewImageView.setBackgroundResource(arrayOfImage[i]);
//                i++;
//                if (i > arrayOfImage.length-1){
//                    i = 0;
//                }
//                handler.postDelayed(this,4000);
//
//            }
//        };
//        handler.postDelayed(runnable,4000);



                return root;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(intentNUmber  == 1 ){
            if (requestCode ==0 && resultCode  == Activity.RESULT_OK && data != null){
                Intent intent = new Intent(getContext(),PhotoScreenActivity.class);

                intent.putExtras(data.getExtras());
                startActivity(intent);
            }
        }else if (intentNUmber == 2){

            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                Intent intent = new Intent(getContext(), PhotoScreenActivity.class);
                intent.putExtra("Uri", data.getData().toString());

                startActivity(intent);
                imageUri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            }
        }else if (intentNUmber == 3){
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
                Intent intent = new Intent(getContext(),PlantDetction.class);
                intent.putExtras(data.getExtras());
                startActivity(intent);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("volley", "onRequestPermissionsResult:called ");
        if (requestCode == 101 ) {
            if (grantResults.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    hasCamera = true;



                    startActivityForResult(intent,0);
                    Log.d("volley", "onRequestPermissionsResult: success");

                }
                else {
                    Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_LONG).show();
                    Log.d("volley", "onRequestPermissionsResult: failed");


                }
            }



        }
    }
}