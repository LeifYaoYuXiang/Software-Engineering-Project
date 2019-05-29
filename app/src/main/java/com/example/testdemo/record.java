package com.example.testdemo;

public class record {
    private String type;
    private int time;
    private String finished;
    private int imageViewId;
    private long date;

    public record(String type, int time ,String finished, int imageViewId, long date){
        this.type = type;
        this.time = time;
        this.finished = finished;
        this.imageViewId = imageViewId;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public String getFinished() {
        return finished;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
