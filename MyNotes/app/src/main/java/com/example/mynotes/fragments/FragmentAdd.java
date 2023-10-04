package com.example.mynotes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mynotes.DB;
import com.example.mynotes.ListViewAdapter;
import com.example.mynotes.Loader;
import com.example.mynotes.Note;
import com.example.mynotes.R;

import java.util.ArrayList;

public class FragmentAdd extends Fragment implements View.OnClickListener {
    private final DB db;
    private EditText etDesc;
    private Loader loader;
    public FragmentAdd(DB db, Loader loader){
        super(R.layout.fragmentadd);
        this.db = db;
        this.loader = loader;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        etDesc = view.findViewById(R.id.editDesc);
        btnAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnAdd) {
            String description_temp = etDesc.getText().toString();
            db.addRec(description_temp);
            etDesc.setText("");

            ArrayList<Note> arrayList = loader.loadDataFromDB();
            ListView lv = (ListView) getActivity().findViewById(R.id.listView);
            ListViewAdapter lva = (ListViewAdapter) lv.getAdapter();
            lva.addNewDate(arrayList);
            lva.notifyDataSetChanged();
        }
    }
}
