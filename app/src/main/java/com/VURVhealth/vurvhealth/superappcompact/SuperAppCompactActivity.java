package com.VURVhealth.vurvhealth.superappcompact;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.pojos.StateReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.StateResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuperAppCompactActivity extends AppCompatActivity {
    static final Integer GPS_SETTINGS = Integer.valueOf(7);
    static final Integer LOCATION = Integer.valueOf(1);
    public GoogleApiClient client;
    private ListView customlistView;
    public ArrayAdapter<String> dataAdapter;
    public ArrayList<String> fullFormList;
    public Editor loginEditor;
    private LocationRequest mLocationRequest;
    private ProgressDialog pDialog;
    public SharedPreferences prefsLoginData;
    private PendingResult<LocationSettingsResult> result;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new Builder(this).addApi(AppIndex.API).addApi(LocationServices.API).build();
        fullFormList = new ArrayList();
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        loginEditor = prefsLoginData.edit();

    }

    public static final int getColor(Context context, int id) {
        if (VERSION.SDK_INT >= 23) {
            return ContextCompat.getColor(context, id);
        }
        return context.getResources().getColor(id);
    }

    protected boolean checkInternet() {
        getBaseContext();
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    protected void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(getString(R.string.please_wait));
        pDialog.setCancelable(false);
        if (pDialog != null&&!pDialog.isShowing()){
            pDialog.show(); 
        }
        
    }

    protected void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        pDialog = null;
    }

    public void askForGPS(final Activity context) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(100);
        mLocationRequest.setInterval(NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS);
        mLocationRequest.setFastestInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public void onResult(LocationSettingsResult result) {
                Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case 6:
                        try {

                            status.startResolutionForResult(context, SuperAppCompactActivity.GPS_SETTINGS.intValue());
                            return;
                        } catch (SendIntentException e) {
                            return;
                        }
                    default:
                        return;
                }
            }
        });
    }



    public void getStateService(final Context context, final Spinner spinnerStatemedical, final Spinner spinnerState, String type) {
        ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
        ArrayList<StateReqPayload> stateReqPayloads = new ArrayList();
        StateReqPayload stateReqPayload = new StateReqPayload();
        stateReqPayload.setType(type);
        stateReqPayloads.add(stateReqPayload);
        apiService.getState(stateReqPayloads).enqueue(new Callback<ArrayList<StateResPayload>>() {
            public void onResponse(Call<ArrayList<StateResPayload>> call, Response<ArrayList<StateResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<StateResPayload> stateResPayload = (ArrayList) response.body();
                    fullFormList.add(0, getResources().getString(R.string.state));
                    for (int i = 1; i < stateResPayload.size(); i++) {
                        fullFormList.add( stateResPayload.get(i).getFacState());
                    }
                    dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_selectable_list_item, fullFormList) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            if (position == 0) {
                                textView.setTextColor(Color.parseColor("#42000000"));
                                textView.setTextSize(18.0f);
                            } else {
                                textView.setTextColor(Color.parseColor("#005FB6"));
                                textView.setTextSize(18.0f);
                            }
                            return textView;
                        }
                    };
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if (spinnerStatemedical != null) {
                        spinnerStatemedical.setAdapter(dataAdapter);
                        spinnerState.setAdapter(dataAdapter);
                        return;
                    }
                    spinnerState.setAdapter(dataAdapter);
                }
            }

            public void onFailure(Call<ArrayList<StateResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LatLng zipToLanLong(String zipcode) {
        String zip = "90210";
        try {
            List<Address> addresses = new Geocoder(this).getFromLocationName(zipcode, 1);
            if (addresses == null || addresses.isEmpty()) {
                return new LatLng(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            }
            Address address =  addresses.get(0);
            String city =  addresses.get(0).getLocality();
            String state =  addresses.get(0).getAdminArea();
            String message = String.format("Latitude: %f, Longitude: %f", new Object[]{Double.valueOf(address.getLatitude()), Double.valueOf(address.getLongitude())});
            return new LatLng(address.getLatitude(), address.getLongitude());
        } catch (Exception e) {
            return new LatLng(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        }
    }
    public void createDAlertDialog(String message) {
        String setmessage = message;
        final Dialog customDialog = new Dialog(this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.custom_alert);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
        TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
        Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);
        ((Button) customDialog.findViewById(R.id.cancelBtn)).setVisibility(View.GONE);
        tv_title.setText(R.string.vurvhealth);
        info_heading.setText(setmessage);
        yesBtn.setText(R.string.ok);
        tv_title.setTypeface(null, Typeface.BOLD);
        tv_title.setTextSize(20.0f);
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        yesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    //Kiran D
    public  void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
