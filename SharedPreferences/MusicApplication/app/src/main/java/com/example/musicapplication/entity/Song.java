package com.example.musicapplication.entity;

public class Song {
    private String title;
    private String author;
    private String duration;
    private int imageResId; // ID của hình ảnh

    public Song(String title, String author, String duration, int imageResId) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}