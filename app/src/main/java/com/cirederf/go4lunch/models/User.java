package com.cirederf.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    // todo determine if just the restaurant with it's placeId is necessary (chosenRestaurant)
    //  or if we need a boolean (isChosenRestaurant) to compare data
    private String uid;
    private String username;
    @Nullable
    private String urlPicture;
    private String chosenRestaurant;
    private Boolean isTheChoiceRestaurant;
    private String restaurantType;
    private String rating;

    public User() { }

    //------------FIREBASE CONSTRUCTOR-----------
    public User(String uid, String username, @Nullable String urlPicture, String chosenRestaurant, Boolean isTheChoiceRestaurant, String restaurantType, String rating ) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = chosenRestaurant;
        this.isTheChoiceRestaurant = isTheChoiceRestaurant;
        this.restaurantType = restaurantType;
        this.rating = rating;
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

    public String getChosenRestaurant() {
        return chosenRestaurant;
    }

    public Boolean getIsTheChoiceRestaurant() {
        return isTheChoiceRestaurant;
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

    public void setChosenRestaurant(String chosenRestaurant) {
        this.chosenRestaurant = chosenRestaurant;
    }

    public void setIsTheChoiceRestaurant(Boolean isTheChoiceRestaurant) {
        this.isTheChoiceRestaurant = isTheChoiceRestaurant;
    }
}
