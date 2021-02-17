package com.cirederf.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
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

//    public SearchViewModel(SearchRepository searchRepository) {
//        this.searchRepository = searchRepository;
//    }

    public SearchViewModel(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    private MutableLiveData<List<Restaurant>> restosMutableLiveDataList = new MutableLiveData<>();

    public MutableLiveData<List<Restaurant>> getNearbyRestos(String location, int radius, String type, String apiKey) {
        //this.restosMutableLiveDataList.setValue(this.searchRepository.getRestaurants(location, radius, type, apiKey));
        this.restosMutableLiveDataList.setValue((List<Restaurant>) this.restaurantsRepository.getRestaurantsList(location, radius, type, apiKey));
        return this.restosMutableLiveDataList;
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
