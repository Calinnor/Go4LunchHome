package com.cirederf.go4lunch.views.activities;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.RestaurantDetailsViewModelFactory;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;

import butterknife.BindView;

import static com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment.RESTAURANT_PLACE_ID_PARAM;

public class FetchRestaurantDetailsActivity extends BaseActivity {

    @BindView(R.id.type_and_address)
    TextView typeAndAddress;

    @BindView(R.id.restaurant_detail_name)
    TextView name;

    @BindView(R.id.restaurant_detail_picture)
    ImageView imageView;

    @BindView(R.id.type)
    TextView textType;

    private String placeId;
    private RestaurantDetailsViewModel restaurantDetailsViewModel;


    @Override
    public int getActivityLayout() {
        //return R.layout.fragment_details_restaurant;
        return R.layout.activity_details_restaurant;
    }


    @Override
    protected void onResume() {
        super.onResume();
        placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
        this.configureRestaurantDetailsViewModel();
    }

    private void getDetailsRestaurant() {
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
        //this.restaurantDetailsViewModel.getRestaurantDetailsLiveData(placeId, apiKey)
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
                .observe(
                        this,
                        (Restaurant restaurant) -> {
                            refreshRestaurantDetails(restaurant);
                        });
    }

    private void refreshRestaurantDetails(Restaurant restaurant) {
        name.setText(restaurant.getName());
        typeAndAddress.setText(restaurant.getAddress()+", "+restaurant.getType());
        textType.setText(restaurant.getType());
        Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
    }

    //----------CONFIGURATION-----------
    private void configureRestaurantDetailsViewModel() {
        RestaurantDetailsViewModelFactory detailsRestaurantViewModelFactory = Injection.provideDetailsFactory();
        this.restaurantDetailsViewModel = ViewModelProviders.of(this, detailsRestaurantViewModelFactory).get(RestaurantDetailsViewModel.class);
        this.getDetailsRestaurant();
    }


}