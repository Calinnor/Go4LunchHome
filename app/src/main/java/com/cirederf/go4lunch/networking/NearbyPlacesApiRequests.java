package com.cirederf.go4lunch.networking;

import com.cirederf.go4lunch.models.apiDetailModels.RestaurantDetailsApi;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbyPlacesApiRequests {

    @GET("nearbysearch/json?")
    Call<PlacesSearchApi> getNearbyPlacesList(@Query("location") String location,
                                                     @Query("radius") int radius,
                                                     @Query("type") String type,
                                                     @Query("key") String key);

    @GET("details/json?")
    Call<RestaurantDetailsApi> getRestaurantDetails(@Query("placeId") String placeId,
                                                    @Query("key") String key);

}
