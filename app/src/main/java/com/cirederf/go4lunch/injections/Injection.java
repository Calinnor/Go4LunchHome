package com.cirederf.go4lunch.injections;

import com.cirederf.go4lunch.repository.RestaurantDetailsRepository;
import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;

/**
 * Centralize and fetch static method which return Factory
 */
public class Injection {

    public static RestaurantsNearbySearchRepository provideNearbyRestaurantsRepository() {
        return new RestaurantsNearbySearchRepository();
    }

    public static RestaurantDetailsRepository provideRestaurantDetailsRepository() {
        return new RestaurantDetailsRepository();
    }

    /**
     * @return Factories with associated repository as param
     * to use in fragment associated
     */
    public static NearbyRestaurantsViewModelFactory provideNearbySearchFactory() {
        RestaurantsNearbySearchRepository restaurantsNearbySearchRepository = provideNearbyRestaurantsRepository();
        return new NearbyRestaurantsViewModelFactory(restaurantsNearbySearchRepository);
    }

    public static RestaurantDetailsViewModelFactory provideDetailsFactory() {
        RestaurantDetailsRepository restaurantDetailsRepository = provideRestaurantDetailsRepository();
        return new RestaurantDetailsViewModelFactory(restaurantDetailsRepository);
    }
}
