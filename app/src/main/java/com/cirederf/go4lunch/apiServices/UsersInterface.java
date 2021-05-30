package com.cirederf.go4lunch.apiServices;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public interface UsersInterface {

    //-------------CREATE------------
    Task<Void> createFirestoreUser(String uid, String username, @Nullable String urlPicture
            , String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating, @Nullable String restaurantName );

    //---------------READ------------
    Query getUsersCollection();
    DocumentReference currentUserDocumentReference(String uid);
    Task<DocumentSnapshot> getUser(String uid);

    //-------------UPDATE----------------
    Task<Void> updateChosenRestaurant(String uid, String chosenRestaurant);
    Task<Void> updateTypeRestaurant(String uid, String typenRestaurant);
    Task<Void> updateNameRestaurant(String uid, String nameRestaurant);

    //-------------DELETE------------------
    Task<Void> deleteFirestoreUser(String uid);
}
