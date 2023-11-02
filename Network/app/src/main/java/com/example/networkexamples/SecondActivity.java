package com.example.networkexamples;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    FileTask fileTask;

    EditText etIdMagazine;
    public Button btnDownload;
    Button btnSee;
    Button btnDelete;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        File baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File folder = new File(baseDir, "Magazines");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        etIdMagazine = findViewById(R.id.etIdMagazine);

        btnDownload = findViewById(R.id.btnDownloadMagazine);
        btnDownload.setOnClickListener(this);
        btnSee = findViewById(R.id.btnSeeMagazine);
        btnSee.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDeleteMagazine);
        btnDelete.setOnClickListener(this);

        btnSee.setEnabled(false);
        btnDelete.setEnabled(false);

        etIdMagazine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Вызывается перед изменением текста
                btnSee.setEnabled(false);
                btnDelete.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Вызывается во время изменения текста
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Вызывается после изменения текста
                // Вы можете выполнять дополнительные операции с новым текстом здесь
            }
        });
    }

    @Override
    public void onClick(View view) {
        String id_magazine = etIdMagazine.getText().toString();
        String filename = "magazine" + id_magazine + ".pdf";
        fileTask = new FileTask(this);
        if(view.getId() == R.id.btnDownloadMagazine)
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Magazines/" + filename);
            if (file.exists()) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Предупреждение");
                alertDialog.setMessage("Файл с именем " + filename + " существует");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                alertDialog.show();
                btnSee.setEnabled(true);
                btnDelete.setEnabled(true);
            }
            else {
                String url = String.format("http://ntv.ifmo.ru/file/journal/%s.pdf", id_magazine);
                fileTask.execute(url);
            }
        }
        if(view.getId() == R.id.btnSeeMagazine)
        {
            fileTask.openPdfFile(filename);
        }

        if(view.getId() == R.id.btnDeleteMagazine)
        {
            fileTask.deletePdffile(filename);
        }

    }
}
