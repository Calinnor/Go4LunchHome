package com.cirederf.go4lunch.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.api.UserFirebaseRequest;
import com.cirederf.go4lunch.apiServices.UsersInterface;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UsersInterface {

    private static UserRepository userRepository;
    private final MutableLiveData<User> _userLiveData = new MutableLiveData<>();
    private final LiveData<User> userLiveData = _userLiveData;
    private final MutableLiveData<List<User>> _usersList = new MutableLiveData<>();
    private final LiveData<List<User>> usersList = _usersList;

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    private final CollectionReference usersDataRequest = UserFirebaseRequest.getUsersCollection();

    public UserRepository() {
    }

    //----------OVERRIDES CRUD------------
    @Override
    public Task<Void> createFirestoreUser(String uid, String username, @Nullable String urlPicture
            , String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating ) {
        User userToCreate = new User(uid, username, urlPicture, chosenRestaurant, restaurantType, rating);
        return this.currentUserDocumentReference(uid).set(userToCreate);
    }

    @Override
    public Task<DocumentSnapshot> getUser(String uid) {
        return usersDataRequest.document(uid).get();
    }

    @Override
    public DocumentReference currentUserDocumentReference(String uid) {
        return usersDataRequest.document(uid);
    }

    @Override
    public Query getUsersCollection() {
        return usersDataRequest;
    }

    @Override
    public Task<Void> updateChosenRestaurant(String uid, String chosenRestaurant) {
        return currentUserDocumentReference(uid).update("chosenRestaurant", chosenRestaurant);
    }

    @Override
    public Task<Void> deleteFirestoreUser(String uid) {
        return currentUserDocumentReference(uid).delete();
    }

    //----------Get LIVEDATA DETAILS FOR A FIRESTORE USER------------
    public LiveData<User> getLiveDataUserDetails(String uid) {
        this.getUser(uid)
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot != null) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    _userLiveData.setValue(currentUser);
                    }
                });
        return userLiveData;
    }

    //------------------get a livedata list of firestore users-------------
//    public LiveData<List<User>> getLivedataUsersList() {
//        this.getUsersCollection().addOnSuccessListener(documentSnapshots -> {
//            List<User> users = new ArrayList<>();
//            for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
//                if (documentSnapshot != null) {
//                    User user = documentSnapshot.toObject(User.class);
//                    users.add(user);
//                }
//            }
//            _usersList.setValue(users);
//        });
//        return usersList;
//    }



}
