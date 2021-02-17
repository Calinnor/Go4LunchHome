package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class RestaurantsViewModel extends ViewModel {

    private final RestaurantsRepository restaurantsDataSource;
    private final Executor executor;

    private MutableLiveData<List<Restaurant>> getRestaurants;

    public RestaurantsViewModel(RestaurantsRepository restaurantsDataSource, Executor executor) {
        this.restaurantsDataSource = restaurantsDataSource;
        this.executor = executor;
    }

    public void initRestaurantsList(String location, int radius, String type, String apiKey) {
        if(getRestaurants != null) {
            return;
        }
        getRestaurants = restaurantsDataSource.getRestaurantsList(location, radius, type, apiKey);
    }

    public MutableLiveData<List<Restaurant>> getRestaurantsList (String location, int radius, String type, String apiKey) {
        return restaurantsDataSource.getRestaurantsList(location, radius, type, apiKey);
    }
}
