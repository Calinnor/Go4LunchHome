package com.cirederf.go4lunch.Controllers.BaseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cirederf.go4lunch.Controllers.Activities.LoginActivity;
import com.cirederf.go4lunch.Controllers.Activities.MainActivity;
import com.cirederf.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getFragmentLayout());
        ButterKnife.bind(this);
    }

    public abstract int getFragmentLayout();

    // --------------------
    // UI
    // --------------------

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

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }
}