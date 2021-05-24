package com.cirederf.go4lunch.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.api.UserFirebaseRequest;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository userRepository;
    private final MutableLiveData<User> _userLiveData = new MutableLiveData<>();
    private final LiveData<User> userLiveData = _userLiveData;


    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    private final CollectionReference userDataRequest = UserFirebaseRequest.getUsersCollection();

    public UserRepository() {
    }

    public Task<Void> createFirestoreUser(String uid, String username, @Nullable String urlPicture
            , String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating ) {
        User userToCreate = new User(uid, username, urlPicture, chosenRestaurant, restaurantType, rating);
        return userDataRequest.document(uid).set(userToCreate);
    }

    //----------Get DETAILS FOR A FIRESTORE USER------------
    public LiveData<User> getUsersDetailsLiveData(String uid) {
        userDataRequest.document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    User currentUser = documentSnapshot.toObject(User.class);
                    _userLiveData.setValue(currentUser);
                });
        return userLiveData;
    }

    //get a list of users
    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> usersList = new MutableLiveData<>();
        userDataRequest.get().addOnSuccessListener(documentSnapshots -> {
            List<User> users = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
                if (documentSnapshot != null) {
                    User user = documentSnapshot.toObject(User.class);
                    users.add(user);
                }
            }
            usersList.setValue(users);
        });
        return usersList;
    }

    public Task<Void> updateChosenRestaurant(String uid, String chosenRestaurant) {
        return userDataRequest.document(uid).update("chosenRestaurant", chosenRestaurant);
    }

    // ----- DELETE A USER FROM FIRESTORE-----
    public Task<Void> deleteFirestoreUser(String uid) {
        return userDataRequest.document(uid).delete();
    }

}
