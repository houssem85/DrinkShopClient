package com.programasoft.drinkshop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.error;
import com.programasoft.drinkshop.model.store;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByStore extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private IDrinkShopApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_store);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        api=Common.getApi();
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    locationRequest = new LocationRequest();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(5000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setSmallestDisplacement(10f);

                    //Location Collback
                    locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            // Add a marker in my Location and move the camera
                            mMap.clear();
                            LatLng YourLocation = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                            mMap.addMarker(new MarkerOptions().position(YourLocation).title("Your Location"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(YourLocation,14.0f));
                            api.GetNearbyStore(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude()).enqueue(new Callback<List<store>>() {
                                @Override
                                public void onResponse(Call<List<store>> call, Response<List<store>> response) {
                                  if(response.isSuccessful())
                                  {
                                      for(store s:response.body())
                                     { LatLng YourLocation = new LatLng(s.getLatitude(), s.getLongitude());
                                       mMap.addMarker(new MarkerOptions().position(YourLocation).title(s.getName()).snippet("Distance :"+s.getDistance_in_km()).icon(BitmapDescriptorFactory.fromResource(R.drawable.store)));
                                     }

                                  }else
                                  {    try {
                                        Gson gson = new GsonBuilder().setLenient().create();
                                        error error = gson.fromJson(response.errorBody().string(), error.class);
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                         } catch (IOException e) {
                                         e.printStackTrace();
                                          }

                                  }

                                }

                                @Override
                                public void onFailure(Call<List<store>> call, Throwable t) {
                                  Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    };

                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NearByStore.this);
                    if (ActivityCompat.checkSelfPermission(NearByStore.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearByStore.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                  }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                Toast.makeText(getApplicationContext(),"Permision denied",Toast.LENGTH_LONG).show();
            }
        }).check();
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
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(locationCallback!=null)
        {fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(locationCallback!=null) {
            if (ActivityCompat.checkSelfPermission(NearByStore.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearByStore.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }
}
