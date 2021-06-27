package com.cirederf.go4lunch.views.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.UserViewModelFactory;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.viewmodels.UserViewModel;
import com.cirederf.go4lunch.views.WorkmatesListAdapter;
import com.cirederf.go4lunch.views.activities.RestaurantDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkmatesListFragment extends Fragment implements WorkmatesListAdapter.OnItemWorkmatesClick{


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_of_workmates_recyclerView)
    RecyclerView workmatesRecyclerView;

    private UserViewModel userViewModel;

    public static WorkmatesListFragment newInstance() {
        return (new WorkmatesListFragment());
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
                .observe(getViewLifecycleOwner(), users -> WorkmatesListFragment.this.configureRecyclerAdapterForWks(WorkmatesListFragment.this.requireView(), users));
    }

    private void configureRecyclerAdapterForWks(View view, List<User> users) {
        WorkmatesListAdapter workmatesListAdapter = new WorkmatesListAdapter(users, this);
        workmatesRecyclerView.setAdapter(workmatesListAdapter);
        RecyclerView.LayoutManager workmatesView = new LinearLayoutManager(view.getContext());
        workmatesRecyclerView.setLayoutManager(workmatesView);
    }

    @Override
    public void onUserItemClick(User user) {
        if (user.getChosenRestaurant() != null && !user.getChosenRestaurant().equals("No restaurant")) {
            this.startDetailRestaurantActivity(user);
        } else {
            Toast.makeText(getContext(), user.getUsername()+" hasn't chosen yet !", Toast.LENGTH_LONG).show();
        }
    }

    private void startDetailRestaurantActivity(User user) {
        Intent intent = new Intent(getContext(), RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantsListFragment.RESTAURANT_PLACE_ID_PARAM, user.getChosenRestaurant());
        this.startActivity(intent);
    }
}

