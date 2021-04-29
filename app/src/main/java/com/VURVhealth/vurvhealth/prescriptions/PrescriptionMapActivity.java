package com.VURVhealth.vurvhealth.prescriptions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
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
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PrescriptionMapActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    static final /* synthetic */ boolean $assertionsDisabled = (!PrescriptionMapActivity.class.desiredAssertionStatus());
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ImageView backBtn;
    private boolean click = false;
    private DrugSearchResultResPayLoad1.Result.PharmacyPricing eventInfo;
    private LatLng latLng = new LatLng(17.42320971d, 78.4342289d);
    private LatLng latLng1 = new LatLng(17.39126876d, 78.46006393d);
    private LatLng latLng2 = new LatLng(17.31393222d, 78.59241486d);
    private LatLng latLng3 = new LatLng(17.27328471d, 78.44272614d);
    private LatLng latLng4 = new LatLng(17.54584367d, 78.48529816d);
    private ImageView listBtn;
    private LinearLayout llAddress;
    private TextView location_name;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private HashMap<Marker, DrugSearchResultResPayLoad1.Result.PharmacyPricing> markerStringHashMap = new HashMap();
    private TextView markerTextview;
    private List<DrugSearchResultResPayLoad1.Result.PharmacyPricing> resultList;
    private ClusterManager<DrugSearchResultResPayLoad1.Result.PharmacyPricing> clusterManager;
    private ArrayList<DrugSearchResultResPayLoad1.Result.PharmacyPricing> clusterItemsList = new ArrayList<>();
    private TextView tvAddress;
    private TextView tvPharmacy;
    private TextView tvPrice;
    private TextView tvSaving;
    private boolean switchGeneric;
    private BottomSheetDialog dialog;
    private float minPrice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_activity_map);
        if (getIntent() != null) {
            switchGeneric = getIntent().getBooleanExtra("switchGeneric", true);
        }
        resultList = new ArrayList<>();
        if (PrescriptionSearchActivity.brandsearchResultResPayLoads1 != null && PrescriptionSearchActivity.brandsearchResultResPayLoads1.size() > 0) {
            resultList.clear();
            resultList = PrescriptionSearchActivity.brandsearchResultResPayLoads1;
            if (PrescriptionSearchActivity.genericsearchResultResPayLoads1 != null && PrescriptionSearchActivity.genericsearchResultResPayLoads1.size() >0 && switchGeneric) {
                resultList.addAll(PrescriptionSearchActivity.genericsearchResultResPayLoads1);
            }
        } else {
            if (PrescriptionSearchActivity.genericsearchResultResPayLoads1 != null && PrescriptionSearchActivity.genericsearchResultResPayLoads1.size() >0 && switchGeneric) {
                resultList.clear();
                resultList = PrescriptionSearchActivity.genericsearchResultResPayLoads1;
            }
        }



        backBtn = (ImageView) findViewById(R.id.backBtn);
        TextView tvresult = (TextView) findViewById(R.id.tvresult);
        if (resultList != null && resultList.size()>10) {
            tvresult.setText("10 " + getResources().getString(R.string.results));
        } else {
            tvresult.setText(resultList.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        }
        listBtn = (ImageView) findViewById(R.id.listBtn);
        llAddress = (LinearLayout) findViewById(R.id.llAddress);
        tvPharmacy = (TextView) findViewById(R.id.tvPharmacy);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvSaving = (TextView) findViewById(R.id.tvSaving);
        llAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionMapActivity.this, PrescriptionResultsDetailsActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("SearchResultObject", eventInfo);
                b.putInt("position", resultList.indexOf(eventInfo));
                b.putString("activity", "BestPricesNearbyActivity");
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
        if (VERSION.SDK_INT >= 23) {
            checkLocationPermission();
        }
        if (checkInternet()) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(PrescriptionMapActivity.this);
        } else {
            Toast.makeText(PrescriptionMapActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        listBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionMapActivity.this, BestPricesNearbyActivity.class);
                intent.putExtra("switchGeneric", switchGeneric);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionMapActivity.this, BestPricesNearbyActivity.class);
                intent.putExtra("switchGeneric", switchGeneric);
                startActivity(intent);
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
        if (resultList != null) {
            if (resultList.size()>10){
                i = 0;
                while (i < 10) {
                    if (resultList.get(i).getPharmacy().getAddress().getLatitude() != null) {
                        addCustomMarker(new LatLng(resultList.get(i).getPharmacy().getAddress().getLatitude(), resultList.get(i).getPharmacy().getAddress().getLongitude()), (resultList.get(i)).getPrices().get(0).getPrice(), (resultList.get(i)).getPharmacy().getName().split("\\#")[0], i);
                    }
                    i++;
                }
            }else {
                i = 0;
                while (i < resultList.size()) {
                    if (resultList.get(i).getPharmacy().getAddress().getLatitude() != null) {
                        addCustomMarker(new LatLng(resultList.get(i).getPharmacy().getAddress().getLatitude(), resultList.get(i).getPharmacy().getAddress().getLongitude()), (resultList.get(i)).getPrices().get(0).getPrice(), (resultList.get(i)).getPharmacy().getName().split("\\#")[0], i);
                    }
                    i++;
                }
            }

        }
        if ($assertionsDisabled || resultList != null) {
            if (!(resultList == null || (resultList.get(0)).getPharmacy().getAddress().getLatitude() == null)) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(resultList.get(0).getPharmacy().getAddress().getLatitude(), resultList.get(0).getPharmacy().getAddress().getLongitude())));
            }
            mMap.animateCamera(CameraUpdateFactory.zoomTo(8.0f));
            if (VERSION.SDK_INT < 23) {
                buildGoogleApiClient();
                return;
            } else if (ContextCompat.checkSelfPermission(PrescriptionMapActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                buildGoogleApiClient();
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(PrescriptionMapActivity.this).addConnectionCallbacks(PrescriptionMapActivity.this).addOnConnectionFailedListener(PrescriptionMapActivity.this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(PrescriptionMapActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, PrescriptionMapActivity.this);
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
        if (ActivityCompat.checkSelfPermission(PrescriptionMapActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(PrescriptionMapActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            mMap.setMyLocationEnabled(false);
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, PrescriptionMapActivity.this);
            }
        }
    }

    private void addCustomMarker(LatLng latLng, String price, String pharmacyName, final int position) {
        Log.d("TAG", "addCustomMarker()");
        if (mMap != null) {
            List<Float> integerList = new ArrayList<>();
            integerList.clear();

            for (int i=0;i<resultList.size();i++){
                if (resultList != null && resultList.size() > 0 && resultList.get(i).getPrices() != null &&
                        resultList.get(i).getPrices().size() > 0 && resultList.get(i).getPrices().get(0).getPrice() != null
                        && resultList.get(i).getPrices().get(0).getPrice().length() != 0) {
                    float pvalue = Float.parseFloat(resultList.get(i).getPrices().get(0).getPrice());
                    integerList.add(pvalue);
                }
            }
            minPrice = Collections.min(integerList);
            //clusterManager.clearItems();
            clusterManager.addItem(resultList.get(position));
            clusterManager.setRenderer(new MyClusterRender(PrescriptionMapActivity.this, mMap, clusterManager, position, price, pharmacyName));
            clusterManager.cluster();
            clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<DrugSearchResultResPayLoad1.Result.PharmacyPricing>() {
                @Override
                public boolean onClusterClick(Cluster<DrugSearchResultResPayLoad1.Result.PharmacyPricing> cluster) {
                    if (cluster.getItems() != null && cluster.getItems().size() > 0) {
                        dialog = new BottomSheetDialog(PrescriptionMapActivity.this, R.style.AppBottomSheetDialogTheme);
                        dialog.setContentView(R.layout.layout_bottomsheet_doctors);
                        TextView tvResults = (TextView) dialog.findViewById(R.id.tv_results);
                        ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
                        RecyclerView rvClusterMapList = (RecyclerView) dialog.findViewById(R.id.rv_clusterMapList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvClusterMapList.setLayoutManager(mLayoutManager);
                        rvClusterMapList.setItemAnimator(new DefaultItemAnimator());
                        clusterItemsList.clear();
                        for (DrugSearchResultResPayLoad1.Result.PharmacyPricing resPayLoad : cluster.getItems()) {
                            clusterItemsList.add(resPayLoad);
                        }
                        if (clusterItemsList != null && clusterItemsList.size() > 0) {
                            tvResults.setText(clusterItemsList.size() + " Results");
                            BestPriceNearbyAdapter1 mAdapter = new BestPriceNearbyAdapter1(PrescriptionMapActivity.this, clusterItemsList, "");
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
                    return true;
                }
            });
            clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<DrugSearchResultResPayLoad1.Result.PharmacyPricing>() {
                @Override
                public boolean onClusterItemClick(DrugSearchResultResPayLoad1.Result.PharmacyPricing pharmacyPricing) {
                    llAddress.setVisibility(View.VISIBLE);
                    eventInfo = pharmacyPricing;
                    tvPharmacy.setText(eventInfo.getPharmacy().getName());
                    tvPrice.setText("$" + eventInfo.getPrices().get(0).getPrice());
                    tvAddress.setText(eventInfo.getPharmacy().getAddress().getAddress1() + "," + eventInfo.getPharmacy().getAddress().getState() + "," + eventInfo.getPharmacy().getAddress().getPostalCode());
                    if (eventInfo.getPrices().get(0).getFormattedPrice() == null || Double.parseDouble(eventInfo.getPrices().get(0).getFormattedPrice()) == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                        tvSaving.setText(String.valueOf(Math.round(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) + getResources().getString(R.string.savings));
                        tvSaving.setVisibility(View.GONE);
                    } else {
                        tvSaving.setText(String.valueOf(Math.round((Double.parseDouble(eventInfo.getPrices().get(0).getFormattedPrice()) / (Double.parseDouble(eventInfo.getPrices().get(0).getFormattedPrice()) + Double.parseDouble(eventInfo.getPrices().get(0).getPrice()))) * 100.0d)) + getResources().getString(R.string.savings));
                        tvSaving.setVisibility(View.GONE);
                    }
                    return true;
                }
            });

        }
    }

    private class MyClusterRender extends DefaultClusterRenderer<DrugSearchResultResPayLoad1.Result.PharmacyPricing> {
        int position;
        String price, pharmacyName;

        public MyClusterRender(Context context, GoogleMap map, ClusterManager<DrugSearchResultResPayLoad1.Result.PharmacyPricing> clusterManager,
                               int position, String price, String pharmacyName) {
            super(context, map, clusterManager);
            this.position = position;
            this.price = price;
            this.pharmacyName = pharmacyName;
        }

        @Override
        protected void onBeforeClusterItemRendered(DrugSearchResultResPayLoad1.Result.PharmacyPricing item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(item.getPrices().get(0).getPrice(), item.getPharmacy().getName(), item, position)));
        }

        @Override
        protected void onClusterItemRendered(DrugSearchResultResPayLoad1.Result.PharmacyPricing clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);

        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(PrescriptionMapActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(PrescriptionMapActivity.this, "android.permission.ACCESS_FINE_LOCATION")) {
            ActivityCompat.requestPermissions(PrescriptionMapActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
            return false;
        }
        ActivityCompat.requestPermissions(PrescriptionMapActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 99:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(PrescriptionMapActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                    return;
                } else if (ContextCompat.checkSelfPermission(PrescriptionMapActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
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

    private Bitmap getMarkerBitmapFromView(String price, String pharmacyName, DrugSearchResultResPayLoad1.Result.PharmacyPricing item, int position) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        markerTextview = (TextView) customMarkerView.findViewById(R.id.cost);
        location_name = (TextView) customMarkerView.findViewById(R.id.location_name);
        //markerTextview.setText("$" + String.valueOf(price));
        //location_name.setText(pharmacyName);
        ArrayList<Double> distance = new ArrayList();
        for (int i = 0; i < resultList.size(); i++) {
            distance.add(Double.valueOf(resultList.get(i).getPharmacy().getDistance()));

        }
          float price1 = 0;

        if (price != null && price.length() > 0) {
            price1 = Float.parseFloat(price); //34.22
        }

        if (price1 > 0 && minPrice == price1) {
            markerTextview.setBackgroundResource(R.drawable.mapitemselected_ic);
            /*markerTextview.setMaxHeight(50);
            markerTextview.setMaxWidth(50);*/
            markerTextview.setText("$" + price1);
            location_name.setText(pharmacyName);
        } else {
            markerTextview.setBackgroundResource(R.drawable.mapitem_ic);
            markerTextview.setText("$" + price1);
            location_name.setText(pharmacyName);
        }

        /*markerTextview.setBackgroundResource(R.drawable.mapitemselected_ic);
        markerTextview.setMaxHeight(50);
        markerTextview.setMaxWidth(50);
        markerTextview.setText("$" + price);
        location_name.setText(pharmacyName);*/

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

    protected boolean checkInternet() {
        getBaseContext();
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PrescriptionMapActivity.this, BestPricesNearbyActivity.class);
        intent.putExtra("switchGeneric", switchGeneric);
        startActivity(intent);
    }
}
