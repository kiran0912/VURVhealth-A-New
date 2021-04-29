package com.VURVhealth.vurvhealth.myProfile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.CancelSubscriptionReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.CancelSubscriptionResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopSubscriptionActivity extends SuperAppCompactActivity {
    private ImageView backBtn;
    private Button btn_cancel;
    private Button btn_pause;
    private ImageView img_card;
    private String orderId;
    private String post_title;
    private TextView tvMemberShip;
    private TextView tvPrice;
    private int userId;
    

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_subscription_screen);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        img_card = (ImageView) findViewById(R.id.img_card);
        tvMemberShip = (TextView) findViewById(R.id.tvMemberShip);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        userId = prefsLoginData.getInt("userId", 0);
        orderId = prefsLoginData.getString("orderId", "7777");
        post_title = prefsLoginData.getString("post_title", "");
        if (post_title.contains("Pulse")) {
            img_card.setImageResource(R.drawable.package_card_pulse);
        } else if (post_title.contains("Care")) {
            img_card.setImageResource(R.drawable.package_card_care);
        } else if (post_title.contains("360")) {
            img_card.setImageResource(R.drawable.package_card_360);
        } else {
            img_card.setImageResource(R.drawable.package_card_rx);
        }
        if (post_title.contains("Monthly")) {
            tvMemberShip.setText("Monthly Membership");
        } else if (post_title.contains("Quarterly")) {
            tvMemberShip.setText("Quarterly Membership");
        } else if (post_title.contains("Half Yearly")) {
            tvMemberShip.setText("Half Yearly Membership");
        } else if (post_title.contains("Yearly")) {
            tvMemberShip.setText("Yearly Membership");
        }
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelCustomAlertDialog();
            }
        });
    }

    private void customAlertDialog() {
        final Dialog customDialog = new Dialog(this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.custom_alert);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
        TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        yesBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    private void cancelCustomAlertDialog() {
        final Dialog customDialog = new Dialog(this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.custom_alert);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);
        ((TextView) customDialog.findViewById(R.id.tv_title)).setText("Are you sure if you want to cancel the subscription?");
        info_heading.setVisibility(View.GONE);
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        yesBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (checkInternet()) {
                    cancelSubscription();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PrimaryAcntHolderActivity.class));
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    private void cancelSubscription() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<CancelSubscriptionReqPayload> cancelSubscriptionReqPayloads = new ArrayList();
        CancelSubscriptionReqPayload cancelSubscriptionReqPayload = new CancelSubscriptionReqPayload();
        cancelSubscriptionReqPayload.setUserId(String.valueOf(userId));
        cancelSubscriptionReqPayload.setOrderId(orderId);
        cancelSubscriptionReqPayloads.add(cancelSubscriptionReqPayload);
        apiService.cancelSubscription(cancelSubscriptionReqPayloads).enqueue(new Callback<CancelSubscriptionResPayload>() {
            @Override
            public void onResponse(Call<CancelSubscriptionResPayload> call, Response<CancelSubscriptionResPayload> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), ((CancelSubscriptionResPayload) response.body()).getStatus(), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                    return;
                }
                Toast.makeText(getApplicationContext(), "In Progress", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<CancelSubscriptionResPayload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
