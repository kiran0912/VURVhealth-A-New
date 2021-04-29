package com.VURVhealth.vurvhealth.dental;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

import java.util.ArrayList;

/**
 * Created by yqlabs on 18/3/17.
 */

public class DentalFilterActivity extends AppCompatActivity {

    private TextView tvApply, tvReset;
    private LinearLayout llGenDentistry, llSpecialist,llGroup,llPractitioner;
    private ImageView ChkGenDentistry, chkSpecialist,chkGroup,chkPractitioner;
    private boolean click = true;
    private static ArrayList<String> checkedList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_dental_screen);

        tvApply = (TextView) findViewById(R.id.tvApply);
        tvReset = (TextView) findViewById(R.id.tvReset);

        llGenDentistry = (LinearLayout) findViewById(R.id.llGenDentistry);
        llSpecialist = (LinearLayout) findViewById(R.id.llSpecialist);
        llGroup = (LinearLayout) findViewById(R.id.llGroup);
        llPractitioner = (LinearLayout) findViewById(R.id.llPractitioner);

        ChkGenDentistry = (ImageView) findViewById(R.id.ChkGenDentistry);
        chkSpecialist = (ImageView) findViewById(R.id.chkSpecialist);
        chkGroup = (ImageView) findViewById(R.id.chkGroup);
        chkPractitioner = (ImageView) findViewById(R.id.chkPractitioner);

        llGenDentistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChkGenDentistry.getVisibility() == View.VISIBLE) {
                    ChkGenDentistry.setVisibility(View.INVISIBLE);
                    checkedList.remove("general");


                } else {
                    checkedList.add("general");
                    ChkGenDentistry.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkSpecialist.getVisibility() == View.VISIBLE) {
                    chkSpecialist.setVisibility(View.INVISIBLE);
                    checkedList.remove("specialist");


                } else {
                    checkedList.add("specialist");
                    chkSpecialist.setVisibility(View.VISIBLE);
                }
            }
        });
        //When button is clicked
        llGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkGroup.getVisibility() == View.VISIBLE) {
                    chkGroup.setVisibility(View.INVISIBLE);
                    checkedList.remove("group");


                } else {
                    checkedList.add("group");
                    chkGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        //When button is clicked
        llPractitioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkPractitioner.getVisibility() == View.VISIBLE) {
                    chkPractitioner.setVisibility(View.INVISIBLE);
                    checkedList.remove("practitioner");


                } else {
                    checkedList.add("practitioner");
                    chkPractitioner.setVisibility(View.VISIBLE);
                }
            }
        });


        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChkGenDentistry.setVisibility(View.INVISIBLE);
                chkSpecialist.setVisibility(View.INVISIBLE);
                chkGroup.setVisibility(View.INVISIBLE);
                chkPractitioner.setVisibility(View.INVISIBLE);
            }
        });

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DentalFilterActivity.this, DentalListActivity.class);

                intent.putExtra("generalDentistry", ChkGenDentistry.getVisibility() == View.VISIBLE ? " gdOrSpCode = 'General Dentistry'" : "(gdOrSpCode ='General Dentistry' or gdOrSpCode ='Specialist')");
                intent.putExtra("specialist", chkSpecialist.getVisibility() == View.VISIBLE ? " AND gdOrSpCode = 'Specialist'" : " and (gdOrSpCode ='General Dentistry' or gdOrSpCode ='Specialist')");
                intent.putExtra("group", chkGroup.getVisibility() == View.VISIBLE ? " AND practiceType = 'Group'" : " and (practiceType ='Group' or practiceType ='Solo')");
                intent.putExtra("solo_practitioner", chkPractitioner.getVisibility() == View.VISIBLE ? " AND practiceType = 'Solo'" : " and (practiceType ='Group' or practiceType ='Solo')");

        /*GdOrSpCode: 'General Dentistry'
    GdOrSpCode: 'Specialist'
    PracticeType: 'Group'
    PracticeType: 'Solo'*/
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
            if (checkedList.get(i).equalsIgnoreCase("general")) {
                ChkGenDentistry.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("specialist")) {
                chkSpecialist.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("group")) {
                chkGroup.setVisibility(View.VISIBLE);
            } else if (checkedList.get(i).equalsIgnoreCase("practitioner")) {
                chkPractitioner.setVisibility(View.VISIBLE);
            }
        }
    }
}
