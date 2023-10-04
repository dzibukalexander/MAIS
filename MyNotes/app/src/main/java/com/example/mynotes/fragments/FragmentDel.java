package com.example.mynotes.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.mynotes.DB;
import com.example.mynotes.R;

public class FragmentDel extends Fragment implements View.OnClickListener{
    private final DB db;

    private EditText editId;
    private Button btnDelRec;
    private Button btnDelAll;
    public FragmentDel(DB db){
        this.db = db;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdel, container, false);
        editId = (EditText)view.findViewById(R.id.editId);
        btnDelRec = (Button) view.findViewById(R.id.btnDelRec);
        btnDelRec.setOnClickListener(this);
        btnDelAll = (Button) view.findViewById(R.id.btnDelAll);
        btnDelAll.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnDelRec) {
            Integer id_temp = Integer.parseInt(editId.getText().toString());
            editId.setText("");
            db.delRec(id_temp);
        }else if(view.getId() == R.id.btnDelAll) {
            db.delAll();
            AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
            // Указываем Title
            alertDialog.setTitle("Диалоговое окно.Дзибук");
            // Указываем текст сообщение
            alertDialog.setMessage("Данные удалены");
            // Обработчик на нажатие OK
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Код который выполнится после закрытия окна
                }
            });
            // показываем Alert
            alertDialog.show();
        }

    }
}
