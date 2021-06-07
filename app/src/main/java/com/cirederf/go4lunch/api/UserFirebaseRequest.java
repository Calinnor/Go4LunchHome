package com.cirederf.go4lunch.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserFirebaseRequest {

    private static final String COLLECTION_NAME = "users";

    // ----- COLLECTION REFERENCE -----
    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
}
