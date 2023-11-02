package com.example.media;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.Activity;


public class MediaActivity extends Activity implements OnPreparedListener, OnCompletionListener{

    MediaPlayer mediaPlayer;
    CheckBox chbLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        chbLoop = (CheckBox) findViewById(R.id.chb_Loop);
        chbLoop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (mediaPlayer != null)
                    mediaPlayer.setLooping(isChecked);
            }
        });
    }

    public void onClickStart(View view) {
        releaseMP();

        String DATA=((EditText)findViewById(R.id.et_MediaPath)).getText().toString();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(DATA);
            mediaPlayer.setDisplay(((SurfaceView)
                    findViewById(R.id.surfaceView1)).getHolder());
            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            showMessage("Ошибка воспроизведения");
        }

        if (mediaPlayer == null)
            return;

        mediaPlayer.setLooping(chbLoop.isChecked());
        mediaPlayer.setOnCompletionListener(this);
    }

    private void showMessage(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        if (mediaPlayer == null)
            return;
        if(view.getId() == R.id.b_Pause) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }
        if(view.getId() == R.id.b_Resume) {
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();
        }
        if(view.getId() == R.id.b_Stop) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

}