package com.VURVhealth.vurvhealth.upgrade;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.pojos.DeleteMemberRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 21/2/17.
 */

public class AddMemberDataActivity extends SuperAppCompactActivity {

    private Button btn_addMember, btn_notnow;
    private RecyclerView rvMember;
    private TextView tvSecondaryUserName, tvDob, tvMail, tvGender, tvSendMail;
    private CheckBox checkEmail;
    private ImageView img_delete, backBtn;
    private RecyclerView.LayoutManager mLayoutManager;
    private String name = "", dob = "", email = "";
    ArrayList<AddMemberDataObject> mgridData;
    private SharedPreferences sP, prefs;
    SqLiteDbHelper recentDbHelper;
    AddMemberAdapter myMembersAdapter;
    ArrayList<MyMembersResponse> myMembersResponses;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);
        recentDbHelper = new SqLiteDbHelper(this);

        btn_addMember = (Button) findViewById(R.id.btn_addMember);
        btn_notnow = (Button) findViewById(R.id.btn_notnow);

        tvSecondaryUserName = (TextView) findViewById(R.id.tvSecondaryUserName);
        tvDob = (TextView) findViewById(R.id.tvDob);
        tvMail = (TextView) findViewById(R.id.tvMail);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvSendMail = (TextView) findViewById(R.id.tvSendMail);
        rvMember = (RecyclerView) findViewById(R.id.rvMember);

        rvMember = (RecyclerView) findViewById(R.id.rvMember);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMember.setLayoutManager(mLayoutManager);
        rvMember.setItemAnimator(new DefaultItemAnimator());

        img_delete = (ImageView) findViewById(R.id.img_delete);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        checkEmail = (CheckBox) findViewById(R.id.checkEmail);
        rvMember.setVisibility(View.VISIBLE);

        btn_addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefsLoginData.getString("post_title","").contains("Spouse") && myMembersResponses.size()<1) {
                    startActivity(new Intent(AddMemberDataActivity.this, AddSecondaryUserActivity.class));
                }else if (prefsLoginData.getString("post_title","").contains("Family") && myMembersResponses.size()<3){
                    startActivity(new Intent(AddMemberDataActivity.this, AddSecondaryUserActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), "this plan have only 3 member", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddMemberDataActivity.this, PrimaryAcntHolderActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });

        btn_notnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMemberDataActivity.this, UpgradeSubscriptionActivity.class));
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMemberDataActivity.this, AddSecondaryUserActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myMembersService();
    }

    public void removeMember(String userId, String vurvId) {
//        recentDbHelper.deleteMember(id);
        deleteMemberService(userId, vurvId);
        myMembersAdapter.notifyDataSetChanged();
    }

    private void deleteMemberService(String userId, String vurvId) {
        showProgressDialog(AddMemberDataActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(AddMemberDataActivity.this).create(ApiInterface.class);
        ArrayList<DeleteMemberRequest> myMemberListPayloadArrayList = new ArrayList<>();
        DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest();
        deleteMemberRequest.setUserId(userId);
        deleteMemberRequest.setVurvId(vurvId);

        myMemberListPayloadArrayList.add(deleteMemberRequest);

        Call<MobileStatusResPayload> call = apiService.getDeleteMember(myMemberListPayloadArrayList);
        call.enqueue(new Callback<MobileStatusResPayload>() {
            @Override
            public void onResponse(Call<MobileStatusResPayload> call, Response<MobileStatusResPayload> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<MobileStatusResPayload> call, Throwable t) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();

//                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void customAlertDialog() {

        final Dialog customDialog = new Dialog(AddMemberDataActivity.this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_alert);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
        TextView line = (TextView) customDialog.findViewById(R.id.line);
        TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);

        line.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("VURVhealth");
        tv_title.setTypeface(null, Typeface.BOLD);
        info_heading.setText("Please select another member from your plan.");
//        info_heading.setText("Please select members from previous screen in order to add more household members.");

        yesBtn.setText("Ok");

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMemberDataActivity.this, PulsePaymentOptionsActivity.class));
                finish();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();

            }
        });


    }

    private void myMembersService() {
        showProgressDialog(AddMemberDataActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(AddMemberDataActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList<MyMemberListPayload>();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 0)));

        myMemberListPayloadArrayList.add(myMemberListPayload);

        Call<ArrayList<MyMembersResponse>> call = apiService.getMyMemberList(myMemberListPayloadArrayList);
        call.enqueue(new Callback<ArrayList<MyMembersResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<MyMembersResponse>> call, Response<ArrayList<MyMembersResponse>> response) {

                if (response.isSuccessful()) {
                   myMembersResponses = response.body();
                    myMembersAdapter = new AddMemberAdapter(AddMemberDataActivity.this, myMembersResponses);
                    rvMember.setAdapter(myMembersAdapter);
                    if (prefsLoginData.getString("post_title","").contains("Family") && myMembersResponses.size() >= 1)
                        btn_addMember.setText("Add Another Member");
                    dismissProgressDialog();

                } else {
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<MyMembersResponse>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();

//                Log.e(TAG, t.toString());
            }
        });
    }
}
