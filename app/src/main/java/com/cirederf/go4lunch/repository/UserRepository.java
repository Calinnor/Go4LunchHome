package com.cirederf.go4lunch.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.api.UserFirebaseRequest;
import com.cirederf.go4lunch.apiServices.UsersInterface;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UsersInterface {

    private static UserRepository userRepository;
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
            ,  @Nullable String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating,@Nullable String restaurantName) {
        User userToCreate = new User(uid, username, urlPicture, chosenRestaurant, restaurantType, rating, restaurantName);
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
    public Task<Void> updateTypeRestaurant(String uid, String typeRestaurant) {
        return currentUserDocumentReference(uid).update("restaurantType", typeRestaurant);
    }

    @Override
    public Task<Void> updateNameRestaurant(String uid, String nameRestaurant) {
        return currentUserDocumentReference(uid).update("restaurantName", nameRestaurant);
    }


    @Override
    public Task<Void> deleteFirestoreUser(String uid) {
        return currentUserDocumentReference(uid).delete();
    }


    //-----------Livedata---------------
    public LiveData<List<User>> getUsersList() {

        getUsersCollection().addSnapshotListener((queryDocumentSnapshots, e) ->
        {
            if (queryDocumentSnapshots != null) {
                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                List<User> users = new ArrayList<>();
                int size = userList.size();
                for (int i = 0; i < size; i++) {
                    User user = userList.get(i).toObject(User.class);
                    users.add(user);
                }
                _usersList.setValue(users);
            }
        });
        return usersList;
    }

}
