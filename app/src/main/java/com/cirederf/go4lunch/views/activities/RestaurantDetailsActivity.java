package com.cirederf.go4lunch.views.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.cirederf.go4lunch.views.WorkmatesListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cirederf.go4lunch.views.fragments.RestaurantsListFragment.RESTAURANT_PLACE_ID_PARAM;

public class RestaurantDetailsActivity extends BaseActivity implements WorkmatesListAdapter.OnItemWorkmatesClick{


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.restaurant_details_type_and_address)
    TextView typeAndAddress;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.restaurant_details_name)
    TextView name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.restaurant_detail_picture)
    ImageView imageView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.restaurant_is_chosen_button)
    FloatingActionButton mChosenRestaurantButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.workmates_recyclerView)
    RecyclerView workmatesRecyclerView;

    private String placeId;
    private String type;
    private String restaurantName;
    private final int applyChosenRestaurantOptionAtStart = 100;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_details_restaurant;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
        this.initChosenButton(applyChosenRestaurantOptionAtStart);
        this.getDetailsRestaurant();
        this.getWorkmatesList();
    }

    //-------------FOR RESTAURANT DETAILS VALUES----------------
    private void getDetailsRestaurant() {
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
                .observe(this, RestaurantDetailsActivity.this::refreshRestaurantDetails);
    }
//    explication de la methodologie de observe

//        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
//                .observe(
//                getViewLifecycleOwner() /*pour un fragment/"this" pour une activité. il s'agit du proprietaire de la vue*/
//                , new Observer<Restaurant>() /*ici la data observée*/{
//        @Override
//        public void onChanged(Restaurant restaurant) {
//            refreshRestaurantDetails(restaurant);
//        }
//    });
//}

    private void refreshRestaurantDetails(Restaurant restaurant) {
        type = restaurant.getType();
        restaurantName = restaurant.getRestaurantName();
        name.setText(restaurantName);
        typeAndAddress.setText(String.format("%s, %s", type, restaurant.getAddress()));
        Glide.with(imageView
                .getContext())
                .load(restaurant.getPicture())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

    //------------UTILS METHODS-------------
    //-----PHONE-----
    private String getRestaurantPhoneNumber() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return "no restaurant to display";
        return restaurant.getPhoneNumber();
    }

    private void startPhoneCall() {
        if(getRestaurantPhoneNumber() == null) {
            toastShowActionNullResult(getApplicationContext(), "No phone number");
        } else {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getRestaurantPhoneNumber())));
        }
    }

    //--------WEBSITE-----------
    private void launchWebView() {
        if(getRestaurantWebSiteUri() == null) {
            toastShowActionNullResult(getApplicationContext(), "No website");
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getRestaurantWebSiteUri())));
        }
    }

    private String getRestaurantWebSiteUri() {
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurantDetailsLiveData().getValue();
        if (restaurant == null) return null;
        return restaurant.getWebsite();
    }

    //-------------CHOSEN RESTAURANT------------------
    private void initChosenButton(int applyChosenRestaurantOption) {
        userViewModel.returnUserDetailDocument(Objects.requireNonNull(this.getCurrentUser()).getUid())
                .addOnSuccessListener(documentSnapshot -> {
                    User currentUser = documentSnapshot.toObject(User.class);
                    assert currentUser != null;
                    String chosenRestaurant = currentUser.getChosenRestaurant();

                    assert chosenRestaurant != null;
                    if (applyChosenRestaurantOption == applyChosenRestaurantOptionAtStart) {
                        if (chosenRestaurant.equals(placeId)) {
                            mChosenRestaurantButton.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                        } else {
                            mChosenRestaurantButton.setImageResource(0);
                        }
                    } else {
                        if (chosenRestaurant.equals(placeId)) {
                            userViewModel.updateChosenRestaurant(getCurrentUser().getUid(), "No restaurant");
                            userViewModel.updateRestaurantType(getCurrentUser().getUid(), null);
                            userViewModel.updateNameRestaurant(getCurrentUser().getUid(), null);
                            mChosenRestaurantButton.setImageResource(0);
                        } else {
                            userViewModel.updateChosenRestaurant(getCurrentUser().getUid(), placeId);
                            userViewModel.updateRestaurantType(getCurrentUser().getUid(), type);
                            userViewModel.updateNameRestaurant(getCurrentUser().getUid(), restaurantName);
                            mChosenRestaurantButton.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                        }
                    }
                });
    }


    //-----------UI UTIL--------------
    private void toastShowActionNullResult(Context context, String response){
        Toast toast = Toast.makeText(context, response, Toast.LENGTH_LONG );
        toast.show();
    }

    //----------ACTIONS---------

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.button_phone_call, R.id.button_web_site_launcher, R.id.restaurant_is_chosen_button})
    public void onRestaurantDetailsACTIONClick(View view){
        int applyChosenRestaurantOptionAtClickSelection = 200;
        switch (view.getId()) {

            case R.id.button_phone_call:
                startPhoneCall();
                break;
            case R.id.button_web_site_launcher:
                launchWebView();
                break;
            case R.id.restaurant_is_chosen_button:
                this.initChosenButton(applyChosenRestaurantOptionAtClickSelection);
                break;
            default:
                break;
        }
    }

    private void getWorkmatesList() {
        this.userViewModel.initLivedataUserListWithChosenRestaurant(placeId);
        this.userViewModel.getLivedataUsersListWithRestaurant()
                .observe(this, RestaurantDetailsActivity.this::configureRecyclerAdapterForWorkmates);
    }

    private void configureRecyclerAdapterForWorkmates(List<User> users) {
        WorkmatesListAdapter workmatesListAdapter = new WorkmatesListAdapter(users, this);
        workmatesRecyclerView.setAdapter(workmatesListAdapter);
        RecyclerView.LayoutManager workmatesView = new LinearLayoutManager(this);
        workmatesRecyclerView.setLayoutManager(workmatesView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    @Override
    public void onUserItemClick(User user) {
    }
}