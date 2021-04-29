package com.cirederf.go4lunch.services;

import androidx.lifecycle.LiveData;

import com.cirederf.go4lunch.models.Restaurant;

import java.util.List;

public interface NearbyPlaceInterface {

    /**
     * get a PlaceSearch result
     * @param location : location
     * @param radius : radius for search
     * @param type : type to search (base = restaurant)
     * @param apiKey : key to access api places
     * @return a list of restaurants liveData
     */
    LiveData<List<Restaurant>> getRestaurantsListLiveData(String location, int radius, String type, String apiKey);
}
