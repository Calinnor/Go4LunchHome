package com.cirederf.go4lunch.apiServices.firestoreUtils;

import androidx.annotation.Nullable;

import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // ----- COLLECTION REFERENCE -----
    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE A USER AS A DOCUMENT IN USER-COLLECTION---
    /**
     * @param uid: id for the user
     * @param username: name/pseudo for the user
     * @param urlPicture: nullable, picture for the user
     * @param chosenRestaurant: the name of the chosen restaurant to use in the call to create a user list for the restaurant
     * @param isTheChoiceRestaurant: boolean for the active restaurant show
     * @param restaurantType: the type of the restaurant for ordering search
     * @return
     */

    public static Task<Void> createUser(String uid, String username, @Nullable String urlPicture
            , @Nullable String chosenRestaurant, @Nullable Boolean isTheChoiceRestaurant, @Nullable String restaurantType
            , @Nullable String rating ) {
        User userToCreate = new User(uid, username, urlPicture, chosenRestaurant, isTheChoiceRestaurant, restaurantType, rating);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    //-----------USERS GETTING------------

    // ----- GET A USER -----
    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }

    //----- GET THE LIST OF ALL USERS ORDER BY CHOSEN RESTAURANT NAME-----
    //first implementation : public static Task<List<User>> getUserList() {
    public static Query getUserListOrderByChosenRestaurantName(String chosenRestaurant) {
        return UserHelper.getUsersCollection().orderBy(chosenRestaurant);
    }

    //----- GET THE LIST OF ALL USERS ORDER BY CHOSEN RESTAURANT TYPE-----
    public static Query getUserListOrderByChosenRestaurantType(String restaurantType) {
        return UserHelper.getUsersCollection().orderBy(restaurantType);
    }

    //----- GET THE LIST OF ALL USERS ORDER BY USERNAME-----
    public static Query getUserListOrderByUsername(String username) {
        return UserHelper.getUsersCollection().orderBy(username);
    }

    //----- GET THE LIST OF USERS FOR A RESTAURANT-----
    public static Query getUsersByRestaurant (String chosenRestaurant) {
        return UserHelper.getUsersCollection().whereEqualTo("chosenRestaurant", chosenRestaurant);
    }

    //----- GET THE LIST OF USERS WHO DOESNT HAVE A CHOSEN RESTAURANT-----
    public static Query getUsersWithoutRestaurantChoice () {
        return UserHelper.getUsersCollection().whereEqualTo("chosenRestaurant", null);
    }

    //----- GET THE LIST OF USERS WHO HAVE A CHOSEN RESTAURANT-----
    public static Query getUsersWithRestaurantChoice () {
        return UserHelper.getUsersCollection().whereNotEqualTo("chosenRestaurant", null);
    }

    //  ----- UPDATES -----
    public static Task<Void> updateUsername(String uid, String username) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> setChosenRestaurantInFirestore(String uid, String chosenRestaurant) {
       return UserHelper.getUsersCollection().document(uid).set(chosenRestaurant);
    }

    public static Task<Void> updateIsTheChoiceRestaurantInFirestore(String uid, Boolean isTheChoiceRestaurant) {
        return UserHelper.getUsersCollection().document(uid).update("isTheChoiceRestaurant", isTheChoiceRestaurant);
    }

    public static Task<Void> setTypeChosenRestaurantInFirestore(String uid, String restaurantType) {
        return UserHelper.getUsersCollection().document(uid).set(restaurantType);
    }

    public static Task<Void> updateRating(String uid, String rating) {
        return UserHelper.getUsersCollection().document(uid).update("rating", rating);
    }

    // ----- DELETE -----
    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}