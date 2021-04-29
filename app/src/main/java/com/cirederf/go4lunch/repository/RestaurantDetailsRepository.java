package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiDetailModels.RestaurantDetailsApi;
import com.cirederf.go4lunch.models.apiDetailModels.Result;
import com.cirederf.go4lunch.networking.PlacesApiRequests;
import com.cirederf.go4lunch.networking.RetrofitService;
import com.cirederf.go4lunch.services.RestaurantsDetailsInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsRepository implements RestaurantsDetailsInterface {

    private final MutableLiveData<Restaurant> _detailsRestaurant = new MutableLiveData<>();
    public LiveData<Restaurant> restaurantDetails = _detailsRestaurant;

    /**
     * Singleton for RestaurantDetailsRepository
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
    private final PlacesApiRequests apiDataSource = RetrofitService.createService(PlacesApiRequests.class);

    /**
     * Create the retrofit request
     */
    public RestaurantDetailsRepository() {
    }

    /**
     * Create a public LiveData restaurant
     * @param placeId : restaurant Id in api
     * @param apiKey : key to access api places
     * @return
     */

    @Override
    public LiveData<Restaurant> getRestaurantDetailsLiveData(String placeId, String apiKey) {

        apiDataSource.getRestaurantDetails(placeId, apiKey)
                .enqueue(new Callback<RestaurantDetailsApi>() {

                    @Override
                    public void onResponse(Call<RestaurantDetailsApi> call, Response<RestaurantDetailsApi> response) {
                        Result result = response.body().getResult();
                        Restaurant detailsRestaurant = new Restaurant(setDetailName(result)
                                ,setDetailsAddress(result)
                                ,setDetailsPicture(result, apiKey)
                                ,setDetailsType(result)
                        );
                        _detailsRestaurant.setValue(detailsRestaurant);
                    }

                    @Override
                    public void onFailure(Call<RestaurantDetailsApi> call, Throwable t) {
                        _detailsRestaurant.setValue(null);
                    }
                });

        return restaurantDetails;
    }

    //----------------METHODS FOR SET DETAILS VALUES IN onResponse()-------------
    private String getPicture(String photoReference, String apiKey) {
        return "https://maps.googleapis.com/maps/api/place/photo?" + "photoreference=" + photoReference
                + "&maxwidth=" + 250 + "&key=" + apiKey;
    }

    private String setDetailsPicture(Result result, String apiKey) {
        return result.getPhotos() != null ?
                getPicture(result.getPhotos().get(0).getPhotoReference(), apiKey)
                : null;
    }

    private String setDetailsAddress(Result result) {
        return result.getFormattedAddress() != null ? result.getFormattedAddress() : "no address";
    }

    private String setDetailsType(Result result) {
        return result.getTypes().get(0) != null ? result.getTypes().get(0) : "no type";
    }

    private String setDetailName(Result result) {
        return result.getName() != null ? result.getName() : "no name";
    }
}
