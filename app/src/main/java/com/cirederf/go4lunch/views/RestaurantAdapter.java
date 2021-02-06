package com.cirederf.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cirederf.go4lunch.Fragments.ListRestaurantsFragment;
import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.models.apiModels.Result;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    List<Result> nearbyRestaurants;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewAddress;
        TextView textViewOpeningHours;
        TextView textViewDistance;
        TextView textViewRating;
        ImageView imageView;

        public RestaurantViewHolder(@NonNull View itemRestaurantView) {
            super(itemRestaurantView);

            textViewName = itemRestaurantView.findViewById(R.id.item_list_restaurant_name_txt);
            textViewAddress = itemRestaurantView.findViewById(R.id.item_list_restaurant_address_txt);
            textViewOpeningHours = itemRestaurantView.findViewById(R.id.item_list_restaurant_hours_txt);
            textViewDistance = itemRestaurantView.findViewById(R.id.item_list_restaurant_distance_txt);
            textViewRating = itemRestaurantView.findViewById(R.id.item_list_restaurant_number_rating_txt);
        }
    }

    public RestaurantAdapter(ListRestaurantsFragment listRestaurantsFragment, List<Result> nearbyRestaurants) {
        this.nearbyRestaurants =nearbyRestaurants;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurants_view, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Result nearbySearchRestaurants = nearbyRestaurants.get(position);        
        holder.textViewName.setText(nearbySearchRestaurants.getName());
        holder.textViewDistance.setText("seem 100m");
        //holder.textViewOpeningHours.setText(nearbySearchRestaurants.getOpeningHours().getOpenNow().toString()); if null error
        holder.textViewAddress.setText(nearbySearchRestaurants.getVicinity());
        holder.textViewRating.setText(nearbySearchRestaurants.getRating().toString());
    }

    @Override
    public int getItemCount() {
        return nearbyRestaurants.size();
    }
}
