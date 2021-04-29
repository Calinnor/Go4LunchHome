package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantDetailsRepository;
import com.cirederf.go4lunch.viewmodels.DetailsRestaurantViewModel;

public class DetailsRestaurantViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
    private final RestaurantDetailsRepository restaurantDetailsDataSource;

    //------------CONSTRUCTOR------------
    public DetailsRestaurantViewModelFactory(RestaurantDetailsRepository restaurantDetailsDataSource) {
        this.restaurantDetailsDataSource = restaurantDetailsDataSource;
    }

    /**
     * For create a ViewModel(repository) instance
     * @param modelClass ViewModel = RestaurantDetailsViewModel
     * using ViewModel(restaurantDetailsRepository)
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RestaurantDetailsRepository.class)) {
            return (T) new DetailsRestaurantViewModel(restaurantDetailsDataSource);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }
}
