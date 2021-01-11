package com.cirederf.go4lunch.Controllers.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cirederf.go4lunch.Controllers.Activities.LoginActivity;
import com.cirederf.go4lunch.Controllers.Activities.MainActivity;
import com.cirederf.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isCurrentUserLogged()) {
            this.startMain();
        }else{
            startLogin();}
    }

    private void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Nullable
    private FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    private Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }
}