package com.cirederf.go4lunch.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;


import butterknife.BindView;
import butterknife.OnClick;

import static com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment.RESTAURANT_PLACE_ID_PARAM;

public class RestaurantDetailsActivity extends BaseActivity {

    @BindView(R.id.restaurant_details_type_and_address)
    TextView typeAndAddress;
    @BindView(R.id.restaurant_details_name)
    TextView name;
    @BindView(R.id.restaurant_detail_picture)
    ImageView imageView;
    @BindView(R.id.restaurant_is_chosen_button)
    FloatingActionButton mChosenRestaurantButton;
    @BindView(R.id.text_view_recyclerview_empty)
    TextView textViewRecyclerViewEmpty;

    private String placeId;
    private String type;
    private String restaurantName;
    private final int applyChosenRestaurantOptionAtStart = 100;
    private final int applyChosenRestaurantOptionAtClickSelection = 200;


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
    }

    //-------------FOR RESTAURANT DETAILS VALUES----------------
    private void getDetailsRestaurant() {
        String apiKey = getString(R.string.places_api_google_key);
        this.restaurantDetailsViewModel.initRestaurantDetails(placeId, apiKey);
        this.restaurantDetailsViewModel.getRestaurantDetailsLiveData()
                .observe(this, new Observer<Restaurant>() {
                    @Override
                    public void onChanged(Restaurant restaurant) {
                        RestaurantDetailsActivity.this.refreshRestaurantDetails(restaurant);
                    }
                });
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
        typeAndAddress.setText(type + ", " + restaurant.getAddress());
        Glide.with(imageView.getContext()).load(restaurant.getPicture()).into(imageView);
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
        userViewModel.returnUserDetailDocument(this.getCurrentUser().getUid())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User currentUser = documentSnapshot.toObject(User.class);
                        String chosenRestaurant = currentUser.getChosenRestaurant();

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
                                userViewModel.updateRestaurantName(getCurrentUser().getUid(), null);
                                mChosenRestaurantButton.setImageResource(0);
                            } else {
                                userViewModel.updateChosenRestaurant(getCurrentUser().getUid(), placeId);
                                userViewModel.updateRestaurantType(getCurrentUser().getUid(), type);
                                userViewModel.updateRestaurantName(getCurrentUser().getUid(), restaurantName);
                                mChosenRestaurantButton.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                            }
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

    @OnClick({R.id.button_phone_call, R.id.button_web_site_launcher, R.id.restaurant_is_chosen_button})
    public void onRestaurantDetailsACTIONClick(View view){
        switch (view.getId()) {

            case R.id.button_phone_call:
                startPhoneCall();
                break;

            case R.id.button_web_site_launcher:
                launchWebView();
                break;

            case R.id.restaurant_is_chosen_button:
                //this.updateUserChosenRestaurant();
                this.initChosenButton(applyChosenRestaurantOptionAtClickSelection);
                break;

            default:
                break;
        }
    }

//    private void updateUserChosenRestaurant() {
//        userViewModel.returnUserDetailDocument(this.getCurrentUser().getUid())
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        User currentUser = documentSnapshot.toObject(User.class);
//                        String chosenRestaurant = currentUser.getChosenRestaurant();
//
//                        if (chosenRestaurant.equals(placeId)) {
//                            userViewModel.updateChosenRestaurant(getCurrentUser().getUid(), "No restaurant as chosen restaurant");
//                            mChosenRestaurantButton.setImageResource(0);
//                        } else {
//                            userViewModel.updateChosenRestaurant(getCurrentUser().getUid(), placeId);
//                            mChosenRestaurantButton.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
//                        }
//                    }
//                });
//    }



}