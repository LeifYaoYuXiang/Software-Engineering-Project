package com.example.testdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class WriteNoteActivity extends AppCompatActivity {
    protected EditText noteswritten;
    protected String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.title = bundle.getString("title");
        noteswritten = findViewById(R.id.noteWritten);
    }

    @Override
    public void onBackPressed() {
        String content = noteswritten.getText().toString();
        NoteDatabaseOpenHelper noteDatabaseOpenHelper = new NoteDatabaseOpenHelper(this, "Notes.db", null, 2);

        SQLiteDatabase sqLiteDatabase = noteDatabaseOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);

        sqLiteDatabase.insert("writeNote", null, contentValues);
        Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }
}
