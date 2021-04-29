package com.VURVhealth.vurvhealth.policies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.authentication.RegistrationActivity;
import com.VURVhealth.vurvhealth.authentication.getpagepojo.GetPageResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 30/11/16.
 */

public class PrivacyPolicyActivity extends SuperAppCompactActivity {

    private WebView tvPP;
    private ImageView img_cancelBtn;
    private GetPageResPayload pageResPayload;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacypolicy_screen);

        tvPP = (WebView) findViewById(R.id.tvPP);
        img_cancelBtn = (ImageView) findViewById(R.id.img_cancelBtn);

        img_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrivacyPolicyActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        tvPP.getSettings();
        tvPP.setBackgroundColor(Color.TRANSPARENT);


//        tvPP.setText(Html.fromHtml(pageResPayload.getPage().getContent()));
        getPrivacyPolicy();
    }

    private void getPrivacyPolicy() {

        showProgressDialog(PrivacyPolicyActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(PrivacyPolicyActivity.this).create(ApiInterface.class);

        Call<GetPageResPayload> call = apiService.getPrivacy("get_page");
        call.enqueue(new Callback<GetPageResPayload>() {
            @Override
            public void onResponse(Call<GetPageResPayload> call, Response<GetPageResPayload> response) {
                if (response.isSuccessful()) {
                    pageResPayload = response.body();
                    dismissProgressDialog();
//                    tvPP.setText(Html.fromHtml(pageResPayload.getPage().getContent()));
                    String summary = pageResPayload.getPage().getContent();
                    tvPP.loadData(summary, "text/html; charset=utf-8", "utf-8");
                }

                dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<GetPageResPayload> call, Throwable t) {
                // Log error here since request failed
                Log.e("Terms and Conditions", t.toString());
                dismissProgressDialog();
                Toast.makeText(PrivacyPolicyActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
