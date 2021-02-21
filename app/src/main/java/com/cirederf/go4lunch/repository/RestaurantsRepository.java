package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiModels.Location;
import com.cirederf.go4lunch.models.apiModels.PlacesSearchApi;
import com.cirederf.go4lunch.models.apiModels.Result;
import com.cirederf.go4lunch.networking.NearbyPlacesApiRequests;
import com.cirederf.go4lunch.networking.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Get data from PlacesSearchApi using NearbyPlacesApiRequests and fetch them with the public method RestaurantRepository()
 */
public class RestaurantsRepository {

    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>();
    public LiveData<List<Restaurant>> restaurantsList = _restaurants;

    /**
     * Singleton for RestaurantsRepository
     * @return restaurantsRepository
     */
    private static RestaurantsRepository restaurantsRepository;

    public static RestaurantsRepository getInstance() {
        if (restaurantsRepository == null) {
            restaurantsRepository = new RestaurantsRepository();
        }
        return restaurantsRepository;
    }

    //---------FOR DATA REQUEST-------------
    private final NearbyPlacesApiRequests nearbyPlacesApiRequests = RetrofitService.createService(NearbyPlacesApiRequests.class);
    /**
     * Create the retrofit request
     */
    public RestaurantsRepository() {
          }

    /**
     * Create a public LiveData list of restaurants using a private MutableLiceData list
     * @param location use to calculate position from the indicated location
     * @param radius use to calculate the search distance between location and max search range
     * @param type use to declare type of search: restaurants
     * @param apiKey use for access at GooglePlacesSearch api
     *               request to PlacesSearchApi: nearbyPlacesApiRequests.getNearbyPlacesList(param).enqueue(CallBack)
     *               onResponse() create a list of places (type = restaurant) using CallBack<PlacesSearchApi>()
     *               onResponse(), onFailure() setValue List of restaurants or null to MutableLiveData<List<Restaurant>>
     * @return getRestaurantsList() return MutableLiveData<List<Restaurant>> nearbyRestaurantsList = new MutableLiveData<List<Restaurant>>()
     */
     public LiveData<List<Restaurant>> getRestaurantsList(String location, int radius, String type, String apiKey){

        List<Restaurant> restaurants = new ArrayList<>();

        nearbyPlacesApiRequests.getNearbyPlacesList(location, radius, type, apiKey)
                .enqueue(new Callback<PlacesSearchApi>() {
                    @Override
                    public void onResponse(Call<PlacesSearchApi> call, Response<PlacesSearchApi> response) {
                        List<Result> results = response.body().getResults();
                        int size = results.size();
                        for(int i = 0; i < size; i ++) {
                            Restaurant restaurant = new Restaurant(setName(results, i)
                                    ,setAddress(results, i)
                                    ,setPicture(results, i, apiKey)
                                    ,setPlaceId(results, i)
                                    ,setRating(results, i)
                                    ,setPhoneNumber(results, i)
                                    ,setWebSite(results, i)
                                    ,setLocation(results, i)
                                    ,setOpenNow(results, i));
                            restaurants.add(restaurant);
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

    //-------------METHODS FOR SET SEARCH VALUES IN OnResponse()-----------------
    private String getPicture(String photoReference, String apiKey) {
        return "https://maps.googleapis.com/maps/api/place/photo?" + "photoreference=" + photoReference
                + "&maxwidth=" + 250 + "&key=" + apiKey;
    }

    private String setPicture(List<Result>results, int i, String apiKey) {
        return results.get(i).getPhotos() != null ?
                getPicture(results.get(i).getPhotos().get(0).getPhotoReference(),
                        apiKey)
                : null;
    }

    private String setPlaceId(List<Result>results, int i) {
        return results.get(i).getPlaceId() != null ? results.get(i).getPlaceId() : "";
    }

    private String setPhoneNumber(List<Result>results, int i) {
        return results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
    }

    private String setWebSite(List<Result>results, int i) {
        return results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
    }

    private String setName(List<Result>results, int i) {
        return results.get(i).getName() != null ? results.get(i).getName() : "";
    }

    private String setAddress(List<Result>results, int i) {
        return results.get(i).getVicinity() != null ? results.get(i).getVicinity() : "";
    }

    private double setRating(List<Result>results, int i) {
        return results.get(i).getRating() != null ? results.get(i).getRating() : 0;
    }

    private Boolean setOpenNow(List<Result>results, int i) {
        return results.get(i).getOpeningHours() != null ? results.get(i).getOpeningHours().getOpenNow() : false;
    }

    private Location setLocation(List<Result>results, int i) {
        return results.get(i).getGeometry().getLocation();
    }

}
