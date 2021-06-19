package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;

import java.util.List;

public class NearbyRestaurantsViewModel extends ViewModel {

    //---------FOR DATA-----------
    private LiveData<List<Restaurant>> listRestaurantsLiveData;

    //---------REPOSITORY---------
    private RestaurantsNearbySearchRepository nearbyRestaurantsDataSource;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public NearbyRestaurantsViewModel(RestaurantsNearbySearchRepository nearbyRestaurantsDataSource) {
        this.nearbyRestaurantsDataSource = nearbyRestaurantsDataSource;
    }

    /**
     * Init a LiveData<List<Restaurant>> with method from Repository use in ListRestaurantsFragment
     */
    public void initRestaurantsList(String location, int radius, String type, String apiKey) {
        nearbyRestaurantsDataSource = RestaurantsNearbySearchRepository.getInstance();
        listRestaurantsLiveData = nearbyRestaurantsDataSource.getRestaurantsListLiveData(location, radius, type, apiKey);
    }

    /**
     * @return a LiveData<List<Restaurant>> data value use in ListRestaurantsFragment
     */
    public LiveData<List<Restaurant>> getListRestaurantsLiveData() {
        return listRestaurantsLiveData;
    }
}
