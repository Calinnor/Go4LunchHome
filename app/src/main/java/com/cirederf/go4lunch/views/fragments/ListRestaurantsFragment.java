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
import com.cirederf.go4lunch.injections.SearchInjection;
import com.cirederf.go4lunch.injections.SearchRestaurantsViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.SearchRestaurantsViewModel;
import com.cirederf.go4lunch.views.RestaurantAdapter;
import com.cirederf.go4lunch.views.activities.DetailsRestaurantActivity;

import java.util.List;

import butterknife.ButterKnife;

public class ListRestaurantsFragment extends Fragment implements RestaurantAdapter.OnItemRestaurantClickListerner {

    private SearchRestaurantsViewModel searchRestaurantsViewModel;

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
        if (searchRestaurantsViewModel == null) {
            this.configureNearbyRestaurantViewModel();
        }
    }

    private void configureNearbyRestaurantViewModel() {
        SearchRestaurantsViewModelFactory searchRestaurantsViewModelFactory = SearchInjection.provideSearchFactory();
        searchRestaurantsViewModel = ViewModelProviders.of(this, searchRestaurantsViewModelFactory).get(SearchRestaurantsViewModel.class);
       this.getRestoListFromRestoRepo();
    }

    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
        RecyclerView recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(/*this,*/restaurants, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(restaurantAdapter);
        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(restaurantRecyclerView);
    }

    private void getRestoListFromRestoRepo() {
        String location = "48.410692,2.738093";
        int radius = 25000;
        String type = "restaurant";
        String apiKey = getString(R.string.places_api_google_key);
        this.searchRestaurantsViewModel.initRestaurantsList(location, radius, type, apiKey);
        this.searchRestaurantsViewModel.getRestaurantsFromRestaurantsRepository()
                .observe(getViewLifecycleOwner(), restaurants -> configureRecyclerViewAdapter(getView(), restaurants));
    }

    @Override
    public void onItemClick(Restaurant restaurant) {
        Intent intent = new Intent(getContext(), DetailsRestaurantActivity.class);
        //intent.putExtra("Restaurants", (Parcelable) restaurant); have to put the parcelable in place ?
        this.startActivity(intent);
    }
}