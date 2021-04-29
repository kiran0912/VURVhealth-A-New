package com.VURVhealth.vurvhealth.myProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import retrofit2.Retrofit;

public class TermsActivity extends SuperAppCompactActivity {
    private ImageView backBtn;
    private Retrofit retrofit;
    private WebView tvTC;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_screen);
        tvTC = (WebView) findViewById(R.id.tvTC);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvTC.getSettings();
        tvTC.setBackgroundColor(0);
        tvTC.getSettings().setJavaScriptEnabled(true);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showProgressDialog(TermsActivity.this);
        tvTC.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(TermsActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }


            @Override
            public void onPageFinished(WebView view, String url) {
                dismissProgressDialog();

                String webUrl = tvTC.getUrl();

            }


        });
        tvTC.loadUrl("https://www.vurvhealth.com/home/legal/");
    }
}

