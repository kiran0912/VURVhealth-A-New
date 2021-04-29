package com.VURVhealth.vurvhealth.medical.facilities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.VURVhealth.vurvhealth.R;

import java.util.ArrayList;

public class FacilityFilterActivity extends AppCompatActivity {

    private TextView tvApply, tvReset, tvNewPatients, tvOpen, tvADA;
    private LinearLayout llNewPatients, llOpen, llADA, llGender;
    private ImageView newpatient_check, open_check, ada_check;
    private boolean click = true;
    private static ArrayList<String> checkedList = new ArrayList<>();

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_doctor_screen);

        tvApply = (TextView) findViewById(R.id.tvApply);
        tvReset = (TextView) findViewById(R.id.tvReset);

        tvNewPatients = (TextView) findViewById(R.id.tvNewPatients);
        tvOpen = (TextView) findViewById(R.id.tvOpen);
        tvADA = (TextView) findViewById(R.id.tvADA);

//        llDistance = (LinearLayout) findViewById(R.id.llDistance);
        llNewPatients = (LinearLayout) findViewById(R.id.llNewPatients);
        llOpen = (LinearLayout) findViewById(R.id.llOpen);
        llADA = (LinearLayout) findViewById(R.id.llADA);
        llGender = (LinearLayout) findViewById(R.id.llGender);

//        sort_check = (ImageView) findViewById(R.id.sort_check);
        newpatient_check = (ImageView) findViewById(R.id.newpatient_check);
        newpatient_check.setVisibility(View.GONE);
        open_check = (ImageView) findViewById(R.id.open_check);
        ada_check = (ImageView) findViewById(R.id.ada_check);

        llGender.setVisibility(View.GONE);
        tvNewPatients.setText("Accredited by the joint commission");
        tvOpen.setText("Essential community provider");
        tvADA.setText("Handicapped accessible");

        //When button is clicked
        llNewPatients.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpatient_check.getVisibility() == View.VISIBLE) {

                    newpatient_check.setVisibility(View.INVISIBLE);
                    checkedList.remove("Accredited");

                } else {
                    checkedList.add("Accredited");
                    newpatient_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llOpen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open_check.getVisibility() == View.VISIBLE) {

                    checkedList.remove("communityProvider");
                    open_check.setVisibility(View.INVISIBLE);

                } else {
                    checkedList.add("communityProvider");
                    open_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llADA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ada_check.getVisibility() == View.VISIBLE) {

                    checkedList.remove("handicapped");
                    ada_check.setVisibility(View.INVISIBLE);
                } else {
                    checkedList.add("handicapped");
                    ada_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        tvReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList.clear();
                newpatient_check.setVisibility(View.INVISIBLE);
                open_check.setVisibility(View.INVISIBLE);
                ada_check.setVisibility(View.INVISIBLE);


            }
        });
        tvApply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacilityFilterActivity.this, FacilitySearchResult.class);
                intent.putExtra("handicapped",
                        ada_check.getVisibility() == View.VISIBLE ? " handicapped_Flag ='Y'"
                                : " (handicapped_Flag ='Y' or handicapped_Flag ='N')");
                intent.putExtra("communityProvider",
                        open_check.getVisibility() == View.VISIBLE ?
                                " and fac_ECPProvider ='Y'"
                                : " and (fac_ECPProvider ='Y' or fac_ECPProvider ='N')");

                intent.putExtra("Accredited", newpatient_check.getVisibility() == View.VISIBLE ?
                        " and Fac_JCAHOAccrediated ='Y'"
                        : " and (Fac_JCAHOAccrediated ='Y' or Fac_JCAHOAccrediated ='N')");

                // Set The Result in Intent
                setResult(101, intent);
                // finish The activity
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < checkedList.size(); i++) {
            if (checkedList.get(i).equalsIgnoreCase("handicapped")) {
                ada_check.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("communityProvider")) {
                open_check.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("Accredited")) {
                newpatient_check.setVisibility(View.VISIBLE);
            }
        }
    }
}
