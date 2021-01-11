package com.cirederf.go4lunch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.usermail_fromfirebase)
    TextView userMail;
    @BindView(R.id.username_fromFirebase)
    TextView username;
    @BindView(R.id.user_picture_fromfirebase)
    ImageView userPicture;
    @BindView(R.id.usename_fromfirebase_atstart)
    TextView startingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
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