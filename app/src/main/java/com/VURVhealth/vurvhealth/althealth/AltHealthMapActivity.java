package com.VURVhealth.vurvhealth.althealth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.dental.DentalListAdapter;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSProviderDetailsRespayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSearchResPayload;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 23/5/17.
 */

public class AltHealthMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ArrayList<ArrayList<AHSSearchResPayload.Datum>> ahsSortedArray2;
    private ClusterManager<AHSSearchResPayload.Datum> clusterManager;
    private ImageView backBtn;
    private boolean click = false;
    private ArrayList<AHSSearchResPayload.Datum> eventInfo = new ArrayList<>();
    private ImageView listBtn;
    private LinearLayout llAddress;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private HashMap<Marker, ArrayList<AHSSearchResPayload.Datum>> markerStringHashMap = new HashMap();
    private ProgressDialog pDialog;
    private TextView tvAddress;
    private TextView tvDoctorName;
    private TextView tvGender;
    private TextView tvLanguage;
    private TextView tvSpecialty;
    private TextView tvresult;
    private BottomSheetDialog dialog;

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
        ArrayList<AHSSearchResPayload.Datum> ahsSortedArray = AltHealthScreenActivity.resPayloads;
        ahsSortedArray2 = new ArrayList();
        ahsSortedArray2.clear();
        HashSet lookup = new HashSet();
        ArrayList<Double> latlist = new ArrayList<>();
        for (int index = 0; index < ahsSortedArray.size(); index++) {
            AHSSearchResPayload.Datum searchAHSResPayLoad = (AHSSearchResPayload.Datum) ahsSortedArray.get(index);
            double lat = searchAHSResPayLoad.getLatitude();
            if (!lookup.contains(lat)) {
                lookup.add(lat);
                latlist.add(lat);
                ArrayList<AHSSearchResPayload.Datum> duplicateList = new ArrayList<>();
                duplicateList.add(searchAHSResPayLoad);
                ahsSortedArray2.add(duplicateList);
            } else {
                int index1 = latlist.indexOf(lat);
                ArrayList<AHSSearchResPayload.Datum> duplicateList = ahsSortedArray2.get(index1);
                duplicateList.add(searchAHSResPayLoad);
                ahsSortedArray2.set(index1, duplicateList);
            }
        }
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventInfo != null && eventInfo.size() <= 1) {
                    getProviderDetails();
                } else if (eventInfo != null && eventInfo.size() > 1) {
                    dialog = new BottomSheetDialog(AltHealthMapActivity.this, R.style.AppBottomSheetDialogTheme);
                    dialog.setContentView(R.layout.layout_bottomsheet_doctors);
                    TextView tvResults = (TextView) dialog.findViewById(R.id.tv_results);
                    ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
                    RecyclerView rvClusterMapList = (RecyclerView) dialog.findViewById(R.id.rv_clusterMapList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvClusterMapList.setLayoutManager(mLayoutManager);
                    rvClusterMapList.setItemAnimator(new DefaultItemAnimator());
                    tvResults.setText(eventInfo.size() + " Results");
                    AltHealthListAdapter mAdapter = new AltHealthListAdapter(AltHealthMapActivity.this, eventInfo);
                    rvClusterMapList.setAdapter(mAdapter);
                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
        if (ahsSortedArray2.size() > 10) {
            tvresult.setText("10 " + getResources().getString(R.string.results));
        } else if (ahsSortedArray2.size() == 1) {
            tvresult.setText(getResources().getString(R.string.one_result));
        } else {
            tvresult.setText(ahsSortedArray2.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission();
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AltHealthMapActivity.this, AltHealthListActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(1);
        clusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        int i;
        LatLng latLng;
        String string, degree;
        if (ahsSortedArray2.size() > 10) {
            i = 0;
            while (i < 10) {
                if (ahsSortedArray2.get(i).get(0).getLatitude() != 0 || ahsSortedArray2.get(i).get(0).getLatitude()!=null) {
                    latLng = new LatLng((ahsSortedArray2.get(i).get(0).getLatitude()), (ahsSortedArray2.get(i).get(0)).getLongitude());
                    degree = ahsSortedArray2.get(i).get(0).getDegree();
                    if (ahsSortedArray2.get(i).size() > 1) {
                        string = getResources().getString(R.string.text_multiple_providers);
                    } else {
                        string = ((AHSSearchResPayload.Datum) ahsSortedArray2.get(i).get(0)).getName();
                    }
                    addCustomMarker(latLng, string, i, degree);
                }
                i++;
            }
        } else {
            i = 0;
            while (i < ahsSortedArray2.size()) {
                if (ahsSortedArray2.get(i).get(0).getLatitude() != 0 || (ahsSortedArray2.get(i).get(0)).getLatitude() !=null) {
                    latLng = new LatLng((ahsSortedArray2.get(i).get(0).getLatitude()), (ahsSortedArray2.get(i).get(0)).getLongitude());
                    degree = ahsSortedArray2.get(i).get(0).getDegree();
                    if (ahsSortedArray2.get(i).size() > 1) {
                        string = getResources().getString(R.string.text_multiple_providers);
                    } else {
                        string = ((AHSSearchResPayload.Datum) ahsSortedArray2.get(i).get(0)).getName();
                    }
                    addCustomMarker(latLng, string, i, degree);
                }
                i++;
            }
        }
        try {
            if (ahsSortedArray2.get(0).get(0).getLatitude() != 0 || (ahsSortedArray2.get(0).get(0)).getLatitude() !=null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(ahsSortedArray2.get(0).get(0).getLatitude(), ahsSortedArray2.get(0).get(0).getLongitude())));
            }
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
        } catch (NumberFormatException e) {
        }
        if (Build.VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
        }
    }

    public void onConnectionSuspended(int i) {
    }

    private void addCustomMarker(LatLng latLng, String name, int position, String degree) {
        Log.d("TAG", "addCustomMarker()");
        if (mMap != null) {
            markerStringHashMap.put(mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(name, position, degree)))), ahsSortedArray2.get(position));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    llAddress.setVisibility(View.VISIBLE);
                    eventInfo = markerStringHashMap.get(marker);
                    if (eventInfo != null && eventInfo.size() <= 1) {
                        tvSpecialty.setVisibility(View.VISIBLE);
                        tvLanguage.setVisibility(View.VISIBLE);
                        tvDoctorName.setText(eventInfo.get(0).getName());
                        tvSpecialty.setText(eventInfo.get(0).getClinicName());
                        tvLanguage.setText(eventInfo.get(0).getSpecialty());
                        tvGender.setVisibility(View.GONE);
                        tvAddress.setText(eventInfo.get(0).getAddress());
                    } else {
                        tvDoctorName.setText(getResources().getString(R.string.text_multiple_providers));
                        tvAddress.setText(eventInfo.get(0).getAddress());
                        tvSpecialty.setVisibility(View.INVISIBLE);
                        tvLanguage.setVisibility(View.INVISIBLE);
                        tvGender.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
            });
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
            return false;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 99:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    return;
                } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }
                    mMap.setMyLocationEnabled(true);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private Bitmap getMarkerBitmapFromView(String name, int position, String degree) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_facility, null);
        ImageView image = (ImageView) customMarkerView.findViewById(R.id.image);
       /* if (ahsSortedArray2 != null && ahsSortedArray2.get(0).get(0).getName().equalsIgnoreCase(ahsSortedArray2.get(position).get(0).getName())) {
            //image.setBackgroundResource(R.drawable.map_selected_alt);
            ((TextView) customMarkerView.findViewById(R.id.location_name)).setText(name + ", " + degree);
        } else {
            image.setBackgroundResource(R.drawable.map_alt);
            ((TextView) customMarkerView.findViewById(R.id.location_name)).setText(name);
        }*/
        image.setBackgroundResource(R.drawable.map_alt);
        ((TextView) customMarkerView.findViewById(R.id.location_name)).setText(name + ", " + degree);
        customMarkerView.measure(0, 0);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(-1, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    protected void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(getResources().getString(R.string.please_wait));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    private void getProviderDetails() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(eventInfo.get(0).getAHSProviderId());
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getAHSProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<AHSProviderDetailsRespayload>>() {
            @Override
            public void onResponse(Call<ArrayList<AHSProviderDetailsRespayload>> call, Response<ArrayList<AHSProviderDetailsRespayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AHSProviderDetailsRespayload> ahsProviderDetailsRespayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(AltHealthMapActivity.this, AltHealthSearchDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) ahsProviderDetailsRespayloads.get(0));
                    b.putInt("position", AltHealthScreenActivity.resPayloads.indexOf(eventInfo.get(0)));
                    b.putString("activity", "AHSListActivity");
                    b.putInt("savedItem", eventInfo.get(0).isSavedStatus());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AHSProviderDetailsRespayload>> call, Throwable t) {
                Toast.makeText(AltHealthMapActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AltHealthMapActivity.this, AltHealthListActivity.class));
    }
}
