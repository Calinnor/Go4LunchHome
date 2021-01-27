package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.GoogleApiPlacesNearbySearchRestaurants;
import com.cirederf.go4lunch.networking.NearbyPlacesRepository;

public class NearbyRestaurantsViewModel extends ViewModel {

    private MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> mutableLiveData;
    private NearbyPlacesRepository nearbyPlacesRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        nearbyPlacesRepository = NearbyPlacesRepository.getInstance();
        mutableLiveData = nearbyPlacesRepository.getNearbyRestaurants("48.410692,2.738093", 15000, "restaurant", "AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI");
    }

    public LiveData<GoogleApiPlacesNearbySearchRestaurants> getNearbyRestaurantsRepository() {
        return mutableLiveData;
    }
}
