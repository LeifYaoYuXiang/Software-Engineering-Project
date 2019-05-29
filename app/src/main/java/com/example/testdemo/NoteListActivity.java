package com.example.testdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private ListView notesLists;
    private NoteDatabaseOpenHelper noteDatabaseOpenHelper;
    private List<Note> noteList=new ArrayList<>();
    private List<String> titles=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        this.notesLists=findViewById(R.id.notesLists);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText=new EditText(NoteListActivity.this);
                AlertDialog.Builder inputDialog=new AlertDialog.Builder(NoteListActivity.this);
                inputDialog.setTitle("请输入该笔记的标题").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title=editText.getText().toString();
                                Bundle bundle=new Bundle();
                                bundle.putString("title",title);
                                Intent intent=new Intent(NoteListActivity.this,WriteNoteActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                Toast.makeText(NoteListActivity.this, "写下自己的笔记", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        SearchView searchView=findViewById(R.id.searchForNote);
        searchView.setSubmitButtonEnabled(true);

        noteDatabaseOpenHelper=new NoteDatabaseOpenHelper(this,"Notes.db",null,2);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                SQLiteDatabase sqLiteDatabase=noteDatabaseOpenHelper.getWritableDatabase();
                noteList=new ArrayList<>();
                Cursor cursor=sqLiteDatabase.query("writeNote",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String title=cursor.getString(cursor.getColumnIndex("title"));
                        if(title.contains(s)){
                            int id=cursor.getInt(cursor.getColumnIndex("id"));
                            Note newNote=new Note(title,id);
                            noteList.add(newNote);
                        }
                    }while(cursor.moveToNext());
                }
                cursor.close();

                final NoteAdapter noteAdapter=new NoteAdapter(NoteListActivity.this,R.layout.note_item,noteList);
                notesLists.setAdapter(noteAdapter);
                notesLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                        Note temp=noteAdapter.getItem(i);

                        int id=temp.getId()-1;

                        Bundle bundle=new Bundle();
                        bundle.putInt("id",id);

                        Intent intent=new Intent(NoteListActivity.this,NoteContentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SQLiteDatabase sqLiteDatabase=noteDatabaseOpenHelper.getWritableDatabase();
                noteList=new ArrayList<>();
                Cursor cursor=sqLiteDatabase.query("writeNote",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String title=cursor.getString(cursor.getColumnIndex("title"));
                        if(title.contains(s)){
                            int id=cursor.getInt(cursor.getColumnIndex("id"));
                            Note newNote=new Note(title,id);
                            noteList.add(newNote);
                        }
                    }while(cursor.moveToNext());
                }
                cursor.close();

                final NoteAdapter noteAdapter=new NoteAdapter(NoteListActivity.this,R.layout.note_item,noteList);
                notesLists.setAdapter(noteAdapter);
                notesLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                        Note temp=noteAdapter.getItem(i);

                        int id=temp.getId()-1;

                        Bundle bundle=new Bundle();
                        bundle.putInt("id",id);

                        Intent intent=new Intent(NoteListActivity.this,NoteContentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(NoteListActivity.this,MainActivity.class);
        startActivity(intent);
    }


    private void initNote(){
        this.noteDatabaseOpenHelper=new NoteDatabaseOpenHelper(this,"Notes.db",null,2);
        SQLiteDatabase sqLiteDatabase=noteDatabaseOpenHelper.getWritableDatabase();

        Cursor cursor=sqLiteDatabase.query("writeNote",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                this.titles.add(title);

                Note newNote=new Note(title,id);
                this.noteList.add(newNote);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.noteList=new ArrayList<>();
        initNote();

        final NoteAdapter noteAdapter=new NoteAdapter(NoteListActivity.this,R.layout.note_item,noteList);
        notesLists.setAdapter(noteAdapter);
        notesLists.setTextFilterEnabled(true);

        notesLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                Note temp=noteAdapter.getItem(i);

                int id=temp.getId()-1;

                Bundle bundle=new Bundle();
                bundle.putInt("id",id);

                Intent intent=new Intent(NoteListActivity.this,NoteContentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        notesLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                AlertDialog.Builder aBuider=new AlertDialog.Builder(NoteListActivity.this);
                aBuider.setTitle("警告！");
                aBuider.setMessage("确认删除这条笔记么？");
                aBuider.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Note temp=noteAdapter.getItem(i);

                        int id=temp.getId();

                        SQLiteDatabase sqLiteDatabase=noteDatabaseOpenHelper.getWritableDatabase();
                        sqLiteDatabase.delete("writeNote","id=?",new String[]{id+""});

                        Intent intent=new Intent(NoteListActivity.this,NoteListActivity.class);
                        startActivity(intent);

                    }
                });

                aBuider.setNegativeButton("取消删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                aBuider.show();

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.noteList=new ArrayList<>();
        initNote();
    }
}
