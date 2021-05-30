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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//public class WorkmatesListAdapter extends FirestoreRecyclerAdapter<User, WorkmatesListAdapter.WorkmatesViewHolder> {
//
//    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.workmates_name_restaurant_type_and_name)
//        TextView nameAndTypeAndRestaurantName;
//        @BindView(R.id.workmates_picture)
//        ImageView workmatesPicture;
//
//        public void updateWorkmatesListUi(User workmates) {
//
//            if (workmates.getUrlPicture() != null) {
//            Glide.with(workmatesPicture.getContext())
//                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.default_restaurant_picture))
//                    .load(workmates.getUrlPicture())
//                    .apply(RequestOptions.centerCropTransform())
//                    .into(workmatesPicture);
//            }
//
//            String username = workmates.getUsername();
//            String restaurantType = workmates.getRestaurantType();
//            String chosenRestaurant;
//
//            if(workmates.getChosenRestaurant() == null
//                    || workmates.getChosenRestaurant().equals("No restaurant as chosen restaurant")){
//         chosenRestaurant = "le restaurant";
//            } else {
//                chosenRestaurant = workmates.getChosenRestaurant();
//            }
//
//         if(restaurantType == null) {
//             restaurantType = "type";
//         }
//
//         nameAndTypeAndRestaurantName.setText(username +" is eating "+restaurantType+ " at ("+chosenRestaurant+")");
//
//        }
//
//        public WorkmatesViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    public interface Listener {
//        void onDataChanged();
//    }
//
//    //FOR COMMUNICATION
//    private Listener callback;
//    /**
//     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
//     * FirestoreRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
//    public WorkmatesListAdapter(@NonNull FirestoreRecyclerOptions<User> options, Listener callback) {
//        super(options);
//        this.callback = callback;
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull WorkmatesListAdapter.WorkmatesViewHolder holder, int position, @NonNull User model) {
//        holder.updateWorkmatesListUi(model);
//    }
//
//    @NonNull
//    @Override
//    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new WorkmatesViewHolder(LayoutInflater.from(parent.getContext())
//        .inflate(R.layout.item_recyclerview_list_workmates_view, parent, false));
//    }
//
//    @Override
//    public void onDataChanged() {
//        super.onDataChanged();
//        this.callback.onDataChanged();
//    }

public class WorkmatesListAdapter extends RecyclerView.Adapter<WorkmatesListAdapter.WorkmatesViewHolder> {

    List<User> workmatesList;

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.workmates_name_restaurant_type_and_name)
        TextView nameAndTypeAndRestaurantName;
        @BindView(R.id.workmates_picture)
        ImageView workmatesPicture;

        public WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public WorkmatesListAdapter(List<User> workmatesList) {
        this.workmatesList = workmatesList;
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list_workmates_view, parent, false);
        return new WorkmatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesListAdapter.WorkmatesViewHolder holder, int position) {
        User workmate = workmatesList.get(position);
        if (!workmate.getChosenRestaurant().equals("No restaurant")) {
            holder.nameAndTypeAndRestaurantName
                    .setText(workmate.getUsername() + " is eating " + workmate.getRestaurantType() + " at (" + workmate.getRestaurantName() + ")");
        } else {
            holder.nameAndTypeAndRestaurantName.setText(workmate.getUsername()+" hasn't chosen yet.");
        }

            Glide.with(holder.workmatesPicture.getContext())
                    .applyDefaultRequestOptions(
                            new RequestOptions()
                                    .error(R.drawable.workmates_default_image)
                    )
                    .load(workmate.getUrlPicture())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.workmatesPicture);
    }

    @Override
    public int getItemCount() {
        return workmatesList.size();
    }


}
