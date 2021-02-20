package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.repository.SearchRepository;
import com.cirederf.go4lunch.viewmodels.SearchRestaurantsViewModel;

import java.util.concurrent.Executor;

/**
 * gather ViewModels creation
 */
public class SearchRestaurantsViewModelFactory implements ViewModelProvider.Factory {

    //-----------REPOSITORY------------
//    private SearchRepository searchRepository;
    private RestaurantsRepository restaurantsRepository;

//    public SearchViewFactory(SearchRepository searchRepository) {
//        this.searchRepository = searchRepository;
//    }

    //------------CONSTRUCTOR------------
    public SearchRestaurantsViewModelFactory(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    /**
     * For create a ViewModel(repository) instance
     * @param modelClass ViewModel = SearchRestaurantViewModel
     * using ViewModel(restaurantRepository)
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchRestaurantsViewModel.class)) {
            //return (T) new SearchViewModel(searchRepository);
            return (T) new SearchRestaurantsViewModel(restaurantsRepository);
        }
        throw new IllegalArgumentException("Problem with unknown ViewModel");
    }
}
