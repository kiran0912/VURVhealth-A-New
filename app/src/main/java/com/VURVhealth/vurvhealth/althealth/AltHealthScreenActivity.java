package com.VURVhealth.vurvhealth.althealth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.DialogClass;
import com.VURVhealth.vurvhealth.GPSTracker;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSPastSearchesReqPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSearchReqPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSearchResPayload;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.medical.doctors.SpecialityScreenActivity;
import com.VURVhealth.vurvhealth.medical.seekbar.RangeSliderView;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.RecentSearchResponcePayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltHealthScreenActivity extends SuperAppCompactActivity {
    private static final int REQUISTCODE_SPECIALTY = 1000;
    public static AHSSearchResPayload ahsSearchResPayload;
    public static ArrayList<AHSSearchResPayload.Datum> resPayloads;
    private ImageView backBtn;
    private Button btn_reset;
    private Button btn_search_active;
    private Button btn_search_inactive;
    private String city;
    private EditText etCity;
    private Geocoder geocoder;
    private GPSTracker gps;
    ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad;
    private double latitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llVurv;
    private double longitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private int miles;
    private RangeSliderView seekbar;
    private Spinner spinnerState;
    private String state;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        public void afterTextChanged(Editable editable) {
        }
    };
    private TextView tvBanner;
    private EditText tvFirstName;
    private EditText tvLastName;
    private Button tvSpecialty;
    private EditText tv_zipcode;
    private String zipCode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.althealth_screen);
        geocoder = new Geocoder(this);
        tvSpecialty = (Button) findViewById(R.id.tvSpecialty);
        tv_zipcode = (EditText) findViewById(R.id.tv_zipcode);
        etCity = (EditText) findViewById(R.id.etCity);
        tvFirstName = (EditText) findViewById(R.id.tvFirstName);
        tvLastName = (EditText) findViewById(R.id.tvLastName);
        tvBanner = (TextView) findViewById(R.id.tvBanner);
        seekbar = (RangeSliderView) findViewById(R.id.seekbar);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        btn_search_inactive = (Button) findViewById(R.id.btn_inactive_search);
        btn_search_active = (Button) findViewById(R.id.btn_active_search);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        tv_zipcode.setCursorVisible(false);
        tv_zipcode.setFocusableInTouchMode(true);
        tvSpecialty.addTextChangedListener(textWatcher);
        tv_zipcode.addTextChangedListener(textWatcher);
        etCity.addTextChangedListener(textWatcher);
        tv_zipcode.setText(Application_holder.zipCode);
        if (Application_holder.zipCode.length() != 0) {
            tv_zipcode.setText(Application_holder.zipCode);
        }
        zipCode = tv_zipcode.getText().toString().trim();
        RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                switch (index) {
                    case 1:
                        miles = 10;
                        return;
                    case 2:
                        miles = 20;
                        return;
                    case 3:
                        miles = 30;
                        return;
                    case 4:
                        miles = 50;
                        return;
                    default:
                        miles = 0;
                        return;
                }
            }
        };
        seekbar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(shouldRequestDisallowIntercept((ViewGroup) view, motionEvent));
                return false;
            }
        });
        seekbar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        seekbar.setOnSlideListener(listener);
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AltHealthScreenActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AltHealthScreenActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AltHealthScreenActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AltHealthScreenActivity.this, HelpActivity.class));

                startActivity(new Intent(AltHealthScreenActivity.this, FreshdeskMainListActivity.class));
                finish();
            }
        });
        tv_zipcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_zipcode.setCursorVisible(true);
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_search_active.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (tv_zipcode.getText().toString().trim().length() > 0 && tv_zipcode.getText().toString().trim().length() < 3) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_zip), Toast.LENGTH_SHORT).show();
                } else if (checkInternet()) {
                    savedAHSService();
                } else {
                    Toast.makeText(AltHealthScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (prefsLoginData.getString("search_type", "").contains("Alt Health")) {
            tvBanner.setVisibility(View.GONE);
        } else {
            tvBanner.setVisibility(View.VISIBLE);
        }
        if (checkInternet()) {
            getStateService(AltHealthScreenActivity.this, null, spinnerState, "A1");
        } else {
            Toast.makeText(AltHealthScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        spinnerState.setAdapter(new ArrayAdapter<String>(AltHealthScreenActivity.this, 17367048, 16908308, fullFormList) {
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
        });
        tvSpecialty.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AltHealthScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", "altHealthSpecialty");
                i.putExtra("activity", "altHealthScreen");
                startActivityForResult(i, 1000);
            }
        });
        btn_reset.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                tvSpecialty.setText("");
                tv_zipcode.setText("");
                etCity.setText("");
                spinnerState.setSelection(0);
                tvFirstName.setText("");
                tvLastName.setText("");
                seekbar.setInitialIndex(0);
            }
        });
        spinnerState.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                checkFieldsForEmptyValues();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void checkFieldsForEmptyValues() {
        String zipCity = tv_zipcode.getText().toString();
        String specialty = tvSpecialty.getText().toString();
        String city = etCity.getText().toString();
        if ((zipCity.equals("") || specialty.equals("")) && (city.equals("") || spinnerState.getSelectedItemPosition() == 0 || specialty.equals(""))) {
            btn_search_active.setVisibility(View.GONE);
            btn_search_inactive.setVisibility(View.VISIBLE);
            return;
        }
        btn_search_inactive.setVisibility(View.GONE);
        btn_search_active.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(AltHealthScreenActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (!checkInternet() || latitude == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                Toast.makeText(AltHealthScreenActivity.this, getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
                return;
            } else if (Application_holder.zipCode.length() == 0) {
                new GetZipAyncTask(AltHealthScreenActivity.this, latitude, longitude).execute(new Object[0]);
                return;
            } else {
                return;
            }
        }
        gps.showSettingsAlert(AltHealthScreenActivity.this);
    }

    protected boolean shouldRequestDisallowIntercept(ViewGroup scrollView, MotionEvent event) {
        float yOffset = getYOffset(event);
        if (scrollView instanceof ListView) {
            ListView listView = (ListView) scrollView;
            if (yOffset < 0.0f && listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() >= 0) {
                return false;
            }
            if (yOffset <= 0.0f || listView.getLastVisiblePosition() != listView.getAdapter().getCount() - 1 || listView.getChildAt(listView.getChildCount() - 1).getBottom() > listView.getHeight()) {
                return true;
            }
            return false;
        }
        float scrollY = (float) scrollView.getScrollY();
        boolean disallowIntercept = (scrollY != 0.0f || yOffset >= 0.0f) && (((float) scrollView.getHeight()) + scrollY != ((float) scrollView.getChildAt(0).getHeight()) || yOffset < 0.0f);
        return disallowIntercept;
    }

    protected float getYOffset(MotionEvent ev) {
        int historySize = ev.getHistorySize();
        int pointerCount = ev.getPointerCount();
        if (historySize <= 0 || pointerCount <= 0) {
            return 0.0f;
        }
        return ev.getHistoricalY(pointerCount - 1, historySize - 1) - ev.getY(pointerCount - 1);
    }

    protected void onDestroy() {
        super.onDestroy();
        tv_zipcode.setText("");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1000) {
            checkFieldsForEmptyValues();
            tvSpecialty.setText(data.getStringExtra("fieldValue"));
        }
    }

    private void ahsPastSearches() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(AltHealthScreenActivity.this).create(ApiInterface.class);
        AHSPastSearchesReqPayload ahsPastSearchesReqPayload = new AHSPastSearchesReqPayload();
        ahsPastSearchesReqPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        ahsPastSearchesReqPayload.setRange(String.valueOf(miles));
        ahsPastSearchesReqPayload.setStatus("");
        ahsPastSearchesReqPayload.setFirstName(tvFirstName.getText().toString().length() == 0 ? "" : tvFirstName.getText().toString());
        ahsPastSearchesReqPayload.setLastName(tvLastName.getText().toString().length() == 0 ? "" : tvLastName.getText().toString());
        ahsPastSearchesReqPayload.setZipCode(tv_zipcode.getText().toString());
        ahsPastSearchesReqPayload.setCity(etCity.getText().toString().length() == 0 ? "" : etCity.getText().toString());
        ahsPastSearchesReqPayload.setState(spinnerState.getSelectedItemPosition() == 0 ? "" : spinnerState.getSelectedItem().toString());
        ahsPastSearchesReqPayload.setSpecialities(tvSpecialty.getText().toString());
        ahsPastSearchesReqPayload.setIP("");
        ahsPastSearchesReqPayload.setDeviceType("M");
        ahsPastSearchesReqPayload.setOperatingSystem("A");
        ahsPastSearchesReqPayload.setBrowser("");
        ArrayList<AHSPastSearchesReqPayload> ahsPastSearchesReqPayloads = new ArrayList();
        ahsPastSearchesReqPayloads.add(ahsPastSearchesReqPayload);
        Log.v("AHS post req ", "" + new Gson().toJson(ahsPastSearchesReqPayload));

        apiService.ahsPastSearch(ahsPastSearchesReqPayloads).enqueue(new Callback<ArrayList<RecentSearchResponcePayLoad>>() {
            public void onResponse(Call<ArrayList<RecentSearchResponcePayLoad>> call, Response<ArrayList<RecentSearchResponcePayLoad>> response) {
                if (!response.isSuccessful()) {
                }
            }

            public void onFailure(Call<ArrayList<RecentSearchResponcePayLoad>> call, Throwable t) {
                Toast.makeText(AltHealthScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
            }
        });
    }

    private void searchAHSService() {
        String stateAHS;
        String cityAHS = etCity.getText().toString().length() == 0 ? "" : etCity.getText().toString();
        if (spinnerState.getSelectedItemPosition() == 0) {
            stateAHS = "";
        } else {
            stateAHS = spinnerState.getSelectedItem().toString();
        }
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(AltHealthScreenActivity.this).create(ApiInterface.class);
        LatLng latLng = zipToLanLong(tv_zipcode.getText().toString());
        AHSSearchReqPayload ahsSearchReqPayload = new AHSSearchReqPayload();
        ahsSearchReqPayload.setZipCode(tv_zipcode.getText().toString());
        ahsSearchReqPayload.setCity(cityAHS);
        ahsSearchReqPayload.setState(stateAHS);
        if (cityAHS.length() <= 0 || stateAHS.length() <= 0) {
            ahsSearchReqPayload.setRange(String.valueOf(miles));
        } else {
            ahsSearchReqPayload.setRange("0");
        }
        if (miles == 0) {
            ahsSearchReqPayload.setLat("");
            ahsSearchReqPayload.setLong("");
        } else {
            ahsSearchReqPayload.setLat(String.valueOf(latLng.latitude));
            ahsSearchReqPayload.setLong(String.valueOf(latLng.longitude));
        }
        ahsSearchReqPayload.setSpecialty(tvSpecialty.getText().toString());
        ahsSearchReqPayload.setFirstName(tvFirstName.getText().toString().length() == 0 ? "" : tvFirstName.getText().toString());
        ahsSearchReqPayload.setLastName(tvLastName.getText().toString().length() == 0 ? "" : tvLastName.getText().toString());
        ahsSearchReqPayload.setType("A1");
        ArrayList<AHSSearchReqPayload> ahsSearchReqPayloads = new ArrayList();
        ahsSearchReqPayloads.add(ahsSearchReqPayload);
        apiService.searchForAHSProvider(ahsSearchReqPayloads).enqueue(new Callback<AHSSearchResPayload>() {
            public void onResponse(Call<AHSSearchResPayload> call, Response<AHSSearchResPayload> response) {
                if (response.isSuccessful()) {
                    ahsSearchResPayload = (AHSSearchResPayload) response.body();
                    resPayloads = ahsSearchResPayload.getData();
                    ArrayList<AHSSearchResPayload.StateList> resPayloadState = ahsSearchResPayload.getStateList();
                    Iterator it;
                    StringBuffer statesList;
                    if (ahsSearchResPayload.getStatus().equalsIgnoreCase("1") || ahsSearchResPayload.getStatus().equalsIgnoreCase("3")) {
                        if (resPayloads.size() > 0) {
                            it = resPayloads.iterator();
                            while (it.hasNext()) {
                                AHSSearchResPayload.Datum ahsSearchResPayloads = (AHSSearchResPayload.Datum) it.next();
                                Iterator it2 = insertRecentSearchRespPayLoad.iterator();
                                while (it2.hasNext()) {
                                    if (ahsSearchResPayloads.getAHSProviderId().equals(((StatusResponseForTotalProject) it2.next()).getProviderId())) {
                                        ahsSearchResPayloads.setSavedStatus(1);
                                        break;
                                    }
                                    ahsSearchResPayloads.setSavedStatus(0);
                                }
                            }
                            String stateShortName = "";
                            try {
                                List<Address> addresses = geocoder.getFromLocationName(tv_zipcode.getText().toString(), 1);
                                if (!(addresses == null || addresses.isEmpty())) {
                                    city = ((Address) addresses.get(0)).getLocality();
                                    if (!(addresses == null || addresses.isEmpty())) {
                                        city = ((Address) addresses.get(0)).getLocality();
                                        state = ((Address) addresses.get(0)).getAddressLine(0);
                                        stateShortName = state.substring(((Address) addresses.get(0)).getAddressLine(0).indexOf(", ") + 2, ((Address) addresses.get(0)).getAddressLine(0).indexOf(", ") + 4);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Editor editor = getSharedPreferences("altHealthType", 0).edit();
                            editor.putString("zipCode", tv_zipcode.getText().toString());
                            if (tv_zipcode.getText().toString().length() == 0) {
                                editor.putString("city", etCity.getText().toString());
                                editor.putString("state", spinnerState.getSelectedItem().toString());
                            } else {
                                editor.putString("city", city);
                                editor.putString("state", stateShortName);
                            }
                            editor.putString("altSpecialty", tvSpecialty.getText().toString().length() > 0 ? tvSpecialty.getText().toString() : "");
                            editor.commit();
                            Intent i = new Intent(AltHealthScreenActivity.this, AltHealthListActivity.class);
                            i.putExtra("zipcode", tv_zipcode.getText().toString());
                            startActivity(i);
                        }
                        if (ahsSearchResPayload.getStatus().equalsIgnoreCase("3") && resPayloads.size() == 0) {
                            statesList = new StringBuffer();
                            it = resPayloadState.iterator();
                            while (it.hasNext()) {
                                statesList.append(((AHSSearchResPayload.StateList) it.next()).getRestrictedState() + ", ");
                                statesList.setLength(statesList.length() - 2);
                                DialogClass.createDAlertDialog(AltHealthScreenActivity.this, getString(R.string.some_providers) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + statesList);
                            }
                        }
                    } else if (ahsSearchResPayload.getStatus().equalsIgnoreCase("0") || ahsSearchResPayload.getStatus().equalsIgnoreCase("2")) {
                        if (ahsSearchResPayload.getStatus().equalsIgnoreCase("0")) {
                            DialogClass.createDAlertDialog(AltHealthScreenActivity.this, getString(R.string.we_are_sorry));
                        } else if (ahsSearchResPayload.getStatus().equalsIgnoreCase("2")) {
                            statesList = new StringBuffer();
                            it = resPayloadState.iterator();
                            while (it.hasNext()) {
                                statesList.append(((AHSSearchResPayload.StateList) it.next()).getRestrictedState() + ", ");
                                statesList.setLength(statesList.length() - 2);
                                DialogClass.createDAlertDialog(AltHealthScreenActivity.this, getString(R.string.vurvcare_plan) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + statesList);
                            }
                        }
                    }
                    dismissProgressDialog();
                }
            }

            public void onFailure(Call<AHSSearchResPayload> call, Throwable t) {
                Toast.makeText(AltHealthScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    private void savedAHSService() {
        showProgressDialog(AltHealthScreenActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(AltHealthScreenActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("2");
        saveForLaterRequest.setProviderId("1");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAHS(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                insertRecentSearchRespPayLoad = (ArrayList) response.body();
                searchAHSService();
                ahsPastSearches();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public class GetZipAyncTask extends AsyncTask {
        Context context;
        double latitude;
        double longitude;
        ProgressDialog pDialog;
        String result = "";

        public GetZipAyncTask(Context context, double latitude, double longitude) {
            this.context = context;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(getResources().getString(R.string.getting_location));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Object doInBackground(Object[] params) {
            try {
                result = EntityUtils.toString(new DefaultHttpClient().execute(new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true")).getEntity());
                Log.v("result", ">>>>>>>>>>>" + result);
                if (result.contains("results")) {
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("results");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONArray jsonArray1 = jsonArray.getJSONObject(0).getJSONArray("address_components");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        if (jsonObject1.getJSONArray("types").getString(0).equals("postal_code")) {
                            Application_holder.zipCode = jsonObject1.getString("long_name");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (pDialog != null) {
                pDialog.dismiss();
            }
            tv_zipcode.setText(Application_holder.zipCode);
        }
    }
}
