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

    /* renamed from: com.VURVhealth.VURVhealth.medical.facilities.FacilitySearchResult$1 */
    class C04941 implements View.OnClickListener {
        C04941() {
        }

        public void onClick(View v) {
            FacilitySearchResult.this.startActivityForResult(new Intent(FacilitySearchResult.this, FacilityFilterActivity.class), FacilitySearchResult.REQUEST_CODE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.medical.facilities.FacilitySearchResult$2 */
    class C04952 implements View.OnClickListener {
        C04952() {
        }

        public void onClick(View v) {
            FacilitySearchResult.this.startActivity(new Intent(FacilitySearchResult.this, FacilityMapActivity.class));
            FacilitySearchResult.this.finish();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.medical.facilities.FacilitySearchResult$3 */
    class C04963 implements View.OnClickListener {
        C04963() {
        }

        public void onClick(View v) {
            FacilitySearchResult.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_result_screen);
        this.recentDbHelper = new SqLiteDbHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("facilityPref", 0);
        this.place = sharedPreferences.getString("zipCode", "");
        this.city = sharedPreferences.getString("city", "");
        this.state = sharedPreferences.getString("state", "");
        this.facilityType = sharedPreferences.getString("facilityType", "");
        this.subFacility = sharedPreferences.getString("subFacility", "");
        this.tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        this.tvGender = (TextView) findViewById(R.id.tvGender);
        this.tvPlace = (TextView) findViewById(R.id.tvPlace);
        this.count = (TextView) findViewById(R.id.count);
        this.no_data = (TextView) findViewById(R.id.no_data);
        this.no_data.setVisibility(View.GONE);
        this.filter_btn = (ImageView) findViewById(R.id.filter_btn);
        this.mapBtn = (ImageView) findViewById(R.id.mapBtn);
        this.backBtn = (ImageView) findViewById(R.id.backBtn);
        this.tvTabletName.setVisibility(View.VISIBLE);
        this.tvGender.setVisibility(View.VISIBLE);
        this.rv_place = (RecyclerView) findViewById(R.id.rv_place);
        this.mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.rv_place.setLayoutManager(this.mLayoutManager);
        this.rv_place.setItemAnimator(new DefaultItemAnimator());
        this.mAdapter = new FacilityResultAdapter(this, MedicalScreenActivity.resPayloadsForFacilities, this.subFacility);
        this.rv_place.setAdapter(this.mAdapter);
        this.count.setText(MedicalScreenActivity.resPayloadsForFacilities.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        if (this.facilityType.length() > 0) {
            this.tvTabletName.setText(this.facilityType);
        } else {
            this.tvTabletName.setVisibility(View.GONE);
        }
        if (this.subFacility.length() > 0) {
            this.tvGender.setText(this.subFacility);
        } else {
            this.tvGender.setVisibility(View.VISIBLE);
        }
        String[] adress = ((SearchFacilitiesResPayLoad) MedicalScreenActivity.resPayloadsForFacilities.get(0)).getAddress().split(", ");
        this.tvPlace.setText(this.city + ", " + this.state + "\n" + this.place);
        this.filter_btn.setOnClickListener(new C04941());
        this.mapBtn.setOnClickListener(new C04952());
        this.backBtn.setOnClickListener(new C04963());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 101) {
            filterData(data.getStringExtra("handicapped"), data.getStringExtra("communityProvider"), data.getStringExtra("Accredited"));
        }
    }

    private void filterData(String handicapped, String communityProvider, String accredited) {
        searchPractitionerResPayLoads = this.recentDbHelper.filterFacilitysData(handicapped, communityProvider, accredited);
        if (searchPractitionerResPayLoads.size() > 0) {
            this.mAdapter = new FacilityResultAdapter(this, searchPractitionerResPayLoads, this.subFacility);
            this.rv_place.setAdapter(this.mAdapter);
            this.count.setText("" + searchPractitionerResPayLoads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
            this.rv_place.setVisibility(View.VISIBLE);
            this.no_data.setVisibility(View.GONE);
            return;
        }
        this.count.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.no_results));
        this.rv_place.setVisibility(View.GONE);
        this.no_data.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        super.onResume();
        this.mAdapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
