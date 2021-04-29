package com.VURVhealth.vurvhealth.freshdesk_help;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.freshdesk_help.pojos.FreshDeskSubListRes;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.VURVhealth.vurvhealth.retrofit.Application_holder.FRESHDESkBASE_URL;

/**
 * Created by yqlabs on 18/3/17.
 */

public class FreshdeskDetailsActivity extends SuperAppCompactActivity {
    private static int REQUEST_CODE = 1000;
    private ImageView backBtn;
    private TextView tbName,details_txt;
    public static String str_description,str_title;





    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshdesk_help_details);
        tbName = (TextView) findViewById(R.id.tbName);
        details_txt = (TextView) findViewById(R.id.details_txt);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();

        if (bundle != null) {
            str_description = bundle.getString("str_description");
            str_title = bundle.getString("str_title");
        }

//        tbName.setText(str_title);

        if (str_description.equals("<p>.</p>")){
            details_txt.setText("No articles details found.");
        }else {
            details_txt.setText(fromHtml(str_description));
        }



    }


    public static Spanned fromHtml(String html) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }


    protected void onResume() {
        super.onResume();
//        mAdapter.notifyDataSetChanged();
    }


    public void onBackPressed() {
        super.onBackPressed();
      //  finish();
    }



}

