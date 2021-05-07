package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantDetailsRepository;

public class RestaurantDetailsViewModel extends ViewModel {

    //----------FOR DATA-------------
    private LiveData<Restaurant> restaurantDetails;

    //----------REPOSITORY----------
    private RestaurantDetailsRepository restaurantDetailsDataSource;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public RestaurantDetailsViewModel(RestaurantDetailsRepository restaurantDetailsDataSource) {
        this.restaurantDetailsDataSource = restaurantDetailsDataSource;
    }

    /**
     * Init a LiveData<Restaurant> with method from repository to use in DetailsRestaurantsFragment
     */
    public void initRestaurantDetails(String placeId, String apiKey) {
        if(this.restaurantDetails != null) {
            return;
        }
        restaurantDetailsDataSource = RestaurantDetailsRepository.getInstance();
        restaurantDetails = restaurantDetailsDataSource.getRestaurantDetailsLiveData(placeId, apiKey);
    }

    /**
     * return a LiveData<Restaurant> details to use in DetailsRestaurants
     */
    //public LiveData<Restaurant> getRestaurantDetailsLiveData(String placeId,String apiKey) {
    public LiveData<Restaurant> getRestaurantDetailsLiveData() {
    return this.restaurantDetails;
    }
}
