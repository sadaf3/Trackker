package com.example.root.myapplication1;

/**
 * Created by root on 31/3/17.
 */

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapViewFragment extends Fragment implements OnMapReadyCallback,
        LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String tag = MapViewFragment.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location location;
    private GoogleApiClient googleApiClient;
    private boolean M_REQUEST_LOCATION_UPDATE = false;
    private LocationRequest mLocationRequest;
    double latitude, longitude;
   double rad;
    double lat,longi;

    double distance;


    private GoogleMap mMap;
    SupportMapFragment mSupportMapFragment;
    Usersession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        // needed to get the map to display immediately

        session=new Usersession(getActivity());

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            /*if (checkPlayServices()){*/
            buildGoogleApiClient();
            CreateLocationRequest();
            //
            /*mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);
                        LatLng marker_latlng = new LatLng(-34, 151);
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(-34, 151)).title("Hello Maps");

                        // Changing marker icon
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                        googleMap.addMarker(marker);

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(marker_latlng).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);

                    }

                }
            });*/
        }


        return rootView;
    }

    private void CreateLocationRequest() {
        mLocationRequest = new LocationRequest();
        int Updateinterval = 10000;
        mLocationRequest.setInterval(Updateinterval);
        int fastestinterval = 5000;
        mLocationRequest.setFastestInterval(fastestinterval);
        mLocationRequest.setPriority(mLocationRequest.PRIORITY_HIGH_ACCURACY);
        int displacement = 10;
        mLocationRequest.setSmallestDisplacement(displacement);
    }

    /* private boolean checkPlayServices() {
         int resultcode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getContext());
         if (resultcode!= ConnectionResult.SUCCESS){
             if (GooglePlayServicesUtil.isUserRecoverableError(resultcode)){
                 GooglePlayServicesUtil.getErrorDialog(resultcode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
             }
             *//*else
            {
                Toast.makeText(getApplicationContext(),"This Device is not supported",Toast.LENGTH_SHORT).show();
                finish();
            }*//*
            return false;

        }
        return true;
    }*/
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this.getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    public void onStart() {

        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        //checkPlayServices();
        if (googleApiClient.isConnected() && M_REQUEST_LOCATION_UPDATE) {
            startLocationUpdates();
        }

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();

    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Current Location");
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        lat=Double.parseDouble(session.getlati());
        longi=Double.parseDouble(session.getlongi());
        MarkerOptions marker1 = new MarkerOptions().position(
                new LatLng(lat, longi)).title("Home");
        marker1.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        rad=Double.parseDouble(session.gethr());
        // Add a marker in Sydney and move the camera
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat, longi))
                .radius(rad)
                .strokeColor(Color.RED).strokeWidth(2)
                .fillColor(0x55a1f9f9));
        LatLng sydney = new LatLng(latitude, longitude);

        mMap.addMarker(marker);
        mMap.addMarker(marker1);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(17.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Toast.makeText(getActivity(), "location Changed", Toast.LENGTH_LONG).show();
        displaylocation();


    }


    @Override
    public void onConnected(Bundle bundle) {
        displaylocation();
        if (M_REQUEST_LOCATION_UPDATE) {
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(tag, "");

    }

    private void displaylocation()

    {
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            String getlati = String.valueOf(latitude);
            String getlongi = String.valueOf(longitude);
            Log.v("lat", getlati);
            Log.v("long", getlongi);

            Location locA = new Location("");
            locA.setLatitude(lat);
            locA.setLongitude(longi);

            Location locB = new Location("");
            locB.setLatitude(latitude);
            locB.setLongitude(longitude);

// distance = locationA.distanceTo(locationB);   // in meters
            distance = locA.distanceTo(locB);

           SupportMapFragment mapFragment=(SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapwhere);
            mapFragment.getMapAsync(this);

        } else {
            Toast.makeText(getActivity(), "enable location settings", Toast.LENGTH_SHORT).show();
        }
    }
}









