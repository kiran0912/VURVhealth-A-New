package com.VURVhealth.vurvhealth.vision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.DialogClass;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.dental.DentalListAdapter;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionProviderIdResPayload;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.Iterator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionListActivity extends SuperAppCompactActivity {
    private static final int FILTER_REQUEST = 100;
    private ImageView backBtn;
    private String city;
    private TextView count;
    private String doctorSpeciality;
    private ArrayList<SearchForVisionResPayload.Datum> filterVisionList = new ArrayList();
    private ImageView filter_btn;
    private Adapter<VisionListAdapter.DataObjectHolder> mAdapter;
    private LayoutManager mLayoutManager;
    private ImageView mapBtn;
    private TextView no_data;
    private String officeSpeciality;
    private String place;
    private RecyclerView rv_place;
    private String state;
    private TextView tvGender;
    private TextView tvPlace;
    private TextView tvTabletName;
    

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_result_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("visionType", 0);
        city = sharedPreferences.getString("city", "");
        state = sharedPreferences.getString("state", "");
        place = sharedPreferences.getString("zipCode", "");
        doctorSpeciality = sharedPreferences.getString("doctorSpecialty", "");
        officeSpeciality = sharedPreferences.getString("officeSpecialty", "");
        count = (TextView) findViewById(R.id.count);
        tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        no_data = (TextView) findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        mapBtn = (ImageView) findViewById(R.id.mapBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        rv_place = (RecyclerView) findViewById(R.id.rv_place);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_place.setLayoutManager(mLayoutManager);
        rv_place.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new VisionListAdapter(this, VisionScreenActivity.resPayloads);
        rv_place.setAdapter(mAdapter);
        if (VisionScreenActivity.resPayloads!=null) {
            count.setText(VisionScreenActivity.resPayloads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        }
        if (doctorSpeciality.length() > 0) {
            tvTabletName.setVisibility(View.VISIBLE);
            tvTabletName.setText(doctorSpeciality);
        } else {
            tvTabletName.setVisibility(View.GONE);
        }
        if (officeSpeciality.length() > 0) {
            tvGender.setVisibility(View.VISIBLE);
            tvGender.setText(officeSpeciality);
        } else {
            tvGender.setVisibility(View.GONE);
        }
        tvPlace.setText(city + ", " + state + "\n" + place);
        filter_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(VisionListActivity.this, VisionFilterActivity.class), 100);
            }
        });
        mapBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisionListActivity.this, VisionMapActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (VisionScreenActivity.searchForVisionResPayload!=null) {
            if (VisionScreenActivity.searchForVisionResPayload.getStatus().equalsIgnoreCase("3")) {
                StringBuffer statesList = new StringBuffer();
                Iterator it = VisionScreenActivity.searchForVisionResPayload.getStateList().iterator();
                while (it.hasNext()) {
                    statesList.append(((SearchForVisionResPayload.StateList) it.next()).getRestrictedState() + ", ");
                    statesList.setLength(statesList.length() - 2);
                    DialogClass.createDAlertDialog(this, getString(R.string.some_providers) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + statesList);
                }
            }
        }
    }

    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 100 || resultCode != 101) {
            return;
        }
        if ((data.getStringExtra("male").length() > 0 && data.getStringExtra("female").length() > 0) || (data.getStringExtra("male").trim().length() == 0 && data.getStringExtra("female").trim().length() == 0)) {
            mAdapter = new VisionListAdapter(this, VisionScreenActivity.resPayloads);
            rv_place.setAdapter(mAdapter);
            count.setText(VisionScreenActivity.resPayloads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        } else if (data.getStringExtra("male").length() > 0) {
            filterVisionList.clear();
            for (int i = 0; i < VisionScreenActivity.resPayloads.size(); i++) {
                if (data.getStringExtra("male").equalsIgnoreCase(((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(i)).getGender())) {
                    filterVisionList.add(VisionScreenActivity.resPayloads.get(i));
                }
            }
            if (filterVisionList.size() > 0) {
                mAdapter = new VisionListAdapter(this, filterVisionList);
                rv_place.setAdapter(mAdapter);
                count.setText(filterVisionList.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
                no_data.setVisibility(View.GONE);
                rv_place.setVisibility(View.VISIBLE);
                return;
            }
            count.setText(getResources().getString(R.string.no_results));
            no_data.setVisibility(View.VISIBLE);
            rv_place.setVisibility(View.GONE);
        } else if (data.getStringExtra("female").length() > 0) {
            filterVisionList.clear();
            for (int i = 0; i < VisionScreenActivity.resPayloads.size(); i++) {
                if (data.getStringExtra("female").equalsIgnoreCase(((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(i)).getGender())) {
                    filterVisionList.add(VisionScreenActivity.resPayloads.get(i));
                }
            }
            if (filterVisionList.size() > 0) {
                mAdapter = new VisionListAdapter(this, filterVisionList);
                rv_place.setAdapter(mAdapter);
                count.setText(filterVisionList.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
                no_data.setVisibility(View.GONE);
                rv_place.setVisibility(View.VISIBLE);
                return;
            }
            count.setText(getResources().getString(R.string.no_results));
            no_data.setVisibility(View.VISIBLE);
            rv_place.setVisibility(View.GONE);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getProviderDetails() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad searchForVisionReqPayload = new AboutDoctorReqPayLoad();
        searchForVisionReqPayload.setProviderId(((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(0)).getVisProviderId());
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(searchForVisionReqPayload);
        apiService.getVisionProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<VisionProviderIdResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<VisionProviderIdResPayload>> call, Response<ArrayList<VisionProviderIdResPayload>> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VisionProviderIdResPayload>> call, Throwable t) {
                Toast.makeText(VisionListActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }
}
