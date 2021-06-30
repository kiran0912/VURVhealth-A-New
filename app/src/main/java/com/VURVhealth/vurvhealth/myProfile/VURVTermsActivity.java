package com.VURVhealth.vurvhealth.myProfile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class VURVTermsActivity extends SuperAppCompactActivity {
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
        tvTC.getSettings();
        tvTC.setBackgroundColor(0);
        tvTC.getSettings().setJavaScriptEnabled(true);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvheader.setText(getResources().getString(R.string.term_condition));
        getTermsAndCondition();

    }


    private void retrofitInitiliazation() {
        retrofit = new Builder()
                .baseUrl(Application_holder.AUTH_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build())
                .build();
    }

    private void getTermsAndCondition() {
        showProgressDialog(this);
        try {

            ((ApiInterface) retrofit.create(ApiInterface.class)).getPageContent(Application_holder.AUTH_BASE_URL == "https://www.vurvhealth.com/v2/api/api/" ? requestVURVTerms() : requestVURVTerms1() ).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        dismissProgressDialog();
                        tvTC.loadDataWithBaseURL(null, (String) response.body(), "text/html", "utf-8", null);
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(VURVTermsActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.v("Upgrad", e.getMessage());
        }
    }

    private String requestVURVTerms() {
        return "[{\n\"pageid\": \"1690\"\n}]";
    }
    private String requestVURVTerms1() {
        return "[{\n\"pageid\": \"1593\"\n}]";
    }
}
