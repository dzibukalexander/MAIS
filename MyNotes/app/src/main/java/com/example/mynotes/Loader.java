package com.example.mynotes;

import android.database.Cursor;

import java.util.ArrayList;

public class Loader {
    private DB db;
    private Cursor cursor;
    public  Loader(DB db) {
        this.db = db;
    }
    public ArrayList<Note> loadDataFromDB() {
        ArrayList<Note> arrayList = new ArrayList<Note>();
        cursor = db.getAllData();
        // ставим позицию курсора на первую строку выборкиSS
        // если в выборке нет строк, вернется falseS
        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("id");
            int descriptionColIndex = cursor.getColumnIndex("description");
            do {
                // получаем значения по номерам столбцов и пишем в лог
                int id = cursor.getInt(idColIndex);
                String description = cursor.getString(descriptionColIndex);
                Note note = new Note(id, description);
                arrayList.add(note);
                // переход на следующую строку, а если следующей нет (текущая - последняя), то выходим из цикла
            } while (cursor.moveToNext());
            cursor.close();
        }
        return arrayList;
    }
}
