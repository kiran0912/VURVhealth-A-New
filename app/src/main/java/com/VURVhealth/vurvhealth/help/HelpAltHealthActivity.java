package com.VURVhealth.vurvhealth.help;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.MyMembersActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yqlabs on 26/5/17.
 */

public class HelpAltHealthActivity extends SuperAppCompactActivity {

    private ImageView backBtn;
    private Retrofit retrofit;
    private TextView tvheader;
    private TextView txtweb;
    private WebView webView;


    class C03891 implements View.OnClickListener {
        C03891() {
        }

        public void onClick(View v) {
            finish();
        }
    }

    class C03912 implements Callback<String> {


        class C03901 extends WebViewClient {
            C03901() {
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("https://www.vurvhealth.com/my-members/")) {
                    Intent intent = new Intent(HelpAltHealthActivity.this, MyMembersActivity.class);
                    intent.putExtra("activity", "GeneralQtnActivity");
                    startActivity(intent);
                }
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        }

        C03912() {
        }

        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                dismissProgressDialog();
                webView.loadDataWithBaseURL(null, (String) response.body(), "text/html", "utf-8", null);
                webView.setWebViewClient(new C03901());
            }
            dismissProgressDialog();
        }

        public void onFailure(Call<String> call, Throwable t) {
            dismissProgressDialog();
            Toast.makeText(HelpAltHealthActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurv_t_and_c);
        retrofitInitiliazation();
        webView = (WebView) findViewById(R.id.tvTC);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvheader = (TextView) findViewById(R.id.tvheader);
        tvheader.setText(getString(R.string.vurvhealth_alternative));
        webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.setBackgroundColor(0);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(2, null);
        } else {
            webView.setLayerType(1, null);
        }
        backBtn.setOnClickListener(new C03891());

        getTermsAndCondition();
    }

    private void retrofitInitiliazation() {
        retrofit = new Retrofit.Builder().baseUrl(Application_holder.AUTH_BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()).build();
    }

    private void getTermsAndCondition() {
        showProgressDialog(this);
        try {
            ((ApiInterface) retrofit.create(ApiInterface.class)).getPageContent(Application_holder.BASE_URL == "https://www.vurvhealth.com/" ? requestVURVTerms() : requestVURVTerms1()).enqueue(new C03912());
        } catch (Exception e) {
            Log.v("Upgrad", e.getMessage());
        }
    }

    private String requestVURVTerms() {
        return "[{\n\"pageid\": \"1668\"\n}]";
    }
    private String requestVURVTerms1() {
        return "[{\n\"pageid\": \"1595\"\n}]";
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
