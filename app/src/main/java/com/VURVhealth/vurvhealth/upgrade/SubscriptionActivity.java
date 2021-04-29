package com.VURVhealth.vurvhealth.upgrade;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;

import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import com.VURVhealth.vurvhealth.utilities.CardNumberTextWatcher;
import com.VURVhealth.vurvhealth.utilities.MonthYearPickerDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.text.DecimalFormat;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yqlabs on 12/7/17.
 */

public class SubscriptionActivity extends SuperAppCompactActivity implements DatePickerDialog.OnDateSetListener{

    private TextView tvMemShip,tvPrice,tvSubTotalPrice,tvTotalPrice,tvRecSubTotals,tvRecTotalPrice,tvRenewalDate;
    private EditText etCardNo,etSecurityCode;
    private Button etExpDate,btn_save_info_inactive,btn_save_info_active;
    private ImageView backBtn;
    private double planPrice = 0.0,totalPrice = 0.0,setupPrice;
    public static DecimalFormat df = new DecimalFormat("##,##,##,##,##,##,##0.00");
    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private Retrofit retrofit;
    private String name="7jEq86w9MyQJ";
    private String transacationKey = "2D252rGbQy39Qtk2";
    private String email,firstName,lastName,fullName,address1,address2,city,stateName,zipCode,country,
            mobileNo,cardNo,expireDate,securityCode,val,setupValue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_screen);

        retrofitInitiliazation();

        tvMemShip = (TextView) findViewById(R.id.tvMemShip);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvSubTotalPrice = (TextView) findViewById(R.id.tvSubTotalPrice);
        tvRecSubTotals = (TextView) findViewById(R.id.tvRecSubTotals);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvRecTotalPrice = (TextView) findViewById(R.id.tvRecTotalPrice);
        tvRenewalDate = (TextView) findViewById(R.id.tvRenewalDate);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        etExpDate = (Button) findViewById(R.id.etExpDate);
        btn_save_info_inactive = (Button) findViewById(R.id.btn_save_info_inactive);
        btn_save_info_active = (Button) findViewById(R.id.btn_save_info_active);
        etCardNo = (EditText) findViewById(R.id.etCardNo);
        etSecurityCode = (EditText) findViewById(R.id.etSecurityCode);

        etCardNo.addTextChangedListener(textwatcher);
        etExpDate.addTextChangedListener(textwatcher);
        etSecurityCode.addTextChangedListener(textwatcher);

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        email = prefsLoginData.getString("email","");
        firstName = prefsLoginData.getString("firstName","");
        lastName = prefsLoginData.getString("lastName","");
        fullName = prefsLoginData.getString("fullName","");
        address1 = prefsLoginData.getString("address1","");
        fullName = prefsLoginData.getString("fullName","");
        address2 = prefsLoginData.getString("address2","");
        city = prefsLoginData.getString("city","");
        stateName = prefsLoginData.getString("stateName","");
        zipCode = prefsLoginData.getString("zipCode","");
        country = prefsLoginData.getString("country","");
        mobileNo = prefsLoginData.getString("mobileNo","");

        SharedPreferences preferences = getSharedPreferences("paymentOptions", Context.MODE_PRIVATE);
        tvMemShip.setText(preferences.getString("planName",""));
        String priceText = preferences.getString("subPlan","");
        String setupText = preferences.getString("setupFee","");
        planPrice = Double.parseDouble(priceText.substring(priceText.indexOf("$") + 1, priceText.length()));
//        setupPrice = Double.parseDouble(setupText.substring(priceText.indexOf("$") + 1, priceText.length()));

        val = df.format(planPrice);
