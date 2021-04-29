package com.VURVhealth.vurvhealth.upgrade;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

/**
 * Created by yqlabs on 21/3/17.
 */

public class PulseDescPopupActivity extends AppCompatActivity {
    private TextView toolbar_txt, desc_heading, desc_para, tvRateBanner;
    private ImageView backBtn, infoRx, infoDoctor, infoFacility,infoTelemed;
    LinearLayout llPrescription, llDoctors, llFacilities, llTelemed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_screen);

        toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
        desc_heading = (TextView) findViewById(R.id.desc_heading);
        desc_para = (TextView) findViewById(R.id.desc_para);
        tvRateBanner = (TextView) findViewById(R.id.tvRateBanner);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        infoRx = (ImageView) findViewById(R.id.infoRx);
        infoDoctor = (ImageView) findViewById(R.id.infoDoctor);
        infoFacility = (ImageView) findViewById(R.id.infoFacility);
        infoTelemed = (ImageView) findViewById(R.id.infoTelemed);

        llPrescription = (LinearLayout) findViewById(R.id.llPrescription);
        llDoctors = (LinearLayout) findViewById(R.id.llDoctors);
        llFacilities = (LinearLayout) findViewById(R.id.llFacilities);
        llTelemed = (LinearLayout) findViewById(R.id.llTelemed);

        toolbar_txt.setText(getResources().getString(R.string.pulse));
        desc_heading.setText(getResources().getString(R.string.pulse_heading));
        desc_para.setText(Html.fromHtml(getResources().getString(R.string.pulse_desc)));
        tvRateBanner.setText(getResources().getString(R.string.starting_at));

        llPrescription.setVisibility(View.VISIBLE);
        llDoctors.setVisibility(View.VISIBLE);
        llFacilities.setVisibility(View.VISIBLE);
        llTelemed.setVisibility(View.VISIBLE);

        infoTelemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infoTelemedCustomAlertDialog();

            }
        });

        tvRateBanner.setVisibility(View.GONE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infoDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDoctorCustomAlertDialog(v);
            }
        });

    }

    public void infoPrescCustomAlertDialog(View view) {

        final Dialog customDialog = new Dialog(PulseDescPopupActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.presc_info_screen);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;

        ImageView img_cancelBtn = (ImageView) customDialog.findViewById(R.id.img_cancelBtn);

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();

        img_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();

            }
        });

    }

    public void infoDoctorCustomAlertDialog(View view) {

        final Dialog customDialog = new Dialog(PulseDescPopupActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.doctor_info_screen);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;

        ImageView img_cancelBtn = (ImageView) customDialog.findViewById(R.id.img_cancelBtn);

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();

        assert img_cancelBtn != null;
        img_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();

            }
        });

      /*  infoRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                infoPrescCustomAlertDialog();
            }
        });

        infoFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                infoFacilityCustomAlertDialog();
            }
        });*/

    }

    public void infoFacilityCustomAlertDialog(View view) {

        final Dialog customDialog = new Dialog(PulseDescPopupActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.facility_info_screen);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;

        ImageView img_cancelBtn = (ImageView) customDialog.findViewById(R.id.img_cancelBtn);

        customDialog.getWindow().setAttributes(lp);
        customDialog.show();

        img_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();

            }
        });

    }

    private void infoTelemedCustomAlertDialog() {

        final Dialog customDialog = new Dialog(PulseDescPopupActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.telemed_info_screen);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;

        ImageView img_cancelBtn = (ImageView) customDialog.findViewById(R.id.img_cancelBtn);

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();

        img_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
