package com.VURVhealth.vurvhealth.upgrade;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.myProfile.MyMembersActivity;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.pojos.GetSubPackageResPayload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqlabs on 20/2/17.
 */

public class PulsePaymentOptionsActivity extends SuperAppCompactActivity implements AdapterView.OnItemSelectedListener {

    private Spinner memberPicker, pricePicker;
    private LinearLayout llSubPlan;
    private ImageView backBtn;
    private TextView tb_title, tvSubPlan, tvSubPlanPrice, tvSubPlanSetup, tvPlans;
    private Button btn_next;
    private String member_type = "yearly";
    private SqLiteDbHelper recentDbHelper;
    private boolean itemSelected = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor1;
    private ArrayAdapter<String> singlePriceAdapter,spousePriceAdapter,familyPriceAdapter;
    List<String> selectedList = new ArrayList<String>();
    ArrayList<GetSubPackageResPayload> singlePrices;
    private int mSelectedPosition = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_option_screen);

        recentDbHelper = new SqLiteDbHelper(PulsePaymentOptionsActivity.this);

        memberPicker = (Spinner) findViewById(R.id.memberPicker);
        pricePicker = (Spinner) findViewById(R.id.pricePicker);
        btn_next = (Button) findViewById(R.id.btn_next);
        llSubPlan = (LinearLayout) findViewById(R.id.llSubPlan);

//        checkYear = (ImageView) findViewById(R.id.checkYear);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        tvSubPlan = (TextView) findViewById(R.id.tvSubPlan);
        tvSubPlanSetup = (TextView) findViewById(R.id.tvSubPlanSetup);
        tvSubPlanPrice = (TextView) findViewById(R.id.tvSubPlanPrice);

        tb_title = (TextView) findViewById(R.id.tb_title);
        tvPlans = (TextView) findViewById(R.id.tvPlans);

        tb_title.setText("Pulse");

        List<String> memberPick = new ArrayList<String>();
        memberPick.add("Single");
        memberPick.add("Spouse");
        memberPick.add("Family");

        ArrayAdapter<String> dataMemberAdapter = new ArrayAdapter<String>(PulsePaymentOptionsActivity.this,
                R.layout.spinner_item, R.id.tvspinner, memberPick);

        memberPicker.setAdapter(dataMemberAdapter);

        /*List<String> singlePrices = new ArrayList<String>();
        singlePrices.add("Monthly - $14.95");
        singlePrices.add("3 Months - $44.85");
        singlePrices.add("6 Months - $89.70");
        singlePrices.add("12 Months - $179.40");

        singlePriceAdapter = new ArrayAdapter<String>(PulsePaymentOptionsActivity.this,
                R.layout.spinner_item, R.id.tvspinner, singlePrices);

        List<String> spousePrices = new ArrayList<String>();
        spousePrices.add("Monthly - $19.95");
        spousePrices.add("3 Months - $59.85");
        spousePrices.add("6 Months - $119.70");
        spousePrices.add("12 Months - $239.40");

        spousePriceAdapter = new ArrayAdapter<String>(PulsePaymentOptionsActivity.this,
                R.layout.spinner_item, R.id.tvspinner, spousePrices);

        List<String> familyPrices = new ArrayList<String>();
        familyPrices.add("Monthly - $24.95");
        familyPrices.add("3 Months - $74.85");
        familyPrices.add("6 Months - $149.70");
        familyPrices.add("12 Months - $299.40");*/

        /*familyPriceAdapter = new ArrayAdapter<String>(PulsePaymentOptionsActivity.this,
                R.layout.spinner_item, R.id.tvspinner, familyPrices);*/

        SharedPreferences sharedPreferences = getSharedPreferences("HouseHoldNumber", Context.MODE_PRIVATE);
