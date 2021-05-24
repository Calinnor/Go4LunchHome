package com.cirederf.go4lunch.injections;

import com.cirederf.go4lunch.repository.RestaurantDetailsRepository;
import com.cirederf.go4lunch.repository.RestaurantsNearbySearchRepository;
import com.cirederf.go4lunch.repository.UserRepository;

/**
 * Centralize and fetch static method which return Factory
 */
public class Injection {

    private static RestaurantsNearbySearchRepository provideNearbyRestaurantsRepository() {
        return new RestaurantsNearbySearchRepository();
    }

    private static RestaurantDetailsRepository provideRestaurantDetailsRepository() {
        return new RestaurantDetailsRepository();
    }

    private static UserRepository provideUserRepository() {
        return new UserRepository();
    }

    /**
     * @return Factories with associated repository as param
     * to use in fragment/activities associated
     */
    public static NearbyRestaurantsViewModelFactory provideNearbySearchFactory() {
        RestaurantsNearbySearchRepository restaurantsNearbySearchRepository = provideNearbyRestaurantsRepository();
        return new NearbyRestaurantsViewModelFactory(restaurantsNearbySearchRepository);
    }

    public static RestaurantDetailsViewModelFactory provideRestaurantDetailsFactory() {
        RestaurantDetailsRepository restaurantDetailsRepository = provideRestaurantDetailsRepository();
        return new RestaurantDetailsViewModelFactory(restaurantDetailsRepository);
    }

    public static UserViewModelFactory provideUserDetailsFactory() {
        UserRepository userRepository = provideUserRepository();
        return new UserViewModelFactory(userRepository);
    }
}
