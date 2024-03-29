package com.DoAn_Mobile.Models;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private boolean visited;
    String creator, postid, imageUrl, caption, kitt;
    List<DocumentReference> likes;

    public Post(String creator, String postid, String imageUrl, String caption) {
        this.creator = creator;
        this.postid = postid;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.likes = new ArrayList<>();
        this.kitt = "";
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public String getKitt() {
        return kitt;
    }

    public Post() {
    }

    public void setKitt(String kitt) {
        this.kitt = kitt;
    }

    public List<DocumentReference> getLikes() {
        return likes;
    }

    public void setLikes(List<DocumentReference> likes) {
        this.likes = likes;
    }


    public String getPostid() {
        return postid;
    }

    public String getCreator() {
        return creator;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public String getCaption() {
        return caption;
    }

}
