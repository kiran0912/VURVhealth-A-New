package com.VURVhealth.vurvhealth.medical.doctors;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.ImageReader;
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

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.MyClusterItems;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
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
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 24/1/17.
 */

public class DoctorMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ImageView backBtn;
    private boolean click = false;
    private ArrayList<ArrayList<SearchPractitionerResPayLoad>> duplicatesSortedArray2;
    private ArrayList<SearchPractitionerResPayLoad> eventInfo = new ArrayList<>();
    private ImageView listBtn;
    private LinearLayout llAddress;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private HashMap<Marker, ArrayList<SearchPractitionerResPayLoad>> markerStringHashMap = new HashMap();
    private TextView markerTextview;
    private ProgressDialog pDialog;
    private TextView tvAddress;
    private TextView tvDoctorName;
    private TextView tvGender;
    private TextView tvLanguage;
    private TextView tvSpecialty;
    private TextView tvresult;
    private ClusterManager<SearchPractitionerResPayLoad> clusterManager;
    ArrayList<SearchPractitionerResPayLoad> duplicatesSortedArray = new ArrayList<>();
    private ArrayList<SearchPractitionerResPayLoad> clusterItemsList = new ArrayList<>();
    private Context context = DoctorMapActivity.this;
    private BottomSheetDialog dialog;
    private RecyclerView.Adapter<DoctorResultAdapter.DataObjectHolder> mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_activity_map);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        listBtn = (ImageView) findViewById(R.id.listBtn);
        tvDoctorName = (TextView) findViewById(R.id.tvDoctorName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvSpecialty = (TextView) findViewById(R.id.tvSpecialty);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvresult = (TextView) findViewById(R.id.tvresult);
        llAddress = (LinearLayout) findViewById(R.id.llAddress);
        duplicatesSortedArray = MedicalScreenActivity.resPayloads;
        duplicatesSortedArray2 = new ArrayList();
        final HashSet lookup = new HashSet();
        ArrayList<Double> latlist = new ArrayList<>();
        for (int index = 0; index < duplicatesSortedArray.size(); index++) {
            SearchPractitionerResPayLoad searchPractitionerResPayLoad = duplicatesSortedArray.get(index);
            Double lat = searchPractitionerResPayLoad.getLat();
            if (!lookup.contains(lat)) {
                lookup.add(lat);
                latlist.add(lat);
                ArrayList<SearchPractitionerResPayLoad> duplicateList = new ArrayList<>();
                duplicateList.add(searchPractitionerResPayLoad);
                duplicatesSortedArray2.add(duplicateList);
            } else {
                int index1 = latlist.indexOf(lat);
                ArrayList<SearchPractitionerResPayLoad> duplicateList = duplicatesSortedArray2.get(index1);
                duplicateList.add(searchPractitionerResPayLoad);
                duplicatesSortedArray2.set(index1, duplicateList);
            }
        }
        if (duplicatesSortedArray2.size() > 10) {
            tvresult.setText("10 " + getResources().getString(R.string.results));
        } else if (duplicatesSortedArray2.size() == 1) {
            tvresult.setText(getResources().getString(R.string.one_result));
        } else {
            tvresult.setText(duplicatesSortedArray2.size() + " " + getResources().getString(R.string.results));
        }
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventInfo!=null && eventInfo.size()<=1) {
                    Intent intent = new Intent(DoctorMapActivity.this, DoctorsResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("position", MedicalScreenActivity.resPayloads.indexOf(eventInfo.get(0)));
                    b.putString("activity", "DoctorSearchResult");
                    b.putInt("savedItem", (MedicalScreenActivity.resPayloads.get(MedicalScreenActivity.resPayloads.indexOf(eventInfo.get(0)))).isSavedStatus());
                    b.putString("locationKey", (MedicalScreenActivity.resPayloads.get(MedicalScreenActivity.resPayloads.indexOf(eventInfo.get(0)))).getLocationKey());
                    b.putString("providerId", (MedicalScreenActivity.resPayloads.get(MedicalScreenActivity.resPayloads.indexOf(eventInfo.get(0)))).getProviderID());
                    intent.putExtras(b);
                    startActivity(intent);
                } else if (eventInfo!=null && eventInfo.size()>1){
                    dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
                    dialog.setContentView(R.layout.layout_bottomsheet_doctors);
                    TextView tvResults = (TextView) dialog.findViewById(R.id.tv_results);
                    ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
                    RecyclerView rvClusterMapList = (RecyclerView) dialog.findViewById(R.id.rv_clusterMapList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvClusterMapList.setLayoutManager(mLayoutManager);
                    rvClusterMapList.setItemAnimator(new DefaultItemAnimator());
                    tvResults.setText(eventInfo.size() + " Results");
                    mAdapter = new DoctorResultAdapter(context, eventInfo);
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
        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission();
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorMapActivity.this, DoctorSearchResults.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(1);
        clusterManager = new ClusterManager<SearchPractitionerResPayLoad>(this, mMap);

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        int i;
        LatLng latLng;
        String name;
        if (duplicatesSortedArray2.size() > 10) {
            i = 0;
            while (i < 10) {
                if (duplicatesSortedArray2.get(i).get(0).getLat() != null || duplicatesSortedArray2.get(i).get(0).getLat() != 0) {
                    latLng = new LatLng(duplicatesSortedArray2.get(i).get(0).getLat(), duplicatesSortedArray2.get(i).get(0).getLng());
                    if (duplicatesSortedArray2.get(i).size()>1){
                        name = getResources().getString(R.string.text_multiple_doctors);
                    }else {
                        name = duplicatesSortedArray2.get(i).get(0).getName();
                    }

                    addCustomMarker(latLng, name, i);
                }
                i++;
            }
        } else {
            i = 0;
            while (i < duplicatesSortedArray2.size()) {
                if (duplicatesSortedArray2.get(i).get(0).getLat() != null || duplicatesSortedArray2.get(i).get(0).getLat() != 0) {
                    latLng = new LatLng(duplicatesSortedArray2.get(i).get(0).getLat(), duplicatesSortedArray2.get(i).get(0).getLng());
                    if (duplicatesSortedArray2.get(i).size()>1){
                        name = getResources().getString(R.string.text_multiple_doctors);
                    }else {
                        name = duplicatesSortedArray2.get(i).get(0).getName();
                    }
                    addCustomMarker(latLng, name, i);
                }
                i++;
            }
        }
        if (duplicatesSortedArray2.get(0).get(0).getLat() != null || (duplicatesSortedArray2.get(0).get(0)).getLat() != 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(duplicatesSortedArray2.get(0).get(0).getLat(), duplicatesSortedArray2.get(0).get(0).getLng())));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
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
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onLocationChanged(Location location) {
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
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

    private void getDoctorProviderDetails() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(eventInfo.get(0).getProviderID());
        Application_holder.providerId = eventInfo.get(0).getProviderID();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getAboutDoctorsService(reqPayLoads).enqueue(new Callback<ArrayList<AboutDoctorResPayLoad>>() {
            @Override
            public void onResponse(Call<ArrayList<AboutDoctorResPayLoad>> call, Response<ArrayList<AboutDoctorResPayLoad>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AboutDoctorResPayLoad> aboutDoctorResPayLoads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(DoctorMapActivity.this, DoctorsResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) aboutDoctorResPayLoads.get(0));
                    b.putInt("position", MedicalScreenActivity.resPayloads.indexOf(eventInfo));
                    b.putString("activity", "DoctorSearchResult");
                    b.putInt("savedItem", (MedicalScreenActivity.resPayloads.get(MedicalScreenActivity.resPayloads.indexOf(eventInfo))).isSavedStatus());
                    b.putString("locationKey", (MedicalScreenActivity.resPayloads.get(MedicalScreenActivity.resPayloads.indexOf(eventInfo))).getLocationKey());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AboutDoctorResPayLoad>> call, Throwable t) {
                Toast.makeText(DoctorMapActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    private void addCustomMarker(LatLng latLng, String doctorName, final int position) {
        Log.d("TAG", "addCustomMarker()");
        if (mMap != null) {
            markerStringHashMap.put(mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(doctorName, position)))), duplicatesSortedArray2.get(position));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    llAddress.setVisibility(View.VISIBLE);
                    eventInfo = markerStringHashMap.get(marker);
                    if (eventInfo!=null && eventInfo.size()<=1) {
                        tvSpecialty.setVisibility(View.VISIBLE);
                        tvLanguage.setVisibility(View.VISIBLE);
                        tvGender.setVisibility(View.VISIBLE);
                        tvDoctorName.setText(eventInfo.get(0).getName());
                        tvAddress.setText(eventInfo.get(0).getAddress());
                        tvSpecialty.setText(eventInfo.get(0).getSpeciality());
                        tvLanguage.setText(eventInfo.get(0).getLanguage());
                        tvGender.setText(eventInfo.get(0).getGender().equalsIgnoreCase("M") ? getResources().getString(R.string.male) : getResources().getString(R.string.female));
                    } else {
                        tvDoctorName.setText(getResources().getString(R.string.text_multiple_doctors));
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

    private Bitmap getMarkerBitmapFromView(String doctorName, int position) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_doctor, null);
        markerTextview = (TextView) customMarkerView.findViewById(R.id.location_name);
        markerTextview.setText(doctorName);
        ImageView image = (ImageView) customMarkerView.findViewById(R.id.image);
        /*if (duplicatesSortedArray2!=null && duplicatesSortedArray2.get(0).get(0).getName().equalsIgnoreCase(duplicatesSortedArray2.get(position).get(0).getName())) {
            image.setBackgroundResource(R.drawable.map_selected_doctors_ic);
        }else {
            image.setBackgroundResource(R.drawable.map_doctors_ic);
        }*/
        image.setBackgroundResource(R.drawable.map_doctors_ic);
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

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(context, DoctorSearchResults.class));
    }

   /* dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
                        dialog.setContentView(R.layout.layout_bottomsheet_doctors);
    TextView tvResults = (TextView)dialog.findViewById(R.id.tv_results);
    ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
    RecyclerView rvClusterMapList = (RecyclerView)dialog.findViewById(R.id.rv_clusterMapList);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvClusterMapList.setLayoutManager(mLayoutManager);
                        rvClusterMapList.setItemAnimator(new DefaultItemAnimator());
                        clusterItemsList.clear();
                        for (SearchPractitionerResPayLoad resPayLoad : cluster.getItems()){
        clusterItemsList.add(resPayLoad);
    }
                        if (clusterItemsList!=null&&clusterItemsList.size()>0){
        tvResults.setText(clusterItemsList.size()+" Results");
        mAdapter = new DoctorResultAdapter(context, clusterItemsList);
        rvClusterMapList.setAdapter(mAdapter);
    }
                        ivClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });
                        dialog.show();*/
}

