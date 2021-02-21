package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsRepository;

import java.util.List;

public class SearchRestaurantsViewModel extends ViewModel {

    //---------FOR DATA-----------
    private LiveData<List<Restaurant>> liveDataListRestaurantsFromRestaurantsRepository;


    //---------REPOSITORY---------
    private RestaurantsRepository restaurantsRepository;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public SearchRestaurantsViewModel(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    /**
     * Init a LiveData<List<Restaurant>> with method from Repository
     */
    public void initRestaurantsList(String location, int radius, String type, String apiKey) {
        if (liveDataListRestaurantsFromRestaurantsRepository != null) {
            return;
        }
        restaurantsRepository = RestaurantsRepository.getInstance();
        liveDataListRestaurantsFromRestaurantsRepository = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
    }

    /**
     *
     * @return a LiveData<List<Restaurant>> data value
     */
    public LiveData<List<Restaurant>> getRestaurantsFromRestaurantsRepository() {
        return liveDataListRestaurantsFromRestaurantsRepository;
    }
}
