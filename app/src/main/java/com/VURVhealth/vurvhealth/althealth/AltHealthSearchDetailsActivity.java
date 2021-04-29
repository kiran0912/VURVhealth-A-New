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

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$1 */
    class C03001 implements View.OnClickListener {
        C03001() {
        }

        public void onClick(View v) {
            new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + AltHealthSearchDetailsActivity.this.mCurrentListing.getPhoneNumber()));
            try {
                AltHealthSearchDetailsActivity.this.onCall();
            } catch (Exception e) {
                Log.v("Call>>", e.getMessage());
            }
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$2 */
    class C03012 implements View.OnClickListener {
        C03012() {
        }

        public void onClick(View v) {
            if (AltHealthSearchDetailsActivity.this.tvSave.getText().toString().equalsIgnoreCase(AltHealthSearchDetailsActivity.this.getResources().getString(R.string.saved))) {
                AltHealthSearchDetailsActivity.this.deleteSaveForLaterService();
            } else {
                AltHealthSearchDetailsActivity.this.saveForLaterAHSService();
            }
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$3 */
    class C03023 implements View.OnClickListener {
        C03023() {
        }

        public void onClick(View v) {
            if (AltHealthSearchDetailsActivity.this.activity.equalsIgnoreCase("AHSListActivity")) {
                AltHealthSearchDetailsActivity.this.finish();
                return;
            }
            AltHealthSearchDetailsActivity.this.startActivity(new Intent(AltHealthSearchDetailsActivity.this, NoSavedItemActivity.class));
            AltHealthSearchDetailsActivity.this.finish();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$4 */
    class C03034 implements View.OnClickListener {
        C03034() {
        }

        @TargetApi(23)
        public void onClick(View v) {
            AltHealthSearchDetailsActivity.this.btnAbout.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
            AltHealthSearchDetailsActivity.this.btnAbout.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
            AltHealthSearchDetailsActivity.this.btnContactInfo.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
            AltHealthSearchDetailsActivity.this.btnContactInfo.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
            AltHealthSearchDetailsActivity.this.llAbout.setVisibility(View.VISIBLE);
            AltHealthSearchDetailsActivity.this.llContactInfo.setVisibility(View.GONE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$5 */
    class C03045 implements View.OnClickListener {
        C03045() {
        }

        @TargetApi(23)
        public void onClick(View v) {
            AltHealthSearchDetailsActivity.this.btnContactInfo.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
            AltHealthSearchDetailsActivity.this.btnContactInfo.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
            AltHealthSearchDetailsActivity.this.btnAbout.setBackgroundColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.view_bg));
            AltHealthSearchDetailsActivity.this.btnAbout.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
            AltHealthSearchDetailsActivity.this.llAbout.setVisibility(View.GONE);
            AltHealthSearchDetailsActivity.this.llContactInfo.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$6 */
    class C03056 implements View.OnClickListener {
        C03056() {
        }

        public void onClick(View v) {
            AltHealthSearchDetailsActivity.this.startActivity(new Intent(AltHealthSearchDetailsActivity.this, AHSVURVBannerActivity.class));
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$7 */
    class C03067 implements View.OnClickListener {
        C03067() {
        }

        public void onClick(View v) {
            AltHealthSearchDetailsActivity.this.startActivity(new Intent(AltHealthSearchDetailsActivity.this, AHSVURVBannerActivity.class));
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$8 */
    class C03078 implements View.OnClickListener {
        C03078() {
        }

        public void onClick(View v) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(HTTP.PLAIN_TEXT_TYPE);
            intent.putExtra("android.intent.extra.TEXT", "http://www.vurvhealth.com/");
            AltHealthSearchDetailsActivity.this.startActivity(Intent.createChooser(intent, AltHealthSearchDetailsActivity.this.getResources().getString(R.string.share_with)));
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.althealth.AltHealthSearchDetailsActivity$9 */
    class C03089 implements View.OnClickListener {
        C03089() {
        }

        public void onClick(View view) {
            AltHealthSearchDetailsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + AltHealthSearchDetailsActivity.this.mCurrentListing.getLatitude() + "," + AltHealthSearchDetailsActivity.this.mCurrentListing.getLongitude())));
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_deatails_althealth);
        this.prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                this.mCurrentListing = (AHSProviderDetailsRespayload) b.getParcelable("SearchResultObject");
                this.clickedPosition = b.getInt("position");
                this.activity = b.getString("activity");
                this.savedItemStatus = b.getInt("savedItem");
            }
        } catch (Exception e) {
        }
        this.backBtn = (ImageView) findViewById(R.id.backBtn);
        this.imgSave = (ImageView) findViewById(R.id.imgSave);
        this.btnContactInfo = (Button) findViewById(R.id.btnContactInfo);
        this.btnAbout = (Button) findViewById(R.id.btnAbout);
        this.llAbout = (LinearLayout) findViewById(R.id.llAbout);
        this.llContactInfo = (LinearLayout) findViewById(R.id.llContactInfo);
        this.ll_direction = (LinearLayout) findViewById(R.id.ll_direction);
        this.llShare = (LinearLayout) findViewById(R.id.llShare);
        this.ll_call = (LinearLayout) findViewById(R.id.ll_call);
        this.flBanner1 = (FrameLayout) findViewById(R.id.flBanner);
        this.flBanner2 = (FrameLayout) findViewById(R.id.flBanner2);
        this.ll_save = (LinearLayout) findViewById(R.id.ll_save);
        this.tvSave = (TextView) findViewById(R.id.tvSave);
        this.tvSpeciality = (TextView) findViewById(R.id.tvSpeciality);
        this.tvClinicName = (TextView) findViewById(R.id.tvClinicName);
        this.tvDegree = (TextView) findViewById(R.id.tvDegree);
        this.doctor_name = (TextView) findViewById(R.id.doctor_name);
        this.tvAddress = (TextView) findViewById(R.id.tvAddress);
        this.tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        if (this.savedItemStatus == 1) {
            this.imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            this.tvSave.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            this.tvSave.setText(getResources().getString(R.string.saved));
        }
        this.doctor_name.setText(this.mCurrentListing.getName() + ", " + this.mCurrentListing.getDegree());
        this.tvSpeciality.setText(this.mCurrentListing.getSpecialty());
        this.tvClinicName.setText(this.mCurrentListing.getClinicName());
        this.tvDegree.setText(this.mCurrentListing.getProgramDegree());
        this.tvAddress.setText(this.mCurrentListing.getAddress());
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(this.mCurrentListing.getPhoneNumber().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(this.mCurrentListing.getPhoneNumber().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(this.mCurrentListing.getPhoneNumber().substring(6, 10));
            this.tvPhoneNumber.setText(stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.tvPhoneNumber.setText(this.mCurrentListing.getPhoneNumber());
        }
        if (this.prefsLoginData.getString("search_type", "").contains("Alt Health")) {
            this.flBanner1.setVisibility(View.VISIBLE);
            this.flBanner2.setVisibility(View.VISIBLE);
        } else {
            this.flBanner1.setVisibility(View.GONE);
            this.flBanner2.setVisibility(View.GONE);
        }
        this.ll_call.setOnClickListener(new C03001());
        this.ll_save.setOnClickListener(new C03012());
        this.backBtn.setOnClickListener(new C03023());
        this.btnAbout.setOnClickListener(new C03034());
        this.btnContactInfo.setOnClickListener(new C03045());
        this.flBanner1.setOnClickListener(new C03056());
        this.flBanner2.setOnClickListener(new C03067());
        this.llShare.setOnClickListener(new C03078());
        this.ll_direction.setOnClickListener(new C03089());
    }

    private void saveForLaterAHSService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setProviderId(this.mCurrentListing.getAHSProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAHS(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                AltHealthSearchDetailsActivity.this.dismissProgressDialog();
                Toast.makeText(AltHealthSearchDetailsActivity.this.getApplicationContext(), AltHealthSearchDetailsActivity.this.getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
                AltHealthSearchDetailsActivity.this.imgSave.setImageDrawable(AltHealthSearchDetailsActivity.this.getResources().getDrawable(R.drawable.toolbar_saved_ic));
                AltHealthSearchDetailsActivity.this.tvSave.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.light_blue));
                AltHealthSearchDetailsActivity.this.tvSave.setText(AltHealthSearchDetailsActivity.this.getResources().getString(R.string.saved));
                if (AltHealthSearchDetailsActivity.this.activity.equalsIgnoreCase("AHSListActivity")) {
                    ( AltHealthScreenActivity.resPayloads.get(AltHealthSearchDetailsActivity.this.clickedPosition)).setSavedStatus(1);
                }
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(AltHealthSearchDetailsActivity.this.getApplicationContext(), AltHealthSearchDetailsActivity.this.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                AltHealthSearchDetailsActivity.this.dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(this.mCurrentListing.getAHSProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAHS(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                AltHealthSearchDetailsActivity.this.imgSave.setImageDrawable(AltHealthSearchDetailsActivity.this.getResources().getDrawable(R.drawable.toolbar_star_ic));
                AltHealthSearchDetailsActivity.this.tvSave.setTextColor(ContextCompat.getColor(AltHealthSearchDetailsActivity.this, R.color.black));
                AltHealthSearchDetailsActivity.this.tvSave.setText(AltHealthSearchDetailsActivity.this.getResources().getString(R.string.save_for_later));
                if (AltHealthSearchDetailsActivity.this.activity.equalsIgnoreCase("AHSListActivity")) {
                    ( AltHealthScreenActivity.resPayloads.get(AltHealthSearchDetailsActivity.this.clickedPosition)).setSavedStatus(0);
                }
                Toast.makeText(AltHealthSearchDetailsActivity.this.getApplicationContext(), ((StatusResponseForTotalProject) ((ArrayList) response.body()).get(0)).getStatus(), Toast.LENGTH_SHORT).show();
                AltHealthSearchDetailsActivity.this.dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(AltHealthSearchDetailsActivity.this.getApplicationContext(), AltHealthSearchDetailsActivity.this.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                AltHealthSearchDetailsActivity.this.dismissProgressDialog();
            }
        });
    }

    public void onConnected(@Nullable Bundle bundle) {
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(1000);
        this.mLocationRequest.setFastestInterval(1000);
        this.mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
        }
    }

    protected void showProgressDialog(Context context) {
        this.pDialog = new ProgressDialog(context);
        this.pDialog.setMessage(getResources().getString(R.string.please_wait));
        this.pDialog.setCancelable(false);
        this.pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (this.pDialog != null) {
            this.pDialog.dismiss();
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setMyLocationEnabled(false);
        this.mMap.setMapType(1);
        if (Build.VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            this.mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            this.mMap.setMyLocationEnabled(true);
        }
        if (this.mCurrentListing.getLatitude() != null && this.mCurrentListing.getLatitude().length() > 0) {
            this.latLng = new LatLng(Double.parseDouble(this.mCurrentListing.getLatitude()), Double.parseDouble(this.mCurrentListing.getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(this.latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_alt));
            this.mCurrLocationMarker = this.mMap.addMarker(markerOptions);
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(this.latLng));
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        this.mGoogleApiClient.connect();
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
        startActivity(new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + this.mCurrentListing.getPhoneNumber())));
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.activity.equalsIgnoreCase("AHSListActivity")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, NoSavedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
