//package com.cirederf.go4lunch.views.activities;
//
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.cirederf.go4lunch.R;
//import com.cirederf.go4lunch.injections.Injection;
//import com.cirederf.go4lunch.injections.RestaurantDetailsViewModelFactory;
//import com.cirederf.go4lunch.models.Restaurant;
//import com.cirederf.go4lunch.viewmodels.RestaurantDetailsViewModel;
//
//import butterknife.BindView;
//
//import static com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment.RESTAURANT_PLACE_ID_PARAM;
//
//public class TryDetails extends BaseActivity {
//
//    //private String placeId;
//    private RestaurantDetailsViewModel restaurantDetailsViewModel;
//
//    @BindView(R.id.type_and_address_try)
//    TextView typeAndAddress;
//    @BindView(R.id.restaurant_detail_name_try)
//    TextView tryname;
//    private String name;
//    @BindView(R.id.restaurant_detail_picture_try)
//    ImageView imageView;
//
//    private Restaurant detailsRestaurant;
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
//        this.configureRestaurantDetailsViewModel();
//    }
//
//    @Override
//    public int getActivityLayout() {
//        return R.layout.activity_try_details;
//    }
//
//    //----------CONFIGURATION-----------
//    private void configureRestaurantDetailsViewModel() {
//        RestaurantDetailsViewModelFactory detailsRestaurantViewModelFactory = Injection.provideDetailsFactory();
//        restaurantDetailsViewModel = ViewModelProviders.of(this, detailsRestaurantViewModelFactory).get(RestaurantDetailsViewModel.class);
//        this.getDetailsRestaurant();
//    }
//
//    //----------FOR DATA---------------
//    private void getDetailsRestaurant() {
//        String apiKey = getString(R.string.places_api_google_key);
//        String placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
//        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
//        //this.restaurantDetailsViewModel.getRestaurantDetailsLiveData(placeId, apiKey)
//        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
//                .observe(this, new Observer<Restaurant>() {
//                    @Override
//                    public void onChanged(Restaurant restaurant) {
//                        refreshRestaurantDetails();
//                    }
//                }
//        );
//    }
////                .observe(this,
////                new Observer<Restaurant>() {
////                    @Override
////                    public void onChanged(Restaurant restaurant) {
////                        refreshRestaurantDetails(restaurant);
////                    }
////                });
////                .observe(this,
////                        this::refreshRestaurantDetails);
////    }
//
//    private void refreshRestaurantDetails() {
//        //name.setText(restaurant.getName());
//        //tryname.setText(restaurant.getName());
//        //typeAndAddress.setText(restaurant.getAddress()+restaurant.getType());
//        //textType.setText(restaurant.getType());
//        //Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
//        Restaurant detailsPictureForRestaurant = detailsRestaurant;
//        Glide.with(imageView.getContext()).load(detailsPictureForRestaurant.getPicture()).into(imageView);
//    }
//
//    private String getName() {
//        String apiKey = getString(R.string.places_api_google_key);
//        String placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
//        //Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData(placeId, apiKey).getValue();
//        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
//        if (restaurant == null) return null;
//        name = restaurant.getName();
//        return name;
//    }
//}