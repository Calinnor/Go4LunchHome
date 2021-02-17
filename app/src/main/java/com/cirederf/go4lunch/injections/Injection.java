package com.cirederf.go4lunch.injections;

import android.content.Context;

import com.cirederf.go4lunch.networking.NearbyPlacesApi;
import com.cirederf.go4lunch.networking.RetrofitService;
import com.cirederf.go4lunch.repository.RestaurantsRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static RestaurantsRepository provideRestaurantsDataSource(Context context, String location, int radius, String type, String apiKey) {
        NearbyPlacesApi nearbyPlacesApi = RetrofitService.createService(NearbyPlacesApi.class);
        return new RestaurantsRepository();
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static RestaurantsViewModelFactory provideRestaurantsViewModelFactory(Context context, String location, int radius, String type, String apiKey) {
        RestaurantsRepository restaurantsDataSource = provideRestaurantsDataSource(context, location, radius, type, apiKey);
        Executor executor = provideExecutor();
        return new RestaurantsViewModelFactory(restaurantsDataSource, executor);
    }
}
