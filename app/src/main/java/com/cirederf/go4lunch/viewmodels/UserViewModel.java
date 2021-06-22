package com.cirederf.go4lunch.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class UserViewModel extends ViewModel {

    //----------FOR DATA-------------
    private Task<Void> userToCreate;
    private LiveData<List<User>> userList;
    private LiveData<List<User>> userListWithRestaurant;
    private LiveData<Integer> workmatesNumber;

    //----------REPOSITORY----------
    private UserRepository userDataSource;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public UserViewModel(UserRepository userDataSource) {
        this.userDataSource = userDataSource;
    }

    //------------CREATE FIRESTORE USER-----------------
    public void initUserDataToCreate(String uid, String username, @Nullable String urlPicture
            , String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating, @Nullable String restaurantName){
        if(this.userToCreate == null) {
            userDataSource = UserRepository.getInstance();
            userToCreate = userDataSource.createFirestoreUser(uid, username, urlPicture
                    , chosenRestaurant, restaurantType, rating
                    , restaurantName
            );
        }
    }

    public Task<Void> setFirestoreUserDetails() {
        return this.userToCreate;
    }

    //-------------READ IN FIRESTORE------------
   public Task<DocumentSnapshot> returnUserDetailDocument(String uid) {
       userDataSource = UserRepository.getInstance();
       return userDataSource.getUser(uid);
   }

    public void initLivedataUsersList() {
        if (userList != null) {
            return;
        }
        userDataSource = UserRepository.getInstance();
        userList = userDataSource.getUsersList();
    }

    public void initLivedataUserListWithChosenRestaurant(String chosenRestaurant) {
        if (userList != null) {
            return;
        }
        userDataSource = UserRepository.getInstance();
        userListWithRestaurant = userDataSource.getUsersListByChosenRestaurant(chosenRestaurant);
    }

    public LiveData<List<User>> getLivedataUsersListWithRestaurant() {
        return this.userListWithRestaurant;
    }

    public LiveData<List<User>> getLivedataUsersList() {
        return this.userList;
    }

    public LiveData<Integer> getWorkmatesNumber(String chosenRestaurant) {
        userDataSource = UserRepository.getInstance();
        workmatesNumber = userDataSource.getWorkmatesNumber(chosenRestaurant);
        return this.workmatesNumber;
    }

    //------------------UPDATE IN FIRESTORE--------------
    public void updateChosenRestaurant(String uid, String chosenRestaurant) {
        userDataSource = UserRepository.getInstance();
        userDataSource.updateChosenRestaurant(uid, chosenRestaurant);
    }

    public void updateRestaurantType(String uid, String restaurantType) {
        userDataSource = UserRepository.getInstance();
        userDataSource.updateTypeRestaurant(uid, restaurantType);
    }

    public void updateNameRestaurant (String uid, String restaurantName) {
        userDataSource = UserRepository.getInstance();
        userDataSource.updateNameRestaurant(uid, restaurantName);
    }

    //-------------------DELETE IN FIRESTORE--------------
    public void deleteFirestoreUser(String uid) {
        userDataSource = UserRepository.getInstance();
        userDataSource.deleteFirestoreUser(uid);
    }

}
