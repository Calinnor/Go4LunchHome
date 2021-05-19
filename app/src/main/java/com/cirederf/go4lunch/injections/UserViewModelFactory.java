package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.UserRepository;
import com.cirederf.go4lunch.viewmodels.UserViewModel;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
    private final UserRepository userDetailsDataSource;

    //------------CONSTRUCTOR use in factory------------
    public UserViewModelFactory(UserRepository userDetailsDataSource) {
        this.userDetailsDataSource = userDetailsDataSource;
    }

    /**
     * For create a ViewModel(repository) instance
     * @param modelClass ViewModel = UserViewModel
     * using ViewModel(userDetailsDataSource)
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userDetailsDataSource);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }



}
