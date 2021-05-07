//package com.cirederf.go4lunch.views.activities;
//
//import com.cirederf.go4lunch.R;
//import com.cirederf.go4lunch.views.fragments.DetailsRestaurantFragment;
//
//import static com.cirederf.go4lunch.views.fragments.ListRestaurantsFragment.RESTAURANT_PLACE_ID_PARAM;
//
//public class DetailsRestaurantActivity extends BaseActivity {
//
//    private String placeId;
//    private String apiKey;
//    private DetailsRestaurantFragment detailsFragment;
//
////    @BindView(R.id.type_and_address)
////    TextView name;
////
////    @BindView(R.id.restaurant_detail_name)
////    TextView address;
////
////    @BindView(R.id.restaurant_detail_picture)
////    ImageView imageView;
////
////    @BindView(R.id.type)
////    TextView textType;
////
////    @BindView(R.id.pluscode)
////    TextView textPlusCode;
////
////    @BindView(R.id.pricelevel)
////    TextView textPrice;
////
////    @BindView(R.id.reference)
////    TextView textReference;
////
////    @BindView(R.id.scope)
////    TextView textScope;
////
////    private Restaurant restaurant;
////
////    @Override
////    public int getActivityLayout() {
////        return R.layout.activity_details_restaurant;
////    }
////
////    @Override
////    protected void onResume() {
////        super.onResume();
////        restaurant = getIntent().getExtras().
////                getParcelable(INTENT_RESTAURANT_PARAM);
////        Log.d("Detail", restaurant.toString());
////        setRestaurantDetails();
////    }
////
////    private void setRestaurantDetails(){
////        setAddress();
////        setName();
////        setPicture();
////        //setPlusCode();
////        //setPrice();
////        setReference();
////        setScope();
////        setTypes();
////    }
////
////    private void setName() {
////        name.setText(restaurant.getName());
////    }
////
////    private void setAddress() {
////        address.setText(restaurant.getAddress());
////    }
////
////    private void setPicture(){
////        Glide.with(this)
////                .load(restaurant.getPicture())
////                .into(imageView);
////    }
////
////    private void setTypes(){
////        textType.setText(restaurant.getType());
////    }
//////    private void setPlusCode(){
//////        textPlusCode.setText(restaurant.getPlusCode().toString());
//////    }
//////    private void setPrice(){
//////        textPrice.setText(restaurant.getPriceLevel());
//////    }
////    private void setReference(){
////        textPlusCode.setText(restaurant.getReference());
////    }
////    private void setScope(){
////        textPrice.setText(restaurant.getScope());
////    }
//
//    @Override
//    public int getActivityLayout() {
//        return R.layout.activity_details_restaurant;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID_PARAM);
//        displayDetailsRestaurantFragment();
//    }
//
//    private DetailsRestaurantFragment displayDetailsRestaurantFragment()
//    {
//        if (this.detailsFragment == null)
//        {
//            this.detailsFragment = DetailsRestaurantFragment.newInstance(placeId);
//        }
//        return this.detailsFragment;
//    }
//
//}