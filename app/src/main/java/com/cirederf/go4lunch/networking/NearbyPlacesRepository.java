package com.cirederf.go4lunch.networking;

import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiModels.GoogleApiPlacesNearbySearchRestaurants;
import com.cirederf.go4lunch.models.apiModels.Location;
import com.cirederf.go4lunch.models.apiModels.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPlacesRepository {

    //private String apiKey;

    private static NearbyPlacesRepository nearbyPlacesRepository;

    //public static NearbyPlacesRepository getInstance(String apiKey) {
    public static NearbyPlacesRepository getInstance() {
        if (nearbyPlacesRepository == null) {
            //nearbyPlacesRepository = new NearbyPlacesRepository(apiKey);
            nearbyPlacesRepository = new NearbyPlacesRepository();
        }
        return nearbyPlacesRepository;
    }

    private NearbyPlacesApi nearbyPlacesApi;

    //private NearbyPlacesRepository(String apiKey) {
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
    //public MutableLiveData<List<Restaurant>> getNearbyRestaurantsList(String location, int radius, String type) {

        MutableLiveData<List<Restaurant>> nearbyRestaurantsList = new MutableLiveData<>();
        //initListRestaurant() use in configureviewmodel is with MutableLiveData<List<Restaurant>> restaurantsListMutableLivedata

        List<Restaurant> restaurants = new ArrayList<>();
        //nearbyPlacesApi.getNearbyRestaurantsList(location, radius, type, apiKey)
        nearbyPlacesApi.getNearbyRestaurantsList(location, radius, type, key)
                .enqueue(new Callback<GoogleApiPlacesNearbySearchRestaurants>() {
                    @Override
                    public void onResponse(Call<GoogleApiPlacesNearbySearchRestaurants> call, Response<GoogleApiPlacesNearbySearchRestaurants> response) {
                        // List<Result> results = googleApiPlacesNearbySearchRestaurants.getResults();
                        List<Result> results = response.body().getResults();
                        //int defaultRestaurantPicture = R.drawable.default_restaurant_picture;
                        int size = results.size();
                        for(int i = 0; i < size; i ++) {
                            //String name = results.get(i).getName() != null ? results.get(i).getName() : "";
                            //String address = results.get(i).getVicinity() != null ? results.get(i).getVicinity() : "";
                            //double rating = results.get(i).getRating() != null ? results.get(i).getRating() : 0;

                            //String picture = results.get(i).getPhotos() != null ?
                            //        getPicture(results.get(i).getPhotos().get(0).getPhotoReference(),
                            //                250,"AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI")
                            //       : null;

                            //String placeId = results.get(i).getPlaceId() != null ? results.get(i).getPlaceId() : "";
                            //setPlaceId(results, i);

                            //String phoneNumber = results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
                            //String website = results.get(i).getBusinessStatus() != null ? results.get(i).getBusinessStatus() : "";
                            //Boolean openNow = results.get(i).getOpeningHours() != null ? results.get(i).getOpeningHours().getOpenNow() : false;
                            //Location location = results.get(i).getGeometry().getLocation();
                            Restaurant restaurant = new Restaurant(/*name*/setName(results, i),/*address*/setAddress(results, i),/*picture*/ setPicture(results, i), /*placeId*/setPlaceId(results, i),/*rating*/setRating(results, i),/*phoneNumber*/ setPhoneNumber(results, i),/*website*/ setWebSite(results, i),/*location*/setLocation(results, i),/*openNow*/setOpenNow(results, i));
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

    private String getPicture(String photoReference, int maxWidth, String key) {
        return "https://maps.googleapis.com/maps/api/place/photo?" + "photoreference=" + photoReference
                + "&maxwidth=" + maxWidth + "&key=" + key;
    }

    private String setPicture(List<Result>results, int i) {
        String picture = results.get(i).getPhotos() != null ?
                getPicture(results.get(i).getPhotos().get(0).getPhotoReference(),
                        250,"AIzaSyAQIMmBdFBM6kVUJ5HyC7tpUXKbkIow_lI")
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


