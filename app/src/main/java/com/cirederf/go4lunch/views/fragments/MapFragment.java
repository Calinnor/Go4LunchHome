package com.cirederf.go4lunch.views.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.views.activities.MainActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment {

    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 12340;
    @BindView(R.id.current_location)
    TextView textCurrentLocation;
    @BindView(R.id.button_get_current_location)
    Button buttonGetLocation;
    @BindView(R.id.textAdress)
    TextView textAddress;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;



    public static MapFragment newInstance() {
        return (new MapFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onButtonLocationClick();
    }

    private void onButtonLocationClick() {
        buttonGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }

    private void getCurrentLocation() {
        //step 8 implement method
        //8.1 progressbar
        progressBar.setVisibility(View.VISIBLE);

        //8.2 set speed to refresh data
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(getContext()
                    , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity()
                        , new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }
                        , REQUEST_CODE_LOCATION_PERMISSIONS
                );
            }
            return;
        }


        //8.3 implement fusedLocation
        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                //unsuscribe
                                LocationServices.getFusedLocationProviderClient(getActivity())
                                        .removeLocationUpdates(this);

                                //if there's data in locationResult
                                if(locationResult != null && locationResult.getLocations().size() > 0) {

                                    int latestLocationIndex = locationResult.getLocations().size() -1;

                                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                                    textCurrentLocation.setText(
                                            String.format(
                                                    "Latitude: %s\nLongitude: %s ",
                                                    latitude, longitude
                                            )
                                    );
                                }
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                        , Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), "Need permissions", Toast.LENGTH_LONG).show();
            }
        }
    }




}