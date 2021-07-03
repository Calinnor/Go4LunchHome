package com.cirederf.go4lunch.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProviders;

import com.cirederf.go4lunch.R;
import com.cirederf.go4lunch.injections.Injection;
import com.cirederf.go4lunch.injections.MapViewModelFactory;
import com.cirederf.go4lunch.models.Restaurant;
import com.cirederf.go4lunch.models.apiNearbyModels.Location;
import com.cirederf.go4lunch.viewmodels.MapViewModel;
import com.cirederf.go4lunch.views.activities.RestaurantDetailsActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.ButterKnife;


public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 12340;
    public static String currentUserLocation;
    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;
    public static LatLng googleLocation;
    private MapViewModel mapViewModel;
    private int radius;
    private int tilt;
    private String typeToSearch;

    public static MapFragment newInstance() {
        return (new MapFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_for_google_map_fragment);
        configureMapViewModel();
        getCurrentLocation();
        setSharedPrefs();
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission
                (requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission
                        (requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //todo find why i cant reduce this part of code...
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        getFusedLocation(locationRequest);
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
     * @SuppressLint("MissingPermission") getGoogleMap is call just after the permissionsCheck in getCurrentLocation()
     */
    @SuppressLint("MissingPermission")
    private void getGoogleMap() {
        supportMapFragment.getMapAsync(googleMap -> {

            MapFragment.this.googleMap = googleMap;

            if (googleLocation != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
            }
            googleMap.setMinZoomPreference(14.0f);//not need too far view, we search for nearby restaurants, not, world...
            googleMap.setMaxZoomPreference(20.0f);
            //1 far far far /5/10/15/20 near near near levels for strat zoom
            googleMap.setMyLocationEnabled(true);
        });
    }

    private void configureMapViewModel() {
        MapViewModelFactory mapViewModelFactory = Injection.provideMapFactory();
        mapViewModel = ViewModelProviders.of(this, mapViewModelFactory).get(MapViewModel.class);
    }

    private void setRestaurantsMarkers(String location) {
        //String type = "restaurant";
        String apiKey = getString(R.string.places_api_google_key);
        //this.mapViewModel.initRestaurantsList(location, radius, type, apiKey);
        this.mapViewModel.initRestaurantsList(location, radius, typeToSearch, apiKey);
        this.mapViewModel
                .getListRestaurantsLiveData()
                .observe(getViewLifecycleOwner(),
                        MapFragment.this::configureRestaurantsMarkers);
    }

    private void configureRestaurantsMarkers(List<Restaurant> restaurants) {
        for (Restaurant restaurant: restaurants) {
            mapViewModel
                    .getWorkmatesNumber(restaurant.getPlaceId())
                    .observe(getViewLifecycleOwner(), numberWorkmates -> configureMarker(restaurant, numberWorkmates));
        }
    }

    private void configureMarker(Restaurant restaurant, Integer numberWorkmates) {
        float iconColor = numberWorkmates > 0 ? BitmapDescriptorFactory.HUE_CYAN : BitmapDescriptorFactory.HUE_GREEN;

        Location location = restaurant.getGeometry().getLocation();
        LatLng latLng = new LatLng(location.getLat(), location.getLng());

        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(iconColor))
                .title(restaurant.getRestaurantName());

        googleMap.addMarker(markerOptions).setTag(restaurant.getPlaceId());
        googleMap.setOnMarkerClickListener(MapFragment.this);
    }

    private void setMapOption(LocationResult locationResult) {
        if (googleMap != null) {
            if (locationResult != null && locationResult.getLocations().size() > 0) {

                int latestLocationIndex = locationResult.getLocations().size() - 1;

                android.location.Location location = locationResult.getLocations().get(latestLocationIndex);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //double latitudeForTest = -33.867487;
                //double longitudeForTest = 151.206990;

                googleLocation = new LatLng(latitude, longitude);
                //googleLocation = new LatLng(latitudeForTest, longitudeForTest);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
                currentUserLocation = googleLocation.latitude+","+googleLocation.longitude;

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        //.target(new LatLng(latitudeForTest, longitudeForTest))
                        .tilt(tilt)
                        .build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 1500, null);

                setRestaurantsMarkers(currentUserLocation);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getFusedLocation(LocationRequest locationRequest) {
        LocationServices
                .getFusedLocationProviderClient(requireActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                LocationServices.getFusedLocationProviderClient(requireActivity())
                                        .removeLocationUpdates(this);

                                setMapOption(locationResult);
                            }
                        }
                        , Looper.getMainLooper());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        this.startDetailsActivity(marker);
        return false;
    }

    private void startDetailsActivity(Marker marker) {
        String restaurantPlaceId = (String) marker.getTag();
        Intent intent = new Intent(getContext(), RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantsListFragment.RESTAURANT_PLACE_ID_PARAM, restaurantPlaceId);
        this.startActivity(intent);

    }

    private void setSharedPrefs() {
        SharedPreferences appPrefs = requireContext().getSharedPreferences(SettingsFragment.APP_PREFS, Context.MODE_PRIVATE );
        radius = appPrefs.getInt(SettingsFragment.RADIUS_PREFS, 700);
        tilt = appPrefs.getInt(SettingsFragment.TILT_PREFS, 20);
        typeToSearch = appPrefs.getString(SettingsFragment.TYPE_PREFS, "restaurant");
    }
}