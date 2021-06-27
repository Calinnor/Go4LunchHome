package com.cirederf.go4lunch.models;

import com.cirederf.go4lunch.models.apiNearbyModels.Geometry;

public class Restaurant {
    private final String restaurantName;
    private final String address;
    private final double rating;
    private String picture;
    private String placeId;
    private final String phoneNumber;
    private final String website;
    private final Boolean openNow;
    private final String type;
    private final int workmatesNumber;
    private Geometry geometry;

    private Restaurant(String restaurantName, String address
            , double rating, String picture
            , String placeId, String phoneNumber
            , String website, Boolean openNow
            , String type, int workmatesNumber
            , Geometry geometry) {

        this.restaurantName = restaurantName;
        this.address = address;
        this.rating = rating;
        this.picture = picture;
        this.placeId = placeId;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.openNow = openNow;
        this.type = type;
        this.workmatesNumber = workmatesNumber;
        this.geometry = geometry;
    }

    //---------------GETTERS----------------

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

    public int getWorkmatesNumber() {
        return workmatesNumber;
    }

    public  Geometry getGeometry() { return geometry; }

    //---------------SETTERS---------------------

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setGeometry(Geometry geometry) { this.geometry = geometry; }

//    public void setWorkmatesNumber(int workmatesNumber) {
//        this.workmatesNumber = workmatesNumber;
//    }

    public static class Builder {
        private String restaurantName;
        private String address;
        private double rating;
        private String picture;
        private String placeId;
        private String phoneNumber;
        private String website;
        private Boolean openNow;
        private String type;
        private int workmatesNumber;
        private Geometry geometry;

        public Builder setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName == null ? "" : restaurantName;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address == null ? "" : address;
            return this;
        }

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }

        public Builder setPicture(String photoReference, String apiKey) {
            this.picture = "https://maps.googleapis.com/maps/api/place/photo?" + "photoreference=" + photoReference
                    + "&maxwidth=" + 250 + "&key=" + apiKey;
            return this;
        }

        public Builder setPlaceId(String placeId) {
            this.placeId = placeId == null ? "" : placeId;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber == null ? "" : phoneNumber;
            return this;
        }

        public Builder setWebsite(String website) {
            this.website = website != null ? website : "";
            return this;
        }

        public Builder setOpenNow(Boolean openNow) {
            this.openNow = openNow != null && openNow;
            return this;
        }

        public Builder setType(String type) {
            this.type = type == null ? "" : type;
            return this;
        }

        public Builder setWorkmatesNumber(int workmatesNumber) {
            this.workmatesNumber = workmatesNumber;
            return this;
        }

        public Builder setGeometry(Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(restaurantName, address, rating, picture, placeId, phoneNumber, website, openNow, type, workmatesNumber, geometry);
        }


    }
}