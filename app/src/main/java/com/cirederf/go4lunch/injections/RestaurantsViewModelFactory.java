package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.viewmodels.RestaurantsViewModel;

import java.util.concurrent.Executor;

public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    private final RestaurantsRepository restaurantsDataSource;
    private final Executor executor;

    public RestaurantsViewModelFactory(RestaurantsRepository restaurantsDataSource, Executor executor) {
        this.restaurantsDataSource = restaurantsDataSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(restaurantsDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
