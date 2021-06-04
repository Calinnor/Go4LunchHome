package com.cirederf.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.UserViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.models.apiNearbyModels.Location;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;
import com.cirederf.go4lunch.models.apiNearbyModels.PlusCode;
import com.cirederf.go4lunch.models.apiNearbyModels.Result;
import com.cirederf.go4lunch.api.PlacesApiRequests;
import com.cirederf.go4lunch.api.RetrofitService;
import com.cirederf.go4lunch.apiServices.placesInterfaces.NearbyPlaceInterface;
import com.cirederf.go4lunch.viewmodels.UserViewModel;

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

    private UserViewModel userViewModel;

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
                        assert response.body() != null;
                        List<Result> results = response.body().getResults();
                        /**
                         * In idea:
                         * List<User> workmatesChekingWithThisResto = new ArrayList();
                         * userViewModel.initLivedataUserListWithChosenRestaurant(setPlaceId(results, i);
                         * workmatesChekingWithThisResto = userVieModel.getLivedataUsersListWithRestaurant();
                         */
                        int size = results.size();
                        for(int i = 0; i < size; i ++) {
                            //todo ask why it's not a good way to do that
                            Restaurant nearbySearchRestaurant = new Restaurant(
                                    results.get(i).getName()
                                    ,setAddress(results, i)
                                    ,setRating(results, i)
                                    ,setPicture(results, i, apiKey)
                                    ,setPlaceId(results, i)
                                    ,setOpenNow(results, i)
                                    ,setType(results, i)
                                    /**
                                     * Here
                                     * may add param "int size" in restaurant model then
                                     * setSize(workmatesChekingWithThisResto.size();
                                     */
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

    private String setType(List<Result>results, int i){
        return results.get(i).getTypes() != null ? results.get(i).getTypes().get(0) : null;}

}
