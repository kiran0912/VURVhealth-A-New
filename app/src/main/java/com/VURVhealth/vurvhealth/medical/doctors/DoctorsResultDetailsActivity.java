package com.VURVhealth.vurvhealth.medical.doctors;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterDoctors;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeMedicalFlipActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
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
import com.google.android.gms.plus.PlusShare;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 24/1/17.
 */

public class DoctorsResultDetailsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_REQUEST_CONTACT = 100;
    private ArrayList<AboutDoctorResPayLoad> aboutDoctorResPayLoads;
    private String activity = "";
    private ImageView backBtn;
    private Button btnAbout;
    private Button btnContactInfo;
    private int clickedPosition;
    private TextView doctor_name;
    private FrameLayout flBanner;
    private ImageView imgSave;
    private LatLng latLng = new LatLng(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    private LinearLayout llAbout;
    private LinearLayout llContactInfo;
    private LinearLayout llSavelater;
    private LinearLayout llShare;
    private LinearLayout ll_call;
    private LinearLayout ll_more;
    private String locationKey = "";
    private RecyclerView.Adapter<DoctorAdressAdapter.DataObjectHolder> mAdapter;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private ProgressDialog pDialog;
    private SearchPractitionerResPayLoad practitionerResPayLoad;
    public SharedPreferences prefsLoginData;
    private String providerId = "";
    private RecyclerView rvAddress;
    private int savedStatus;
    private SqLiteDbHelper sqLiteDbHelper;
    private TextView tvADAType;
    private TextView tvAcptType;
    private TextView tvAffiliation;
    private TextView tvEduSchl;
    private TextView tvGenderType;
    private TextView tvLanguageType;
    private TextView tvSave;
    private TextView tvSpeciality;
    
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_details_doctors);
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                clickedPosition = b.getInt("position");
                activity = b.getString("activity");
                savedStatus = b.getInt("savedItem");
                locationKey = b.getString("locationKey");
                providerId = b.getString("providerId");
            }
        } catch (Exception e) {
        }
        sqLiteDbHelper = new SqLiteDbHelper(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        btnContactInfo = (Button) findViewById(R.id.btnContactInfo);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        llAbout = (LinearLayout) findViewById(R.id.llAbout);
        llContactInfo = (LinearLayout) findViewById(R.id.llContactInfo);
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        llShare = (LinearLayout) findViewById(R.id.llShare);
        tvAcptType = (TextView) findViewById(R.id.tvAcptType);
        tvADAType = (TextView) findViewById(R.id.tvADAType);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        llSavelater = (LinearLayout) findViewById(R.id.llSavelater);
        flBanner = (FrameLayout) findViewById(R.id.flBanner);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        tvSpeciality = (TextView) findViewById(R.id.tvSpeciality);
        tvGenderType = (TextView) findViewById(R.id.tvGenderType);
        tvAffiliation = (TextView) findViewById(R.id.tvAffiliation);
        tvEduSchl = (TextView) findViewById(R.id.tvEduSchl);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvLanguageType = (TextView) findViewById(R.id.tvLanguageType);
        rvAddress = (RecyclerView) findViewById(R.id.rvaddress);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAddress.setLayoutManager(mLayoutManager);
        rvAddress.setItemAnimator(new DefaultItemAnimator());
        if (prefsLoginData.getString("search_type", "").contains("Doctors")) {
            flBanner.setVisibility(View.VISIBLE);
        } else {
            flBanner.setVisibility(View.GONE);
        }
        if (savedStatus == 1) {
            imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            tvSave.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            tvSave.setText(getResources().getString(R.string.saved));
        }
        getDoctorProviderDetails();
        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getMobileNo()));
                try {
                    onCall();
                } catch (Exception e) {
                    Log.v("Call>>", e.getMessage());
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equalsIgnoreCase("DoctorSearchResult")) {
                    finish();
                    return;
                }
                startActivity(new Intent(DoctorsResultDetailsActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAbout.setBackgroundColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.light_blue));
                btnAbout.setTextColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.view_bg));
                btnContactInfo.setBackgroundColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.view_bg));
                btnContactInfo.setTextColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.light_blue));
                llAbout.setVisibility(View.VISIBLE);
                llContactInfo.setVisibility(View.GONE);
            }
        });
        btnContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnContactInfo.setBackgroundColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.light_blue));
                btnContactInfo.setTextColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.view_bg));
                btnAbout.setBackgroundColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.view_bg));
                btnAbout.setTextColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.light_blue));
                llAbout.setVisibility(View.GONE);
                llContactInfo.setVisibility(View.VISIBLE);
            }
        });
        flBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorsResultDetailsActivity.this, UpgradeMedicalFlipActivity.class);
                intent.putExtra("activity", "DoctorVURVBannerActivity");
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
        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertDialog();
            }
        });
        llSavelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvSave.getText().toString().equalsIgnoreCase(getResources().getString(R.string.saved))) {
                    deleteSaveForLaterDoctors();
                } else {
                    saveForLaterDoctors();
                }
            }
        });

    }

    private void saveForLaterDoctors() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterDoctors> saveForLaterRequestList = new ArrayList();
        SaveForLaterDoctors saveForLaterRequest = new SaveForLaterDoctors();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setPractitionerProviderId(providerId);
        saveForLaterRequest.setCity(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getCity());
        saveForLaterRequest.setState(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getState());
        saveForLaterRequest.setZipCode(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getZipcode());
        saveForLaterRequest.setLocationKey(locationKey);
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setZipCode(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getZipcode());
        saveForLaterRequest.setCity(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getCity());
        saveForLaterRequest.setState(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getState());
        saveForLaterRequest.setLocationKey("");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDoctor(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                if (response.isSuccessful()) {
                    //ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                    imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
                    tvSave.setText(getResources().getString(R.string.saved));
                    /*if (activity.equalsIgnoreCase("DoctorSearchResult")) {
                        int i = 0;
                        while (i < MedicalScreenActivity.resPayloads.size()) {
                            if ((MedicalScreenActivity.resPayloads.get(i)).getProviderID().equalsIgnoreCase(providerId)) {
                                MedicalScreenActivity.resPayloads.get(i).setSavedStatus(1);
                                if (DoctorSearchResults.searchPractitionerResPayLoadsFilter != null) {
                                    (DoctorSearchResults.searchPractitionerResPayLoadsFilter.get(clickedPosition)).setSavedStatus(1);
                                }
                                sqLiteDbHelper.updateSavedStatusFlag("filter_doctor", 1, providerId);
                            } else {
                                i++;
                            }
                        }

                    }*/

                    if (activity.equalsIgnoreCase("DoctorSearchResult")) {
                        for (int i = 0; i< MedicalScreenActivity.resPayloads.size(); i++) {
                            if ((MedicalScreenActivity.resPayloads.get(i)).getProviderID().equalsIgnoreCase(providerId)){
                                MedicalScreenActivity.resPayloads.get(i).setSavedStatus(1);
                                if (DoctorSearchResults.searchPractitionerResPayLoadsFilter != null) {
                                    (DoctorSearchResults.searchPractitionerResPayLoadsFilter.get(clickedPosition)).setSavedStatus(1);
                                }
                                sqLiteDbHelper.updateSavedStatusFlag("filter_doctor", 1, providerId);
                                break;
                            }
                        }
//                    MedicalScreenActivity.resPayloadsForFacilities.get(clickedPosition).setSavedStatus(1);
                    }

                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterDoctors() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterDoctors> saveForLaterRequestList = new ArrayList();
        SaveForLaterDoctors saveForLaterRequest = new SaveForLaterDoctors();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setPractitionerProviderId(providerId);
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setZipCode(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getZipcode());
        saveForLaterRequest.setCity(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getCity());
        saveForLaterRequest.setState(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getState());
        saveForLaterRequest.setLocationKey(locationKey);
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDoctor(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                if (response.isSuccessful()) {
                    ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                    imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_star_ic));
                    tvSave.setTextColor(ContextCompat.getColor(DoctorsResultDetailsActivity.this, R.color.black));
                    tvSave.setText(getResources().getString(R.string.save_for_later));
                    /*if (activity.equalsIgnoreCase("DoctorSearchResult")) {
                        int i = 0;
                        while (i < MedicalScreenActivity.resPayloads.size()) {
                            if (( MedicalScreenActivity.resPayloads.get(i)).getProviderID().equalsIgnoreCase(providerId)) {
                                ( MedicalScreenActivity.resPayloads.get(i)).setSavedStatus(0);
                                if (DoctorSearchResults.searchPractitionerResPayLoadsFilter != null) {
                                    ( DoctorSearchResults.searchPractitionerResPayLoadsFilter.get(clickedPosition)).setSavedStatus(1);
                                }
                                sqLiteDbHelper.updateSavedStatusFlag("filter_doctor", 0, providerId);
                            } else {
                                i++;
                            }
                        }
                    }*/
                    if (activity.equalsIgnoreCase("DoctorSearchResult")) {
                        for (int i = 0; i< MedicalScreenActivity.resPayloads.size(); i++) {
                            if ((MedicalScreenActivity.resPayloads.get(i)).getProviderID().equalsIgnoreCase(providerId)){
                                MedicalScreenActivity.resPayloads.get(i).setSavedStatus(0);
                                if (DoctorSearchResults.searchPractitionerResPayLoadsFilter != null) {
                                    (DoctorSearchResults.searchPractitionerResPayLoadsFilter.get(clickedPosition)).setSavedStatus(0);
                                }
                                sqLiteDbHelper.updateSavedStatusFlag("filter_doctor", 0, providerId);
                                break;
                            }
                        }
//                    MedicalScreenActivity.resPayloadsForFacilities.get(clickedPosition).setSavedStatus(1);
                    }
                }
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
        if (!(aboutDoctorResPayLoads == null || ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLat() == null || ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLat().length() <= 0)) {
            latLng = new LatLng(Double.parseDouble(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLat()), Double.parseDouble(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLong()));
        }
        MarkerOptions markerOptions = new MarkerOptions();
        if (latLng != null) {
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_doctors_ic));
            mCurrLocationMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void customAlertDialog() {
        final Dialog customDialog = new Dialog(this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.more_options);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button save_contact = (Button) customDialog.findViewById(R.id.save_contact);
        Button email_doc = (Button) customDialog.findViewById(R.id.email_doc);
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        save_contact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestContactPermission();
                Toast.makeText(DoctorsResultDetailsActivity.this, getResources().getString(R.string.contact_saved), Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        email_doc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail();
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    private void getContact() {
        ContentValues values = new ContentValues();
        values.put("number", ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getMobileNo());
        values.put("type", Integer.valueOf(0));
        values.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getName());
        values.put("name", ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getName());
        Uri updateUri = Uri.withAppendedPath(getContentResolver().insert(Contacts.People.CONTENT_URI, values), "phones");
        values.clear();
        values.put("type", Integer.valueOf(2));
        values.put("number", ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getMobileNo());
        updateUri = getContentResolver().insert(updateUri, values);
    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)) {
                    final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Read Contacts permission");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.WRITE_CONTACTS}
                                    , 100);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_CONTACTS},
                            100);
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (permissions[0].equals(Manifest.permission.WRITE_CONTACTS)) {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getContact();
                    } else {
                        Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }
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
        startActivity(new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getMobileNo())));
    }

    public static String extractDigits(String in) {
        Matcher m = Pattern.compile("(\\d{5})").matcher(in);
        if (m.find()) {
            return m.group(0);
        }
        return "";
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (activity.equalsIgnoreCase("DoctorSearchResult")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, NoSavedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void getDoctorProviderDetails() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(providerId);
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getAboutDoctorsService(reqPayLoads).enqueue(new Callback<ArrayList<AboutDoctorResPayLoad>>() {
            public void onResponse(Call<ArrayList<AboutDoctorResPayLoad>> call, Response<ArrayList<AboutDoctorResPayLoad>> response) {
                if (response.isSuccessful()) {


                        aboutDoctorResPayLoads = response.body();

                        if (aboutDoctorResPayLoads != null && aboutDoctorResPayLoads.size() > 0) {

                            tvSpeciality.setText(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getSpeciality());
                            tvAffiliation.setText(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getHospitalAffiliation());
                            tvGenderType.setText(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getGender().equals("M") ? getResources().getString(R.string.male) : getResources().getString(R.string.female));
                            tvEduSchl.setText(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getClinicalEducation());
                            doctor_name.setText(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getName() + ", " + ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getClinicalEducation());
                            tvLanguageType.setText(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLanguage());
                            if (((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getPracAcceptNewPatients() == null) {
                                tvAcptType.setText("");
                            } else if (((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getPracAcceptNewPatients().equalsIgnoreCase("Y")) {
                                tvAcptType.setText("Yes");
                            } else {
                                tvAcptType.setText("No");
                            }
                            if (((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getPracADAaccessibilityFlag() == null) {
                                tvADAType.setText("");
                            } else if (((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getPracADAaccessibilityFlag().equalsIgnoreCase("Y")) {
                                tvADAType.setText("Yes");
                            } else {
                                tvADAType.setText("No");
                            }
                            mAdapter = new DoctorAdressAdapter(DoctorsResultDetailsActivity.this, aboutDoctorResPayLoads);
                            rvAddress.setAdapter(mAdapter);
                            if (!(aboutDoctorResPayLoads == null || ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLat() == null || ((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLat().length() <= 0)) {
                                latLng = new LatLng(Double.parseDouble(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLat()), Double.parseDouble(((AboutDoctorResPayLoad) aboutDoctorResPayLoads.get(0)).getLong()));
                            }
                            MarkerOptions markerOptions = new MarkerOptions();
                            if (latLng != null) {
                                markerOptions.position(latLng);
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_doctors_ic));
                                mCurrLocationMarker = mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                            }
                        }
                       dismissProgressDialog();

                }
            }

            public void onFailure(Call<ArrayList<AboutDoctorResPayLoad>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }
}
