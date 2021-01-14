package com.cirederf.go4lunch.Controllers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cirederf.go4lunch.R;

public class ListRestaurantsFragment extends Fragment {
    public static ListRestaurantsFragment newInstance() {
        return (new ListRestaurantsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_restaurants, container, false);
    }
}