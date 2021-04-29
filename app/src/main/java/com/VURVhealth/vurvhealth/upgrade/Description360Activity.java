package com.VURVhealth.vurvhealth.upgrade;

import android.app.Dialog;
import android.content.Intent;
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
 * Created by yqlabs on 7/2/17.
 */

public class Description360Activity extends AppCompatActivity {

    private TextView toolbar_txt, desc_heading, desc_para, tvRateBanner;
    private ImageView backBtn, infoRx, infoDoctor, infoFacility, infoDental, infoVision, infoTelemed;
    LinearLayout llPrescription, llDoctors, llFacilities, llDental, llVision, llTelemed;

    /** Called when the activity is first created. */
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
        infoDental = (ImageView) findViewById(R.id.infoDental);
        infoVision = (ImageView) findViewById(R.id.infoVision);
        infoTelemed = (ImageView) findViewById(R.id.infoTelemed);

        llPrescription = (LinearLayout) findViewById(R.id.llPrescription);
        llDoctors = (LinearLayout) findViewById(R.id.llDoctors);
        llFacilities = (LinearLayout) findViewById(R.id.llFacilities);
        llDental = (LinearLayout) findViewById(R.id.llDental);
        llVision = (LinearLayout) findViewById(R.id.llVision);
        llTelemed = (LinearLayout) findViewById(R.id.llTelemed);

        toolbar_txt.setText("360");
        desc_heading.setText(getResources().getString(R.string.heading360));
        desc_para.setText(Html.fromHtml(getResources().getString(R.string.desc360)));
        tvRateBanner.setText(getResources().getString(R.string.starting_at)+UpgradeSubscriptionActivity.responsePayLoad.get(0).getSubscriptionPrice()+" / Month");

        llPrescription.setVisibility(View.VISIBLE);
        llDoctors.setVisibility(View.VISIBLE);
        llFacilities.setVisibility(View.VISIBLE);
        llDental.setVisibility(View.VISIBLE);
        llVision.setVisibility(View.VISIBLE);
        llTelemed.setVisibility(View.VISIBLE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Description360Activity.this, UpgradeSubscriptionActivity.class));
                finish();
            }
        });

        infoRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPrescCustomAlertDialog();
            }
        });
        infoDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDoctorCustomAlertDialog();
            }
        });
        infoFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoFacilityCustomAlertDialog();
            }
        });
        infoDental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDentalCustomAlertDialog();
            }
        });
        infoVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoVisionCustomAlertDialog();
            }
        });
        infoTelemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTelemedCustomAlertDialog();
            }
        });

        tvRateBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Description360Activity.this,PaymentOptions360Activity.class);
                startActivity(intent);
//                finish();
            }
        });

    }

    //show info popup for prescription
    private void infoPrescCustomAlertDialog() {

        final Dialog customDialog = new Dialog(Description360Activity.this);
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
    //show info popup for doctors
    private void infoDoctorCustomAlertDialog() {

        final Dialog customDialog = new Dialog(Description360Activity.this);
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

    }

    //show info popup for facilities
    private void infoFacilityCustomAlertDialog() {

        final Dialog customDialog = new Dialog(Description360Activity.this);
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

    //show info popup for dental
    private void infoDentalCustomAlertDialog() {

        final Dialog customDialog = new Dialog(Description360Activity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dental_info_screen);

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

    //show info popup for vision
    private void infoVisionCustomAlertDialog() {

        final Dialog customDialog = new Dialog(Description360Activity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.vision_info_screen);

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

    //show info popup for telemed
    private void infoTelemedCustomAlertDialog() {

        final Dialog customDialog = new Dialog(Description360Activity.this);
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
