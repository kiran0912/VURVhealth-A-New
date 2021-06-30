package com.VURVhealth.vurvhealth.myProfile;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.authentication.LoginActivity;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPhotoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UserDetailDataResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeSubscriptionActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrimaryAcntHolderActivity extends SuperAppCompactActivity {
    private Button BtnSignOut;
    private String activity = "";
    private Editor editor;
    private String email;
    private String fullName;
    private ImageView img_prf;
    Editor langEditor;
    SharedPreferences langPreferences;
    private LinearLayout llChangPackg;
    private LinearLayout llChanhPsw;
    private LinearLayout llEditBilling;
    private LinearLayout llEditProfile;
    private LinearLayout llHelp;
    private LinearLayout llLanguage;
    private LinearLayout llMemberAgree;
    private LinearLayout llMyMembers;
    private LinearLayout llPrivacy;
    private LinearLayout llSaved;
    private LinearLayout llSearch;
    private LinearLayout llStopSub;
    private LinearLayout llTermsofService;
    private LinearLayout llVurv;
    private String packageId;
    private String packageName;
    private String parent_id;
    private String post_titile;
    private SharedPreferences prefsData;
    private Uri profileImageUril;
    private String selectedLanguage;
    private SharedPreferences sharedPreferences;
    private TextView tvName;
    private TextView tvPkgName;
    private TextView tvUserEmail;
    private TextView tvVURVID;
    private int userId;
    private ArrayList<ArrayList<UserDetailDataResPayload>> userInfoResPayload;
    private View view;
    private String vurvId;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_acnt_holder_profile);
        langPreferences = getSharedPreferences("languagePref", 0);
        langEditor = langPreferences.edit();
        tvName = (TextView) findViewById(R.id.tvName);
        tvVURVID = (TextView) findViewById(R.id.tvVURVID);
        tvPkgName = (TextView) findViewById(R.id.tvPkgName);
        tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
        llEditProfile = (LinearLayout) findViewById(R.id.llEditProfile);
        llChanhPsw = (LinearLayout) findViewById(R.id.llChanhPsw);
        llEditBilling = (LinearLayout) findViewById(R.id.llEditBilling);
        llChangPackg = (LinearLayout) findViewById(R.id.llChangPackg);
        llLanguage = (LinearLayout) findViewById(R.id.llLanguage);
        llMyMembers = (LinearLayout) findViewById(R.id.llMyMembers);
        llStopSub = (LinearLayout) findViewById(R.id.llStopSub);
        llTermsofService = (LinearLayout) findViewById(R.id.llTermsofService);
        llMemberAgree = (LinearLayout) findViewById(R.id.llMemberAgree);
        llPrivacy = (LinearLayout) findViewById(R.id.llPrivacy);
        view = findViewById(R.id.view);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        BtnSignOut = (Button) findViewById(R.id.BtnSignOut);
        activity = getIntent().getStringExtra("activity");
        img_prf = (ImageView) findViewById(R.id.img_prf);
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        userId = prefsData.getInt("userId", 1);
        fullName = prefsData.getString("fullName", Application_holder.userName);
        email = prefsData.getString("email", Application_holder.userEmail);
        vurvId = prefsData.getString("vurvId", "");
        packageId = prefsData.getString("packageId", "");
        parent_id = prefsData.getString("parent_id", "");
        tvName.setText(fullName);
        if (checkInternet()) {
            displayPhoto();
            myUserDetailsService();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        post_titile = prefsData.getString("post_title", "");
        packageName = prefsData.getString("packageName", "");
        /*if (parent_id.equalsIgnoreCase("0")) {
            llMyMembers.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        } else {
            llMyMembers.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }*/

        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvId.substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvId.substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvId.substring(7, 11));
            tvVURVID.setText(": "+stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            tvVURVID.setText(": "+vurvId);
        }


