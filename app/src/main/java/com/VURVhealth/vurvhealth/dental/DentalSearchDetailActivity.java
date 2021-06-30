package com.VURVhealth.vurvhealth.dental;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.upgrade.UpgradeDentalFlipActivity;
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
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.dental.pojos.DentalProviderDetailsResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 18/3/17.
 */

public class DentalSearchDetailActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    //    private LinearLayout llAbout, llContactInfo, llShare, ll_more, ll_call;
    private FrameLayout flBanner1, flBanner2;
    private LinearLayout llAbout, llContactInfo, llShare, ll_more, ll_call, ll_save, ll_direction;
    ;
    //    private Button flBanner1, flBanner2;
    double latitude;
    double longitude;
    private ProgressDialog pDialog;
    private DentalProviderDetailsResPayload mCurrentListing;


    private ImageView backBtn, imgSave;
    private Button btnAbout, btnContactInfo;
    private TextView tvDentalFacility, tvGenderType, tvCenter, tvStatus, tvCode, tvPracticeType,
            doctor_name, tvLanguageType, tvStreet, tvCity, tvZipcode, tvPhoneNumber, tvSave;
    private LatLng latLng;
    private static final int PERMISSION_REQUEST_CONTACT = 100;
    public SharedPreferences prefsLoginData;
    private int clickedPosition, savedItemStatus;
    private String activity = "";
    private SqLiteDbHelper sqLiteDbHelper;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dental_search_detail_screen);

        prefsLoginData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sqLiteDbHelper = new SqLiteDbHelper(DentalSearchDetailActivity.this);

        try {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                mCurrentListing = b.getParcelable("SearchResultObject");
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
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        llShare = (LinearLayout) findViewById(R.id.llShare);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        flBanner1 = (FrameLayout) findViewById(R.id.flBanner1);
        flBanner2 = (FrameLayout) findViewById(R.id.flBanner2);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        tvSave = (TextView) findViewById(R.id.tvSave);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        tvDentalFacility = (TextView) findViewById(R.id.tvDentalFacility);
        tvGenderType = (TextView) findViewById(R.id.tvGenderType);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvCode = (TextView) findViewById(R.id.tvCode);
        tvPracticeType = (TextView) findViewById(R.id.tvPracticeType);
        tvStreet = (TextView) findViewById(R.id.tvStreet);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);

        tvLanguageType = (TextView) findViewById(R.id.tvLanguageType);

        if (savedItemStatus == 1) {
            imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
            tvSave.setTextColor(getResources().getColor(R.color.light_blue));
            tvSave.setText("Saved");
        }

        doctor_name.setText(mCurrentListing.getFirstName() + " " + mCurrentListing.getLastName());
        tvLanguageType.setText(mCurrentListing.getLanguage());
        tvCenter.setText(mCurrentListing.getCenter());
        tvStatus.setText(mCurrentListing.getStatus());
        tvCode.setText(mCurrentListing.getGdOrSpCode());
        tvPracticeType.setText(mCurrentListing.getPracticeType());
        tvDentalFacility.setText(mCurrentListing.getSpec());

        tvStreet.setText(mCurrentListing.getAddr1().replace("   ", "") + " ,"
                + mCurrentListing.getCity() + ", " + mCurrentListing.getState() + " ," + mCurrentListing.getZipCode());

