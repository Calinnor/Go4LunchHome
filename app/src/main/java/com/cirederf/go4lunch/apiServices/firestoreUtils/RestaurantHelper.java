package com.cirederf.go4lunch.apiServices.firestoreUtils;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RestaurantHelper {

    //todo determine if this class is necessary !

    private static final String COLLECTION_NAME = "restaurant";

    // ----- COLLECTION REFERENCE -----
    public static CollectionReference getRestaurantsFirestoreCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // ----- CREATE A RESTAURANT AS A DOCUMENT IN RESTAURANT-COLLECTION -----
    /**
     * @param workmatesFirestoreUserList: list of user who as this restaurant for choice
     * @param placeId: id for the restaurant
     * @return
     */
    public static Task<Void> createFirestoreRestaurant(List<User> workmatesFirestoreUserList, String restaurantName, String placeId) {
        Restaurant restaurantFirestoreToCreate = new Restaurant(workmatesFirestoreUserList, restaurantName, placeId);
        return RestaurantHelper.getRestaurantsFirestoreCollection().document(placeId).set(restaurantFirestoreToCreate);
    }

    // ----- GET RESTAURANT -----
    public static Task<DocumentSnapshot> getFirestoreRestaurant(String placeId){
        return RestaurantHelper.getRestaurantsFirestoreCollection().document(placeId).get();
    }

    // ----- UPDATE List<User> workmatesFirestoreUserList -----
    public static Task<Void> updateWorkmatesFirestoreUserList(List<User> workmatesFirestoreUserList, String placeId) {
        return RestaurantHelper.getRestaurantsFirestoreCollection().document(placeId).update("workmatesFirestoreUserList", workmatesFirestoreUserList);
    }

    // ----- DELETE -----
    public static Task<Void> deleteFirestoreRestaurant(String placeId) {
        return RestaurantHelper.getRestaurantsFirestoreCollection().document(placeId).delete();
    }

}
