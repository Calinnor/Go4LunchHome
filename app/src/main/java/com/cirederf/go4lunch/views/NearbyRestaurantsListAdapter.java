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

import java.util.List;

public class NearbyRestaurantsListAdapter extends RecyclerView.Adapter<NearbyRestaurantsListAdapter.RestaurantViewHolder> {

    List<Restaurant> nearbyRestaurants;
    private final OnItemRestaurantClickListerner onItemRestaurantClickListerner;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAddress;
        TextView textViewOpeningHours;
        TextView textViewDistance;
        ImageView imageView;
        TextView textViewRating;
        TextView textWorkmatesNumber;

        public RestaurantViewHolder(@NonNull View itemRestaurantView) {
            super(itemRestaurantView);
            textViewName = itemRestaurantView.findViewById(R.id.item_list_restaurant_name_txt);
            textViewAddress = itemRestaurantView.findViewById(R.id.item_list_restaurant_address_txt);
            textViewOpeningHours = itemRestaurantView.findViewById(R.id.item_list_restaurant_hours_txt);
            textViewDistance = itemRestaurantView.findViewById(R.id.item_list_restaurant_distance_txt);
            imageView = itemRestaurantView.findViewById(R.id.item_list_restaurant_illustration_image);
            //textWorkmatesNumber = itemRestaurantView.findViewById(R.id.item_list_restaurant_number_workmates_txt);
            textViewRating = itemRestaurantView.findViewById(R.id.item_list_restaurant_number_workmates_txt);
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
        holder.textViewDistance.setText("seem 100m");
        holder.textViewAddress.setText(nearbySearchRestaurants.getAddress());
        holder.textViewRating.setText(String.valueOf(nearbySearchRestaurants.getRating()));

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

        holder.itemView.setOnClickListener(v -> onItemRestaurantClickListerner.onItemClick(nearbySearchRestaurants));
    }

    @Override
    public int getItemCount() {
        return nearbyRestaurants.size();
    }

    public interface OnItemRestaurantClickListerner {
        void onItemClick(Restaurant restaurant);
    }

}
