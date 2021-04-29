package com.VURVhealth.vurvhealth.medical.facilities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.doctors.DoctorVURVBannerActivity;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDeatilResPayload;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterFacility;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 25/1/17.
 */
public class FacilityResultDetailsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private ImageView backBtn,imgSave;
    private FrameLayout flBanner;
//    private SearchFacilitiesResPayLoad mCurrentListing;
    private FacilityDeatilResPayload mCurrentListing;
    private TextView tvresult,tvStreet,tvCity,tvPhoneNumber,tvProcedure,tvSave;
    private LinearLayout ll_call,llShare,ll_direction,ll_save;
    private LatLng latLng;
    private ProgressDialog pDialog;
    public SharedPreferences prefsLoginData;
    private static String facilityName;
    private int clickedPosition, savedItemStatus;
    private String activity = "";
    private SqLiteDbHelper sqLiteDbHelper;


    /** Called when the activity is first created. */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_facility);
        sqLiteDbHelper = new SqLiteDbHelper(FacilityResultDetailsActivity.this);

        prefsLoginData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        if(checkInternet()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }else {
//            DialogClass.createDAlertDialog(PrescriptionResultsDetailsActivity.this, getResources().getString(R.string.no_network));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();

        }
        try {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                mCurrentListing = b.getParcelable("SearchResultObject");
                clickedPosition = b.getInt("position");
                activity = b.getString("activity");
                savedItemStatus = b.getInt("savedItem");
                facilityName = getIntent().getExtras().getString("facilityName");
            }
        } catch (Exception e) {
        }


        backBtn = (ImageView) findViewById(R.id.backBtn);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        flBanner = (FrameLayout) findViewById(R.id.flBanner);

        tvresult = (TextView) findViewById(R.id.tvresult);
        tvStreet = (TextView) findViewById(R.id.tvStreet);
        tvCity = (TextView) findViewById(R.id.tvCity);
//        tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        ll_direction = (LinearLayout) findViewById(R.id.ll_direction);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        ll_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+mCurrentListing.getFacAddLat()+","+mCurrentListing.getFacAddLong()));
                startActivity(intent); //33.643446, -117.596786
            }
        });

        llShare = (LinearLayout) findViewById(R.id.llShare);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvProcedure = (TextView) findViewById(R.id.tvProcedure);
        tvSave = (TextView) findViewById(R.id.tvSave);

        if ( savedItemStatus == 1) {
            imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            tvSave.setTextColor(getResources().getColor(R.color.light_blue));
            tvSave.setText("Saved");
        }

        tvresult.setText(facilityName);
//        String[] address = mCurrentListing.getFacAddressLine1().split(",");
//        String[] street = address[0].split("\\s+");
//        String street[] = address[0].split("\\s+", 2);

        tvStreet.setText(mCurrentListing.getFacAddressLine1());
        tvCity.setText(mCurrentListing.getFacCity()+", "+mCurrentListing.getFacState()+", "+mCurrentListing.getFacZipCode());
