package com.cirederf.go4lunch.networking;

import com.cirederf.go4lunch.models.apiModels.GoogleApiPlacesNearbySearchRestaurants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbyPlacesApi {


    @GET("nearbysearch/json?")
    Call<GoogleApiPlacesNearbySearchRestaurants> getNearbyRestaurants(@Query("location") String location,
                                                                      @Query("radius") int radius,
                                                                      @Query("type") String type,
                                                                      @Query("key") String key);

    @GET("nearbysearch/json?")
    Call<GoogleApiPlacesNearbySearchRestaurants> getNearbyRestaurantsList(@Query("location") String location,
                                                                      @Query("radius") int radius,
                                                                      @Query("type") String type,
                                                                      @Query("key") String key);

}
