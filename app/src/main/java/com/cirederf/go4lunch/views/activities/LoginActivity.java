package com.cirederf.go4lunch.views.activities;

import androidx.annotation.Nullable;

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

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final int RC_SIGNIN = 100;
    private static final int TWITTER_PROVIDER_CHOICE = 400;
    private static final int GOOGLE_PROVIDER_CHOICE = 300;
    private static final int FACEBOOK_PROVIDER_CHOICE = 200;

    @BindView(R.id.button_login_with_twitter)
    Button buttonTwitter;
    @BindView(R.id.button_login_with_facebook)
    Button facebook;
    @BindView(R.id.button_login_with_google)
    Button google;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(isCurrentUserLogged()) {
//            this.startMain();
//        }
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.getResponseAfterSignInClose(requestCode, resultCode, data);
    }

    //Requests Firebase
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

    //Utils
    private void getResponseAfterSignInClose(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGNIN) {
            if (resultCode == RESULT_OK) {
                toastShowLoginResult(getApplicationContext(), getString(R.string.connection_succeed));
                this.startMain();
            } else {
                if (response == null) {
                    toastShowLoginResult(getApplicationContext(), getString(R.string.error_authentication_canceled));
                } else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    toastShowLoginResult(getApplicationContext(), getString(R.string.error_no_internet));
                } else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    toastShowLoginResult(getApplicationContext(), getString(R.string.error_unknown_error));
                }
            }
        }
    }

    //UI
    private void toastShowLoginResult(Context context, String response){
        Toast toast = Toast.makeText(context, response, Toast.LENGTH_LONG );
        toast.show();
    }

    private AuthUI.IdpConfig getProviderId(int providerIdChoice) {
        AuthUI.IdpConfig providerId = null;

        if(providerIdChoice == GOOGLE_PROVIDER_CHOICE){
            providerId = new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build();
        }
        if(providerIdChoice == FACEBOOK_PROVIDER_CHOICE){
            providerId = new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build();
        }
        if(providerIdChoice == TWITTER_PROVIDER_CHOICE){
            providerId = new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build();
        }
        return providerId;
    }


}