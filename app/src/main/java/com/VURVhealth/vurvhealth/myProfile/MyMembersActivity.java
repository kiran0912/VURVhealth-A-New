package com.VURVhealth.vurvhealth.myProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.VURVhealth.vurvhealth.myProfile.pojos.PrimaryMemberTypeResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UserDetailDataResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.AddSecondaryUserActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeAltHealthFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeDentalFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeMedicalFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeRxFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeVisionFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.pojos.DeleteMemberRequest;
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.drive.DriveFile;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMembersActivity extends SuperAppCompactActivity {
    public static ArrayList<MyMembersResponse> myMembersResponses;
    private String activity = "";
    private ImageView backBtn;
    private Button btn_addMember;
    private ImageView img_prf;
    private LinearLayout ll_item;
    private Adapter<MyMembersAdapter.DataObjectHolder> mAdapter;
    private LayoutManager mLayoutManager;
    String memberType;
    MyMembersAdapter myMembersAdapter;
    private TextView no_mems;
    private String packageId;
    private String parent_id;
    private SharedPreferences prefsData;
    private Uri profileImageUril;
    private SqLiteDbHelper recentDbHelper;
    private RecyclerView rvMemberList;
    private SharedPreferences sharedPreferences;
    private TextView tb_title;
    private TextView tvPopUp;
    private TextView tvPrimaryMember;
    private TextView tvUserName;
    private TextView tvVURVID;
    private int userId;
    private ArrayList<ArrayList<UserDetailDataResPayload>> userInfoResPayload;
    private String userName;
    private UserSharedPreferences userSharedPreferences;
    private String vurvId;
    
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_members_profile);
        recentDbHelper = new SqLiteDbHelper(this);
        userSharedPreferences = UserSharedPreferences.getInstance(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        img_prf = (ImageView) findViewById(R.id.img_prf);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvVURVID = (TextView) findViewById(R.id.tvVURVID);
        no_mems = (TextView) findViewById(R.id.no_mems);
        tvPopUp = (TextView) findViewById(R.id.tvPopUp);
        tvPrimaryMember = (TextView) findViewById(R.id.tvPrimaryMember);
        btn_addMember = (Button) findViewById(R.id.btn_addMember);
        ll_item = (LinearLayout) findViewById(R.id.ll_item);
        tb_title = (TextView) findViewById(R.id.tb_title);
        getIntentData();
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        parent_id = prefsData.getString("parent_id", "");
        if (activity != null && activity.equalsIgnoreCase("VurvPackageActivity")) {
            tb_title.setText(getResources().getString(R.string.select_member));
            tvPrimaryMember.setText(R.string.primary_member);
            btn_addMember.setVisibility(View.GONE);
            tvPopUp.setVisibility(View.GONE);
        } else if (activity == null || !activity.equalsIgnoreCase("GeneralQtnActivity")) {
            tvPrimaryMember.setText(R.string.primary_member);
            btn_addMember.setVisibility(View.VISIBLE);
            tvPopUp.setVisibility(View.GONE);
        } else if (parent_id.equalsIgnoreCase("0")) {
            tvPrimaryMember.setText(R.string.primary_member);
            tvPopUp.setVisibility(View.GONE);
        } else {
            tvPrimaryMember.setText(R.string.secondary_member);
            btn_addMember.setVisibility(View.GONE);
            tvPopUp.setVisibility(View.GONE);
        }
        btn_addMember.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMembersActivity.this, AddSecondaryUserActivity.class));
                finish();
            }
        });
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        userId = prefsData.getInt("userId", 1);
        packageId = prefsData.getString("packageId", "");
        sharedPreferences = getSharedPreferences("VURVProfileDetails", 0);
        profileImageUril = Uri.parse(sharedPreferences.getString("ProfileImageURl", "http://"));
        ll_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equalsIgnoreCase("VurvPackageActivity")) {
                    String packageName = userSharedPreferences.getData("packageName");
                    Intent intent;
                    if (packageName.equalsIgnoreCase("Rx")) {
                        intent = new Intent(MyMembersActivity.this, UpgradeRxFlipActivity.class);
                        intent.putExtra("activity", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return;
                    } else if (packageName.equalsIgnoreCase("medical")) {
                        intent = new Intent(MyMembersActivity.this, UpgradeMedicalFlipActivity.class);
                        intent.putExtra("activity", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return;
                    } else if (packageName.equalsIgnoreCase("dental")) {
                        intent = new Intent(MyMembersActivity.this, UpgradeDentalFlipActivity.class);
                        intent.putExtra("activity", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return;
                    } else if (packageName.equalsIgnoreCase("vision")) {
                        intent = new Intent(MyMembersActivity.this, UpgradeVisionFlipActivity.class);
                        intent.putExtra("activity", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return;
                    } else if (packageName.equalsIgnoreCase("AltHealth")) {
                        intent = new Intent(MyMembersActivity.this, UpgradeAltHealthFlipActivity.class);
                        intent.putExtra("activity", "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return;
                    } else {
                        return;
                    }
                }
                startActivity(new Intent(MyMembersActivity.this, EditProfileActivity.class));
            }
        });
        userName = prefsData.getString("fullName", "");
        vurvId = prefsData.getString("vurvId", "");
        tvUserName.setText(userName);
        tvVURVID.setText(": " +vurvId);
        rvMemberList = (RecyclerView) findViewById(R.id.rvMemberList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMemberList.setLayoutManager(mLayoutManager);
        rvMemberList.setItemAnimator(new DefaultItemAnimator());
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIntentData() {
        try {
            activity = getIntent().getStringExtra("activity");
        } catch (NullPointerException e) {
            activity = "";
            System.out.print("Exception>>>" + e.getMessage());
        }
    }

    protected void onResume() {
        super.onResume();
        if (checkInternet()) {
            myMembersService();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        profileImageUril = Uri.parse(sharedPreferences.getString("ProfileImageURl", "http://").replace("http://", "https://"));
        Picasso.with(MyMembersActivity.this).load(profileImageUril).error(R.drawable.profile_noimage_ic).placeholder(R.drawable.profile_noimage_ic).networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).resize(200, 200).centerCrop().into(img_prf);
    }

    private void myMembersTypeService() {
        showProgressDialog(MyMembersActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MyMembersActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(userId));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getMemberTypePrimary(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<PrimaryMemberTypeResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<PrimaryMemberTypeResPayload>> call, Response<ArrayList<PrimaryMemberTypeResPayload>> response) {
                if (response.isSuccessful()) {
                    tvPrimaryMember.setText(((PrimaryMemberTypeResPayload) ((ArrayList) response.body()).get(0)).getMemberType() + " Member");
                    myMembersService();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<PrimaryMemberTypeResPayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void myMembersService() {
        showProgressDialog(MyMembersActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MyMembersActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 0)));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getMyMemberList(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<MyMembersResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<MyMembersResponse>> call, Response<ArrayList<MyMembersResponse>> response) {
                if (response.isSuccessful()) {
                    myMembersResponses = (ArrayList) response.body();
                    myUserDetailsService();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<MyMembersResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void myUserDetailsService() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MyMembersActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(userId));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getUserDetailData(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<ArrayList<UserDetailDataResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Response<ArrayList<ArrayList<UserDetailDataResPayload>>> response) {
                if (response.isSuccessful()) {
                    String str;
                    dismissProgressDialog();
                    userInfoResPayload = (ArrayList) response.body();
                    loginEditor.putString("user_login", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserLogin());
                    loginEditor.putString("emailId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserEmail() == null ? "" : ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserEmail());
                    loginEditor.putString("firstName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getFirstName());
                    loginEditor.putString("lastName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getLastName());
                    loginEditor.putString("fullName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getLastName());
                    Editor editor = loginEditor;
                    String str2 = "email";
                    if (((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserEmail() == null) {
                        str = "";
                    } else {
                        str = ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserEmail();
                    }
                    editor.putString(str2, str);
                    loginEditor.putString("vurvId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getMemberId());
                    loginEditor.putInt("userId", Integer.parseInt(((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserId()));
                    loginEditor.putString("dob", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getDateOfBirth());
                    loginEditor.putString("gender", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getGender());
                    if (((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getMobileNo() != null) {
                        loginEditor.putString("mobileNo", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getMobileNo());
                    }
                    loginEditor.putString("address1", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getAddress1());
                    loginEditor.putString("address2", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getAddress2());
                    loginEditor.putString("city", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getCity());
                    loginEditor.putString("stateName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getState());
                    loginEditor.putString("zipCode", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getZipcode());
                    loginEditor.putString("zip4Code", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getZipFourCode());
                    loginEditor.putString("country", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getCountry());
                    loginEditor.putString("packageId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPackageId());
                    loginEditor.putString("packageName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getName());
                    loginEditor.putString("subPackageId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubPackageId());
                    loginEditor.putString("post_title", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPostTitle());
                    loginEditor.putString(Param.PRICE, ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPrice());
                    loginEditor.putString("childCount", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getChild_count());
                    loginEditor.putString("subscription_end_date", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubscriptionEndDate());
                    loginEditor.putString("parent_id", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getParentId());
                    loginEditor.putString("orderId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getOrderId());
                    loginEditor.putString("search_type", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSearch_type());
                    loginEditor.putString("logout", "");
                    loginEditor.commit();
                    myMembersAdapter = new MyMembersAdapter(MyMembersActivity.this, myMembersResponses, activity);
                    rvMemberList.setAdapter(myMembersAdapter);
                    if (prefsLoginData.getString("post_title", "").contains("Single") && myMembersResponses.size() >= 1) {
                        btn_addMember.setVisibility(View.GONE);
                        tvPopUp.setVisibility(View.GONE);
                    } else if (prefsLoginData.getString("post_title", "").contains("Spouse") && myMembersResponses.size() < 1) {
                        btn_addMember.setVisibility(View.VISIBLE);
                        tvPopUp.setVisibility(View.GONE);
                    } else if (prefsLoginData.getString("post_title", "").contains("Family") && myMembersResponses.size() < 3) {
                        btn_addMember.setVisibility(View.VISIBLE);
                        tvPopUp.setVisibility(View.GONE);
                    } else if (prefsLoginData.getString("post_title", "").contains("Family") && myMembersResponses.size() == 3) {
                        btn_addMember.setVisibility(View.GONE);
                        tvPopUp.setVisibility(View.VISIBLE);
                    } else {
                        btn_addMember.setVisibility(View.GONE);
                        tvPopUp.setVisibility(View.GONE);
                    }
                    if (activity.equalsIgnoreCase("VurvPackageActivity")) {
                        btn_addMember.setVisibility(View.GONE);
                        tvPopUp.setVisibility(View.GONE);
                        return;
                    } else if (activity != null && activity.equalsIgnoreCase("GeneralQtnActivity")) {
                        if (parent_id.equalsIgnoreCase("0")) {
                            tvPrimaryMember.setText(R.string.primary_member);
                            tvPopUp.setVisibility(View.GONE);
                            return;
                        }
                        tvPrimaryMember.setText(R.string.secondary_member);
                        btn_addMember.setVisibility(View.GONE);
                        tvPopUp.setVisibility(View.GONE);
                        return;
                    } else {
                        return;
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void removeMember(String userId, String vurvId) {
        deleteMemberService(userId, vurvId);
    }

    private void deleteMemberService(String userId, String vurvId) {
        showProgressDialog(MyMembersActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(MyMembersActivity.this).create(ApiInterface.class);
        ArrayList<DeleteMemberRequest> myMemberListPayloadArrayList = new ArrayList();
        DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest();
        deleteMemberRequest.setUserId(userId);
        deleteMemberRequest.setVurvId(vurvId);
        myMemberListPayloadArrayList.add(deleteMemberRequest);
        apiService.getDeleteMember(myMemberListPayloadArrayList).enqueue(new Callback<MobileStatusResPayload>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<MobileStatusResPayload> call, Response<MobileStatusResPayload> response) {
                if (response.isSuccessful()) {
                    loginEditor.putString("childCount", String.valueOf(Integer.parseInt(prefsLoginData.getString("childCount", "1")) - 1));
                    loginEditor.commit();
                    Intent intent = new Intent(MyMembersActivity.this, MyMembersActivity.class);
                    intent.putExtra("activity", "MyMembersActivity");
                    intent.setFlags(DriveFile.MODE_WRITE_ONLY);
                    startActivity(intent);
                    dismissProgressDialog();
                    return;
                }
                Toast.makeText(getApplicationContext(), ((MobileStatusResPayload) response.body()).getMessage(), 0).show();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<MobileStatusResPayload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void moveToNextActivity(MyMembersResponse myMembersResponse) {
        if (activity.equalsIgnoreCase("VurvPackageActivity")) {
            String packageName = userSharedPreferences.getData("packageName");
            userSharedPreferences.SaveData("secondaryUserName", myMembersResponse.getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + myMembersResponse.getLastName());
            userSharedPreferences.SaveData("secondaryUserVurvId", myMembersResponse.getMemberId());
            userSharedPreferences.SaveData("expiresDate", myMembersResponse.getSubscriptionEndDate());
            Intent intent;
            if (packageName.equalsIgnoreCase("Rx")) {
                intent = new Intent(MyMembersActivity.this, UpgradeRxFlipActivity.class);
                intent.putExtra("activity", "MyMembersActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return;
            } else if (packageName.equalsIgnoreCase("medical")) {
                intent = new Intent(MyMembersActivity.this, UpgradeMedicalFlipActivity.class);
                intent.putExtra("activity", "MyMembersActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return;
            } else if (packageName.equalsIgnoreCase("dental")) {
                intent = new Intent(MyMembersActivity.this, UpgradeDentalFlipActivity.class);
                intent.putExtra("activity", "MyMembersActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return;
            } else if (packageName.equalsIgnoreCase("vision")) {
                intent = new Intent(MyMembersActivity.this, UpgradeVisionFlipActivity.class);
                intent.putExtra("activity", "MyMembersActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return;
            } else if (packageName.equalsIgnoreCase("AltHealth")) {
                intent = new Intent(MyMembersActivity.this, UpgradeAltHealthFlipActivity.class);
                intent.putExtra("activity", "MyMembersActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return;
            } else {
                return;
            }
        }
        EditSecondaryMemberActivity.startActivityWithConstractor(MyMembersActivity.this, myMembersResponse);
    }
}
