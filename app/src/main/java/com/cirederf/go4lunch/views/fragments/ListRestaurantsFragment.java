package com.cirederf.go4lunch.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.NearbyRestaurantsViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.NearbyRestaurantsViewModel;
import com.cirederf.go4lunch.views.NearbyRestaurantsListAdapter;
import com.cirederf.go4lunch.views.activities.DetailsRestaurantActivity;

import java.util.List;

import butterknife.ButterKnife;

public class ListRestaurantsFragment extends Fragment implements NearbyRestaurantsListAdapter.OnItemRestaurantClickListerner {

    private NearbyRestaurantsViewModel nearbyRestaurantsViewModel;
    public static final String INTENT_RESTAURANT_PARAM = "Restaurant";

    public static ListRestaurantsFragment newInstance() {
        return (new ListRestaurantsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_restaurants, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nearbyRestaurantsViewModel == null) {
            this.configureNearbyRestaurantsViewModel();
        }
    }

    private void getRestaurantsList() {
        String location = "48.410692,2.738093";
        int radius = 15000;
        String type = "restaurant";
        String apiKey = getString(R.string.places_api_google_key);
        this.nearbyRestaurantsViewModel.initRestaurantsList(location, radius, type, apiKey);
        this.nearbyRestaurantsViewModel.getListRestaurantsLiveData()
                .observe(getViewLifecycleOwner(), restaurants -> configureRecyclerViewAdapter(requireView(), restaurants));
    }

    //------------CONFIGURATIONS---------------
    private void configureNearbyRestaurantsViewModel() {
        NearbyRestaurantsViewModelFactory nearbyRestaurantsViewModelFactory = Injection.provideNearbySearchFactory();
        nearbyRestaurantsViewModel = ViewModelProviders.of(this, nearbyRestaurantsViewModelFactory).get(NearbyRestaurantsViewModel.class);
        this.getRestaurantsList();
    }

    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
        RecyclerView recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
        NearbyRestaurantsListAdapter nearbyRestaurantsListAdapter = new NearbyRestaurantsListAdapter(restaurants, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
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
        Intent intent = new Intent(getContext(), DetailsRestaurantActivity.class);
        intent.putExtra(INTENT_RESTAURANT_PARAM, restaurant);
        this.startActivity(intent);
    }

}