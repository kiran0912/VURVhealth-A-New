package com.VURVhealth.vurvhealth.dental;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.dental.pojos.DentalProviderDetailsResPayload;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 18/3/17.
 */

public class DentalMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ImageView backBtn;
    private boolean click = false;
    private Context context = DentalMapActivity.this;
    ArrayList<ArrayList<SearchForDentalResPayLoad.Datum>> dentalSortedArray2;
    ClusterManager<SearchForDentalResPayLoad.Datum> clusterManager;
    //ArrayList<SearchForDentalResPayLoad.Datum> clusterItemsList = new ArrayList<>();
    private ArrayList<SearchForDentalResPayLoad.Datum> eventInfo = new ArrayList<>();
    private ImageView listBtn;
    private LinearLayout llAddress;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private HashMap<Marker, ArrayList<SearchForDentalResPayLoad.Datum>> markerStringHashMap = new HashMap();
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
        final ArrayList<SearchForDentalResPayLoad.Datum> dentalSortedArray = DentalScreenActivity.resPayloads;
        dentalSortedArray2 = new ArrayList();
        HashSet lookup = new HashSet();
        ArrayList<Double> latlist = new ArrayList<>();
        for (int index = 0; index < dentalSortedArray.size(); index++) {
            SearchForDentalResPayLoad.Datum searchdentalResPayLoad = (SearchForDentalResPayLoad.Datum) dentalSortedArray.get(index);
            double lat = searchdentalResPayLoad.getLatitude();

            if (!lookup.contains(lat)) {
                lookup.add(lat);
                latlist.add(lat);
                ArrayList<SearchForDentalResPayLoad.Datum> duplicateList = new ArrayList<>();
                duplicateList.add(searchdentalResPayLoad);
                dentalSortedArray2.add(duplicateList);
            } else {
                int index1 = latlist.indexOf(lat);
                ArrayList<SearchForDentalResPayLoad.Datum> duplicateList = dentalSortedArray2.get(index1);
                duplicateList.add(searchdentalResPayLoad);
                dentalSortedArray2.set(index1, duplicateList);
            }
        }
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventInfo!=null && eventInfo.size()==1) {
                    getDentalProviderDetails();
                }
                else if (eventInfo!=null && eventInfo.size()>1) {
                    dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
                    dialog.setContentView(R.layout.layout_bottomsheet_doctors);
                    TextView tvResults = (TextView) dialog.findViewById(R.id.tv_results);
                    ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
                    RecyclerView rvClusterMapList = (RecyclerView) dialog.findViewById(R.id.rv_clusterMapList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvClusterMapList.setLayoutManager(mLayoutManager);
                    rvClusterMapList.setItemAnimator(new DefaultItemAnimator());
                    tvResults.setText(eventInfo.size() + " Results");
                    DentalListAdapter mAdapter = new DentalListAdapter(context, eventInfo);
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
        if (dentalSortedArray2.size() > 10) {
            tvresult.setText("10 " + getResources().getString(R.string.results));
        } else if (dentalSortedArray2.size() == 1) {
            tvresult.setText(getResources().getString(R.string.one_result));
        } else {
            tvresult.setText(dentalSortedArray2.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission();
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DentalMapActivity.this, DentalListActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        String string;
        if (dentalSortedArray2.size() > 10) {
            i = 0;
            while (i < 10) {
                if (dentalSortedArray2.get(i).get(0).getLatitude() != 0) {
                    latLng = new LatLng(dentalSortedArray2.get(i).get(0).getLatitude(), dentalSortedArray2.get(i).get(0).getLongitude());
                    if (dentalSortedArray2.get(i).size() > 1) {
                        string = getResources().getString(R.string.text_multiple_dentists);
                    } else {
                        string = ((SearchForDentalResPayLoad.Datum) dentalSortedArray2.get(i).get(0)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForDentalResPayLoad.Datum) dentalSortedArray2.get(i).get(0)).getMidInitName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForDentalResPayLoad.Datum) dentalSortedArray2.get(i).get(0)).getLastName();
                    }
                    addCustomMarker(latLng, string, i);
                }
                i++;

            }
        } else {
            i = 0;
            while (i < dentalSortedArray2.size()) {
                if (dentalSortedArray2.get(i).get(0).getLatitude() != 0) {
                    latLng = new LatLng(dentalSortedArray2.get(i).get(0).getLatitude(), dentalSortedArray2.get(i).get(0).getLongitude());
                    if (dentalSortedArray2.get(i).size() > 1) {
                        string = getResources().getString(R.string.text_multiple_dentists);
                    } else {
                        string = ((SearchForDentalResPayLoad.Datum) dentalSortedArray2.get(i).get(0)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForDentalResPayLoad.Datum) dentalSortedArray2.get(i).get(0)).getMidInitName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForDentalResPayLoad.Datum) dentalSortedArray2.get(i).get(0)).getLastName();
                    }
                    addCustomMarker(latLng, string, i);
                }
                i++;
            }
        }
        if (dentalSortedArray2.get(0).get(0).getLatitude() != 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(dentalSortedArray2.get(0).get(0).getLatitude(), dentalSortedArray2.get(0).get(0).getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
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

    private void addCustomMarker(LatLng latLng, String name, final int position) {
        Log.d("TAG", "addCustomMarker()");
        if (mMap != null) {
            markerStringHashMap.put(mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(name, position)))), dentalSortedArray2.get(position));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    llAddress.setVisibility(View.VISIBLE);
                    eventInfo = markerStringHashMap.get(marker);
                        if (eventInfo!=null && eventInfo.size()<=1) {
                            tvSpecialty.setVisibility(View.VISIBLE);
                            tvDoctorName.setText(eventInfo.get(0).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + eventInfo.get(0).getMidInitName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + eventInfo.get(0).getLastName());
                            tvSpecialty.setText(eventInfo.get(0).getLanguage());
                            tvLanguage.setVisibility(View.GONE);
                            tvGender.setVisibility(View.GONE);
                            tvAddress.setText(eventInfo.get(0).getAdd1() + ", " + eventInfo.get(0).getCity() + ", " + eventInfo.get(0).getState());

                        } else {
                            tvDoctorName.setText(getResources().getString(R.string.text_multiple_dentists));
                            tvAddress.setText(eventInfo.get(0).getAdd1() + ", " + eventInfo.get(0).getCity() + ", " + eventInfo.get(0).getState());
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
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
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
        /*if(dentalSortedArray2!=null && dentalSortedArray2.get(0).get(0).getFirstName().equalsIgnoreCase(dentalSortedArray2.get(position).get(0).getFirstName())) {
            image.setBackgroundResource(R.drawable.map_selected_dental_ic);
        } else {
            image.setBackgroundResource(R.drawable.map_dental_ic);
        }*/
        image.setBackgroundResource(R.drawable.map_dental_ic);
        ((TextView) customMarkerView.findViewById(R.id.location_name)).setText(name);
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

    private void getDentalProviderDetails() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(eventInfo.get(0).getProviderId());
        Application_holder.providerId = eventInfo.get(0).getProviderId();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getDentalProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<DentalProviderDetailsResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<DentalProviderDetailsResPayload>> call, Response<ArrayList<DentalProviderDetailsResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DentalProviderDetailsResPayload> facilityDeatilResPayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(DentalMapActivity.this, DentalSearchDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) facilityDeatilResPayloads.get(0));
                    b.putInt("position", DentalScreenActivity.resPayloads.indexOf(eventInfo.get(0)));
                    b.putString("activity", "DentalListActivity");
                    b.putInt("savedItem", ((SearchForDentalResPayLoad.Datum) DentalScreenActivity.resPayloads.get(DentalScreenActivity.resPayloads.indexOf(eventInfo.get(0)))).isSavedStatus());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DentalProviderDetailsResPayload>> call, Throwable t) {
                Toast.makeText(DentalMapActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DentalMapActivity.this, DentalListActivity.class));
        finish();
    }

}
