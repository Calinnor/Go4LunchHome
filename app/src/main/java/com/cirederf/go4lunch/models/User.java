package com.cirederf.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    @Nullable
    private String urlPicture;
    @Nullable
    private String chosenRestaurant;
    @Nullable
    private String restaurantType;
    private String rating;
    @Nullable
    private String restaurantName;
    @Nullable
    Boolean ischosenRestaurantDisplay;

    //---------DETAILS FOR WORKMATES CHOSEN RESTAURANT----------
    public User(String username, @Nullable String urlPicture,@Nullable String chosenRestaurant) {
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = chosenRestaurant;
    }

    //------------FIREBASE CONSTRUCTOR-----------
    public User(String uid, String username
            ,@Nullable String urlPicture, @Nullable String chosenRestaurant
            ,@Nullable String restaurantType, String rating, @Nullable String restaurantName) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = chosenRestaurant;
        this.restaurantType = restaurantType;
        this.rating = rating;
        this.restaurantName = restaurantName;
    }

    public User() {}

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

    public String getRestaurantType() {
        return restaurantType;
    }

    public String getRating() {
        return rating;
    }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    @Nullable
    public Boolean getIschosenRestaurantDisplay() {
        return ischosenRestaurantDisplay;
    }

    //------------SETTERS-----------
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setChosenRestaurant(String chosenRestaurant) {
        this.chosenRestaurant = chosenRestaurant;
    }

    public void setIschosenRestaurantDisplay(@Nullable Boolean ischosenRestaurant) {
        this.ischosenRestaurantDisplay = ischosenRestaurant;
    }
}
