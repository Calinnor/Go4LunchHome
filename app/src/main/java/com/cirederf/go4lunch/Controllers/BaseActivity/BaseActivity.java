package com.cirederf.go4lunch.Controllers.BaseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.cirederf.go4lunch.Controllers.Activities.LoginActivity;
import com.cirederf.go4lunch.Controllers.Activities.MainActivity;
import com.cirederf.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loadLocale();
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

    protected void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                configuration,
                getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.settings), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.my_langage), lang);
        editor.apply();
    }

    protected void loadLocale(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.settings), Activity.MODE_PRIVATE);
        String language = preferences.getString(getString(R.string.my_langage), "en" );
        setLocale(language);
    }
}