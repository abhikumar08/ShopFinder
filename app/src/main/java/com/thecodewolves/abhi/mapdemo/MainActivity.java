package com.thecodewolves.abhi.mapdemo;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.thecodewolves.abhi.mapdemo.Model.NearByShopsResponse;
import com.thecodewolves.abhi.mapdemo.Model.Shop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    @Inject
    Retrofit retrofit;

    int PLACE_PICKER_REQUEST = 1;
    private final static String API_KEY = "AIzaSyBydLrTT31ku-bHQoam3oAq6U3-_F6zZr4";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    @BindView(R.id.place_picker_button) Button placePickerButton;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private String loc;
    private List<Shop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication)getApplication()).getNetComponent().inject(this);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

    }

    @OnClick(R.id.place_picker_button)
    public void pickPlace(Button button){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try{
            startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            Toast.makeText(this,"Pick a location",Toast.LENGTH_LONG).show();
        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            ApiInterface apiService =
                    retrofit.create(ApiInterface.class);
            List<Shop> nearByShops = new ArrayList<Shop>();

            Call<NearByShopsResponse> call = apiService.getNearByShops(currentLatitude+","+currentLongitude,500,"store",API_KEY);
            call.enqueue(new Callback<NearByShopsResponse>() {

                @Override
                public void onResponse(Call<NearByShopsResponse> call, Response<NearByShopsResponse> response) {

                    shops = response.body().getResults();
                    ShopRecyclerViewAdapter shopAdapter = new ShopRecyclerViewAdapter(getApplicationContext(),shops);
                    recyclerView.setAdapter(shopAdapter);
                }

                @Override
                public void onFailure(Call<NearByShopsResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            //Toast.makeText(MainActivity.this,"No of shops : "+ shops.size(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                currentLatitude = place.getLatLng().latitude;
                currentLongitude = place.getLatLng().longitude;
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }


}
