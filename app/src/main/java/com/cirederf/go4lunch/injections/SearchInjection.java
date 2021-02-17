package com.cirederf.go4lunch.injections;

import com.cirederf.go4lunch.repository.RestaurantsRepository;

/**
 * centralize
 */
public class SearchInjection {

//    private static SearchRepository provideSearchRepository() {
//        return new SearchRepository();
//    }

    public static RestaurantsRepository provideRestaurantRepository() {
        return new RestaurantsRepository();
    }

    /**
     * @return Factory with repository as param
     */
    public static SearchRestaurantsViewModelFactory provideSearchFactory() {
        //return new SearchViewFactory(provideSearchRepository());
        RestaurantsRepository restaurantsRepository = provideRestaurantRepository();
        return new SearchRestaurantsViewModelFactory(restaurantsRepository);
    }
}
