package com.cirederf.go4lunch.injections;

import com.cirederf.go4lunch.repository.RestaurantsRepository;
import com.cirederf.go4lunch.repository.SearchRepository;

public class SearchInjection {

    private static SearchRepository provideSearchRepository() {
        return new SearchRepository();
    }

    private static RestaurantsRepository provideRestaurantRepository() {
        return new RestaurantsRepository();
    }

    public static SearchViewFactory provideSearchFactory() {
        //return new SearchViewFactory(provideSearchRepository());
        return new SearchViewFactory(provideRestaurantRepository());
    }
}
