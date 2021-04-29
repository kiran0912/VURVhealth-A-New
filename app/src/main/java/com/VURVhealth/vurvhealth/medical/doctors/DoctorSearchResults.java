package com.VURVhealth.vurvhealth.medical.doctors;

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
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;


/**
 * Created by yqlabs on 11/1/17.
 */

public class DoctorSearchResults extends SuperAppCompactActivity {
    private static final int FILTER_CODE = 1000;
    private static String LOG_TAG = "DoctorSearchResults";
    static ArrayList<SearchPractitionerResPayLoad> searchPractitionerResPayLoadsFilter;
    private String adaAccess = "";
    private ImageView backBtn;
    private String city;
    private TextView count;
    private String female = "";
    private ImageView filter_btn;
    private RecyclerView.Adapter<DoctorResultAdapter.DataObjectHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String male = "";
    private ImageView mapBtn;
    private String newPatient = "";
    private TextView no_data;
    private String openWeekends = "";
    private String place;
    private SqLiteDbHelper recentDbHelper;
    private RecyclerView rv_place;
    private String specialty;
    private String state;
    private TextView tvGender;
    private TextView tvPlace;
    private TextView tvTabletName;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_result_screen);
        recentDbHelper = new SqLiteDbHelper(this);
        tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        mapBtn = (ImageView) findViewById(R.id.mapBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        count = (TextView) findViewById(R.id.count);
        no_data = (TextView) findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);
        tvGender.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("doctorType", 0);
        place = sharedPreferences.getString("zipCode", "");
        city = sharedPreferences.getString("city", "");
        state = sharedPreferences.getString("state", "");
        place = sharedPreferences.getString("zipCode", "");
        specialty = sharedPreferences.getString("doctorSpecialty", "");
        if (!((MedicalScreenActivity.resPayloads.get(0)).getGender() == null || ((SearchPractitionerResPayLoad) MedicalScreenActivity.resPayloads.get(0)).getGender().isEmpty() || ((SearchPractitionerResPayLoad) MedicalScreenActivity.resPayloads.get(0)).getGender().equalsIgnoreCase("null"))) {
            if (specialty.length() > 0) {
                tvTabletName.setVisibility(View.VISIBLE);
                tvTabletName.setText(specialty);
            } else {
                tvTabletName.setVisibility(View.GONE);
            }
            tvGender.setText(city + ", " + ((SearchPractitionerResPayLoad) MedicalScreenActivity.resPayloads.get(0)).getAddress().split(", ")[2]);
            String[] zipcode = ((SearchPractitionerResPayLoad) MedicalScreenActivity.resPayloads.get(0)).getAddress().split(",");
            tvPlace.setText(place);
        }
        rv_place = (RecyclerView) findViewById(R.id.rv_place);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_place.setLayoutManager(mLayoutManager);
        rv_place.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DoctorResultAdapter(this, MedicalScreenActivity.resPayloads);
        rv_place.setAdapter(mAdapter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DoctorSearchResults.this, DoctorsFilterActivity.class), 1000);
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorSearchResults.this, DoctorMapActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        count.setText(MedicalScreenActivity.resPayloads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 101) {
            newPatient = data.getStringExtra("newPatient");
            openWeekends = data.getStringExtra("openWeekends");
            adaAccess = data.getStringExtra("adaAccess");
            male = data.getStringExtra("male");
            female = data.getStringExtra("female");
            filteredData(newPatient, openWeekends, adaAccess, male, female);
        }
    }

    private void filteredData(String newPatient, String openWeekends, String adaAccess, String male, String female) {
        searchPractitionerResPayLoadsFilter = recentDbHelper.filterDoctorsData(newPatient, openWeekends, adaAccess, male, female);
        if (searchPractitionerResPayLoadsFilter.size() > 0) {
            rv_place.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            mAdapter = new DoctorResultAdapter(this, searchPractitionerResPayLoadsFilter);
            rv_place.setAdapter(mAdapter);
            count.setText(searchPractitionerResPayLoadsFilter.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
            return;
        }
        no_data.setVisibility(View.VISIBLE);
        rv_place.setVisibility(View.GONE);
        count.setText(R.string.no_results);
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
