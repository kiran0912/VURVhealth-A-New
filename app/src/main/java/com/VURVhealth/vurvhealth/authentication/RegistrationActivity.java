package com.VURVhealth.vurvhealth.authentication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.authentication.noncevaluepojo.NonceResPayLoad;
import com.VURVhealth.vurvhealth.onboarding.MainActivity;
import com.VURVhealth.vurvhealth.policies.PrivacyPolicyActivity;
import com.VURVhealth.vurvhealth.policies.TermsandConditionActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 29/11/16.
 */
public class RegistrationActivity extends SuperAppCompactActivity {
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private EditText tv_EmailId, tv_password, tv_cnfpwd;
    private Button btn_createAcnt, btn_createAcnt_active;
    private TextView tvpwd_dntmatch, tvPasswordChar, tvTermsandPrivacy, tvInfo;
    private ImageView backBtn, cross_btn;


    private String email, password, confirmpwd;
    private String nonceValue;
    private int userId;
    private WebView webView;
    public final Pattern patternPwd = Pattern
            .compile("((?=.*\\d)(?=.*[A-Z])(?=.*[@#$%]).{8,15})");

    public final Pattern pattern = Pattern
            .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        webView = (WebView) findViewById(R.id.webview_register);
        webView.getSettings();
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(RegistrationActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgressDialog(RegistrationActivity.this);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                dismissProgressDialog();
                String webUrl = webView.getUrl();

            }


        });
        webView.loadUrl(Application_holder.RX__REG_URL);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        /*tv_EmailId = (EditText) findViewById(R.id.tv_EmailId);
        tv_password = (EditText) findViewById(R.id.tv_password);
        tv_cnfpwd = (EditText) findViewById(R.id.tv_cnfpwd);
        btn_createAcnt = (Button) findViewById(R.id.btn_createAcnt);
        btn_createAcnt_active = (Button) findViewById(R.id.btn_createAcnt_active);
        cross_btn = (ImageView) findViewById(R.id.cross_btn);
        tvpwd_dntmatch = (TextView) findViewById(R.id.tvpwd_dntmatch);
        tvPasswordChar = (TextView) findViewById(R.id.tvPasswordChar);
        tvTermsandPrivacy = (TextView) findViewById(R.id.tvTermsandPrivacy);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setVisibility(View.VISIBLE);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        tv_EmailId.addTextChangedListener(textWatcher);
        tv_password.addTextChangedListener(textWatcher);
        tv_cnfpwd.addTextChangedListener(textWatcher);

        if (checkInternet())
            getNonce();
        else
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();


        tv_EmailId.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvPasswordChar.setVisibility(View.GONE);
                tvInfo.setVisibility(View.GONE);
                return false;
            }
        });
        tv_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvInfo.setVisibility(View.GONE);
                tvPasswordChar.setVisibility(View.VISIBLE);
                return false;
            }
        });
        tv_cnfpwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tv_cnfpwd.setFocusable(true);
                tvPasswordChar.setVisibility(View.GONE);
                tvInfo.setVisibility(View.GONE);
                return false;
            }
        });
        boolean b = tv_cnfpwd.isInEditMode();
        tv_cnfpwd.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    tvInfo.setVisibility(View.VISIBLE);
                }


                return false;
            }
        });


        checkFieldsForEmptyValues();

        customTextView(tvTermsandPrivacy);

        //When button is clicked
        btn_createAcnt_active.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                email = tv_EmailId.getText().toString();
                password = tv_password.getText().toString();
                confirmpwd = tv_cnfpwd.getText().toString();

                Matcher m = pattern.matcher(email);
                boolean emailFormat = m.matches();
                Matcher mPwd = patternPwd.matcher(password);
                boolean passfrmat = mPwd.matches();

                if (email.length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();

                }else if (password.length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();

                } else if (!passfrmat) {
                    tvPasswordChar.setVisibility(View.VISIBLE);
                    tvPasswordChar.setTextColor(getResources().getColor(R.color.holo_red_light));
//                    Toast.makeText(RegistrationActivity.this, "Password must be at least 8 characters long; contain at least 1 uppercase letter and 1 number", Toast.LENGTH_SHORT).show();
                } else if (confirmpwd.length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                } else if (!confirmpwd.equals(password)) {

                    tvPasswordChar.setVisibility(View.GONE);
                    tvpwd_dntmatch.setVisibility(View.VISIBLE);
                    cross_btn.setVisibility(View.VISIBLE);

                    btn_createAcnt.setVisibility(View.VISIBLE);
                    btn_createAcnt_active.setVisibility(View.GONE);

//                     btn_createAcnt.setBackground(getResources().getDrawable(R.drawable.gradient_button_background));
                } else if (!emailFormat || !email.contains(".com")) {
                    if (isMobileNumberValid(email) && email.length() == 10){
                        Intent intent = new Intent(RegistrationActivity.this, VURVHealthIDCreateActivity.class);
                        intent.putExtra("mobile", email);
                        intent.putExtra("password", password);
                        intent.putExtra("nonceValue", nonceValue);
                        startActivity(intent);
                    }else if (!isMobileNumberValid(email)){
                        Toast.makeText(RegistrationActivity.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistrationActivity.this, "Please enter 10 digit valid mobile number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(RegistrationActivity.this, VURVHealthIDCreateActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("nonceValue", nonceValue);
                    startActivity(intent);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_cnfpwd.setText("");

                cross_btn.setVisibility(View.GONE);
                tvpwd_dntmatch.setVisibility(View.GONE);
            }
        });
*/

    }

    //  Custom TextView for terms&conditions and privacypolicy
    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "By signing up I agree to the VURVhealth ");
        spanTxt.append("terms ");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                Intent i = new Intent(RegistrationActivity.this, TermsandConditionActivity.class);
                startActivity(i);

            }
        }, spanTxt.length() - " terms".length(), spanTxt.length(), 0);
        spanTxt.append(" and ");
        spanTxt.append("conditions");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {


                Intent i = new Intent(RegistrationActivity.this, TermsandConditionActivity.class);
                startActivity(i);

            }
        }, spanTxt.length() - "conditions".length(), spanTxt.length(), 0);
        spanTxt.append(" and");
        spanTxt.append(" privacy policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                Intent i = new Intent(RegistrationActivity.this, PrivacyPolicyActivity.class);
                startActivity(i);
            }
        }, spanTxt.length() - " privacy policy".length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    //TextWatcher to handle the empty fields
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    //check for the empty fields
    private void checkFieldsForEmptyValues() {

        email = tv_EmailId.getText().toString();
        password = tv_password.getText().toString();
        confirmpwd = tv_cnfpwd.getText().toString();

        if (email.equals("") && password.equals("") && confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.VISIBLE);


            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!email.equals("") && password.equals("") && confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);


            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!email.equals("") && !password.equals("") && confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);


            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!email.equals("") && password.equals("") && !confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);


            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!password.equals("") && email.equals("") && confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);

            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!password.equals("") && !email.equals("") && confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);

            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!password.equals("") && email.equals("") && !confirmpwd.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);

            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!confirmpwd.equals("") && email.equals("") && password.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);

            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!confirmpwd.equals("") && !email.equals("") && password.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);

            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else if (!confirmpwd.equals("") && email.equals("") && !password.equals("")) {

            btn_createAcnt_active.setVisibility(View.GONE);
            btn_createAcnt.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);

            btn_createAcnt_active.setEnabled(false);
            btn_createAcnt.setEnabled(true);

        } else {

            btn_createAcnt_active.setVisibility(View.VISIBLE);
            btn_createAcnt.setVisibility(View.GONE);
            tvInfo.setVisibility(View.VISIBLE);

            btn_createAcnt.setEnabled(false);
            btn_createAcnt_active.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Consuming nonce value form the service
    private void getNonce() {

        ApiInterface apiService =
                ApiClient.getClient(RegistrationActivity.this).create(ApiInterface.class);

        Call<NonceResPayLoad> call = apiService.getNonce("user", "register");
        call.enqueue(new Callback<NonceResPayLoad>() {
            @Override
            public void onResponse(Call<NonceResPayLoad> call, Response<NonceResPayLoad> response) {

                NonceResPayLoad nonceResPayLoad = response.body();

                if (nonceResPayLoad.getNonce()!=null) {
                    nonceValue = nonceResPayLoad.getNonce();
                }else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.server_not_found),Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "Number of movies received: ");
            }

            @Override
            public void onFailure(Call<NonceResPayLoad> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    public boolean isMobileNumberValid(String phoneNumber) {
        boolean isValid = false;

        // Use the libphonenumber library to validate Number
        /*PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber swissNumberProto =null ;
        try {
            swissNumberProto = phoneUtil.parse(phoneNumber, "CH");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        if(phoneUtil.isValidNumber(swissNumberProto))
        {
            isValid = true;
        }*/

        // The Library failed to validate number if it contains - sign
        // thus use regex to validate Mobile Number.
        String regex = "[0-9*#+() -]*";
        Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}