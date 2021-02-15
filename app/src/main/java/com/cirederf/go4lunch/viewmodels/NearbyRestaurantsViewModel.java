package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiModels.GoogleApiPlacesNearbySearchRestaurants;
import com.cirederf.go4lunch.networking.NearbyPlacesRepository;

import java.util.List;

public class NearbyRestaurantsViewModel extends ViewModel {

    private MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> mutableLiveData;
    private MutableLiveData<List<Restaurant>> restaurantsListMutableLivedata;
    private NearbyPlacesRepository nearbyPlacesRepository;

    public void initListRestaurants() {
        if (mutableLiveData != null) {
            return;
        }
        nearbyPlacesRepository = NearbyPlacesRepository.getInstance();
        restaurantsListMutableLivedata = nearbyPlacesRepository.getNearbyRestaurantsList("48.410692,2.738093", 15000, "restaurant", "AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI");
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurantsListMutableLivedata;
    }
}
