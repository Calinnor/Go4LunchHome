package com.cirederf.go4lunch.networking;

import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.GoogleApiPlacesNearbySearchRestaurants;

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

    public NearbyPlacesRepository() {
        nearbyPlacesApi = RetrofitService.createService(NearbyPlacesApi.class);
    }

    public MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> getNearbyRestaurants(String location, int radius, String type, String key) {
        MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> nearbyRestaurantsData = new MutableLiveData<>();
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

}
