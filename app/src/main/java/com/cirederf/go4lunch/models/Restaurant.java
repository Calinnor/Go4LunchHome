package com.cirederf.go4lunch.models;

public class Restaurant {
    private String restaurantName;
    private String address;
    private double rating;
    private String picture;
    private String placeId;
    private String phoneNumber;
    private String website;
    private Boolean openNow;
    private String type;

    //////// CONSTRUCTORS ////////
    //-------CONSTRUCTOR FOR SEARCH PLACES--------
    public Restaurant(String restaurantName, String address, double rating, String picture, String placeId, Boolean openNow, String type) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.rating = rating;
        this.picture = picture;
        this.placeId = placeId;
        this.openNow = openNow;
        this.type = type;
    }

    //---------CONSTRUCTOR FOR DETAILS-----------
    public Restaurant(String setDetailName, String setDetailsAddress, double rating, String setDetailsPicture,
                      String setDetailsType, String website, String phoneNumber) {
        this.restaurantName = setDetailName;
        this.address = setDetailsAddress;
        this.rating = rating;
        this.picture = setDetailsPicture;
        this.type = setDetailsType;
        this.website = website;
        this.phoneNumber = phoneNumber;
    }

    //-------FIREBASE CONSTRUCTOR--------
    /**
     //* @param workmatesUserList: list of workmates using the app and having selected this restaurant
     * @param placeId: id for each restaurant
     */
    public Restaurant (String restaurantName, String placeId) {
        this.restaurantName = restaurantName;
        this.placeId = placeId;
    }

    //////// GETTERS ////////
    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }

    public String getPicture() {
        return picture;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public String getType() {
        return type;
    }

    //////// SETTERS ////////

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
