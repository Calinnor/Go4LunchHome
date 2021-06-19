package com.cirederf.go4lunch.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment {

    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 12340;
    private SupportMapFragment supportMapFragment;

    //todo don't understand
    private GoogleMap googleMap;

    public static MapFragment newInstance() {
        return (new MapFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_for_google_map_fragment);
        ButterKnife.bind(this, view);
        return view;
          }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentLocation();
    }

    private void getCurrentLocation() {
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
        getGoogleMap();

        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(getActivity())
                                        .removeLocationUpdates(this);

                                if(locationResult != null && locationResult.getLocations().size() > 0) {

                                    int latestLocationIndex = locationResult.getLocations().size() -1;
                                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                                    //todo don't understand
                                    if(googleMap != null) {
                                        LatLng googleLocation = new LatLng(latitude, longitude);
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
                                    }
                                }
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

    private void getGoogleMap() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(150));
                googleMap.setMyLocationEnabled(true);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(48.5277772, 2.6580556))
                        .title("apm"));
            }
        });
    }

}