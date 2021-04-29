package com.VURVhealth.vurvhealth.upgrade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.VURVhealth.vurvhealth.authorize.netpojos.UpgradePackageReqPayload;
import com.VURVhealth.vurvhealth.authorize.netpojos.UpgradePackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.VURVhealth.vurvhealth.myProfile.pojos.UserDetailDataResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * Created by yqlabs on 22/2/17.
 */

public class PulseAnnualConfPaymentActivity extends SuperAppCompactActivity {

    private Button btn_cnf,btnReviewPckg;
    private LinearLayout llMembers,llSecMembers;
    private TextView tvPrice,tvSetupFee,tvMemShip,tvStreetName,tvCityState,tvZipcode,tvTotalPrice,tvName,tvMail,tvGender,tvDob;
    private ImageView backBtn,package_image;
    private SqLiteDbHelper recentDbHelper;
    ArrayList<MyMembersResponse> addMemberDataObjects;
    private String firstName,fullName,vurvId,email,gender,dob,dobFormat,state;
    private double planPrice = 0.0,totalPrice = 0.0;
    public static DecimalFormat df = new DecimalFormat("##,##,##,##,##,##,##0.00");
    private Date date;
    private DateFormat dateFormat;
    private SharedPreferences preferences;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String name="7jEq86w9MyQJ";
    private String transacationKey = "2D252rGbQy39Qtk2";
    String val,address1,address2,city,stateName,zipCode,country,mobileNo;
    private ArrayList<ArrayList<UserDetailDataResPayload>> userInfoResPayload;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conf_payment_screen);
        recentDbHelper = new SqLiteDbHelper(this);

        retrofitInitiliazation();

        btnReviewPckg = (Button) findViewById(R.id.btnReviewPckg);
        btn_cnf = (Button) findViewById(R.id.btn_cnf);

        llMembers = (LinearLayout) findViewById(R.id.llMembers);
        llSecMembers = (LinearLayout) findViewById(R.id.llSecMembers);

        tvName = (TextView) findViewById(R.id.tvName);
        tvMail = (TextView) findViewById(R.id.tvMail);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvDob = (TextView) findViewById(R.id.tvDob);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvSetupFee = (TextView) findViewById(R.id.tvSetupFee);
        tvMemShip = (TextView) findViewById(R.id.tvMemShip);
        tvStreetName = (TextView) findViewById(R.id.tvStreetName);
        tvCityState = (TextView) findViewById(R.id.tvCityState);
        tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        package_image = (ImageView) findViewById(R.id.package_image);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        SharedPreferences prefsData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        firstName = prefsData.getString("firstName","Mary");
        fullName = prefsData.getString("fullName","Mary Smith");
        email = prefsData.getString("email","");
        vurvId = prefsData.getString("vurvId","9268");
        gender = prefsData.getString("gender","");
        dob = prefsData.getString("dob","");
        address1 = prefsData.getString("address1","");
        fullName = prefsData.getString("fullName","");
        address2 = prefsData.getString("address2","");
        city = prefsData.getString("city","");
        stateName = prefsData.getString("stateName","");
        zipCode = prefsData.getString("zipCode","");
        country = prefsData.getString("country","");
        mobileNo = prefsData.getString("mobileNo","");

        sharedPreferences = getSharedPreferences("authorizePref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvStreetName.setText(address1+" "+address2);
        tvCityState.setText(city+ ", " +stateName);
        tvZipcode.setText(zipCode);

        SharedPreferences sP = getSharedPreferences("HouseHoldNumber", Context.MODE_PRIVATE);
        String mem_type = sP.getString("member_type", "");
        preferences = getSharedPreferences("paymentOptions", Context.MODE_PRIVATE);
        tvMemShip.setText(preferences.getString("planName",""));
        String priceText = preferences.getString("subPlan","");
        String setupText = preferences.getString("setupFee","");
        planPrice = Double.parseDouble(priceText.substring(priceText.indexOf("$") + 1, priceText.length()));
        /*final SpannableStringBuilder sb = new SpannableStringBuilder(priceText);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        sb.setSpan(bss, 0, 100, Spannable.SPAN_INCLUSIVE_INCLUSIVE);*/
//        planPrice = Double.parseDouble(priceText.substring(priceText.indexOf("$") + 1, priceText.length()));

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date = new Date();

        val = df.format(planPrice);
        totalPrice = Double.parseDouble(val) + 20.00;
       /* while (m.find())
        {
            planPrice = Double.parseDouble(m.group(1));

        }*/
        df.format(planPrice);
        df.format(totalPrice);

        if (preferences.getString("planName","").contains("Monthly")){
            tvPrice.setText("$"+val + " per Month");

        }else if (preferences.getString("planName","").contains("Quarterly")){
            tvPrice.setText("$"+val + " per 3 Months");

        }else if (preferences.getString("planName","").contains("Half Yearly")){
            tvPrice.setText("$"+val + " per 6 Months");

        }else if (preferences.getString("planName","").contains("Yearly")){
            tvPrice.setText("$"+val + " per 12 Months");
        }

        if (preferences.getString("planName","").contains("Pulse")){
            package_image.setImageResource(R.drawable.package_card_pulse);
            btnReviewPckg.setText("Review PULSE Package");

            btnReviewPckg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PulseAnnualConfPaymentActivity.this,PulseDescPopupActivity.class));
