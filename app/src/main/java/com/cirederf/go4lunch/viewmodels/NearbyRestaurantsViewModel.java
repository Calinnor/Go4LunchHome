package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.NearbyPlacesRepository;

import java.util.List;

public class NearbyRestaurantsViewModel extends ViewModel {

    private MutableLiveData<List<Restaurant>> restaurantsListMutableLiveData;
    private NearbyPlacesRepository nearbyPlacesRepository;

    //here is missing the constructor which will be call in Factory

    //public void initListRestaurants() {
    public void initListRestaurants(String location, int radius, String type, String apiKey) {
        if (restaurantsListMutableLiveData != null) {
            return;
        }
        nearbyPlacesRepository = NearbyPlacesRepository.getInstance();
        //restaurantsListMutableLiveData = nearbyPlacesRepository.getNearbyRestaurantsList("48.410692,2.738093", 15000, "restaurant", "AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI");
        restaurantsListMutableLiveData = nearbyPlacesRepository.getNearbyRestaurantsList(location,radius,type,apiKey);
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurantsListMutableLiveData;
    }
}
