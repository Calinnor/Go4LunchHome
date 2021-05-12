package com.cirederf.go4lunch.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.apiServices.firestoreUtils.UserHelper;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment;
import com.cirederf.go4lunch.views.fragments.MapFragment;
import com.cirederf.go4lunch.views.fragments.WorkmatesFragment;
import com.cirederf.go4lunch.views.fragments.YourLunchFragment;
import com.cirederf.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view)
    NavigationView navigationView;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private static final int ITEM_MENU_LOGOUT = 0;
    private static final int FRAGMENT_SETTINGS = 1;
    private static final int FRAGMENT_YOUR_LUNCH = 2;
    private static final int SIGN_OUT_TASK = 10;

//    private List<User> userList = new ArrayList<>();

    @Override
    public int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.configureBottomNavigationView();
        this.configureToolbar();
        this.configureNavigationDrawer();
        this.configureDrawerLayout();
        this.updateCurrentUserUi();
        //this.createUserInFirestore();
        this.showSelectedFragment(R.id.main_content, MapFragment.newInstance());
    }

    /**
     *  FIREBASE REQUESTS
     *  GetInstance
     *  Get data
     *  Logout
     *  UpdateUi
     */
    //---------GET FIREBASE USER----------
    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    //-----------GET USER DATA-----------
    private void updateCurrentUsername(TextView textViewUsernameToUpdate){
        String username = TextUtils.isEmpty(Objects.requireNonNull(this.getCurrentUser()).getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();
        textViewUsernameToUpdate.setText(username);
    }

    private void updateCurrentUserMail(TextView textViewUserMailToUpdate){
        String email = TextUtils.isEmpty(Objects.requireNonNull(this.getCurrentUser()).getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
        textViewUserMailToUpdate.setText(email);
    }

    private void updateCurrentUserPicture(ImageView imageViewUserPictureToUpdate){
        Glide.with(this)
                .load(Objects.requireNonNull(this.getCurrentUser()).getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(imageViewUserPictureToUpdate);
    }

    //---------FIREBASE USER LOGOUT---------------
    private void signOutCurrentUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(){
        return aVoid -> {
            if (MainActivity.SIGN_OUT_TASK == SIGN_OUT_TASK) {
                finish();
            }
        };
    }

    //---------UPDATE UI WITH FIREBASE DATA IN NAV HEADER-----------
    private void updateCurrentUserUi(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_username_txt);
        TextView navUserMail = headerView.findViewById(R.id.nav_header_user_email_txt);
        ImageView navUserPicture = headerView.findViewById(R.id.nav_header_user_picture);
        this.updateCurrentUsername(navUsername);
        this.updateCurrentUserMail(navUserMail);
        this.updateCurrentUserPicture(navUserPicture);
    }


    /**
     * ACTIONS
     * LogoutClick
     * BottomNavigation
     * DrawerNavigation
     */
    //-------------USER LOGOUT ACTIONS-------------
    public void onClickLogoutButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_logout_account)
                .setPositiveButton(R.string.popup_message_choice_yes, (dialogInterface, i) -> signOutCurrentUserFromFirebase())
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    //-----------BOTTOM NAVIGATION-------------
    private void configureBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map:
                    showSelectedFragment(R.id.map, MapFragment.newInstance());
                    return true;
                case R.id.list:
                    showSelectedFragment(R.id.list, ListRestaurantsFragment.newInstance());
                    return true;
                case R.id.workmates:
                    showSelectedFragment(R.id.workmates,WorkmatesFragment.newInstance());
                    return true;
            }
            return false;
        });
    }

    //------------NAVIGATION DRAWER-----------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.activity_main_drawer_settings:
                this.showItemMenuAction(FRAGMENT_SETTINGS);
                break;
            case R.id.activity_main_drawer_your_lunch:
                this.showItemMenuAction(FRAGMENT_YOUR_LUNCH);
                break;
            case R.id.activity_main_drawer_logout:
                this.showItemMenuAction(ITEM_MENU_LOGOUT);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showItemMenuAction(int fragmentIdentifier) {
        switch (fragmentIdentifier) {
            case ITEM_MENU_LOGOUT:
                this.onClickLogoutButton();
                break;
            case FRAGMENT_SETTINGS:
                break;
            case FRAGMENT_YOUR_LUNCH:
                this.showSelectedFragment(R.id.fragment_your_lunch, YourLunchFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * CONFIGURATION
     * toolbar
     * drawers
     * fragment views
     */
    //-----------TOOLBAR---------
    protected void configureToolbar(){
        setSupportActionBar(toolbar);
    }

    //---------DRAWERS-----------
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    //---------FRAGMENTS-----------
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, fragment).commit();
        }
    }

    private void showSelectedFragment(Integer integer, Fragment fragment) {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(integer);
        if (visibleFragment == null) {
            this.startTransactionFragment(fragment);
        }
    }

//    //firestore
//    public Boolean isFirestoreUser(){
//        FirebaseUser currentUser = this.getCurrentUser();//FirebaseAuth.getInstance().getCurrentUser();
//        for (User user : userList){
//            if (user.getUid().equals(currentUser.getUid())) return true;
//        }
//        return false;
//    }
//
//    private void createUserInFirestore(){
//        if (!isFirestoreUser()){
//            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
//            String username = this.getCurrentUser().getDisplayName();
//            String uid = this.getCurrentUser().getUid();
//            UserHelper.createUser(uid, username, urlPicture, null, null,null ,null).addOnFailureListener(this.onFailureListener());
//        }
//    }

}