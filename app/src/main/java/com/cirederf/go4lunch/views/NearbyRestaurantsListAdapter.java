package com.cirederf.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyRestaurantsListAdapter extends RecyclerView.Adapter<NearbyRestaurantsListAdapter.RestaurantViewHolder> {

    private static final String COLLECTION_NAME = "users";
    List<Restaurant> nearbyRestaurants;
    private final OnItemRestaurantClickListerner onItemRestaurantClickListerner;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_restaurant_name_txt)
        TextView textViewName;
        @BindView(R.id.item_list_restaurant_address_txt)
        TextView textViewAddress;
        @BindView(R.id.item_list_restaurant_hours_txt)
        TextView textViewOpeningHours;
        @BindView(R.id.item_list_restaurant_distance_txt)
        TextView textViewDistance;
        @BindView(R.id.item_list_restaurant_illustration_image)
        ImageView imageView;
        @BindView(R.id.item_list_restaurant_number_workmates_txt)
        TextView numberWorkmates;

        public RestaurantViewHolder(@NonNull View itemRestaurantView) {
            super(itemRestaurantView);
            ButterKnife.bind(this, itemView);
        }
    }

    public NearbyRestaurantsListAdapter(List<Restaurant> restaurants, OnItemRestaurantClickListerner onItemRestaurantClickListerner) {
        this.nearbyRestaurants = restaurants;
        this.onItemRestaurantClickListerner = onItemRestaurantClickListerner;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list_restaurants_view, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant nearbySearchRestaurants = nearbyRestaurants.get(position);
        holder.textViewName.setText(nearbySearchRestaurants.getRestaurantName());
        holder.textViewDistance.setText(R.string.default_distance_display);
        holder.textViewAddress.setText(nearbySearchRestaurants.getAddress());

        if(nearbySearchRestaurants.getOpenNow()) {
            holder.textViewOpeningHours.setText(R.string.open);
        } else {
            holder.textViewOpeningHours.setText(R.string.closed);
        }

        Glide.with(holder.imageView.getContext())
                .applyDefaultRequestOptions(
                        new RequestOptions()
                                .error(R.drawable.default_restaurant_picture)
                )
                .load(nearbySearchRestaurants.getPicture())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
        holder.numberWorkmates.setText(String.valueOf(nearbySearchRestaurants.getWorkmatesNumber()));

        Query query = getCollection().whereEqualTo("chosenRestaurant", nearbySearchRestaurants.getPlaceId());

        query.addSnapshotListener((snapshots, e) -> {
//            int numberWorkmates;
//            if (e != null) {
//                return;
//            }
//            if (snapshots != null && !snapshots.isEmpty()) {
            if (snapshots != null && e == null) {
                int numberWorkmates = snapshots.size();
                holder.numberWorkmates.setText
                        (String.valueOf(numberWorkmates));
            } else {
                holder.numberWorkmates.setText("0");
            }
        });

        holder.itemView.setOnClickListener(v -> onItemRestaurantClickListerner.onItemClick(nearbySearchRestaurants));
    }

    private CollectionReference getCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    @Override
    public int getItemCount() {
        return nearbyRestaurants.size();
    }

    public interface OnItemRestaurantClickListerner {
        void onItemClick(Restaurant restaurant);
    }

}
