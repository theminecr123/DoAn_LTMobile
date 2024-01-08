package com.DoAn_Mobile.Models;

public class VideoInfo {
    private String url;
    private String title;
    private String description;

    public VideoInfo(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }
    public VideoInfo(String url, String description){
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

