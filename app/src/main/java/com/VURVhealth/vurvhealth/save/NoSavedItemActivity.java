package com.VURVhealth.vurvhealth.save;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.help.HelpActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterAllRequestPojo;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterAllResponsePojo;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoSavedItemActivity extends SuperAppCompactActivity {
    public static ArrayList<SaveForLaterAllResponsePojo> saveForLaterAllResponsePojos;
    private Context context;
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSearch;
    private LinearLayout llVurv;
    public SharedPreferences prefsLoginData;
    private TextView tvPress;
    
    
   
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_saved_item);
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        tvPress = (TextView) findViewById(R.id.tvPress);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        customTextView(tvPress);
        llSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoSavedItemActivity.this, StartScreenActivity.class));
                finish();
            }
        });
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoSavedItemActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoSavedItemActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoSavedItemActivity.this, HelpActivity.class));
                finish();
            }
        });
        if (checkInternet()) {
            saveForLaterAllService();
        } else {
            Toast.makeText(NoSavedItemActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveForLaterAllService() {
        showProgressDialog(NoSavedItemActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(NoSavedItemActivity.this).create(ApiInterface.class);
        ArrayList<SaveForLaterAllRequestPojo> saveForLaterRequestList = new ArrayList();
        SaveForLaterAllRequestPojo saveForLaterRequest = new SaveForLaterAllRequestPojo();
        saveForLaterRequest.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAll(saveForLaterRequestList).enqueue(new Callback<ArrayList<SaveForLaterAllResponsePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<SaveForLaterAllResponsePojo>> call, Response<ArrayList<SaveForLaterAllResponsePojo>> response) {
                if (response.isSuccessful()) {
                    saveForLaterAllResponsePojos = (ArrayList) response.body();
                    if (saveForLaterAllResponsePojos.size() > 0) {
                        startActivity(new Intent(NoSavedItemActivity.this, SaveItemActivity.class));
                        finish();
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<SaveForLaterAllResponsePojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(getString(R.string.press_the) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        Drawable d = getResources().getDrawable(R.drawable.saveditem_star);
        d.setBounds(12, 12, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        spanTxt.setSpan(new ImageSpan(d, 1), spanTxt.length() - 1, spanTxt.length(), 17);
        view.setText(spanTxt);
        spanTxt.append("  ").append(getString(R.string.button_to_return));
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, BufferType.SPANNABLE);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
