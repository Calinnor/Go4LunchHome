package com.cirederf.go4lunch.Fragments;

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
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiModels.Result;
import com.cirederf.go4lunch.viewmodels.NearbyRestaurantsViewModel;
import com.cirederf.go4lunch.views.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ListRestaurantsFragment extends Fragment {

    //ArrayList<Result> restaurantsArrayList = new ArrayList<>();
    NearbyRestaurantsViewModel nearbyRestaurantsViewModel;

    private RecyclerView recyclerView;

    public static ListRestaurantsFragment newInstance() {
        return (new ListRestaurantsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_restaurants, container, false);
        ButterKnife.bind(this, view);

        this.configureNearbyRestaurantViewModel();
        return view;
    }

    private void configureNearbyRestaurantViewModel() {
        nearbyRestaurantsViewModel = ViewModelProviders.of(this).get(NearbyRestaurantsViewModel.class);
        //nearbyRestaurantsViewModel.init(); // MutableLiveData<GoogleApiPlacesNearbySearchRestaurants> mutableLiveData
        nearbyRestaurantsViewModel.initListRestaurants(); // MutableLiveData<List<Restaurant>> restaurantsListMutableLivedata
        nearbyRestaurantsViewModel.getRestaurants().observe(this, restaurants -> {
//                    List<Result> nearbyRestaurants = googleApiPlacesNearbySearchRestaurants.getResults();
//                    restaurantsArrayList.addAll(nearbyRestaurants);
                    configureRecyclerViewAdapter(getView(), restaurants);
                });
    }

//    private void configureRecyclerView(View view) {
//        recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
//        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
//        recyclerView.setLayoutManager(restaurantRecyclerView);
//    }

    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
        recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, /*restaurantsArrayList*/restaurants);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(restaurantAdapter);
        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(restaurantRecyclerView);
    }


}