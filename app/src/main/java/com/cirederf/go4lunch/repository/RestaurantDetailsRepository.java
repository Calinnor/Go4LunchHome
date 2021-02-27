package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiDetailModels.RestaurantDetailsApi;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;
import com.cirederf.go4lunch.networking.NearbyPlacesApiRequests;
import com.cirederf.go4lunch.networking.RetrofitService;
import com.cirederf.go4lunch.services.RestaurantsDetailsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsRepository implements RestaurantsDetailsInterface {

    private final MutableLiveData<Restaurant> _detailsRestaurant = new MutableLiveData<>();
    public LiveData<Restaurant> restaurantDetails = _detailsRestaurant;

    /**
     * Singleton for RestaurantDetailsRepository
     *
     * @return restaurantDetailsRepository
     */
    private static RestaurantDetailsRepository restaurantDetailsRepository;

    public static RestaurantDetailsRepository getInstance() {
        if (restaurantDetailsRepository == null) {
            restaurantDetailsRepository = new RestaurantDetailsRepository();
        }
        return restaurantDetailsRepository;
    }

    //---------FOR DATA REQUEST-------------
    private final NearbyPlacesApiRequests apiDataSource = RetrofitService.createService(NearbyPlacesApiRequests.class);

    /**
     * Create the retrofit request
     */
    public RestaurantDetailsRepository() {
    }

    @Override
    public LiveData<List<Restaurant>> getRestaurantsDetailsLiveData(String placeId, String apiKey) {

        Restaurant restaurant = new Restaurant();

        apiDataSource.getRestaurantDetails(placeId, apiKey)
                .enqueue(new Callback<RestaurantDetailsApi>() {

                    @Override
                    public void onResponse(Call<RestaurantDetailsApi> call, Response<RestaurantDetailsApi> response) {

                    }

                    @Override
                    public void onFailure(Call<RestaurantDetailsApi> call, Throwable t) {

                    }
                });
        return null;
    }
}
