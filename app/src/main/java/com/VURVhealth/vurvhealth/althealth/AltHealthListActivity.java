package com.VURVhealth.vurvhealth.althealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

/**
 * Created by yqlabs on 22/5/17.
 */

public class AltHealthListActivity extends SuperAppCompactActivity {
    private static final int FILTER_REQUEST = 100;

    private TextView tbName,tvTabletName,tvPlace,no_data;
    private ImageView filter_btn,mapBtn,backBtn;
    private RecyclerView rv_place;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter<AltHealthListAdapter.DataObjectHolder> mAdapter;
    private String place,speciality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_price);

        tbName = (TextView) findViewById(R.id.tbName);
        tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        no_data = (TextView) findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        mapBtn = (ImageView) findViewById(R.id.mapBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        rv_place = (RecyclerView) findViewById(R.id.rv_place);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_place.setLayoutManager(mLayoutManager);
        rv_place.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences sharedPreferences = getSharedPreferences("altHealthType", Context.MODE_PRIVATE);
        place = sharedPreferences.getString("zipCode","");
        speciality = sharedPreferences.getString("altSpecialty","");


        mAdapter = new AltHealthListAdapter(AltHealthListActivity.this, AltHealthScreenActivity.resPayloads);
        rv_place.setAdapter(mAdapter);

        tbName.setText(AltHealthScreenActivity.resPayloads.size()+" Results");
        if (speciality.length()>0){
            tvTabletName.setVisibility(View.VISIBLE);
            tvTabletName.setText(speciality);
        }else {
            tvTabletName.setVisibility(View.GONE);
        }
//        tvPlace.setText(AltHealthScreenActivity.resPayloads.get(0).());
        tvPlace.setText(AltHealthScreenActivity.resPayloads.get(0).getCity()+", "+AltHealthScreenActivity.resPayloads.get(0).getState()+"\n"+place);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(AltHealthListActivity.this,AHSFilterActivity.class);
                startActivityForResult(intent,FILTER_REQUEST);*/
                Toast.makeText(AltHealthListActivity.this,"Filter option not available",Toast.LENGTH_SHORT).show();

            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AltHealthListActivity.this,AltHealthMapActivity.class));
                finish();
//                Toast.makeText(AltHealthListActivity.this,"Loaction not found",Toast.LENGTH_SHORT).show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
