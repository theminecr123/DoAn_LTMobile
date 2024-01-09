package com.DoAn_Mobile.Authentication;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    String name;
    String username;
    String email;
    String bio;
    int post;
    int follow;
    String profileImageUrl;
    String backgroundImageUrl;
    String onesignalPlayerId;
    boolean isPrivate;
    Boolean isActive;
    String gender;

    List<DocumentReference> posts;
    List<DocumentReference> saved;
    List<DocumentReference> following;
    List<DocumentReference> followers;
    List<DocumentReference> blockedAccounts;

    private Location location;
    String description;
    public User() {

    }



    public User(String id, String email, String name, String profileImageUrl, String gender, String bio, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.onesignalPlayerId = "";
        this.profileImageUrl = profileImageUrl;
        this.backgroundImageUrl = backgroundImageUrl;
        this.bio = "";
        this.post = 0;
        this.follow = 0;
        this.posts = new ArrayList<>();
        this.saved = new ArrayList<>();
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.blockedAccounts = new ArrayList<>();
        this.isPrivate = false;
        this.isActive = isActive;
        this.gender = gender;


    }

    public String getOnesignalPlayerId() {
        return onesignalPlayerId;
    }

    public void setOnesignalPlayerId(String onesignalPlayerId) {
        this.onesignalPlayerId = onesignalPlayerId;
    }

    public List<DocumentReference> getFollowing() {
        return following;
    }

    public List<DocumentReference> getFollowers() {
        return followers;
    }

    public List<DocumentReference> getBlockedAccounts() {
        return blockedAccounts;
    }

    public List<DocumentReference> getSaved() {
        return saved;
    }

    public List<DocumentReference> getPosts() {
        return posts;
    }

    public String getId() {
        return id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getName() {
        return name;
    }


    public String getBio() {
        return bio;
    }
    public String getEmail() {
        return email;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPosts(List<DocumentReference> posts) {
        this.posts = posts;
    }

    public void setSaved(List<DocumentReference> saved) {
        this.saved = saved;
    }

    public void setFollowing(List<DocumentReference> following) {
        this.following = following;
    }

    public void setFollowers(List<DocumentReference> followers) {
        this.followers = followers;
    }

    public void setBlockedAccounts(List<DocumentReference> blockedAccounts) {
        this.blockedAccounts = blockedAccounts;
    }
}
