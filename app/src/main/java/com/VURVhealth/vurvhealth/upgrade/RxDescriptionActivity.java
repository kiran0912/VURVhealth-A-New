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
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

/**
 * Created by yqlabs on 6/2/17.
 */

public class RxDescriptionActivity extends AppCompatActivity {

    private TextView toolbar_txt,desc_heading,desc_para,tvRateBanner;
    private ImageView backBtn,infoRx;

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

        toolbar_txt.setText(getResources().getString(R.string.rx));
        desc_heading.setText(getResources().getString(R.string.rx_heading));
        desc_para.setText(Html.fromHtml(getResources().getString(R.string.rx_desc)));
        tvRateBanner.setText("FREE");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(RxDescriptionActivity.this,UpgradeSubscriptionActivity.class));
                finish();
            }
        });

        infoRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPrescCustomAlertDialog();
            }
        });
    }

    //show popup for info prescription
    private void infoPrescCustomAlertDialog() {

        final Dialog customDialog = new Dialog(RxDescriptionActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
