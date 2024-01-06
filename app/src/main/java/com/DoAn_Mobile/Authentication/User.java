package com.DoAn_Mobile.Authentication;

public class User {
    String id;
    String email;
    String name;
    String imgProfile;
    String gender;
    Boolean isActive;
    String description;
    private Location location;

    public User(String id, String email, String name, String imgProfile, String gender, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.imgProfile = imgProfile;
        this.gender = gender;
        this.isActive = isActive;
    }


    public User() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
