package com.cirederf.go4lunch.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cirederf.go4lunch.R;

import butterknife.ButterKnife;

public class DetailsRestaurantFragment extends Fragment {

    public static DetailsRestaurantFragment newInstance() {
        return (new DetailsRestaurantFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_restaurant, container, false);
    }


}