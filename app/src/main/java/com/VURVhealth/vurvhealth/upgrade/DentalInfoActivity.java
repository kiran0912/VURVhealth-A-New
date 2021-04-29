package com.VURVhealth.vurvhealth.upgrade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;

/**
 * Created by yqlabs on 7/2/17.
 */

public class DentalInfoActivity extends AppCompatActivity {

    private ImageView img_title;
    private TextView tv_title,info_heading,info_para;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presc_info_screen);

        img_title = (ImageView) findViewById(R.id.img_title);

        tv_title = (TextView) findViewById(R.id.tv_title);
        info_heading = (TextView) findViewById(R.id.info_heading);
//        info_para = (TextView) findViewById(R.id.info_para);

        img_title.setImageResource(R.drawable.dental_ic);

        tv_title.setText(getResources().getString(R.string.dental));

    }
}