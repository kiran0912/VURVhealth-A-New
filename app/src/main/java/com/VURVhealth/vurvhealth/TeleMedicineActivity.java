package com.VURVhealth.vurvhealth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.help.WebViewActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;

public class TeleMedicineActivity extends SuperAppCompactActivity {
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llVurv;
    private LinearLayout llSearch;
    private WebView webView;
    private Context context = TeleMedicineActivity.this;
    private String url = "https://member.dialcare.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_medicine);

        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, StartScreenActivity.class));
                finish();
            }
        });

        llVurv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, VurvPackageActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, FreshdeskMainListActivity.class));
//                startActivity(new Intent(context, HelpActivity.class));
                finish();
            }
        });
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgressDialog(context);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dismissProgressDialog();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e("Ssl Error:",handler.toString() + "error:" +  error);
           handler.proceed();
        }
    }
}