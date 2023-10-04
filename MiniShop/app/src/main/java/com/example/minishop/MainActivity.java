package com.example.minishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minishop.adapters.GoodsAdapter;
import com.example.minishop.interfaces.OnChangeListener;
import com.example.minishop.models.Good;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnChangeListener, View.OnClickListener  {

    private ArrayList<Good> arr_checked_goods = new ArrayList<Good>();
    private LayoutInflater layoutInflater;
    private View view_header, view_footer;
    private Button btnShow;
    private TextView tv_count;
    private ListView listView;
    private ArrayList<Good> arr_goods = new ArrayList<Good>();
    private final int SIZE_OF_ARR = 15;
    private GoodsAdapter goodsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createMyListView();
    }
    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }
    private void createMyListView() {
        fillData();
        goodsAdapter = new GoodsAdapter(this, arr_goods, this);

        layoutInflater = LayoutInflater.from(this);
        view_header = layoutInflater.inflate(R.layout.header_mygoods, null);
        view_footer = layoutInflater.inflate(R.layout.footer_mygoods, null);
        btnShow = (Button) view_footer.findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        tv_count = (TextView) view_footer.findViewById(R.id.tv_count);
        listView.addHeaderView(view_header);
        listView.addFooterView(view_footer);

        listView.setAdapter(goodsAdapter);
    }

    private void fillData(){
        int i=0;
        while (i<SIZE_OF_ARR) {
            i++;
            int random_int = new Random().nextInt(100);
            double random_double = new Random().nextDouble();
            double price = random_int + random_double;

            BigDecimal bd = new BigDecimal(Double.toString(price));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            arr_goods.add(new Good(i," " + "My good №" + i, bd.doubleValue(),false));
        }
    }

    @Override
    public void onClick(View view) {
        arr_checked_goods = goodsAdapter.getCheckedGoods();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putParcelableArrayListExtra("MyList", arr_checked_goods);
        startActivity(intent);
    }

    @Override
    public void onDataChanged() {
        int size = goodsAdapter.getCheckedGoods().size();
        tv_count.setText("Count of goods = " + size);
    }
}
