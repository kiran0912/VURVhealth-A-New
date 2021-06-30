package com.VURVhealth.vurvhealth.medical.facilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDeatilResPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDetailReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.vision.VisionMapActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityMapActivity extends FragmentActivity implements OnMapReadyCallback,
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener {

    private HashMap<Marker, SearchFacilitiesResPayLoad> markerStringHashMap = new HashMap<>();
    private ClusterManager<SearchFacilitiesResPayLoad> clusterManager;
    private ArrayList<SearchFacilitiesResPayLoad> clusterItemsList = new ArrayList<>();
    private SearchFacilitiesResPayLoad eventInfo;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;

    private boolean click = false;
    private TextView tvDoctorName, tvAddress, tvSpecialty, tvLanguage, tvGender, tvresult;
    private ImageView backBtn, listBtn;
    private LinearLayout llAddress;
    private ProgressDialog pDialog;
    private BottomSheetDialog dialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_activity_map);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        listBtn = (ImageView) findViewById(R.id.listBtn);
        tvDoctorName = (TextView) findViewById(R.id.tvDoctorName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvSpecialty = (TextView) findViewById(R.id.tvSpecialty);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvresult = (TextView) findViewById(R.id.tvresult);
        tvGender = (TextView) findViewById(R.id.tvGender);
        llAddress = (LinearLayout) findViewById(R.id.llAddress);
        llAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getFacilityDetails();
            }
        });
        if (MedicalScreenActivity.resPayloadsForFacilities.size() > 10) {
            tvresult.setText(10 + " Results");
        } else
            tvresult.setText(MedicalScreenActivity.resPayloadsForFacilities.size() + " Results");

        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FacilityMapActivity.this, FacilitySearchResult.class);
                startActivity(i);
                finish();
            }
        });

        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(FacilityMapActivity.this,MedicalScreenActivity.class);
//                startActivity(i);
                finish();
            }
        });

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        if (MedicalScreenActivity.resPayloadsForFacilities.size() > 10) {
            for (int i = 0; i < 10; i++) {
                if (MedicalScreenActivity.resPayloadsForFacilities.get(i).getLat() != 0)
                    addCustomMarker(new LatLng(MedicalScreenActivity.resPayloadsForFacilities.get(i).getLat(),
                                    MedicalScreenActivity.resPayloadsForFacilities.get(i).getLng()),
                            MedicalScreenActivity.resPayloadsForFacilities.get(i).getFacilityName(), i);
            }
        } else {
            for (int i = 0; i < MedicalScreenActivity.resPayloadsForFacilities.size(); i++) {
                if (MedicalScreenActivity.resPayloadsForFacilities.get(i).getLat() != 0)
                    addCustomMarker(new LatLng(MedicalScreenActivity.resPayloadsForFacilities.get(i).getLat(),
                                    MedicalScreenActivity.resPayloadsForFacilities.get(i).getLng()),
                            MedicalScreenActivity.resPayloadsForFacilities.get(i).getFacilityName(), i);
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(MedicalScreenActivity.resPayloadsForFacilities.get(0).getLat(),
                MedicalScreenActivity.resPayloadsForFacilities.get(0).getLng())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

        //Initialize Google Play Services
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        /*latLng = new LatLng(location.getLatitude(), location.getLongitude());

         *//* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);*//*


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(false);*/
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void addCustomMarker(LatLng latLng, String name, int position) {
        Log.d("TAG", "addCustomMarker()");
        if (mMap == null) {
            return;
        }
        // adding a marker on map with image from  drawable
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(name, position))));
        markerStringHashMap.put(marker, MedicalScreenActivity.resPayloadsForFacilities.get(position));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {

                llAddress.setVisibility(View.VISIBLE);
                eventInfo = markerStringHashMap.get(arg0);

                tvDoctorName.setText(eventInfo.getFacilityName());
                tvSpecialty.setText(eventInfo.getFacilityType());
                tvLanguage.setText("");
                tvGender.setText("");
                tvAddress.setText(eventInfo.getAddress());
                return true;
            }

        });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    //set progress dialog
    protected void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (pDialog != null)
            pDialog.dismiss();
    }

    private void getFacilityDetails() {
        showProgressDialog(FacilityMapActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(FacilityMapActivity.this).create(ApiInterface.class);
        FacilityDetailReqPayload facilityDetailReqPayload = new FacilityDetailReqPayload();
        facilityDetailReqPayload.setFacilityProviderId(eventInfo.getProviderID());
        Application_holder.providerId = eventInfo.getProviderID();
        final ArrayList<FacilityDetailReqPayload> reqPayLoads = new ArrayList<>();
        reqPayLoads.add(facilityDetailReqPayload);
        Call<ArrayList<FacilityDeatilResPayload>> call = apiService.getFacilityDetailsService(reqPayLoads);
        call.enqueue(new Callback<ArrayList<FacilityDeatilResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<FacilityDeatilResPayload>> call, Response<ArrayList<FacilityDeatilResPayload>> response) {

                if (response.isSuccessful()) {
                    ArrayList<FacilityDeatilResPayload> facilityDeatilResPayloads = response.body();

//                    resPayloads = response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(FacilityMapActivity.this, FacilityResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    intent.putExtra("facilityName", eventInfo.getFacilityName());
                    b.putParcelable("SearchResultObject", facilityDeatilResPayloads.get(0));
                    b.putInt("position", MedicalScreenActivity.resPayloadsForFacilities.indexOf(eventInfo));
                    b.putString("activity", "FacilitySearchResults");
                    b.putInt("savedItem", MedicalScreenActivity.resPayloadsForFacilities.get(MedicalScreenActivity.resPayloadsForFacilities.indexOf(eventInfo)).isSavedStatus());
                    intent.putExtras(b);
                    startActivity(intent);

                    Log.d("", "Number of movies received: ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FacilityDeatilResPayload>> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(FacilityMapActivity.this, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();

                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }


    //set custom marker to mapview
    private Bitmap getMarkerBitmapFromView(String name, int position) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_facility, null);
        ImageView image = (ImageView) customMarkerView.findViewById(R.id.image);
        TextView markerTextview = (TextView) customMarkerView.findViewById(R.id.location_name);
        markerTextview.setText(name);
        /*if(MedicalScreenActivity.resPayloadsForFacilities!=null&&MedicalScreenActivity.resPayloadsForFacilities.get(0).getFacilityName().equalsIgnoreCase(MedicalScreenActivity.resPayloadsForFacilities.get(position).getFacilityName())) {
            image.setBackgroundResource(R.drawable.map_selected_facilities_ic);
        } else {
            image.setBackgroundResource(R.drawable.map_facilities_ic);
        }*/
        image.setBackgroundResource(R.drawable.map_facilities_ic);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
