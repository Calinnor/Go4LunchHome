package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.repository.SearchRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository;
    private RestaurantsRepository restaurantsRepository;
    private MutableLiveData<List<Restaurant>> restaurantsListLiveDataFromViewModelFromRestoRepo;

    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public SearchViewModel(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    private MutableLiveData<List<Restaurant>> restosMutableLiveDataList = new MutableLiveData<>();

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

        this.restosMutableLiveDataList = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
        return this.restosMutableLiveDataList;
        /**
         * here was my error. Difficulty to find how to obtain the good list of mutablelivedata<restaurant> and not a list of restaurant
         */
    }

    public void initRestoList(String location, int radius, String type, String apiKey) {
        if (restaurantsListLiveDataFromViewModelFromRestoRepo != null) {
            return;
        }
        restaurantsRepository = RestaurantsRepository.getInstance();
        restaurantsListLiveDataFromViewModelFromRestoRepo = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
    }

    public MutableLiveData<List<Restaurant>> getRestaurantsFromRestoRepo() {
        return restaurantsListLiveDataFromViewModelFromRestoRepo;
    }
}
