package com.example.testdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class NoteDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_NOTE = "create table writeNote ("+
            "id integer primary key autoincrement,"+
            "title String,"+
            "content String )";

    private Context context;

    public NoteDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE);
        Toast.makeText(context, "Successfully Create Databases", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
