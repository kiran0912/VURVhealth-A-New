package com.VURVhealth.vurvhealth.dental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.DialogClass;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by yqlabs on 18/3/17.
 */

public class DentalListActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 1000;
    static ArrayList<SearchForDentalResPayLoad.Datum> searchForDentalResPayLoads;
    private ImageView backBtn;
    private String city;
    private ImageView filter_btn;
    private RecyclerView.Adapter<DentalListAdapter.DataObjectHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mapBtn;
    private TextView no_data;
    private String place;
    private SqLiteDbHelper recentDbHelper;
    private RecyclerView rv_place;
    private String specialty;
    private String state;
    private TextView tbName;
    private TextView tvPlace;
    private TextView tvTabletName;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_price);
        recentDbHelper = new SqLiteDbHelper(this);
        tbName = (TextView) findViewById(R.id.tbName);
        tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        no_data = (TextView) findViewById(R.id.no_data);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        mapBtn = (ImageView) findViewById(R.id.mapBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        rv_place = (RecyclerView) findViewById(R.id.rv_place);
        SharedPreferences sharedPreferences = getSharedPreferences("dentalType", 0);
        specialty = sharedPreferences.getString(MedicalScreenActivity.specialty, "");
        place = sharedPreferences.getString("zipCode", "");
        city = sharedPreferences.getString("city", "");
        state = sharedPreferences.getString("state", "");
        if (DentalScreenActivity.resPayloads != null) {
            tbName.setText(DentalScreenActivity.resPayloads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        } else {
            tbName.setText(getResources().getString(R.string.no_results));
        }
        if (specialty.length() > 0) {
            tvTabletName.setText(specialty);
        } else {
            tvTabletName.setVisibility(View.GONE);
        }
        tvPlace.setText(city + ", " + state + "\n" + place);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_place.setLayoutManager(mLayoutManager);
        rv_place.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DentalListAdapter(this, DentalScreenActivity.resPayloads);
        rv_place.setAdapter(mAdapter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DentalListActivity.this, DentalFilterActivity.class), DentalListActivity.REQUEST_CODE);
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DentalListActivity.this, DentalMapActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (DentalScreenActivity.dentalSearchResPayload.getStatus().equalsIgnoreCase("3")) {
            StringBuffer statesList = new StringBuffer();
            Iterator it = DentalScreenActivity.dentalSearchResPayload.getStateList().iterator();
            while (it.hasNext()) {
                statesList.append(((SearchForDentalResPayLoad.StateList) it.next()).getRestrictedState() + ", ");
                statesList.setLength(statesList.length() - 2);
                DialogClass.createDAlertDialog(this, getString(R.string.some_providers) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + statesList);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 101) {
            filterData(data.getStringExtra("generalDentistry"), data.getStringExtra("specialist"), data.getStringExtra("group"), data.getStringExtra("solo_practitioner"));
        }
    }

    private void filterData(String generalDentistry, String specialist, String group, String solo_practitioner) {
        searchForDentalResPayLoads = recentDbHelper.filterDentalData(generalDentistry, specialist, group, solo_practitioner);
        if (searchForDentalResPayLoads.size() > 0) {
            rv_place.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            mAdapter = new DentalListAdapter(this, searchForDentalResPayLoads);
            rv_place.setAdapter(mAdapter);
            tbName.setText(searchForDentalResPayLoads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
            return;
        }
        no_data.setVisibility(View.VISIBLE);
        rv_place.setVisibility(View.GONE);
        tbName.setText(getResources().getString(R.string.no_results));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
