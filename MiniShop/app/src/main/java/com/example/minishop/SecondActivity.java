package com.example.minishop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minishop.adapters.CheckedGoodsAdapter;
import com.example.minishop.models.Good;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Good> arr_checked_goods = new ArrayList<Good>();
    private ListView listViewActSecond;
    private CheckedGoodsAdapter checkedGoodsAdapter;
    private LayoutInflater layoutInflater;

    private Button btnBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        arr_checked_goods = getIntent().getParcelableArrayListExtra("MyList");
        listViewActSecond = (ListView) findViewById(R.id.listViewActSecond);
        btnBack = (Button) findViewById(R.id.btnBack);
        TextView textViewActSecond = (TextView) findViewById(R.id.textViewActSecond);
        textViewActSecond.setText("В вашей корзине товаров: " + Integer.toString(arr_checked_goods.size()));
        btnBack.setOnClickListener(this);

        checkedGoodsAdapter = new CheckedGoodsAdapter(this, arr_checked_goods);

        layoutInflater = LayoutInflater.from(this);

        listViewActSecond.setAdapter(checkedGoodsAdapter);

        setTitle("New title");
    }

    @Override
    public void onClick(View view) {
        if(R.id.btnBack == view.getId()) {
            finish();
        }
    }
}
