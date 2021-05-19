package com.cirederf.go4lunch.viewmodels;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.cirederf.go4lunch.repository.UserRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserViewModel extends ViewModel {

    //----------FOR DATA-------------
    private Task<Void> userToCreate;
    private Task<Void> userToDelete;


    //----------REPOSITORY----------
    private UserRepository userDataSource;
    private Query getUsers;

    //----------CONSTRUCTOR CALL IN FACTORY-----------
    public UserViewModel(UserRepository userDataSource) {
        this.userDataSource = userDataSource;
    }

    public void initUserData(String uid, String username, @Nullable String urlPicture
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

    public void initListOfFirestoreUser() {
        if(this.getUsers != null) {
            return;
        }
        userDataSource = UserRepository.getInstance();
        getUsers = userDataSource.getListOfFirestoreUsers();
    }

    public Query setFirestoreUserList() {
        return this.getUsers;
    }

    public void deleteFirestoreUser(String uid) {
        userDataSource = UserRepository.getInstance();
        userToDelete = userDataSource.deleteFirestoreUser(uid);
    }




}
