package com.cirederf.go4lunch.networking;

import com.cirederf.go4lunch.models.apiDetailModels.RestaurantDetailsApi;
import com.cirederf.go4lunch.models.apiNearbyModels.PlacesSearchApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiRequests {

    /**
     * @param location: position call in google api
     * @param radius: radius from the position call in google api
     * @param type: search type call in google api
     * @param key: call to google api key
     * @return
     */
    @GET("nearbysearch/json?")
    Call<PlacesSearchApi> getNearbyPlacesList(@Query("location") String location,
                                                     @Query("radius") int radius,
                                                     @Query("type") String type,
                                                     @Query("key") String key);

    /**
     * @param placeId: "place_id" call in google place detail
     *                ------!!! place_id != placeId !!!!------
     * @param key: call to google api key
     * @return
     */
    @GET("details/json?")
    Call<RestaurantDetailsApi> getRestaurantDetails(@Query("place_id") String placeId,
                                                    @Query("key") String key);

}
