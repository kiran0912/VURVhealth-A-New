package com.VURVhealth.vurvhealth.althealth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.upgrade.UpgradeAltHealthFlipActivity;
import com.VURVhealth.vurvhealth.utilities.Utility;
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
import com.VURVhealth.vurvhealth.althealth.pojos.AHSProviderDetailsRespayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.google.android.gms.plus.PlusShare;

import org.apache.http.protocol.HTTP;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltHealthSearchDetailsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_REQUEST_CONTACT = 100;
    private String activity = "";
    private ImageView backBtn;
    private Button btnAbout;
    private Button btnContactInfo;
    private int clickedPosition;
    private TextView doctor_name;
    private FrameLayout flBanner1;
    private FrameLayout flBanner2;
    private ImageView imgSave;
    private LatLng latLng;
    double latitude;
    private LinearLayout llAbout;
    private LinearLayout llContactInfo;
    private LinearLayout llShare;
    private LinearLayout ll_call;
    private LinearLayout ll_direction;
    private LinearLayout ll_save;
    double longitude;
    private Marker mCurrLocationMarker;
    private AHSProviderDetailsRespayload mCurrentListing;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private ProgressDialog pDialog;
    public SharedPreferences prefsLoginData;
    private int savedItemStatus;
    private TextView tvAddress;
    private TextView tvClinicName;
    private TextView tvDegree;
    private TextView tvPhoneNumber;
    private TextView tvSave;
    private TextView tvSpeciality;
    
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_deatails_althealth);
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                mCurrentListing = (AHSProviderDetailsRespayload) b.getParcelable("SearchResultObject");
                clickedPosition = b.getInt("position");
                activity = b.getString("activity");
                savedItemStatus = b.getInt("savedItem");
            }
        } catch (Exception e) {
        }
        backBtn = (ImageView) findViewById(R.id.backBtn);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        btnContactInfo = (Button) findViewById(R.id.btnContactInfo);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        llAbout = (LinearLayout) findViewById(R.id.llAbout);
        llContactInfo = (LinearLayout) findViewById(R.id.llContactInfo);
        ll_direction = (LinearLayout) findViewById(R.id.ll_direction);
        llShare = (LinearLayout) findViewById(R.id.llShare);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        flBanner1 = (FrameLayout) findViewById(R.id.flBanner);
        flBanner2 = (FrameLayout) findViewById(R.id.flBanner2);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvSpeciality = (TextView) findViewById(R.id.tvSpeciality);
        tvClinicName = (TextView) findViewById(R.id.tvClinicName);
        tvDegree = (TextView) findViewById(R.id.tvDegree);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        if (savedItemStatus == 1) {
            imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            tvSave.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            tvSave.setText(getResources().getString(R.string.saved));
        }
        doctor_name.setText(mCurrentListing.getName() + ", " + mCurrentListing.getDegree());
        tvSpeciality.setText(mCurrentListing.getSpecialty());
        tvClinicName.setText(mCurrentListing.getClinicName());
        tvDegree.setText(mCurrentListing.getProgramDegree());
        tvAddress.setText(mCurrentListing.getAddress());
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(mCurrentListing.getPhoneNumber().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(mCurrentListing.getPhoneNumber().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(mCurrentListing.getPhoneNumber().substring(6, 10));
            tvPhoneNumber.setText(stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            tvPhoneNumber.setText(mCurrentListing.getPhoneNumber());
        }
        if (prefsLoginData.getString("search_type", "").contains("Alt Health")) {
            flBanner1.setVisibility(View.VISIBLE);
            flBanner2.setVisibility(View.VISIBLE);
        } else {
            flBanner1.setVisibility(View.GONE);
            flBanner2.setVisibility(View.GONE);
        }
        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + mCurrentListing.getPhoneNumber()));
                try {
                    onCall();
                } catch (Exception e) {
                    Log.v("Call>>", e.getMessage());
                }
            }
        });
        ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvSave.getText().toString().equalsIgnoreCase(getResources().getString(R.string.saved))) {
                    deleteSaveForLaterService();
                } else {
                    saveForLaterAHSService();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equalsIgnoreCase("AHSListActivity")) {
                    finish();
                    return;
                }
                startActivity(new Intent(AltHealthSearchDetailsActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAbout.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
                btnAbout.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
                btnContactInfo.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
                btnContactInfo.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
                llAbout.setVisibility(View.VISIBLE);
                llContactInfo.setVisibility(View.GONE);
            }
        });
        btnContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnContactInfo.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
                btnContactInfo.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
                btnAbout.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
                btnAbout.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
                llAbout.setVisibility(View.GONE);
                llContactInfo.setVisibility(View.VISIBLE);
            }
        });
        flBanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AltHealthSearchDetailsActivity.this, UpgradeAltHealthFlipActivity.class);
                intent.putExtra("activity", "AHSVURVBannerActivity");
                startActivity(intent);
            }
        });
        flBanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AltHealthSearchDetailsActivity.this, UpgradeAltHealthFlipActivity.class);
                intent.putExtra("activity", "AHSVURVBannerActivity");
                startActivity(intent);
            }
        });
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.putExtra("android.intent.extra.TEXT", "http://www.vurvhealth.com/");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_with)));
            }
        });
        ll_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + mCurrentListing.getLatitude() + "," + mCurrentListing.getLongitude())));
            }
        });
    }

    private void saveForLaterAHSService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setProviderId(mCurrentListing.getAHSProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAHS(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
                tvSave.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
                tvSave.setText(getResources().getString(R.string.saved));
                if (activity.equalsIgnoreCase("AHSListActivity")) {
                    ( AltHealthScreenActivity.resPayloads.get(clickedPosition)).setSavedStatus(1);
                }
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(mCurrentListing.getAHSProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAHS(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_star_ic));
                tvSave.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.black));
                tvSave.setText(getResources().getString(R.string.save_for_later));
                if (activity.equalsIgnoreCase("AHSListActivity")) {
                    ( AltHealthScreenActivity.resPayloads.get(clickedPosition)).setSavedStatus(0);
                }
                Toast.makeText(getApplicationContext(), ((StatusResponseForTotalProject) ((ArrayList) response.body()).get(0)).getStatus(), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
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

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(1);
        if (Build.VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        if (mCurrentListing.getLatitude() != null && mCurrentListing.getLatitude().length() > 0) {
            latLng = new LatLng(Double.parseDouble(mCurrentListing.getLatitude()), Double.parseDouble(mCurrentListing.getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_alt));
            mCurrLocationMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void getContact() {
        ContentValues values = new ContentValues();
        values.put("number", "987456321");
        values.put("type", Integer.valueOf(0));
        values.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, "VURV");
        values.put("name", "VURV");
        Uri updateUri = Uri.withAppendedPath(getContentResolver().insert(Contacts.People.CONTENT_URI, values), "phones");
        values.clear();
        values.put("type", Integer.valueOf(2));
        values.put("number", "987456321");
        updateUri = getContentResolver().insert(updateUri, values);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "No permission for contacts", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    getContact();
                    return;
                }
            default:
                return;
        }
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = new String[]{""};
        String[] CC = new String[]{""};
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra("android.intent.extra.EMAIL", TO);
        emailIntent.putExtra("android.intent.extra.CC", CC);
        emailIntent.putExtra("android.intent.extra.SUBJECT", "VURVhealth");
        emailIntent.putExtra("android.intent.extra.TEXT", "http://www.vurvhealth.com/");
        try {
            startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_mail)));
            Log.i("Finish sending email...", "");
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onCall() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }
        startActivity(new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + mCurrentListing.getPhoneNumber())));
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (activity.equalsIgnoreCase("AHSListActivity")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, NoSavedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
