package com.cirederf.go4lunch.Controllers.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.Controllers.BaseActivity.BaseActivity;
import com.cirederf.go4lunch.Controllers.Fragments.LogoutFragment;
import com.cirederf.go4lunch.Controllers.Fragments.SettingsFragment;
import com.cirederf.go4lunch.Controllers.Fragments.YourLunchFragment;
import com.cirederf.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view)
    NavigationView navigationView;

    private Fragment logoutFragment;
    private Fragment settingsFragment;
    private Fragment yourLunchFragment;

    private static final int FRAGMENT_LOGOUT = 0;
    private static final int FRAGMENT_SETTINGS = 1;
    private static final int FRAGMENT_YOUR_LUNCH = 2;
    private static final int SIGN_OUT_TASK = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureToolbar();
        this.configureNavigationDrawer();
        this.configureDrawerLayout();
        this.showDefaultFragment();
    }

    // Actions
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.activity_main_drawer_settings:
                this.showFragment(FRAGMENT_SETTINGS);
                break;
            case R.id.activity_main_drawer_your_lunch:
                this.showFragment(FRAGMENT_YOUR_LUNCH);
                break;
            case R.id.activity_main_drawer_logout:
                this.showFragment(FRAGMENT_LOGOUT);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //configuration toolbar, drawerLayout and navigationDrawer
    protected void configureToolbar(){
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    private void configureNavigationDrawer(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Configure fragments ,fragments views and first fragment view
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    private void showSettingsFragment(){
        if (this.settingsFragment == null) this.settingsFragment = SettingsFragment.newInstance();
        this.startTransactionFragment(this.settingsFragment);
    }

    private void showYourLunchFragment(){
        if (this.yourLunchFragment == null) this.yourLunchFragment = YourLunchFragment.newInstance();
        this.startTransactionFragment(this.yourLunchFragment);
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_LOGOUT :
                this.onClickLogoutButton();
                break;
            case FRAGMENT_SETTINGS:
                this.showSettingsFragment();
                break;
            case FRAGMENT_YOUR_LUNCH:
                this.showYourLunchFragment();
                break;
            default:
                break;
        }
    }

    private void showDefaultFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            this.showFragment(FRAGMENT_YOUR_LUNCH);
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

//    private void updateUi(){
//        TextView userMail = findViewById(R.id.nav_header_user_email_txt);
//        TextView userName = findViewById(R.id.nav_header_username_txt);
//        //Get email & username from Firebase
//        String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
//        String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();
//
//        //Update views with data
//        userName.setText(username);
//        userMail.setText(email);
//    }



   //Logout actions :http request, close activity, show dialog to confirm option

    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public void onClickLogoutButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_logout_account)
                .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        signOutUserFromFirebase();
                    }
                })
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

}