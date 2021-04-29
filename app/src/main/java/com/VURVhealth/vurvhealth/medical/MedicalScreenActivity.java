package com.VURVhealth.vurvhealth.medical;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.DialogClass;
import com.VURVhealth.vurvhealth.GPSTracker;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.help.HelpActivity;
import com.VURVhealth.vurvhealth.medical.doctors.DoctorSearchResults;
import com.VURVhealth.vurvhealth.medical.doctors.SpecialityScreenActivity;
import com.VURVhealth.vurvhealth.medical.facilities.FacilitySearchResult;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacPastSearchReqPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityTypeReqPayLoad;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityTypeResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.DoctorPastSearchReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.FacilitySubTypeResPayload;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterDoctors;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterFacility;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.medical.seekbar.RangeSliderView;
import com.VURVhealth.vurvhealth.medical.seekbar.RangeSliderView1;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchRespPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
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

public class MedicalScreenActivity extends SuperAppCompactActivity implements OnItemClickListener {
    private static final int REQUISTCODE_HOSPITAL = 102;
    private static final int REQUISTCODE_LANGUAGE = 101;
    private static final int REQUISTCODE_SPECIALITY = 100;
    private static final String TAG = MedicalScreenActivity.class.getSimpleName();
    public static final String languageTitle = "language";
    public static ArrayList<SearchPractitionerResPayLoad> resPayloads = null;
    public static ArrayList<SearchFacilitiesResPayLoad> resPayloadsForFacilities = null;
    public static final String specialty = "specialty";
    private ImageView backBtn;
    private Button btn_doctor;
    private Button btn_doctor_reset;
    private Button btn_doctor_search;
    private Button btn_facilities;
    private Button btn_inactive_doctor_search;
    private CheckBox chkFemale;
    private CheckBox chkMale;
    String city;
    private Dialog customDialog;
    private ListView customlistView;
    private boolean doctorlayout = true;
    private EditText etCityDoctors;
    private boolean facilities = false;
    private ArrayList<String> facility_code_list;
    private ArrayList<String> facility_list_data;
    private ArrayList<String> facility_subType_list_data;
    private Geocoder geocoder;
    private GPSTracker gps;
    ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad;
    double latitude;
    public ArrayList<String> list;
    private LinearLayout llBottomTab;
    private LinearLayout llCheck;
    private LinearLayout llDoctors;
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llTab;
    private LinearLayout llVurv;
    private LinearLayout ll_Facilities;
    double longitude;
    private int miles = 0;
    private SqLiteDbHelper recentDbHelper;
    private ArrayList<StatusResponseForTotalProject> responseForTotalProjects;
    private ScrollView scrollView;
    private RangeSliderView seekbarDoctors;
    private String shortName = "";
    private EditText spinnerCityFacility;
    private Spinner spinnerStateDoctors;
    private Spinner spinnerStateFacility;
    String state;
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
    private EditText tvDovtorsFirstName;
    private EditText tvDovtorsLastName;
    private Button tvFacilitySubtype;
    private Button tvHospitalAffiliation;
    private Button tvSpeciality;
    private EditText tv_facilityName;
    private Button tv_language;
    private Button tv_typeFacility;
    private EditText tv_zipcodeFacility;
    private EditText tv_zipodeDoctors;



   

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_screen);
        recentDbHelper = new SqLiteDbHelper(this);
        geocoder = new Geocoder(this);
        Toolbar htab_toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        setSupportActionBar(htab_toolbar);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tvBanner = (TextView) findViewById(R.id.tvBanner);
        scrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        if (prefsLoginData.getString("search_type", "").contains("Doctors")) {
            tvBanner.setVisibility(View.GONE);
        } else {
            tvBanner.setVisibility(View.VISIBLE);
        }
        seekbarDoctors = (RangeSliderView) findViewById(R.id.seekbarDoctors);
        RangeSliderView.OnSlideListener listener = (RangeSliderView.OnSlideListener) new RangeSliderView.OnSlideListener() {
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
        seekbarDoctors.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(shouldRequestDisallowIntercept((ViewGroup) view, motionEvent));
                return false;
            }
        });
        seekbarDoctors.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        seekbarDoctors.setOnSlideListener(listener);
        tvDovtorsFirstName = (EditText) findViewById(R.id.tvDovtorsFirstName);
        tvDovtorsLastName = (EditText) findViewById(R.id.tvDovtorsLastName);
        spinnerStateDoctors = (Spinner) findViewById(R.id.spinnerStateDoctors);
        etCityDoctors = (EditText) findViewById(R.id.etCityDoctors);
        spinnerStateFacility = (Spinner) findViewById(R.id.spinnerStateFacility);
        spinnerCityFacility = (EditText) findViewById(R.id.spinnerCityFacility);
        llCheck = (LinearLayout) findViewById(R.id.llCheck);
        llTab = (LinearLayout) findViewById(R.id.llTab);
        llBottomTab = (LinearLayout) findViewById(R.id.llBottomTab);
        chkMale = (CheckBox) findViewById(R.id.chkMale);
        chkFemale = (CheckBox) findViewById(R.id.chkFemale);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        btn_doctor = (Button) findViewById(R.id.btn_doctor);
        btn_facilities = (Button) findViewById(R.id.btn_facilities);
        btn_doctor_reset = (Button) findViewById(R.id.btn_doctor_reset);
        btn_doctor_search = (Button) findViewById(R.id.btn_doctor_search);
        btn_inactive_doctor_search = (Button) findViewById(R.id.btn_inactive_doctor_search);
        tvSpeciality = (Button) findViewById(R.id.tvSpeciality);
        tv_zipodeDoctors = (EditText) findViewById(R.id.tv_zipodeDoctors);
        tvHospitalAffiliation = (Button) findViewById(R.id.tvHospitalAffiliation);
        tv_language = (Button) findViewById(R.id.tv_language);
        tv_typeFacility = (Button) findViewById(R.id.tv_typeFacility);
        tv_zipcodeFacility = (EditText) findViewById(R.id.tv_zipcodeFacility);
        tv_facilityName = (EditText) findViewById(R.id.tv_facilityName);
        tvFacilitySubtype = (Button) findViewById(R.id.tvFacilitySubtype);
        llDoctors = (LinearLayout) findViewById(R.id.llDoctors);
        ll_Facilities = (LinearLayout) findViewById(R.id.ll_Facilities);
        tv_zipodeDoctors.setFocusableInTouchMode(true);
        tv_zipcodeFacility.setFocusableInTouchMode(true);
        tv_zipodeDoctors.setText(Application_holder.zipCode);
        tv_zipcodeFacility.setText(Application_holder.zipCode);
        tv_zipodeDoctors.setCursorVisible(false);
        tv_zipcodeFacility.setCursorVisible(false);
        tvSpeciality.addTextChangedListener(textWatcher);
        tv_zipodeDoctors.addTextChangedListener(textWatcher);
        etCityDoctors.addTextChangedListener(textWatcher);
        tv_typeFacility.addTextChangedListener(textWatcher);
        tvFacilitySubtype.addTextChangedListener(textWatcher);
        tv_zipcodeFacility.addTextChangedListener(textWatcher);
        spinnerCityFacility.addTextChangedListener(textWatcher);
        if (checkInternet()) {
            getFacilityTypeService();
            getStateService(this, spinnerStateDoctors, spinnerStateFacility, "M1");
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        if (Application_holder.zipCode.length() != 0) {
            tv_zipcodeFacility.setText(Application_holder.zipCode);
            tv_zipodeDoctors.setText(Application_holder.zipCode);
        }
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                miles = 0;
                finish();
            }
        });
        llSaved.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalScreenActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalScreenActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalScreenActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicalScreenActivity.this, FreshdeskMainListActivity.class));
