package com.example.mynotes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.mynotes.DB;
import com.example.mynotes.R;

public class FragmentUpdate extends Fragment implements View.OnClickListener{

    private final DB db;
    private EditText editId;
    private EditText editDesc;
    private Button btnUpdate;
    public FragmentUpdate(DB db){
        this.db = db;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentupdate, container, false);
        editId = (EditText)view.findViewById(R.id.editId);
        editDesc = (EditText)view.findViewById(R.id.editDesc);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnUpdate) {
            Integer id_temp = Integer.parseInt(editId.getText().toString());
            editId.setText("");
            String description_temp = editDesc.getText().toString();
            editDesc.setText("");
            db.update(id_temp, description_temp);
        }
    }
}
