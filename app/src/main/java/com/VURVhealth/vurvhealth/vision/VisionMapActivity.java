package com.VURVhealth.vurvhealth.vision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.location.Location;
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
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionReqPayload;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionProviderIdResPayload;
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
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionMapActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ImageView backBtn;
    private Context context = VisionMapActivity.this;
    private boolean click = false;
    private ArrayList<SearchForVisionResPayload.Datum> duplicateVisionResPayloads2;
    private ClusterManager<SearchForVisionResPayload.Datum> clusterManager;
    private ArrayList<SearchForVisionResPayload.Datum> clusterItemsList = new ArrayList<>();
    private VisionListAdapter mAdapter;
    private SearchForVisionResPayload.Datum eventInfo;
    private ImageView listBtn;
    private LinearLayout llAddress;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private HashMap<Marker, SearchForVisionResPayload.Datum> markerStringHashMap = new HashMap();
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
        ArrayList<SearchForVisionResPayload.Datum> duplicateVisionResPayloads = VisionScreenActivity.resPayloads;
        duplicateVisionResPayloads2 = new ArrayList();
        HashSet lookup = new HashSet();
        for (int index = 0; index < duplicateVisionResPayloads.size(); index++) {
            SearchForVisionResPayload.Datum searchVisionResPayLoad = (SearchForVisionResPayload.Datum) duplicateVisionResPayloads.get(index);
            double lat = searchVisionResPayLoad.getLatitude();
            if (!lookup.contains(lat)) {
                lookup.add(lat);
                duplicateVisionResPayloads2.add(searchVisionResPayLoad);
            }
        }
        llAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventInfo == null || Integer.parseInt(eventInfo.getLocationcount()) <= 0) {
                    getProviderDetails();
                }
            }
        });
        if (duplicateVisionResPayloads2.size() > 10) {
            tvresult.setText("10 " + getResources().getString(R.string.results));
        } else if (duplicateVisionResPayloads2.size() == 1) {
            tvresult.setText(getResources().getString(R.string.one_result));
        } else {
            tvresult.setText(duplicateVisionResPayloads2.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        }
        if (VERSION.SDK_INT >= 23) {
            checkLocationPermission();
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        listBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisionMapActivity.this, VisionListActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(1);
        clusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        try {
            int i;
            LatLng latLng;
            String string;
            if (duplicateVisionResPayloads2.size() > 10) {
                i = 0;
                while (i < 10) {
                    if (duplicateVisionResPayloads2.get(i).getLatitude() != 0) {
                        latLng = new LatLng(duplicateVisionResPayloads2.get(i).getLatitude(), duplicateVisionResPayloads2.get(i).getLongitude());
                        if (Integer.parseInt(((SearchForVisionResPayload.Datum) duplicateVisionResPayloads2.get(i)).getLocationcount()) > 0) {
                            string = getResources().getString(R.string.text_multiple_providers);
                        } else {
                            string = ((SearchForVisionResPayload.Datum) duplicateVisionResPayloads2.get(i)).getFullName();
                        }
                        addCustomMarker(latLng, string, i);
                    }
                    i++;
                }
            } else {
                i = 0;
                while (i < duplicateVisionResPayloads2.size()) {
                    if (duplicateVisionResPayloads2.get(i).getLatitude() != 0) {
                        latLng = new LatLng(duplicateVisionResPayloads2.get(i).getLatitude(), duplicateVisionResPayloads2.get(i).getLongitude());
                        if (Integer.parseInt(((SearchForVisionResPayload.Datum) duplicateVisionResPayloads2.get(i)).getLocationcount()) > 0) {
                            string = getResources().getString(R.string.text_multiple_providers);
                        } else {
                            string = ((SearchForVisionResPayload.Datum) duplicateVisionResPayloads2.get(i)).getFullName();
                        }
                        addCustomMarker(latLng, string, i);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            Log.v("Vision Map", "error>>>" + e.getMessage());
        }
        if (duplicateVisionResPayloads2.get(0).getLatitude() != 0 || duplicateVisionResPayloads2.get(0).getLatitude() == 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(duplicateVisionResPayloads2.get(0).getLatitude(), duplicateVisionResPayloads2.get(0).getLongitude())));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8.0f));
        if (VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
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
    }

    private void addCustomMarker(LatLng latLng, String name, int position) {
        Log.d("TAG", "addCustomMarker()");
        if (mMap != null) {
            //markerStringHashMap.put(mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(name, position)))), duplicateVisionResPayloads2.get(position));
            clusterManager.addItem(duplicateVisionResPayloads2.get(position));
            clusterManager.setRenderer(new MyClusterRender(context, mMap, clusterManager, position));
            clusterManager.cluster();
            clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<SearchForVisionResPayload.Datum>() {
                @Override
                public boolean onClusterClick(Cluster<SearchForVisionResPayload.Datum> cluster) {
                    if (cluster.getItems() != null && cluster.getItems().size() > 0) {
                        dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
                        dialog.setContentView(R.layout.layout_bottomsheet_doctors);
                        TextView tvResults = (TextView) dialog.findViewById(R.id.tv_results);
                        ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
                        RecyclerView rvClusterMapList = (RecyclerView) dialog.findViewById(R.id.rv_clusterMapList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvClusterMapList.setLayoutManager(mLayoutManager);
                        rvClusterMapList.setItemAnimator(new DefaultItemAnimator());
                        clusterItemsList.clear();
                        for (SearchForVisionResPayload.Datum resPayLoad : cluster.getItems()) {
                            clusterItemsList.add(resPayLoad);
                        }
                        if (clusterItemsList != null && clusterItemsList.size() > 0) {
                            tvResults.setText(clusterItemsList.size() + " Results");
                            mAdapter = new VisionListAdapter(context, clusterItemsList);
                            rvClusterMapList.setAdapter(mAdapter);
                        }
                        ivClose.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    //Toast.makeText(context, String.valueOf(cluster.getSize()), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<SearchForVisionResPayload.Datum>() {
                @Override
                public boolean onClusterItemClick(SearchForVisionResPayload.Datum datum) {
                    llAddress.setVisibility(View.VISIBLE);
                    eventInfo = datum;
                    if (eventInfo == null || Integer.parseInt(eventInfo.getLocationcount()) <= 0) {
                        tvSpecialty.setVisibility(View.VISIBLE);
                        tvLanguage.setVisibility(View.VISIBLE);
                        tvGender.setVisibility(View.VISIBLE);
                        tvDoctorName.setText(eventInfo.getFullName());
                        tvSpecialty.setText(eventInfo.getDoctarLanguage());
                        tvLanguage.setVisibility(View.GONE);
                        tvGender.setText(eventInfo.getGender());
                        tvAddress.setText(eventInfo.getAddressLine1() + ", " + eventInfo.getCity() + ", " + eventInfo.getState());
                    } else {
                        tvDoctorName.setText(getResources().getString(R.string.text_multiple_providers));
                        tvAddress.setText(eventInfo.getAddressLine1() + ", " + eventInfo.getCity() + ", " + eventInfo.getState());
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

    private Bitmap getMarkerBitmapFromView(String name, int position) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_facility, null);
        ImageView image = (ImageView) customMarkerView.findViewById(R.id.image);
        image.setBackgroundResource(R.drawable.map_selected_vision_ic);
       /* if (position == 0) {
            image.setBackgroundResource(R.drawable.map_selected_vision_ic);
        } else {
            image.setBackgroundResource(R.drawable.map_vision_ic);
        }*/
        ((TextView) customMarkerView.findViewById(R.id.location_name)).setText(name);
        customMarkerView.measure(0, 0);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(-1, Mode.SRC_IN);
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
        AboutDoctorReqPayLoad searchForVisionReqPayload = new AboutDoctorReqPayLoad();
        searchForVisionReqPayload.setProviderId(eventInfo.getVisProviderId());
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(searchForVisionReqPayload);
        apiService.getVisionProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<VisionProviderIdResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<VisionProviderIdResPayload>> call, Response<ArrayList<VisionProviderIdResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VisionProviderIdResPayload> visionProviderIdResPayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(VisionMapActivity.this, VisionSearchDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) visionProviderIdResPayloads.get(0));
                    b.putInt("position", VisionScreenActivity.resPayloads.indexOf(eventInfo));
                    b.putString("activity", "VisionListActivity");
                    b.putInt("savedItem", ((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(VisionScreenActivity.resPayloads.indexOf(eventInfo))).isSavedStatus());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VisionProviderIdResPayload>> call, Throwable t) {
                Toast.makeText(VisionMapActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VisionMapActivity.this, VisionListActivity.class));
        finish();
    }

    private class MyClusterRender extends DefaultClusterRenderer<SearchForVisionResPayload.Datum> {

        int position;

        public MyClusterRender(Context context, GoogleMap map, ClusterManager<SearchForVisionResPayload.Datum> clusterManager, int position) {
            super(context, map, clusterManager);
            this.position = position;
        }

        @Override
        protected void onBeforeClusterItemRendered(SearchForVisionResPayload.Datum item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(item.getFullName(), position)));

        }

        @Override
        protected void onClusterItemRendered(SearchForVisionResPayload.Datum clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
        }
    }
}