//        tvZipcode.setText(address[3]);

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(mCurrentListing.getFacPhoneNumber().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(mCurrentListing.getFacPhoneNumber().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(mCurrentListing.getFacPhoneNumber().substring(6, 10));
            tvPhoneNumber.setText(stringBuilder);
        }catch (ArrayIndexOutOfBoundsException e){
            tvPhoneNumber.setText(mCurrentListing.getFacPhoneNumber());
        }
        tvProcedure.setText(mCurrentListing.getFacFacilitySubType());

        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mCurrentListing.getFacPhoneNumber()));
                try {
                    onCall();
                    /*Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mCurrentListing.getPhone()));

                    startActivity(callIntent);*/
                }catch (Exception e){
                    Log.v("Call>>",e.getMessage());
                }
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, "I’ve saved money using VURVhealth. Download it FREE here https://itunes.apple.com/us/app/vurvhealth/id1164812682?ls=1&mt=8");
                intent.putExtra(Intent.EXTRA_TEXT, "http://www.vurvhealth.com");
                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.equalsIgnoreCase("FacilitySearchResults")) {
//                    Intent intent = new Intent(FacilityResultDetailsActivity.this, FacilitySearchResult.class);
//                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(FacilityResultDetailsActivity.this, NoSavedItemActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        flBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FacilityResultDetailsActivity.this, DoctorVURVBannerActivity.class));
            }
        });
        ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvSave.getText().toString().equalsIgnoreCase("Saved"))
                    saveForLaterFacilityService();
                else
                    deleteSaveForLaterFacility();
            }
        });

    }

    protected void showProgressDialog(Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void dismissProgressDialog(){
        if(pDialog != null)
            pDialog.dismiss();
    }

    private void saveForLaterFacilityService() {
        ApiInterface apiService =
                ApiClient.getClient(FacilityResultDetailsActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterFacility> saveForLaterRequestList = new ArrayList<SaveForLaterFacility>();
        SaveForLaterFacility saveForLaterRequest = new SaveForLaterFacility();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId",1)));
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setFacilityProviderId(Application_holder.providerId);

        saveForLaterRequestList.add(saveForLaterRequest);

        Call<ArrayList<StatusResponseForTotalProject>> call = apiService.saveForLaterFacility(saveForLaterRequestList);
        call.enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {

                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = response.body();
                Toast.makeText(getApplicationContext(),"Added as favourite",Toast.LENGTH_SHORT).show();
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
                tvSave.setText("Saved");
                if (activity.equalsIgnoreCase("FacilitySearchResults")) {
                    for (int i = 0; i< MedicalScreenActivity.resPayloadsForFacilities.size(); i++) {
                        if (MedicalScreenActivity.resPayloadsForFacilities.get(i).getProviderID().equalsIgnoreCase(Application_holder.providerId)){
                            MedicalScreenActivity.resPayloadsForFacilities.get(i).setSavedStatus(1);
                            if (FacilitySearchResult.searchPractitionerResPayLoads != null) {
                                FacilitySearchResult.searchPractitionerResPayLoads.get(clickedPosition).setSavedStatus(1);
                            }
                            sqLiteDbHelper.updateSavedStatusFlag("filter_facility",1,Application_holder.providerId);
                            break;
                        }
                    }
//                    MedicalScreenActivity.resPayloadsForFacilities.get(clickedPosition).setSavedStatus(1);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterFacility() {
        showProgressDialog(FacilityResultDetailsActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(FacilityResultDetailsActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterFacility> saveForLaterRequestList = new ArrayList<SaveForLaterFacility>();
        SaveForLaterFacility saveForLaterRequest = new SaveForLaterFacility();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId",1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setFacilityProviderId(Application_holder.providerId);

        saveForLaterRequestList.add(saveForLaterRequest);

        Call<ArrayList<StatusResponseForTotalProject>> call = apiService.saveForLaterFacility(saveForLaterRequestList);
        call.enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {

                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = response.body();
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_star_ic));
                tvSave.setTextColor(getResources().getColor(R.color.black));
                tvSave.setText("Save for Later");
                if (activity.equalsIgnoreCase("FacilitySearchResults")) {
                    for (int i=0;i<MedicalScreenActivity.resPayloadsForFacilities.size(); i++) {
                        if (MedicalScreenActivity.resPayloadsForFacilities.get(i).getProviderID().equalsIgnoreCase(Application_holder.providerId)){
                            MedicalScreenActivity.resPayloadsForFacilities.get(i).setSavedStatus(0);
                            if (FacilitySearchResult.searchPractitionerResPayLoads != null) {
                                FacilitySearchResult.searchPractitionerResPayLoads.get(clickedPosition).setSavedStatus(0);
                            }
                            sqLiteDbHelper.updateSavedStatusFlag("filter_facility",0,Application_holder.providerId);
                            break;
                        }
                    }
                }
//                Toast.makeText(getApplicationContext(),insertRecentSearchRespPayLoad.get(0).getStatus(),Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        if (mCurrentListing.getFacAddLat() != null && mCurrentListing.getFacAddLat().length() > 0) {
            latLng = new LatLng(Double.parseDouble(mCurrentListing.getFacAddLat()), Double.parseDouble(mCurrentListing.getFacAddLong()));

            //set the drawable marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_facilities_ic));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    //checking internet connection
    protected boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

    //checking call permissions
    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + mCurrentListing.getFacPhoneNumber())));
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (activity.equalsIgnoreCase("FacilitySearchResults")) {
//            Intent intent = new Intent(FacilityResultDetailsActivity.this, FacilitySearchResult.class);
//            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(FacilityResultDetailsActivity.this, NoSavedItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }    }
}
