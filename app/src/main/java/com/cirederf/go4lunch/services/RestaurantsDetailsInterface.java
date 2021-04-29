package com.cirederf.go4lunch.services;

import androidx.lifecycle.LiveData;

import com.cirederf.go4lunch.models.Restaurant;

import java.util.List;

public interface RestaurantsDetailsInterface {

    /**
     * get a PlaceSearch result
     * @param placeId : restaurant Id in api
     * @param apiKey : key to access api places
     * @return a list of restaurants liveData
     */
    LiveData<Restaurant> getRestaurantDetailsLiveData(String placeId, String apiKey);
}
