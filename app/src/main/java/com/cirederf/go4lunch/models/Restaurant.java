package com.cirederf.go4lunch.models;

import com.cirederf.go4lunch.models.apiModels.Location;

public class Restaurant
{
    private String name;
    private String address;
    private double rating;
    private String picture;
    private String placeId;
    private String phoneNumber;
    private String website;
    private Boolean openNow;
    private Location location;
    private int distanceCurrentUser;

    //////// CONSTRUCTORS ////////

    public Restaurant(String name, String address, String picture, String placeId, double rating,
                      String phoneNumber, String website, Location location) {
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.placeId = placeId;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.location = location;
    }

    //Empty constructor for Firebase
    public Restaurant () {}

    //Constructor for Places' Request
    public Restaurant(String name, String address, String picture, String placeId, double rating, Boolean openNow, Location location)
    {
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.placeId = placeId;
        this.rating = rating;
        this.openNow = openNow;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    //////// SETTERS ////////
    public void setName(String name) {
        this.name = name;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
