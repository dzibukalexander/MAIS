package com.example.mynotes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.mynotes.fragments.FragmentAdd;
import com.example.mynotes.fragments.FragmentDel;
import com.example.mynotes.fragments.FragmentShow;
import com.example.mynotes.fragments.FragmentUpdate;

public class MainActivity extends FragmentActivity{
    private static DB db;
    private static Loader loader;

    private PagerTabStrip pagerTabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTextSize(2,40);
        viewPager.setAdapter(adapter); // устанавливаем адаптер


        initDB();
        loader = new Loader(db);
    }

    private void initDB() {
        // открываем подключение к БД
        db = new DB(this);
        db.open();
        //db.delAll(); // если нужно все вернуть к исходному состоянию
        //db.write(); // при каждом запуске дописываем данные из write()
    }
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение к базе данных при выходе
        db.close();
    }
    public static class MyAdapter extends FragmentPagerAdapter {

        static final int PAGE_COUNT = 4;
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentShow(loader);
                case 1:
                    return new FragmentAdd(db, loader);
                case 2:
                    return new FragmentDel(db);
                case 3:
                    return new FragmentUpdate(db);
                default:
                    return null;
            }
        }

        // при необходимости добавляем верхнее меню вкладок с заголовками
        @Override
        public CharSequence getPageTitle(int i) {
            switch (i){
                case 0: return "Show";
                case 1: return "Add";
                case 2: return "Del";
                case 3: return "Update";
                default: return null;
            }
        }
    }
}