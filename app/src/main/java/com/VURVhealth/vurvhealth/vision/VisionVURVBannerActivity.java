package com.VURVhealth.vurvhealth.vision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.prescriptions.PrescriptionBannerActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeRxFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeVisionFlipActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.text.SimpleDateFormat;

public class VisionVURVBannerActivity extends AppCompatActivity {
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

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurvid_banner_screen);
        img_cancelBtn = (ImageView) findViewById(R.id.img_cancelBtn);
        openBtn = (Button) findViewById(R.id.openBtn);
        tvVurvName = (TextView) findViewById(R.id.tvVurvName);
        tvVURVID = (TextView) findViewById(R.id.tvVURVID);
        tvPatient = (TextView) findViewById(R.id.tvPatient);
        tvPatient2 = (TextView) findViewById(R.id.tvPatient2);
        tvPatient2.setVisibility(View.VISIBLE);
        tvHealth = (TextView) findViewById(R.id.tvHealth);
        rxBinNum = (TextView) findViewById(R.id.rxBinNum);
        rxPcnNum = (TextView) findViewById(R.id.rxPcnNum);
        raMemID = (TextView) findViewById(R.id.raMemID);
        rxGRP = (TextView) findViewById(R.id.rxGRP);
        name = (TextView) findViewById(R.id.name);
        vurv_id = (TextView) findViewById(R.id.vurv_id);
        tvExp = (TextView) findViewById(R.id.tvExp);
        tvPlan = (TextView) findViewById(R.id.tvPlan);
        cardImg = (FrameLayout) findViewById(R.id.cardImg);
        cardImg.setBackgroundResource(R.drawable.card_vision_new);
        SharedPreferences prefsData = getSharedPreferences("VURVProfileDetails", 0);
        vurvName = prefsData.getString("firstName", "");
        lastName = prefsData.getString("lastName", "");
        vurvID = prefsData.getString("vurvId", "");
        tvVurvName.setText(vurvName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastName);


        cardImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VisionVURVBannerActivity.this, UpgradeVisionFlipActivity.class);
                intent.putExtra("activity", "VisionVURVBannerActivity");
                startActivity(intent);            }
        });

        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvID.substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(7, 11));
            tvVURVID.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            tvVURVID.setText("VURV ID: " + vurvID);
        }


//        tvVURVID.setText("VURV ID: " + vurvID);
        rxGRP.setVisibility(View.GONE);
        raMemID.setVisibility(View.GONE);
        name.setText(vurvName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastName);


        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvID.substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvID.substring(7, 11));
            vurv_id.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            vurv_id.setText("VURV ID: " + vurvID);
        }

//        vurv_id.setText("VURV ID: " + vurvID);
        rxBinNum.setVisibility(View.GONE);
        rxPcnNum.setVisibility(View.GONE);
        String dobFormat = null;
        try {
            dobFormat = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(prefsData.getString("vurv_mem_exp_date", "12/12/2017")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvExp.setText(getResources().getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
        tvPlan.setText(getString(R.string.plan) + " CARE");
        tvPatient.setText(Html.fromHtml(getResources().getString(R.string.vision_vurv_txt)));
        tvPatient2.setText(Html.fromHtml(getResources().getString(R.string.vision_vurv_text1)));
        tvPatient.setTextColor(ContextCompat.getColor(this, R.color.black));
        tvHealth.setVisibility(View.GONE);
        img_cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        openBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisionVURVBannerActivity.this, UpgradeVisionFlipActivity.class);
                intent.putExtra("activity", "VisionVURVBannerActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
