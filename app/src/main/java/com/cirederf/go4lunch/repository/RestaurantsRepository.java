package com.cirederf.go4lunch.repository;

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
    private NearbyPlacesApiRequests nearbyPlacesApiRequests;
    /**
     * Create the retrofit request
     */
    public RestaurantsRepository() {
        nearbyPlacesApiRequests = RetrofitService.createService(NearbyPlacesApiRequests.class);
    }

    /**
     * Create a MutableLiveData list of restaurants
     * @param location use to calculate position from the indicated location
     * @param radius use to calculate the search distance between location and max search range
     * @param type use to declare type of search: restaurants
     * @param apiKey use for access at GooglePlacesSearch api
     *               request to PlacesSearchApi: nearbyPlacesApiRequests.getNearbyPlacesList(param).enqueue(CallBack)
     *               onResponse() create a list of places (type = restaurant) using CallBack<PlacesSearchApi>()
     *               onResponse(), onFailure() setValue List of restaurants or null to MutableLiveData<List<Restaurant>>
     * @return getRestaurantsList() return MutableLiveData<List<Restaurant>> nearbyRestaurantsList = new MutableLiveData<List<Restaurant>>()
     */
    public MutableLiveData<List<Restaurant>> getRestaurantsList(String location, int radius, String type, String apiKey){

        MutableLiveData<List<Restaurant>> nearbyRestaurantsList = new MutableLiveData<>();
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
                        nearbyRestaurantsList.setValue(restaurants);
                    }

                    @Override
                    public void onFailure(Call<PlacesSearchApi> call, Throwable t) {
                        nearbyRestaurantsList.setValue(null);
                    }
                });

        return nearbyRestaurantsList;
    }

    //-------------METHODS FOR SET SEARCH VALUES IN OnResponse()-----------------
    private String getPicture(String photoReference, int maxWidth, String apiKey) {
        return "https://maps.googleapis.com/maps/api/place/photo?" + "photoreference=" + photoReference
                + "&maxwidth=" + maxWidth + "&key=" + apiKey;
    }

    private String setPicture(List<Result>results, int i, String apiKey) {
        String picture = results.get(i).getPhotos() != null ?
                getPicture(results.get(i).getPhotos().get(0).getPhotoReference(),
                        250,/*"AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI"*/apiKey)
                : null;
        return picture;
    }

    private String setPlaceId(List<Result>results, int i) {
        String placeId = results.get(i).getPlaceId() != null ? results.get(i).getPlaceId() : "";
        return placeId;
    }

    private String setPhoneNumber(List<Result>results, int i) {
        String phoneNumber = results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
        return phoneNumber;
    }

    private String setWebSite(List<Result>results, int i) {
        String webSite = results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
        return webSite;
    }

    private String setName(List<Result>results, int i) {
        String name = results.get(i).getName() != null ? results.get(i).getName() : "";
        return name;
    }

    private String setAddress(List<Result>results, int i) {
        String address = results.get(i).getVicinity() != null ? results.get(i).getVicinity() : "";
        return address;
    }

    private double setRating(List<Result>results, int i) {
        double rating = results.get(i).getRating() != null ? results.get(i).getRating() : 0;
        return rating;
    }

    private Boolean setOpenNow(List<Result>results, int i) {
        Boolean openNow = results.get(i).getOpeningHours() != null ? results.get(i).getOpeningHours().getOpenNow() : false;
        return openNow;
    }

    private Location setLocation(List<Result>results, int i) {
        Location location = results.get(i).getGeometry().getLocation();
        return location;
    }

}
