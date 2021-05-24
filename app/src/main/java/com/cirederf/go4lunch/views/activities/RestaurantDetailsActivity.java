package com.cirederf.go4lunch.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import butterknife.BindView;
import butterknife.OnClick;

import static com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment.RESTAURANT_PLACE_ID_PARAM;

public class RestaurantDetailsActivity extends BaseActivity {

    @BindView(R.id.restaurant_details_type_and_address)
    TextView typeAndAddress;
    @BindView(R.id.restaurant_details_name)
    TextView name;
    @BindView(R.id.restaurant_detail_picture)
    ImageView imageView;
    @BindView(R.id.restaurant_is_chosen_button)
    FloatingActionButton mChosenRestaurantButton;

    private String placeId;
    private RecyclerView workmatesRecyclerView;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_details_restaurant;
    }

    @Override
    protected void onResume() {
        super.onResume();
        placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
        this.getDetailsRestaurant();
        this.configureWorkmatesListRecyclerView();
    }

    private void configureWorkmatesListRecyclerView() {
        workmatesRecyclerView = findViewById(R.id.workmates_recyclerView);
        RecyclerView.LayoutManager workmatesListLayoutManager = new LinearLayoutManager(this);
        workmatesRecyclerView.setLayoutManager(workmatesListLayoutManager);
    }

    //-------------FOR RESTAURANT DETAILS VALUES----------------
    private void getDetailsRestaurant() {
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
                .observe(this, restaurant -> RestaurantDetailsActivity.this.refreshRestaurantDetails(restaurant));
    }
//    explication de la methodologie de observe

//        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
//                .observe(
//                getViewLifecycleOwner() /*pour un fragment/"this" pour une activité. il s'agit du proprietaire de la vue*/
//                , new Observer<Restaurant>() /*ici la data observée*/{
//        @Override
//        public void onChanged(Restaurant restaurant) {
//            refreshRestaurantDetails(restaurant);
//        }
//    });
//}

    private void refreshRestaurantDetails(Restaurant restaurant) {
        name.setText(restaurant.getRestaurantName());
        typeAndAddress.setText(restaurant.getType() + ", " + restaurant.getAddress());
        Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
    }

    //------------UTILS METHODS-------------
    //-----PHONE-----
    private String getRestaurantPhoneNumber() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return "no restaurant to display";
        return restaurant.getPhoneNumber();
    }

    private void startPhoneCall() {
        if(getRestaurantPhoneNumber() == null) {
            toastShowActionNullResult(getApplicationContext(), "No phone number");
        } else {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getRestaurantPhoneNumber())));
        }
    }

    //--------WEBSITE-----------
    private void launchWebView() {
        if(getRestaurantWebSiteUri() == null) {
            toastShowActionNullResult(getApplicationContext(), "No website");
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getRestaurantWebSiteUri())));
        }
    }

    private String getRestaurantWebSiteUri() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return null;
        return restaurant.getWebsite();
    }

    //-------------FIRESTORE DATA-------------------
    private void getUserDetail() {
        this.userViewModel.initUserLivedataDetails(getCurrentUser().getUid());
        this.userViewModel.getUserLivedataDetails()
                .observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        RestaurantDetailsActivity.this.refreshUserDetails();
                    }
                });
    }

    private void refreshUserDetails() {
            if (this.isChosen()) {
                mChosenRestaurantButton.setImageResource(0);
            } else {
                mChosenRestaurantButton.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
            }
    }

    private Boolean isChosen() {
        String chosenRestaurant = userViewModel.getUserLivedataDetails().getValue().getChosenRestaurant();
        if (chosenRestaurant.equals(placeId)) {
            userViewModel.updateRestoChosen(getCurrentUser().getUid(), "No restaurant as chosen restaurant");
            return true;
        }else {
            userViewModel.updateRestoChosen(getCurrentUser().getUid(), placeId);
            return false;
        }
    }

    //-----------UI UTIL--------------
    private void toastShowActionNullResult(Context context, String response){
        Toast toast = Toast.makeText(context, response, Toast.LENGTH_LONG );
        toast.show();
    }

    //----------ACTIONS---------

    @OnClick({R.id.button_phone_call, R.id.button_web_site_launcher, R.id.restaurant_is_chosen_button})
    public void onRestaurantDetailsACTIONClick(View view){
        switch (view.getId()) {

            case R.id.button_phone_call:
                startPhoneCall();
                break;

            case R.id.button_web_site_launcher:
                launchWebView();
                break;

            case R.id.restaurant_is_chosen_button:
                getUserDetail();
                break;

            default:
                break;
        }
    }

}