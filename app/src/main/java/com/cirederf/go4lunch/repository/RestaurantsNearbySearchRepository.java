package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiNearbyModels.Location;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;
import com.cirederf.go4lunch.models.apiNearbyModels.PlusCode;
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
                        int size = results.size();
                        for(int i = 0; i < size; i ++) {
                            String name = results.get(i).getName() != null ? results.get(i).getName() : "";
                            String address = results.get(i).getVicinity() != null ? results.get(i).getVicinity() : "";
                            String placeId = results.get(i).getPlaceId() != null ? results.get(i).getPlaceId() : "";

                            Restaurant nearbySearchRestaurant = new Restaurant(
                                    //setName(results, i)
                                    //name
                                    results.get(i).getName()
                                    //,setAddress(results, i)
                                    ,address
                                    ,setRating(results, i)
                                    ,setPicture(results, i, apiKey)
                                    //,setPlaceId(results, i)
                                    ,placeId
                                    //,setLocation(results, i)
                                    ,setOpenNow(results, i)
                                    ,setType(results, i)
                            );

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

    private PlusCode setPlusCode(List<Result>results, int i){
        return results.get(i).getPlusCode();}

    private Integer setPriceLevel(List<Result>results, int i){
        return results.get(i).getPriceLevel();}

    private String setReference(List<Result>results, int i){
        return results.get(i).getReference() != null ? results.get(i).getReference() : "";}

    private String setScope(List<Result>results, int i){
        return results.get(i).getScope() != null ? results.get(i).getScope() : "";}

    private String setType(List<Result>results, int i){
        return results.get(i).getTypes() != null ? results.get(i).getTypes().get(0) : null;}

    private void refreshNearbyRestaurantValues(List<Result>results, int i) {
         String name = results.get(i).getName();

    }



}
