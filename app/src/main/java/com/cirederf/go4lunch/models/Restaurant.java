package com.cirederf.go4lunch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.cirederf.go4lunch.models.apiNearbyModels.Location;
import com.cirederf.go4lunch.models.apiNearbyModels.PlusCode;

public class Restaurant {
    private String name;
    private String address;
    private double rating;
    private String picture;
    private String placeId;
    private String phoneNumber;
    private String website;
    private Boolean openNow;
    //private Location location;
    private PlusCode plusCode;
    private Integer priceLevel;
    private String reference;
    private String scope;
    private String type;

    public Restaurant(String name, String address,  String picture, String placeId, double rating, String phoneNumber, String website,/* Location location,*/ Boolean openNow, PlusCode plusCode, Integer priceLevel, String reference, String scope, String type) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.picture = picture;
        this.placeId = placeId;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.openNow = openNow;
        //this.location = location;
        this.plusCode = plusCode;
        this.priceLevel = priceLevel;
        this.reference = reference;
        this.scope = scope;
        this.type = type;
    }
//////// CONSTRUCTORS ////////

    public Restaurant(String name, String address, String picture, String placeId, double rating,
                      String phoneNumber, String website,/*Location location, */Boolean openNow) {
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.placeId = placeId;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
        //this.location = location;
        this.openNow = openNow;
    }

    //Empty constructor for Firebase
    public Restaurant () {}

    //constructor for details
    public Restaurant(String setDetailName, String setDetailsAddress, String setDetailsPicture, String setDetailsType, String website, String phoneNumber) {
        this.name = setDetailName;
        this.address = setDetailsAddress;
        this.picture = setDetailsPicture;
        this.type = setDetailsType;
        this.website = website;
        this.phoneNumber = phoneNumber;
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

//    public Location getLocation() {
//        return location;
//    }

    public PlusCode getPlusCode() {
        return plusCode;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public String getReference() {
        return reference;
    }

    public String getScope() {
        return scope;
    }

    public String getType() {
        return type;
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

//    public void setLocation(Location location) {
//        this.location = location;
//    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public void setWebsite(String website) {
//        this.website = website;
//    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

//    public void setPlusCode(PlusCode plusCode) {
//        this.plusCode = plusCode;
//    }
//
//    public void setPriceLevel(Integer priceLevel) {
//        this.priceLevel = priceLevel;
//    }
//
//    public void setReference(String reference) {
//        this.reference = reference;
//    }
//
//    public void setScope(String scope) {
//        this.scope = scope;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

}
