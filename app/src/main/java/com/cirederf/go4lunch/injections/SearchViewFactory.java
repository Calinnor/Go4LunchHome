package com.cirederf.go4lunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.repository.SearchRepository;
import com.cirederf.go4lunch.viewmodels.SearchViewModel;

public class SearchViewFactory implements ViewModelProvider.Factory {

    private SearchRepository searchRepository;
    private RestaurantsRepository restaurantsRepository;

//    public SearchViewFactory(SearchRepository searchRepository) {
//        this.searchRepository = searchRepository;
//    }

    public SearchViewFactory(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            //return (T) new SearchViewModel(searchRepository);
            return (T) new SearchViewModel(restaurantsRepository);
        }
        throw new IllegalArgumentException("Problem with ViewModelFactory");
    }
}
