package com.cirederf.go4lunch.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.repository.SearchRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class SearchRestaurantsViewModel extends ViewModel {

//    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>();
//    public LiveData<List<Restaurant>> restaurants;
//
//    private final MutableLiveData<Integer> _restaurantsCount = new MutableLiveData<>();
//    public LiveData<Integer> restaurantsCount = _restaurantsCount;

    //---------FOR DATA-----------
    //private MutableLiveData<List<Restaurant>> liveDataListRestaurantsFromRestaurantsRepository;
    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>();
    public LiveData<List<Restaurant>> restaurants = _restaurants;


    //---------REPOSITORY---------
    //private SearchRepository searchRepository;
    private RestaurantsRepository restaurantsRepository;


//    public SearchRestaurantsViewModel(SearchRepository searchRepository) {
//        this.searchRepository = searchRepository;
//    }

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public SearchRestaurantsViewModel(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

//    public void init() {
//        Transformations.map(_restaurants, new Function<List<Restaurant>, Boolean>() {
//            @Override
//            public Boolean apply(List<Restaurant> input) {
//                _restaurantsCount.setValue(input.size());
//                return true;
//            }
//        })
//    }

//    public LiveData<List<Restaurant>> getNearbyRestos(String location, int radius, String type, String apiKey) {
//       // return this.restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
//        //these line make crash
//        //this.restosMutableLiveDataList.setValue(this.searchRepository.getRestaurants(location, radius, type, apiKey));
//        //TODO find why it crash here
//        /**
//         * here getRestaurants return a List<Restaurant> so there isn't error in compile
//         *
//         * below here too is required List<Restaurant> but is provided MutableLiveData<List<Restaurant>>
//         *     cast to List is unchecked
//         */


//        LiveData<List<Restaurant>> restaurantsList = this.restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
//        restaurants = Transformations.map(restaurantsList, (Function<List<Restaurant>, List<Restaurant>>) input -> {
//            _restaurants.setValue(input);
//            return input;
//        });
//
//        this.liveDataListRestaurantsFromRestaurantsRepository.setValue(this.searchRepository.getRestaurants(location, radius, type, apiKey));


        //this.restosMutableLiveDataList.setValue((List<Restaurant>) this.restaurantsRepository.getRestaurantsList(location, radius, type, apiKey));
//        MutableLiveData<List<Restaurant>> restosMutableLiveDataList;
//        restosMutableLiveDataList = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
//        return restosMutableLiveDataList;
//        /**
//         * here was my error. Difficulty to find how to obtain the good list of mutablelivedata<restaurant> and not a list of restaurant
//         */
//    }

    /**
     * Init a MutableLiveData<List<Restaurant>> with method from Repository
     */
    public void initRestaurantsList(String location, int radius, String type, String apiKey) {
        //if (liveDataListRestaurantsFromRestaurantsRepository != null) {
        if (restaurants != null) {
            return;
        }
        restaurantsRepository = RestaurantsRepository.getInstance();
        //liveDataListRestaurantsFromRestaurantsRepository = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
        restaurants = restaurantsRepository.getRestaurantsList(location, radius, type, apiKey);
    }

    /**
     *
     * @return a MutableLiveData<List<Restaurant>> data value
     */
    public LiveData<List<Restaurant>> getRestaurantsFromRestaurantsRepository() {
        //return liveDataListRestaurantsFromRestaurantsRepository;
        return restaurants;
    }
}
