package com.cirederf.go4lunch.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.apiServices.firestoreUtils.UserHelper;
import com.cirederf.go4lunch.injections.RestaurantDetailsViewModelFactory;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment.RESTAURANT_PLACE_ID_PARAM;

public class FetchRestaurantDetailsActivity extends BaseActivity {

    @BindView(R.id.restaurant_details_type_and_address)
    TextView typeAndAddress;
    @BindView(R.id.restaurant_details_name)
    TextView name;
    @BindView(R.id.restaurant_detail_picture)
    ImageView imageView;

    private String placeId;
    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_details_restaurant;
    }

    @Override
    protected void onResume() {
        super.onResume();
        placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
        this.configureRestaurantDetailsViewModel();
    }

    //----------CONFIGURATION-----------
    private void configureRestaurantDetailsViewModel() {
        RestaurantDetailsViewModelFactory detailsRestaurantViewModelFactory = Injection.provideDetailsFactory();
        this.restaurantDetailsViewModel = ViewModelProviders.of(this, detailsRestaurantViewModelFactory).get(RestaurantDetailsViewModel.class);
        this.getDetailsRestaurant();
    }

    //-------------FOR RESTAURANT DETAILS VALUES----------------
    private void getDetailsRestaurant() {
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
                .observe(this, this::refreshRestaurantDetails);
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
        //workmatesNumber.setText(workmatesNumberByRestaurant(restaurant.getRestaurantName()));
    }

    //------------UTILS METHODS-------------
    //-----PHONE-----
    private String getRestaurantPhoneNumber() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return "no restaurant to display" /*null*/;
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

    //-----------UI UTIL--------------
    private void toastShowActionNullResult(Context context, String response){
        Toast toast = Toast.makeText(context, response, Toast.LENGTH_LONG );
        toast.show();
    }

    //----------ACTIONS---------

    @OnClick({R.id.button_phone_call, R.id.button_web_site_launcher})
    public void onRestaurantDetailsACTIONClick(View view){
        switch (view.getId()) {

            case R.id.button_phone_call:
                startPhoneCall();
                break;

            case R.id.button_web_site_launcher:
                launchWebView();
                break;

            default:
                break;
        }
    }
}