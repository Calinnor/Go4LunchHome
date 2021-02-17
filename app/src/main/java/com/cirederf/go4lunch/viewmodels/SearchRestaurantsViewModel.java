package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.repository.SearchRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class SearchRestaurantsViewModel extends ViewModel {

    //---------REPOSITORY---------
    //private SearchRepository searchRepository;
    private RestaurantsRepository restaurantsRepository;
    private MutableLiveData<List<Restaurant>> liveDataListRestaurantsFromRestaurantsRepository;

//    public SearchRestaurantsViewModel(SearchRepository searchRepository) {
//        this.searchRepository = searchRepository;
//    }

    //----------CONSTRUCTOR-----------
    public SearchRestaurantsViewModel(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    public MutableLiveData<List<Restaurant>> getNearbyRestos(String location, int radius, String type, String apiKey) {
        //these line make crash
        //this.restosMutableLiveDataList.setValue(this.searchRepository.getRestaurants(location, radius, type, apiKey));
        //TODO find why it crash here
        /**
         * here getRestaurants return a List<Restaurant> so there isn't error in compile
         *
         * below here too is required List<Restaurant> but is provided MutableLiveData<List<Restaurant>>
         *     cast to List is unchecked
         */
        //this.restosMutableLiveDataList.setValue((List<Restaurant>) this.restaurantsRepository.getRestaurantsList(location, radius, type, apiKey));

        MutableLiveData<List<Restaurant>> restosMutableLiveDataList = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
        return restosMutableLiveDataList;
        /**
         * here was my error. Difficulty to find how to obtain the good list of mutablelivedata<restaurant> and not a list of restaurant
         */
    }

    /**
     * init MutableLiveData<List<Restaurant>> with method from Repository and set them on variable
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
     * @return MutableLiveData<List<Restaurant>> variable
     */
    public MutableLiveData<List<Restaurant>> getRestaurantsFromRestaurantsRepository() {
        return liveDataListRestaurantsFromRestaurantsRepository;
    }
}
