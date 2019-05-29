package com.example.testdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    private int resourceId;

    public NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);
        View view =  LayoutInflater.from(getContext()).inflate(resourceId, parent,false);

        TextView textView = view.findViewById(R.id.note_title);
        textView.setText(note.getTitle());


        return view;
    }
}
