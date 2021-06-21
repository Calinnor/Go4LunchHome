package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.api.PlacesApiRequests;
import com.cirederf.go4lunch.api.RetrofitService;
import com.cirederf.go4lunch.apiServices.placesInterfaces.NearbyPlaceInterface;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;
import com.cirederf.go4lunch.models.apiNearbyModels.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Get data from PlacesSearchApi using NearbyPlacesApiRequests and fetch them with the public method RestaurantRepository()
 */
public class RestaurantsNearbySearchRepository implements NearbyPlaceInterface {

    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>();
    public LiveData<List<Restaurant>> restaurantsList = _restaurants;

    /**
     * Singleton for RestaurantsRepository
     * @return restaurantsRepository
     */
    private static RestaurantsNearbySearchRepository restaurantsNearbySearchRepository;

    public static RestaurantsNearbySearchRepository getInstance() {
        if (restaurantsNearbySearchRepository == null) {
            restaurantsNearbySearchRepository = new RestaurantsNearbySearchRepository();
        }
        return restaurantsNearbySearchRepository;
    }

    //---------FOR DATA REQUEST-------------
    private final PlacesApiRequests apiDataSource = RetrofitService.createService(PlacesApiRequests.class);
    /**
     * Create the retrofit request
     */
    public RestaurantsNearbySearchRepository() {
    }

    /**
     * Create a public LiveData list of restaurants using a private MutableLiceData list
     *               request to PlacesSearchApi: nearbyPlacesApiRequests.getNearbyPlacesList(param).enqueue(CallBack)
     *               onResponse() create a list of places (type = restaurant) using CallBack<PlacesSearchApi>()
     *               onResponse(), onFailure() setValue List of restaurants or null to MutableLiveData<List<Restaurant>>
     * @return LiveData<List<Restaurant>> restaurantsList = MutableLiveData<List<Restaurant>> _restaurants;
     */

    @Override
    public LiveData<List<Restaurant>> getRestaurantsListLiveData(String location, int radius, String type, String apiKey){

        apiDataSource.getNearbyPlacesList(location, radius, type, apiKey)
                .enqueue(new Callback<PlacesSearchApi>() {
                    @Override
                    public void onResponse(Call<PlacesSearchApi> call, Response<PlacesSearchApi> response) {
                        assert response.body() != null;
                        List<Result> results = response.body().getResults();
                        List<Restaurant> restaurants = new ArrayList<>();

                        for (int i = 0; i < results.size(); i ++) {
                            Result result = results.get(i);
                            Restaurant nearbySearchRestaurant = buildRestaurant(result, apiKey);
                            restaurants.add(nearbySearchRestaurant);
                        }
                        _restaurants.setValue(restaurants);
                    }

                    @Override
                    public void onFailure(Call<PlacesSearchApi> call, Throwable t) {
                        _restaurants.setValue(null);
                    }
                });

        return restaurantsList;
    }

    private Restaurant buildRestaurant(Result result, String apiKey) {
        return new Restaurant.Builder()
                .setRestaurantName(result.getName())
                .setAddress(result.getVicinity())
                .setRating((result.getRating()) != null ? result.getRating() : 0)
                .setPicture(result.getPhotos() != null ? result.getPhotos().get(0).getPhotoReference() : null, apiKey)
                .setPlaceId(result.getPlaceId())
                .setOpenNow(result.getOpeningHours() != null ? result.getOpeningHours().getOpenNow() : false)
                .setType(result.getTypes().get(0))
                .setGeometry(result.getGeometry())
                .build();
    }

}



