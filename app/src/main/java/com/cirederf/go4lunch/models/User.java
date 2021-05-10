package com.cirederf.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    // todo determine if just the restaurant with it's placeId is necessary (chosenRestaurant)
    //  or if we need a boolean (isChosenRestaurant) to compare data
    private String uid;
    private String username;
    @Nullable
    private String urlPicture;
    private Restaurant chosenRestaurant;
    private Boolean isChosenRestaurant;

    public User() { }

    //------------FIREBASE CONSTRUCTOR-----------
    public User(String uid, String username, @Nullable String urlPicture, Restaurant chosenRestaurant, Boolean isChosenRestaurant) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = chosenRestaurant;
        this.isChosenRestaurant = isChosenRestaurant;
    }

    //---------GETTERS----------
    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public Restaurant getChosenRestaurant() {
        return chosenRestaurant;
    }

    public Boolean getIsChosenRestaurant() {
        return isChosenRestaurant;
    }

    //------------SETTERS-----------
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public void setChosenRestaurant(Restaurant chosenRestaurant) {
        this.chosenRestaurant = chosenRestaurant;
    }

    public void setIsChosenRestaurant(Boolean isChosenRestaurant) {
        this.isChosenRestaurant = isChosenRestaurant;
    }
}
