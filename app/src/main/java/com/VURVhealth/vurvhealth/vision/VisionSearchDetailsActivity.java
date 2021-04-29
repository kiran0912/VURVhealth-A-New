package com.VURVhealth.vurvhealth.vision;

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
import android.location.Location;
import android.net.Uri;
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

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$1 */
    class C08271 implements OnClickListener {
        C08271() {
        }

        public void onClick(View v) {
            new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + VisionSearchDetailsActivity.this.mCurrentListing.getPhone()));
            try {
                VisionSearchDetailsActivity.this.onCall();
            } catch (Exception e) {
                Log.v("Call>>", e.getMessage());
            }
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$2 */
    class C08282 implements OnClickListener {
        C08282() {
        }

        public void onClick(View v) {
            if (VisionSearchDetailsActivity.this.activity.equalsIgnoreCase("VisionListActivity")) {
                VisionSearchDetailsActivity.this.finish();
                return;
            }
            VisionSearchDetailsActivity.this.startActivity(new Intent(VisionSearchDetailsActivity.this, NoSavedItemActivity.class));
            VisionSearchDetailsActivity.this.finish();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$3 */
    class C08293 implements OnClickListener {
        C08293() {
        }

        public void onClick(View v) {
            VisionSearchDetailsActivity.this.btnAbout.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
            VisionSearchDetailsActivity.this.btnAbout.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
            VisionSearchDetailsActivity.this.btnContactInfo.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
            VisionSearchDetailsActivity.this.btnContactInfo.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
            VisionSearchDetailsActivity.this.llAbout.setVisibility(View.VISIBLE);
            VisionSearchDetailsActivity.this.llContactInfo.setVisibility(View.GONE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$4 */
    class C08304 implements OnClickListener {
        C08304() {
        }

        public void onClick(View v) {
            VisionSearchDetailsActivity.this.btnContactInfo.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
            VisionSearchDetailsActivity.this.btnContactInfo.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
            VisionSearchDetailsActivity.this.btnAbout.setBackgroundColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.view_bg));
            VisionSearchDetailsActivity.this.btnAbout.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
            VisionSearchDetailsActivity.this.llAbout.setVisibility(View.GONE);
            VisionSearchDetailsActivity.this.llContactInfo.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$5 */
    class C08315 implements OnClickListener {
        C08315() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(VisionSearchDetailsActivity.this, VisionVURVBannerActivity.class);
            intent.putExtra("activity", "visionScreen");
            VisionSearchDetailsActivity.this.startActivity(intent);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$6 */
    class C08326 implements OnClickListener {
        C08326() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(VisionSearchDetailsActivity.this, VisionVURVBannerActivity.class);
            intent.putExtra("activity", "visionScreen");
            VisionSearchDetailsActivity.this.startActivity(intent);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$7 */
    class C08337 implements OnClickListener {
        C08337() {
        }

        public void onClick(View v) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(HTTP.PLAIN_TEXT_TYPE);
            intent.putExtra("android.intent.extra.TEXT", "http://www.vurvhealth.com/");
            VisionSearchDetailsActivity.this.startActivity(Intent.createChooser(intent, VisionSearchDetailsActivity.this.getResources().getString(R.string.share_with)));
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$8 */
    class C08348 implements OnClickListener {
        C08348() {
        }

        public void onClick(View view) {
            VisionSearchDetailsActivity.this.customAlertDialog();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionSearchDetailsActivity$9 */
    class C08359 implements OnClickListener {
        C08359() {
        }

        public void onClick(View view) {
            if (VisionSearchDetailsActivity.this.tvSave.getText().toString().equalsIgnoreCase(VisionSearchDetailsActivity.this.getResources().getString(R.string.saved))) {
                VisionSearchDetailsActivity.this.deleteSaveForLaterVisionService();
            } else {
                VisionSearchDetailsActivity.this.saveForLaterVisionService();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_search_details);
        this.prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                this.mCurrentListing = (VisionProviderIdResPayload) b.getParcelable("SearchResultObject");
                this.clickedPosition = b.getInt("position");
                this.savedItem = b.getInt("savedItem");
                this.activity = b.getString("activity");
                latitude = b.getDouble("latitude");
                longitude = b.getDouble("longitude");

            }
        } catch (Exception e) {
        }
        this.backBtn = (ImageView) findViewById(R.id.backBtn);
        this.imgSave = (ImageView) findViewById(R.id.imgSave);
        this.btnContactInfo = (Button) findViewById(R.id.btnContactInfo);
        this.btnAbout = (Button) findViewById(R.id.btnAbout);
        this.llAbout = (LinearLayout) findViewById(R.id.llAbout);
        this.llContactInfo = (LinearLayout) findViewById(R.id.llContactInfo);
        this.ll_more = (LinearLayout) findViewById(R.id.ll_more);
        this.llShare = (LinearLayout) findViewById(R.id.llShare);
        this.ll_call = (LinearLayout) findViewById(R.id.ll_call);
        this.llSavelater = (LinearLayout) findViewById(R.id.llSavelater);
        this.flBanner = (FrameLayout) findViewById(R.id.flBanner);
        this.flBanner2 = (FrameLayout) findViewById(R.id.flBanner2);
        this.doctor_specialty = (TextView) findViewById(R.id.doctor_specialty);
        this.tvName = (TextView) findViewById(R.id.tvName);
        this.tvDocLanguage = (TextView) findViewById(R.id.tvDocLanguage);
        this.tvOfcLanguage = (TextView) findViewById(R.id.tvOfcLanguage);
        this.tvDocSpecial = (TextView) findViewById(R.id.tvDocSpecial);
        this.tvOfcSpecial = (TextView) findViewById(R.id.tvOfcSpecial);
        this.tvSave = (TextView) findViewById(R.id.tvSave);
        this.tvStreet = (TextView) findViewById(R.id.tvStreet);
        this.tvCity = (TextView) findViewById(R.id.tvCity);
        this.tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        this.tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        this.tvMonDoc = (TextView) findViewById(R.id.tvMonDoc);
        this.tvTueDoc = (TextView) findViewById(R.id.tvTueDoc);
        this.tvWedDoc = (TextView) findViewById(R.id.tvWedDoc);
        this.tvThuDoc = (TextView) findViewById(R.id.tvThuDoc);
        this.tvFriDoc = (TextView) findViewById(R.id.tvFriDoc);
        this.tvSatDoc = (TextView) findViewById(R.id.tvSatDoc);
        this.tvSunDoc = (TextView) findViewById(R.id.tvSunDoc);
        this.tvMonofc = (TextView) findViewById(R.id.tvMonofc);
        this.tvTueofc = (TextView) findViewById(R.id.tvTueofc);
        this.tvWedofc = (TextView) findViewById(R.id.tvWedofc);
        this.tvThuofc = (TextView) findViewById(R.id.tvThuofc);
        this.tvFriofc = (TextView) findViewById(R.id.tvFriofc);
        this.tvSatofc = (TextView) findViewById(R.id.tvSatofc);
        this.tvSunofc = (TextView) findViewById(R.id.tvSunofc);

        this.tvGenderType = (TextView) findViewById(R.id.tvGenderType);

        this.tvGenderType.setText(this.mCurrentListing.getGender());

        this.doctor_specialty.setText(this.mCurrentListing.getFullName());
        this.tvName.setText(this.mCurrentListing.getPracticeName());
        this.tvDocLanguage.setText(this.mCurrentListing.getDoctarLanguage());
        this.tvOfcLanguage.setText(this.mCurrentListing.getOfficeLanguage());
        this.tvDocSpecial.setText(this.mCurrentListing.getDoctorSpecialInterestCode());
        this.tvOfcSpecial.setText(this.mCurrentListing.getOfficeSpecialInterestCode());
        this.tvStreet.setText(this.mCurrentListing.getAddressLine1());
        this.tvCity.setText(this.mCurrentListing.getCity() + ", " + this.mCurrentListing.getState()+", "+this.mCurrentListing.getZipCode());
        tvZipcode.setVisibility(View.GONE);
        this.tvZipcode.setText(this.mCurrentListing.getZipCode());
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(this.mCurrentListing.getPhone().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(this.mCurrentListing.getPhone().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(this.mCurrentListing.getPhone().substring(6, 10));
            this.tvPhoneNumber.setText(stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.tvPhoneNumber.setText(this.mCurrentListing.getPhone());
        }
        this.tvMonDoc.setText(this.mCurrentListing.getDoctorMondayHours());
        this.tvTueDoc.setText(this.mCurrentListing.getDoctorTuesdayHours());
        this.tvWedDoc.setText(this.mCurrentListing.getDoctorWednesDayHours());
        this.tvThuDoc.setText(this.mCurrentListing.getDoctorThursDayHours());
        this.tvFriDoc.setText(this.mCurrentListing.getDoctorFridayHours());
        this.tvSatDoc.setText(this.mCurrentListing.getDoctorSaturdayHours());
        this.tvSunDoc.setText(this.mCurrentListing.getDoctorSundayHours());
        this.tvMonofc.setText(this.mCurrentListing.getOfficeMondayHours());
        this.tvTueofc.setText(this.mCurrentListing.getOfficeTuesdayHours());
        this.tvWedofc.setText(this.mCurrentListing.getOfficeWednesdayHours());
        this.tvThuofc.setText(this.mCurrentListing.getOfficeThursdayHours());
        this.tvFriofc.setText(this.mCurrentListing.getOfficeFridayHours());
        this.tvSatofc.setText(this.mCurrentListing.getOfficeSaturdayHours());
        this.tvSunofc.setText(this.mCurrentListing.getOfficeSundayHours());
        if (this.savedItem == 1) {
            this.imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            this.tvSave.setTextColor(ContextCompat.getColor(this, R.color.light_blue));
            this.tvSave.setText(getResources().getString(R.string.saved));
        }
        if (this.prefsLoginData.getString("search_type", "").contains("Vision")) {
            this.flBanner.setVisibility(View.VISIBLE);
            this.flBanner2.setVisibility(View.VISIBLE);
        } else {
            this.flBanner.setVisibility(View.GONE);
            this.flBanner2.setVisibility(View.GONE);
        }
        this.ll_call.setOnClickListener(new C08271());
        this.backBtn.setOnClickListener(new C08282());
        this.btnAbout.setOnClickListener(new C08293());
        this.btnContactInfo.setOnClickListener(new C08304());
        this.flBanner.setOnClickListener(new C08315());
        this.flBanner2.setOnClickListener(new C08326());
        this.llShare.setOnClickListener(new C08337());
        this.ll_more.setOnClickListener(new C08348());
        this.llSavelater.setOnClickListener(new C08359());
    }

    private void saveForLaterVisionService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setProviderId(this.mCurrentListing.getVisProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterVision(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                VisionSearchDetailsActivity.this.dismissProgressDialog();
                Toast.makeText(VisionSearchDetailsActivity.this.getApplicationContext(), VisionSearchDetailsActivity.this.getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
                VisionSearchDetailsActivity.this.imgSave.setImageDrawable(VisionSearchDetailsActivity.this.getResources().getDrawable(R.drawable.toolbar_saved_ic));
                VisionSearchDetailsActivity.this.tvSave.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.light_blue));
                VisionSearchDetailsActivity.this.tvSave.setText(VisionSearchDetailsActivity.this.getResources().getString(R.string.saved));
                if (VisionSearchDetailsActivity.this.activity.equalsIgnoreCase("VisionListActivity")) {
                    ((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(VisionSearchDetailsActivity.this.clickedPosition)).setSavedStatus(1);
                }
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(VisionSearchDetailsActivity.this.getApplicationContext(), VisionSearchDetailsActivity.this.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                VisionSearchDetailsActivity.this.dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterVisionService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(this.mCurrentListing.getVisProviderId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterVision(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                VisionSearchDetailsActivity.this.imgSave.setImageDrawable(VisionSearchDetailsActivity.this.getResources().getDrawable(R.drawable.toolbar_star_ic));
                VisionSearchDetailsActivity.this.tvSave.setTextColor(ContextCompat.getColor(VisionSearchDetailsActivity.this, R.color.black));
                VisionSearchDetailsActivity.this.tvSave.setText(VisionSearchDetailsActivity.this.getResources().getString(R.string.save_for_later));
                if (VisionSearchDetailsActivity.this.activity.equalsIgnoreCase("VisionListActivity")) {
                    ((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(VisionSearchDetailsActivity.this.clickedPosition)).setSavedStatus(0);
                }
                Toast.makeText(VisionSearchDetailsActivity.this.getApplicationContext(), ((StatusResponseForTotalProject) ((ArrayList) response.body()).get(0)).getStatus(), Toast.LENGTH_SHORT).show();
                VisionSearchDetailsActivity.this.dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(VisionSearchDetailsActivity.this.getApplicationContext(), VisionSearchDetailsActivity.this.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                VisionSearchDetailsActivity.this.dismissProgressDialog();
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

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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

    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setMyLocationEnabled(false);
        this.mMap.setMapType(1);
        if (VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            this.mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            this.mMap.setMyLocationEnabled(true);
        }
        latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_vision_ic));
        this.mCurrLocationMarker = this.mMap.addMarker(markerOptions);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        this.mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        this.mGoogleApiClient.connect();
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
                VisionSearchDetailsActivity.this.askForContactPermission();
                Toast.makeText(VisionSearchDetailsActivity.this, VisionSearchDetailsActivity.this.getResources().getString(R.string.contact_saved), Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        email_doc.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VisionSearchDetailsActivity.this.sendEmail();
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    private void getContact() {
        ContentValues values = new ContentValues();
        values.put("number", this.mCurrentListing.getPhone());
        values.put("type", Integer.valueOf(0));
        values.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, this.mCurrentListing.getFullName());
        values.put("name", this.mCurrentListing.getFullName());
        Uri updateUri = Uri.withAppendedPath(getContentResolver().insert(People.CONTENT_URI, values), "phones");
        values.clear();
        values.put("type", Integer.valueOf(2));
        values.put("number", this.mCurrentListing.getPhone());
        updateUri = getContentResolver().insert(updateUri, values);
    }

    public void askForContactPermission() {
        if (VERSION.SDK_INT < 23) {
            getContact();
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") == 0) {
            getContact();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_CONTACTS")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Contacts access needed");
            builder.setPositiveButton(R.string.ok, null);
            builder.setMessage("Please confirm Contacts access");
            builder.setOnDismissListener(new OnDismissListener() {
                @TargetApi(23)
                public void onDismiss(DialogInterface dialog) {
                    VisionSearchDetailsActivity.this.requestPermissions(new String[]{"android.permission.READ_CONTACTS"}, 100);
                }
            });
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 100);
        }
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
        startActivity(new Intent("android.intent.action.CALL").setData(Uri.parse("tel:" + this.mCurrentListing.getPhone())));
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.activity.equalsIgnoreCase("VisionListActivity")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, NoSavedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
