package com.VURVhealth.vurvhealth.prescriptions;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterRequestPojo;
import com.VURVhealth.vurvhealth.upgrade.UpgradeRxFlipActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.utilities.Utility;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HTTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.VURVhealth.vurvhealth.prescriptions.BestPricesNearbyActivity.brandsearchResultResPayLoads1;
import static com.VURVhealth.vurvhealth.prescriptions.BestPricesNearbyActivity.genericsearchResultResPayLoads1;
import static com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity.isGeneric;


public class PrescriptionResultsDetailsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private String activity = "";
    private String address;
    private ImageView backBtn, imgCall;
    private int clickedPosition;
    private String drugGenNDC;
    private String drugNDC;
    private String drugName;
    private String drugType;
    private FrameLayout flBanner;
    private ImageView imgSave;
    private LatLng latLng;
    private LinearLayout llShare;
    private LinearLayout ll_call;
    private LinearLayout ll_direction;
    private LinearLayout ll_save;
    private Marker mCurrLocationMarker;
    private DrugSearchResultResPayLoad1.Result.PharmacyPricing mCurrentListing;
    private List<DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy> mCurrentPharmacy = new ArrayList<>();
    private List<DrugSearchResultResPayLoad1.Result.PharmacyPricing.Price> mCurrentPrice = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private ProgressDialog pDialog;
    public SharedPreferences prefsLoginData;
    private TextView tvCity;
    private TextView tvPhoneNumber;
    private TextView tvSave;
    private TextView tvStreet;
    private TextView tvZipcode;
    private TextView tv_DrugName;
    private TextView tv_Price;
    private TextView tvresult;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_details_prescription);
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        if (checkInternet()) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
       /* try {
        if (getIntent()!=null){
            mCurrentListing = (List<DrugSearchResultResPayLoad1.Result.PharmacyPricing>) getIntent().getSerializableExtra("SearchResultObject");
            clickedPosition = getIntent().getIntExtra("position",0);
            activity = getIntent().getStringExtra("activity");
        }} catch (Exception e) {
            Log.v("TAG", "SearchResultObject" + e.getMessage());
        }*/
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                b.setClassLoader(DrugSearchResultResPayLoad1.Result.PharmacyPricing.class.getClassLoader());
                mCurrentListing = b.getParcelable("SearchResultObject");
                clickedPosition = b.getInt("position");
                activity = b.getString("activity");
            }
        } catch (Exception e) {
            Log.v("TAG", "SearchResultObject" + e.getMessage());
        }
        backBtn = (ImageView) findViewById(R.id.backBtn);
        flBanner = (FrameLayout) findViewById(R.id.flBanner);
        tvresult = (TextView) findViewById(R.id.tvresult);
        tvStreet = (TextView) findViewById(R.id.tvStreet);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tv_DrugName = (TextView) findViewById(R.id.tv_DrugName);
        tv_Price = (TextView) findViewById(R.id.tv_Price);
        tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        ll_direction = (LinearLayout) findViewById(R.id.ll_direction);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgCall = (ImageView) findViewById(R.id.imgCall);
        address = tvCity.getText().toString();
        if (mCurrentListing.isSavedItem()) {
            imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            tvSave.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            tvSave.setText(getResources().getString(R.string.saved));
        }
        ll_direction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + mCurrentListing.getPharmacy().getAddress().getLatitude() + "," + mCurrentListing.getPharmacy().getAddress().getLongitude())));
            }
        });
        llShare = (LinearLayout) findViewById(R.id.llShare);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        if (prefsLoginData.getString("search_type", "").contains("Prescriptions")) {
            flBanner.setVisibility(View.VISIBLE);
        } else {
            flBanner.setVisibility(View.GONE);
        }
        String[] ads = address.split(",");
        tvresult.setText(mCurrentListing !=null ?mCurrentListing.getPharmacy().getName().split("\\#")[0]:"");
        tvStreet.setText(mCurrentListing !=null ?mCurrentListing.getPharmacy().getAddress().getAddress1():"");
        tvCity.setText(mCurrentListing.getPharmacy().getAddress().getCity() + ", " + this.mCurrentListing.getPharmacy().getAddress().getState());
        Log.v("Presc", "City: "+tvCity.getText().toString());
        tvZipcode.setText(mCurrentListing !=null ? mCurrentListing.getPharmacy().getAddress().getPostalCode():"");
        if (tvZipcode.getText().toString().length()>5) {
            tvZipcode.setText(mCurrentListing != null ? mCurrentListing.getPharmacy().getAddress().getPostalCode().substring(0, 5) + "-" + mCurrentListing.getPharmacy().getAddress().getPostalCode().substring(5) : "");
        }

        SharedPreferences prefs = getSharedPreferences("SearchData", 0);
        if (prefs.getString("DrugName", null) != null) {
            drugName = prefs.getString("DrugName", "");
            drugType = prefs.getString("drugType", "");
            drugGenNDC = prefs.getString("drugGenNDC", "");
            drugNDC = prefs.getString("drugNDC", "");
        }
        if (tvPhoneNumber.getText().toString().equals("--")){
            imgCall.setImageDrawable(getDrawable(R.drawable.ic_call_grey));
        }
        /*srikanth*/
        if (getIntent().getStringExtra("activity") == null) {
            tv_DrugName.setText(drugName);
           // tv_Price.setText("$" + String.valueOf(mCurrentListing.getPrice()));
            tv_Price.setText("$" + String.valueOf(mCurrentListing !=null ?mCurrentListing.getPrices().get(0).getPrice():""));
        } else if (!getIntent().getStringExtra("activity").equalsIgnoreCase("saveItemActivity")) {
            tv_DrugName.setText(drugName + " ("+mCurrentListing.getDrugType() + ")");
          //  tv_Price.setText("$" + String.valueOf(mCurrentListing.getPrice()));
            tv_Price.setText("$" + String.valueOf(mCurrentListing !=null ?mCurrentListing.getPrices().get(0).getPrice():""));
        } else if (getIntent() != null) {
            tv_DrugName.setText(drugName + "(" + getIntent().getStringExtra("drugType") + ")");
          //  tv_Price.setText("$" + getIntent().getStringExtra(Param.PRICE));
            tv_Price.setText("$" + getIntent().getStringExtra(Param.PRICE));
        }
        /*try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(mCurrentListing.getPhone().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(mCurrentListing.getPhone().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(mCurrentListing.getPhone().substring(6, 10));
            tvPhoneNumber.setText(stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            tvPhoneNumber.setText(mCurrentListing.getPhone());
        }*/
        /*ll_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + mCurrentListing.getPhone()));
                try {
                    onCall();
                } catch (Exception e) {
                    Log.v("Call>>", e.getMessage());
                }
            }
        });*/
        llShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.putExtra("android.intent.extra.TEXT", "http://www.vurvhealth.com");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_with)));
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equalsIgnoreCase("BestPricesNearbyActivity")) {
                    startActivity(new Intent(PrescriptionResultsDetailsActivity.this, BestPricesNearbyActivity.class));
                    finish();
                    return;
                }
                startActivity(new Intent(PrescriptionResultsDetailsActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        flBanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionResultsDetailsActivity.this, UpgradeRxFlipActivity.class);
                intent.putExtra("activity", "PrescriptionBannerActivity");
                startActivity(intent);
            }
        });
        ll_save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvSave.getText().toString().equalsIgnoreCase(getResources().getString(R.string.saved))) {
                    deleteSaveForLaterPrescriptionService();
                } else {
                    saveForLaterService();
                }
            }
        });
    }

    private void saveForLaterService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterRequestPojo> saveForLaterRequestList = new ArrayList();
        SaveForLaterRequestPojo saveForLaterRequest = new SaveForLaterRequestPojo();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        if (isGeneric == true) {
            saveForLaterRequest.setDrugType("generic");
            saveForLaterRequest.setGNRCALTNDC(drugGenNDC);
            saveForLaterRequest.setNDC("0");
        } else {
            saveForLaterRequest.setDrugType("brand");
            saveForLaterRequest.setGNRCALTNDC("0");
            saveForLaterRequest.setNDC(drugNDC);
        }
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setIsLeast("");
        saveForLaterRequest.setNCPDP(mCurrentListing !=null ?mCurrentListing.getPharmacy().getNcpdpChaincode():"");
        saveForLaterRequest.setPrice(mCurrentListing !=null ?String.valueOf(mCurrentListing.getPrices().get(0).getPrice()):"");
        saveForLaterRequest.setDrugName(drugName);
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLater(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_fav), Toast.LENGTH_LONG).show();
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
                tvSave.setTextColor(ContextCompat.getColor(PrescriptionResultsDetailsActivity.this, R.color.light_blue));
                tvSave.setText(getResources().getString(R.string.saved));
                if (!activity.equalsIgnoreCase("BestPricesNearbyActivity")) {
                    return;
                }
                if (isGeneric == true) {
                    (genericsearchResultResPayLoads1.get(clickedPosition)).setSavedItem(true);
                } else {
                    (brandsearchResultResPayLoads1.get(clickedPosition)).setSavedItem(true);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterPrescriptionService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterRequestPojo> saveForLaterRequestList = new ArrayList();
        SaveForLaterRequestPojo saveForLaterRequest = new SaveForLaterRequestPojo();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setNCPDP(mCurrentListing !=null ?mCurrentListing.getPharmacy().getNcpdpChaincode():"");
        saveForLaterRequest.setPrice(mCurrentListing !=null ?String.valueOf(mCurrentListing.getPrices().get(0).getPrice()):"");
        saveForLaterRequest.setNDC(drugNDC);
        saveForLaterRequest.setGNRCALTNDC(drugGenNDC);
        saveForLaterRequest.setIsLeast("");
        saveForLaterRequest.setDrugName(drugName);
        saveForLaterRequest.setDrugType(drugType);
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLater(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                mCurrentListing.setSavedItem(false);
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_star_ic));
                tvSave.setTextColor(ContextCompat.getColor(PrescriptionResultsDetailsActivity.this, R.color.black));
                tvSave.setText(getResources().getString(R.string.save_for_later));
                if (activity.equalsIgnoreCase("BestPricesNearbyActivity")) {
                    if (isGeneric == true) {
                        (genericsearchResultResPayLoads1.get(clickedPosition)).setSavedItem(false);
                    } else {
                        (brandsearchResultResPayLoads1.get(clickedPosition)).setSavedItem(false);
                    }
                }
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    protected void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(getString(R.string.please_wait));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    public void onConnected(@Nullable Bundle bundle) {
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

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void onLocationChanged(Location location) {
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        latLng = new LatLng(mCurrentListing !=null ?mCurrentListing.getPharmacy().getAddress().getLatitude():0, mCurrentListing !=null ?mCurrentListing.getPharmacy().getAddress().getLongitude():0);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_ic));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            mMap.setMyLocationEnabled(false);
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(1);
        if (VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        latLng = new LatLng(mCurrentListing !=null ?mCurrentListing.getPharmacy().getAddress().getLatitude():0, mCurrentListing !=null ?mCurrentListing.getPharmacy().getAddress().getLongitude():0);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_ic));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    protected boolean checkInternet() {
        getBaseContext();
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE /*123*/:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Log.d("TAG", "Call Permission Not Granted");
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void onCall() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }
        //startActivity(new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + mCurrentListing.getPhone())));
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (activity.equalsIgnoreCase("BestPricesNearbyActivity")) {
            startActivity(new Intent(PrescriptionResultsDetailsActivity.this, BestPricesNearbyActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(this, NoSavedItemActivity.class));
        finish();
    }


}
