package com.cirederf.go4lunch.injections;

import com.cirederf.go4lunch.repository.RestaurantsRepository;

/**
 * Centralize and fetch static method which return Factory
 */
public class SearchInjection {

    public static RestaurantsRepository provideRestaurantRepository() {
        return new RestaurantsRepository();
    }

    /**
     * @return Factory with repository as param
     */
    public static SearchRestaurantsViewModelFactory provideSearchFactory() {
        RestaurantsRepository restaurantsRepository = provideRestaurantRepository();
        return new SearchRestaurantsViewModelFactory(restaurantsRepository);
    }
}
