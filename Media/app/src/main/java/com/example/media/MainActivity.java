package com.example.media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    Button bMusic;
    Button bGallery;
    Button bCamera;

    // объявим константу, содержащую код, передаваемый в запрос на получение разрешения,
    // для последующего отслеживания ответа пользователя на запрос
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

        bMusic = (Button)findViewById(R.id.bMusic);
        bMusic.setOnClickListener(this);
        bCamera = (Button)findViewById(R.id.bCamera);
        bCamera.setOnClickListener(this);
        bGallery = (Button)findViewById(R.id.bGallery);
        bGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        if(view.getId() == R.id.bMusic) {
            intent=new Intent(this,MediaActivity.class);
        }
        if(view.getId() == R.id.bGallery) {
            intent=new Intent(this,GalleryActivity.class);
        }
        if(view.getId() == R.id.bCamera) {
            intent=new Intent(this,CameraActivity.class);
        }
        if(intent!=null){
            startActivity(intent);
        }
    }

    private void requestPermissions() {
        String[] permissions = {android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            } else {
                // Permissions already granted
                // Do something here
            }
        } else {
            // Permissions not needed for devices below Marshmallow
            // Do something here
        }
    }

}