package com.VURVhealth.vurvhealth.authentication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 30/11/16.
 */

public class ForgotPasswordActivity extends SuperAppCompactActivity {

    private EditText emailId;
    private Button btn_sendEmail,btn_sendEmail_active;
    private TextView cancel;
    private Toolbar toolbar;
    private String mailId;
    public final Pattern pattern = Pattern
            .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
    private ForgotPasswordResPayLoad forgotPasswordResPayLoad;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pasword_screen);

        emailId = (EditText) findViewById(R.id.emailId);
        btn_sendEmail = (Button) findViewById(R.id.btn_sendEmail);
        btn_sendEmail_active = (Button) findViewById(R.id.btn_sendEmail_active);
        cancel = (TextView) findViewById(R.id.cancelBtn);

        toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);

        //When button is clicked
        btn_sendEmail_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mailId = emailId.getText().toString();
               // Matcher m = pattern.matcher(emailId.getText().toString().trim());
               // boolean emailFormat = m.matches();
                if (mailId.length() == 0) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email address", Toast.LENGTH_SHORT).show();
                }  else {

                    if(checkInternet()){
                        if(mailId.contains("@")&& mailId.contains(".")) {
                            getForgotPasswordService();
                        }else{
                            //getForgotSendOtp();
                            Toast.makeText(ForgotPasswordActivity.this, "Please enter correct email address ", Toast.LENGTH_SHORT).show();
                        }/*else{
                            Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.invalid_email_mobile), Toast.LENGTH_SHORT).show();
                        }*/
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
        //When button is clicked
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        //TextWatcher to check the empty fields
        emailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (emailId.getText().toString().length() > 0) {

                        btn_sendEmail.setVisibility(View.GONE);
                        btn_sendEmail_active.setVisibility(View.VISIBLE);
//                        btn_sendEmail.setBackground(getResources().getDrawable(R.drawable.gradient_button_background));
                }else {
                    btn_sendEmail_active.setVisibility(View.GONE);

                    btn_sendEmail.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void getForgotSendOtp(){

        try {
            showProgressDialog(ForgotPasswordActivity.this);
            ApiInterface apiService =
                    ApiClient.getClient(ForgotPasswordActivity.this).create(ApiInterface.class);

            ArrayList<ForgotPasswordReqSendOtpPayLoad> forgotPasswordReqSendOtpPayLoads = new ArrayList<ForgotPasswordReqSendOtpPayLoad>();
            final ForgotPasswordReqSendOtpPayLoad forgotPasswordReqSendOtpPayLoad = new ForgotPasswordReqSendOtpPayLoad();
            forgotPasswordReqSendOtpPayLoad.setMobile_no(emailId.getText().toString());
            forgotPasswordReqSendOtpPayLoads.add(forgotPasswordReqSendOtpPayLoad);

            Call<ForgotPasswordResPayLoad> call = apiService.getForgotPasswordSendOtp(forgotPasswordReqSendOtpPayLoads);
            call.enqueue(new Callback<ForgotPasswordResPayLoad>() {
                @Override
                public void onResponse(Call<ForgotPasswordResPayLoad> call, Response<ForgotPasswordResPayLoad> response) {

                    if (response.isSuccessful()) {
                        forgotPasswordResPayLoad = response.body();
                        dismissProgressDialog();

                        if (forgotPasswordResPayLoad.getCode()==1){
//                            Toast.makeText(getApplicationContext(),forgotPasswordResPayLoad.getMessage(),Toast.LENGTH_SHORT).show();
                            //To show the email popup
                            final Dialog customDialog = new Dialog(ForgotPasswordActivity.this);
                            customDialog.setCancelable(false);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.email_popup);

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(customDialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.gravity = Gravity.CENTER;

                            Button  btn_ok =(Button)customDialog.findViewById(R.id.btn_ok);
                            TextView  tvTitle =(TextView)customDialog.findViewById(R.id.tvTitle);
                            tvTitle.setText("SMS Sent!");
                            TextView  tvText =(TextView)customDialog.findViewById(R.id.tvText);
                            tvText.setText(""+getResources().getString(R.string.mobile_instructions));
                            TextView  name =(TextView)customDialog.findViewById(R.id.tvEmail);
                            name.setText(mailId);

                            customDialog.getWindow().setAttributes(lp);
                            customDialog.show();

                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                    customDialog.cancel();
                                    emailId.setText("");

                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(),forgotPasswordResPayLoad.getMessage(),Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        dismissProgressDialog();

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invali_mobile), Toast.LENGTH_LONG).show();

                       /* try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/
                    }

                }
                @Override
                public void onFailure(Call<ForgotPasswordResPayLoad> call, Throwable t) {
                    Log.e("ForgotPassword", t.toString());
                    dismissProgressDialog();
                    Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            Log.v("ForgotPassword", e.getMessage());
        }

    }

    private void getForgotPasswordService(){

        try {
            showProgressDialog(ForgotPasswordActivity.this);
            ApiInterface apiService =
                    ApiClient.getClient(ForgotPasswordActivity.this).create(ApiInterface.class);

            ArrayList<ForgotPasswordReqPayLoad> forgotPasswordReqPayLoads = new ArrayList<ForgotPasswordReqPayLoad>();
            final ForgotPasswordReqPayLoad forgotPasswordReqPayLoad = new ForgotPasswordReqPayLoad();
            forgotPasswordReqPayLoad.setEmailId(emailId.getText().toString());
            forgotPasswordReqPayLoads.add(forgotPasswordReqPayLoad);

            Call<ForgotPasswordResPayLoad> call = apiService.getForgotPasswordService(forgotPasswordReqPayLoads);
            call.enqueue(new Callback<ForgotPasswordResPayLoad>() {
                @Override
                public void onResponse(Call<ForgotPasswordResPayLoad> call, Response<ForgotPasswordResPayLoad> response) {

                    if (response.isSuccessful()) {
                        forgotPasswordResPayLoad = response.body();
                        dismissProgressDialog();

                        if (forgotPasswordResPayLoad.getCode()==1){
//                            Toast.makeText(getApplicationContext(),forgotPasswordResPayLoad.getMessage(),Toast.LENGTH_SHORT).show();
                            //To show the email popup
                            final Dialog customDialog = new Dialog(ForgotPasswordActivity.this);
                            customDialog.setCancelable(false);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.email_popup);

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(customDialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.gravity = Gravity.CENTER;

                            Button  btn_ok =(Button)customDialog.findViewById(R.id.btn_ok);
                            TextView  name =(TextView)customDialog.findViewById(R.id.tvEmail);
                            name.setText(mailId);

                            customDialog.getWindow().setAttributes(lp);
                            customDialog.show();

                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                    customDialog.cancel();
                                    emailId.setText("");

                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(),forgotPasswordResPayLoad.getMessage(),Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_not_found), Toast.LENGTH_LONG).show();

                       /* try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/
                    }

                }
                @Override
                public void onFailure(Call<ForgotPasswordResPayLoad> call, Throwable t) {
                    Log.e("ForgotPassword", t.toString());
                    dismissProgressDialog();
                    Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            Log.v("ForgotPassword", e.getMessage());
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
