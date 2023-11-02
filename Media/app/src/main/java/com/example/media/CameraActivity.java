package com.example.media;

import android.content.pm.ActivityInfo;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

import java.io.File;
import java.io.FileOutputStream;
import android.hardware.Camera.Size;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraActivity extends Activity {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    private View shotBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_camera);

        preview = (SurfaceView) findViewById(R.id.surfaceCamera);
        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(new MyCallback(this));
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        shotBtn = findViewById(R.id.bCameraShot);
        shotBtn.setOnClickListener(new MyViewListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
         openCamera();
    }

    private void openCamera() {
        try {
            camera = Camera.open();
            // Добавьте код для настройки камеры, например, установку параметров и предварительного просмотра
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyCallback implements SurfaceHolder.Callback{

        Activity host;

        MyCallback(Activity act){
            host=act;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height){}
        @Override
        public void surfaceCreated(SurfaceHolder holder){
            try {
                camera.setPreviewDisplay(holder);
                camera.setPreviewCallback(new MyPreviewCallback());
            }
            catch (IOException e){
                Log.d("myLogs","Ошибка камеры");
                e.printStackTrace();
            }
            Size previewSize = camera.getParameters().getPreviewSize();
            float aspect = (float) previewSize.width / previewSize.height;

            int previewSurfaceWidth = preview.getWidth();
            int previewSurfaceHeight = preview.getHeight();

            LayoutParams lp = preview.getLayoutParams();

            // здесь корректируем размер отображаемого preview, чтобы не было искажений

            if (host.getResources().getConfiguration().orientation !=
                    Configuration.ORIENTATION_LANDSCAPE){
                // портретный вид
                camera.setDisplayOrientation(90);
                lp.height = previewSurfaceHeight;
                lp.width = (int) (previewSurfaceHeight / aspect);
            }
            else {
                // ландшафтный
                camera.setDisplayOrientation(0);
                lp.width = previewSurfaceWidth;
                lp.height = (int) (previewSurfaceWidth / aspect);
            }
            preview.setLayoutParams(lp);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder){}
    }

    class MyViewListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == shotBtn) {
                // либо делаем снимок непосредственно здесь
                // либо включаем обработчик автофокуса

                //camera.takePicture(null, null, null, this);
                camera.autoFocus(new MyAutoFocusCallback());
            }
        }
    }

    class MyAutoFocusCallback implements Camera.AutoFocusCallback{

        @Override
        public void onAutoFocus(boolean paramBoolean, Camera paramCamera){
            if (paramBoolean){
                // если удалось сфокусироваться, делаем снимок
                paramCamera.takePicture(null, null, null, new MyPictureCallback());
            }
        }
    }

    class MyPictureCallback implements Camera.PictureCallback{

        @Override
        public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera){
            // сохраняем полученные jpg в папке /sdcard/TrainingMedia/
            // имя файла - System.currentTimeMillis()
            try {

                File file = new File("sdcard/TrainingMedia");
                if(!file.exists())
                    file.mkdir();

                String filename = String.format("sdcard/TrainingMedia/%s.jpg", System.currentTimeMillis());
                FileOutputStream os = new FileOutputStream(filename);
                os.write(paramArrayOfByte);
                os.close();
            }
            catch (Exception e) {
                Log.e("camera exc", e.getMessage());
            }

            // после того, как снимок сделан, показ превью отключается.
            // Необходимо включить его
            paramCamera.startPreview();
        }
    }

    class MyPreviewCallback implements Camera.PreviewCallback{

        @Override
        public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
            // здесь можно обрабатывать изображение, показываемое в preview
        }
    }
}