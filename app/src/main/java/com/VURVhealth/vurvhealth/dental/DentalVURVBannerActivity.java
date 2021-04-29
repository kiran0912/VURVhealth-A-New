package com.VURVhealth.vurvhealth.dental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.upgrade.UpgradeDentalFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeSubscriptionActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeVisionFlipActivity;
import com.VURVhealth.vurvhealth.vision.VisionVURVBannerActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.text.SimpleDateFormat;

/**
 * Created by yqlabs on 10/5/17.
 */

public class DentalVURVBannerActivity extends AppCompatActivity {
    private FrameLayout cardImg;
    private ImageView img_cancelBtn;
    private String lastName;
    private TextView name;
    private Button openBtn;
    private TextView raMemID;
    private TextView rxBinNum;
    private TextView rxGRP;
    private TextView rxPcnNum;
    private TextView tvExp;
    private TextView tvHealth;
    private TextView tvPatient;
    private TextView tvPatient2;
    private TextView tvPlan;
    private TextView tvVURVID;
    private TextView tvVurvName;
    private String vurvID;
    private String vurvName;
    private TextView vurv_id;

    /* renamed from: com.VURVhealth.VURVhealth.dental.DentalVURVBannerActivity$1 */
    class C03781 implements View.OnClickListener {
        C03781() {
        }

        public void onClick(View v) {
            DentalVURVBannerActivity.this.finish();
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.dental.DentalVURVBannerActivity$2 */
    class C03792 implements View.OnClickListener {
        C03792() {
        }

        public void onClick(View v) {
            DentalVURVBannerActivity.this.startActivity(new Intent(DentalVURVBannerActivity.this, UpgradeDentalFlipActivity.class));
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurvid_banner_screen);
        this.img_cancelBtn = (ImageView) findViewById(R.id.img_cancelBtn);
        this.openBtn = (Button) findViewById(R.id.openBtn);
        this.tvVurvName = (TextView) findViewById(R.id.tvVurvName);
        this.tvVURVID = (TextView) findViewById(R.id.tvVURVID);
        this.tvPatient = (TextView) findViewById(R.id.tvPatient);
        this.tvPatient2 = (TextView) findViewById(R.id.tvPatient2);
        this.tvPatient2.setVisibility(View.VISIBLE);
        this.tvHealth = (TextView) findViewById(R.id.tvHealth);
        this.rxBinNum = (TextView) findViewById(R.id.rxBinNum);
        this.rxPcnNum = (TextView) findViewById(R.id.rxPcnNum);
        this.raMemID = (TextView) findViewById(R.id.raMemID);
        this.rxGRP = (TextView) findViewById(R.id.rxGRP);
        this.name = (TextView) findViewById(R.id.name);
        this.vurv_id = (TextView) findViewById(R.id.vurv_id);
        this.tvExp = (TextView) findViewById(R.id.tvExp);
        this.tvPlan = (TextView) findViewById(R.id.tvPlan);
        this.cardImg = (FrameLayout) findViewById(R.id.cardImg);
        this.tvHealth.setVisibility(View.GONE);
        this.cardImg.setBackgroundResource(R.drawable.card_dental_new);
        this.tvPatient.setText(Html.fromHtml(getResources().getString(R.string.dental_vurv_txt)));
        this.tvPatient2.setText(Html.fromHtml(getResources().getString(R.string.dental_vurv_txt1)));
        this.tvPatient.setTextColor(ContextCompat.getColor(this, R.color.black));
        SharedPreferences prefsData = getSharedPreferences("VURVProfileDetails", 0);
        this.vurvName = prefsData.getString("firstName", "");
        this.lastName = prefsData.getString("lastName", "");
        this.vurvID = prefsData.getString("vurvId", "");
        this.tvVurvName.setText(this.vurvName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.lastName);



        cardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DentalVURVBannerActivity.this, UpgradeDentalFlipActivity.class));

            }
        });
        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvID.substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(7, 11));
            this.tvVURVID.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.tvVURVID.setText("VURV ID: " + this.vurvID);
        }
//        this.tvVURVID.setText("VURV ID: " + this.vurvID);
        this.rxGRP.setVisibility(View.GONE);
        this.raMemID.setVisibility(View.GONE);
        this.name.setText(this.vurvName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.lastName);

        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvID.substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(7, 11));
            this.vurv_id.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.vurv_id.setText("VURV ID: " + this.vurvID);
        }

//        this.vurv_id.setText("VURV ID: " + this.vurvID);
        this.rxBinNum.setVisibility(View.GONE);
        this.rxPcnNum.setVisibility(View.GONE);
        String dobFormat = null;
        try {
            dobFormat = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(prefsData.getString("subscription_end_date", "12/12/2017")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tvExp.setText(getResources().getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
        this.tvPlan.setText(getString(R.string.plan) + " CARE");
        this.img_cancelBtn.setOnClickListener(new C03781());
        this.openBtn.setOnClickListener(new C03792());
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
