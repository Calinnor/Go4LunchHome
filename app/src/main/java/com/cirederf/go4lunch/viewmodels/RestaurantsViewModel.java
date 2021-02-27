package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;

import java.util.List;

public class RestaurantsViewModel extends ViewModel {

    //---------FOR DATA-----------
    private LiveData<List<Restaurant>> listRestaurantsLiveData;

    //---------REPOSITORY---------
    private RestaurantsNearbySearchRepository restaurantsDataSource;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public RestaurantsViewModel(RestaurantsNearbySearchRepository restaurantsDataSource) {
        this.restaurantsDataSource = restaurantsDataSource;
    }

    /**
     * Init a LiveData<List<Restaurant>> with method from Repository
     */
    public void initRestaurantsList(String location, int radius, String type, String apiKey) {
        if (listRestaurantsLiveData != null) {
            return;
        }
        restaurantsDataSource = RestaurantsNearbySearchRepository.getInstance();
        listRestaurantsLiveData = restaurantsDataSource.getListRestaurantsLiveData(location, radius, type, apiKey);
    }

    /**
     *
     * @return a LiveData<List<Restaurant>> data value
     */
    public LiveData<List<Restaurant>> getListRestaurantsLiveData() {
        return listRestaurantsLiveData;
    }
}
