package com.cirederf.go4lunch.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.RestaurantDetailsViewModelFactory;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;

import butterknife.BindView;

public class DetailsRestaurantFragment extends Fragment {

    @BindView(R.id.type_and_address)
    TextView name;

    @BindView(R.id.restaurant_detail_name)
    TextView address;

    @BindView(R.id.restaurant_detail_picture)
    ImageView imageView;

    @BindView(R.id.type)
    TextView textType;

    private Restaurant restaurant;
    private String placeId;
    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    public DetailsRestaurantFragment() {}

    private DetailsRestaurantFragment(String placeId) {
        this.placeId = placeId;
    }

    public static DetailsRestaurantFragment newInstance(String placeId) {
        return new DetailsRestaurantFragment(placeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_restaurant, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.configureDetailsRestaurantViewModel();
    }

    private void configureDetailsRestaurantViewModel() {
        RestaurantDetailsViewModelFactory detailsRestaurantViewModelFactory = Injection.provideDetailsFactory();
        restaurantDetailsViewModel = ViewModelProviders.of(this, detailsRestaurantViewModelFactory).get(RestaurantDetailsViewModel.class);
        this.getDetailsRestaurant();
    }

    private void getDetailsRestaurant() {
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
        //this.restaurantDetailsViewModel.getRestaurantDetailsLiveData(placeId, apiKey)//; ici le livedata observé
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()//; ici le livedata observé
        .observe(getViewLifecycleOwner()/*ici proprietaire du cyvle de vie: get view for fragment and this for activity*/, (Restaurant restaurant)/* ici la data produite*/ -> {
            this.restaurant = restaurant;
            setDetails();
        });

        /**
         * explication de la methodologie de observe
         */
        //this.restaurantDetailsViewModel.getRestaurantDetailsLiveData(placeId, apiKey)
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
                .observe(getViewLifecycleOwner(), new Observer<Restaurant>() {
                    @Override
                    public void onChanged(Restaurant restaurant) {
                        refreshRestaurantDetails(restaurant);
                    }
                });
    }

//    private void startPhoneCall() {
//        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetails(placeId, apiKey).getValue();
//        if (restaurant == null) return;
//        String phoneNumber = restaurant.getPhoneNumber();
//    }

    private void setDetails() {
        name.setText(restaurant.getName());
        address.setText(restaurant.getAddress());
        textType.setText(restaurant.getType());
        Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
    }

    private void refreshRestaurantDetails(Restaurant restaurant) {
        name.setText(restaurant.getName());
        address.setText(restaurant.getAddress());
        textType.setText(restaurant.getType());
        Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
    }

}