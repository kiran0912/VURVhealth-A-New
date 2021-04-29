package com.VURVhealth.vurvhealth.myProfile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FAQActivity extends SuperAppCompactActivity {
    private ImageView backBtn;
    private Retrofit retrofit;
    private WebView tvTC;
    private TextView tvheader;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurv_t_and_c);
        retrofitInitiliazation();
        tvTC = (WebView) findViewById(R.id.tvTC);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvheader = (TextView) findViewById(R.id.tvheader);
        tvheader.setText("FAQ");
        tvTC.getSettings();
        tvTC.setBackgroundColor(0);
        tvTC.getSettings().setJavaScriptEnabled(true);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getTermsAndCondition();
    }

    private void retrofitInitiliazation() {
        retrofit = new Builder().baseUrl(Application_holder.AUTH_BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()).build();
    }

    private void getTermsAndCondition() {
        showProgressDialog(this);
        try {
            ((ApiInterface) retrofit.create(ApiInterface.class)).getPageContent(requestVURVTerms()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        dismissProgressDialog();
                        String vurvData = ((String) response.body()).trim().replace("\\n", "").replace("\\", "");
                        tvTC.loadData(vurvData.substring(1, vurvData.length() - 1), "text/html", "utf-8");
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(FAQActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.v("Upgrad", e.getMessage());
        }
    }

    private String requestVURVTerms() {
        return "[{\n\"pageid\": \"1030\"\n}]";
    }
}
