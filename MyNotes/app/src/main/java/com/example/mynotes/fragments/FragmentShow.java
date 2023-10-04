package com.example.mynotes.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mynotes.DB;
import com.example.mynotes.ListViewAdapter;
import com.example.mynotes.Loader;
import com.example.mynotes.Note;
import com.example.mynotes.R;

import java.util.ArrayList;

public class FragmentShow extends Fragment {

    private ListView listView;
    private Loader loader;
    public FragmentShow(Loader loader) {
        this.loader = loader;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentshow, container, false);
        listView = view.findViewById(R.id.listView);
        ArrayList<Note> listNotes = loader.loadDataFromDB();
        ListViewAdapter adapter = new ListViewAdapter(getContext().getApplicationContext(), R.layout.adapter_item,0,listNotes);
        listView.setAdapter(adapter);
        return view;
    }
}