//        setupValue = df.format(setupPrice);
        totalPrice = Double.parseDouble(val) + 20.00;

        tvSubTotalPrice.setText("$"+val);
        tvTotalPrice.setText("$"+String.valueOf(df.format(totalPrice)));

        if (preferences.getString("planName","").contains("Monthly")){
            tvPrice.setText("$"+val + " / Month and a $20.00 sign-up fee");
            tvRecSubTotals.setText("$"+val + " / Month");
            tvRecTotalPrice.setText("$"+val + " / Month");
            c.add(Calendar.MONTH, 1);
            Date date =c.getTime();
            String ct = dateFormat.format(date);
            tvRenewalDate.setText("First renewal: "+ct);

        }else if (preferences.getString("planName","").contains("Quarterly")){
            tvPrice.setText("$"+val + " / 3 Months and a $20.00 sign-up fee");
            tvRecSubTotals.setText("$"+val + " / 3 Months");
            tvRecTotalPrice.setText("$"+val + " / 3 Months");

            c.add(Calendar.MONTH, 3);
            Date date =c.getTime();
            String ct = dateFormat.format(date);
            tvRenewalDate.setText("First renewal: "+ct);

        }else if (preferences.getString("planName","").contains("Half Yearly")){
            tvPrice.setText("$"+val + " / 6 Months and a $20.00 sign-up fee");
            tvRecSubTotals.setText("$"+val + " / 6 Months");
            tvRecTotalPrice.setText("$"+val + " / 6 Months");
            c.add(Calendar.MONTH, 6);
            Date date =c.getTime();
            String ct = dateFormat.format(date);
            tvRenewalDate.setText("First renewal: "+ct);

        }else if (preferences.getString("planName","").contains("Yearly")){
            tvPrice.setText("$"+val + " / 12 Months and a $20.00 sign-up fee");
            tvRecSubTotals.setText("$"+val + " / 12 Months");
            tvRecTotalPrice.setText("$"+val + " / 12 Months");
            c.add(Calendar.YEAR, 1);
            Date date =c.getTime();
            String ct = dateFormat.format(date);
            tvRenewalDate.setText("First renewal: "+ct);
        }

