package com.cirederf.go4lunch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.cirederf.go4lunch.models.apiNearbyModels.Location;

public class Restaurant implements Parcelable {
    private String name;
    private String address;
    private double rating;
    private String picture;
    private String placeId;
    private String phoneNumber;
    private String website;
    private Boolean openNow;
    private Location location;

    //////// CONSTRUCTORS ////////

    public Restaurant(String name, String address, String picture, String placeId, double rating,
                      String phoneNumber, String website,Location location, Boolean openNow) {
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.placeId = placeId;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.location = location;
        this.openNow = openNow;
    }

    //Empty constructor for Firebase
    public Restaurant () {}


//    protected Restaurant(Parcel in) {
//        name = in.readString();
//        address = in.readString();
//        rating = in.readDouble();
//        picture = in.readString();
//        placeId = in.readString();
//        phoneNumber = in.readString();
//        website = in.readString();
//        byte tmpOpenNow = in.readByte();
//        openNow = tmpOpenNow == 0 ? null : tmpOpenNow == 1;
//    }
//
//    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
//        @Override
//        public Restaurant createFromParcel(Parcel in) {
//            return new Restaurant(in);
//        }
//
//        @Override
//        public Restaurant[] newArray(int size) {
//            return new Restaurant[size];
//        }
//    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(placeId);
        parcel.writeString(picture);
        parcel.writeString(name);
        parcel.writeDouble(rating);
        parcel.writeString(address);
        parcel.writeString(phoneNumber);
        parcel.writeString(website);
    }
    public Restaurant(Parcel in) {
        placeId = in.readString();
        picture = in.readString();
        name = in.readString();
        rating = in.readDouble();
        address = in.readString();
        phoneNumber = in.readString();
        website = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
