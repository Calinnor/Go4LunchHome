package com.cirederf.go4lunch.networking;

import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiModels.GoogleApiPlacesNearbySearchRestaurants;
import com.cirederf.go4lunch.models.apiModels.Location;
import com.cirederf.go4lunch.models.apiModels.Photo;
import com.cirederf.go4lunch.models.apiModels.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPlacesRepository {

    private static NearbyPlacesRepository nearbyPlacesRepository;

    public static NearbyPlacesRepository getInstance() {
        if (nearbyPlacesRepository == null) {
            nearbyPlacesRepository = new NearbyPlacesRepository();
        }
        return nearbyPlacesRepository;
    }

    private NearbyPlacesApi nearbyPlacesApi;

    private NearbyPlacesRepository() {
        nearbyPlacesApi = RetrofitService.createService(NearbyPlacesApi.class);
    }

    public MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> getNearbyRestaurants(String location, int radius, String type, String key) {

        MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> nearbyRestaurantsData = new MutableLiveData<>();
        // method init() use in configureviewmodel is with MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> mutableLiveData

        nearbyPlacesApi.getNearbyRestaurants(location, radius, type, key)
                .enqueue(new Callback<GoogleApiPlacesNearbySearchRestaurants>() {

                    @Override
                    public void onResponse(Call<GoogleApiPlacesNearbySearchRestaurants> call, Response<GoogleApiPlacesNearbySearchRestaurants> response) {
                        if (response.isSuccessful()) {
                            nearbyRestaurantsData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GoogleApiPlacesNearbySearchRestaurants> call, Throwable t) {
                        nearbyRestaurantsData.setValue(null);
                    }
                });
        return nearbyRestaurantsData;
    }

    public MutableLiveData<List<Restaurant>> getNearbyRestaurantsList(String location, int radius, String type, String key) {

        MutableLiveData<List<Restaurant>> nearbyRestaurantsList = new MutableLiveData<>();
        //initListRestaurant() use in configureviewmodel is with MutableLiveData<List<Restaurant>> restaurantsListMutableLivedata

        List<Restaurant> restaurants = new ArrayList<>();
        nearbyPlacesApi.getNearbyRestaurantsList(location, radius, type, key)
                .enqueue(new Callback<GoogleApiPlacesNearbySearchRestaurants>() {
                    @Override
                    public void onResponse(Call<GoogleApiPlacesNearbySearchRestaurants> call, Response<GoogleApiPlacesNearbySearchRestaurants> response) {
                        // List<Result> results = googleApiPlacesNearbySearchRestaurants.getResults();
                        List<Result> results = response.body().getResults();
                        int size = results.size();
                        for(int i = 0; i < size; i ++) {
                            String name = results.get(i).getName() != null ? results.get(i).getName() : "";
                            String address = results.get(i).getVicinity() != null ? results.get(i).getVicinity() : "";
                            double rating = results.get(i).getRating() != null ? results.get(i).getRating() : 0;

                            //todo find who fetch restaurant image
                            List<Photo> photos = results.get(i).getPhotos();
                            if(photos != null && !photos.isEmpty()) {
                                String photoReference = photos.get(0).getPhotoReference();
                            }

                            String picture = results.get(i).getPhotos() != null ? getPicture(results.get(i).getPhotos().get(0).getPhotoReference(), 250, "AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI")  : "";
                            String placeId = results.get(i).getPlaceId() != null ? results.get(i).getPlaceId() : "";
                            String phoneNumber = results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
                            String website = results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
                            Boolean openNow = results.get(i).getOpeningHours() != null ? results.get(i).getOpeningHours().getOpenNow() : false;
                            Location location = results.get(i).getGeometry().getLocation();
                            Restaurant restaurant = new Restaurant(name, address, picture, placeId, rating, phoneNumber, website, location, openNow);
                            restaurants.add(restaurant);
                            //nearbyRestaurantsList.setValue(restaurants); if put ,here at each call is used
                        }
                        nearbyRestaurantsList.setValue(restaurants);
                    }

                    @Override
                    public void onFailure(Call<GoogleApiPlacesNearbySearchRestaurants> call, Throwable t) {
                        nearbyRestaurantsList.setValue(null);
                    }
                });

        return nearbyRestaurantsList;

        }

    public String getPicture(String photoReference, int maxWidth, String key) {
        return "https://maps.googleapis.com/maps/api/place/photo?" + "photoreference=" + photoReference
                + "&maxwidth=" + maxWidth + "&key=" + key;
    }


    }


