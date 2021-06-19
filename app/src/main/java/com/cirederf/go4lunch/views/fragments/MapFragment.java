package com.cirederf.go4lunch.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cirederf.go4lunch.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;


public class MapFragment extends Fragment {

    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 12340;
    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;
    private LatLng googleLocation;
    public static String currentUserLocation;

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

    public void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(requireContext()
                    , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        requireActivity()
                        , new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }
                        , REQUEST_CODE_LOCATION_PERMISSIONS
                );
            }
            return;
        }

        getGoogleMap();

        LocationServices.getFusedLocationProviderClient(requireActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(requireActivity())
                                        .removeLocationUpdates(this);

                                if(locationResult != null && locationResult.getLocations().size() > 0) {

                                    int latestLocationIndex = locationResult.getLocations().size() -1;
                                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

//                                    double latitude =  -33.8667 ;
//                                    double longitude = 151.206990 ;
                                    currentUserLocation = latitude + ","+longitude;

                                    if(googleMap != null) {
                                        googleLocation = new LatLng(latitude, longitude);
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));

                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(new LatLng(latitude, longitude))
                                                .tilt(20)
                                                .build();
                                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18), 1500, null);

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

    /**
     * @SuppressLint("MissingPermission")
     * getGoogleMap is call just after the permissionsCheck in getCurrentLocation()
     */
    @SuppressLint("MissingPermission")
    private void getGoogleMap() {
        supportMapFragment.getMapAsync(googleMap -> {

            MapFragment.this.googleMap = googleMap;

            if (googleLocation != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
            }

            //1/5/10/15/20 levels for strat zoom
            googleMap.setMyLocationEnabled(true);
        });
    }


}