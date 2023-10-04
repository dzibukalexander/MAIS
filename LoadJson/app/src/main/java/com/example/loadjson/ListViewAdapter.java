package com.example.loadjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<User>{
    int listLayout;
    ArrayList<User> usersList;
    Context context;

    public ListViewAdapter(Context context,
                           int listLayout,
                           int field,
                           ArrayList<User> usersList) {
        super(context, listLayout, field, usersList);
        this.context = context;
        this.listLayout = listLayout;
        this.usersList = usersList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView name = listViewItem.findViewById(R.id.textViewName);
        TextView email = listViewItem.findViewById(R.id.textViewEmail);

        try{
            name.setText("Имя:  " + usersList.get(position).getName());
            email.setText("Почта:   " + usersList.get(position).getEmail());
        }catch (Exception jex) {
            jex.printStackTrace();
        }
        return listViewItem;

    }
}