//        tvCity.setText(mCurrentListing.getCity() + ", " + mCurrentListing.getState());
//        tvZipcode.setText(mCurrentListing.getZipCode());
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(mCurrentListing.getPhoneNo().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(mCurrentListing.getPhoneNo().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(mCurrentListing.getPhoneNo().substring(6, 10));
            tvPhoneNumber.setText(stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e) {
            tvPhoneNumber.setText(mCurrentListing.getPhoneNo());
        }
        //When button is clicked
        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mCurrentListing.getPhoneNo()));
                try {
                    onCall();
                    /*Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mCurrentListing.getPhone()));

                    startActivity(callIntent);*/
                } catch (Exception e) {
                    Log.v("Call>>", e.getMessage());
                }
            }
        });
        ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvSave.getText().toString().equalsIgnoreCase("Saved"))
                    saveForLaterDentalService();
                else
                    deleteSaveForLaterVisionService();
            }
        });

        //When button is clicked
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.equalsIgnoreCase("DentalListActivity")) {
//                    Intent intent = new Intent(DentalSearchDetailActivity.this, DentalListActivity.class);
//                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(DentalSearchDetailActivity.this, NoSavedItemActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //When button is clicked
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnAbout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                btnAbout.setTextColor(getResources().getColor(R.color.view_bg));
                btnContactInfo.setBackgroundColor(getResources().getColor(R.color.view_bg));
                btnContactInfo.setTextColor(getResources().getColor(R.color.light_blue));

                llAbout.setVisibility(View.VISIBLE);
                llContactInfo.setVisibility(View.GONE);
            }
        });

        //When button is clicked
        btnContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnContactInfo.setBackgroundColor(getResources().getColor(R.color.light_blue));
                btnContactInfo.setTextColor(getResources().getColor(R.color.view_bg));
                btnAbout.setBackgroundColor(getResources().getColor(R.color.view_bg));
                btnAbout.setTextColor(getResources().getColor(R.color.light_blue));

                llAbout.setVisibility(View.GONE);
                llContactInfo.setVisibility(View.VISIBLE);
            }
        });

        //When button is clicked
        flBanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DentalSearchDetailActivity.this, UpgradeDentalFlipActivity.class));
            }
        });

        //When button is clicked
        flBanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DentalSearchDetailActivity.this, UpgradeDentalFlipActivity.class));
            }
        });

        //When button is clicked
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, "http://www.simplerx.com/");
                intent.putExtra(Intent.EXTRA_TEXT, "http://www.vurvhealth.com/");
                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

        //When button is clicked
        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Inprogress",Toast.LENGTH_SHORT).show();
                customAlertDialog();
            }
        });
        ll_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" + mCurrentListing.getLatitude() + "," + mCurrentListing.getLongitude())));
            }
        });

    }

    private void saveForLaterDentalService() {
        showProgressDialog(DentalSearchDetailActivity.this);

        ApiInterface apiService =
                ApiClient.getClient(DentalSearchDetailActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList<SaveForLaterVision>();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("1");
        saveForLaterRequest.setProviderId(mCurrentListing.getProviderId());

        saveForLaterRequestList.add(saveForLaterRequest);

        Call<ArrayList<StatusResponseForTotalProject>> call = apiService.saveForLaterDental(saveForLaterRequestList);
        call.enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {

                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = response.body();
                dismissProgressDialog();
//                Toast.makeText(getApplicationContext(),insertRecentSearchRespPayLoad.get(0).getStatus(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Added as favourite", Toast.LENGTH_SHORT).show();
                imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_saved_ic));
                tvSave.setTextColor(getResources().getColor(R.color.light_blue));
                tvSave.setText("Saved");
                if (activity.equalsIgnoreCase("DentalListActivity")) {
                    for (int i = 0; i < DentalScreenActivity.resPayloads.size(); i++) {
                        if (DentalScreenActivity.resPayloads.get(i).getProviderId().equalsIgnoreCase(mCurrentListing.getProviderId())) {
                            DentalScreenActivity.resPayloads.get(i).setSavedStatus(1);
                            if (DentalListActivity.searchForDentalResPayLoads != null) {
                                DentalListActivity.searchForDentalResPayLoads.get(clickedPosition).setSavedStatus(1);
                            }
                            sqLiteDbHelper.updateSavedStatusFlag("filter_dental", 1, mCurrentListing.getProviderId());
                            break;
                        }
                    }
//                    DentalScreenActivity.resPayloads.get(clickedPosition).setSavedStatus(1);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                dismissProgressDialog();

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterVisionService() {
        showProgressDialog(DentalSearchDetailActivity.this);

        ApiInterface apiService =
                ApiClient.getClient(DentalSearchDetailActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList<SaveForLaterVision>();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(mCurrentListing.getProviderId());

        saveForLaterRequestList.add(saveForLaterRequest);

        Call<ArrayList<StatusResponseForTotalProject>> call = apiService.saveForLaterDental(saveForLaterRequestList);
        call.enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            @Override
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                if (response.isSuccessful()) {
                    imgSave.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_star_ic));
                    tvSave.setTextColor(getResources().getColor(R.color.black));
                    tvSave.setText("Save for Later");
                    if (activity.equalsIgnoreCase("DentalListActivity")) {
                        if (activity.equalsIgnoreCase("DentalListActivity")) {
                            for (int i = 0; i < DentalScreenActivity.resPayloads.size(); i++) {
                                if (DentalScreenActivity.resPayloads.get(i).getProviderId().equalsIgnoreCase(mCurrentListing.getProviderId())) {
                                    DentalScreenActivity.resPayloads.get(i).setSavedStatus(0);
                                    if (DentalListActivity.searchForDentalResPayLoads != null) {
                                        DentalListActivity.searchForDentalResPayLoads.get(clickedPosition).setSavedStatus(0);
                                    }
                                    sqLiteDbHelper.updateSavedStatusFlag("filter_dental", 0, mCurrentListing.getProviderId());
                                    break;
                                }
                            }
//                    DentalScreenActivity.resPayloads.get(clickedPosition).setSavedStatus(1);
                        }
//                       DentalScreenActivity.resPayloads.get(clickedPosition).setSavedStatus(0);
                    }
                    ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = response.body();
                }
//                Toast.makeText(getApplicationContext(),insertRecentSearchRespPayLoad.get(0).getStatus(),Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                dismissProgressDialog();
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

    protected void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (pDialog != null)
            pDialog.dismiss();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(false);

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
        if (mCurrentListing.getLatitude() != null && mCurrentListing.getLatitude().length() > 0) {
            latLng = new LatLng(Double.parseDouble(mCurrentListing.getLatitude()), Double.parseDouble(mCurrentListing.getLongitude()));

            //set the drawable marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_dental_ic));
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

    //set popup for more options
    private void customAlertDialog() {

        final Dialog customDialog = new Dialog(DentalSearchDetailActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.more_options);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button save_contact = (Button) customDialog.findViewById(R.id.save_contact);
        Button email_doc = (Button) customDialog.findViewById(R.id.email_doc);

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        save_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                askForContactPermission();
                Toast.makeText(DentalSearchDetailActivity.this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
                customDialog.cancel();
//                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            }
        });
        email_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
                customDialog.dismiss();
                customDialog.cancel();
            }
        });


    }

    //save the contact directly in Phone contacts
    private void getContact() {
        ContentValues values = new ContentValues();
        values.put(Contacts.People.NUMBER, "987456321");
        values.put(Contacts.People.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
        values.put(Contacts.People.LABEL, "VURV");
        values.put(Contacts.People.NAME, "VURV");
        Uri dataUri = getContentResolver().insert(Contacts.People.CONTENT_URI, values);
        Uri updateUri = Uri.withAppendedPath(dataUri, Contacts.People.Phones.CONTENT_DIRECTORY);
        values.clear();
        values.put(Contacts.People.Phones.TYPE, Contacts.People.TYPE_MOBILE);
        values.put(Contacts.People.NUMBER, "987456321");
        updateUri = getContentResolver().insert(updateUri, values);

        /*ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 100)
                .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mCurrentListing.getMobileNo())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
                .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, mCurrentListing.getName())
                .build());
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }*/
    }

    //Checking read and write permission for the contact
    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(DentalSearchDetailActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(DentalSearchDetailActivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DentalSearchDetailActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(DentalSearchDetailActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
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
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(DentalSearchDetailActivity.this, "No permission for contacts", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //method for send mail to doctor
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "VURVhealth");
//        emailIntent.putExtra(Intent.EXTRA_TEXT,
//                "kindly find the below text and implement the same in Android (click on the \"More \" E-mail and after receiving user to E-mail show the above text).");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "http://www.vurvhealth.com/");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
            Log.i("Finish sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DentalSearchDetailActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    //permissions for call phone
    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + mCurrentListing.getPhoneNo())));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (activity.equalsIgnoreCase("DentalListActivity")) {
//            Intent intent = new Intent(DentalSearchDetailActivity.this, DentalListActivity.class);
//            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(DentalSearchDetailActivity.this, NoSavedItemActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
