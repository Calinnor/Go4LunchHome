package com.cirederf.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        this.startCorrectActivity();
    }

    private void startCorrectActivity() {
       // this.startLogin();
        this.startMain();
        //TODO: after isCurrentUserLogged implement, add here if logged or not and add startMain. Try but don't work...may work on this.
    }

    private void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}