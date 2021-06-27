package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;
import com.cirederf.go4lunch.repository.UserRepository;
import com.cirederf.go4lunch.viewmodels.MapViewModel;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
    private final UserRepository userDetailsDataSource;
    private final RestaurantsNearbySearchRepository restaurantsNearbySearchDataSource;

    public MapViewModelFactory(UserRepository userDetailsDataSource, RestaurantsNearbySearchRepository restaurantsNearbySearchDataSource) {
        this.userDetailsDataSource = userDetailsDataSource;
        this.restaurantsNearbySearchDataSource = restaurantsNearbySearchDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)) {
           return (T) new MapViewModel(userDetailsDataSource, restaurantsNearbySearchDataSource);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }
}
