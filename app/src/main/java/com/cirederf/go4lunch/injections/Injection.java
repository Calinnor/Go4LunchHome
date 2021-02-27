package com.cirederf.go4lunch.injections;

import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;

/**
 * Centralize and fetch static method which return Factory
 */
public class Injection {

    public static RestaurantsNearbySearchRepository provideRestaurantRepository() {
        return new RestaurantsNearbySearchRepository();
    }

    /**
     * @return Factory with repository as param
     */
    public static RestaurantsViewModelFactory provideSearchFactory() {
        RestaurantsNearbySearchRepository restaurantsNearbySearchRepository = provideRestaurantRepository();
        return new RestaurantsViewModelFactory(restaurantsNearbySearchRepository);
    }
}
