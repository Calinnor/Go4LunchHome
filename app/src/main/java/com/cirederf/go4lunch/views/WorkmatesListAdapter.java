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


public class WorkmatesListAdapter extends RecyclerView.Adapter<WorkmatesListAdapter.WorkmatesViewHolder> {

    List<User> workmatesList;
    private final OnItemWorkmatesClick onItemWorkmatesClick;

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.workmates_name_restaurant_type_and_name)
        TextView nameAndTypeAndRestaurantName;
        @BindView(R.id.workmates_picture)
        ImageView workmatesPicture;

        public WorkmatesViewHolder(@NonNull View itemWorkmatesView) {
            super(itemWorkmatesView);
            ButterKnife.bind(this, itemView);
        }
    }

    public WorkmatesListAdapter(List<User> workmatesList, OnItemWorkmatesClick onItemWorkmatesClick) {
        this.workmatesList = workmatesList;
        this.onItemWorkmatesClick = onItemWorkmatesClick;
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list_workmates_view, parent, false);
        return new WorkmatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position) {
        User workmate = workmatesList.get(position);

        if (workmate.getIsChosenRestaurantDisplay() != null && !workmate.getIsChosenRestaurantDisplay()) {
            holder.nameAndTypeAndRestaurantName.setText(String.format("%sis joining.", workmate.getUsername()));
        }
        else if (workmate.getChosenRestaurant() != null && !workmate.getChosenRestaurant().equals("No restaurant") && workmate.getIsChosenRestaurantDisplay()) {
            holder.nameAndTypeAndRestaurantName
                    .setText(String.format("%s is eating (%s) at %s", workmate.getUsername(), workmate.getRestaurantType(), workmate.getRestaurantName()));
        } else {
            holder.nameAndTypeAndRestaurantName.setText(String.format("%s hasn't chosen yet.", workmate.getUsername()));
        }

            Glide.with(holder.workmatesPicture.getContext())
                    .applyDefaultRequestOptions(
                            new RequestOptions()
                                    .error(R.drawable.workmates_default_image)
                    )
                    .load(workmate.getUrlPicture())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.workmatesPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemWorkmatesClick.onUserItemClick(workmate);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workmatesList.size();
        //beware, if item.xml for list is
        //android:layout_height="wrap_content"
        //for the main contenair, the adapter will display ONLY ONE (the firs) item...!!!!!!!
    }

    public interface OnItemWorkmatesClick {
        void onUserItemClick(User user);
    }



}
