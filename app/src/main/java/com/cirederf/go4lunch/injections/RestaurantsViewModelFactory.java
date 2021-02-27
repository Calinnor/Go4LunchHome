package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;
import com.cirederf.go4lunch.viewmodels.RestaurantsViewModel;

/**
 * gather ViewModels creation
 */
public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
    private final RestaurantsNearbySearchRepository restaurantsDataSource;

    //------------CONSTRUCTOR------------
    public RestaurantsViewModelFactory(RestaurantsNearbySearchRepository restaurantsDataSource) {
        this.restaurantsDataSource = restaurantsDataSource;
    }

    /**
     * For create a ViewModel(repository) instance
     * @param modelClass ViewModel = SearchRestaurantViewModel
     * using ViewModel(restaurantRepository)
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(restaurantsDataSource);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }
}
