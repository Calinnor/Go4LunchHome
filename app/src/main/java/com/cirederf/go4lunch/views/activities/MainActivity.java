package com.cirederf.go4lunch.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.views.fragments.RestaurantsListFragment;
import com.cirederf.go4lunch.views.fragments.MapFragment;
import com.cirederf.go4lunch.views.fragments.SettingsFragment;
import com.cirederf.go4lunch.views.fragments.WorkmatesListFragment;
import com.cirederf.go4lunch.views.fragments.YourLunchFragment;
import com.cirederf.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{


    private static final int AUTOCOMPLETE_REQUEST_CODE = 235;
    private static final String TAG = "handle_error";
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout drawerLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.activity_main_nav_view)
    NavigationView navigationView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private static final int ITEM_MENU_LOGOUT = 0;
    private static final int FRAGMENT_SETTINGS = 1;
    private static final int FRAGMENT_YOUR_LUNCH = 2;

    private static final int apiKey = R.string.places_api_google_key;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getAutocomplete();
        this.configureBottomNavigationView();
        this.configureToolbar();
        this.configureNavigationDrawer();
        this.configureDrawerLayout();
        this.updateCurrentUserUi();
        this.showSelectedFragment(R.id.main_content, MapFragment.newInstance());

        //this one is for test with woklist
        //this.showSelectedFragment(R.id.main_content, WorkmatesListFragment.newInstance());

        //this one for restolist
        //this.showSelectedFragment(R.id.main_content, RestaurantsListFragment.newInstance());
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

    //---------FIREBASE AND FIRESTORE USER LOGOUT---------------
    private void signOutCurrentUserFromFirebaseAndFirestore(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this,
                        this.updateUIAfterFirestoreAndFirebaseRESTRequestsCompleted())
                .addOnFailureListener(this.onFailureListener());
    }

    private OnSuccessListener<Void> updateUIAfterFirestoreAndFirebaseRESTRequestsCompleted(){
        this.configureUserViewModel();
        if (this.getCurrentUser() != null) {
        this.userViewModel.deleteFirestoreUser(getCurrentUser().getUid());
        AuthUI.getInstance().delete(this);
        }
        return aVoid -> finish();
    }

    //---------UPDATE UI WITH FIREBASE DATA IN NAV HEADER-----------
    private void updateCurrentUserUi(){
        //todo determine if the user data may be cast from firestore (may introduce some values like email) or if firebase is sufficient
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
                .setPositiveButton(R.string.popup_message_choice_yes, (dialogInterface, i) -> signOutCurrentUserFromFirebaseAndFirestore())
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    //-----------BOTTOM NAVIGATION-------------
    @SuppressLint("NonConstantResourceId")
    private void configureBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map:
                    showSelectedFragment(R.id.map, MapFragment.newInstance());
                    return true;
                case R.id.list:
                    showSelectedFragment(R.id.list, RestaurantsListFragment.newInstance());
                    return true;
                case R.id.workmates:
                    showSelectedFragment(R.id.workmates, WorkmatesListFragment.newInstance());
                    return true;
            }
            return false;
        });
    }

    //------------NAVIGATION DRAWER-----------

    @SuppressLint("NonConstantResourceId")
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
                this.showSelectedFragment(R.id.fragment_settings, SettingsFragment.newInstance());
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

    private void getAutocomplete() {
        if(!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(apiKey));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_search_button:
                onSearchCalled();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void onSearchCalled() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        //reduce the radius for google search to location near the location
        double radius = this.radius * 0.0008;

        double[] boundsFromLatLng = getBoundsFromLatLng (radius, MapFragment.googleLocation.latitude, MapFragment.googleLocation.longitude);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setLocationRestriction(RectangularBounds.newInstance(
                        new LatLng(boundsFromLatLng[0], boundsFromLatLng[1]),
                        new LatLng(boundsFromLatLng[2], boundsFromLatLng[3])
                ))
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private double[] getBoundsFromLatLng(double radius, double lat, double lng) {
        double lat_change = radius / 111.2f;
        double lng_change = Math.abs(Math.cos(lat * (Math.PI / 180)));
        return new double[] {
                lat - lat_change,
                lng - lng_change,
                lat + lat_change,
                lng + lng_change
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG,  "Place: "+ place.getName() + ", " + place.getId());
                Toast.makeText(this, "ID: "
                        + place.getId() + "NAME: "
                        + place.getName() + "latLong: "
                        + place.getLatLng(), Toast.LENGTH_LONG).show();
                String placeId = place.getId();
                this.startDetailRestaurantActivity(placeId);

                //todo:
                //have sometimes this error which crashes the app
                //E/AndroidRuntime: FATAL EXCEPTION: main
                //    Process: com.cirederf.go4lunch, PID: 23751
                //    java.lang.NullPointerException: Attempt to invoke virtual method 'boolean androidx.appcompat.widget.SearchView.hasFocus()' on a null object reference
                //        at androidx.appcompat.widget.SearchView$SearchAutoComplete.onWindowFocusChanged(SearchView.java:1910)
                //        at android.view.View.dispatchWindowFocusChanged(View.java:12819)

            } else if(resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {

            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startDetailRestaurantActivity(String placeId){
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantsListFragment.RESTAURANT_PLACE_ID_PARAM, placeId);
        this.startActivity(intent);
    }
}