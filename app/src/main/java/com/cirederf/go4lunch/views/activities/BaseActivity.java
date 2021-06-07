package com.cirederf.go4lunch.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.RestaurantDetailsViewModelFactory;
import com.cirederf.go4lunch.injections.UserViewModelFactory;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;
import com.cirederf.go4lunch.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private final List<User> userList = new ArrayList<>();
    protected UserViewModel userViewModel;
    protected RestaurantDetailsViewModel restaurantDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getActivityLayout());
        ButterKnife.bind(this);
        this.configureRestaurantDetailsViewModel();
        this.configureUserViewModel();
    }

    public abstract int getActivityLayout();

    protected void configureToolbar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void configureUserViewModel() {
        UserViewModelFactory userViewModelFactory = Injection.provideUserDetailsFactory();
        this.userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
    }

    protected void configureRestaurantDetailsViewModel() {
        RestaurantDetailsViewModelFactory detailsRestaurantViewModelFactory = Injection.provideRestaurantDetailsFactory();
        this.restaurantDetailsViewModel = ViewModelProviders.of(this, detailsRestaurantViewModelFactory).get(RestaurantDetailsViewModel.class);
    }

    //-----FIREBASE USER VERIFICATION-----
    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (
            this.getCurrentUser() != null); }

    //-----FIRESTORE USER VERIFICATION-----
    protected Boolean isFirestoreUser(){
        FirebaseUser currentUser = this.getCurrentUser();
        for (User user : userList){
            if (user.getUid().equals(currentUser.getUid())) return true;
        }
        return false;
    }

    // -----ERROR HANDLER-----
    protected OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
            }
        };
    }
}