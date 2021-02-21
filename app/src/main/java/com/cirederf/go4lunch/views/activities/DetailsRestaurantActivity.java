package com.cirederf.go4lunch.views.activities;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.views.fragments.DetailsRestaurantFragment;

public class DetailsRestaurantActivity extends BaseActivity {

    @Override
    public int getActivityLayout() {
        return R.layout.activity_details_restaurant;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.configureAndShowDetailsFragment();
    }

    private void configureAndShowDetailsFragment(){
        DetailsRestaurantFragment detailsRestaurantFragment = (DetailsRestaurantFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_details);
        if (detailsRestaurantFragment == null) {
            detailsRestaurantFragment = new DetailsRestaurantFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_details, detailsRestaurantFragment)
                    .commit();
        }
    }
}