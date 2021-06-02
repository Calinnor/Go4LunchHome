package com.cirederf.go4lunch.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.UserViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.viewmodels.UserViewModel;
import com.cirederf.go4lunch.views.NearbyRestaurantsListAdapter;
import com.cirederf.go4lunch.views.WorkmatesListAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListWorkmatesFragment extends Fragment {

    @BindView(R.id.list_of_workmates_recyclerView)
    RecyclerView workmatesRecyclerView;

    private UserViewModel userViewModel;

    public static ListWorkmatesFragment newInstance() {
        return (new ListWorkmatesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_list_workmates, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userViewModel == null) {
            this.configureUserViewModel();
        }
    }

    private void configureUserViewModel() {
        UserViewModelFactory userViewModelFactory = Injection.provideUserDetailsFactory();
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        this.getUsersList();
    }

    private void getUsersList() {
        this.userViewModel.initLivedataUsersList();
        this.userViewModel.getLivedataUsersList()
                .observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                ListWorkmatesFragment.this.configureRecyclerAdapterForWks(ListWorkmatesFragment.this.requireView(), users);
            }
        });
    }

    private void configureRecyclerAdapterForWks(View requireView, List<User> users) {
        WorkmatesListAdapter workmatesListAdapter = new WorkmatesListAdapter(users);
        workmatesRecyclerView.setAdapter(workmatesListAdapter);
        RecyclerView.LayoutManager workmatesView = new LinearLayoutManager(requireView.getContext());
        workmatesRecyclerView.setLayoutManager(workmatesView);
    }

}