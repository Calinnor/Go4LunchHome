package com.cirederf.go4lunch.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.repository.UserRepository;
import com.google.android.gms.tasks.Task;

public class UserViewModel extends ViewModel {

    //----------FOR DATA-------------
    private Task<Void> userToCreate;
    private Task<Void> userToDelete;
    private Task<Void> restoNameToUpdate;
    private LiveData<User> userDetails;

    //----------REPOSITORY----------
    private UserRepository userDataSource;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public UserViewModel(UserRepository userDataSource) {
        this.userDataSource = userDataSource;
    }

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

    public void updateRestoChosen(String uid, String chosenRestaurant) {
        userDataSource = UserRepository.getInstance();
        restoNameToUpdate = userDataSource.updateChosenRestaurant(uid, chosenRestaurant);
    }

    //this make chose reto dont update
//    public void userDetails(String uid) {
//        if(userDetails != null) {
//            return;
//        }
//        userDataSource = UserRepository.getInstance();
//        userDetails = userDataSource.getUsersDetailsLiveData(uid);
//    }

    public void initUserLivedataDetails(String uid) {
        userDataSource = UserRepository.getInstance();
        userDetails = userDataSource.getUsersDetailsLiveData(uid);
    }

    public LiveData<User> getUserLivedataDetails() {
        return this.userDetails;
    }

    public void deleteFirestoreUser(String uid) {
        userDataSource = UserRepository.getInstance();
        userToDelete = userDataSource.deleteFirestoreUser(uid);
    }

}