//        int selectedNumber = sharedPreferences.getInt("house_number", 0);
//        memberPicker.setSelection(selectedNumber);

        memberPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeDataSelectedItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*SharedPreferences prefs1;
        prefs1 = PreferenceManager.getDefaultSharedPreferences(PulsePaymentOptionsActivity.this);
        SharedPreferences.Editor prefEditor = prefs1.edit();
        prefEditor.putString("savedValue", memberPicker.getSelectedItem().toString());
        prefEditor.commit();*/

        SharedPreferences prefs = getSharedPreferences("prefs_name", Context.MODE_PRIVATE);
        prefs.edit().putInt("spinner_indx", memberPicker.getSelectedItemPosition()).apply();

        llSubPlan.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

              /*  llSubPlan.setBackgroundResource(R.drawable.gradient_package);
                checkYear.setVisibility(View.VISIBLE);*/


                preferences = getSharedPreferences("PulsePayment", Context.MODE_PRIVATE);
                prefEditor1 = preferences.edit();
               /* prefEditor1.putString("price", tvPriceYear.getText().toString());
                prefEditor1.putString("setup", tvSetupYear.getText().toString());*/

                member_type = "yearly";
                itemSelected = true;

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PulsePaymentOptionsActivity.this, PulseDescriptionActivity.class));
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences1 = getSharedPreferences("paymentOptions", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("planName",tvSubPlan.getText().toString());
                editor.putString("subPlan",tvSubPlanPrice.getText().toString());
                editor.putString("setupFee",tvSubPlanSetup.getText().toString());
                editor.commit();

                if (tvSubPlan.getText().toString().contains(prefsLoginData.getString("post_title",""))){
                    Intent i = new Intent(PulsePaymentOptionsActivity.this, BillingInfoActivity.class);
                    startActivity(i);
                }else {

                    if (tvSubPlan.getText().toString().contains("Family") &&
                            Integer.parseInt(prefsLoginData.getString("childCount", "1")) <= 3) {
                        Intent i = new Intent(PulsePaymentOptionsActivity.this, BillingInfoActivity.class);
                        startActivity(i);
                    }else if (tvSubPlan.getText().toString().contains("Spouse") &&
                            Integer.parseInt(prefsLoginData.getString("childCount", "0")) > 1) {

                        if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) > 1){
                            memberDeleteDialog("spouse");
                        }

                        else {
                            Intent i = new Intent(PulsePaymentOptionsActivity.this, BillingInfoActivity.class);
                            startActivity(i);
                        }
                    }else if (tvSubPlan.getText().toString().contains("Single") || prefsLoginData.getString("post_title", "").length() == 0){

                        if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) > 0){
                            memberDeleteDialog("single");
                        }

                        else {
                            Intent i = new Intent(PulsePaymentOptionsActivity.this, BillingInfoActivity.class);
                            startActivity(i);
                        }

                    }else {
                        Intent i = new Intent(PulsePaymentOptionsActivity.this, MyMembersActivity.class);
                        startActivity(i);
                    }
                }
            }
        });

    }

    private void memberDeleteDialog(String member_type) {

        final Dialog customDialog = new Dialog(PulsePaymentOptionsActivity.this);
        customDialog.setCancelable(true);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.alert_delete_members);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
        TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);

        Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
        Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);

        tv_title.setText(getResources().getString(R.string.app_name));
        if (member_type.equalsIgnoreCase("single")) {
            info_heading.setText(R.string.remove_members);
        }else {
            info_heading.setText(R.string.remove_members);
        }
        tv_title.setTypeface(null, Typeface.BOLD);
        tv_title.setTextSize(20f);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write your code here to invoke YES event
                Intent i = new Intent(PulsePaymentOptionsActivity.this, MyMembersActivity.class);
                startActivity(i);
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();


    }

    private void changeDataSelectedItem(int position) {

        singlePrices = UpgradeSubscriptionActivity.stringArrayListHashMap.get(memberPicker.getSelectedItem());
        ArrayList<String> pricelist = new ArrayList<>();
        pricelist.add("Monthly - $"+singlePrices.get(0).getSubscriptionPrice());
        pricelist.add("3 Months - $"+singlePrices.get(1).getSubscriptionPrice());
        pricelist.add("6 Months - $"+singlePrices.get(2).getSubscriptionPrice());
        pricelist.add("12 Months - $"+singlePrices.get(3).getSubscriptionPrice());
        singlePriceAdapter = new ArrayAdapter<String>(PulsePaymentOptionsActivity.this,
                R.layout.spinner_item, R.id.tvspinner, pricelist);

        pricePicker.setAdapter(singlePriceAdapter);
        pricePicker.setSelection(mSelectedPosition);

        pricePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSelectedPosition = position;
                tvSubPlan.setText(singlePrices.get(position).getPostTitle()+" Subscription");
                tvSubPlanPrice.setText("Price - $"+singlePrices.get(position).getSubscriptionPrice());
                tvSubPlanSetup.setText("Plus $"+singlePrices.get(position).getSubscriptionSignUpFee() +" setup fee");

                SharedPreferences sharedPreferences = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("subPackageId",singlePrices.get(position).getID());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void deleteOldMembers(String member_type) {
        recentDbHelper.removeOldMembers(member_type);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        SharedPreferences prefs = getSharedPreferences("prefs_name", Context.MODE_PRIVATE);
        prefs.edit().putInt("spinner_indx", memberPicker.getSelectedItemPosition()).apply();
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