//                    finish();
                }
            });

        }else if (preferences.getString("planName","").contains("Care")){
            package_image.setImageResource(R.drawable.package_card_care);
            btnReviewPckg.setText("Review CARE Package");
            btnReviewPckg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PulseAnnualConfPaymentActivity.this,CareDescPopActivity.class));
//                    finish();
                }
            });

        }else if (preferences.getString("planName","").contains("360")){
            package_image.setImageResource(R.drawable.package_card_360);
            btnReviewPckg.setText("Review 360 Package");
            btnReviewPckg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PulseAnnualConfPaymentActivity.this,Desc360PopActivity.class));
//                    finish();
                }
            });
        }

//        tvPrice.setText(preferences.getString("subPlan",""));
//        tvSetupFee.setText(preferences.getString("setupFee",""));


      /*  tvStreetName.setText(prefsData.getString("address1", ""));
        tvCityState.setText(prefsData.getString("city", "")+", "+prefsData.getString("stateName", " "));
        tvZipcode.setText(prefsData.getString("zipCode", ""));*/
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            date = dt.parse(dob);
            SimpleDateFormat sm = new SimpleDateFormat("MM-dd-yyyy");
            dobFormat = sm.format(date);
            tvDob.setText(dobFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(gender.equalsIgnoreCase("F")){
            tvGender.setText("Female");
        }else {
            tvGender.setText("Male");
        }
//        editor.putString("firstName", loginResPayLoads.getUser().getNicename());

        tvName.setText(firstName);
        tvMail.setText(email);
        tvTotalPrice.setText("$"+String.valueOf(df.format(totalPrice)));
//        tvGender.setText(gender);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PulseAnnualConfPaymentActivity.this,BillingInfoActivity.class));
                finish();
            }
        });

        btn_cnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chargeCustomerProfileService();
//                welComeDialog();
            }
        });
