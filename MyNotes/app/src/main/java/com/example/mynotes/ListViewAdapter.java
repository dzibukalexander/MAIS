package com.example.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Note> {
    int listLayout;
    ArrayList<Note> noteList;
    Context context;

    public ListViewAdapter(Context context,
                           int listLayout,
                           int field,
                           ArrayList<Note> noteList) {
        super(context, listLayout, field, noteList);
        this.context = context;
        this.listLayout = listLayout;
        this.noteList = noteList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView txtViewAdapterId = listViewItem.findViewById(R.id.txtViewAdapterId);
        TextView txtViewAdapterDesc = listViewItem.findViewById(R.id.txtViewAdapterDesc);
        try{
            txtViewAdapterId.setText(noteList.get(position).getId().toString());
            txtViewAdapterDesc.setText(noteList.get(position).getDescription());
        }catch (Exception jex) {
            jex.printStackTrace();
        }
        return listViewItem;
    }

    public void addNewDate(ArrayList<Note> noteList) {
        this.noteList.clear();
        this.noteList.addAll(noteList);
    }
}