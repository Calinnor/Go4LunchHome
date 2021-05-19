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
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.UserViewModelFactory;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.viewmodels.UserViewModel;
import com.cirederf.go4lunch.views.WorkmatesListAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListWorkmatesFragment extends Fragment {

    private UserViewModel userViewModel;
    //private WorkmatesListAdapter workmatesListAdapter;
    //private RecyclerView recyclerView;
    @BindView(R.id.list_of_workmates_recyclerView) RecyclerView recyclerView;


    public static ListWorkmatesFragment newInstance() {
        return (new ListWorkmatesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_workmates_list_second, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userViewModel == null) {
            //this.configureUserViewModel();
            this.getFirestoreUserList();
        }
    }

    private void configureUserViewModel() {
        UserViewModelFactory userViewModelFactory = Injection.provideUserDetailsFactory();
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        //this.getFirestoreUserList();
    }

    private void getFirestoreUserList() {
        this.configureUserViewModel();

        //set userDataSource.getListOfFirestoreUsers(); if query == null
        this.userViewModel.initListOfFirestoreUser();
        this.configureWorkmatesRecyclerViewAdapter(getView());

        //if query ==null set return from below else use return existing

        //need to learn who to do here with :
        //this.userViewModel.setFirestoreUserList().addSnapshotListener()
        //this.configureRecyclerViewAdapter(requireView(), List<User>fisuser);
    }

//    private void configureRecyclerViewAdapter(View view, List<User> firestoreUsersList) {
//        RecyclerView recyclerView = view.findViewById(R.id.list_of_workmates_recyclerView);
//        WorkmatesListAdapter workmatesListAdapter = new WorkmatesListAdapter(firestoreUsersList);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
//        recyclerView.setAdapter(workmatesListAdapter);
//        RecyclerView.LayoutManager firestoreUsersRecyclerView = new LinearLayoutManager(view.getContext());
//        recyclerView.setLayoutManager(firestoreUsersRecyclerView);
//    }

    private void configureWorkmatesRecyclerViewAdapter(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.list_of_workmates_recyclerView);
        WorkmatesListAdapter workmatesListAdapter = new WorkmatesListAdapter(generateOptionsForAdapter(userViewModel.setFirestoreUserList()));
        //workmatesListAdapter = new WorkmatesListAdapter(generateOptionsForAdapter(userViewModel.setFirestoreUserList()));
        workmatesListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(workmatesListAdapter.getItemCount());
            }
        });
        recyclerView.setAdapter(workmatesListAdapter);
        RecyclerView.LayoutManager workmatesRCV = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(workmatesRCV);

    }

    private FirestoreRecyclerOptions<User> generateOptionsForAdapter(Query setFirestoreUserList) {
        return new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(setFirestoreUserList, User.class)
                .setLifecycleOwner(this)
                .build();
    }

}