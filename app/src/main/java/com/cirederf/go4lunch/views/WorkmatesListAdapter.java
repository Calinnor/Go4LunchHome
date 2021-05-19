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

import java.util.List;

import butterknife.ButterKnife;

public class WorkmatesListAdapter extends FirestoreRecyclerAdapter<User, WorkmatesListAdapter.WorkmatesViewHolder> {

    User user;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WorkmatesListAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {
        TextView nameAndTypeAndRestaurantName;
        ImageView workmatesPicture;

        public WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    protected void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position, @NonNull User model) {

        if (user.getUrlPicture() != null) {
            Glide.with(holder.workmatesPicture.getContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .error(R.drawable.default_restaurant_picture))
                    .load(user.getUrlPicture())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.workmatesPicture);
        }

         String username = user.getUsername();
         String chosenRestaurant = user.getChosenRestaurant();
         String restaurantType = user.getRestaurantType();
         holder.nameAndTypeAndRestaurantName.setText(username +" is eating "+restaurantType+ "at "+chosenRestaurant);
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list_workmates_view, parent, false);
        return new WorkmatesViewHolder(view);
    }

}
//public class WorkmatesListAdapter extends RecyclerView.Adapter<WorkmatesListAdapter.WorkmatesViewHolder> {

    //List<User> workmates;
//    User user;
//
//    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {
//
//        TextView nameAndTypeAndRestaurantName;
//        ImageView workmatesPicture;
//
//        public WorkmatesViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            //nameAndTypeAndRestaurantName = itemView.findViewById(R.id.workmates_name_restaurant_type_and_name);
//           // workmatesPicture = itemView.findViewById(R.id.workmates_picture);
//        }
//    }

//    public WorkmatesListAdapter(List<User> workmates) {
//        this.workmates = workmates;
//    }
//    public WorkmatesListAdapter(User user) {
//        this.user = user;
//    }
//
//    @NonNull
//    @Override
//    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list_workmates_view, parent, false);
//        return new WorkmatesViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull WorkmatesListAdapter.WorkmatesViewHolder holder, int position) {
//        User workmatesList = workmates.get(position);
//        //holder.nameAndTypeAndRestaurantName.setText(workmatesList.getUsername() + workmatesList.getChosenRestaurant());
//        holder.nameAndTypeAndRestaurantName.setText("test1" + "lidile");
//        Glide.with(holder.workmatesPicture.getContext())
//                .applyDefaultRequestOptions(new RequestOptions()
//                        .error(R.drawable.default_restaurant_picture)
//                )
//                .load(workmatesList.getUrlPicture())
//                .apply(RequestOptions.centerCropTransform())
//                .into(holder.workmatesPicture);


//        if (user.getUrlPicture() != null) {
//            Glide.with(holder.workmatesPicture.getContext())
//                    .applyDefaultRequestOptions(new RequestOptions()
//                            .error(R.drawable.default_restaurant_picture))
//                    .load(user.getUrlPicture())
//                    .apply(RequestOptions.centerCropTransform())
//                    .into(holder.workmatesPicture);
//        }
//
//         String username = user.getUsername();
//         String chosenRestaurant = user.getChosenRestaurant();
//         String restaurantType = user.getRestaurantType();
//         holder.nameAndTypeAndRestaurantName.setText(username +" is eating "+restaurantType+ "at "+chosenRestaurant);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
