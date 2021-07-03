package com.cirederf.go4lunch.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.NearbyRestaurantsViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.NearbyRestaurantsViewModel;
import com.cirederf.go4lunch.views.NearbyRestaurantsListAdapter;
import com.cirederf.go4lunch.views.activities.RestaurantDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsListFragment extends Fragment implements NearbyRestaurantsListAdapter.OnItemRestaurantClickListerner {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fragment_list_restaurants_recycler_view)
    RecyclerView recyclerView;

    private NearbyRestaurantsViewModel nearbyRestaurantsViewModel;
    public static final String RESTAURANT_PLACE_ID_PARAM = "placeId";
    private int radius;
    private String typeToSearch;

    public static RestaurantsListFragment newInstance() {
        return (new RestaurantsListFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_list_restaurants, container, false);
        ButterKnife.bind(this, view);
        configureNearbyRestaurantsViewModel();
        setSharedPrefs();
        getRestaurantsList();
        return view;
    }

    //------------ViewModel CONFIGURATION---------------
    private void configureNearbyRestaurantsViewModel() {
        NearbyRestaurantsViewModelFactory nearbyRestaurantsViewModelFactory = Injection.provideNearbySearchFactory();
        nearbyRestaurantsViewModel = ViewModelProviders.of(this, nearbyRestaurantsViewModelFactory).get(NearbyRestaurantsViewModel.class);
    }

    //-----------FOR NEARBY RESTAURANTS LIST------------
    private void getRestaurantsList() {
        String location = MapFragment.currentUserLocation;
        String apiKey = getString(R.string.places_api_google_key);
        this.nearbyRestaurantsViewModel.initRestaurantsList(location, radius, typeToSearch, apiKey);
        this.nearbyRestaurantsViewModel
                .getListRestaurantsLiveData()
                .observe(getViewLifecycleOwner(),
                        restaurants -> RestaurantsListFragment.this.configureRecyclerViewAdapter(RestaurantsListFragment.this.requireView(), restaurants));
    }

    //-----------Adapter CONFIGURATION----------------
    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
        NearbyRestaurantsListAdapter nearbyRestaurantsListAdapter = new NearbyRestaurantsListAdapter(restaurants, this);
        recyclerView.setAdapter(nearbyRestaurantsListAdapter);
        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(restaurantRecyclerView);
    }

    //---------ACTION-----------
    @Override
    public void onItemClick(Restaurant restaurant) {
        this.startDetailRestaurantActivity(restaurant);
    }

    private void startDetailRestaurantActivity(Restaurant restaurant){
        Intent intent = new Intent(getContext(), RestaurantDetailsActivity.class);
        intent.putExtra(RESTAURANT_PLACE_ID_PARAM, restaurant.getPlaceId());
        this.startActivity(intent);
    }

    private void setSharedPrefs() {
        SharedPreferences appPrefs = requireContext().getSharedPreferences(SettingsFragment.APP_PREFS, Context.MODE_PRIVATE );
        radius = appPrefs.getInt(SettingsFragment.RADIUS_PREFS, 700);
        typeToSearch = appPrefs.getString(SettingsFragment.TYPE_PREFS, "restaurant");
    }

}