package com.cirederf.go4lunch.Controllers.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cirederf.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogoutFragment extends Fragment {

    @BindView(R.id.nav_header_user_picture)
    ImageView imageViewProfile;
    @BindView(R.id.nav_header_user_email_txt)
    TextView textViewEmail;
    @BindView(R.id.nav_header_username_txt)
    TextView textUsername;

    public static LogoutFragment newInstance() {
        return (new LogoutFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }






}