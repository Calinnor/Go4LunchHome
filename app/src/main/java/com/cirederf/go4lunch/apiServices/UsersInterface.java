package com.cirederf.go4lunch.apiServices;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

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
    void updateChosenRestaurant(String uid, String chosenRestaurant);
    void updateTypeRestaurant(String uid, String typenRestaurant);
    void updateNameRestaurant(String uid, String nameRestaurant);

    //-------------DELETE------------------
    void deleteFirestoreUser(String uid);
}
