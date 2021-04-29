package com.cirederf.go4lunch.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cirederf.go4lunch.R;

public class DetailsRestaurantFragment extends Fragment {

    public DetailsRestaurantFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_restaurant, container, false);
    }
}