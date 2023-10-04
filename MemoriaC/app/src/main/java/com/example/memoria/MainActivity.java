package com.example.memoria;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private GridView mGrid;
    private GridAdapter mAdapter;

    int GRID_SIZE = 4;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGrid = (GridView)findViewById(R.id.field);
        mGrid.setNumColumns(GRID_SIZE);
        mGrid.setEnabled(true);

        mAdapter = new GridAdapter(this, GRID_SIZE, GRID_SIZE);
        mGrid.setAdapter(mAdapter);

        mGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {

                mAdapter.checkOpenCells ();
                mAdapter.openCell (position);

                if (mAdapter.checkGameOver())
                    ShowGameOver();
            }
        });
    }

    private void startGame() {
        // Вызываем activity повторно
        Intent i = new Intent(this, MainActivity.class);
        startActivity (i);
    }
    private void ShowGameOver () {

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        // Заголовок и текст
        alertbox.setTitle("Диалоговое окно. Дзибук Александр");
        alertbox.setMessage("Поздравляем! Игра окончена.\n"+"Хотите выйти?");

        // Добавляем кнопку
        alertbox.setPositiveButton("Начать заново", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                startGame();
            }
        });
        alertbox.setNeutralButton("Ок", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // закрываем текущюю Activity
                System.exit(0);
            }
        });
        // показываем окно
        alertbox.show();
    }

    public void onButtonClick(View view) {
        startGame();
    }

}