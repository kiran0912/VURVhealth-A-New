package com.VURVhealth.vurvhealth.myProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.CurrentPackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.GetPackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.CareDescriptionActivity;
import com.VURVhealth.vurvhealth.upgrade.Description360Activity;
import com.VURVhealth.vurvhealth.upgrade.PulseDescriptionActivity;
import com.VURVhealth.vurvhealth.upgrade.RxDescriptionActivity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePackageActivity extends SuperAppCompactActivity {
    private ImageView backBtn;
    private ArrayList<CurrentPackageResPayload> currentPackageResPayload;
    private LinearLayout ll360Package;
    private LinearLayout llCarePackage;
    private LinearLayout llPulsePackage;
    private LinearLayout llRxPackage;
    private SharedPreferences prefsData;
    private TextView tvPopup;
    private int userId;
    

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_subscription_screen);
        tvPopup = (TextView) findViewById(R.id.tvPopup);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        ll360Package.setVisibility(View.VISIBLE);
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        userId = prefsData.getInt("userId", 1);
        llRxPackage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llRxPackage.setBackgroundResource(R.drawable.gradient_package);
                llPulsePackage.setBackgroundResource(R.drawable.package_border);
                llCarePackage.setBackgroundResource(R.drawable.package_border);
                startActivity(new Intent(ChangePackageActivity.this, RxDescriptionActivity.class));
            }
        });
        llPulsePackage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llRxPackage.setBackgroundResource(R.drawable.package_border);
                llPulsePackage.setBackgroundResource(R.drawable.gradient_package);
                llCarePackage.setBackgroundResource(R.drawable.package_border);
                ll360Package.setBackgroundResource(R.drawable.package_border);
                startActivity(new Intent(ChangePackageActivity.this, PulseDescriptionActivity.class));
            }
        });
        llCarePackage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llRxPackage.setBackgroundResource(R.drawable.package_border);
                llPulsePackage.setBackgroundResource(R.drawable.package_border);
                llCarePackage.setBackgroundResource(R.drawable.gradient_package);
                ll360Package.setBackgroundResource(R.drawable.package_border);
                startActivity(new Intent(ChangePackageActivity.this, CareDescriptionActivity.class));
            }
        });
        ll360Package.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                llRxPackage.setBackgroundResource(R.drawable.package_border);
                llPulsePackage.setBackgroundResource(R.drawable.package_border);
                llCarePackage.setBackgroundResource(R.drawable.package_border);
                ll360Package.setBackgroundResource(R.drawable.gradient_package);
                startActivity(new Intent(ChangePackageActivity.this, Description360Activity.class));
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePackageActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        if (checkInternet()) {
            currentPackageService();
            getPackage();
            return;
        }
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
    }

    private void getPackage() {
        showProgressDialog(ChangePackageActivity.this);
        ((ApiInterface) ApiClient.getClient(ChangePackageActivity.this).create(ApiInterface.class)).getPackageInfo().enqueue(new Callback<ArrayList<GetPackageResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<GetPackageResPayload>> call, Response<ArrayList<GetPackageResPayload>> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetPackageResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(ChangePackageActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void currentPackageService() {
        showProgressDialog(ChangePackageActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(ChangePackageActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(userId));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getCurrentPackage(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<CurrentPackageResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<CurrentPackageResPayload>> call, Response<ArrayList<CurrentPackageResPayload>> response) {
                if (response.isSuccessful()) {
                    currentPackageResPayload = (ArrayList) response.body();
                    if (((CurrentPackageResPayload) currentPackageResPayload.get(0)).getTermId().equalsIgnoreCase("21")) {
                        llRxPackage.setBackgroundResource(R.drawable.gradient_package);
                        llPulsePackage.setBackgroundResource(R.drawable.package_border);
                        llCarePackage.setBackgroundResource(R.drawable.package_border);
                        ll360Package.setBackgroundResource(R.drawable.package_border);
                    } else if (((CurrentPackageResPayload) currentPackageResPayload.get(0)).getTermId().equalsIgnoreCase("22")) {
                        llRxPackage.setVisibility(View.GONE);
                        llPulsePackage.setBackgroundResource(R.drawable.gradient_package);
                        llCarePackage.setBackgroundResource(R.drawable.package_border);
                        ll360Package.setBackgroundResource(R.drawable.package_border);
                    } else if (((CurrentPackageResPayload) currentPackageResPayload.get(0)).getTermId().equalsIgnoreCase("23")) {
                        llRxPackage.setVisibility(View.GONE);
                        llPulsePackage.setBackgroundResource(R.drawable.package_border);
                        llCarePackage.setBackgroundResource(R.drawable.gradient_package);
                        ll360Package.setBackgroundResource(R.drawable.package_border);
                    } else {
                        llRxPackage.setVisibility(View.GONE);
                        llPulsePackage.setBackgroundResource(R.drawable.package_border);
                        llCarePackage.setBackgroundResource(R.drawable.package_border);
                        ll360Package.setBackgroundResource(R.drawable.gradient_package);
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
}
