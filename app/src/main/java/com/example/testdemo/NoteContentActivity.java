package com.example.testdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoteContentActivity extends AppCompatActivity {
    private TextView noteContent;
    private int id;
    private NoteDatabaseOpenHelper noteDatabaseOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);

        this.noteContent = findViewById(R.id.note_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.id = bundle.getInt("id");

        initDataBse(id + 1);
    }

    private void initDataBse(int id) {
        this.noteDatabaseOpenHelper = new NoteDatabaseOpenHelper(this, "Notes.db", null, 2);
        SQLiteDatabase sqLiteDatabase = noteDatabaseOpenHelper.getWritableDatabase();
        String stringid = id+"";
        Cursor cursor = sqLiteDatabase.query("writeNote",new String[]{"content"}, "id like ?",new String[]{stringid}, null, null,null);
        if (cursor.moveToFirst()) {
            do {
                String content = cursor.getString(0);
                this.noteContent.setText(content);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onBackPressed() {
//        this.noteDatabaseOpenHelper=new NoteDatabaseOpenHelper(this,"Notes.db",null,2);
//        SQLiteDatabase db=noteDatabaseOpenHelper.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        Toast.makeText(this, ""+this.noteContent.getText().toString(), Toast.LENGTH_SHORT).show();
//        contentValues.put("content",this.noteContent.getText().toString());
//        String stringId=id+"";
//        db.update("writeNote",contentValues,"id=?",new String[]{stringId});


        Intent intent = new Intent(NoteContentActivity.this,NoteListActivity.class);
        startActivity(intent);
    }
}
