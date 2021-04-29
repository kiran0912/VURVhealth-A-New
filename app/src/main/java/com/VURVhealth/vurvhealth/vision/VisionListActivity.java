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

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionListActivity$1 */
    class C08071 implements OnClickListener {
        C08071() {
        }

        public void onClick(View v) {
            VisionListActivity.this.startActivityForResult(new Intent(VisionListActivity.this, VisionFilterActivity.class), 100);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionListActivity$2 */
    class C08082 implements OnClickListener {
        C08082() {
        }

        public void onClick(View v) {
            VisionListActivity.this.startActivity(new Intent(VisionListActivity.this, VisionMapActivity.class));
            VisionListActivity.this.finish();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionListActivity$3 */
    class C08093 implements OnClickListener {
        C08093() {
        }

        public void onClick(View v) {
            VisionListActivity.this.finish();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionListActivity$4 */
    class C08104 implements Callback<ArrayList<VisionProviderIdResPayload>> {
        C08104() {
        }

        public void onResponse(Call<ArrayList<VisionProviderIdResPayload>> call, Response<ArrayList<VisionProviderIdResPayload>> response) {
            if (response.isSuccessful()) {
                VisionListActivity.this.dismissProgressDialog();
            }
        }

        public void onFailure(Call<ArrayList<VisionProviderIdResPayload>> call, Throwable t) {
            Toast.makeText(VisionListActivity.this, VisionListActivity.this.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            Log.e("", t.toString());
            VisionListActivity.this.dismissProgressDialog();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_result_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("visionType", 0);
        this.city = sharedPreferences.getString("city", "");
        this.state = sharedPreferences.getString("state", "");
        this.place = sharedPreferences.getString("zipCode", "");
        this.doctorSpeciality = sharedPreferences.getString("doctorSpecialty", "");
        this.officeSpeciality = sharedPreferences.getString("officeSpecialty", "");
        this.count = (TextView) findViewById(R.id.count);
        this.tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        this.tvGender = (TextView) findViewById(R.id.tvGender);
        this.tvPlace = (TextView) findViewById(R.id.tvPlace);
        this.no_data = (TextView) findViewById(R.id.no_data);
        this.no_data.setVisibility(View.GONE);
        this.filter_btn = (ImageView) findViewById(R.id.filter_btn);
        this.mapBtn = (ImageView) findViewById(R.id.mapBtn);
        this.backBtn = (ImageView) findViewById(R.id.backBtn);
        this.rv_place = (RecyclerView) findViewById(R.id.rv_place);
        this.mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.rv_place.setLayoutManager(this.mLayoutManager);
        this.rv_place.setItemAnimator(new DefaultItemAnimator());
        this.mAdapter = new VisionListAdapter(VisionListActivity.this, VisionScreenActivity.resPayloads);
        this.rv_place.setAdapter(this.mAdapter);
        this.count.setText(VisionScreenActivity.resPayloads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        if (this.doctorSpeciality.length() > 0) {
            this.tvTabletName.setVisibility(View.VISIBLE);
            this.tvTabletName.setText(this.doctorSpeciality);
        } else {
            this.tvTabletName.setVisibility(View.GONE);
        }
        if (this.officeSpeciality.length() > 0) {
            this.tvGender.setVisibility(View.VISIBLE);
            this.tvGender.setText(this.officeSpeciality);
        } else {
            this.tvGender.setVisibility(View.GONE);
        }
        this.tvPlace.setText(this.city + ", " + this.state + "\n" + this.place);
        this.filter_btn.setOnClickListener(new C08071());
        this.mapBtn.setOnClickListener(new C08082());
        this.backBtn.setOnClickListener(new C08093());
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

    protected void onResume() {
        super.onResume();
        this.mAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 100 || resultCode != 101) {
            return;
        }
        if ((data.getStringExtra("male").length() > 0 && data.getStringExtra("female").length() > 0) || (data.getStringExtra("male").trim().length() == 0 && data.getStringExtra("female").trim().length() == 0)) {
            this.mAdapter = new VisionListAdapter(this, VisionScreenActivity.resPayloads);
            this.rv_place.setAdapter(this.mAdapter);
            this.count.setText(VisionScreenActivity.resPayloads.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
        } else if (data.getStringExtra("male").length() > 0) {
            this.filterVisionList.clear();
            for (int i = 0; i < VisionScreenActivity.resPayloads.size(); i++) {
                if (data.getStringExtra("male").equalsIgnoreCase(((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(i)).getGender())) {
                    this.filterVisionList.add(VisionScreenActivity.resPayloads.get(i));
                }
            }
            if (this.filterVisionList.size() > 0) {
                this.mAdapter = new VisionListAdapter(this, this.filterVisionList);
                this.rv_place.setAdapter(this.mAdapter);
                this.count.setText(this.filterVisionList.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
                this.no_data.setVisibility(View.GONE);
                this.rv_place.setVisibility(View.VISIBLE);
                return;
            }
            this.count.setText(getResources().getString(R.string.no_results));
            this.no_data.setVisibility(View.VISIBLE);
            this.rv_place.setVisibility(View.GONE);
        } else if (data.getStringExtra("female").length() > 0) {
            this.filterVisionList.clear();
            for (int i = 0; i < VisionScreenActivity.resPayloads.size(); i++) {
                if (data.getStringExtra("female").equalsIgnoreCase(((SearchForVisionResPayload.Datum) VisionScreenActivity.resPayloads.get(i)).getGender())) {
                    this.filterVisionList.add(VisionScreenActivity.resPayloads.get(i));
                }
            }
            if (this.filterVisionList.size() > 0) {
                this.mAdapter = new VisionListAdapter(this, this.filterVisionList);
                this.rv_place.setAdapter(this.mAdapter);
                this.count.setText(this.filterVisionList.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getResources().getString(R.string.results));
                this.no_data.setVisibility(View.GONE);
                this.rv_place.setVisibility(View.VISIBLE);
                return;
            }
            this.count.setText(getResources().getString(R.string.no_results));
            this.no_data.setVisibility(View.VISIBLE);
            this.rv_place.setVisibility(View.GONE);
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
        apiService.getVisionProviderDetails(reqPayLoads).enqueue(new C08104());
    }
}
