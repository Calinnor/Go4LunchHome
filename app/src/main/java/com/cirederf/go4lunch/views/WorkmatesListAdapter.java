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
import com.cirederf.go4lunch.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkmatesListAdapter extends FirestoreRecyclerAdapter<User, WorkmatesListAdapter.WorkmatesViewHolder> {

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.workmates_name_restaurant_type_and_name)
        TextView nameAndTypeAndRestaurantName;
        @BindView(R.id.workmates_picture)
        ImageView workmatesPicture;

        public void updateWorkmatesListUi(User workmates) {

            if (workmates.getUrlPicture() != null) {
            Glide.with(workmatesPicture.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.default_restaurant_picture))
                    .load(workmates.getUrlPicture())
                    .apply(RequestOptions.centerCropTransform())
                    .into(workmatesPicture);
            }

         String username = workmates.getUsername();
         String chosenRestaurant = workmates.getChosenRestaurant();
         String restaurantType = workmates.getRestaurantType();
         if(chosenRestaurant.equals("No restaurant as chosen restaurant")) {
             chosenRestaurant = "le restaurant";
         }
         if(restaurantType == null) {
             restaurantType = "type";
         }

         nameAndTypeAndRestaurantName.setText(username +" is eating "+restaurantType+ " at ("+chosenRestaurant+")");

        }

        public WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Listener {
        void onDataChanged();
    }

    //FOR COMMUNICATION
    private Listener callback;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WorkmatesListAdapter(@NonNull FirestoreRecyclerOptions<User> options, Listener callback) {
        super(options);
        this.callback = callback;
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkmatesListAdapter.WorkmatesViewHolder holder, int position, @NonNull User model) {
        holder.updateWorkmatesListUi(model);
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkmatesViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_recyclerview_list_workmates_view, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
