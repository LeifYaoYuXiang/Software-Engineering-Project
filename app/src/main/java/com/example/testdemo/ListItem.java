package com.example.testdemo;

public class ListItem {

    String word1;
    int imageViewid = R.drawable.froyo;

    public ListItem(String w1) {
        word1 = w1;
    }

    public ListItem(String word1, int imageId) {
        this.word1 = word1;
        this.imageViewid = imageId;
    }


    public String getWord1() {
        return word1;
    }
    public int getImageViewid() {
        return imageViewid;
    }
}