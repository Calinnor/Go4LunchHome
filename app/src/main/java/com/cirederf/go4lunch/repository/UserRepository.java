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

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UsersInterface {

    private static UserRepository userRepository;
    private final MutableLiveData<List<User>> _usersList = new MutableLiveData<>();
    private final MutableLiveData<List<User>> _usersListWithRestaurant = new MutableLiveData<>();
    private final LiveData<List<User>> usersList = _usersList;
    private final LiveData<List<User>> usersListWithRestaurant = _usersListWithRestaurant;
    //private final MutableLiveData<Integer> _workmatesNumber = new MutableLiveData<>();
    //private final LiveData<Integer> workmatesNumber = _workmatesNumber;

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
            , @Nullable String rating,@Nullable String restaurantName
    ) {
        User userToCreate = new User(uid, username, urlPicture, chosenRestaurant, restaurantType, rating, restaurantName
        );
        return this.currentUserDocumentReference(uid).set(userToCreate);
    }

    @Override
    public CollectionReference getCollectionUser() {
        return usersDataRequest;
    }

    @Override
    public Task<DocumentSnapshot> getUser(String uid) {
        return getCollectionUser().document(uid).get();
    }

    @Override
    public Query getUsersByChosenRestaurant(String chosenRestaurant) {
        return getCollectionUser().whereEqualTo("chosenRestaurant", chosenRestaurant);
    }

    @Override
    public DocumentReference currentUserDocumentReference(String uid) {
        return getCollectionUser().document(uid);
    }

    @Override
    public void updateChosenRestaurant(String uid, String chosenRestaurant) {
        currentUserDocumentReference(uid).update("chosenRestaurant", chosenRestaurant);
    }

    @Override
    public void updateTypeRestaurant(String uid, String typeRestaurant) {
        currentUserDocumentReference(uid).update("restaurantType", typeRestaurant);
    }

    @Override
    public void updateNameRestaurant(String uid, String nameRestaurant) {
        currentUserDocumentReference(uid).update("restaurantName", nameRestaurant);
    }

    @Override
    public void deleteFirestoreUser(String uid) {
        currentUserDocumentReference(uid).delete();
    }

    //-----------LIVEDATA TRANSFORM---------------
    public LiveData<List<User>> getUsersList() {//
        getCollectionUser().addSnapshotListener((queryDocumentSnapshots, e) ->
        {
            if (queryDocumentSnapshots != null) {
                //sometimes had a null query with :
                            //com.google.firebase.firestore.FirebaseFirestoreException: PERMISSION_DENIED: Missing or insufficient permissions.
                            //modify permissions in firestore like this :
                            //match /users/{userId}/{document=**} {
                            //allow read, write: if request.auth.uid != null && request.auth.uid == userId;
                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                List<User> users = new ArrayList<>();
                int size = userList.size();
                for (int i = 0; i < size; i++) {
                    User user = userList.get(i).toObject(User.class);
                    assert user != null;
                    user.setIsChosenRestaurantDisplay(true);
                    users.add(user);
                }
                _usersList.setValue(users);
            }
        });
        return usersList;
    }

    public LiveData<List<User>> getUsersListByChosenRestaurant(String chosenRestaurant) {
        getUsersByChosenRestaurant(chosenRestaurant).addSnapshotListener(
                (queryDocumentSnapshots, e) ->
        {
            if (queryDocumentSnapshots != null) {
                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                List<User> users = new ArrayList<>();
                int size = userList.size();

                for (int i = 0; i < size; i++) {
                    User user = userList.get(i).toObject(User.class);
                    assert user != null;
                    user.setIsChosenRestaurantDisplay(false);
                    users.add(user);
                }
                _usersListWithRestaurant.setValue(users);
            }
        });
        return usersListWithRestaurant;
    }

    public LiveData<Integer> getWorkmatesNumber(String chosenRestaurant) {
        final MutableLiveData<Integer> _workmatesNumber = new MutableLiveData<>();

        getUsersByChosenRestaurant(chosenRestaurant).addSnapshotListener((snapshots, e) -> {
            if (snapshots != null && e == null) {
                _workmatesNumber.setValue(snapshots.size());
            }
        });
        return _workmatesNumber;
    }


}
