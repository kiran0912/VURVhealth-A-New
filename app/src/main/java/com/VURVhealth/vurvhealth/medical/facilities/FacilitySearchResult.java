package com.VURVhealth.vurvhealth.medical.facilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesResPayLoad;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;

/**
 * Created by yqlabs on 25/1/17.
 */

public class FacilitySearchResult extends AppCompatActivity {
    private static String LOG_TAG = "FacilitySearchResult";
    private static int REQUEST_CODE = 101;
    static ArrayList<SearchFacilitiesResPayLoad> searchPractitionerResPayLoads;
    private ImageView backBtn;
    private String city;
    private TextView count;
    private String facilityType;
    private ImageView filter_btn;
    private RecyclerView.Adapter<FacilityResultAdapter.DataObjectHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mapBtn;
    private TextView no_data;
    private String place;
    private SqLiteDbHelper recentDbHelper;
    private RecyclerView rv_place;
    private String state;
    private String subFacility;
    private TextView tvGender;
    private TextView tvPlace;
    private TextView tvTabletName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_result_screen);
        recentDbHelper = new SqLiteDbHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("facilityPref", 0);
        place = sharedPreferences.getString("zipCode", "");
        city = sharedPreferences.getString("city", "");
        state = sharedPreferences.getString("state", "");
        facilityType = sharedPreferences.getString("facilityType", "");
        subFacility = sharedPreferences.getString("subFacility", "");
        tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        count = (TextView) findViewById(R.id.count);
        no_data = (TextView) findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        mapBtn = (ImageView) findViewById(R.id.mapBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvTabletName.setVisibility(View.VISIBLE);
        tvGender.setVisibility(View.VISIBLE);
        rv_place = (RecyclerView) findViewById(R.id.rv_place);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_place.setLayoutManager(mLayoutManager);
        rv_place.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new FacilityResultAdapter(this, MedicalScreenActivity.resPayloadsForFacilities, subFacility);
        rv_place.setAdapter(mAdapter);
        count.setText(MedicalScreenActivity.resPayloadsForFacilities.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        if (facilityType.length() > 0) {
            tvTabletName.setText(facilityType);
        } else {
            tvTabletName.setVisibility(View.GONE);
        }
        if (subFacility.length() > 0) {
            tvGender.setText(subFacility);
        } else {
            tvGender.setVisibility(View.VISIBLE);
        }
        String[] adress = ((SearchFacilitiesResPayLoad) MedicalScreenActivity.resPayloadsForFacilities.get(0)).getAddress().split(", ");
        tvPlace.setText(city + ", " + state + "\n" + place);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(FacilitySearchResult.this, FacilityFilterActivity.class), REQUEST_CODE);
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacilitySearchResult.this, FacilityMapActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 101) {
            filterData(data.getStringExtra("handicapped"), data.getStringExtra("communityProvider"), data.getStringExtra("Accredited"));
        }
    }

    private void filterData(String handicapped, String communityProvider, String accredited) {
        searchPractitionerResPayLoads = recentDbHelper.filterFacilitysData(handicapped, communityProvider, accredited);
        if (searchPractitionerResPayLoads.size() > 0) {
            mAdapter = new FacilityResultAdapter(this, searchPractitionerResPayLoads, subFacility);
            rv_place.setAdapter(mAdapter);
            count.setText("" + searchPractitionerResPayLoads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
            rv_place.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            return;
        }
        count.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.no_results));
        rv_place.setVisibility(View.GONE);
        no_data.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
