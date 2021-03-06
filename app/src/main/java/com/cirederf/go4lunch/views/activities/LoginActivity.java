package com.cirederf.go4lunch.views.activities;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cirederf.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final int RC_SIGNIN = 100;
    private static final int TWITTER_PROVIDER_CHOICE = 400;
    private static final int GOOGLE_PROVIDER_CHOICE = 300;
    private static final int FACEBOOK_PROVIDER_CHOICE = 200;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.button_login_with_twitter)
    Button buttonTwitter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.button_login_with_facebook)
    Button facebook;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.button_login_with_google)
    Button google;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.getResponseAfterSignInClose(requestCode, resultCode, data);
    }

    //----------ACTIONS---------

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.button_login_with_google, R.id.button_login_with_facebook, R.id.button_login_with_twitter })
    public void onItemClicked(View view){
        int providerIdChoice = -1;
        switch (view.getId()) {
            case R.id.button_login_with_twitter:
                providerIdChoice = TWITTER_PROVIDER_CHOICE;
                break;
            case R.id.button_login_with_facebook:
                providerIdChoice = FACEBOOK_PROVIDER_CHOICE;
                break;
            case R.id.button_login_with_google:
                providerIdChoice = GOOGLE_PROVIDER_CHOICE;
                break;
            default:
                break;
        }
        AuthUI.IdpConfig providerId = this.getProviderId(providerIdChoice);
        this.startSignInActivity(providerId);
    }

    //---------------FIREBASE REQUEST-----------
    private void startSignInActivity(AuthUI.IdpConfig providerId){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Collections.singletonList(
                                        providerId
                                )
                        )
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.cup_of_tea)
                        .build(),
                RC_SIGNIN
        );
    }

    private AuthUI.IdpConfig getProviderId(int providerIdChoice) {
        AuthUI.IdpConfig providerId = null;

        if(providerIdChoice == GOOGLE_PROVIDER_CHOICE){
            providerId = new AuthUI.IdpConfig.GoogleBuilder().build();
        }
        if(providerIdChoice == FACEBOOK_PROVIDER_CHOICE){
            providerId = new AuthUI.IdpConfig.FacebookBuilder().build();
        }
        if(providerIdChoice == TWITTER_PROVIDER_CHOICE){
            providerId = new AuthUI.IdpConfig.TwitterBuilder().build();
        }
        return providerId;
    }

    //--------------RESULT REQUEST NOTIFICATIONS-----------
    private void getResponseAfterSignInClose(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGNIN) {
            if (resultCode == RESULT_OK) {
                toastShowLoginResult(getApplicationContext(), getString(R.string.connection_succeed));
                this.createUserInFirestore();
                this.startMain();
            } else {
                if (response == null) {
                    toastShowLoginResult(getApplicationContext(), getString(R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    toastShowLoginResult(getApplicationContext(), getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    toastShowLoginResult(getApplicationContext(), getString(R.string.error_unknown_error));
                }
            }
        }
    }

    //-----------UI UTIL--------------
    private void toastShowLoginResult(Context context, String response){
        Toast toast = Toast.makeText(context, response, Toast.LENGTH_LONG );
        toast.show();
    }

    private void createUserInFirestore(){
        this.configureUserViewModel();
        if (!isFirestoreUser()){
            String urlPicture = (Objects.requireNonNull(this.getCurrentUser()).getPhotoUrl() != null) ? Objects.requireNonNull(this.getCurrentUser().getPhotoUrl()).toString() : null;
            String username = this.getCurrentUser().getDisplayName();
            String uid = this.getCurrentUser().getUid();

            this.userViewModel.initUserDataToCreate(uid, username, urlPicture, "No restaurant"
                    , null,null, null
            );
            this.userViewModel.setFirestoreUserDetails()
                    .addOnFailureListener(this.onFailureListener());
        }
    }
}