//        tvVURVID.setText(": " +vurvId);
        try {
            long number = Long.parseLong(prefsData.getString("email", "").split("@")[0]);
            tvUserEmail.setText("");
            tvUserEmail.setEnabled(true);
        } catch (NumberFormatException e) {
            tvUserEmail.setText(prefsData.getString("email", ""));
            tvUserEmail.setEnabled(false);
        }
        sharedPreferences = getSharedPreferences("VURVProfileDetails", 0);
        editor = sharedPreferences.edit();
        //profileImageUril = Uri.parse(sharedPreferences.getString("ProfileImageURl", ""));
        /*Glide.with(this)
                .load(profileImageUril)
                .error(R.drawable.profile_noimage_ic)
                .placeholder(R.drawable.profile_noimage_ic)
                .centerCrop().into(img_prf);*/
        llSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, StartScreenActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrimaryAcntHolderActivity.this, FreshdeskMainListActivity.class);
                intent.putExtra("move","PrimaryAcntHolderActivity");
                startActivity(intent);
                finish();
            }
        });
        llEditProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, EditProfileActivity.class));
            }
        });
        llChanhPsw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, ChangePasswordScreen.class));
            }
        });
        llEditBilling.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, EditBillingActivity.class));
            }
        });
        llMyMembers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myMembersIntent = new Intent(PrimaryAcntHolderActivity.this, MyMembersActivity.class);
                myMembersIntent.putExtra("activity", "PrimaryAcntHolderActivity");
                startActivity(myMembersIntent);
            }
        });
        llStopSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, StopSubscriptionActivity.class));
            }
        });
        llChangPackg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, UpgradeSubscriptionActivity.class));
            }
        });
        llLanguage.setOnClickListener(new OnClickListener() {
            @TargetApi(23)
            public void onClick(View v) {
                customLangDialog();
            }
        });
        llTermsofService.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, VURVTermsActivity.class));
            }
        });
        llMemberAgree.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, MemberShipAgreementActivity.class));
            }
        });
        llPrivacy.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PrimaryAcntHolderActivity.this, PrivacyActivity.class));
            }
        });
        BtnSignOut.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final Dialog customDialog = new Dialog(PrimaryAcntHolderActivity.this);
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
                tv_title.setText(getResources().getString(R.string.app_name));
                info_heading.setText(R.string.logout_txt);
                tv_title.setTypeface(null, Typeface.BOLD);
                tv_title.setTextSize(20.0f);
                yesBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Editor editor = getSharedPreferences("VURVProfileDetails", 0).edit();
                        editor.clear();
                        editor.putString("logout", "logout");
                        editor.commit();
                        Intent intent = new Intent(PrimaryAcntHolderActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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
                customDialog.getWindow().setAttributes(lp);
                customDialog.show();
            }
        });
    }

    private void displayPhoto() {
        showProgressDialog(PrimaryAcntHolderActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(PrimaryAcntHolderActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> displayPhotopojo = new ArrayList();
        MyMemberListPayload displayPhotopayload = new MyMemberListPayload();
        displayPhotopayload.setUserId(String.valueOf(userId));
        displayPhotopojo.add(displayPhotopayload);
        apiService.displayPhotoService(displayPhotopojo).enqueue(new Callback<EditPhotoResPayload>() {
            public void onResponse(Call<EditPhotoResPayload> call, Response<EditPhotoResPayload> response) {
                if (response.isSuccessful()) {
                    if(response.body().getImageLink()!=null) {
                        String url = response.body().getImageLink().replace("https://www.vurvhealth.com/","https://www.vurvhealth.com/v2/api/");
                        Glide.with(PrimaryAcntHolderActivity.this).load(url).error(R.drawable.profilepic_ic).placeholder(R.drawable.profilepic_ic).centerCrop().into(img_prf);
                        editor.putString("ProfileImageURl", url);
                        editor.commit();
                    }
                }
            }

            public void onFailure(Call<EditPhotoResPayload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        editor.putString("ProfileImageURl", data.getData().toString());
        editor.commit();
    }

    protected void onResume() {
        super.onResume();
        profileImageUril = Uri.parse(sharedPreferences.getString("ProfileImageURl", ""));
        Glide.with(PrimaryAcntHolderActivity.this).load(profileImageUril).error(R.drawable.profile_noimage_ic).placeholder(R.drawable.profile_noimage_ic).centerCrop().into(img_prf);
        myUserDetailsService();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon((int) R.drawable.vurv_logo_r).setTitle((CharSequence) getString(R.string.app_name)).setMessage((CharSequence) "Are you sure you want to close this App?").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) null).show();
    }

    private void myUserDetailsService() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(PrimaryAcntHolderActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(userId));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getUserDetailData(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<ArrayList<UserDetailDataResPayload>>>() {
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
                    loginEditor.putString("vurv_mem_exp_date", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getVurvMemExpDate());
                    loginEditor.putString("parent_id", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getParentId());
                    loginEditor.putString("orderId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getOrderId());
                    loginEditor.putString("search_type", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSearch_type());
                    loginEditor.putString("logout", "");
                    loginEditor.commit();
                    return;
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    @RequiresApi(api = 23)
    private void customLangDialog() {
        final Dialog customDialog = new Dialog(PrimaryAcntHolderActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.language_screen);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        Button doneBtn = (Button) customDialog.findViewById(R.id.doneBtn);
        Button cancelButton = (Button) customDialog.findViewById(R.id.cancelButton);
        final TextView english = (TextView) customDialog.findViewById(R.id.english);
        final TextView chinese = (TextView) customDialog.findViewById(R.id.chinese);
        selectedLanguage = langPreferences.getString(MedicalScreenActivity.languageTitle, "");
        if (selectedLanguage.equalsIgnoreCase("chinese")) {
            chinese.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.blue));
            chinese.setTypeface(null, Typeface.BOLD);
            english.setTypeface(null, Typeface.NORMAL);
            english.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.black));
        } else if (selectedLanguage.equalsIgnoreCase("english")) {
            english.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.blue));
            english.setTypeface(null, Typeface.BOLD);
            chinese.setTypeface(null, Typeface.NORMAL);
            chinese.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.black));
        }
        english.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                english.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.blue));
                english.setTypeface(null, Typeface.BOLD);
                chinese.setTypeface(null, Typeface.NORMAL);
                chinese.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.black));
                langEditor.putString(MedicalScreenActivity.languageTitle, "english");
                langEditor.commit();
            }
        });
        chinese.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                chinese.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.blue));
                chinese.setTypeface(null, Typeface.BOLD);
                english.setTypeface(null, Typeface.NORMAL);
                english.setTextColor(ContextCompat.getColor(PrimaryAcntHolderActivity.this, R.color.black));
                langEditor.putString(MedicalScreenActivity.languageTitle, "chinese");
                langEditor.commit();
            }
        });
        doneBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectedLanguage = langPreferences.getString(MedicalScreenActivity.languageTitle, "");
                Locale mylocale = null;
                if (selectedLanguage.equalsIgnoreCase("chinese")) {
                    mylocale = Locale.SIMPLIFIED_CHINESE;
                } else if (selectedLanguage.equalsIgnoreCase("english")) {
                    mylocale = Locale.ENGLISH;
                }
                Resources resources = getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration conf = resources.getConfiguration();
                conf.locale = mylocale;
                resources.updateConfiguration(conf, dm);
                customDialog.dismiss();
                customDialog.cancel();
                Intent intent = new Intent(PrimaryAcntHolderActivity.this, StartScreenActivity.class);
                finish();
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
    }
}
