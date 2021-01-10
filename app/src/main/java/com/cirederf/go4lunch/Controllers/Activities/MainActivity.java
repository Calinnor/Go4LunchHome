package com.cirederf.go4lunch.Controllers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cirederf.go4lunch.Controllers.Fragments.LogoutFragment;
import com.cirederf.go4lunch.Controllers.Fragments.SettingsFragment;
import com.cirederf.go4lunch.Controllers.Fragments.YourLunchFragment;
import com.cirederf.go4lunch.R;
import com.google.android.material.navigation.NavigationView;

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

    //configuration drawer and toolbar
    private void configureToolbar(){
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

    private void showLogoutFragment(){
        if (this.logoutFragment == null) this.logoutFragment = LogoutFragment.newInstance();
        this.startTransactionFragment(this.logoutFragment);
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
                this.showLogoutFragment();
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

}