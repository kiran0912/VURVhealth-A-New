package com.VURVhealth.vurvhealth.dental;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.save.SaveItemActivity;
import com.google.android.gms.maps.model.LatLng;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.GPSTracker;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.dental.pojos.DentalPastSearchReqPayload;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalReqPayload;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.VURVhealth.vurvhealth.medical.doctors.SpecialityScreenActivity;
import com.VURVhealth.vurvhealth.medical.seekbar.RangeSliderView;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchRespPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 26/12/16.
 */
public class DentalScreenActivity extends SuperAppCompactActivity {
    private static final int REQUISTCODE_LANGUAGE = 101;
    private static final int REQUISTCODE_SPECIALTY = 100;
    public static SearchForDentalResPayLoad dentalSearchResPayload;
    public static ArrayList<SearchForDentalResPayLoad.Datum> resPayloads;
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
    private int miles = 0;
    private SqLiteDbHelper recentDbHelper;
    private ScrollView scrollView;
    private RangeSliderView seekbar;
    private Spinner spStatus;
    private String specCode = "";
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
    private Button tv_language;
    private EditText tv_zipcode;
    private String zipCode;




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
            pDialog = new ProgressDialog(DentalScreenActivity.this);
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dental_search_screen);
        geocoder = new Geocoder(this);
        recentDbHelper = new SqLiteDbHelper(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvSpecialty = (Button) findViewById(R.id.tvSpecialty);
        tv_zipcode = (EditText) findViewById(R.id.tv_zipcode);
        etCity = (EditText) findViewById(R.id.etCity);
        tvFirstName = (EditText) findViewById(R.id.tvFirstName);
        tvLastName = (EditText) findViewById(R.id.tvLastName);
        tv_language = (Button) findViewById(R.id.tv_language);
        tvBanner = (TextView) findViewById(R.id.tvBanner);
        seekbar = (RangeSliderView) findViewById(R.id.seekbar);
        spStatus = (Spinner) findViewById(R.id.spStatus);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_search_inactive = (Button) findViewById(R.id.btn_inactive_search);
        btn_search_active = (Button) findViewById(R.id.btn_active_search);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        tv_zipcode.addTextChangedListener(textWatcher);
        etCity.addTextChangedListener(textWatcher);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });
        tv_zipcode.setCursorVisible(false);
        tv_zipcode.setFocusableInTouchMode(true);
        checkFieldsForEmptyValues();
        tv_zipcode.setText(Application_holder.zipCode);
        if (Application_holder.zipCode.length() != 0) {
            tv_zipcode.setText(Application_holder.zipCode);
        }
        zipCode = tv_zipcode.getText().toString().trim();
        List<String> categories = new ArrayList();
        categories.add(getString(R.string.status));
        categories.add(getString(R.string.accepting));
        categories.add(getString(R.string.mixed_out));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, 17367048, 16908308, categories) {
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
        tv_zipcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_zipcode.setCursorVisible(true);
            }
        });
        spStatus.setAdapter(spinnerAdapter);
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
        seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(shouldRequestDisallowIntercept((ViewGroup) view, motionEvent));
                return false;
            }
        });
        seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        seekbar.setOnSlideListener(listener);
        getStateService(DentalScreenActivity.this, null, spinnerState, "D1");
        spinnerState.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, fullFormList) {
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
        if (prefsLoginData.getString("search_type", "").contains("Dental")) {
            tvBanner.setVisibility(View.GONE);
        } else {
            tvBanner.setVisibility(View.VISIBLE);
        }
        btn_search_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_zipcode.getText().toString().trim().length() > 0 && tv_zipcode.getText().toString().trim().length() < 3) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_zip), Toast.LENGTH_SHORT).show();
                } else if (checkInternet()) {
                    savedDentalService();
                } else {
                    Toast.makeText(DentalScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        llVurv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DentalScreenActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DentalScreenActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DentalScreenActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(Application_holder.help_url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        tvSpecialty.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DentalScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", "dentalSpecialty");
                i.putExtra("activity", "dentalScreen");
                startActivityForResult(i, 100);
            }
        });
        tv_language.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DentalScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", "dentalLanguage");
                i.putExtra("activity", "dentalScreen");
                startActivityForResult(i, 101);
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvSpecialty.setText("");
                tv_zipcode.setText("");
                etCity.setText("");
                spStatus.setSelection(0);
                spinnerState.setSelection(0);
                tvFirstName.setText("");
                tvLastName.setText("");
                tv_language.setText("");
                seekbar.setInitialIndex(0);
                specCode = "";
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 100) {
            tvSpecialty.setText(data.getStringExtra("fieldValue"));
            specCode = data.getStringExtra("specCode");
            return;
        }
        tv_language.setText(data.getStringExtra("fieldValue"));
    }

    private void checkFieldsForEmptyValues() {

        String dentistType = tvSpecialty.getText().toString();
        String zipCity = tv_zipcode.getText().toString();
        String dentistName = etCity.getText().toString();
        String language = tv_language.getText().toString();
//        && !zipCity.equals("") && !dentistName.equals("") && !language.equals("")

        if (!zipCity.equals("")) {

            btn_search_active.setVisibility(View.VISIBLE);
            btn_search_inactive.setVisibility(View.GONE);

        } else {
            btn_search_active.setVisibility(View.GONE);
            btn_search_inactive.setVisibility(View.VISIBLE);
        }

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

    private void dentalPastSearch() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        DentalPastSearchReqPayload dentalPastSearchReqPayload = new DentalPastSearchReqPayload();
        dentalPastSearchReqPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        dentalPastSearchReqPayload.setFirstName(tvFirstName.getText().toString().length() == 0 ? "" : tvFirstName.getText().toString());
        dentalPastSearchReqPayload.setLastName(tvLastName.getText().toString().length() == 0 ? "" : tvLastName.getText().toString());
        dentalPastSearchReqPayload.setZipCode(tv_zipcode.getText().toString());
        dentalPastSearchReqPayload.setCity(etCity.getText().toString().length() == 0 ? "" : etCity.getText().toString());
        dentalPastSearchReqPayload.setState(spinnerState.getSelectedItemPosition() == 0 ? "" : spinnerState.getSelectedItem().toString());
        dentalPastSearchReqPayload.setSpecialities(specCode);
        dentalPastSearchReqPayload.setStatus(spStatus.getSelectedItem() == null ? "" : spStatus.getSelectedItem().toString());
        dentalPastSearchReqPayload.setLanguages(tv_language.getText().toString());
        dentalPastSearchReqPayload.setOperatingSystem("A");
        dentalPastSearchReqPayload.setDeviceType("M");
        dentalPastSearchReqPayload.setIP("");
        dentalPastSearchReqPayload.setBrowser("");
        dentalPastSearchReqPayload.setRanges(String.valueOf(miles));
        ArrayList<DentalPastSearchReqPayload> pastSearchReqPayloads = new ArrayList();
        pastSearchReqPayloads.add(dentalPastSearchReqPayload);

        Log.v("Dental post req ", "" + new Gson().toJson(dentalPastSearchReqPayload).toString());

        apiService.dentalPastSearch(pastSearchReqPayloads).enqueue(new Callback<ArrayList<InsertRecentSearchRespPayLoad>>() {
            public void onResponse(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Response<ArrayList<InsertRecentSearchRespPayLoad>> response) {
                if (!response.isSuccessful()) {
                }
            }

            public void onFailure(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Throwable t) {
                Toast.makeText(DentalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
            }
        });
    }

    private void searchForDentalService() {
        try {
            String dentalState;
            String status;
            String dentalCity = etCity.getText().toString();
            if (spinnerState.getSelectedItemPosition() == 0) {
                dentalState = "";
            } else {
                dentalState = spinnerState.getSelectedItem().toString();
            }
            LatLng latLng = zipToLanLong(tv_zipcode.getText().toString().trim());
            ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
            SearchForDentalReqPayload searchForDentalReqPayload = new SearchForDentalReqPayload();
            searchForDentalReqPayload.setZipCode(tv_zipcode.getText().toString());
            searchForDentalReqPayload.setCity(dentalCity);
            searchForDentalReqPayload.setState(dentalState);
            if (dentalCity.length() <= 0 || dentalState.length() <= 0) {
                searchForDentalReqPayload.setRange(String.valueOf(miles));
            } else {
                searchForDentalReqPayload.setRange("0");
            }
            searchForDentalReqPayload.setSpecialty(tvSpecialty.getText().toString());
            if (spStatus.getSelectedItemPosition() == 1) {
                status = "OK";
            } else if (spStatus.getSelectedItemPosition() == 2) {
                status = "MX";
            } else {
                status = "";
            }
            searchForDentalReqPayload.setStatus(status);
            searchForDentalReqPayload.setLanguage(tv_language.getText().toString());
            searchForDentalReqPayload.setFirstName(tvFirstName.getText().toString().length() == 0 ? "" : tvFirstName.getText().toString());
            searchForDentalReqPayload.setLastName(tvLastName.getText().toString().length() == 0 ? "" : tvLastName.getText().toString());
            if (miles == 0) {
                searchForDentalReqPayload.setLatitude("");
                searchForDentalReqPayload.setLongitude("");
            } else {
                searchForDentalReqPayload.setLatitude(String.valueOf(latLng.latitude));
                searchForDentalReqPayload.setLongitude(String.valueOf(latLng.longitude));
            }
            searchForDentalReqPayload.setType("D1");
            ArrayList<SearchForDentalReqPayload> searchForDentalReqPayloads = new ArrayList();
            searchForDentalReqPayloads.add(searchForDentalReqPayload);
            Gson gson = new Gson();
            String dataReq = gson.toJson(searchForDentalReqPayloads);
            apiService.getSearchForDental(searchForDentalReqPayloads).enqueue(new Callback<SearchForDentalResPayLoad>() {
                public void onResponse(Call<SearchForDentalResPayLoad> call, Response<SearchForDentalResPayLoad> response) {
                    if (response.isSuccessful()) {
                        dentalSearchResPayload = (SearchForDentalResPayLoad) response.body();
                        resPayloads = dentalSearchResPayload.getData();
                        ArrayList<SearchForDentalResPayLoad.StateList> resPayloadState = dentalSearchResPayload.getStateList();
                        if (dentalSearchResPayload.getStatus().equalsIgnoreCase("1") || DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("3")) {
                            Iterator it;
                            if (resPayloads.size() > 0) {
                                if (resPayloads != null) {
                                    it = resPayloads.iterator();
                                    while (it.hasNext()) {
                                        SearchForDentalResPayLoad.Datum searchForDentalResPayLoad = (SearchForDentalResPayLoad.Datum) it.next();
                                        Iterator it2 = insertRecentSearchRespPayLoad.iterator();
                                        while (it2.hasNext()) {
                                            if (searchForDentalResPayLoad.getProviderId().equals(((StatusResponseForTotalProject) it2.next()).getProviderId())) {
                                                searchForDentalResPayLoad.setSavedStatus(1);
                                                break;
                                            }
                                            searchForDentalResPayLoad.setSavedStatus(0);
                                        }
                                    }
                                }
                                recentDbHelper.removeRecordsFromDentalFilter();
                                recentDbHelper.insertDentalResult(DentalScreenActivity.resPayloads);
                                String stateShortName = "";
                                try {
                                    List<Address> addresses = geocoder.getFromLocationName(tv_zipcode.getText().toString(), 1);
                                    if (!(addresses == null || addresses.isEmpty())) {
                                        city = ((Address) addresses.get(0)).getLocality();
                                        state = ((Address) addresses.get(0)).getAddressLine(0);
                                        stateShortName = state.substring(((Address) addresses.get(0)).getAddressLine(0).indexOf(", ") + 2, ((Address) addresses.get(0)).getAddressLine(0).indexOf(", ") + 4);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                SharedPreferences.Editor editor = getSharedPreferences("dentalType", 0).edit();
                                editor.putString("zipCode", tv_zipcode.getText().toString());
                                if (tv_zipcode.getText().toString().length() > 0) {
                                    editor.putString("city", city);
                                    editor.putString("state", stateShortName);
                                } else {
                                    editor.putString("city", etCity.getText().toString());
                                    editor.putString("state", spinnerState.getSelectedItem().toString());
                                }
                                editor.putString(MedicalScreenActivity.specialty, tvSpecialty.getText().toString().length() > 0 ? tvSpecialty.getText().toString() : "");
                                editor.commit();
                                startActivity(new Intent(DentalScreenActivity.this, DentalListActivity.class));
                            }
                            if (DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("3")) {
                                StringBuffer statesList = new StringBuffer();
                                it = resPayloadState.iterator();
                                while (it.hasNext()) {
                                    statesList.append(((SearchForDentalResPayLoad.StateList) it.next()).getRestrictedState() + ", ");
                                    statesList.setLength(statesList.length() - 2);
                                }
                            }
                        } else if (DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("0") || DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("2")) {
                            if (DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("0")) {
                                createDAlertDialog(getString(R.string.we_are_sorry));
                            } else if (DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("2")) {
                                createDAlertDialog(getString(R.string.we_are_sorry));
                            }
                        }

                    }
                    dismissProgressDialog();
                }

                public void onFailure(Call<SearchForDentalResPayLoad> call, Throwable t) {
                    Toast.makeText(DentalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    Log.e("", t.toString());
                    dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            Log.v("Dental","Error: "+e.getMessage());
            dismissProgressDialog();
        }
    }

    public void createDAlertDialog(String message) {
        String setmessage = message;
        final Dialog customDialog = new Dialog(this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.custom_alert);
        /*ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;*/
        TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
        TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
        Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);
        ((Button) customDialog.findViewById(R.id.cancelBtn)).setVisibility(View.GONE);
        tv_title.setText(R.string.vurvhealth);
        info_heading.setText(setmessage);
        yesBtn.setText(R.string.ok);
        tv_title.setTypeface(null, Typeface.BOLD);
        tv_title.setTextSize(20.0f);
        //customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        yesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    private void savedDentalService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("2");
        saveForLaterRequest.setProviderId("1");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDental(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                insertRecentSearchRespPayLoad = response.body();
                searchForDentalService();
               /*Srikanth*/
                //dentalPastSearch();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (!checkInternet() || latitude == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                Toast.makeText(this, getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
                return;
            } else if (Application_holder.zipCode.length() == 0) {
                new GetZipAyncTask(this, latitude, longitude).execute(new Object[0]);
                return;
            } else {
                return;
            }
        }
        gps.showSettingsAlert(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        tv_zipcode.setText("");
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
