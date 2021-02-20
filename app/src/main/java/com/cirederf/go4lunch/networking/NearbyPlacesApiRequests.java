package com.cirederf.go4lunch.networking;

import com.cirederf.go4lunch.models.apiModels.PlacesSearchApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbyPlacesApiRequests {


//    @GET("nearbysearch/json?")
//    Call<PlacesSearchApi> getNearbyRestaurants(@Query("location") String location,
//                                               @Query("radius") int radius,
//                                               @Query("type") String type,
//                                               @Query("key") String key);

    @GET("nearbysearch/json?")
    Call<PlacesSearchApi> getNearbyPlacesList(@Query("location") String location,
                                                     @Query("radius") int radius,
                                                     @Query("type") String type,
                                                     @Query("key") String key);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
