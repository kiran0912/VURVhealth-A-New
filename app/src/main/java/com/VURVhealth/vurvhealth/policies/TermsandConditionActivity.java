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

public class TermsandConditionActivity extends SuperAppCompactActivity {

    private WebView tvTC;
    private ImageView img_cancelBtn;
    GetPageResPayload pageResPayload;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsandcondition_screen);

        tvTC = (WebView) findViewById(R.id.tvTC);
        img_cancelBtn = (ImageView) findViewById(R.id.img_cancelBtn);

        tvTC.getSettings();
        tvTC.setBackgroundColor(Color.TRANSPARENT);


        img_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TermsandConditionActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        getTermsAndCondition();
    }

    private void getTermsAndCondition() {

        showProgressDialog(TermsandConditionActivity.this);

        ApiInterface apiService =
                ApiClient.getClient(TermsandConditionActivity.this).create(ApiInterface.class);

        Call<GetPageResPayload> call = apiService.getTermsandConditions("get_page");
        call.enqueue(new Callback<GetPageResPayload>() {
            @Override
            public void onResponse(Call<GetPageResPayload> call, Response<GetPageResPayload> response) {
                if (response.isSuccessful()) {
                    pageResPayload = response.body();
                    dismissProgressDialog();

                    String summary = pageResPayload.getPage().getContent();
                    tvTC.loadData(summary, "text/html; charset=utf-8", "utf-8");
//                    tvTC.setText(Html.fromHtml(pageResPayload.getPage().getContent()));
                }
                dismissProgressDialog();

                Log.d("Terms and Conditions", "Number of movies received: ");

            }

            @Override
            public void onFailure(Call<GetPageResPayload> call, Throwable t) {
                // Log error here since request failed
                Log.e("Terms and Conditions", t.toString());
                dismissProgressDialog();
                Toast.makeText(TermsandConditionActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

            }
        });

    }



}
