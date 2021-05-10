package com.cirederf.go4lunch.models;

import java.util.List;

public class Restaurant {
    private String name;
    private String address;
    private double rating;
    private String picture;
    private String placeId;
    private String phoneNumber;
    private String website;
    private Boolean openNow;
    private String type;
    private List<User> workmatesUserList;

    public Restaurant(String name, String address, double rating, String picture, String placeId, Boolean openNow, String type) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.picture = picture;
        this.placeId = placeId;
        this.openNow = openNow;
        this.type = type;
    }
    //////// CONSTRUCTORS ////////
    //-------CONSTRUCTOR FOR SEARCH PLACES--------
    public Restaurant(String name, String address, String picture, String placeId, double rating,
                      String phoneNumber, String website, Boolean openNow) {
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.placeId = placeId;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.openNow = openNow;
    }

    //---------CONSTRUCTOR FOR DETAILS-----------
    public Restaurant(String setDetailName, String setDetailsAddress, String setDetailsPicture,
                      String setDetailsType, String website, String phoneNumber) {
        this.name = setDetailName;
        this.address = setDetailsAddress;
        this.picture = setDetailsPicture;
        this.type = setDetailsType;
        this.website = website;
        this.phoneNumber = phoneNumber;
    }

    //-------FIREBASE CONSTRUCTOR--------
    /**
     * @param workmatesUserList: list of workmates using the app and having selected this restaurant
     * @param placeId: id for each restaurant
     */
    public Restaurant (List<User> workmatesUserList, String placeId) {
        this.workmatesUserList = workmatesUserList;
        this.placeId = placeId;
    }

    //////// GETTERS ////////
    public String getName() {
        return name;
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
    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
