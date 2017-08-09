package com.example.alexeysushko.carandpeopleproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexey Sushko
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String nameDB) {
        super(context, nameDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable("+
                "id integer primary key autoincrement, "+
                "name text, "+
                "birth integer, "+
                "adress text, "+
                "marka text, "+
                "model text, "+
                "year_car integer, "+
                "number_serial text, "+
                "number_state text, "+
                "comment text"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
