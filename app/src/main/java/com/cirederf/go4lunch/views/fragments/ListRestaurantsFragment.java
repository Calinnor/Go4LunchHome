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
import com.cirederf.go4lunch.injections.RestaurantsViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.viewmodels.RestaurantsViewModel;
import com.cirederf.go4lunch.views.RestaurantAdapter;
import com.cirederf.go4lunch.views.activities.DetailsRestaurantActivity;

import java.util.List;

import butterknife.ButterKnife;

public class ListRestaurantsFragment extends Fragment implements RestaurantAdapter.OnItemRestaurantClickListerner {

    private RestaurantsViewModel restaurantsViewModel;

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
        if (restaurantsViewModel == null) {
            this.configureRestaurantsViewModel();
        }
    }

//    private void configureSearchRestaurantsViewModel() {
//        SearchRestaurantsViewModelFactory searchRestaurantsViewModelFactory = SearchInjection.provideSearchFactory();
//        searchRestaurantsViewModel = ViewModelProviders.of(this, searchRestaurantsViewModelFactory).get(SearchRestaurantsViewModel.class);
//       this.getRestaurantsList();
//    }
//
//    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
//        RecyclerView recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
//        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(/*this,*/restaurants, this);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
//        recyclerView.setAdapter(restaurantAdapter);
//        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
//        recyclerView.setLayoutManager(restaurantRecyclerView);
//    }

    private void getRestaurantsList() {
        String location = "48.410692,2.738093";
        int radius = 15000;
        String type = "restaurant";
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantsViewModel.initRestaurantsList(location, radius, type, apiKey);
        this.restaurantsViewModel.getListRestaurantsLiveData()
                //.observe(getViewLifecycleOwner(), restaurants -> configureRecyclerViewAdapter(getView(), restaurants));
                .observe(getViewLifecycleOwner(), restaurants -> configureRecyclerViewAdapter(requireView(), restaurants));
    }

    @Override
    public void onItemClick(Restaurant restaurant) {
        Intent intent = new Intent(getContext(), DetailsRestaurantActivity.class);
        //intent.putExtra("Restaurants", restaurant);
        this.startActivity(intent);

        // this three line code is for Activity not Fragment!
        //Intent intent = new Intent(getContext(), DetailsRestaurantFragment.class);
        //intent.putExtra("Student", restaurant);
        //Fragment detailFragment = new DetailsRestaurantFragment();
        //detailFragment.setArguments(intent.getExtras());

//        DetailsRestaurantFragment detailFragment = new DetailsRestaurantFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("restaurant", restaurant);
//
//        detailFragment.setArguments(bundle);
////
////
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_content, detailFragment);
//        //fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        //this.startFragmentTransaction(restaurant);

    }

    //------------CONFIGURATIONS---------------
    private void configureRestaurantsViewModel() {
        RestaurantsViewModelFactory restaurantsViewModelFactory = Injection.provideSearchFactory();
        restaurantsViewModel = ViewModelProviders.of(this, restaurantsViewModelFactory).get(RestaurantsViewModel.class);
        this.getRestaurantsList();
    }

    private void configureRecyclerViewAdapter(View view, List<Restaurant> restaurants) {
        RecyclerView recyclerView = view.findViewById(R.id.fragment_list_restaurants_recycler_view);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurants, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(restaurantAdapter);
        RecyclerView.LayoutManager restaurantRecyclerView = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(restaurantRecyclerView);
    }

    private void startFragmentTransaction(Restaurant restaurant){
        DetailsRestaurantFragment detailFragment = new DetailsRestaurantFragment();
        //Restaurant restaurant = new Restaurant();
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurant", restaurant);
        detailFragment.setArguments(bundle);

//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_content, detailFragment);
//        //fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

}