package com.VURVhealth.vurvhealth.vision;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.VURVhealth.vurvhealth.help.HelpActivity;
import com.VURVhealth.vurvhealth.medical.doctors.SpecialityScreenActivity;
import com.VURVhealth.vurvhealth.medical.seekbar.RangeSliderView;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchRespPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.pojos.DoctorSpecialtyResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionReqPayload;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionInsertPastSearchReqPayload;
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

public class VisionScreenActivity extends SuperAppCompactActivity implements OnItemClickListener {
    private static final int REQUISTCODE_DOCTOR = 1000;
    private static final int REQUISTCODE_LANGUAGE = 100;
    private static final int REQUISTCODE_OFFICE = 1000;
    public static ArrayList<SearchForVisionResPayload.Datum> resPayloads;
    public static SearchForVisionResPayload searchForVisionResPayload;
    private ImageView backBtn;
    private Button btn_active_search;
    private Button btn_inactive_search;
    private Button btn_reset;
    private CheckBox chkFemale;
    private CheckBox chkMale;
    private String city;
    private Dialog customDialog;
    private ListView customlistView;
    private Button doc_speciality;
    private boolean doctorSpecialtySpinner = false;
    private ArrayList<String> doctor_list_data;
    private EditText etCityDoctors;
    private Geocoder geocoder;
    private GPSTracker gps;
    private ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad;
    private double latitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llVurv;
    private double longitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private int miles = 0;
    private Button ofc_speciality;
    private ArrayList<String> office_list_data;
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
    private EditText tvFirstName;
    private EditText tvLastName;
    private TextView tvUpgradeBanner;
    private Button tv_language;
    private EditText tv_zipcode;
    private String userId;
    private String zipCode;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_search_screen);
        geocoder = new Geocoder(VisionScreenActivity.this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvUpgradeBanner = (TextView) findViewById(R.id.tvUpgradeBanner);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        doc_speciality = (Button) findViewById(R.id.doc_speciality);
        ofc_speciality = (Button) findViewById(R.id.ofc_speciality);
        tv_zipcode = (EditText) findViewById(R.id.tv_zipcode);
        etCityDoctors = (EditText) findViewById(R.id.etCityDoctors);
        tvFirstName = (EditText) findViewById(R.id.tvFirstName);
        tvLastName = (EditText) findViewById(R.id.tvLastName);
        tv_language = (Button) findViewById(R.id.tv_language);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_inactive_search = (Button) findViewById(R.id.btn_inactive_search);
        btn_active_search = (Button) findViewById(R.id.btn_active_search);
        chkMale = (CheckBox) findViewById(R.id.chkMale);
        chkFemale = (CheckBox) findViewById(R.id.chkFemale);
        seekbar = (RangeSliderView) findViewById(R.id.seekbar);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        tv_zipcode.setCursorVisible(false);
        tv_zipcode.setFocusableInTouchMode(true);
        tv_zipcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_zipcode.setCursorVisible(true);
            }
        });
        tv_zipcode.setText(Application_holder.zipCode);
        userId = String.valueOf(prefsLoginData.getInt("userId", 1));
        tv_zipcode.addTextChangedListener(textWatcher);
        etCityDoctors.addTextChangedListener(textWatcher);
        if (Application_holder.zipCode.length() != 0) {
            tv_zipcode.setText(Application_holder.zipCode);
        }
        zipCode = tv_zipcode.getText().toString().trim();
        if (checkInternet()) {
            getDoctorSpecialtyService();
            getStateService(VisionScreenActivity.this, null, spinnerState, "V1");
        } else {
            Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        ofc_speciality.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisionScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", "visionOffice");
                i.putExtra("activity", "visionScreen");
                startActivityForResult(i, 1000);
            }
        });
        tv_language.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisionScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", "visionLang");
                i.putExtra("activity", "visionScreen");
                startActivityForResult(i, 100);
            }
        });
        if (prefsLoginData.getString("search_type", "").contains("Vision")) {
            tvUpgradeBanner.setVisibility(View.GONE);
        } else {
            tvUpgradeBanner.setVisibility(View.VISIBLE);
        }
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
        spinnerState.setAdapter(new ArrayAdapter<String>(VisionScreenActivity.this, R.layout.support_simple_spinner_dropdown_item, fullFormList) {
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
        seekbar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(shouldRequestDisallowIntercept((ViewGroup) v, event));
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
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisionScreenActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(VisionScreenActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(VisionScreenActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(VisionScreenActivity.this, HelpActivity.class));
                finish();
            }
        });
        doc_speciality.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (doctor_list_data.size() > 0) {
                        doctorSpecialtySpinner = true;
                        doctorCustomAlertDialog(VisionScreenActivity.this, doctor_list_data);
                        return;
                    }
                    Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
        btn_active_search.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (tv_zipcode.getText().toString().trim().length() > 0 && tv_zipcode.getText().toString().trim().length() < 3) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_zip), Toast.LENGTH_SHORT).show();
                } else if (checkInternet()) {
                    savedVisionService();
                } else {
                    Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_reset.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doc_speciality.setText("");
                ofc_speciality.setText("");
                spinnerState.setSelection(0);
                tvFirstName.setText("");
                tvLastName.setText("");
                tv_zipcode.setText("");
                tv_language.setText("");
                etCityDoctors.setText("");
                seekbar.setInitialIndex(0);
                chkMale.setChecked(false);
                chkFemale.setChecked(false);
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

    private void getDoctorSpecialtyService() {
        showProgressDialog(VisionScreenActivity.this);
        ApiInterface apiInterface = (ApiInterface) ApiClient.getClient(VisionScreenActivity.this).create(ApiInterface.class);
        DoctorSpecialtyResPayload doctorSpecReqPayLoad = new DoctorSpecialtyResPayload();
        doctorSpecReqPayLoad.setDoctorSpecialty("");
        ArrayList<DoctorSpecialtyResPayload> docSpecialtyReqPayLoads = new ArrayList();
        docSpecialtyReqPayLoads.add(doctorSpecReqPayLoad);
        apiInterface.getDoctorSpecialtyService(docSpecialtyReqPayLoads).enqueue(new Callback<ArrayList<DoctorSpecialtyResPayload>>() {
            public void onResponse(Call<ArrayList<DoctorSpecialtyResPayload>> call, Response<ArrayList<DoctorSpecialtyResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DoctorSpecialtyResPayload> doctorSpecialtyResPayloadArrayList = response.body();
                    doctor_list_data = new ArrayList();
                    dismissProgressDialog();
                    if (doctorSpecialtyResPayloadArrayList.size() > 0) {
                        for (int i = 0; i < doctorSpecialtyResPayloadArrayList.size(); i++) {
                            doctor_list_data.add(doctorSpecialtyResPayloadArrayList.get(i).getDoctorSpecialty());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<DoctorSpecialtyResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("DoctorSpecialtyService", t.toString());
            }
        });
    }

    private void visionPastSearches() {
        String gender;
        ApiInterface apiService = ApiClient.getClient(VisionScreenActivity.this).create(ApiInterface.class);
        VisionInsertPastSearchReqPayload visionInsertPastSearchReqPayload = new VisionInsertPastSearchReqPayload();
        visionInsertPastSearchReqPayload.setUserId(userId);
        visionInsertPastSearchReqPayload.setRange(String.valueOf(miles));
        visionInsertPastSearchReqPayload.setFirstName(tvFirstName.getText().toString().length() == 0 ? "" : tvFirstName.getText().toString());
        visionInsertPastSearchReqPayload.setLastName(tvLastName.getText().toString().length() == 0 ? "" : tvLastName.getText().toString());
        visionInsertPastSearchReqPayload.setCity(etCityDoctors.getText().toString().length() == 0 ? "" : etCityDoctors.getText().toString());
        visionInsertPastSearchReqPayload.setState(spinnerState.getSelectedItemPosition() == 0 ? "" : spinnerState.getSelectedItem().toString());
        if (chkMale.isChecked()) {
            gender = "M";
        } else if (chkFemale.isChecked()) {
            gender = "F";
        } else {
            gender = "";
        }
        visionInsertPastSearchReqPayload.setGender(gender);
        visionInsertPastSearchReqPayload.setLanguage(tv_language.getText().toString());
        visionInsertPastSearchReqPayload.setStatus("");
        visionInsertPastSearchReqPayload.setZipCode(tv_zipcode.getText().toString());
        visionInsertPastSearchReqPayload.setDoctorSpeciality(doc_speciality.getText().toString());
        visionInsertPastSearchReqPayload.setOfficeSpeciality(ofc_speciality.getText().toString());
        visionInsertPastSearchReqPayload.setIP("");
        visionInsertPastSearchReqPayload.setDeviceType("M");
        visionInsertPastSearchReqPayload.setOperatingSystem("A");
        visionInsertPastSearchReqPayload.setBrowser("");
        ArrayList<VisionInsertPastSearchReqPayload> visionInsertPastSearchReqPayloads = new ArrayList();
        visionInsertPastSearchReqPayloads.add(visionInsertPastSearchReqPayload);

        Log.v("vision post req ", "" + new Gson().toJson(visionInsertPastSearchReqPayloads));

        apiService.insertPastSearchesVision(visionInsertPastSearchReqPayloads).enqueue(new Callback<ArrayList<InsertRecentSearchRespPayLoad>>() {
            public void onResponse(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Response<ArrayList<InsertRecentSearchRespPayLoad>> response) {
                if (!response.isSuccessful()) {
                }
            }

            public void onFailure(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Throwable t) {
                Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
            }
        });
    }

    private void postVisionService() {
        String gender;
        String visionCity = etCityDoctors.getText().toString();
        String visionState = spinnerState.getSelectedItemPosition() == 0 ? "" : spinnerState.getSelectedItem().toString();
        LatLng latLng = zipToLanLong(tv_zipcode.getText().toString());
        ApiInterface apiService = ApiClient.getClient(VisionScreenActivity.this).create(ApiInterface.class);
        SearchForVisionReqPayload searchForVisionReqPayload = new SearchForVisionReqPayload();
        searchForVisionReqPayload.setZipCode(tv_zipcode.getText().toString());
        if (miles == 0) {
            searchForVisionReqPayload.setLat("");
            searchForVisionReqPayload.setLong("");
        } else {
            searchForVisionReqPayload.setLat(String.valueOf(latLng.latitude));
            searchForVisionReqPayload.setLong(String.valueOf(latLng.longitude));
        }
        searchForVisionReqPayload.setCity(visionCity);
        searchForVisionReqPayload.setState(visionState);
        if (visionCity.length() <= 0 || visionState.length() <= 0) {
            searchForVisionReqPayload.setRange(String.valueOf(miles));
        } else {
            searchForVisionReqPayload.setRange("0");
        }
        searchForVisionReqPayload.setDocSpecialty(doc_speciality.getText().toString());
        searchForVisionReqPayload.setOfficeSpecialty(ofc_speciality.getText().toString());
        searchForVisionReqPayload.setFirstName(tvFirstName.getText().toString().length() == 0 ? "" : tvFirstName.getText().toString());
        searchForVisionReqPayload.setLastName(tvLastName.getText().toString().length() == 0 ? "" : tvLastName.getText().toString());
        if (chkMale.isChecked() && chkFemale.isChecked()) {
            gender = "";
        } else if (chkFemale.isChecked()) {
            gender = "female";
        } else if (chkMale.isChecked()) {
            gender = "male";
        } else {
            gender = "";
        }
        searchForVisionReqPayload.setGender(gender);
        searchForVisionReqPayload.setType("V1");
        searchForVisionReqPayload.setLanguage(tv_language.getText().toString());
        ArrayList<SearchForVisionReqPayload> searchForVisionReqPayloads = new ArrayList();
        searchForVisionReqPayloads.add(searchForVisionReqPayload);
        Log.v("searchForVisionReq", "" + new Gson().toJson(searchForVisionReqPayloads));
        apiService.getSearchForVisionService(searchForVisionReqPayloads).enqueue(new Callback<SearchForVisionResPayload>() {
            public void onResponse(Call<SearchForVisionResPayload> call, Response<SearchForVisionResPayload> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        searchForVisionResPayload = response.body();
                        resPayloads = searchForVisionResPayload.getData();
                        ArrayList<SearchForVisionResPayload.StateList> resPayloadState = searchForVisionResPayload.getStateList();
                        if (searchForVisionResPayload.getStatus() == null) {
                            createDAlertDialog(getString(R.string.we_are_sorry));
                        } else if (searchForVisionResPayload.getStatus().equalsIgnoreCase("1") || searchForVisionResPayload.getStatus().equalsIgnoreCase("3")) {
                            Iterator it;
                            if (resPayloads.size() > 0) {
                                if (resPayloads != null) {
                                    it = resPayloads.iterator();
                                    while (it.hasNext()) {
                                        SearchForVisionResPayload.Datum searchForVisionResPayloads = (SearchForVisionResPayload.Datum) it.next();
                                        Iterator it2 = insertRecentSearchRespPayLoad.iterator();
                                        while (it2.hasNext()) {
                                            if (searchForVisionResPayloads.getVisProviderId().equals(((StatusResponseForTotalProject) it2.next()).getProviderId())) {
                                                searchForVisionResPayloads.setSavedStatus(1);
                                                break;
                                            }
                                            searchForVisionResPayloads.setSavedStatus(0);
                                        }
                                    }
                                }
                                String stateShortName = "";
                                try {
                                    List<Address> addresses = geocoder.getFromLocationName(tv_zipcode.getText().toString(), 1);
                                    if (!(addresses == null || addresses.isEmpty())) {
                                        city = ((Address) addresses.get(0)).getLocality();
                                        if (!(addresses == null || addresses.isEmpty())) {
                                            city = ((Address) addresses.get(0)).getLocality();
                                            if (!(addresses == null || addresses.isEmpty())) {
                                                city = ((Address) addresses.get(0)).getLocality();
                                                state = ((Address) addresses.get(0)).getAddressLine(0);
                                                stateShortName = state.substring(((Address) addresses.get(0)).getAddressLine(0).indexOf(", ") + 2, ((Address) addresses.get(0)).getAddressLine(0).indexOf(", ") + 4);
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Editor editor = getSharedPreferences("visionType", 0).edit();
                                editor.putString("zipCode", tv_zipcode.getText().toString());
                                if (tv_zipcode.getText().toString().length() == 0) {
                                    editor.putString("city", etCityDoctors.getText().toString());
                                    editor.putString("state", spinnerState.getSelectedItem().toString());
                                } else {
                                    editor.putString("city", city);
                                    editor.putString("state", stateShortName);
                                }
                                editor.putString("doctorSpecialty", doc_speciality.getText().toString().length() > 0 ? doc_speciality.getText().toString() : "");
                                editor.putString("officeSpecialty", ofc_speciality.getText().toString().length() > 0 ? ofc_speciality.getText().toString() : "");
                                editor.commit();
                                dismissProgressDialog();
                                startActivity(new Intent(VisionScreenActivity.this, VisionListActivity.class));
                            }
                            if (searchForVisionResPayload.getStatus().equalsIgnoreCase("3") && resPayloads.size() == 0) {
                                StringBuffer statesList = new StringBuffer();
                                it = resPayloadState.iterator();
                                while (it.hasNext()) {
                                    statesList.append(((SearchForVisionResPayload.StateList) it.next()).getRestrictedState() + ", ");
                                    statesList.setLength(statesList.length() - 2);
                                    DialogClass.createDAlertDialog(VisionScreenActivity.this, getString(R.string.some_providers) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + statesList);
                                }
                            }
                        } else if (searchForVisionResPayload.getStatus().equalsIgnoreCase("0") || searchForVisionResPayload.getStatus().equalsIgnoreCase("2")) {
                            if (searchForVisionResPayload.getStatus().equalsIgnoreCase("0")) {
                                createDAlertDialog(getString(R.string.we_are_sorry));
                            } else if (searchForVisionResPayload.getStatus().equalsIgnoreCase("2")) {
                                createDAlertDialog(getString(R.string.we_are_sorry));
                            }
                        }
                        dismissProgressDialog();
                    } else {
                        createDAlertDialog(getString(R.string.we_are_sorry));
                        dismissProgressDialog();
                    }
                    dismissProgressDialog();
                }
            }

            public void onFailure(Call<SearchForVisionResPayload> call, Throwable t) {
                Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    private void savedVisionService() {
        showProgressDialog(VisionScreenActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(VisionScreenActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(userId);
        saveForLaterRequest.setFlag("2");
        saveForLaterRequest.setProviderId("1");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDental(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                insertRecentSearchRespPayLoad = (ArrayList) response.body();
                postVisionService();
                visionPastSearches();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    protected void doctorCustomAlertDialog(Context context, ArrayList<String> doctor_list_data) {
        customDialog = new Dialog(context);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.picker_screen);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        customlistView = (ListView) customDialog.findViewById(R.id.spinnerlist);
        customlistView.setAdapter(new CustomAdapterDialog(context, doctor_list_data));
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        customlistView.setOnItemClickListener(VisionScreenActivity.this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (doctorSpecialtySpinner) {
            doc_speciality.setText((CharSequence) doctor_list_data.get(position));
        }
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog.cancel();
        }
    }

    private void checkFieldsForEmptyValues() {
        String zipcode_value = tv_zipcode.getText().toString();
        String city = etCityDoctors.getText().toString();
        String specialty = etCityDoctors.getText().toString();
        if (zipcode_value.equals("") && (city.equals("") || spinnerState.getSelectedItemPosition() == 0)) {
            btn_active_search.setVisibility(View.GONE);
            btn_inactive_search.setVisibility(View.VISIBLE);
            return;
        }
        btn_active_search.setVisibility(View.VISIBLE);
        btn_inactive_search.setVisibility(View.GONE);
    }

    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(VisionScreenActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (!checkInternet() || latitude == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                Toast.makeText(VisionScreenActivity.this, getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
                return;
            } else if (Application_holder.zipCode.length() == 0) {
                new GetZipAyncTask(VisionScreenActivity.this, latitude, longitude).execute(new Object[0]);
                return;
            } else {
                return;
            }
        }
        gps.showSettingsAlert(VisionScreenActivity.this);
    }

    public void createDAlertDialog(String message) {
        String setmessage = message;
        final Dialog customDialog = new Dialog(VisionScreenActivity.this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.custom_alert);
        LayoutParams lp = new LayoutParams();
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
        yesBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            tv_language.setText(data.getStringExtra("fieldValue"));
        } else if (requestCode == 1000 && resultCode == 101) {
            ofc_speciality.setText(data.getStringExtra("fieldValue"));
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        tv_zipcode.setText("");
    }

    protected class CustomAdapterDialog extends BaseAdapter {
        Context context;
        ArrayList<String> doctorSpecialty = new ArrayList();

        private class ViewHolder {
            TextView txtTitle;

            private ViewHolder() {
            }
        }

        public CustomAdapterDialog(Context context, ArrayList<String> doctorSpecialty) {
            this.doctorSpecialty = doctorSpecialty;
            this.context = context;
        }

        public int getCount() {
            return doctorSpecialty.size();
        }

        public Object getItem(int position) {
            return doctorSpecialty.get(position);
        }

        public long getItemId(int position) {
            return (long) doctorSpecialty.indexOf(getItem(position));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) convertView.findViewById(R.id.tvItemName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtTitle.setText((CharSequence) doctorSpecialty.get(position));
            return convertView;
        }
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
