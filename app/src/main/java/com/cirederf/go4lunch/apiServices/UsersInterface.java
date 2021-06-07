package com.cirederf.go4lunch.apiServices;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.cirederf.go4lunch.models.Restaurant;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public interface UsersInterface {

    //-------------CREATE------------
    Task<Void> createFirestoreUser(String uid, String username, @Nullable String urlPicture
            , String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating, @Nullable String restaurantName
    );

    //---------------READ------------
    CollectionReference getCollectionUser();
    DocumentReference currentUserDocumentReference(String uid);
    Task<DocumentSnapshot> getUser(String uid);
    Query getUsersByChosenRestaurant(String chosenRestaurant);

    //-------------UPDATE----------------
    Task<Void> updateChosenRestaurant(String uid, String chosenRestaurant);
    Task<Void> updateTypeRestaurant(String uid, String typenRestaurant);
    Task<Void> updateNameRestaurant(String uid, String nameRestaurant);

    //-------------DELETE------------------
    Task<Void> deleteFirestoreUser(String uid);
}
//    @Override
//    public LiveData<List<Restaurant>> getRestaurantsListLiveData(String location, int radius, String type, String apiKey){
//
//        List<Restaurant> restaurants = new ArrayList<>();
//apiDataSource.getNearbyPlacesList(location, radius, type, apiKey)
//        .enqueue(new Callback<PlacesSearchApi>() {
//@Override
//public void onResponse(Call<PlacesSearchApi> call, Response<PlacesSearchApi> response) {
//        assert response.body() != null;
//        List<Result> results = response.body().getResults();
//
//        for (Result result : results) {
//        getUsersByChosenRestaurant(result.getPlaceId()).addSnapshotListener((query, e) -> {
//        int workmatesNumber = query != null ? query.getDocuments().size() : 0;
//
//        Restaurant restaurant = new Restaurant.Builder()
//        .restaurantName(result.getName())
//        .workmatesNumber(workmatesNumber)
//        .build();
//
//        restaurants.add(restaurant);
//        });
//        _restaurants.setValue(restaurants);
//        }
//        }
//
//@Override
//public void onFailure(Call<PlacesSearchApi> call, Throwable t) {
//        _restaurants.setValue(null);
//        }
//        });
//        return restaurantsList;
//        }
