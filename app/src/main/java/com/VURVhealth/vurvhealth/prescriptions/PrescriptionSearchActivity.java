package com.VURVhealth.vurvhealth.prescriptions;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.adapters.CustomAdapter;
import com.VURVhealth.vurvhealth.adapters.CustomAdapter1;
import com.VURVhealth.vurvhealth.authentication.loginPojos.LoginResPayLoad;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.onboarding.MainActivity;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugInfoResponse;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameRes1;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNdcReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNdcRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNdcRes1;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugQntReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugQntRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugStrengthReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugStrengthRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.GenerateTokenReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.GenerateTokenRes;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchReqPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchRespPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.PrescriptionSavedDataResponse;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.RecentSearchRequestPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.RecentSearchResponcePayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrescriptionSearchActivity extends SuperAppCompactActivity {
    public static List<DrugSearchResultResPayLoad.Datum> brandSearchResultResPayLoads;

    static ArrayList<DrugInfoResponse> drugInfo;
    static DrugNameRes drugNamesArray;
    private ArrayList<DrugNameRes1> drugNamesArray1;
    private static String drugName = "";
    protected static boolean isGeneric;
    private boolean switchGeneric = true;

    public static List<DrugSearchResultResPayLoad.Datum> genericSearchResultResPayLoads;
    public static ArrayList<PrescriptionSavedDataResponse> savedDataResponses;
    static SearchResultReqPayLoad searchResultResPayLoad;
    private ImageView backBtn;
    private Button btn_search;
    private Button btn_search_inactive;
    ArrayList<String> cityorZipcodearr = new ArrayList();
    private CustomAdapter customAdapter;
    private CustomAdapter1 customAdapter1;
    ArrayList<String> drugArrayList;
    private String drugDose;
    private String drugForm;
    private int pos;
    private String drugGenNDC;
    private String drugId;
    private String drugNDC, drugNDC1, drugFormName;
    private String drug_name = "", token = "";
    ArrayList<RecentDrugPojo> drugs;
    private boolean firstClick = false;
    private Geocoder geocoder;
    GPSTracker gps;
    private double latitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private LinearLayout llCitySearch;
    private LinearLayout llDrugSearch;
    private LinearLayout llHelp;
    private LinearLayout llLocation;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llVurv;
    private LinearLayout llswitch;
    private LinearLayout spinner_list_ll;
    private double longitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private ProgressDialog pDialog;
    SqLiteDbHelper recentDbHelper;
    ArrayList<String> recentDrugName;
    ArrayList<String> recentList = new ArrayList();
    private RecyclerView search_list;
    private SwitchCompat switchDrug;
    private Context context = PrescriptionSearchActivity.this;
    private  UserSharedPreferences sharedPreferences;


    private TextView tvClear;
    private EditText tv_CityorZipcode;
    private EditText tv_DrugName;
    private TextView str_of_drug, qnt_of_drug, type_drug_txt;
    private String vurvID;
    private String zipCode;

    private Spinner str_of_drug_spinner, qnt_of_drug_spinner, type_of_drug_spinner;
    private TextView qnt_txt, str_txt;
    private ArrayList<String> drug_str_array = new ArrayList();
    private ArrayList<Integer> drug_id_array = new ArrayList();
    private ArrayList<String> drug_str_array_forms = new ArrayList<>();
    private ArrayList<String> drug_qnt_array = new ArrayList();
    private ArrayList<Integer> drug_qnt_id_array = new ArrayList();
    private ArrayAdapter<String> strengthArrayAdapter;

    private StrengthSpinner strengthBaseAdapter;
    private QuantitySpinner quantitiyBaseAdapter;
    private int perviousPos;
    private LinearLayout strength_ll_spinner, type_ll_spinner;
    private String TAG = "PrescriptionSearch";
    private int drugFormPostion, drugQntyPosition;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_search_screen);
        SharedPreferences prefsData = getSharedPreferences("VURVProfileDetails", 0);
        geocoder = new Geocoder(this);
        vurvID = prefsData.getString("vurvId", "");
        switchDrug = (SwitchCompat) findViewById(R.id.switchDrug);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search_inactive = (Button) findViewById(R.id.btn_search_inactive);
        tvClear = (TextView) findViewById(R.id.tvClear);
        search_list = (RecyclerView) findViewById(R.id.search_list);
        search_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        search_list.setItemAnimator(new DefaultItemAnimator());
        llCitySearch = (LinearLayout) findViewById(R.id.llCitySearch);
        llDrugSearch = (LinearLayout) findViewById(R.id.llDrugSearch);
        llswitch = (LinearLayout) findViewById(R.id.llswitch);
        llLocation = (LinearLayout) findViewById(R.id.llLocation);
        llLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_CityorZipcode.setText("Current Location");
                search_list.setVisibility(View.GONE);
                llswitch.setVisibility(View.VISIBLE);
            }
        });
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tv_DrugName = (EditText) findViewById(R.id.tv_DrugName);
        str_of_drug = (TextView) findViewById(R.id.str_of_drug);
        qnt_of_drug = (TextView) findViewById(R.id.qnt_of_drug);

        type_of_drug_spinner = (Spinner) findViewById(R.id.type_of_drug_spinner);
        str_of_drug_spinner = (Spinner) findViewById(R.id.str_of_drug_spinner);
        qnt_of_drug_spinner = (Spinner) findViewById(R.id.qnt_of_drug_spinner);
        qnt_txt = (TextView) findViewById(R.id.qnt_txt);
        str_txt = (TextView) findViewById(R.id.str_txt);

        qnt_txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                qnt_of_drug_spinner.performClick();

                qnt_of_drug_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        qnt_of_drug_spinner.setSelection(position);
                        qnt_txt.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        qnt_txt.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        generateToken(Application_holder.client_code, Application_holder.client_secret);


        drug_str_array.add(getResources().getString(R.string.str_drug));
        strengthBaseAdapter = new StrengthSpinner(drug_str_array);
        str_of_drug_spinner.setAdapter(strengthBaseAdapter);
        str_of_drug_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (drug_str_array.size() == 1) {
                        //str_of_drug_spinner.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Please select the drug name", Toast.LENGTH_SHORT).show();
                    }

                    if (drug_qnt_array.size() > 1) {
                        drug_qnt_array.clear();
                        //drug_qnt_array.add(getResources().getString(R.string.qnt_drug));
                        quantitiyBaseAdapter = new QuantitySpinner(drug_qnt_array);
                        qnt_of_drug_spinner.setAdapter(quantitiyBaseAdapter);

                        //getDrugQntService(tv_DrugName.getText().toString(), drug_str_array.get(perviousPos));
                        //getDrugQntService1(token, tv_DrugName.getText().toString(), 0);
                    }


                }

                return false;
            }
        });

        str_of_drug_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (drug_str_array.size() != 1&& drug_id_array!=null && drug_id_array.size()>0) {
                    perviousPos = drug_id_array.get(pos);

                    //getDrugQntService(tv_DrugName.getText().toString(), drug_str_array.get(pos));
                    getDrugQntService1(token, tv_DrugName.getText().toString(), perviousPos);
                } else {
                    perviousPos = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        drug_qnt_array.add(getResources().getString(R.string.qnt_drug));
        quantitiyBaseAdapter = new QuantitySpinner(drug_qnt_array);
        qnt_of_drug_spinner.setAdapter(quantitiyBaseAdapter);
        qnt_of_drug_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (drug_qnt_array.size() == 1) {
                        //str_of_drug_spinner.setEnabled(false);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_str_drug), Toast.LENGTH_SHORT).show();
                    } else {

                    }


                }

                return false;
            }
        });


        qnt_of_drug_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (drug_qnt_array.size() > 1 && drug_qnt_id_array!=null && drug_qnt_id_array.size()>0) {
                    drugQntyPosition = drug_qnt_id_array.get(pos);
                    if (strength_ll_spinner.getVisibility() == View.VISIBLE) {
                        //getDrugNdcService(tv_DrugName.getText().toString(), drug_str_array.get(str_of_drug_spinner.getSelectedItemPosition()), drug_qnt_array.get(pos));
                        getDrugNdcService1(token, tv_DrugName.getText().toString(), 0);
                    } else {
                        //getDrugNdcService(tv_DrugName.getText().toString(), " ", drug_qnt_array.get(pos));
                        getDrugNdcService1(token, tv_DrugName.getText().toString(), 0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        strength_ll_spinner = (LinearLayout) findViewById(R.id.strength_ll_spinner);
        spinner_list_ll = (LinearLayout) findViewById(R.id.spinner_list_ll);

        tv_CityorZipcode = (EditText) findViewById(R.id.tv_CityorZipcode);
        tv_CityorZipcode.setText(Application_holder.zipCode);
        tv_CityorZipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (Application_holder.zipCode.length() != 0) {
            tv_CityorZipcode.setText(Application_holder.zipCode);
        }
        zipCode = tv_CityorZipcode.getText().toString().trim();
        drug_name = tv_DrugName.getText().toString();
        tv_DrugName.setCursorVisible(false);
        tv_DrugName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_DrugName.setCursorVisible(true);
                tv_CityorZipcode.setCursorVisible(true);
                if (tv_DrugName.getText().toString().length() == 0) {
                    getRecentSeach();
                }
            }
        });
        tv_CityorZipcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                search_list.setVisibility(View.GONE);
                llCitySearch.setVisibility(View.GONE);
                llDrugSearch.setVisibility(View.GONE);
                llswitch.setVisibility(View.VISIBLE);
            }
        });
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrescriptionSearchActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrescriptionSearchActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrescriptionSearchActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrescriptionSearchActivity.this, FreshdeskMainListActivity.class));
//                startActivity(new Intent(PrescriptionSearchActivity.this, HelpActivity.class));
                finish();
            }
        });
        btn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int drugStrPos = str_of_drug_spinner.getSelectedItemPosition();
                int drugQntPos = qnt_of_drug_spinner.getSelectedItemPosition();

                /*if (strength_ll_spinner.getVisibility()==View.VISIBLE && str_of_drug.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_str_drug), Toast.LENGTH_SHORT).show();
                    return;
                } else if (qnt_of_drug.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_qnt_drug), Toast.LENGTH_SHORT).show();
                    return;
                }*/
                if (strength_ll_spinner.getVisibility() == View.VISIBLE && drugStrPos == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_str_drug), Toast.LENGTH_SHORT).show();
                } else if (drugQntPos == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_qnt_drug), Toast.LENGTH_SHORT).show();
                } else if (tv_CityorZipcode.getText().toString().trim().length() < 5) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_zip), Toast.LENGTH_SHORT).show();
                }else {
                    if(drug_qnt_array!=null) {
                        Editor editor = getSharedPreferences("SearchData", 0).edit();
                        editor.putString("drugId", drugId);
                        editor.putString("drugDose", drugDose);
                        editor.putString("DrugName", drugName);
                        editor.putString("drugForm", drugForm);
                        editor.putString("drugGenNDC", drugGenNDC);
                        editor.putString(Param.LOCATION, tv_CityorZipcode.getText().toString().trim());

                        editor.putString("drugNDC", drugNDC);
                        editor.putString("zipCode", tv_CityorZipcode.getText().toString().trim());
                        editor.putString("token", token);
                        editor.putString("drugNDC1", drugNDC1);
                        editor.putString("quantity", drug_qnt_array.get(qnt_of_drug_spinner.getSelectedItemPosition()));
                        editor.commit();
                        hideKeyboard(PrescriptionSearchActivity.this);
                        startActivity(new Intent(context, BestPricesNearbyActivity.class));
                    }else {
                        Toast.makeText(context, R.string.server_not_found, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        switchDrug.setChecked(true);
        switchDrug.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //drugType = "Generic";
                    switchGeneric = true;
                } else {
                    //drugType = "Brand";
                    switchGeneric = false;
                }
            }
        });
        tvClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                clearRecentSeach();
                llDrugSearch.setVisibility(View.GONE);
                search_list.setVisibility(View.GONE);
                llswitch.setVisibility(View.VISIBLE);
                if (recentDrugName != null) {
                    recentDrugName.clear();
                }
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                drugName = "";
                zipCode = "";
                tv_DrugName.setText("");
                tv_CityorZipcode.setText("");
                startActivity(new Intent(context, StartScreenActivity.class));
                finish();
            }
        });
        tv_DrugName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                drug_str_array.clear();
                str_of_drug.setText("");
                drug_qnt_array.clear();
                qnt_of_drug.setText("");
                tv_CityorZipcode.setText("");

                if (strength_ll_spinner.getVisibility() == View.GONE) {
                    strength_ll_spinner.setVisibility(View.VISIBLE);
                }

                drug_str_array.add(getResources().getString(R.string.str_drug));
                strengthBaseAdapter = new StrengthSpinner(drug_str_array);
                str_of_drug_spinner.setAdapter(strengthBaseAdapter);

                drug_qnt_array.add(getResources().getString(R.string.qnt_drug));
                quantitiyBaseAdapter = new QuantitySpinner(drug_qnt_array);
                qnt_of_drug_spinner.setAdapter(quantitiyBaseAdapter);

                if (s.length() >= 3 && !drugName.equalsIgnoreCase(s.toString().trim())) {
                    drugNDC = "";
                    drugGenNDC = "";
                    //getDrugNameService(s.toString(), "");
                    if (checkInternet()) {
                        getDrugNameService1(token, s.toString(), "");
                    } else {
                        Toast.makeText(context, R.string.no_network, Toast.LENGTH_SHORT).show();
                    }
                } else if (s.length() == 1 || s.length() == 2) {
                    search_list.setVisibility(View.GONE);
                    llCitySearch.setVisibility(View.GONE);
                    llDrugSearch.setVisibility(View.GONE);
                    llswitch.setVisibility(View.VISIBLE);
                }
                if (s.length() == 0) {
                    firstClick = false;
                    getRecentSeach();
                }
            }
        });

    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(PrescriptionSearchActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (!checkInternet() || latitude == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                Toast.makeText(PrescriptionSearchActivity.this, getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
                return;
            } else if (zipCode.length() == 0) {
                new GetZipAyncTask(PrescriptionSearchActivity.this, latitude, longitude).execute(new Object[0]);
                return;
            } else {
                return;
            }
        }
        gps.showSettingsAlert(PrescriptionSearchActivity.this);
    }

    private void onResponseAppend(List<DrugNameRes1> allDrugs) {
        customAdapter1 = new CustomAdapter1(PrescriptionSearchActivity.this, allDrugs, "drugsFiled", "prescription");
        search_list.setAdapter(customAdapter1);
        //customAdapter.filter(drug_name);
        llDrugSearch.setVisibility(View.GONE);
        search_list.setVisibility(View.VISIBLE);
        llswitch.setVisibility(View.GONE);
        llCitySearch.setVisibility(View.GONE);

        spinner_list_ll.setVisibility(View.GONE);
    }

    public void recentDrugList(String searchStr, int position, boolean recentItemClick) {
        try {
            pos = position;
            recentDbHelper = new SqLiteDbHelper(PrescriptionSearchActivity.this);
            recentList.clear();
            firstClick = true;
            llswitch.setVisibility(View.VISIBLE);
            search_list.setVisibility(View.GONE);
            llDrugSearch.setVisibility(View.GONE);
            drugName = searchStr;
            /* if (recentItemClick) {
                getDrugNameService(searchStr, "recentDrug");
                tv_DrugName.setText(searchStr);
                tv_DrugName.setSelection(searchStr.length());
                llswitch.setVisibility(View.VISIBLE);
                search_list.setVisibility(View.GONE);
                return;
            }
            drugForm = ((DrugInfoResponse) drugInfo.get(position)).getDOSFRMDESCN();
            drugDose = ((DrugInfoResponse) drugInfo.get(position)).getDRGSTRENGTH();
            drugId = ((DrugInfoResponse) drugInfo.get(position)).getDrugId();
            drugNDC = ((DrugInfoResponse) drugInfo.get(position)).getNDC();
            drugGenNDC = ((DrugInfoResponse) drugInfo.get(position)).getGNRCALTNDC();*/
            spinner_list_ll.setVisibility(View.VISIBLE);

            tv_DrugName.setText(searchStr);
            tv_DrugName.setSelection(searchStr.length());
            llswitch.setVisibility(View.VISIBLE);
            search_list.setVisibility(View.GONE);

            //showDailogForms();
            drugFormsService(token, tv_DrugName.getText().toString(), 0);
            //getDrugStrengthService(tv_DrugName.getText().toString().trim());


        } catch (Exception e) {
        }
    }

    public void recentHistoryListView() {
        recentDbHelper = new SqLiteDbHelper(PrescriptionSearchActivity.this);
        drugs = new ArrayList();
        if (recentDrugName != null && recentDrugName.size() > View.VISIBLE) {
            customAdapter = new CustomAdapter(PrescriptionSearchActivity.this, recentDrugName, "drugsFiled", "prescription", "recentlist");
            search_list.setAdapter(customAdapter);
            search_list.setVisibility(View.VISIBLE);
            llCitySearch.setVisibility(View.GONE);
            llDrugSearch.setVisibility(View.VISIBLE);
            llswitch.setVisibility(View.GONE);
        }
    }

    private void insertRecentSeach() {
        ApiInterface apiService = ApiClient.getClient(PrescriptionSearchActivity.this).create(ApiInterface.class);
        ArrayList<InsertRecentSearchReqPayLoad> insertRecentSearchReqPayLoadList = new ArrayList();
        InsertRecentSearchReqPayLoad insertRecentSearchReqPayLoad = new InsertRecentSearchReqPayLoad();
        insertRecentSearchReqPayLoad.setDrugName(tv_DrugName.getText().toString().trim());
        insertRecentSearchReqPayLoad.setSearchedLocation(tv_CityorZipcode.getText().toString().trim());
        insertRecentSearchReqPayLoad.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        insertRecentSearchReqPayLoad.setVurvId(vurvID);
        insertRecentSearchReqPayLoad.setBrowser("");
        insertRecentSearchReqPayLoad.setDeviceType("M");
        insertRecentSearchReqPayLoad.setIP("");
        insertRecentSearchReqPayLoad.setOperatingSystem("A");
        insertRecentSearchReqPayLoadList.add(insertRecentSearchReqPayLoad);

        Log.v("Prescripon post req ", "" + new Gson().toJson(insertRecentSearchReqPayLoad));

        apiService.insertRecentSearch(insertRecentSearchReqPayLoadList).enqueue(new Callback<ArrayList<InsertRecentSearchRespPayLoad>>() {
            public void onResponse(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Response<ArrayList<InsertRecentSearchRespPayLoad>> response) {
                ArrayList<InsertRecentSearchRespPayLoad> insertRecentSearchRespPayLoad = (ArrayList) response.body();
            }

            public void onFailure(Call<ArrayList<InsertRecentSearchRespPayLoad>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void getRecentSeach() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(PrescriptionSearchActivity.this).create(ApiInterface.class);
        ArrayList<RecentSearchRequestPayLoad> insertRecentSearchReqPayLoadList = new ArrayList();
        RecentSearchRequestPayLoad recentSearchRequestPayLoad = new RecentSearchRequestPayLoad();
        recentSearchRequestPayLoad.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        recentSearchRequestPayLoad.setFlag("1");
        insertRecentSearchReqPayLoadList.add(recentSearchRequestPayLoad);
        apiService.getPastSearchPriscription(insertRecentSearchReqPayLoadList).enqueue(new Callback<ArrayList<RecentSearchResponcePayLoad>>() {
            public void onResponse(Call<ArrayList<RecentSearchResponcePayLoad>> call, Response<ArrayList<RecentSearchResponcePayLoad>> response) {
                ArrayList<RecentSearchResponcePayLoad> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                if (insertRecentSearchRespPayLoad != null) {
                    recentDrugName = new ArrayList();
                    Iterator it = insertRecentSearchRespPayLoad.iterator();
                    while (it.hasNext()) {
                        RecentSearchResponcePayLoad data = (RecentSearchResponcePayLoad) it.next();
                        if (data.getPastData().trim().length() > 0) {
                            recentDrugName.add(data.getPastData());
                        }
                    }
                    if (recentDrugName != null && recentDrugName.size() > 0) {
                        customAdapter = new CustomAdapter(PrescriptionSearchActivity.this, recentDrugName, "drugsFiled", "prescription", "recentlist");
                        search_list.setAdapter(customAdapter);
                        search_list.setVisibility(View.VISIBLE);
                        llCitySearch.setVisibility(View.GONE);
                        llDrugSearch.setVisibility(View.VISIBLE);
                        llswitch.setVisibility(View.GONE);
                    }
                    dismissProgressDialog();
                }
            }

            public void onFailure(Call<ArrayList<RecentSearchResponcePayLoad>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void clearRecentSeach() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(PrescriptionSearchActivity.this).create(ApiInterface.class);
        ArrayList<RecentSearchRequestPayLoad> insertRecentSearchReqPayLoadList = new ArrayList();
        RecentSearchRequestPayLoad recentSearchRequestPayLoad = new RecentSearchRequestPayLoad();
        recentSearchRequestPayLoad.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        recentSearchRequestPayLoad.setFlag("1");
        insertRecentSearchReqPayLoadList.add(recentSearchRequestPayLoad);
        apiService.clearPastSearchPriscription(insertRecentSearchReqPayLoadList).enqueue(new Callback<ArrayList<RecentSearchResponcePayLoad>>() {
            public void onResponse(Call<ArrayList<RecentSearchResponcePayLoad>> call, Response<ArrayList<RecentSearchResponcePayLoad>> response) {
                if (!response.isSuccessful()) {
                }
            }

            public void onFailure(Call<ArrayList<RecentSearchResponcePayLoad>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void checkFieldsForEmptyValues() {
      /*  String drug_name = tv_DrugName.getText().toString();
        String zipcode_value = tv_CityorZipcode.getText().toString();
        String drugStr = str_of_drug.getText().toString();
        String drugQnt = qnt_of_drug.getText().toString();
        if (drug_name.equals("") || zipcode_value.equals("") || drugStr.length() == 0 || drugQnt.length() == 0 || drugNDC.length() == 0) {
            btn_search_inactive.setVisibility(View.VISIBLE);
            btn_search.setVisibility(View.GONE);
            return;
        }
        btn_search_inactive.setVisibility(View.GONE);
        btn_search.setVisibility(View.VISIBLE);*/

        String drug_name = tv_DrugName.getText().toString();
        String zipcode_value = tv_CityorZipcode.getText().toString();
        int drugStrPos = str_of_drug_spinner.getSelectedItemPosition();
        int drugQntPos = qnt_of_drug_spinner.getSelectedItemPosition();
        if (strength_ll_spinner.getVisibility() == View.VISIBLE) {
            if (drug_name.equals("") || zipcode_value.equals("") || drugStrPos == 0 || drugQntPos == 0) {
                btn_search_inactive.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.GONE);
                return;
            }
        } else if (strength_ll_spinner.getVisibility() == View.GONE) {
            if (drug_name.equals("") || zipcode_value.equals("") || drugQntPos == 0) {
                btn_search_inactive.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.GONE);
                return;
            }
        }
        btn_search_inactive.setVisibility(View.GONE);
        btn_search.setVisibility(View.VISIBLE);
    }

    public void onStart() {
        super.onStart();
        client.connect();
    }

    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(context, StartScreenActivity.class));
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        drugName = "";
        tv_DrugName.setText("");
        tv_CityorZipcode.setText("");
        dismissProgressDialog();
    }

    public void setZipCode() {
        tv_CityorZipcode.setText(zipCode);
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
                            zipCode = jsonObject1.getString("long_name");
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
            tv_CityorZipcode.setText(zipCode);
        }

    }

    public void showStrOfDrug() {


       /* final ArrayList<String> strRowList = new ArrayList<String>();
        strRowList.clear();
        strRowList.add("60 Mg");
        strRowList.add("40 Mg");
        strRowList.add("20 Mg");
        strRowList.add("10 Mg");*/

        final Dialog dialogComp = new Dialog(PrescriptionSearchActivity.this);
        dialogComp.setCancelable(true);
        dialogComp.setCanceledOnTouchOutside(true);
        dialogComp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogComp.setContentView(R.layout.cust_dialog);
        dialogComp.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final TextView headingName = (TextView) dialogComp.findViewById(R.id.select_str_txt);
        headingName.setText("Strengths List");
        final ListView strListView = (ListView) dialogComp.findViewById(R.id.strength_name);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(PrescriptionSearchActivity.this, R.layout.txt_inflator, R.id.txtName, drug_str_array) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView = (TextView) super.getView(position, convertView, parent);
                //textView.setTextColor(getResources().getColor(R.color.textDark));
                return textView;
            }

        };

        strListView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();


        strListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str_of_drug.setText("" + drug_str_array.get(position).toString());
                qnt_of_drug.setText("");
                //getDrugQntService(tv_DrugName.getText().toString(), drug_str_array.get(position).toString());
                //getDrugQntService1(token, tv_DrugName.getText().toString(), 0);
                dialogComp.dismiss();
            }
        });

        dialogComp.show();
    }

    public void showQntOfDrug() {

     /*   final ArrayList<String> strRowList = new ArrayList<String>();
        strRowList.clear();
        strRowList.add("1 Sheet ");
        strRowList.add("2 Sheets ");
        strRowList.add("3 Sheets ");
        strRowList.add("4 Sheets ");
        strRowList.add("5 Sheets ");*/


        final Dialog dialogComp = new Dialog(PrescriptionSearchActivity.this);
        dialogComp.setCancelable(true);
        dialogComp.setCanceledOnTouchOutside(true);
        dialogComp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogComp.setContentView(R.layout.cust_dialog);
        dialogComp.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final TextView headingName = (TextView) dialogComp.findViewById(R.id.select_str_txt);
        headingName.setText("Quantities List");
        final ListView strListView = (ListView) dialogComp.findViewById(R.id.strength_name);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(PrescriptionSearchActivity.this, R.layout.txt_inflator, R.id.txtName, drug_qnt_array) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView = (TextView) super.getView(position, convertView, parent);
                //textView.setTextColor(getResources().getColor(R.color.textDark));
                return textView;
            }

        };

        strListView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        strListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                qnt_of_drug.setText("" + drug_qnt_array.get(position).toString());
                //getDrugNdcService(tv_DrugName.getText().toString(), str_of_drug.getText().toString(), drug_qnt_array.get(position).toString());
                getDrugNdcService1(token, tv_DrugName.getText().toString(), 0);
                dialogComp.dismiss();
            }
        });

        dialogComp.show();
    }


    public class StrengthSpinner extends BaseAdapter {

        private ArrayList<String> strengthArray;
        private Context context;
        private LayoutInflater inflater;

        public StrengthSpinner(ArrayList<String> strengthArray) {
            this.strengthArray = strengthArray;
        }

        @Override
        public int getCount() {
            return strengthArray.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        class Holder {
            private TextView tvSpinnerVal;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            View myView = null;
            try {
                final Holder holder;
                myView = convertView;

                if (myView == null) {
                    inflater = (LayoutInflater) PrescriptionSearchActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.spinner_cust, null);

                    holder = new Holder();
                    holder.tvSpinnerVal = (TextView) myView.findViewById(R.id.strVal);
                    myView.setTag(holder);
                } else {
                    holder = (Holder) myView.getTag();
                }
                if (i == 0) {
                    holder.tvSpinnerVal.setTextColor(getResources().getColor(R.color.hintColor));
                    holder.tvSpinnerVal.setTextSize(18f);
                } else {
                    holder.tvSpinnerVal.setTextColor(getResources().getColor(R.color.blue));
                    holder.tvSpinnerVal.setTextSize(18f);
                }

                holder.tvSpinnerVal.setText(strengthArray.get(i));



              /*  holder.tvSpinnerVal.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // strengthBaseAdapter.notifyDataSetChanged();
                       *//* try {
                            Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                            method.setAccessible(true);
                            method.invoke(str_of_drug_spinner);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*//*

                    }
                });*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return myView;
        }

        // Below code is used to hide first item.

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = null;
            if (position == 0) {
                TextView tv = new TextView(PrescriptionSearchActivity.this);
                tv.setVisibility(View.GONE);
                tv.setHeight(0);
                v = tv;
                v.setVisibility(View.GONE);
            } else {
                v = super.getDropDownView(position, null, parent);
            }
            return v;

        }

    }


    public class QuantitySpinner extends BaseAdapter {

        private ArrayList<String> quantityArray;
        private Context context;
        private LayoutInflater inflater;

        public QuantitySpinner(ArrayList<String> quantityArray) {
            this.quantityArray = quantityArray;
        }

        @Override
        public int getCount() {
            return quantityArray.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        class Holder {
            private TextView tvSpinnerVal;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            View myView = null;
            try {
                Holder holder;
                myView = convertView;

                if (myView == null) {
                    inflater = (LayoutInflater) PrescriptionSearchActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.spinner_cust, null);

                    holder = new Holder();
                    holder.tvSpinnerVal = (TextView) myView.findViewById(R.id.strVal);
                    myView.setTag(holder);
                } else {
                    holder = (Holder) myView.getTag();
                }
                if (i == 0) {
                    holder.tvSpinnerVal.setTextColor(getResources().getColor(R.color.hintColor));
                    holder.tvSpinnerVal.setTextSize(18f);
                } else {
                    holder.tvSpinnerVal.setTextColor(getResources().getColor(R.color.blue));
                    holder.tvSpinnerVal.setTextSize(18f);
                }

                holder.tvSpinnerVal.setText(quantityArray.get(i));


            } catch (Exception e) {
                e.printStackTrace();
            }

            return myView;
        }

        // Below code is used to hide first item.

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = null;
            if (position == 0) {
                TextView tv = new TextView(PrescriptionSearchActivity.this);
                tv.setVisibility(View.GONE);
                tv.setHeight(0);
                v = tv;
                v.setVisibility(View.GONE);
            } else {
                v = super.getDropDownView(position, null, parent);
            }
            return v;

        }

    }

    //new Service Calls

    private void generateToken(int clientId, String clientSeceret) {

        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        GenerateTokenReq tokenReq = new GenerateTokenReq(clientId, clientSeceret);
        Gson gson = new Gson();
        String dat = gson.toJson(tokenReq);
        Log.v("TAG", "Request generateToken -->" + dat);

        apiService.getToken(tokenReq).enqueue(new Callback<GenerateTokenRes>() {
            @Override
            public void onResponse(Call<GenerateTokenRes> call, Response<GenerateTokenRes> response) {
                if (response.body() != null && response.isSuccessful()) {
                    token = response.body().getToken();
                    Log.v("Prescriptions", "Token--->: " + token);
                } else {
                    DialogClass.createDAlertDialog(context, getResources().getString(R.string.no_token_created));
                }

            }

            @Override
            public void onFailure(Call<GenerateTokenRes> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getDrugNameService1(String token, String query, final String recentDrug) {
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getDrugName1(token, query).enqueue(new Callback<ArrayList<DrugNameRes1>>() {
            @Override
            public void onResponse(Call<ArrayList<DrugNameRes1>> call, Response<ArrayList<DrugNameRes1>> response) {
                if (response.isSuccessful()) {
                    response.body();
                    Gson gson = new Gson();
                    String dat = gson.toJson(response.body());
                    Log.v("TAG", "Response getDrugNameOnPrescription-->" + dat);
                    drugNamesArray1 = response.body();
                    List<DrugNameRes1> stringList = new ArrayList();
                    if (drugNamesArray1 != null) {
                        for (int i = 0; i < drugNamesArray1.size(); i++) {
                            stringList.add((drugNamesArray1.get(i)));
                        }
                        if (drugNamesArray1.size() > 0 && !recentDrug.equalsIgnoreCase("recentDrug") && tv_DrugName.getText().toString().length() > 2) {
                            onResponseAppend(stringList);
                        }
                    } else if (tv_DrugName.getText().toString().length() > 2) {
                        llDrugSearch.setVisibility(View.GONE);
                        search_list.setVisibility(View.GONE);
                        llswitch.setVisibility(View.VISIBLE);
                        llCitySearch.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<DrugNameRes1>> call, Throwable t) {
                dismissProgressDialog();
                Log.v("Presc:", "Error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDrugNdcService1(String token, String seoName, int limit) {
        showProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getDrugNdc1(token, seoName, 0).enqueue(new Callback<DrugNdcRes1>() {
            @Override
            public void onResponse(Call<DrugNdcRes1> call, Response<DrugNdcRes1> response) {
                if (response.isSuccessful()) {
                    List<DrugNdcRes1.Drug> ndcRes1List = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value> ndcvalues = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value.Value_> ndcValues1 = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value.Value_.Value__> ndcValues2 = new ArrayList<>();
                    ndcRes1List = response.body().getResult().getDrugs();
                    Gson gson = new Gson();
                    String dat = gson.toJson(response.body());
                    Log.v("TAG", "Response getDrugNdc -->" + dat);

                    if (response != null && response.body() != null && ndcRes1List != null
                            && ndcRes1List.size() > 0) {
                        if (isGeneric == false) {
                            ndcvalues = ndcRes1List.get(0).getValues();
                            ndcValues1 = ndcvalues.get(drugFormPostion).getValues();
                            ndcValues2 = ndcValues1.get(perviousPos).getValues();
                            drugNDC = ndcValues2.get(drugQntyPosition).getValue().getNDC();

                            if (ndcRes1List.size() > 1) {
                                ndcvalues = ndcRes1List.get(1).getValues();
                                ndcValues1 = ndcvalues.get(0).getValues();
                                ndcValues2 = ndcValues1.get(0).getValues();
                                if (ndcValues2!=null && ndcValues2.size()>0) {
                                    drugNDC1 = ndcValues2.get(0).getValue().getNDC();
                                }
                            }
                        }
                        if (isGeneric == true) {
                            ndcvalues = ndcRes1List.get(0).getValues();
                            ndcValues1 = ndcvalues.get(drugFormPostion).getValues();
                            ndcValues2 = ndcValues1.get(perviousPos).getValues();
                            drugNDC1 = ndcValues2.get(drugQntyPosition).getValue().getNDC();
                        }
                    }
                    dismissProgressDialog();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<DrugNdcRes1> call, Throwable t) {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void drugFormsService(final String token, String seoName, int limit) {
        showProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getDrugNdc1(token, seoName, 0).enqueue(new Callback<DrugNdcRes1>() {
            @Override
            public void onResponse(Call<DrugNdcRes1> call, Response<DrugNdcRes1> response) {

                if (response.isSuccessful()) {

                    List<DrugNdcRes1.Drug> ndcRes1List = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value> ndcvalues = new ArrayList<>();
                    ndcRes1List = response.body().getResult().getDrugs();
                    Gson gson = new Gson();
                    String dat = gson.toJson(response.body());
                    Log.v("TAG", "Response getDrugStrength -->" + dat);

                    drug_str_array_forms.clear();
                    ndcvalues = ndcRes1List.get(0).getValues();
                    for (int i = 0; i < ndcvalues.size(); i++) {
                        drug_str_array_forms.add(ndcvalues.get(i).getForm());
                    }

                    if (drug_str_array_forms.size() > 0) {
                        CustomAdapter2 adapter = new CustomAdapter2(drug_str_array_forms);
                        search_list.setAdapter(adapter);
                        search_list.setVisibility(View.VISIBLE);
                        spinner_list_ll.setVisibility(View.GONE);
                    } else {
                        search_list.setVisibility(View.GONE);
                        spinner_list_ll.setVisibility(View.VISIBLE);
                    }

                    dismissProgressDialog();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<DrugNdcRes1> call, Throwable t) {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void getDrugStrengthService1(final String token, String seoName, final int position) {
        showProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getDrugNdc1(token, seoName, 0).enqueue(new Callback<DrugNdcRes1>() {
            @Override
            public void onResponse(Call<DrugNdcRes1> call, Response<DrugNdcRes1> response) {
                if (response.isSuccessful()) {

                    List<DrugNdcRes1.Drug> ndcRes1List = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value> ndcvalues = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value.Value_> ndcValues1 = new ArrayList<>();
                    ndcRes1List = response.body().getResult().getDrugs();
                    Gson gson = new Gson();
                    String dat = gson.toJson(response.body());
                    Log.v("TAG", "Response getDrugStrength -->" + dat);

                    if (response != null && response.body() != null && ndcRes1List != null
                            && ndcRes1List.size() > 0) {
                        ndcvalues = ndcRes1List.get(0).getValues();
                        ndcValues1 = ndcvalues.get(position).getValues();
                        drug_id_array.clear();
                        drug_id_array.add(0);
                        for (int k = 0; k < ndcValues1.size(); k++) {
                            drug_str_array.add(ndcValues1.get(k).getDosage());
                            drug_id_array.add(k);
                        }

                        if (drug_str_array.size() > 1) { // again change SpinnerList
                            strength_ll_spinner.setVisibility(View.VISIBLE);
                            str_of_drug_spinner.setEnabled(true);
                            str_of_drug_spinner.setVisibility(View.VISIBLE);

                        } else {
                            strength_ll_spinner.setVisibility(View.GONE);
                        }


                    } else {
                        strength_ll_spinner.setVisibility(View.GONE);
                    }
                    dismissProgressDialog();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<DrugNdcRes1> call, Throwable t) {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void getDrugQntService1(String token, String seoName, final int position) {
        showProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getDrugNdc1(token, seoName, 0).enqueue(new Callback<DrugNdcRes1>() {
            @Override
            public void onResponse(Call<DrugNdcRes1> call, Response<DrugNdcRes1> response) {
                if (response.isSuccessful()) {

                    List<DrugNdcRes1.Drug> ndcRes1List = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value> ndcvalues = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value.Value_> ndcValues1 = new ArrayList<>();
                    List<DrugNdcRes1.Drug.Value.Value_.Value__> ndcValues2 = new ArrayList<>();
                    ndcRes1List = response.body().getResult().getDrugs();
                    Gson gson = new Gson();
                    String dat = gson.toJson(response.body());
                    Log.v("TAG", "Response getDrugStrength -->" + dat);

                    //drug_qnt_array.clear();
                    if (response != null && response.body() != null && ndcRes1List != null
                            && ndcRes1List.size() > 0) {
                        ndcvalues = ndcRes1List.get(0).getValues();
                        ndcValues1 = ndcvalues.get(drugFormPostion).getValues();
                        ndcValues2 = ndcValues1.get(position).getValues();
                        drug_qnt_id_array.clear();
                        drug_qnt_id_array.add(0);
                        for (int k = 0; k < ndcValues2.size(); k++) {
                            drug_qnt_array.add(ndcValues2.get(k).getQuantity());
                            drug_qnt_id_array.add(k);
                            isGeneric = ndcValues2.get(k).getValue().getIsGeneric();

                        }
                    }
                    dismissProgressDialog();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<DrugNdcRes1> call, Throwable t) {
                if (pDialog != null) {
                    pDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }


    public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {

        private List<String> formsList = new ArrayList<>();

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvItemName;
            public LinearLayout llItem;

            public MyViewHolder(View view) {
                super(view);
                tvItemName = (TextView) view.findViewById(R.id.tvItemName);
                llItem = (LinearLayout) view.findViewById(R.id.ll_item);
            }
        }

        public CustomAdapter2(List<String> formsList) {
            this.formsList = formsList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Inflate the layout, initialize the View Holder
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tvItemName.setText(tv_DrugName.getText().toString() != null ? tv_DrugName.getText().toString() + " (" + formsList.get(position) + ")" : "");
            holder.llItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    drugFormName = formsList.get(position);
                    Log.v(TAG, "drugFormName: " + drugFormName);
                    drugFormPostion = position;
                    Log.v(TAG, "drugFormPostion: " + drugFormPostion);
                    spinner_list_ll.setVisibility(View.VISIBLE);
                    search_list.setVisibility(View.GONE);
                    hideKeyboard(PrescriptionSearchActivity.this);
                    getDrugStrengthService1(token, tv_DrugName.getText().toString(), position);
                }
            });
        }

        @Override
        public int getItemCount() {
            //returns the number of elements the RecyclerView will display
            return formsList.size();
        }


    }

}
