package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiDetailModels.RestaurantDetailsApi;
import com.cirederf.go4lunch.models.apiDetailModels.Result;
import com.cirederf.go4lunch.api.PlacesApiRequests;
import com.cirederf.go4lunch.api.RetrofitService;
import com.cirederf.go4lunch.apiServices.placesInterfaces.RestaurantDetailsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsRepository implements RestaurantDetailsInterface {

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
     * @param placeId : restaurant Id (place_id from search) in api
     * @param apiKey : key to access api places
     * @return a livedata restaurant with name, address, picture, type, website, phone
     */
    @Override
    public LiveData<Restaurant> getRestaurantDetailsLiveData(String placeId, String apiKey) {

        apiDataSource.getRestaurantDetails(placeId, apiKey)
                .enqueue(new Callback<RestaurantDetailsApi>() {

                    @Override
                    public void onResponse(Call<RestaurantDetailsApi> call, Response<RestaurantDetailsApi> response) {
                        assert response.body() != null;
                        Result result = response.body().getResult();

                        Restaurant detailsRestaurant = new Restaurant.Builder()
                                .setRestaurantName(result.getName())
                                .setAddress(result.getFormattedAddress())
                                .setRating(result.getRating())
                                .setPpicture(result.getPhotos().get(0).getPhotoReference(), apiKey)
                                .setType(setDetailsType(result))
                                .setWebsite(result.getWebsite())
                                .setPhoneNumber(result.getFormattedPhoneNumber())
                                .build();

                        _detailsRestaurant.setValue(detailsRestaurant);
                    }
                    @Override
                    public void onFailure(Call<RestaurantDetailsApi> call, Throwable t) {
                        _detailsRestaurant.setValue(null);
                    }
                });
        return restaurantDetails;
    }

    private String setDetailsType(Result result) {
        List<String> types = result.getTypes();
        String restaurantType = null;
        int typesSize = types.size();
        for (int i = 0; i < typesSize; i++) {
            if (types.get(i).equals("restaurant")) {
                restaurantType = "Restaurant";
                break;
            } else {
                restaurantType = result.getTypes().get(0);
            }
        }
        return restaurantType;
    }

}
