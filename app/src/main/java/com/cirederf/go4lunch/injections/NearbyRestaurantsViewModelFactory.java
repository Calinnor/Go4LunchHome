package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;
import com.cirederf.go4lunch.viewmodels.NearbyRestaurantsViewModel;

/**
 * gather ViewModels creation
 */
public class NearbyRestaurantsViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
    private final RestaurantsNearbySearchRepository restaurantsDataSource;

    //------------CONSTRUCTOR use in factory------------
    public NearbyRestaurantsViewModelFactory(RestaurantsNearbySearchRepository restaurantsDataSource) {
        this.restaurantsDataSource = restaurantsDataSource;
    }

    /**
     * For create a ViewModel(repository) instance
     * @param modelClass ViewModel = NearbyRestaurantsViewModel
     * using ViewModel(restaurantsDataSource)
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NearbyRestaurantsViewModel.class)) {
            return (T) new NearbyRestaurantsViewModel(restaurantsDataSource);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }
}