//        etCardNo.setTransformationMethod(new PasswordTransformation());
        etCardNo.addTextChangedListener(new CardNumberTextWatcher());
        etExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.setListener(SubscriptionActivity.this);
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });

        btn_save_info_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCardNo.getText().toString().length() < 19) {
                    Toast.makeText(SubscriptionActivity.this,"Please enter valid card number",Toast.LENGTH_SHORT).show();

                }/*else if (etSecurityCode.getText().toString().length() < 2) {
                    Toast.makeText(SubscriptionActivity.this,"Please enter valid security code",Toast.LENGTH_SHORT).show();

                }*/else {
                    createProfileAuthorizedService();
//                    startActivity(new Intent(SubscriptionActivity.this, PulseAnnualConfPaymentActivity.class));
                }
            }
        });

        etExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.setListener(SubscriptionActivity.this);
                pd.show(getFragmentManager(), "MonthYearPickerDialog");

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void retrofitInitiliazation() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Application_holder.PAYMENT_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    //TextWatcher to handle the empty fields
    private TextWatcher textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if(monthOfYear>0 && monthOfYear<10)
            etExpDate.setText("0"+monthOfYear +" / "+ String.valueOf(year).substring(2));
        else
            etExpDate.setText(monthOfYear +" / "+ String.valueOf(year).substring(2));
    }

    private void checkFieldsForEmptyValues() {

        String cardNo = etCardNo.getText().toString();
        String expDate = etExpDate.getText().toString();
        String securityCode = etSecurityCode.getText().toString();

        if(!cardNo.equals("")  && !expDate.equals("") && !securityCode.equals("")){

//            !spState.getSelectedItem().toString().trim().equals("State")
            btn_save_info_inactive.setVisibility(View.GONE);
            btn_save_info_active.setVisibility(View.VISIBLE);

        }else {
            btn_save_info_inactive.setVisibility(View.VISIBLE);
            btn_save_info_active.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void createProfileAuthorizedService() {
        try {
            showProgressDialog(SubscriptionActivity.this);
            ApiInterface apiService =
                    retrofit.create(ApiInterface.class);

            Call<String> call = apiService.createUserProfile(requestProfileCreation());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()){
                        Log.v("Response: ","response"+response);
//                        dismissProgressDialog();
                        JSONObject jsonObject = null;
                        JSONArray jsonElements = null;
                        try {
                            jsonObject = new JSONObject(response.body().trim());
                            JSONObject jsonObject1 = (JSONObject) jsonObject.get("messages");
                            jsonElements = jsonObject1.getJSONArray("message");
                            JSONArray paymentProfile = jsonObject.getJSONArray("customerPaymentProfileIdList");

                            sharedPreferences = getSharedPreferences("authorizePref", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("customerProfileId",jsonObject.getString("customerProfileId"));
                            editor.putString("paymentProfileId",paymentProfile.get(0).toString());
                            editor.commit();

                            for (int i=0;i<jsonElements.length();i++){

                                JSONObject object = (JSONObject) jsonElements.get(i);

                                if(object.getString("code").equalsIgnoreCase("E00027")){
                                    String errorMsg = object.getString("text");
                                    if (errorMsg.contains("card number is invalid")){
                                        Toast.makeText(getApplicationContext(), "Please enter valid card number",Toast.LENGTH_SHORT).show();
                                    }else if(errorMsg.contains("card has expired")){
                                        Toast.makeText(getApplicationContext(), "The credit card has expired.",Toast.LENGTH_SHORT).show();
                                    }else {

//                                        Toast.makeText(getApplicationContext(), object.getString("text"),Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    customerShippingAddressService();
//                                    Toast.makeText(getApplicationContext(), object.getString("text"), Toast.LENGTH_SHORT).show();
                                }

                                dismissProgressDialog();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            for (int i=0;i<jsonElements.length();i++){

                                JSONObject object = null;
                                try {
                                    object = (JSONObject) jsonElements.get(i);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                                try {
                                    if(object.getString("code").equalsIgnoreCase("E00027")){
                                        String errorMsg = object.getString("text");
                                        if (errorMsg.contains("card number is invalid")){
                                            Toast.makeText(getApplicationContext(), "Please enter valid card number",Toast.LENGTH_SHORT).show();
                                        }else if(errorMsg.contains("card has expired")){
                                            Toast.makeText(getApplicationContext(), "The credit card has expired.",Toast.LENGTH_SHORT).show();
                                        }else {

//                                            Toast.makeText(getApplicationContext(), object.getString("text"),Toast.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        customerShippingAddressService();
    //                                    Toast.makeText(getApplicationContext(), object.getString("text"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                                dismissProgressDialog();

                            }
                        }
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(SubscriptionActivity.this, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.v("Upgrad", e.getMessage());
        }
    }



    public void customerShippingAddressService() {
        try {
//            showProgressDialog(SubscriptionActivity.this);
            ApiInterface apiService =
                    retrofit.create(ApiInterface.class);

            Call<String> call = apiService.getCustomerShippingAddress(requestCustomerShippingAdres());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()){

                        dismissProgressDialog();
                        JSONObject jsonObject = null;
                        try {

                            jsonObject = new JSONObject(response.body().trim());
                            JSONObject jsonObject1 = (JSONObject) jsonObject.get("messages");
                            JSONArray jsonElements = jsonObject1.getJSONArray("message");

//                            JSONObject customerAddressId = (JSONObject) jsonObject.get("customerAddressId");

                            editor.putString("customerAddressId", jsonObject.getString("customerAddressId"));
                            editor.commit();

                            for (int i=0;i<jsonElements.length();i++){

                                JSONObject object = (JSONObject) jsonElements.get(i);

                                startActivity(new Intent(SubscriptionActivity.this, PulseAnnualConfPaymentActivity.class));

//                                Toast.makeText(getApplicationContext(), object.getString("text"),Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(SubscriptionActivity.this, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.v("Upgrad", e.getMessage());
        }
    }

    private String requestProfileCreation() {

//        email = "roopareddy.580@gmail.com";
        cardNo = etCardNo.getText().toString();
        expireDate = etExpDate.getText().toString();
        securityCode = etSecurityCode.getText().toString();
        Random rand = new Random();
        int randomValue = rand.nextInt(5000) + 1;

        String jsonRequest = "{\n" +
                "  \"createCustomerProfileRequest\": {\n" +
                "    \"merchantAuthentication\": {\n" +
                "      \"name\":"+"\""+name+"\""+",\n" +
                "      \"transactionKey\":"+"\""+transacationKey+"\""+"\n" +
                "    },\n" +
                "    \"profile\": {\n" +
                "    \t\"merchantCustomerId\":"+"\""+randomValue+"\""+",\n" +
                "      \"description\": \"This is test payment\",\n" +
                "      \"email\": \"\",\n" +
                "      \n" +
                "      \"paymentProfiles\": {\n" +
                "        \"customerType\": \"individual\",\n" +
                "        \"payment\": {\n" +
                "          \"creditCard\": {\n" +
                "            \"cardNumber\": "+"\""+cardNo.trim().replace(" ","")+"\""+",\n" +
                "            \"expirationDate\": "+"\""+expireDate.trim().replace(" ","")+"\""+"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"validationMode\": \"testMode\"\n" +
                "  }\n" +
                "}";

        return jsonRequest;
       /* //      -----------------------------------------------
        CreateCustomerProfileReqPayload.MerchantAuthentication merchantAuthentication = new CreateCustomerProfileReqPayload.MerchantAuthentication();
        merchantAuthentication.setName("7jEq86w9MyQJ");//need to change live account (loginid)
        merchantAuthentication.setTransactionKey("2D252rGbQy39Qtk2");// create on authorized .net

        CreateCustomerProfileReqPayload.CreditCard creditCard = new CreateCustomerProfileReqPayload.CreditCard();
        creditCard.setCardNumber(etCardNo.getText().toString().replace(" ",""));
        creditCard.setExpirationDate(etExpDate.getText().toString().trim().replace(" ",""));

        CreateCustomerProfileReqPayload.Payment payment = new CreateCustomerProfileReqPayload.Payment();
        payment.setCreditCard(creditCard);

        CreateCustomerProfileReqPayload.PaymentProfiles paymentProfiles = new CreateCustomerProfileReqPayload.PaymentProfiles();
        paymentProfiles.setCustomerType("individual");
        paymentProfiles.setPayment(payment);

        CreateCustomerProfileReqPayload.Profile profile = new CreateCustomerProfileReqPayload.Profile();
        profile.setMerchantCustomerId(String.valueOf(n));
        profile.setDescription("This is test payment");
        profile.setEmail(prefsLoginData.getString("email","VURVhealth@yqlabs.com"));
        profile.setPaymentProfiles(paymentProfiles);

//        ----------------------------------------

        CreateCustomerProfileReqPayload.CreateCustomerProfileRequest createCustomerProfileReqPayload = new CreateCustomerProfileReqPayload.CreateCustomerProfileRequest();
        createCustomerProfileReqPayload.setMerchantAuthentication(merchantAuthentication);
        createCustomerProfileReqPayload.setProfile(profile);
        createCustomerProfileReqPayload.setValidationMode("testMode");

        CreateCustomerProfileReqPayload reqPayload = new CreateCustomerProfileReqPayload();
        reqPayload.setCreateCustomerProfileRequest(createCustomerProfileReqPayload);

        Gson gson = new Gson();
        String gsonRequest = gson.toJson(reqPayload);
        Log.v("TAG","Profile Service Request >>>>>"+gsonRequest);
        return reqPayload;*/
    }



    private String requestCustomerShippingAdres() {

        sharedPreferences = getSharedPreferences("authorizePref", Context.MODE_PRIVATE);
        String customerProfileId = sharedPreferences.getString("customerProfileId","");
//        String paymentProfileId = sharedPreferences.getString("paymentProfileId","");

        String shippingRequest = "{\n" +
                "    \"createCustomerShippingAddressRequest\": {\n" +
                "        \"merchantAuthentication\": {\n" +
                "            \"name\": "+"\""+name+"\""+",\n" +
                "            \"transactionKey\": "+"\""+transacationKey+"\""+"\n" +
                "        },\n" +
                "        \"customerProfileId\": "+"\""+customerProfileId+"\""+",\n" +
                "        \"address\": {\n" +
                "            \"firstName\": "+"\""+firstName+"\""+",\n" +
                "            \"lastName\": "+"\""+lastName+"\""+",\n" +
                "            \"company\": \"\",\n" +
                "            \"address\": "+"\""+address1+"\""+",\n" +
                "            \"city\": "+"\""+city+"\""+",\n" +
                "            \"state\": "+"\""+stateName+"\""+",\n" +
                "            \"zip\": "+"\""+zipCode+"\""+",\n" +
                "            \"country\": "+"\""+country+"\""+",\n" +
                "            \"phoneNumber\": "+"\""+mobileNo+"\""+",\n" +
                "            \"faxNumber\": \"\"\n" +
                "        },\n" +
                "        \"defaultShippingAddress\": false\n" +
                "    }\n" +
                "}";

        return shippingRequest;

        /*CustomerShippingAdrsReqPayload customerShippingAdrsReqPayload = new CustomerShippingAdrsReqPayload();

        Gson gson = new Gson();
        String gsonRequest = gson.toJson(customerShippingAdrsReqPayload);
        Log.v("TAG","Profiel Service Request >>>>>"+gsonRequest);
        return customerShippingAdrsReqPayload;*/
    }
}
