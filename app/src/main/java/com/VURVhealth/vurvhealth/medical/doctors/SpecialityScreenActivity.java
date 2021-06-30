package com.VURVhealth.vurvhealth.medical.doctors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.adapters.CustomAdapter;
import com.VURVhealth.vurvhealth.althealth.AltHealthScreenActivity;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSpecialtyReqPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSpecialtyResPayload;
import com.VURVhealth.vurvhealth.dental.DentalScreenActivity;
import com.VURVhealth.vurvhealth.dental.pojos.DentalLangResPayLoad;
import com.VURVhealth.vurvhealth.dental.pojos.SpecialtyDentalResPayload;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.pojos.HospitalAffiliationReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.HospitalAffiliationResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.LanguageResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.LanguagesReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SpecialtyReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SpecialtyResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.vision.VisionScreenActivity;
import com.VURVhealth.vurvhealth.vision.pojos.OfficeSpecialtyReqPayLoad;
import com.VURVhealth.vurvhealth.vision.pojos.OfficeSpecialtyResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionLangReqPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionLangResPayload;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 12/1/17.
 */

public class SpecialityScreenActivity extends SuperAppCompactActivity {

    private ImageView backBtn;
    private AutoCompleteTextView tvSpeciality;
    private RecyclerView search_list;
    private  RecyclerView.LayoutManager mLayoutManager;
    private CustomAdapter customAdapter;
    private TextView toolbar_txt,no_data;
    ArrayList<String> specialtyArrayList;
    ArrayList<String> specialtyIdArray;
    private Context context;
    private String fieldName,zipCode;
    String activity = "";
    public SpecialtyDentalResPayload specialtyDentalResPayloads;
//    private ArrayList<String> specArray = new ArrayList<>();

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speciality_screen);
        if(getIntent() != null){
            fieldName = getIntent().getStringExtra("fieldName");
            activity = getIntent().getStringExtra("activity");

        }

        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvSpeciality = (AutoCompleteTextView) findViewById(R.id.tvSpeciality);
        toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
        no_data = (TextView) findViewById(R.id.no_data);
        search_list = (RecyclerView) findViewById(R.id.search_list);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        search_list.setLayoutManager(mLayoutManager);
        search_list.setItemAnimator(new DefaultItemAnimator());

        if (fieldName.equalsIgnoreCase("specialty")) {
            toolbar_txt.setText(getString(R.string.choose_speciality));
            tvSpeciality.setHint(getString(R.string.choose_speciality));
            if(checkInternet()) {
                getSpecialtyService();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();

            }

        } else if (fieldName.equalsIgnoreCase("language")){

            toolbar_txt.setText(getString(R.string.choose_language));
            tvSpeciality.setHint(getString(R.string.choose_language));

            if(checkInternet()) {
                getLanguagesService();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if (fieldName.equalsIgnoreCase("dentalSpecialty")){

            toolbar_txt.setText(getString(R.string.choose_speciality));
            tvSpeciality.setHint(getString(R.string.choose_speciality));

            if(checkInternet()) {
                getDentalSpecialty();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        } else if (fieldName.equalsIgnoreCase("dentalStatus")){

            toolbar_txt.setText(getString(R.string.status));
            tvSpeciality.setHint(getString(R.string.status));

            if(checkInternet()) {
                getDentalSpecialty();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if (fieldName.equalsIgnoreCase("dentalLanguage")){

            toolbar_txt.setText(getString(R.string.choose_language));
            tvSpeciality.setHint(getString(R.string.choose_language));

            if(checkInternet()) {
                getDentalLanguagesService();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if (fieldName.equalsIgnoreCase("visionOffice")){
            toolbar_txt.setText(R.string.choose_office);
            tvSpeciality.setHint(R.string.choose_office);

            if(checkInternet()) {
                getOfficeSpecialtyService();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if (fieldName.equalsIgnoreCase("visionLang")){

            toolbar_txt.setText(getString(R.string.choose_language));
            tvSpeciality.setHint(getString(R.string.choose_language));

            if(checkInternet()) {
                getVisionLangService();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if (fieldName.equalsIgnoreCase("altHealthSpecialty")){

            toolbar_txt.setText(getString(R.string.choose_speciality));
            tvSpeciality.setHint(getString(R.string.choose_speciality));

            if(checkInternet()) {
                getAHSSpecialty();
            }else {
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }

        }else if (fieldName.equalsIgnoreCase("hospitalAffiliations")){
            toolbar_txt.setText(getString(R.string.choose_hospital));
            tvSpeciality.setHint(getString(R.string.choose_hospital));
            zipCode = getIntent().getStringExtra("zipCode");

            if(checkInternet()) {
                getHospitalAffiliationsService();
            }else {

                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();

            }

        }

        //When button is clicked
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        tvSpeciality.setCursorVisible(false);
       /* tvSpeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specArray,"spec","specialty");
                tvSpeciality.setCursorVisible(true);
                search_list.setAdapter(customAdapter);
                search_list.setVisibility(View.VISIBLE);

            }
        });*/

        //add textwatcher for the field
        tvSpeciality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvSpeciality != null) {
                    customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "specialty");
                    search_list.setAdapter(customAdapter);
                    List<String> filter =  customAdapter.filter(tvSpeciality.getText().toString());
                    if(filter.size()>0){
                        search_list.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                    }else {
                        search_list.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setText("No search results found");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //consuming specialty service
    private void getSpecialtyService() {
        showProgressDialog(SpecialityScreenActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        ArrayList<SpecialtyReqPayLoad> specialtyReqPayLoad = new ArrayList<SpecialtyReqPayLoad>();
        SpecialtyReqPayLoad specialtyReqPayLoad1 = new SpecialtyReqPayLoad("");
        specialtyReqPayLoad.add(specialtyReqPayLoad1);

        Call<ArrayList<SpecialtyResPayLoad>> call = apiService.getSpecialty(specialtyReqPayLoad);
        call.enqueue(new Callback<ArrayList<SpecialtyResPayLoad>>() {
            @Override
            public void onResponse(Call<ArrayList<SpecialtyResPayLoad>> call, Response<ArrayList<SpecialtyResPayLoad>> response) {

                if (response.isSuccessful()) {
                    ArrayList<SpecialtyResPayLoad> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        specialtyArrayList.add(responsePayLoad.get(i).getSpecialty());
                    }
                    if(specialtyArrayList.size()>0) {
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "specialty");
                        dismissProgressDialog();
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }
                }
                dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<ArrayList<SpecialtyResPayLoad>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void getOfficeSpecialtyService() {
        showProgressDialog(SpecialityScreenActivity.this);
        ApiInterface apiInterface = ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        OfficeSpecialtyReqPayLoad officeSpecReqPayLoad = new OfficeSpecialtyReqPayLoad();
        officeSpecReqPayLoad.setOfficeSpecialty("");
        ArrayList<OfficeSpecialtyReqPayLoad> ofcSpecialtyReqPayLoads = new ArrayList<>();
        ofcSpecialtyReqPayLoads.add(officeSpecReqPayLoad);
        Call<ArrayList<OfficeSpecialtyResPayload>> ofcSpecialtyPayLoadCall = apiInterface.getOfficeSpecialtyService(ofcSpecialtyReqPayLoads);
        ofcSpecialtyPayLoadCall.enqueue(new Callback<ArrayList<OfficeSpecialtyResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<OfficeSpecialtyResPayload>> call, Response<ArrayList<OfficeSpecialtyResPayload>> response) {

                if (response.isSuccessful()) {
                    ArrayList<OfficeSpecialtyResPayload> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        if (responsePayLoad.get(i).getOfficeSpecialtyName().trim().length()>0)
                            specialtyArrayList.add(responsePayLoad.get(i).getOfficeSpecialtyName());
                    }
                    if(specialtyArrayList.size()>0) {
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "officeSpecialty");
                        dismissProgressDialog();
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }

                }
                dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<ArrayList<OfficeSpecialtyResPayload>> call, Throwable t) {

                dismissProgressDialog();
                // Log error here since request failed
                Toast.makeText(SpecialityScreenActivity.this, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();

                Log.e("DoctorSpecialtyService", t.toString());
            }
        });
    }

    private void getVisionLangService() {
        showProgressDialog(SpecialityScreenActivity.this);
        ApiInterface apiInterface = ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        VisionLangReqPayload visionLangReqPayload = new VisionLangReqPayload();
        visionLangReqPayload.setVisionLangInit("");
        ArrayList<VisionLangReqPayload> visionLangReqPayloads = new ArrayList<>();
        visionLangReqPayloads.add(visionLangReqPayload);
        Call<ArrayList<VisionLangResPayload>> ofcSpecialtyPayLoadCall = apiInterface.getVisionLanguage(visionLangReqPayloads);
        ofcSpecialtyPayLoadCall.enqueue(new Callback<ArrayList<VisionLangResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<VisionLangResPayload>> call, Response<ArrayList<VisionLangResPayload>> response) {

                if (response.isSuccessful()) {

                    ArrayList<VisionLangResPayload> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        specialtyArrayList.add(responsePayLoad.get(i).getVisionLanguages());
                    }
                    if(specialtyArrayList.size()>0) {
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "visionLang");
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }
                }

                dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<ArrayList<VisionLangResPayload>> call, Throwable t) {

                dismissProgressDialog();
                // Log error here since request failed
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                Log.e("DoctorSpecialtyService", t.toString());
            }
        });
    }


    //consuming language service
    private void getLanguagesService() {
        ApiInterface apiService =
                ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        ArrayList<LanguagesReqPayLoad> specialtyReqPayLoad = new ArrayList<LanguagesReqPayLoad>();
        LanguagesReqPayLoad specialtyReqPayLoad1 = new LanguagesReqPayLoad("");
        specialtyReqPayLoad.add(specialtyReqPayLoad1);

        Call<ArrayList<LanguageResPayLoad>> call = apiService.getLanguages(specialtyReqPayLoad);
        call.enqueue(new Callback<ArrayList<LanguageResPayLoad>>() {
            @Override
            public void onResponse(Call<ArrayList<LanguageResPayLoad>> call, Response<ArrayList<LanguageResPayLoad>> response) {

                if (response.isSuccessful()) {

                    ArrayList<LanguageResPayLoad> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        specialtyArrayList.add(responsePayLoad.get(i).getLanguageName());
                    }
                    if(specialtyArrayList.size()>0) {
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "specialty");
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<LanguageResPayLoad>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //consuming hospital affiliation service
    private void getHospitalAffiliationsService() {
        ApiInterface apiService =
                ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        ArrayList<HospitalAffiliationReqPayLoad> hospitalAffiliationReqPayLoads = new ArrayList<HospitalAffiliationReqPayLoad>();
        HospitalAffiliationReqPayLoad hospitalAffiliationReqPayLoad = new HospitalAffiliationReqPayLoad();
        hospitalAffiliationReqPayLoad.setCity("");
        hospitalAffiliationReqPayLoad.setHospitalName("");
        hospitalAffiliationReqPayLoad.setZipcode(zipCode);
        hospitalAffiliationReqPayLoads.add(hospitalAffiliationReqPayLoad);

        Call<ArrayList<HospitalAffiliationResPayLoad>> call = apiService.getHospitalAffiliation(hospitalAffiliationReqPayLoads);
        call.enqueue(new Callback<ArrayList<HospitalAffiliationResPayLoad>>() {
            @Override
            public void onResponse(Call<ArrayList<HospitalAffiliationResPayLoad>> call, Response<ArrayList<HospitalAffiliationResPayLoad>> response) {

                if (response.isSuccessful()) {

                    ArrayList<HospitalAffiliationResPayLoad> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        if(responsePayLoad.get(i).getHName() != null && !responsePayLoad.get(i).getHName().equalsIgnoreCase("null"))
                            specialtyArrayList.add(responsePayLoad.get(i).getHName());
                    }
                    if(specialtyArrayList.size()>1) {
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "hospitalAffiliations");
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                        search_list.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<HospitalAffiliationResPayLoad>> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
                dismissProgressDialog();
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getDentalSpecialty() {
        showProgressDialog(SpecialityScreenActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        ArrayList<SpecialtyReqPayLoad> specialtyReqPayLoad = new ArrayList<SpecialtyReqPayLoad>();
        SpecialtyReqPayLoad specialtyReqPayLoad1 = new SpecialtyReqPayLoad("");
        specialtyReqPayLoad.add(specialtyReqPayLoad1);

        Call<ArrayList<SpecialtyDentalResPayload>> call = apiService.getDentalSpecialty(specialtyReqPayLoad);
        call.enqueue(new Callback<ArrayList<SpecialtyDentalResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<SpecialtyDentalResPayload>> call, Response<ArrayList<SpecialtyDentalResPayload>> response) {

                if (response.isSuccessful()) {
                    ArrayList<SpecialtyDentalResPayload> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();
                    specialtyIdArray = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        specialtyArrayList.add(responsePayLoad.get(i).getSpecialty());
                        specialtyIdArray.add(responsePayLoad.get(i).getSpecialtyCode());
                    }
                    if(specialtyArrayList.size()>0) {
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "dentalSpecialty");
                        dismissProgressDialog();
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }

                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<SpecialtyDentalResPayload>> call, Throwable t) {

                dismissProgressDialog();
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getDentalLanguagesService() {
        ApiInterface apiService =
                ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        ArrayList<LanguagesReqPayLoad> specialtyReqPayLoad = new ArrayList<LanguagesReqPayLoad>();
        LanguagesReqPayLoad specialtyReqPayLoad1 = new LanguagesReqPayLoad("");
        specialtyReqPayLoad.add(specialtyReqPayLoad1);

        Call<ArrayList<DentalLangResPayLoad>> call = apiService.getDentalLanguages(specialtyReqPayLoad);
        call.enqueue(new Callback<ArrayList<DentalLangResPayLoad>>() {
            @Override
            public void onResponse(Call<ArrayList<DentalLangResPayLoad>> call, Response<ArrayList<DentalLangResPayLoad>> response) {

                if (response.isSuccessful()) {

                    ArrayList<DentalLangResPayLoad> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        specialtyArrayList.add(responsePayLoad.get(i).getLanguageName());
                    }
                    if(specialtyArrayList.size()>0) {
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "dentalLanguage");
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<DentalLangResPayLoad>> call, Throwable t) {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                dismissProgressDialog();
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAHSSpecialty() {
        showProgressDialog(SpecialityScreenActivity.this);
        ApiInterface apiInterface = ApiClient.getClient(SpecialityScreenActivity.this).create(ApiInterface.class);
        AHSSpecialtyReqPayload ahsSpecialtyReqPayload = new AHSSpecialtyReqPayload();
        ahsSpecialtyReqPayload.setAHSspecialty("");
        ArrayList<AHSSpecialtyReqPayload> ahsSpecialtyReqPayloads = new ArrayList<>();
        ahsSpecialtyReqPayloads.add(ahsSpecialtyReqPayload);
        Call<ArrayList<AHSSpecialtyResPayload>> arrayListCall = apiInterface.getAHSSpecialty(ahsSpecialtyReqPayloads);
        arrayListCall.enqueue(new Callback<ArrayList<AHSSpecialtyResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<AHSSpecialtyResPayload>> call, Response<ArrayList<AHSSpecialtyResPayload>> response) {

                if (response.isSuccessful()) {
                    ArrayList<AHSSpecialtyResPayload> responsePayLoad = response.body();
                    specialtyArrayList = new ArrayList<String>();

                    for(int i=0; i<responsePayLoad.size();i++) {
                        specialtyArrayList.add(responsePayLoad.get(i).getSpecialty());
                    }
                    if(specialtyArrayList.size()>0) {
                        no_data.setVisibility(View.GONE);
                        search_list.setVisibility(View.VISIBLE);
                        customAdapter = new CustomAdapter(SpecialityScreenActivity.this, specialtyArrayList, "spec", "altHealthSpecialty");
                        dismissProgressDialog();
                        search_list.setAdapter(customAdapter);
                        customAdapter.filter(tvSpeciality.getText().toString());
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        search_list.setVisibility(View.GONE);
                    }

                }
                dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<ArrayList<AHSSpecialtyResPayload>> call, Throwable t) {

                dismissProgressDialog();
                // Log error here since request failed
                Toast.makeText(SpecialityScreenActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                Log.e("DoctorSpecialtyService", t.toString());
            }
        });
    }

    //return values to set to view
    public void setValueToSpecialty(String specialtyValue, String fieldName, int position) {

        if(fieldName.equalsIgnoreCase("spec")){
            tvSpeciality.setText(specialtyValue);
            if(tvSpeciality!=null){
                if (activity.equalsIgnoreCase("visionScreen")){
                    Intent intent = new Intent(SpecialityScreenActivity.this, VisionScreenActivity.class);
                    intent.putExtra("fieldValue", specialtyValue);
                    setResult(101, intent);
                    finish();
                }else if (activity.equalsIgnoreCase("dentalScreen")){
                    Intent intent = new Intent(SpecialityScreenActivity.this, DentalScreenActivity.class);
                    intent.putExtra("fieldValue", specialtyValue);
                    intent.putExtra("specCode", specialtyIdArray.get(position));
                    setResult(101, intent);
                    finish();
                }else if (activity.equalsIgnoreCase("altHealthScreen")){
                    Intent intent = new Intent(SpecialityScreenActivity.this, AltHealthScreenActivity.class);
                    intent.putExtra("fieldValue", specialtyValue);
                    setResult(101, intent);
                    finish();
                }else if (activity.equalsIgnoreCase("medicalScreen")) {
                    Intent intent = new Intent(SpecialityScreenActivity.this, MedicalScreenActivity.class);
                    intent.putExtra("fieldValue", specialtyValue);
                    setResult(101, intent);
                    hideKeyboard(SpecialityScreenActivity.this);
                    finish();
                }
            }
            search_list.setVisibility(View.GONE);
        }

    }


}
