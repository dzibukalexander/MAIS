package com.example.minishop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.minishop.R;
import com.example.minishop.interfaces.OnChangeListener;
import com.example.minishop.models.Good;

import java.util.ArrayList;

public class CheckedGoodsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Good> arr_goods_adapter;
    private LayoutInflater layoutInflater;
    public CheckedGoodsAdapter(Context context, ArrayList<Good> arr_goods_adapter) {
        this.context = context;
        this.arr_goods_adapter = arr_goods_adapter;
        this.layoutInflater = LayoutInflater.from(context);
    }
    // количество элементов
    @Override
    public int getCount() {
        return arr_goods_adapter.size();
    }
    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return arr_goods_adapter.get(position);
    }
    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }
    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_good, null, false);
        }
        Good good_temp = arr_goods_adapter.get(position);
        TextView tv_goodId = (TextView) view.findViewById(R.id.textViewId);
        tv_goodId.setText(Integer.toString(good_temp.getId()));
        TextView tv_goodName = (TextView) view.findViewById(R.id.textViewName);
        tv_goodName.setText(good_temp.getName());
        TextView tv_googPrice = (TextView) view.findViewById(R.id.textViewPrice);
        tv_googPrice.setText("Цена "+ Double.toString(good_temp.getPrice()) + "руб.");
        CheckBox cb_good = (CheckBox) view.findViewById(R.id.cb_good);
        cb_good.setChecked(true);
        cb_good.setTag(position);
        cb_good.setEnabled(false);
        return view;
    }
}
