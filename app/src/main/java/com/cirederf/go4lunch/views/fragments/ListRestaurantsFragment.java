package com.cirederf.go4lunch.views.fragments;

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
import com.cirederf.go4lunch.viewmodels.NearbyRestaurantsViewModel;
import com.cirederf.go4lunch.viewmodels.RestaurantsViewModel;
import com.cirederf.go4lunch.viewmodels.SearchRestaurantsViewModel;
import com.cirederf.go4lunch.views.RestaurantAdapter;

import java.util.List;

import butterknife.ButterKnife;

public class ListRestaurantsFragment extends Fragment {

    private NearbyRestaurantsViewModel nearbyRestaurantsViewModel;
    private RestaurantsViewModel restaurantsViewModel;
    private SearchRestaurantsViewModel searchRestaurantsViewModel;

    private RecyclerView recyclerView;

    public static ListRestaurantsFragment newInstance() {
        return (new ListRestaurantsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_restaurants, container, false);
        ButterKnife.bind(this, view);
        //this.configureNearbyRestaurantViewModel();
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
//        String location = "48.410692,2.738093";
//        int radius = 15000;
//        String type = "restaurant";
//        String apiKey = getString(R.string.google_api_key);


//        nearbyRestaurantsViewModel = ViewModelProviders.of(this).get(NearbyRestaurantsViewModel.class);
//        nearbyRestaurantsViewModel.initListRestaurants();
//        nearbyRestaurantsViewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurants -> {
//                    configureRecyclerViewAdapter(getView(), restaurants);
//                });


//        RestaurantsViewModelFactory restaurantsViewModelFactory = Injection.provideRestaurantsViewModelFactory(getContext(), location, radius, type, apiKey);
//        this.restaurantsViewModel = ViewModelProviders.of(this, restaurantsViewModelFactory).get(RestaurantsViewModel.class);
//        this.restaurantsViewModel.initRestaurantsList(location, radius, type, apiKey);
//        this.restaurantsViewModel.getRestaurantsList(location, radius, type, apiKey).observe(getViewLifecycleOwner(), restaurants -> {
//            configureRecyclerViewAdapter(getView(), restaurants);
//        });

        SearchRestaurantsViewModelFactory searchRestaurantsViewModelFactory = SearchInjection.provideSearchFactory();
        searchRestaurantsViewModel = ViewModelProviders.of(this, searchRestaurantsViewModelFactory).get(SearchRestaurantsViewModel.class);

        //create the method to get restaurantlist here : this.method
        //this.getRestautantsList();
       this.getRestoListFromRestoRepo();

    }

    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
        recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this,restaurants);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(restaurantAdapter);
        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(restaurantRecyclerView);
    }

//    private void getRestautantsList() {
//        String location = "48.410692,2.738093";
//        int radius = 15000;
//        String type = "restaurant";
//        String apiKey = getString(R.string.places_api_google_key);
//        this.searchRestaurantsViewModel.getNearbyRestos(location, radius, type, apiKey)
//                .observe(getViewLifecycleOwner(), restaurants -> {
//                    configureRecyclerViewAdapter(getView(), restaurants);
//                });
//    }

    private void getRestoListFromRestoRepo() {
        String location = "48.410692,2.738093";
        int radius = 15000;
        String type = "restaurant";
        String apiKey = getString(R.string.places_api_google_key);
        this.searchRestaurantsViewModel.initRestaurantsList(location, radius, type, apiKey);
        this.searchRestaurantsViewModel.getRestaurantsFromRestaurantsRepository(/*location, radius, type, apiKey*/)
                .observe(getViewLifecycleOwner(), restaurants -> {
            configureRecyclerViewAdapter(getView(), restaurants);
                });
    }


}