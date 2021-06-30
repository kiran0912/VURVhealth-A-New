package com.VURVhealth.vurvhealth.medical.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

import java.util.ArrayList;

/**
 * Created by yqlabs on 24/1/17.
 */

public class DoctorsFilterActivity extends AppCompatActivity {

    private TextView tvApply, tvReset;
    private LinearLayout llNewPatients, llOpen, llADA, llMale, llFemale;
    private ImageView newpatient_check, open_check, ada_check, male_check, female_check;
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

        llNewPatients = (LinearLayout) findViewById(R.id.llNewPatients);
        llOpen = (LinearLayout) findViewById(R.id.llOpen);
        llADA = (LinearLayout) findViewById(R.id.llADA);
        llMale = (LinearLayout) findViewById(R.id.llMale);
        llFemale = (LinearLayout) findViewById(R.id.llFemale);

//        sort_check = (ImageView) findViewById(R.id.sort_check);
        newpatient_check = (ImageView) findViewById(R.id.newpatient_check);
        open_check = (ImageView) findViewById(R.id.open_check);
        ada_check = (ImageView) findViewById(R.id.ada_check);
        male_check = (ImageView) findViewById(R.id.male_check);
        female_check = (ImageView) findViewById(R.id.female_check);

        //When button is clicked
        llNewPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpatient_check.getVisibility() == View.VISIBLE) {
                    newpatient_check.setVisibility(View.INVISIBLE);
                    checkedList.remove("newPatient");


                } else {
                    checkedList.add("newPatient");
                    newpatient_check.setVisibility(View.VISIBLE);
                }
            }
        });
        //When button is clicked
        llOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open_check.getVisibility() == View.VISIBLE) {
//                    newpatient_check.setVisibility(View.INVISIBLE);
                    checkedList.remove("weekEnd");
                    open_check.setVisibility(View.INVISIBLE);
//                    ada_check.setVisibility(View.INVISIBLE);
                } else {
                    checkedList.add("weekEnd");
                    open_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llADA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ada_check.getVisibility() == View.VISIBLE) {
                    checkedList.remove("ada");
//                    newpatient_check.setVisibility(View.INVISIBLE);
//                    open_check.setVisibility(View.INVISIBLE);
                    ada_check.setVisibility(View.INVISIBLE);
                } else {
                    checkedList.add("ada");
                    ada_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male_check.getVisibility() == View.VISIBLE) {
                    checkedList.remove("male");
                    male_check.setVisibility(View.INVISIBLE);

                } else {
                    checkedList.add("male");
                    male_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (female_check.getVisibility() == View.VISIBLE) {
                    checkedList.remove("female");
//                    male_check.setVisibility(View.INVISIBLE);
                    female_check.setVisibility(View.INVISIBLE);
                } else {
                    checkedList.add("female");
                    female_check.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked to reset
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sort_check.setVisibility(View.INVISIBLE);
                checkedList.clear();
                newpatient_check.setVisibility(View.INVISIBLE);
                open_check.setVisibility(View.INVISIBLE);
                ada_check.setVisibility(View.INVISIBLE);
                male_check.setVisibility(View.INVISIBLE);
                female_check.setVisibility(View.INVISIBLE);

            }
        });

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorsFilterActivity.this, DoctorSearchResults.class);
                intent.putExtra("newPatient",
                        newpatient_check.getVisibility() == View.VISIBLE ? " accept_new_patients ='Y'"
                                : " (accept_new_patients ='Y' or accept_new_patients ='N')");
                intent.putExtra("openWeekends",
                        open_check.getVisibility() == View.VISIBLE ?
                                " and open_on_weekends ='Y'"
                                : " and (open_on_weekends ='Y' or open_on_weekends ='N')");

                intent.putExtra("adaAccess", ada_check.getVisibility() == View.VISIBLE ?
                        " and ada_accesible ='Y'"
                        : " and (ada_accesible ='Y' or ada_accesible ='N')");

                intent.putExtra("male", male_check.getVisibility() == View.VISIBLE ? " AND gender = 'M'" : "");
                intent.putExtra("female", female_check.getVisibility() == View.VISIBLE ? "AND gender = 'F'" : "");

//                intent.putExtra("languagetxt",specialtyValue);
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
            if (checkedList.get(i).equalsIgnoreCase("newPatient")) {
                newpatient_check.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("weekEnd")) {
                open_check.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("ada")) {
                ada_check.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("male")) {
                male_check.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("female")) {
                female_check.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
