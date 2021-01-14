package com.cirederf.go4lunch.Controllers.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.Controllers.BaseActivity.BaseActivity;
import com.cirederf.go4lunch.Controllers.Fragments.YourLunchFragment;
import com.cirederf.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

      @BindView(R.id.activity_main_toolbar)
      Toolbar toolbar;
      @BindView(R.id.activity_main_drawer_layout)
      DrawerLayout drawerLayout;
      @BindView(R.id.activity_main_nav_view)
      NavigationView navigationView;

    private Fragment yourLunchFragment;

    private static final int ITEM_MENU_LOGOUT = 0;
    private static final int FRAGMENT_SETTINGS = 1;
    private static final int FRAGMENT_YOUR_LUNCH = 2;
    private static final int SIGN_OUT_TASK = 10;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.content_name)
    TextView tvContenName;

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
      //  this.showDefaultFragment();
        this.updateUi();
    }


    /**
     *  FIREBASE REQUESTS
     *  GetInstance
     *  Get data
     *  Logout
     *  UpdateUi
     */

    //Get instance
    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    //Get data
    private void updateUsername(TextView textViewUsernameToUpdate){
        String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();
        textViewUsernameToUpdate.setText(username);
    }

    private void updateUserMail(TextView textViewUserMailToUpdate){
        String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
        textViewUserMailToUpdate.setText(email);
    }

    private void updateUserPicture(ImageView imageViewUserPictureToUpdate){
        Glide.with(this)
                .load(this.getCurrentUser().getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(imageViewUserPictureToUpdate);
    }

    //Logout
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

    //Update UI
    private void updateUi(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_username_txt);
        TextView navUserMail = headerView.findViewById(R.id.nav_header_user_email_txt);
        ImageView navUserPicture = headerView.findViewById(R.id.nav_header_user_picture);
        this.updateUsername(navUsername);
        this.updateUserMail(navUserMail);
        this.updateUserPicture(navUserPicture);
    }


    /**
     * ACTIONS
     * LogoutClick
     * BottomNavigation
     * DrawerNavigation
     * 
     */
    //Logout
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
    
    //BottomNavigation
    private void configureBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {//new bottomnavigationview with setOn
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        tvContenName.setText(R.string.bottom_navigation_menu_map);
                        return true;
                    case R.id.chat:
                        tvContenName.setText(R.string.bottom_navigation_menu_list);
                        return true;
                    case R.id.profil:
                        tvContenName.setText(R.string.bottom_navigation_menu_workmate);
                        return true;
                }
                return false;
            }
        });
    }

    //DrawerNavigation 
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
                this.showYourLunchFragment();
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
     */    
    protected void configureToolbar(){
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    private void configureNavigationDrawer(){
        navigationView.setNavigationItemSelectedListener(this);//with setNavigation
    }
    //TODO find the difference between set and setOnNavigation and try to merge the two method similare but not equals

    // Configure item menu views as fragments views and a first fragment view
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, fragment).commit();
        }
    }

    private void showYourLunchFragment(){
        if (this.yourLunchFragment == null) this.yourLunchFragment = YourLunchFragment.newInstance();
        this.startTransactionFragment(this.yourLunchFragment);
    }


}