package com.VURVhealth.vurvhealth.vision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.VURVhealth.vurvhealth.R;

public class VisionFilterActivity extends AppCompatActivity {
    private static boolean femaleClick = false;
    private static boolean maleClick = false;
    private TextView female;
    private LinearLayout llFemale;
    private LinearLayout ll_male;
    private TextView male;
    private ImageView sort_check1;
    private ImageView sort_check2;
    private TextView tvApply;
    private TextView tvReset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_prescription_screen);
        tvApply = (TextView) findViewById(R.id.tvApply);
        tvReset = (TextView) findViewById(R.id.tvReset);
        male = (TextView) findViewById(R.id.tvPrice);
        female = (TextView) findViewById(R.id.tvDistance);
        ll_male = (LinearLayout) findViewById(R.id.llPrice);
        llFemale = (LinearLayout) findViewById(R.id.llDistance);
        sort_check1 = (ImageView) findViewById(R.id.sort_check2);
        sort_check2 = (ImageView) findViewById(R.id.sort_check1);
        male.setText(getResources().getString(R.string.male));
        female.setText(getResources().getString(R.string.female));
        ll_male.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sort_check1.getVisibility() == View.VISIBLE) {
                    sort_check1.setVisibility(View.INVISIBLE);
                    maleClick = false;
                    return;
                }
                maleClick = true;
                sort_check1.setVisibility(View.VISIBLE);
            }
        });
        llFemale.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sort_check2.getVisibility() == View.VISIBLE) {
                    femaleClick = false;
                    sort_check2.setVisibility(View.INVISIBLE);
                    return;
                }
                femaleClick = true;
                sort_check2.setVisibility(View.VISIBLE);
            }
        });
        tvReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleClick = false;
                maleClick = false;
                sort_check1.setVisibility(View.INVISIBLE);
                sort_check2.setVisibility(View.INVISIBLE);
            }
        });
        tvApply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisionFilterActivity.this, VisionListActivity.class);
                i.putExtra("male", sort_check1.getVisibility() == View.VISIBLE ? "male" : "");
                i.putExtra("female", sort_check2.getVisibility() == View.VISIBLE ? "female" : "");
                setResult(101, i);
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (maleClick) {
            sort_check1.setVisibility(View.VISIBLE);
        } else {
            sort_check1.setVisibility(View.GONE);
        }
        if (femaleClick) {
            sort_check2.setVisibility(View.VISIBLE);
        } else {
            sort_check2.setVisibility(View.GONE);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
