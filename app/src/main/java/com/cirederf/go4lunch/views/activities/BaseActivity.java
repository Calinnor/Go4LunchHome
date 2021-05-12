package com.cirederf.go4lunch.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.apiServices.firestoreUtils.UserHelper;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private final List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getActivityLayout());
        ButterKnife.bind(this);
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

    //FIREBASE USER VERIFICATION
    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }

    //FIRESTORE USER VERIFICATION
    protected Boolean isFirestoreUser(){
        FirebaseUser currentUser = this.getCurrentUser();//FirebaseAuth.getInstance().getCurrentUser();
        for (User user : userList){
            if (user.getUid().equals(currentUser.getUid())) return true;
        }
        return false;
    }

    protected void createUserInFirestore(){
        if (!isFirestoreUser()){
            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
            String username = this.getCurrentUser().getDisplayName();
            String uid = this.getCurrentUser().getUid();
            UserHelper.createUser(uid, username, urlPicture, null, null,null ,null).addOnFailureListener(this.onFailureListener());
        }
    }

    protected OnFailureListener onFailureListener(){
        return e -> Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
    }
}