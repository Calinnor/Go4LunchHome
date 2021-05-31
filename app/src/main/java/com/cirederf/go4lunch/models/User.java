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
    String recyclerDisplay;

    //---------DETAILS FOR WORKMATES CHOSEN RESTAURANT----------
    public User(String username, @Nullable String urlPicture, String chosenRestaurant) {
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = chosenRestaurant;
    }

    //------------FIREBASE CONSTRUCTOR-----------
    public User(String uid, String username, @Nullable String urlPicture, @Nullable String chosenRestaurant,@Nullable String restaurantType, String rating, @Nullable String restaurantName, @Nullable
            String recyclerDisplay) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.chosenRestaurant = chosenRestaurant;
        this.restaurantType = restaurantType;
        this.rating = rating;
        this.restaurantName = restaurantName;
        this.recyclerDisplay = recyclerDisplay;
    }

    public User() {}

    public <T> User(T toObject) {

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

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setRestaurantName(@Nullable String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
