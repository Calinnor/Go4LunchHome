package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;
import com.cirederf.go4lunch.models.apiNearbyModels.Result;
import com.cirederf.go4lunch.api.PlacesApiRequests;
import com.cirederf.go4lunch.api.RetrofitService;
import com.cirederf.go4lunch.apiServices.placesInterfaces.NearbyPlaceInterface;

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

        List<Restaurant> restaurants = new ArrayList<>();

        apiDataSource.getNearbyPlacesList(location, radius, type, apiKey)
                .enqueue(new Callback<PlacesSearchApi>() {
                    @Override
                    public void onResponse(Call<PlacesSearchApi> call, Response<PlacesSearchApi> response) {
                        List<Result> results = response.body().getResults();

                        for (int i = 0; i < results.size(); i ++) {

                            Restaurant nearbySearchRestaurant = new Restaurant.Builder()
                                    .setRestaurantName(results.get(i).getName())
                                    .setAddress(results.get(i).getVicinity())
                                    .setRating(results.get(i).getRating())
                                    .setPpicture(results.get(i).getPhotos().get(0).getPhotoReference(), apiKey)
                                    .setPlaceId(results.get(i).getPlaceId())
                                    .setOpenNow(results.get(i).getOpeningHours() != null ? results.get(i).getOpeningHours().getOpenNow() : false)
                                    .setType(results.get(i).getTypes().get(0))
                                    .build();

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
}



