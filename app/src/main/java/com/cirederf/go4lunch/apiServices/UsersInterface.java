package com.cirederf.go4lunch.apiServices;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public interface UsersInterface {

    //-------------CREATE------------
    Task<Void> createFirestoreUser(String uid, String username, @Nullable String urlPicture
            , String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating, @Nullable String restaurantName,
                                   @Nullable String recyclerDisplay, @Nullable Boolean isChosenRestaurant );

    //---------------READ------------
    CollectionReference getCollectionUser();
    DocumentReference currentUserDocumentReference(String uid);
    Task<DocumentSnapshot> getUser(String uid);
    Query getUsersByChosenRestaurant(String chosenRestaurant);

    //-------------UPDATE----------------
    Task<Void> updateChosenRestaurant(String uid, String chosenRestaurant);
    Task<Void> updateTypeRestaurant(String uid, String typenRestaurant);
    Task<Void> updateNameRestaurant(String uid, String nameRestaurant);
    Task<Void> updateisChosenRestaurant(String uid, boolean b);

    //-------------DELETE------------------
    Task<Void> deleteFirestoreUser(String uid);
}
