package com.cirederf.go4lunch.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.RestaurantDetailsViewModelFactory;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;
import com.firebase.ui.auth.AuthUI;

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
    @BindView(R.id.restaurant_details_type)
    TextView textType;
    @BindView(R.id.restaurant_details_phone)
    TextView textPhone;
    @BindView(R.id.restaurant_details_website)
    TextView textWebSide;
    @BindView(R.id.restaurant_details_pluscode)
    TextView textPlusCode;
    @BindView(R.id.restaurant_details_priceLevel)
    TextView textprice;
    @BindView(R.id.restaurant_details_reference)
    TextView textreference;
    @BindView(R.id.restaurant_details_scope)
    TextView textscope;

    private String placeId;
    private RestaurantDetailsViewModel restaurantDetailsViewModel;
    private static final int PHONE_CALL_BUTTON = 100;
    private static final int WEBSITE_BUTTON = 200;

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
                .observe(
                        this,
                        (Restaurant restaurant) -> {
                            refreshRestaurantDetails(restaurant);
                        });
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
        name.setText(restaurant.getName());
        typeAndAddress.setText(restaurant.getAddress()+", "+restaurant.getType());
        textType.setText(restaurant.getType());
        Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
        textPhone.setText(restaurant.getPhoneNumber());
        textWebSide.setText(restaurant.getWebsite());
        //textPlusCode.setText((CharSequence) restaurant.getPlusCode());
        //textprice.setText(restaurant.getPriceLevel());
        textreference.setText(restaurant.getReference());
        textscope.setText(restaurant.getScope());
    }

    //------------UTILS METHODS-------------
    //-----PHONE-----
    private String getRestaurantPhoneNumber() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return null;
        return restaurant.getPhoneNumber();
    }

    private void startPhoneCall() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(getRestaurantPhoneNumber())));
    }

    //--------WEBSITE-----------
    private void launchWebView() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getRestaurantWebSiteUri())));
    }

    private String getRestaurantWebSiteUri() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return null;
        return restaurant.getWebsite();
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