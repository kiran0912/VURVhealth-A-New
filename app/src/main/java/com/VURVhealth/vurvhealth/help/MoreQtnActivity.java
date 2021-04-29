package com.VURVhealth.vurvhealth.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

/**
 * Created by yqlabs on 29/5/17.
 */

public class MoreQtnActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView tvheader,tvhelpQ1,helpDesc1,tvhelpQ2,tvhelpDesc2;
    private LinearLayout llhelpQ1,llhelpQ2,llhelpQ3,llhelpQ4;
    private View view,view1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_pay_screen);

        backBtn = (ImageView) findViewById(R.id.backBtn);

        tvheader = (TextView) findViewById(R.id.tvheader);
        tvhelpQ1 = (TextView) findViewById(R.id.tvhelpQ1);
        helpDesc1 = (TextView) findViewById(R.id.helpDesc1);
        tvhelpQ2 = (TextView) findViewById(R.id.tvhelpQ2);
        tvhelpDesc2 = (TextView) findViewById(R.id.tvhelpDesc2);

        llhelpQ1 = (LinearLayout) findViewById(R.id.llhelpQ1);
        llhelpQ2 = (LinearLayout) findViewById(R.id.llhelpQ2);
        llhelpQ3 = (LinearLayout) findViewById(R.id.llhelpQ3);
        llhelpQ4 = (LinearLayout) findViewById(R.id.llhelpQ4);
        //view = (View) findViewById(R.id.view);
        view1 = (View) findViewById(R.id.view1);

        tvheader.setText(getResources().getString(R.string.more_qtn));
        tvhelpQ1.setText(getResources().getString(R.string.more_qtnQ));
        helpDesc1.setText(getResources().getString(R.string.more_qtnA));

        tvhelpDesc2.setVisibility(View.GONE);
        llhelpQ3.setVisibility(View.GONE);
        llhelpQ4.setVisibility(View.GONE);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //view.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.INVISIBLE);
        llhelpQ2.setVisibility(View.GONE);

        llhelpQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpDesc1.getVisibility() == View.GONE)
                {
                    helpDesc1.setVisibility(View.VISIBLE);

                }else{
                    helpDesc1.setVisibility(View.GONE);
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
