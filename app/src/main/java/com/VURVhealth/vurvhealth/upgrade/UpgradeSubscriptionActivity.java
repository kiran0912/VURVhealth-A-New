package com.VURVhealth.vurvhealth.upgrade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.CurrentPackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.GetPackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.pojos.GetSubPackageReqPayload;
import com.VURVhealth.vurvhealth.upgrade.pojos.GetSubPackageResPayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpgradeSubscriptionActivity extends SuperAppCompactActivity {
    public static ArrayList<GetPackageResPayload> getPackageResPayload;
    public static ArrayList<GetSubPackageResPayload> responsePayLoad;
    public static Map<String, ArrayList<GetSubPackageResPayload>> stringArrayListHashMap;
    private ImageView backBtn;
    private ArrayList<CurrentPackageResPayload> currentPackageResPayload;
    private Adapter<UpgradeSubscriptionAdapter.DataObjectHolder> mAdapter;
    private LayoutManager mLayoutManager;
    private SharedPreferences prefsData;
    private RecyclerView rvPackages;
    private TextView tvPopup;
    private int userId;

   
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_subscription_screen);
        tvPopup = (TextView) findViewById(R.id.tvPopup);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        rvPackages = (RecyclerView) findViewById(R.id.rvPackages);
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        userId = prefsData.getInt("userId", 1);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPackages.setLayoutManager(mLayoutManager);
        rvPackages.setItemAnimator(new DefaultItemAnimator());
        if (checkInternet()) {
            getPackage();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getSubPackage(final int position, String termId) {
        try {
            showProgressDialog(this);
            ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
            ArrayList<GetSubPackageReqPayload> getSubPackageReqPayloads = new ArrayList();
            GetSubPackageReqPayload getSubPackageReqPayload = new GetSubPackageReqPayload();
            getSubPackageReqPayload.setPackageId(Integer.valueOf(Integer.parseInt(termId)));
            getSubPackageReqPayloads.add(getSubPackageReqPayload);
            apiService.getSubPackage(getSubPackageReqPayloads).enqueue(new Callback<ArrayList<GetSubPackageResPayload>>() {
                public void onResponse(Call<ArrayList<GetSubPackageResPayload>> call, Response<ArrayList<GetSubPackageResPayload>> response) {
                    if (response.isSuccessful()) {
                        responsePayLoad = (ArrayList) response.body();
                        stringArrayListHashMap = new HashMap();
                        stringArrayListHashMap.clear();
                        dismissProgressDialog();
                        ArrayList<GetSubPackageResPayload> singleArrayList = new ArrayList();
                        ArrayList<GetSubPackageResPayload> spouseArrayList = new ArrayList();
                        ArrayList<GetSubPackageResPayload> familyArrayList = new ArrayList();
                        Iterator it = responsePayLoad.iterator();
                        while (it.hasNext()) {
                            GetSubPackageResPayload subPackageResPayload = (GetSubPackageResPayload) it.next();
                            if (subPackageResPayload.getPostTitle().contains("Single")) {
                                singleArrayList.add(subPackageResPayload);
                            }
                            if (subPackageResPayload.getPostTitle().contains("Spouse")) {
                                spouseArrayList.add(subPackageResPayload);
                            }
                            if (subPackageResPayload.getPostTitle().contains("Family")) {
                                familyArrayList.add(subPackageResPayload);
                            }
                            stringArrayListHashMap.put("Single", singleArrayList);
                            stringArrayListHashMap.put("Spouse", spouseArrayList);
                            stringArrayListHashMap.put("Family", familyArrayList);
                        }
                        if (position == 0) {
                            startActivity(new Intent(UpgradeSubscriptionActivity.this, RxDescriptionActivity.class));
                        } else if (position == 1) {
                            startActivity(new Intent(UpgradeSubscriptionActivity.this, PulseDescriptionActivity.class));
                        } else if (position == 2) {
                            startActivity(new Intent(UpgradeSubscriptionActivity.this, CareDescriptionActivity.class));
                        } else if (position == 3) {
                            startActivity(new Intent(UpgradeSubscriptionActivity.this, Description360Activity.class));
                        }
                    }
                    dismissProgressDialog();
                }

                public void onFailure(Call<ArrayList<GetSubPackageResPayload>> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(UpgradeSubscriptionActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.v("Upgrad", e.getMessage());
        }
    }

    private void getPackage() {
        showProgressDialog(UpgradeSubscriptionActivity.this);
        ((ApiInterface) ApiClient.getClient(UpgradeSubscriptionActivity.this).create(ApiInterface.class)).getPackageInfo().enqueue(new Callback<ArrayList<GetPackageResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<GetPackageResPayload>> call, Response<ArrayList<GetPackageResPayload>> response) {
                if (response.isSuccessful()) {
                    getPackageResPayload = (ArrayList) response.body();
                    mAdapter = new UpgradeSubscriptionAdapter(UpgradeSubscriptionActivity.this, getPackageResPayload);
                    Editor editor = getSharedPreferences("VURVProfileDetails", 0).edit();
                    rvPackages.setAdapter(mAdapter);
                    dismissProgressDialog();
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<GetPackageResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(UpgradeSubscriptionActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void currentPackageService() {
        showProgressDialog(UpgradeSubscriptionActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(UpgradeSubscriptionActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(userId));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getCurrentPackage(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<CurrentPackageResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<CurrentPackageResPayload>> call, Response<ArrayList<CurrentPackageResPayload>> response) {
                if (response.isSuccessful()) {
                    currentPackageResPayload = (ArrayList) response.body();
                    if (!(((CurrentPackageResPayload) currentPackageResPayload.get(0)).getTermId().equalsIgnoreCase("21") || ((CurrentPackageResPayload) currentPackageResPayload.get(0)).getTermId().equalsIgnoreCase("22") || !((CurrentPackageResPayload) currentPackageResPayload.get(0)).getTermId().equalsIgnoreCase("23"))) {
                    }
                    dismissProgressDialog();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<CurrentPackageResPayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
