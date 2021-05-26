package com.cirederf.go4lunch.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.repository.UserRepository;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class UserViewModel extends ViewModel {

    //----------FOR DATA-------------
    private Task<Void> userToCreate;
    private LiveData<User> userDetails;
    private LiveData<List<User>> usersList;

    //----------REPOSITORY----------
    private UserRepository userDataSource;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public UserViewModel(UserRepository userDataSource) {
        this.userDataSource = userDataSource;
    }

    //------------CREATE FIRESTORE USER-----------------
    public void initUserDataToCreate(String uid, String username, @Nullable String urlPicture
            , @Nullable String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating) {
        if(this.userToCreate == null) {
            userDataSource = UserRepository.getInstance();
            userToCreate = userDataSource.createFirestoreUser(uid, username, urlPicture, chosenRestaurant, restaurantType, rating);
        }
    }

    public Task<Void> setFirestoreUserDetails() {
        return this.userToCreate;
    }

    //-------------READ IN FIRESTORE------------
    public void initLivedataUserDetails(String uid) {
        userDataSource = UserRepository.getInstance();
        userDetails = userDataSource.getLiveDataUserDetails(uid);
    }

    public LiveData<User> setUserLivedataDetails() {
        return this.userDetails;
    }

    public void initLivedataUsersList() {
        userDataSource = UserRepository.getInstance();
        usersList = userDataSource.getLivedataUsersList();
    }

    public LiveData<List<User>> setLivedataUsersList() {
        return this.usersList;
    }

    //------------------UPDATE IN FIRESTORE--------------
    public void updateChosenRestaurant(String uid, String chosenRestaurant) {
        userDataSource = UserRepository.getInstance();
        userDataSource.updateChosenRestaurant(uid, chosenRestaurant);
    }

    //-------------------DELETE IN FIRESTORE--------------
    public void deleteFirestoreUser(String uid) {
        userDataSource = UserRepository.getInstance();
        userDataSource.deleteFirestoreUser(uid);
    }

}
