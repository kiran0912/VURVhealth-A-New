package com.VURVhealth.vurvhealth.vision;

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
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeVisionFlipActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.utilities.Utility;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionProviderIdResPayload;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
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
import java.util.ArrayList;
import org.apache.http.protocol.HTTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionSearchDetailsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int PERMISSION_REQUEST_CONTACT = 100;
    private String activity = "";
    private ImageView backBtn;
    private Button btnAbout;
    private Button btnContactInfo;
    private int clickedPosition;
    private TextView doctor_specialty;
    private FrameLayout flBanner;
    private FrameLayout flBanner2;
    private ImageView imgSave;
    private LatLng latLng;
    private Double latitude, longitude;
    private LinearLayout llAbout;
    private LinearLayout llContactInfo;
    private LinearLayout llSavelater;
    private LinearLayout llShare;
    private LinearLayout ll_call;
    private LinearLayout ll_more;
    private LinearLayout ll_direction;
    private Marker mCurrLocationMarker;
    private VisionProviderIdResPayload mCurrentListing;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private ProgressDialog pDialog;
    public SharedPreferences prefsLoginData;
    private int savedItem;
    private TextView tvCity;
    private TextView tvDocLanguage;
    private TextView tvDocSpecial;
    private TextView tvFriDoc;
    private TextView tvFriofc;
    private TextView tvMonDoc;
    private TextView tvMonofc;
    private TextView tvName;
    private TextView tvOfcLanguage;
    private TextView tvOfcSpecial;
    private TextView tvPhoneNumber;
    private TextView tvSatDoc;
    private TextView tvSatofc;
    private TextView tvSave;
    private TextView tvStreet;
    private TextView tvSunDoc;
    private TextView tvSunofc;
    private TextView tvThuDoc;
    private TextView tvThuofc;
    private TextView tvTueDoc;
    private TextView tvTueofc;
    private TextView tvWedDoc;
    private TextView tvWedofc;
    private TextView tvZipcode;
    private TextView tvGenderType;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_search_details);
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                mCurrentListing = (VisionProviderIdResPayload) b.getParcelable("SearchResultObject");
                clickedPosition = b.getInt("position");
                savedItem = b.getInt("savedItem");
                activity = b.getString("activity");
                latitude = b.getDouble("latitude");
                longitude = b.getDouble("longitude");

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
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        llShare = (LinearLayout) findViewById(R.id.llShare);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        llSavelater = (LinearLayout) findViewById(R.id.llSavelater);
        flBanner = (FrameLayout) findViewById(R.id.flBanner);
        flBanner2 = (FrameLayout) findViewById(R.id.flBanner2);
        doctor_specialty = (TextView) findViewById(R.id.doctor_specialty);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDocLanguage = (TextView) findViewById(R.id.tvDocLanguage);
        tvOfcLanguage = (TextView) findViewById(R.id.tvOfcLanguage);
        tvDocSpecial = (TextView) findViewById(R.id.tvDocSpecial);
        tvOfcSpecial = (TextView) findViewById(R.id.tvOfcSpecial);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvStreet = (TextView) findViewById(R.id.tvStreet);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvMonDoc = (TextView) findViewById(R.id.tvMonDoc);
        tvTueDoc = (TextView) findViewById(R.id.tvTueDoc);
        tvWedDoc = (TextView) findViewById(R.id.tvWedDoc);
        tvThuDoc = (TextView) findViewById(R.id.tvThuDoc);
        tvFriDoc = (TextView) findViewById(R.id.tvFriDoc);
        tvSatDoc = (TextView) findViewById(R.id.tvSatDoc);
        tvSunDoc = (TextView) findViewById(R.id.tvSunDoc);
        tvMonofc = (TextView) findViewById(R.id.tvMonofc);
        tvTueofc = (TextView) findViewById(R.id.tvTueofc);
        tvWedofc = (TextView) findViewById(R.id.tvWedofc);
        tvThuofc = (TextView) findViewById(R.id.tvThuofc);
        tvFriofc = (TextView) findViewById(R.id.tvFriofc);
        tvSatofc = (TextView) findViewById(R.id.tvSatofc);
        tvSunofc = (TextView) findViewById(R.id.tvSunofc);

        tvGenderType = (TextView) findViewById(R.id.tvGenderType);

        tvGenderType.setText(mCurrentListing.getGender());

        doctor_specialty.setText(mCurrentListing.getFullName());
        tvName.setText(mCurrentListing.getPracticeName());
        tvDocLanguage.setText(mCurrentListing.getDoctarLanguage());
        tvOfcLanguage.setText(mCurrentListing.getOfficeLanguage());
        tvDocSpecial.setText(mCurrentListing.getDoctorSpecialInterestCode());
        tvOfcSpecial.setText(mCurrentListing.getOfficeSpecialInterestCode());
        tvStreet.setText(mCurrentListing.getAddressLine1());
        tvCity.setText(mCurrentListing.getCity() + ", " + mCurrentListing.getState()+", "+mCurrentListing.getZipCode());
        tvZipcode.setVisibility(View.GONE);
        tvZipcode.setText(mCurrentListing.getZipCode());
        try {
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
        }
        tvMonDoc.setText(mCurrentListing.getDoctorMondayHours());
        tvTueDoc.setText(mCurrentListing.getDoctorTuesdayHours());
        tvWedDoc.setText(mCurrentListing.getDoctorWednesDayHours());
        tvThuDoc.setText(mCurrentListing.getDoctorThursDayHours());
        tvFriDoc.setText(mCurrentListing.getDoctorFridayHours());
        tvSatDoc.setText(mCurrentListing.getDoctorSaturdayHours());
        tvSunDoc.setText(mCurrentListing.getDoctorSundayHours());
        tvMonofc.setText(mCurrentListing.getOfficeMondayHours());
        tvTueofc.setText(mCurrentListing.getOfficeTuesdayHours());
        tvWedofc.setText(mCurrentListing.getOfficeWednesdayHours());
        tvThuofc.setText(mCurrentListing.getOfficeThursdayHours());
        tvFriofc.setText(mCurrentListing.getOfficeFridayHours());
        tvSatofc.setText(mCurrentListing.getOfficeSaturdayHours());
        tvSunofc.setText(mCurrentListing.getOfficeSundayHours());
        if (savedItem == 1) {
            imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            tvSave.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            tvSave.setText(getResources().getString(R.string.saved));
        }
        if (prefsLoginData.getString("search_type", "").contains("Vision")) {
            flBanner.setVisibility(View.VISIBLE);
            flBanner2.setVisibility(View.VISIBLE);
        } else {
            flBanner.setVisibility(View.GONE);
            flBanner2.setVisibility(View.GONE);
        }
        ll_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + mCurrentListing.getPhone()));
                try {
                    onCall();
                } catch (Exception e) {
                    Log.v("Call>>", e.getMessage());
                }
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equalsIgnoreCase("VisionListActivity")) {
                    finish();
                    return;
                }
                startActivity(new Intent(VisionSearchDetailsActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        btnAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAbout.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
                btnAbout.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
                btnContactInfo.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
                btnContactInfo.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
                llAbout.setVisibility(View.VISIBLE);
                llContactInfo.setVisibility(View.GONE);
            }
        });
        btnContactInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnContactInfo.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
                btnContactInfo.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
                btnAbout.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
                btnAbout.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
                llAbout.setVisibility(View.GONE);
                llContactInfo.setVisibility(View.VISIBLE);
            }
        });
        flBanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisionSearchDetailsActivity.this, UpgradeVisionFlipActivity.class);
                intent.putExtra("activity", "VisionVURVBannerActivity");
                startActivity(intent);
            }
        });
        flBanner2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisionSearchDetailsActivity.this, UpgradeVisionFlipActivity.class);
                intent.putExtra("activity", "VisionVURVBannerActivity");
                startActivity(intent);
            }
        });
        llShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.putExtra("android.intent.extra.TEXT", "http://www.vurvhealth.com/");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_with)));
            }
        });
        ll_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertDialog();
            }
        });
        llSavelater.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvSave.getText().toString().equalsIgnoreCase(getResources().getString(R.string.saved))) {
                    deleteSaveForLaterVisionService();
                } else {
                    saveForLaterVisionService();
                }
            }
        });
        ll_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude)));
            }
        });
    }

    private void saveForLaterVisionService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setProviderId(mCurrentListing.getVisProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterVision(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
                tvSave.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
                tvSave.setText(getResources().getString(R.string.saved));
                if (activity.equalsIgnoreCase("VisionListActivity")) {
                    ((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(clickedPosition)).setSavedStatus(1);
                }
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterVisionService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(mCurrentListing.getVisProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterVision(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_star_ic));
                tvSave.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.black));
                tvSave.setText(getResources().getString(R.string.save_for_later));
                if (activity.equalsIgnoreCase("VisionListActivity")) {
                    ((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(clickedPosition)).setSavedStatus(0);
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

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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
        latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_vision_ic));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void customAlertDialog() {
        final Dialog customDialog = new Dialog(this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.more_options);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button save_contact = (Button) customDialog.findViewById(R.id.save_contact);
        Button email_doc = (Button) customDialog.findViewById(R.id.email_doc);
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        save_contact.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                requestContactPermission();
                customDialog.dismiss();

            }
        });
        email_doc.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendEmail();
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    private void getContact() {
        ContentValues values = new ContentValues();
        values.put("number", mCurrentListing.getPhone());
        values.put("type", Integer.valueOf(0));
        values.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, mCurrentListing.getFullName());
        values.put("name", mCurrentListing.getFullName());
        Uri updateUri = Uri.withAppendedPath(getContentResolver().insert(People.CONTENT_URI, values), "phones");
        values.clear();
        values.put("type", Integer.valueOf(2));
        values.put("number", mCurrentListing.getPhone());
        Toast.makeText(VisionSearchDetailsActivity.this, getResources().getString(R.string.contact_saved), Toast.LENGTH_SHORT).show();
        updateUri = getContentResolver().insert(updateUri, values);
    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        startActivity(new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + mCurrentListing.getPhone())));
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (activity.equalsIgnoreCase("VisionListActivity")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, NoSavedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
