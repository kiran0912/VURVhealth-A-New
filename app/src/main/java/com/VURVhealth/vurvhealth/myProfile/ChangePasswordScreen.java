package com.VURVhealth.vurvhealth.myProfile;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.authentication.LoginActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.ChangePasswordReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.ChangePasswordResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordScreen extends SuperAppCompactActivity {
    private String activity = "";
    ChangePasswordResPayload changePasswordResPayload;
    private ImageView cross_btn;
    public final Pattern patternPwd = Pattern.compile("((?=.*\\d)(?=.*[A-Z])(?=.*[@#$%]).{8,15})");
    private TextView tvCancel;
    private EditText tvCurrentPswd;
    private TextView tvDone, tvDone_inactive;
    private EditText tvNewPswd;
    private TextView tvPasswordChar;
    private EditText tvRetypePswd;
    private TextView tvpwd_dntmatch;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_screen);
        try {
            activity = getIntent().getStringExtra("activity");
        } catch (NullPointerException e) {
            activity = "";
        }
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvDone_inactive = (TextView) findViewById(R.id.tvDone_inactive);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvCurrentPswd = (EditText) findViewById(R.id.tvCurrentPswd);
        tvNewPswd = (EditText) findViewById(R.id.tvNewPswd);
        tvRetypePswd = (EditText) findViewById(R.id.tvRetypePswd);
        tvPasswordChar = (TextView) findViewById(R.id.tvPasswordChar);
        tvpwd_dntmatch = (TextView) findViewById(R.id.tvpwd_dntmatch);
        cross_btn = (ImageView) findViewById(R.id.cross_btn);
        if (activity == null || !activity.equalsIgnoreCase("LoginActivity")) {
            tvCancel.setVisibility(View.VISIBLE);
        } else {
            tvCancel.setVisibility(View.GONE);
        }
        cross_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRetypePswd.setText("");
                tvpwd_dntmatch.setVisibility(View.GONE);
            }
        });
        tvNewPswd.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //tvPasswordChar.setVisibility(View.VISIBLE);
                return false;
            }
        });
        tvRetypePswd.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvPasswordChar.setVisibility(View.GONE);
                tvRetypePswd.setSelected(true);
                return false;
            }
        });
        tvRetypePswd.addTextChangedListener(new TextWatcher() {
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
        });
        tvDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1 = tvCurrentPswd.getText().toString();
                String password = tvNewPswd.getText().toString();
                String confirmpwd = tvRetypePswd.getText().toString();
                boolean passfrmat = patternPwd.matcher(password).matches();
                boolean passfrmat1 = patternPwd.matcher(password1).matches();
                if (tvCurrentPswd.getText().toString().length() == 0) {
                    Toast.makeText(ChangePasswordScreen.this, R.string.enter_current_pwd, Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(ChangePasswordScreen.this, R.string.enter_new_pwd, Toast.LENGTH_SHORT).show();
                } else if (!passfrmat) {
                    tvPasswordChar.setVisibility(View.VISIBLE);
                    tvPasswordChar.setTextColor(ContextCompat.getColor(ChangePasswordScreen.this, R.color.holo_red_light));
                } else if (confirmpwd.length() == 0) {
                    Toast.makeText(ChangePasswordScreen.this, R.string.retype_pwd, Toast.LENGTH_SHORT).show();
                } else if (!confirmpwd.equals(password)) {
                    tvPasswordChar.setVisibility(View.GONE);
                    tvpwd_dntmatch.setVisibility(View.VISIBLE);
                    cross_btn.setVisibility(View.VISIBLE);
                } else if (checkInternet()) {
                    changePassword();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordScreen.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
    }

    private void checkFieldsForEmptyValues() {
        String retypepwd = tvRetypePswd.getText().toString();
        if (retypepwd.equals("")) {
            tvDone.setVisibility(View.GONE);
            tvDone_inactive.setVisibility(View.VISIBLE);
            return;
        }
        tvDone.setVisibility(View.VISIBLE);
        tvDone.setTypeface(tvDone.getTypeface(), Typeface.BOLD);
        tvDone_inactive.setVisibility(View.GONE);
    }

    private void changePassword() {
        showProgressDialog(ChangePasswordScreen.this);
        ApiInterface apiInterface = (ApiInterface) ApiClient.getClient(ChangePasswordScreen.this).create(ApiInterface.class);
        ChangePasswordReqPayload changePasswordReqPayload = new ChangePasswordReqPayload();
        changePasswordReqPayload.setLoginvariable(prefsLoginData.getString("user_login", ""));
        changePasswordReqPayload.setLoginpass(tvCurrentPswd.getText().toString());
        changePasswordReqPayload.setNewPass(tvNewPswd.getText().toString());
        ArrayList<ChangePasswordReqPayload> changePasswordReqPayloads = new ArrayList();
        changePasswordReqPayloads.add(changePasswordReqPayload);
        apiInterface.changePassword(changePasswordReqPayloads).enqueue(new Callback<ChangePasswordResPayload>() {
            @Override
            public void onResponse(Call<ChangePasswordResPayload> call, Response<ChangePasswordResPayload> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    changePasswordResPayload = (ChangePasswordResPayload) response.body();
                    String info = changePasswordResPayload.getMessage();
                    if (changePasswordResPayload.getCode().intValue() == 1) {
                        final Dialog customDialog = new Dialog(ChangePasswordScreen.this);
                        customDialog.setCancelable(false);
                        customDialog.requestWindowFeature(1);
                        customDialog.setContentView(R.layout.password_popup);
                        LayoutParams lp = new LayoutParams();
                        lp.copyFrom(customDialog.getWindow().getAttributes());
                        lp.width = -1;
                        lp.height = -2;
                        lp.gravity = 17;
                        Button btn_ok = (Button) customDialog.findViewById(R.id.pwdbtn_ok);
                        TextView tvPopText = (TextView) customDialog.findViewById(R.id.tvText);
                        TextView name = (TextView) customDialog.findViewById(R.id.tvEmail);
                        //name.setText(info);
                        customDialog.getWindow().setAttributes(lp);
                        customDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                customDialog.dismiss();
                                customDialog.cancel();
                                Intent intent = new Intent(ChangePasswordScreen.this, PrimaryAcntHolderActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                    } else {
                        Toast.makeText(ChangePasswordScreen.this, changePasswordResPayload.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ChangePasswordResPayload> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(ChangePasswordScreen.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("changePwd", t.toString());
            }
        });
    }
}
