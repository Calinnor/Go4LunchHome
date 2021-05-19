package com.cirederf.go4lunch.repository;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.api.UserFirebaseRequest;
import com.cirederf.go4lunch.apiServices.firestoreUtils.UserHelper;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static android.provider.Settings.Global.getString;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UserRepository {

    /**
     * Singleton for userrepo
     * @return usezrrepo
     */
    private static UserRepository userRepository;

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    private final CollectionReference userDataRequest = UserFirebaseRequest.getUsersCollection();

    /**
     * Create the retrofit request
     */
    public UserRepository() {
    }

    public Task<Void> createFirestoreUser(String uid, String username, @Nullable String urlPicture
            , @Nullable String chosenRestaurant, @Nullable String restaurantType
            , @Nullable String rating ) {
        User userToCreate = new User(uid, username, urlPicture, chosenRestaurant, restaurantType, rating);
        return userDataRequest.document(uid).set(userToCreate);
    }

    //----- GET THE LIST OF USERS in asyncTask-----
    public Query getListOfFirestoreUsers() {
        //return UserHelper.getUsersCollection();
        return userDataRequest;
    }

    // ----- DELETE A USER FROM FIRESTORE-----
    public Task<Void> deleteFirestoreUser(String uid) {
        return userDataRequest.document(uid).delete();
    }
}
