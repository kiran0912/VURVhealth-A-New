package com.VURVhealth.vurvhealth.authentication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.myProfile.ChangePasswordScreen;
import com.VURVhealth.vurvhealth.myProfile.TermsActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UserDetailDataResPayload;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.authentication.loginPojos.LoginResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 29/11/16.
 */
public class LoginActivity extends SuperAppCompactActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btn_createAcnt;
    private Button btn_login_active;
    private Button btn_login_inactive;
    SharedPreferences.Editor editor;
    private TextView forgot_pswdTv;
    private TextView incorrect_login;
    SharedPreferences langPreferences;
    private LoginResPayLoad loginResPayLoads;
    private String selectedLanguage;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private EditText tvEmailID;
    private EditText tvPassword;
    private ArrayList<ArrayList<UserDetailDataResPayload>> userInfoResPayload;
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        langPreferences = getSharedPreferences("languagePref", 0);
        editor = langPreferences.edit();
        if (langPreferences.getString(MedicalScreenActivity.languageTitle, "").length() == 0) {
            customLangDialog();
        }
        tvEmailID = (EditText) findViewById(R.id.tvEmailID);
        tvPassword = (EditText) findViewById(R.id.tvPassword);
        btn_login_inactive = (Button) findViewById(R.id.btn_login_inactive);
        btn_login_active = (Button) findViewById(R.id.btn_login_active);
        btn_createAcnt = (Button) findViewById(R.id.btn_createAcnt);
        forgot_pswdTv = (TextView) findViewById(R.id.forgot_pswdTv);
        incorrect_login = (TextView) findViewById(R.id.incorrect_login);
        tvEmailID.addTextChangedListener(textWatcher);
        tvPassword.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();
        incorrect_login.setVisibility(View.GONE);
        btn_login_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tvEmailID.getText().toString();
                String password = tvPassword.getText().toString();
                if (email.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                    incorrect_login.setVisibility(View.GONE);
                } else if (password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    incorrect_login.setVisibility(View.GONE);
                } else if (checkInternet()) {
                    getLoginService(email, password, "cool");
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_createAcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Application_holder.SIGN_UP_URL));
                startActivity(intent);

            }

        });
        forgot_pswdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void getLoginService(final String userNameAndEmail, String password, String insecure) {
        try {
            showProgressDialog(LoginActivity.this);
            ((ApiInterface) ApiClient.getClient(LoginActivity.this).create(ApiInterface.class)).getLoginWithUSerNameService(userNameAndEmail, password,insecure).enqueue(new Callback<LoginResPayLoad>() {
                public void onResponse(Call<LoginResPayLoad> call, Response<LoginResPayLoad> response) {
                    if (response.isSuccessful()) {
                        loginResPayLoads = (LoginResPayLoad) response.body();
                        if (loginResPayLoads.getStatus().equalsIgnoreCase("ok")) {
                            if (loginResPayLoads.getUser().getId() == null || loginResPayLoads.getPackageInfo() == null) {
                                incorrect_login.setVisibility(View.VISIBLE);
                                dismissProgressDialog();
                                return;
                            }
                            SharedPreferences.Editor editor = getSharedPreferences("VURVProfileDetails", 0).edit();
                            editor.putString("ProfileImageURl", "https://www.vurvhealth.com/wp-content/uploads" + loginResPayLoads.getPackageInfo().getImagePath());
                            editor.commit();
                            loginEditor.putString("emailId", userNameAndEmail);
                            incorrect_login.setVisibility(View.GONE);
                            loginEditor.putString("firstName", loginResPayLoads.getPackageInfo().getFirstName());
                            loginEditor.putString("lastName", loginResPayLoads.getPackageInfo().getLastName());
                            loginEditor.putString("fullName", loginResPayLoads.getPackageInfo().getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + loginResPayLoads.getPackageInfo().getLastName());
                            loginEditor.putString("email", loginResPayLoads.getUser().getEmail());
                            loginEditor.putString("vurvId", loginResPayLoads.getPackageInfo().getMemberId());
                            loginEditor.putInt("userId", Integer.parseInt(loginResPayLoads.getPackageInfo().getUserId()));
                            loginEditor.putString("dob", loginResPayLoads.getPackageInfo().getDateOfBirth());
                            loginEditor.putString("gender", loginResPayLoads.getPackageInfo().getGender());
                            if (loginResPayLoads.getPackageInfo().getMobileNo() != null) {
                                loginEditor.putString("mobileNo", loginResPayLoads.getPackageInfo().getMobileNo().equalsIgnoreCase("0") ? loginResPayLoads.getUser().getUsername() : loginResPayLoads.getPackageInfo().getMobileNo());
                            } else {
                                editor.putString("mobileNo", loginResPayLoads.getPackageInfo().getMobileNo() == null ? loginResPayLoads.getUser().getUsername() : loginResPayLoads.getPackageInfo().getMobileNo());
                            }
                            loginEditor.putString("address1", loginResPayLoads.getPackageInfo().getAddress1());
                            loginEditor.putString("address2", loginResPayLoads.getPackageInfo().getAddress2());
                            loginEditor.putString("city", loginResPayLoads.getPackageInfo().getCity());
                            loginEditor.putString("stateName", loginResPayLoads.getPackageInfo().getState());
                            loginEditor.putString("zipCode", loginResPayLoads.getPackageInfo().getZipcode());
                            loginEditor.putString("zip4Code", loginResPayLoads.getPackageInfo().getZipFourCode());
                            loginEditor.putString("country", loginResPayLoads.getPackageInfo().getCountry());
                            loginEditor.putString("subPackageId", loginResPayLoads.getPackageInfo().getSubPackageId());
                            loginEditor.putString("packageId", loginResPayLoads.getPackageInfo().getPackageId());
                            loginEditor.putString("parent_id", loginResPayLoads.getPackageInfo().getParentId());
                            loginEditor.putString("subscription_end_date", loginResPayLoads.getPackageInfo().getSubscriptionEndDate());
                            if (loginResPayLoads.getPackageInfo().getOrderId() != null) {
                                loginEditor.putString("orderId", loginResPayLoads.getPackageInfo().getOrderId());
                            }
                            loginEditor.putString("logout", "");
                            loginEditor.commit();
                            myUserDetailsService();
                            return;
                        } /*else if (loginResPayLoads.getStatus().equalsIgnoreCase(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)) {
                            dismissProgressDialog();
                            incorrect_login.setVisibility(View.VISIBLE);
                            return;
                        } */else {
                            incorrect_login.setVisibility(View.VISIBLE);
                            return;
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    }
                    dismissProgressDialog();
                    //incorrect_login.setVisibility(View.VISIBLE);
                }

                public void onFailure(Call<LoginResPayLoad> call, Throwable t) {
                    dismissProgressDialog();
                    Log.e("LoginError", t.toString());
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            dismissProgressDialog();
            Log.d("LoginError", e.getMessage());
        }
    }

    public void onStart() {
        super.onStart();
        client.connect();
    }

    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    private void checkFieldsForEmptyValues() {
        String email = tvEmailID.getText().toString();
        String password = tvPassword.getText().toString();
        if (email.equals("") || password.equals("")) {
            btn_login_active.setVisibility(View.GONE);
            btn_login_inactive.setVisibility(View.VISIBLE);
            incorrect_login.setVisibility(View.GONE);
            return;
        }
        btn_login_active.setVisibility(View.VISIBLE);
        btn_login_inactive.setVisibility(View.GONE);
    }

    private void customLangDialog() {
        final Dialog customDialog = new Dialog(LoginActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.language_screen);
        /*LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;*/
        Button doneBtn = (Button) customDialog.findViewById(R.id.doneBtn);
        final TextView english = (TextView) customDialog.findViewById(R.id.english);
        final TextView chinese = (TextView) customDialog.findViewById(R.id.chinese);
        ((Button) customDialog.findViewById(R.id.cancelButton)).setVisibility(View.GONE);
        english.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                english.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.blue));
                english.setTypeface(null, Typeface.BOLD);
                chinese.setTypeface(null, Typeface.NORMAL);
                chinese.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.black));
                editor.putString(MedicalScreenActivity.languageTitle, "english");
                editor.commit();
            }
        });
        chinese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chinese.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.blue));
                chinese.setTypeface(null, Typeface.BOLD);
                english.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.black));
                english.setTypeface(null, Typeface.NORMAL);
                editor.putString(MedicalScreenActivity.languageTitle, "chinese");
                editor.commit();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //customDialog.getWindow().setAttributes(lp);
        customDialog.show();
    }

    private void myUserDetailsService() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(LoginActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getUserDetailData(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<ArrayList<UserDetailDataResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Response<ArrayList<ArrayList<UserDetailDataResPayload>>> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    userInfoResPayload = (ArrayList) response.body();
                    if (userInfoResPayload.size() != 0) {
                        String str;
                        loginEditor.putString("user_login", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserLogin());
                        loginEditor.putString("emailId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserEmail() == null ? "" : ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getUserEmail());
                        loginEditor.putString("firstName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getFirstName());
                        loginEditor.putString("lastName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getLastName());
                        loginEditor.putString("fullName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getLastName());
                        SharedPreferences.Editor editor = loginEditor;
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
                        loginEditor.putString("subPackageId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubPackageId());
                        loginEditor.putString("post_title", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPostTitle());
                        loginEditor.putString("orderId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getOrderId());
                        loginEditor.putString("packageName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getName());
                        loginEditor.putString(FirebaseAnalytics.Param.PRICE, ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPrice());
                        loginEditor.putString("childCount", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getChild_count());
                        loginEditor.putString("search_type", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSearch_type());
                        loginEditor.putString("IsPasswordResetFlag", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getIsPasswordResetFlag());
                        if (((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubscriptionEndDate() != null) {
                            loginEditor.putString("subscription_end_date", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubscriptionEndDate());
                        }
                        loginEditor.putString("logout", "");
                        loginEditor.commit();
                        Intent i;
                        if (prefsLoginData.getString("IsPasswordResetFlag", "").equalsIgnoreCase("0")) {
                            i = new Intent(LoginActivity.this, ChangePasswordScreen.class);
                            i.putExtra("activity", "LoginActivity");
                            startActivity(i);
                            return;
                        }
                        i = new Intent(LoginActivity.this, StartScreenActivity.class);
                        i.putExtra("firstName", loginResPayLoads.getPackageInfo().getFirstName());
                        startActivity(i);
                        finish();
                        return;
                    } else if (userInfoResPayload.size() == 0) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
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
}
