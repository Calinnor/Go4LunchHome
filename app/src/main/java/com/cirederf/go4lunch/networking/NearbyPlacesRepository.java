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

    public MutableLiveData<List<Restaurant>> getNearbyRestaurantsList(String location, int radius, String type, String key) {

        MutableLiveData<List<Restaurant>> nearbyRestaurantsList = new MutableLiveData<>();

        List<Restaurant> restaurants = new ArrayList<>();
        nearbyPlacesApi.getNearbyRestaurantsList(location, radius, type, key)
                .enqueue(new Callback<GoogleApiPlacesNearbySearchRestaurants>() {
                    @Override
                    public void onResponse(Call<GoogleApiPlacesNearbySearchRestaurants> call, Response<GoogleApiPlacesNearbySearchRestaurants> response) {
                        List<Result> results = response.body().getResults();
                        int size = results.size();
                        for(int i = 0; i < size; i ++) {
                            Restaurant restaurant = new Restaurant(setName(results, i)
                                    ,setAddress(results, i)
                                    ,setPicture(results, i)
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


