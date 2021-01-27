package com.cirederf.go4lunch.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.Result;
import com.cirederf.go4lunch.viewmodels.NearbyRestaurantsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListRestaurantsFragment extends Fragment {

    ArrayList<Result> restaurantsArrayList = new ArrayList<>();
    NearbyRestaurantsViewModel nearbyRestaurantsViewModel;

    @BindView(R.id.textView2)
    TextView textViewTest;

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

    @OnClick(R.id.fragment_main_button)
    public void submit(View view) {
        this.configureNearbyRestaurantViewModel();
        /**
         * at this point body contains 20 restaurants.
         */
        //TODO: modify access to key in viewmodel. Actually hard writing. Dont understand why a cant use get.string(R.string...)
        //TODO implements result view.

    }

    private void configureNearbyRestaurantViewModel() {
        nearbyRestaurantsViewModel = ViewModelProviders.of(this).get(NearbyRestaurantsViewModel.class);
        nearbyRestaurantsViewModel.init();
        nearbyRestaurantsViewModel.getNearbyRestaurantsRepository().observe(this, googleApiPlacesNearbySearchRestaurants -> {
                    List<Result> nearbyRestaurants = googleApiPlacesNearbySearchRestaurants.getResults();
                    restaurantsArrayList.addAll(nearbyRestaurants);
                });
    }
}