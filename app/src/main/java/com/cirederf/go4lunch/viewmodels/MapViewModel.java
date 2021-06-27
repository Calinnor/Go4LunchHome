package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;
import com.cirederf.go4lunch.repository.UserRepository;

import java.util.List;

public class MapViewModel extends ViewModel {
    //---------FOR DATA-----------
    private LiveData<List<Restaurant>> listRestaurantsLiveData;
    private LiveData<Integer> workmatesNumber;

    private UserRepository userDataSource;
    private RestaurantsNearbySearchRepository restaurantsNearbySearchRepository;

    public MapViewModel(UserRepository userDataSource, RestaurantsNearbySearchRepository restaurantsNearbySearchRepository) {
        this.userDataSource = userDataSource;
        this.restaurantsNearbySearchRepository = restaurantsNearbySearchRepository;
    }

    /**
     * Init a LiveData<List<Restaurant>> with method from Repository use in ListRestaurantsFragment
     */
    public void initRestaurantsList(String location, int radius, String type, String apiKey) {
        restaurantsNearbySearchRepository = RestaurantsNearbySearchRepository.getInstance();
        listRestaurantsLiveData = restaurantsNearbySearchRepository.getRestaurantsListLiveData(location, radius, type, apiKey);
    }

    /**
     * @return a LiveData<List<Restaurant>> data value use in ListRestaurantsFragment
     */
    public LiveData<List<Restaurant>> getListRestaurantsLiveData() {
        return listRestaurantsLiveData;
    }

    public LiveData<Integer> getWorkmatesNumber(String chosenRestaurant) {
        userDataSource = UserRepository.getInstance();
        workmatesNumber = userDataSource.getWorkmatesNumber(chosenRestaurant);
        return this.workmatesNumber;
    }
}
