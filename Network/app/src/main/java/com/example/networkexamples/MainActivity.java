package com.example.networkexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String myurl = "http://www.government.by";
    TextView tvConnection;
    Button btnConnection;

    TextView tvHttpUrlConnection;
    Button btnHttpUrlConnection;

    TextView tvHttpGETrequest;
    Button btnHttpGetRequest;
    TextView tvSocket;
    Button btnSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Пример подключения к сети
        btnConnection = (Button) findViewById(R.id.btnConnection);
        btnConnection.setOnClickListener(this);
        tvConnection = (TextView) findViewById(R.id.tvConnection);

        //HttpURLConnection
        btnHttpUrlConnection = (Button) findViewById(R.id.btnHttpUrlConnection);
        btnHttpUrlConnection.setOnClickListener(this);
        tvHttpUrlConnection = (TextView) findViewById(R.id.tvHttpUrlConnection);

        //HttpGetRequest
        btnHttpGetRequest = (Button) findViewById(R.id.btnHttpGETrequest);
        btnHttpGetRequest.setOnClickListener(this);

        //Socket
        tvSocket = findViewById(R.id.tvSocket);
        socket();

        //Go to the second_actibity
        btnSecondActivity = findViewById(R.id.btnSecondActivity);
        btnSecondActivity.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //Пример подключения к сети
        if (view.getId() == R.id.btnConnection) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // fetch data
                tvConnection.setText("Подключение создано");
            } else {
                // display error
                tvConnection.setText("Ошибка подключеия");
            }
        }
        if (view.getId() == R.id.btnHttpUrlConnection) {
            try {
                downloadUrl(myurl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (view.getId() == R.id.btnHttpGETrequest) {
            httpGetRequest(myurl);
        }

        if(view.getId() == R.id.btnSecondActivity) {
            //переходим с первой на вторую активность
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    }

    //HttpURLConnection
    private void downloadUrl (String myurl) throws IOException {
        // Only display the first 500 characters of the retrieved
        // web page content
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                int len = 500;
                try {
                    URL url = new URL(myurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.d("DEBUG_TAG", "The response is: " + response);
                    is = conn.getInputStream();
                    // Convert the InputStream into a string
                    String contentAsString = readIt(is, len);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHttpUrlConnection.setText(contentAsString);
                        }
                    });
                    // Makes sure that the InputStream is closed after the app is
                    // finished using it.
                } catch (Exception exp) {
                    Log.e("Exception", exp.getMessage());
                    tvHttpUrlConnection.setText("Ошибка HttpURLConnection");
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            // Преобразование полученной информации к типу Srting
            private String readIt (InputStream stream,int len) throws
                    IOException, UnsupportedEncodingException {
                Reader reader = null;
                reader = new InputStreamReader(stream, "UTF-8");
                char[] buffer = new char[len];
                reader.read(buffer);
                return new String(buffer);
            }
        });
        try {
            thread.start();
        }catch (Exception exp){
            Log.e("Exception thread", exp.getMessage());
        }
    }

    //HttpGet запрос
    private void httpGetRequest(String myurl){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(myurl)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("Response");
                                alertDialog.setMessage(responseBody);
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                       //
                                    }
                                });
                                alertDialog.show();
                            }
                        });
                        Log.d("Response of GET request", responseBody);
                    } else {
                        Log.e("GET request failed", "Response code: " + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void socket() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = new Requester();
                requester.run();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSocket.setText("Ответ сервера: " + requester.message);
                    }
                });
            }
        });
        thread.start();
    }
}