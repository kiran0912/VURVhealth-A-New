package com.VURVhealth.vurvhealth.prescriptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.upgrade.UpgradeRxFlipActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class PrescriptionBannerActivity extends AppCompatActivity {
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
    private TextView tvInsure;
    private TextView tvPatient;
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
        tvHealth = (TextView) findViewById(R.id.tvHealth);
        rxBinNum = (TextView) findViewById(R.id.rxBinNum);
        rxPcnNum = (TextView) findViewById(R.id.rxPcnNum);
        raMemID = (TextView) findViewById(R.id.raMemID);
        rxGRP = (TextView) findViewById(R.id.rxGRP);
        name = (TextView) findViewById(R.id.name);
        vurv_id = (TextView) findViewById(R.id.vurv_id);
        tvInsure = (TextView) findViewById(R.id.tvInsure);
        tvExp = (TextView) findViewById(R.id.tvExp);
        tvPlan = (TextView) findViewById(R.id.tvPlan);
        cardImg = (FrameLayout) findViewById(R.id.cardImg);
        cardImg.setBackgroundResource(R.drawable.card_rx_new);
        tvExp.setVisibility(View.INVISIBLE);
        tvPlan.setVisibility(View.INVISIBLE);
        SharedPreferences prefsData = getSharedPreferences("VURVProfileDetails", 0);
        vurvName = prefsData.getString("firstName", "");
        lastName = prefsData.getString("lastName", "");
        vurvID = prefsData.getString("vurvId", "");
        tvVurvName.setText(vurvName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastName);


        cardImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PrescriptionBannerActivity.this, UpgradeRxFlipActivity.class);
                intent.putExtra("activity", "PrescriptionBannerActivity");
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


        // tvVURVID.setText("VURV ID: " + vurvID);
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

       // vurv_id.setText("VURV ID: " + vurvID);
        tvPatient.setText(Html.fromHtml(getResources().getString(R.string.prescription_id1)));
        tvHealth.setText(Html.fromHtml(getResources().getString(R.string.prescrption_id2)));
        img_cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        openBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionBannerActivity.this, UpgradeRxFlipActivity.class);
                intent.putExtra("activity", "PrescriptionBannerActivity");
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
