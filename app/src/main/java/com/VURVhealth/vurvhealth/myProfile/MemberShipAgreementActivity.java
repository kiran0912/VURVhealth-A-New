package com.VURVhealth.vurvhealth.myProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MemberShipAgreementActivity extends SuperAppCompactActivity {
    private ImageView backBtn;
    private String memberText1;
    private Retrofit retrofit;
    private WebView tvTC;
    private TextView tvheader;
   
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurv_t_and_c);
        retrofitInitiliazation();
        tvTC = (WebView) findViewById(R.id.tvTC);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvheader = (TextView) findViewById(R.id.tvheader);
        tvheader.setText(getResources().getString(R.string.membership_agreement));
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
                        memberText1 = (String) response.body();
                        getTermsAndCondition1();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.v("Upgrad", e.getMessage());
        }
    }

    private void getTermsAndCondition1() {
        try {
            ((ApiInterface) retrofit.create(ApiInterface.class)).getPageContent(requestVURVTerms1()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
                        String fullName = prefsLoginData.getString("fullName", "");
                        String vurvId = prefsLoginData.getString("vurvId", "");
                        String add1 = prefsLoginData.getString("address1", "");
                        String add2 = prefsLoginData.getString("address2", "");
                        String city = prefsLoginData.getString("city", "");
                        String stateName = prefsLoginData.getString("stateName", "");
                        String post_title = prefsLoginData.getString("post_title", "");
                        String address = add1 + ", " + city + ", " + stateName;
                        if (post_title.equalsIgnoreCase("Pulse Family Monthly")) {
                            post_title = "Monthly";
                        }
                        String dobFormat = null;
                        try {
                            dobFormat = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(prefsLoginData.getString("subscription_end_date", "12/12/2017")));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String memberText = (String) response.body();
                        tvTC.loadData(memberText1 + "<br />" + (getString(R.string.memberid) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + vurvId + "<br />" + getString(R.string.memName) + "<br />" + fullName + "<br />" + address + "<br />" + getString(R.string.effect_date) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat + "<br />" + getString(R.string.term) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + post_title + "<br />" + getString(R.string.total_fee) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.total_fee_text) + "<br />") + "<br />" + memberText, "text/html", "utf-8");
                        dismissProgressDialog();
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(MemberShipAgreementActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.v("Upgrad", e.getMessage());
        }
    }

    private String requestVURVTerms() {
        return "[{\n\"pageid\": \"1159\"\n}]";
    }

    private String requestVURVTerms1() {
        return "[{\n\"pageid\": \"1157\"\n}]";
    }
}
