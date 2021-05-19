package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantDetailsRepository;
import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;

public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
    private final RestaurantDetailsRepository restaurantDetailsDataSource;

    //------------CONSTRUCTOR use in factory------------
    public RestaurantDetailsViewModelFactory(RestaurantDetailsRepository restaurantDetailsDataSource) {
        this.restaurantDetailsDataSource = restaurantDetailsDataSource;
    }

    /**
     * For create a ViewModel(repository) instance
     * @param modelClass ViewModel = RestaurantDetailsViewModel
     * using ViewModel(restaurantDetailsDataSource)
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(restaurantDetailsDataSource);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }
}
