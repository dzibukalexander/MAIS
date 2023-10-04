package com.example.loadjson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String JSON_URL = "https://dzibukalexander.github.io/json/file.json";

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        Button btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                User user = (User)listView.getAdapter().getItem(position);
                Integer id_user = user.getId();
                String name = user.getName();
                String email = user.getEmail();
                String location = user.getLocation();
                if(name == null || location == null || email == null) return;

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                // Указываем Title
                alertDialog.setTitle("Пользователь с id = " + id_user);

                // Указываем текст сообщение
                alertDialog.setMessage(
                        "Имя: " + name + '\n' +
                        "Почта: " + email + '\n' +
                        "Местоположение: " + location
                );
                // Обработчик на нажатие OK
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Код который выполнится после закрытия окна
                    }
                });
                // показываем Alert
                alertDialog.show();
            }
        });
    }


    private void  loadJSONFromURL(String url){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener< String>(){
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("users");
                    ArrayList<User> listItems = getArrayListFromJSONArray(jsonArray);
                    ListAdapter adapter = new ListViewAdapter(getApplicationContext(),R.layout.row,R.id.textViewName,listItems);
                    listView.setAdapter(adapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private ArrayList<User> getArrayListFromJSONArray(JSONArray jsonArray) {
        ArrayList<User> arrayList = new ArrayList<User>();
        try{
            if(jsonArray != null) {
                for(int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Integer id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    String location = jsonObject.getString("location");

                    User user = new User(id, name, email , location);
                    arrayList.add(user);
                }
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLoad){
            loadJSONFromURL(JSON_URL);
        }
    }
}