//        addMemberDataObjects = recentDbHelper.getMembersList(mem_type);
        myMembersService();


    }

    private void myMembersService() {
        showProgressDialog(PulseAnnualConfPaymentActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(PulseAnnualConfPaymentActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList<MyMemberListPayload>();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 0)));

        myMemberListPayloadArrayList.add(myMemberListPayload);

        Call<ArrayList<MyMembersResponse>> call = apiService.getMyMemberList(myMemberListPayloadArrayList);
        call.enqueue(new Callback<ArrayList<MyMembersResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<MyMembersResponse>> call, Response<ArrayList<MyMembersResponse>> response) {

                if (response.isSuccessful()) {
                    addMemberDataObjects = response.body();

                    for(int i=0; i<addMemberDataObjects.size();i++){
                        llSecMembers.addView(AddMemDeatils(i));
                    }
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

    private View AddMemDeatils(int position) {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.members_layout, null);

        // fill in any details dynamically here
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvMail = (TextView) view.findViewById(R.id.tvMail);
        TextView tvGender = (TextView) view.findViewById(R.id.tvGender);
        TextView tvDob = (TextView) view.findViewById(R.id.tvDob);
        tvName.setText(addMemberDataObjects.get(position).getFirstName());
        if(addMemberDataObjects.get(position).getUserEmail().contains("@vurvhealth.com")) {
            tvMail.setVisibility(View.GONE);
        }else
            tvMail.setText(addMemberDataObjects.get(position).getUserEmail());

        if(addMemberDataObjects.get(position).getGender().equalsIgnoreCase("F")){
            tvGender.setText("Female");
        }else {
            tvGender.setText("Male");
        }
        String dobMember = addMemberDataObjects.get(position).getDateOfBirth();

        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            date = dt.parse(dobMember);
            SimpleDateFormat sm = new SimpleDateFormat("MM-dd-yyyy");
            dobFormat = sm.format(date);
            tvDob.setText(dobFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        tvGender.setText(addMemberDataObjects.get(position).getGender());
//        tvDob.setText(addMemberDataObjects.get(position).getDateOfBirth());
        return view;
    }

    //show welcome dialog
//    private void welComeDialog(){
//        final Dialog customDialog = new Dialog(PulseAnnualConfPaymentActivity.this);
//        customDialog.setCancelable(false);
//        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        customDialog.setContentView(R.layout.confirmation_popup);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(customDialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.CENTER;
//
//        final Button btn_ok = (Button) customDialog.findViewById(R.id.okBtn);
//        final Button addMemberBtn = (Button) customDialog.findViewById(R.id.addMemberBtn);
//        final TextView name = (TextView) customDialog.findViewById(R.id.name);
//        final TextView vurv_id = (TextView) customDialog.findViewById(R.id.vurv_id);
//        final TextView tvWelcome = (TextView) customDialog.findViewById(R.id.tvWelcome);
//        final TextView tvName = (TextView) customDialog.findViewById(R.id.tvName);
//        final TextView tvMemberId = (TextView) customDialog.findViewById(R.id.tvMemberId);
//        final TextView expires = (TextView) customDialog.findViewById(R.id.expires);
//        final TextView tvpresentId = (TextView) customDialog.findViewById(R.id.tvpresentId);
//        final FrameLayout cardImg = (FrameLayout) customDialog.findViewById(R.id.cardImg);
//        tvName.setText(firstName+"!");
//        vurv_id.setText("VURV ID: "+vurvId);
//
//        tvWelcome.setText("Thanks, ");
//        expires.setVisibility(View.VISIBLE);
//        expires.setText("Expires: "+prefsLoginData.getString("subscription_end_date","12/12/2017"));
//
//        name.setText(fullName);
//
//        tvMemberId.setText("Your upgrade is complete.");
//        tvpresentId.setText("Present your VURV ID at a participating health provider to receive your discount.");
//
//        if (preferences.getString("planName","").contains("Pulse")){
//            cardImg.setBackgroundResource(R.drawable.package_card_pulse);
//
//        }else if (preferences.getString("planName","").contains("Care")){
//            cardImg.setBackgroundResource(R.drawable.package_card_care);
//
//        }else if (preferences.getString("planName","").contains("360")){
//            cardImg.setBackgroundResource(R.drawable.package_card_360);
//        }
//
//        if (preferences.getString("planName","").contains("Spouse") && Integer.parseInt(prefsLoginData.getString("childCount", "1")) < 1) {
//
//            addMemberBtn.setVisibility(View.VISIBLE);
//
//        }else if (preferences.getString("planName","").contains("Family") && Integer.parseInt(prefsLoginData.getString("childCount", "1")) < 3) {
//
//            addMemberBtn.setVisibility(View.VISIBLE);
//
//        }else {
//            addMemberBtn.setVisibility(View.GONE);
//        }
//
//
//        addMemberBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myUserDetailsService();
//                startActivity(new Intent(PulseAnnualConfPaymentActivity.this,MyMembersActivity.class));
//                customDialog.dismiss();
//                customDialog.cancel();
//            }
//        });
//
//        /*
//         * For custom color only using layerdrawable to fill the star colors
//         * */
//
//        customDialog.getWindow().setAttributes(lp);
//
//        customDialog.show();
//
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                upgradePackageService();
//
//                Intent i = new Intent(PulseAnnualConfPaymentActivity.this, StartScreenActivity.class);
//                startActivity(i);
//                finish();
//
////                chargeCustomerProfileService();
//
//                customDialog.dismiss();
//                customDialog.cancel();
//
//
//
//            }
//        });
//    }

    public void chargeCustomerProfileService() {
        try {
            showProgressDialog(PulseAnnualConfPaymentActivity.this);
            ApiInterface apiService =
                    retrofit.create(ApiInterface.class);

            Call<String> call = apiService.chargeCreditCard(requestChargeCustomerProfile());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()){
//                        dismissProgressDialog();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().trim());
                            JSONObject jsonObject1 = (JSONObject) jsonObject.get("messages");
                            JSONArray jsonElements = jsonObject1.getJSONArray("message");
                            JSONObject jsonObject2 = (JSONObject) jsonObject.get("transactionResponse");

                            editor.putString("transId",jsonObject2.getString("transId"));
                            editor.commit();

                            for (int i=0;i<jsonElements.length();i++){

                                JSONObject object = (JSONObject) jsonElements.get(i);

//                                Toast.makeText(getApplicationContext(), object.getString("text"),Toast.LENGTH_SHORT).show();
                                upgradePackageService();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dismissProgressDialog();
                        }

                    }

//                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(PulseAnnualConfPaymentActivity.this, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.v("Upgrad", e.getMessage());
        }
    }

    private String requestChargeCustomerProfile() {

        Random rand = new Random();
        int randomValue = rand.nextInt(5000) + 1;

        sharedPreferences = getSharedPreferences("authorizePref", Context.MODE_PRIVATE);
        String customerProfileId = sharedPreferences.getString("customerProfileId","");
        String paymentProfileId = sharedPreferences.getString("paymentProfileId","");

        String chargeProfileReq = "{\n" +
                "    \"createTransactionRequest\": {\n" +
                "        \"merchantAuthentication\": {\n" +
                "            \"name\": "+"\""+name+"\""+",\n" +
                "            \"transactionKey\": "+"\""+transacationKey+"\""+"\n" +
                "        },\n" +
                "        \"refId\": "+"\""+randomValue+"\""+",\n" +
                "        \"transactionRequest\": {\n" +
                "            \"transactionType\": \"authCaptureTransaction\",\n" +
                "            \"amount\": "+"\""+String.valueOf(df.format(totalPrice))+"\""+",\n" +
                "              \"profile\": {\n" +
                "    \t\t  \t\"customerProfileId\": "+"\""+customerProfileId+"\""+",\n" +
                "    \t\t  \t\"paymentProfile\": { \"paymentProfileId\": "+"\""+paymentProfileId+"\""+" }\n" +
                "  \t\t\t},\n" +
                "            \"lineItems\": {\n" +
                "                \"lineItem\": {\n" +
                "                    \"itemId\": \"1\",\n" +
                "                    \"name\": "+"\""+fullName+"\""+",\n" +
                "                    \"description\": \"Authorize Payment\",\n" +
                "                    \"quantity\": \"1\",\n" +
                "                    \"unitPrice\": "+"\""+String.valueOf(df.format(totalPrice))+"\""+"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        return chargeProfileReq;
        /*Random rand = new Random();
        int  n = rand.nextInt(500000) + 1;

        ChargeCustomerProfileRequest.LineItem lineItem = new ChargeCustomerProfileRequest.LineItem();
        lineItem.setDescription("");
        lineItem.setItemId("");
        lineItem.setName("");
        lineItem.setQuantity("");
        lineItem.setUnitPrice("");

        ChargeCustomerProfileRequest.LineItems lineItems = new ChargeCustomerProfileRequest.LineItems();
        lineItems.setLineItem(lineItem);

        ChargeCustomerProfileRequest.PaymentProfile paymentProfile = new ChargeCustomerProfileRequest.PaymentProfile();
        paymentProfile.setPaymentProfileId("1501052459");

        ChargeCustomerProfileRequest.Profile profile = new ChargeCustomerProfileRequest.Profile();
        profile.setCustomerProfileId("1501521372");
        profile.setPaymentProfile(paymentProfile);

        ChargeCustomerProfileRequest.TransactionRequest transactionRequest = new ChargeCustomerProfileRequest.TransactionRequest();
        transactionRequest.setTransactionType("authCaptureTransaction");
        transactionRequest.setAmount("");
        transactionRequest.setLineItems(lineItems);
        transactionRequest.setProfile(profile);

        ChargeCustomerProfileRequest.MerchantAuthentication merchantAuthentication = new ChargeCustomerProfileRequest.MerchantAuthentication();
        merchantAuthentication.setName("7jEq86w9MyQJ");
        merchantAuthentication.setTransactionKey("2D252rGbQy39Qtk2");

        ChargeCustomerProfileRequest.CreateTransactionRequest createTransactionRequest= new ChargeCustomerProfileRequest.CreateTransactionRequest();
        createTransactionRequest.setTransactionRequest(transactionRequest);
        createTransactionRequest.setMerchantAuthentication(merchantAuthentication);
        createTransactionRequest.setRefId("");


        ChargeCustomerProfileRequest chargeCustomerProfileRequest = new ChargeCustomerProfileRequest();
        chargeCustomerProfileRequest.setCreateTransactionRequest(createTransactionRequest);
        return chargeCustomerProfileRequest;*/



    }

    public void upgradePackageService() {
        try {
//            showProgressDialog(PulseAnnualConfPaymentActivity.this);
            ApiInterface apiService =
                    ApiClient.getClient(PulseAnnualConfPaymentActivity.this).create(ApiInterface.class);

            String profileId = sharedPreferences.getString("customerProfileId","");
            String transId = sharedPreferences.getString("transId","");
            String customerAddressId = sharedPreferences.getString("customerAddressId","");

            UpgradePackageReqPayload upgradePackageReqPayload = new UpgradePackageReqPayload();
            upgradePackageReqPayload.setCustomerId(String.valueOf(prefsLoginData.getInt("userId",1)));
            upgradePackageReqPayload.setCustomerProfileId(profileId);
            upgradePackageReqPayload.setShippingAddressId(transId);
            upgradePackageReqPayload.setShippingAddressHash(customerAddressId);
            upgradePackageReqPayload.setPaymentMethod("authorize_net_cim_credit_card");
            upgradePackageReqPayload.setPaymentMethodTitle("Authorize.Net");
            upgradePackageReqPayload.setCimCreditCardTransDate("");
            upgradePackageReqPayload.setCimCreditCardEnvironment("test");
            upgradePackageReqPayload.setCimCreditCardCustomerId("");
            upgradePackageReqPayload.setCimCreditCardPaymentToken("");
            upgradePackageReqPayload.setCimCreditCardAccountFour("");
            upgradePackageReqPayload.setCimCreditCardChargeCaptured("yes");
            upgradePackageReqPayload.setCimCreditCardCardExpiryDate("");
            upgradePackageReqPayload.setCimCreditCardCardType("");
            upgradePackageReqPayload.setCimCreditCardTransId("");
            upgradePackageReqPayload.setTransactionId("");
            upgradePackageReqPayload.setCimCreditCardAuthorizationCode("");
            upgradePackageReqPayload.setSubscriptionStartDate("");
            upgradePackageReqPayload.setSubscriptionEndDate("");
            upgradePackageReqPayload.setEmail(prefsLoginData.getString("email",""));
            upgradePackageReqPayload.setPhone(prefsLoginData.getString("mobileNo",""));
            upgradePackageReqPayload.setAddress1(prefsLoginData.getString("address1",""));
            upgradePackageReqPayload.setAddress2(prefsLoginData.getString("address2",""));
            upgradePackageReqPayload.setCity(prefsLoginData.getString("city",""));
            upgradePackageReqPayload.setState(prefsLoginData.getString("stateName",""));
            upgradePackageReqPayload.setPostcode(prefsLoginData.getString("zipCode",""));
            upgradePackageReqPayload.setCountry(prefsLoginData.getString("country",""));
            upgradePackageReqPayload.setPackageId(prefsLoginData.getString("packageId",""));
            upgradePackageReqPayload.setProductId(prefsLoginData.getString("subPackageId",""));

            ArrayList<UpgradePackageReqPayload> upgradePackageReqPayloadsList = new ArrayList<>();
            upgradePackageReqPayloadsList.add(upgradePackageReqPayload);

            Gson gson = new Gson();
            String request = gson.toJson(upgradePackageReqPayloadsList);
            Log.v("Upgrade", "Upgrade>>>>>>"+request);

            Call<UpgradePackageResPayload> call = apiService.getUpgradePackage(upgradePackageReqPayloadsList);
            call.enqueue(new Callback<UpgradePackageResPayload>() {
                @Override
                public void onResponse(Call<UpgradePackageResPayload> call, Response<UpgradePackageResPayload> response) {

                    if (response.isSuccessful()){
                        dismissProgressDialog();
                        SharedPreferences preferences1 = getSharedPreferences("paymentOptions", Context.MODE_PRIVATE);
                        loginEditor.putString("post_title",preferences1.getString("planName",""));
//                        welComeDialog();

                        myUserDetailsService();
                        Intent intent = new Intent(PulseAnnualConfPaymentActivity.this,StartScreenActivity.class);
                        intent.putExtra("activity", "ConfirmPaymentActivity");
                        startActivity(intent);
                        finish();
                    }
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<UpgradePackageResPayload> call, Throwable t) {
                    dismissProgressDialog();
                    Toast.makeText(PulseAnnualConfPaymentActivity.this, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.v("Upgrade", e.getMessage());
        }
    }

    private void myUserDetailsService() {
        showProgressDialog(PulseAnnualConfPaymentActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(PulseAnnualConfPaymentActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList<MyMemberListPayload>();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId",1)));

        myMemberListPayloadArrayList.add(myMemberListPayload);

        Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call = apiService.getUserDetailData(myMemberListPayloadArrayList);
        call.enqueue(new Callback<ArrayList<ArrayList<UserDetailDataResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Response<ArrayList<ArrayList<UserDetailDataResPayload>>> response) {

                if (response.isSuccessful()) {

                    dismissProgressDialog();
                    userInfoResPayload = response.body();

                    loginEditor.putString("user_login", userInfoResPayload.get(0).get(0).getUserLogin());
                    loginEditor.putString("emailId", userInfoResPayload.get(0).get(0).getUserEmail());
//                                loginEditor.putString("password", password);
                    loginEditor.putString("firstName", userInfoResPayload.get(0).get(0).getFirstName());
                    loginEditor.putString("lastName",userInfoResPayload.get(0).get(0).getLastName());
                    loginEditor.putString("fullName", userInfoResPayload.get(0).get(0).getFirstName()+" "+userInfoResPayload.get(0).get(0).getLastName());
                    loginEditor.putString("email", userInfoResPayload.get(0).get(0).getUserEmail());
                    loginEditor.putString("vurvId", userInfoResPayload.get(0).get(0).getMemberId());
                    loginEditor.putInt("userId", Integer.parseInt(userInfoResPayload.get(0).get(0).getUserId()));
                    loginEditor.putString("dob", userInfoResPayload.get(0).get(0).getDateOfBirth());
                    loginEditor.putString("gender", userInfoResPayload.get(0).get(0).getGender());
                    String s = userInfoResPayload.get(0).get(0).getUserEmail();
                    String[] mobileNo = s.split("@");
                    String mobile = mobileNo[0];
                    if(userInfoResPayload.get(0).get(0).getMobileNo()!=null)
                        loginEditor.putString("mobileNo", userInfoResPayload.get(0).get(0).getMobileNo());

                    loginEditor.putString("address1", userInfoResPayload.get(0).get(0).getAddress1());
                    loginEditor.putString("address2", userInfoResPayload.get(0).get(0).getAddress2());
                    loginEditor.putString("city", userInfoResPayload.get(0).get(0).getCity());
                    loginEditor.putString("stateName", userInfoResPayload.get(0).get(0).getState());
                    loginEditor.putString("zipCode", userInfoResPayload.get(0).get(0).getZipcode());
                    loginEditor.putString("zip4Code", userInfoResPayload.get(0).get(0).getZipFourCode());
                    loginEditor.putString("country", userInfoResPayload.get(0).get(0).getCountry());
                    loginEditor.putString("packageId", userInfoResPayload.get(0).get(0).getPackageId());
                    loginEditor.putString("subPackageId", userInfoResPayload.get(0).get(0).getSubPackageId());
                    loginEditor.putString("orderId", userInfoResPayload.get(0).get(0).getOrderId());
                    loginEditor.putString("post_title", userInfoResPayload.get(0).get(0).getPostTitle());
                    loginEditor.putString("subscription_end_date", userInfoResPayload.get(0).get(0).getSubscriptionEndDate());
                    loginEditor.putString("logout", "");
                    loginEditor.commit();

                } else {
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Throwable t) {

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
}