//                startActivity(new Intent(MedicalScreenActivity.this, HelpActivity.class));
                finish();
            }
        });
        tv_language.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MedicalScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", languageTitle);
                i.putExtra("activity", "medicalScreen");
                startActivityForResult(i, 101);
            }
        });
        tvHospitalAffiliation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MedicalScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("fieldName", "hospitalAffiliations");
                i.putExtra("activity", "medicalScreen");
                i.putExtra("zipCode", tv_zipodeDoctors.getText().toString());
                i.putExtra("city", etCityDoctors.getText().toString());
                startActivityForResult(i, 102);
            }
        });
        btn_doctor.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btn_doctor.setBackgroundColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.light_blue));
                btn_doctor.setTextColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.view_bg));
                btn_facilities.setBackgroundColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.view_bg));
                btn_facilities.setTextColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.light_blue));
                llDoctors.setVisibility(View.VISIBLE);
                ll_Facilities.setVisibility(View.GONE);
                doctorlayout = true;
                llCheck.setVisibility(View.VISIBLE);
                seekbarDoctors.setInitialIndex(View.VISIBLE);
                checkFieldsForEmptyValues();
            }
        });
        btn_facilities.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                miles = 0;
                btn_facilities.setBackgroundColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.light_blue));
                btn_facilities.setTextColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.view_bg));
                btn_doctor.setBackgroundColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.view_bg));
                btn_doctor.setTextColor(ContextCompat.getColor(MedicalScreenActivity.this, R.color.light_blue));
                ll_Facilities.setVisibility(View.VISIBLE);
                llDoctors.setVisibility(View.GONE);
                doctorlayout = false;
                llCheck.setVisibility(View.GONE);
                seekbarDoctors.setInitialIndex(0);
                checkFieldsForEmptyValues();
            }
        });
        tvSpeciality.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MedicalScreenActivity.this, SpecialityScreenActivity.class);
                i.putExtra("activity", "medicalScreen");
                i.putExtra("fieldName", specialty);
                startActivityForResult(i, 100);
            }
        });
        btn_doctor_search.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (doctorlayout) {
                    if (tv_zipodeDoctors.getText().toString().trim().length() > 0 && tv_zipodeDoctors.getText().toString().trim().length() < 3) {
                        Toast.makeText(MedicalScreenActivity.this, R.string.zipcode_validation, Toast.LENGTH_SHORT).show();
                    } else if (checkInternet()) {
                        savedDoctorsData();
                    } else {
                        Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                    }
                } else if (tv_zipcodeFacility.getText().toString().trim().length() > 0 && tv_zipcodeFacility.getText().toString().trim().length() < 3) {
                    Toast.makeText(MedicalScreenActivity.this, R.string.zipcode_validation, Toast.LENGTH_SHORT).show();
                } else if (checkInternet()) {
                    SavedFacilityData();
                } else {
                    Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_typeFacility.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                facilities = true;
                try {
                    if (facility_list_data.size() > 0) {
                        facilityCustomAlertDialog(MedicalScreenActivity.this, facility_list_data);
                    }
                } catch (Exception e) {
                    e.getMessage();
                    Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvFacilitySubtype.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                facilities = false;
                try {
                    if (facility_subType_list_data.size() > 0) {
                        facilityCustomAlertDialog(MedicalScreenActivity.this, facility_subType_list_data);
                    }
                } catch (Exception e) {
                    e.getMessage();
                    Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_zipodeDoctors.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                tv_zipodeDoctors.setCursorVisible(true);
            }
        });
        tv_zipcodeFacility.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                tv_zipcodeFacility.setCursorVisible(true);
            }
        });
        btn_doctor_reset.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (doctorlayout) {
                    tvSpeciality.setText("");
                    tv_zipodeDoctors.setText("");
                    spinnerStateDoctors.setSelection(0);
                    tvDovtorsFirstName.setText("");
                    tvDovtorsLastName.setText("");
                    tv_language.setText("");
                    etCityDoctors.setText("");
                    tvHospitalAffiliation.setText("");
                    seekbarDoctors.setInitialIndex(0);
                    chkMale.setChecked(false);
                    chkFemale.setChecked(false);
                    return;
                }
                tv_typeFacility.setText("");
                tv_facilityName.setTag("");
                tvFacilitySubtype.setText("");
                tv_zipcodeFacility.setText("");
                spinnerCityFacility.setText("");
                spinnerStateFacility.setSelection(0);
                tv_facilityName.setText("");
                seekbarDoctors.setInitialIndex(0);
            }
        });
        spinnerStateDoctors.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                checkFieldsForEmptyValues();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerStateFacility.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                checkFieldsForEmptyValues();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getFacilityTypeService() {
        ApiInterface apiInterface = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        FacilityTypeReqPayLoad facilityTypeReqPayLoad = new FacilityTypeReqPayLoad();
        facilityTypeReqPayLoad.setFacilityType("");
        ArrayList<FacilityTypeReqPayLoad> facilityTypeReqPayLoads = new ArrayList();
        facilityTypeReqPayLoads.add(facilityTypeReqPayLoad);
        apiInterface.getFacilityTypeService(facilityTypeReqPayLoads).enqueue(new Callback<ArrayList<FacilityTypeResPayLoad>>() {
            public void onResponse(Call<ArrayList<FacilityTypeResPayLoad>> call, Response<ArrayList<FacilityTypeResPayLoad>> response) {
                if (response.isSuccessful()) {
                    ArrayList<FacilityTypeResPayLoad> facilityTypeResPayLoad = (ArrayList) response.body();
                    facility_list_data = new ArrayList();
                    facility_code_list = new ArrayList();
                    dismissProgressDialog();
                    if (facilityTypeResPayLoad.size() > 0) {
                        for (int i = 0; i < facilityTypeResPayLoad.size(); i++) {
                            facility_list_data.add(((FacilityTypeResPayLoad) facilityTypeResPayLoad.get(i)).getDescription());
                            facility_code_list.add(((FacilityTypeResPayLoad) facilityTypeResPayLoad.get(i)).getFacilityType());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    }
                    dismissProgressDialog();
                }
            }

            public void onFailure(Call<ArrayList<FacilityTypeResPayLoad>> call, Throwable t) {
                Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                dismissProgressDialog();
            }
        });
    }

    private void doctorPastSearch() {
        String gender;
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        DoctorPastSearchReqPayload doctorPastSearchReqPayload = new DoctorPastSearchReqPayload();
        doctorPastSearchReqPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        doctorPastSearchReqPayload.setZipCode(tv_zipodeDoctors.getText().toString());
        doctorPastSearchReqPayload.setRanges(Integer.valueOf(miles));
        doctorPastSearchReqPayload.setCity(etCityDoctors.getText().toString().length() == 0 ? "" : etCityDoctors.getText().toString());
        doctorPastSearchReqPayload.setState(spinnerStateDoctors.getSelectedItemPosition() == 0 ? "" : spinnerStateDoctors.getSelectedItem().toString());
        doctorPastSearchReqPayload.setFirstName(tvDovtorsFirstName.getText().toString().length() == 0 ? "" : tvDovtorsFirstName.getText().toString());
        doctorPastSearchReqPayload.setLastName(tvDovtorsLastName.getText().toString().length() == 0 ? "" : tvDovtorsLastName.getText().toString());
        if (chkMale.isChecked() && chkFemale.isChecked()) {
            gender = "";
        } else if (chkFemale.isChecked()) {
            gender = "female";
        } else if (chkMale.isChecked()) {
            gender = "male";
        } else {
            gender = "";
        }
        doctorPastSearchReqPayload.setGender(gender);
        doctorPastSearchReqPayload.setSpecialities(tvSpeciality.getText().toString());
        doctorPastSearchReqPayload.setLanguages(tv_language.getText().toString());
        doctorPastSearchReqPayload.setHospitalAffiliations(tvHospitalAffiliation.getText().toString());
        doctorPastSearchReqPayload.setIP("");
        doctorPastSearchReqPayload.setDeviceType("M");
        doctorPastSearchReqPayload.setBrowser("");
        doctorPastSearchReqPayload.setOperatingSystem("A");
        ArrayList<DoctorPastSearchReqPayload> doctorPastSearchReqPayloads = new ArrayList();
        doctorPastSearchReqPayloads.add(doctorPastSearchReqPayload);
        apiService.docPastSearch(doctorPastSearchReqPayloads).enqueue(new Callback<ArrayList<InsertRecentSearchRespPayLoad>>() {
            public void onResponse(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Response<ArrayList<InsertRecentSearchRespPayLoad>> response) {
                if (!response.isSuccessful()) {
                }
            }

            public void onFailure(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Throwable t) {
                Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void postDoctorsService() {
        try {
            String stateDoctor;
            String gender;
            String cityDoctor = etCityDoctors.getText().toString();
            if (spinnerStateDoctors.getSelectedItemPosition() == 0) {
                stateDoctor = "";
            } else {
                stateDoctor = spinnerStateDoctors.getSelectedItem().toString();
            }
            LatLng latLng = zipToLanLong(tv_zipodeDoctors.getText().toString());
            ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
            SearchPractitionerReqPayLoad searchPractitionerReqPayLoad = new SearchPractitionerReqPayLoad();
            searchPractitionerReqPayLoad.setZipCode(tv_zipodeDoctors.getText().toString());
            searchPractitionerReqPayLoad.setCity(cityDoctor);
            searchPractitionerReqPayLoad.setState(stateDoctor);
            if (cityDoctor.length() <= 0 || stateDoctor.length() <= 0) {
                searchPractitionerReqPayLoad.setRadius(String.valueOf(miles));
            } else {
                searchPractitionerReqPayLoad.setRadius("0");
            }
            if (miles == 0) {
                searchPractitionerReqPayLoad.setLatitude("");
                searchPractitionerReqPayLoad.setLongitude("");
            } else {
                searchPractitionerReqPayLoad.setLatitude(String.valueOf(latLng.latitude));
                searchPractitionerReqPayLoad.setLongitude(String.valueOf(latLng.longitude));
            }
            searchPractitionerReqPayLoad.setSpeciality(tvSpeciality.getText().toString());
            searchPractitionerReqPayLoad.setFirstName(tvDovtorsFirstName.getText().toString().length() == 0 ? "" : tvDovtorsFirstName.getText().toString());
            searchPractitionerReqPayLoad.setLastName(tvDovtorsLastName.getText().toString().length() == 0 ? "" : tvDovtorsLastName.getText().toString());
            if (chkMale.isChecked() && chkFemale.isChecked()) {
                gender = "";
            } else if (chkFemale.isChecked()) {
                gender = "female";
            } else if (chkMale.isChecked()) {
                gender = "male";
            } else {
                gender = "";
            }
            searchPractitionerReqPayLoad.setGender(gender);
            searchPractitionerReqPayLoad.setLanguages(tv_language.getText().toString());
            searchPractitionerReqPayLoad.setHospitals(tvHospitalAffiliation.getText().toString());
            searchPractitionerReqPayLoad.setUserId(Integer.valueOf(prefsLoginData.getInt("userId", 1)));
            searchPractitionerReqPayLoad.setIPAddr("");
            searchPractitionerReqPayLoad.setOS("");
            searchPractitionerReqPayLoad.setBrowser("");
            searchPractitionerReqPayLoad.setDevice("");
            ArrayList<SearchPractitionerReqPayLoad> searchPractitionerReqPayLoadsList = new ArrayList();
            searchPractitionerReqPayLoadsList.add(searchPractitionerReqPayLoad);
            Log.v("medicaldocter", "" + new Gson().toJson(searchPractitionerReqPayLoadsList));
            apiService.getSearchPractitioner(searchPractitionerReqPayLoadsList).enqueue(new Callback<ArrayList<SearchPractitionerResPayLoad>>() {
                public void onResponse(Call<ArrayList<SearchPractitionerResPayLoad>> call, Response<ArrayList<SearchPractitionerResPayLoad>> response) {
                    if (response.isSuccessful()) {
                        resPayloads = response.body();
                        ArrayList<String> facilitySubTypeList = new ArrayList();
                        if (resPayloads.size() <= 0) {
                            DialogClass.createDAlertDialog(MedicalScreenActivity.this, getString(R.string.we_are_sorry));
                        } else if (((SearchPractitionerResPayLoad) resPayloads.get(0)).getName() == null || ((SearchPractitionerResPayLoad) resPayloads.get(0)).getName().isEmpty() || ((SearchPractitionerResPayLoad) resPayloads.get(0)).getName().equalsIgnoreCase("null")) {
                            DialogClass.createDAlertDialog(MedicalScreenActivity.this, getString(R.string.we_are_sorry));
                        } else {
                            Iterator it = resPayloads.iterator();
                            while (it.hasNext()) {
                                SearchPractitionerResPayLoad searchPractitionerResPayLoad = (SearchPractitionerResPayLoad) it.next();
                                Iterator it2 = insertRecentSearchRespPayLoad.iterator();
                                while (it2.hasNext()) {
                                    if (searchPractitionerResPayLoad.getProviderID().equals(((StatusResponseForTotalProject) it2.next()).getPractitionerProviderId())) {
                                        searchPractitionerResPayLoad.setSavedStatus(1);
                                        break;
                                    }
                                    searchPractitionerResPayLoad.setSavedStatus(0);
                                }
                            }
                            recentDbHelper.removeRecordsFromDoctorFilter();
                            recentDbHelper.insertDoctorsResult(resPayloads);
                            String stateShortName = "";
                            try {
                                List<Address> addresses = geocoder.getFromLocationName(tv_zipodeDoctors.getText().toString(), 1);
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
                            Editor editor = getSharedPreferences("doctorType", 0).edit();
                            editor.putString("zipCode", tv_zipodeDoctors.getText().toString());
                            if (tv_zipodeDoctors.getText().toString().length() == 0) {
                                editor.putString("city", etCityDoctors.getText().toString());
                                editor.putString("state", spinnerStateDoctors.getSelectedItem().toString());
                            } else {
                                editor.putString("city", city);
                                editor.putString("state", stateShortName);
                            }
                            editor.putString("doctorSpecialty", tvSpeciality.getText().toString().length() > 0 ? tvSpeciality.getText().toString() : "");
                            editor.commit();
                            startActivity(new Intent(MedicalScreenActivity.this, DoctorSearchResults.class));
                        }
                        dismissProgressDialog();
                        return;
                    }
                    dismissProgressDialog();
                }

                public void onFailure(Call<ArrayList<SearchPractitionerResPayLoad>> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {
        }
    }

    protected void onPause() {
        dismissProgressDialog();
        super.onPause();
    }

    private void facPastSearch() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        FacPastSearchReqPayload facPastSearchReqPayload = new FacPastSearchReqPayload();
        facPastSearchReqPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        facPastSearchReqPayload.setZipCode(tv_zipcodeFacility.getText().toString());
        facPastSearchReqPayload.setRanges(String.valueOf(miles));
        facPastSearchReqPayload.setCity(spinnerCityFacility.getText().toString().length() == 0 ? spinnerCityFacility.getText().toString() : "");
        facPastSearchReqPayload.setState(spinnerStateFacility.getSelectedItemPosition() == 0 ? "" : spinnerStateFacility.getSelectedItem().toString());
        facPastSearchReqPayload.setFacilityName(tv_facilityName.getText().toString());
        facPastSearchReqPayload.setFacilityType(tv_typeFacility.getText().toString());
        facPastSearchReqPayload.setFacilitySubType(tvFacilitySubtype.getText().toString());
        facPastSearchReqPayload.setIP("");
        facPastSearchReqPayload.setOperatingSystem("A");
        facPastSearchReqPayload.setBrowser("");
        facPastSearchReqPayload.setDeviceType("M");
        ArrayList<FacPastSearchReqPayload> facPastSearchReqPayloads = new ArrayList();
        facPastSearchReqPayloads.add(facPastSearchReqPayload);
        Gson gson = new Gson();
        String request = gson.toJson(facPastSearchReqPayloads);
        Log.v("Upgrade", "Upgrade>>>>>>"+request);
        apiService.facPastSearch(facPastSearchReqPayloads).enqueue(new Callback<ArrayList<InsertRecentSearchRespPayLoad>>() {
            public void onResponse(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Response<ArrayList<InsertRecentSearchRespPayLoad>> response) {
                if (!response.isSuccessful()) {
                }
            }

            public void onFailure(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Throwable t) {
                Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void postFacilityService() {
        String stateFacility;
        String cityFacility = spinnerCityFacility.getText().toString();
        if (spinnerStateFacility.getSelectedItemPosition() == 0) {
            stateFacility = "";
        } else {
            stateFacility = spinnerStateFacility.getSelectedItem().toString();
        }
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        LatLng latLng = zipToLanLong(tv_zipcodeFacility.getText().toString());
        SearchFacilitiesReqPayLoad searchFacilitiesReqPayLoad = new SearchFacilitiesReqPayLoad();
        searchFacilitiesReqPayLoad.setZipCode(tv_zipcodeFacility.getText().toString());
        searchFacilitiesReqPayLoad.setFacilityName(tv_facilityName.getText().toString());
        searchFacilitiesReqPayLoad.setTypeOfFacility(shortName);
        searchFacilitiesReqPayLoad.setSubTypeOfFacility(tvFacilitySubtype.getText().toString());
        searchFacilitiesReqPayLoad.setCity(cityFacility);
        searchFacilitiesReqPayLoad.setState(stateFacility);
        if (cityFacility.length() <= 0 || stateFacility.length() <= 0) {
            searchFacilitiesReqPayLoad.setRadius(String.valueOf(miles));
        } else {
            searchFacilitiesReqPayLoad.setRadius("0");
        }
        if (miles == 0) {
            searchFacilitiesReqPayLoad.setLatitude("");
            searchFacilitiesReqPayLoad.setLongitude("");
        } else {
            searchFacilitiesReqPayLoad.setLatitude(String.valueOf(latLng.latitude));
            searchFacilitiesReqPayLoad.setLongitude(String.valueOf(latLng.longitude));
        }
        searchFacilitiesReqPayLoad.setFlag("");
        searchFacilitiesReqPayLoad.setSpeciality("");
        searchFacilitiesReqPayLoad.setFirstName("");
        searchFacilitiesReqPayLoad.setLastName("");
        searchFacilitiesReqPayLoad.setGender("");
        searchFacilitiesReqPayLoad.setLanguages("");
        searchFacilitiesReqPayLoad.setHospitals("");
        searchFacilitiesReqPayLoad.setUserId(Integer.valueOf(prefsLoginData.getInt("userId", 1)));
        searchFacilitiesReqPayLoad.setIPAddr("");
        searchFacilitiesReqPayLoad.setOS("M");
        searchFacilitiesReqPayLoad.setBrowser("");
        searchFacilitiesReqPayLoad.setDevice("A");
        ArrayList<SearchFacilitiesReqPayLoad> searchFacilitiesReqPayLoads = new ArrayList();
        searchFacilitiesReqPayLoads.add(searchFacilitiesReqPayLoad);
        Gson gson = new Gson();
        String request = gson.toJson(searchFacilitiesReqPayLoads);
        Log.v("Upgrade", "Upgrade>>>>>>"+request);
        apiService.getSearchFacilities(searchFacilitiesReqPayLoads).enqueue(new Callback<ArrayList<SearchFacilitiesResPayLoad>>() {
            public void onResponse(Call<ArrayList<SearchFacilitiesResPayLoad>> call, Response<ArrayList<SearchFacilitiesResPayLoad>> response) {
                if (response.isSuccessful()) {
                    resPayloadsForFacilities = (ArrayList) response.body();
                    ArrayList<String> facilitySubTypeList = new ArrayList();
                    if (resPayloadsForFacilities.size() > 0) {
                        Iterator it = resPayloadsForFacilities.iterator();
                        while (it.hasNext()) {
                            SearchFacilitiesResPayLoad searchFacilitiesResPayLoad = (SearchFacilitiesResPayLoad) it.next();
                            Iterator it2 = responseForTotalProjects.iterator();
                            while (it2.hasNext()) {
                                if (searchFacilitiesResPayLoad.getProviderID().equals(((StatusResponseForTotalProject) it2.next()).getFacilityProviderId())) {
                                    searchFacilitiesResPayLoad.setSavedItem(1);
                                    break;
                                }
                                searchFacilitiesResPayLoad.setSavedItem(0);
                            }
                        }
                        recentDbHelper.removeRecordsFromFacilityFilter();
                        recentDbHelper.insertFacilityResult(resPayloadsForFacilities);
                        String stateShortName = "";
                        try {
                            List<Address> addresses = geocoder.getFromLocationName(tv_zipcodeFacility.getText().toString(), 1);
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
                        Editor editor = getSharedPreferences("facilityPref", 0).edit();
                        editor.putString("zipCode", tv_zipcodeFacility.getText().toString());
                        if (tv_zipcodeFacility.getText().toString().length() == 0) {
                            editor.putString("city", spinnerCityFacility.getText().toString());
                            editor.putString("state", spinnerStateFacility.getSelectedItem().toString());
                        } else {
                            editor.putString("city", city);
                            editor.putString("state", stateShortName);
                        }
                        editor.putString("facilityType", tv_typeFacility.getText().toString().length() > 0 ? tv_typeFacility.getText().toString() : "");
                        editor.putString("subFacility", tvFacilitySubtype.getText().toString().length() > 0 ? tvFacilitySubtype.getText().toString() : "");
                        editor.commit();
                        startActivity(new Intent(MedicalScreenActivity.this, FacilitySearchResult.class));
                    } else {
                        DialogClass.createDAlertDialog(MedicalScreenActivity.this, getString(R.string.we_are_sorry));
                    }
                    dismissProgressDialog();
                }
            }

            public void onFailure(Call<ArrayList<SearchFacilitiesResPayLoad>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getFacilitySubType(String facilityType) {
        showProgressDialog(MedicalScreenActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        FacilityTypeReqPayLoad facilityTypeReqPayLoad = new FacilityTypeReqPayLoad();
        facilityTypeReqPayLoad.setFacilityType(facilityType);
        ArrayList<FacilityTypeReqPayLoad> facilityTypeReqPayLoads = new ArrayList();
        facilityTypeReqPayLoads.add(facilityTypeReqPayLoad);
        apiService.getFacilitySubTypeService(facilityTypeReqPayLoads).enqueue(new Callback<ArrayList<FacilitySubTypeResPayload>>() {
            public void onResponse(Call<ArrayList<FacilitySubTypeResPayload>> call, Response<ArrayList<FacilitySubTypeResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<FacilitySubTypeResPayload> resPayloads = (ArrayList) response.body();
                    facility_subType_list_data = new ArrayList();
                    for (int i = 0; i < resPayloads.size(); i++) {
                        facility_subType_list_data.add(((FacilitySubTypeResPayload) resPayloads.get(i)).getFacilitySubType());
                    }
                    dismissProgressDialog();
                }
            }

            public void onFailure(Call<ArrayList<FacilitySubTypeResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 100) {
            checkFieldsForEmptyValues();
            tvSpeciality.setText(data.getStringExtra("fieldValue"));
        } else if (requestCode == 101) {
            tv_language.setText(data.getStringExtra("fieldValue"));
        } else {
            tvHospitalAffiliation.setText(data.getStringExtra("fieldValue"));
        }
    }

    protected void facilityCustomAlertDialog(Context context, ArrayList<String> facility_list_data) {
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
        customlistView.setAdapter(new CustomAdapterDialog(context, facility_list_data));
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        customlistView.setOnItemClickListener(MedicalScreenActivity.this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (facilities) {
            if (checkInternet()) {
                shortName = (String) facility_code_list.get(position);
                getFacilitySubType(shortName);
            } else {
                Toast.makeText(MedicalScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }
            tv_typeFacility.setText((CharSequence) facility_list_data.get(position));
            tvFacilitySubtype.setVisibility(View.VISIBLE);
            tvFacilitySubtype.setText("");
        } else {
            tvFacilitySubtype.setText((CharSequence) facility_subType_list_data.get(position));
        }
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog.cancel();
        }
    }

    public void onStart() {
        super.onStart();
        client.connect();
    }

    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(MedicalScreenActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (!checkInternet() || latitude == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
                return;
            } else if (Application_holder.zipCode.length() == 0) {
                new GetZipAync(MedicalScreenActivity.this, latitude, longitude).execute(new Object[0]);
                return;
            } else {
                return;
            }
        }
        gps.showSettingsAlert(MedicalScreenActivity.this);
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

    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    public void onBackPressed() {
        super.onBackPressed();
        miles = 0;
        finish();
    }

    public void setValueToMedical(String doctorLocation, String fieldName) {
        if (fieldName.equalsIgnoreCase("locationField")) {
            llCheck.setVisibility(View.VISIBLE);
            llTab.setVisibility(View.VISIBLE);
            llBottomTab.setVisibility(View.VISIBLE);
            return;
        }
        tv_facilityName.setText(doctorLocation);
    }

    private void checkFieldsForEmptyValues() {
        String specialty = tvSpeciality.getText().toString();
        String zipcode_value = tv_zipodeDoctors.getText().toString();
        String city_doc = etCityDoctors.getText().toString();
        String zipcode_facilty = tv_zipcodeFacility.getText().toString();
        String city_fac = spinnerCityFacility.getText().toString();
        String facilityType = tv_typeFacility.getText().toString();
        String facilitySubType = tvFacilitySubtype.getText().toString();
        if (doctorlayout) {
            if ((zipcode_value.equals("") || specialty.equals("")) && (city_doc.equals("") || spinnerStateDoctors.getSelectedItemPosition() == 0 || specialty.equals(""))) {
                btn_inactive_doctor_search.setVisibility(View.VISIBLE);
                btn_doctor_search.setVisibility(View.GONE);
                return;
            }
            btn_inactive_doctor_search.setVisibility(View.GONE);
            btn_doctor_search.setVisibility(View.VISIBLE);
        } else if ((zipcode_facilty.equals("") || facilityType.equals("") || facilitySubType.equals("")) && (city_fac.equals("") || facilityType.equals("") || spinnerStateFacility.getSelectedItemPosition() == 0 || facilitySubType.equals(""))) {
            btn_doctor_search.setVisibility(View.GONE);
            btn_inactive_doctor_search.setVisibility(View.VISIBLE);
        } else {
            btn_inactive_doctor_search.setVisibility(View.GONE);
            btn_doctor_search.setVisibility(View.VISIBLE);
        }
    }

    public void savedDoctorsData() {
        showProgressDialog(MedicalScreenActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterDoctors> saveForLaterRequestList = new ArrayList();
        SaveForLaterDoctors saveForLaterRequest = new SaveForLaterDoctors();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setPractitionerProviderId("1");
        saveForLaterRequest.setFlag("2");
        saveForLaterRequest.setZipCode("");
        saveForLaterRequest.setCity("");
        saveForLaterRequest.setState("");
        saveForLaterRequest.setLocationKey("");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDoctor(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                if (response.isSuccessful()) {
                    insertRecentSearchRespPayLoad = (ArrayList) response.body();
                    postDoctorsService();
                    doctorPastSearch();
                }
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void SavedFacilityData() {
        showProgressDialog(MedicalScreenActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MedicalScreenActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterFacility> saveForLaterRequestList = new ArrayList();
        SaveForLaterFacility saveForLaterRequest = new SaveForLaterFacility();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("2");
        saveForLaterRequest.setFacilityProviderId("1");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterFacility(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                responseForTotalProjects = (ArrayList) response.body();
                postFacilityService();
                facPastSearch();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    protected class CustomAdapterDialog extends BaseAdapter {
        Context context;
        ArrayList<String> typeOfFacility = new ArrayList();

        private class ViewHolder {
            TextView txtTitle;

            private ViewHolder() {
            }
        }

        public CustomAdapterDialog(Context context, ArrayList<String> typeOfFacility) {
            this.typeOfFacility = typeOfFacility;
            this.context = context;
        }

        public int getCount() {
            return typeOfFacility.size();
        }

        public Object getItem(int position) {
            return typeOfFacility.get(position);
        }

        public long getItemId(int position) {
            return (long) typeOfFacility.indexOf(getItem(position));
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
            holder.txtTitle.setText((CharSequence) typeOfFacility.get(position));
            return convertView;
        }
    }

    public class GetZipAync extends AsyncTask {
        Context context;
        double latitude;
        double longitude;
        ProgressDialog pDialog;
        String result = "";

        public GetZipAync(Context context, double latitude, double longitude) {
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
            tv_zipcodeFacility.setText(Application_holder.zipCode);
            tv_zipodeDoctors.setText(Application_holder.zipCode);
        }
    }
}
