package com.example.testdemo;

public class Note {
    private String title;
    private int id;

    public Note(String title) {
        this.title = title;
    }

    public Note(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
