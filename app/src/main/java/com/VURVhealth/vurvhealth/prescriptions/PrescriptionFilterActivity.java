package com.VURVhealth.vurvhealth.prescriptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public class PrescriptionFilterActivity extends AppCompatActivity {
    public static String checkedItem = Param.PRICE;
    private ImageView dosage_check1;
    private ImageView dosage_check2;
    private ImageView dosage_check3;
    private ImageView dosage_check4;
    private ImageView form_check;
    private LinearLayout llDistance;
    private LinearLayout llDosage10;
    private LinearLayout llDosage20;
    private LinearLayout llDosage40;
    private LinearLayout llDosage80;
    private LinearLayout llPrice;
    private LinearLayout llTablet;
    private ImageView sort_check1;
    private ImageView sort_check2;
    private TextView tvApply;
    private TextView tvReset;
  

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_prescription_screen);
        tvApply = (TextView) findViewById(R.id.tvApply);
        tvReset = (TextView) findViewById(R.id.tvReset);
        llDistance = (LinearLayout) findViewById(R.id.llDistance);
        llPrice = (LinearLayout) findViewById(R.id.llPrice);
        sort_check1 = (ImageView) findViewById(R.id.sort_check1);
        sort_check2 = (ImageView) findViewById(R.id.sort_check2);




        llDistance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sort_check1.getVisibility() == View.VISIBLE) {
                    sort_check1.setVisibility(View.INVISIBLE);
                    sort_check2.setVisibility(View.VISIBLE);
                    checkedItem = Param.PRICE;
                    return;
                }
                sort_check1.setVisibility(View.VISIBLE);
                sort_check2.setVisibility(View.INVISIBLE);
                checkedItem = "distance";
            }
        });
        llPrice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sort_check2.getVisibility() == View.VISIBLE) {
                    checkedItem = "distance";
                    sort_check1.setVisibility(View.VISIBLE);
                    sort_check2.setVisibility(View.INVISIBLE);
                    return;
                }
                checkedItem = Param.PRICE;
                sort_check1.setVisibility(View.INVISIBLE);
                sort_check2.setVisibility(View.VISIBLE);
            }
        });
        tvReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedItem = Param.PRICE;
                sort_check1.setVisibility(View.INVISIBLE);
                sort_check2.setVisibility(View.INVISIBLE);
            }
        });
        tvApply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrescriptionFilterActivity.this, BestPricesNearbyActivity.class);
                i.putExtra(Param.PRICE, sort_check2.getVisibility() == View.VISIBLE ? "y" : "n");
                i.putExtra("distance", sort_check1.getVisibility() == View.VISIBLE ? "y" : "n");
                setResult(101, i);
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (checkedItem.equalsIgnoreCase(Param.PRICE)) {
            //sort_check2.setVisibility(View.VISIBLE);
            sort_check1.setVisibility(View.INVISIBLE);
            sort_check2.setVisibility(View.VISIBLE);
        } else if (checkedItem.equalsIgnoreCase("distance")) {
            //sort_check1.setVisibility(View.VISIBLE);
            sort_check1.setVisibility(View.VISIBLE);
            sort_check2.setVisibility(View.INVISIBLE);
        }


    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
