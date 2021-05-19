package com.cirederf.go4lunch.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cirederf.go4lunch.R;

import butterknife.ButterKnife;

public class ListWorkmatesListFragment extends Fragment {

    public static ListWorkmatesListFragment newInstance() {
        return (new ListWorkmatesListFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_list_workmates, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}