package com.VURVhealth.vurvhealth;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.althealth.AltHealthScreenActivity;
import com.VURVhealth.vurvhealth.circleView.CircleImageView;
import com.VURVhealth.vurvhealth.circleView.CircleLayout;
import com.VURVhealth.vurvhealth.dental.DentalScreenActivity;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.help.HelpActivity;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.myProfile.MyMembersActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UserDetailDataResPayload;
import com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.vision.VisionScreenActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StartScreenActivity extends SuperAppCompactActivity implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener {
    private String activity = "";
    private Button btn_next;
    private ImageView circleImg;
    protected CircleLayout circleLayout;
    private String firstName;
    protected TextView item_text;
    private String lastName;
    private LinearLayout llCitySearch;
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llVurv;
    private ImageView main_dental_image;
    private ImageView main_medical_image;
    private ImageView main_optical_image;
    private ImageView main_rx_image;
    private ImageView main_telemed_image;
    private ImageView main_telemedicince_image;
    protected TextView selectedTextView;
    public String selected_item = "prescription";
    private TextView tvDental;
    private TextView tvMedical;
    protected TextView tvName;
    private TextView tvOptical;
    private TextView tvRx;
    private TextView tvTelemed;
    private TextView tvTelemedicine;
    private ArrayList<ArrayList<UserDetailDataResPayload>> userInfoResPayload;
    private String vurvId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        String activity = getIntent().getStringExtra("activity");
        if (activity != null && activity.equals("ConfirmPaymentActivity")) {
            thanksDialog();
        }
        circleImg = (ImageView) findViewById(R.id.image);
        btn_next = (Button) findViewById(R.id.btn_next);
        tvName = (TextView) findViewById(R.id.tvName);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        if (checkInternet()) {
            myUserDetailsService();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        GPSTracker gps = new GPSTracker(this);
        SharedPreferences prefsData = getSharedPreferences("VURVProfileDetails", 0);
        firstName = prefsData.getString("firstName", "No name");
        lastName = prefsData.getString("lastName", "No name");
        vurvId = prefsData.getString("vurvId", "No name");
        tvName.setText(getResources().getString(R.string.hi_name) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + firstName + "!");
        circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setOnItemSelectedListener(this);
        circleLayout.setOnItemClickListener(this);
        circleLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                View childV = findViewById(R.id.circle_layout);
                if (childV != null) {
                    int[] l = new int[2];
                    childV.getLocationOnScreen(l);
                    if (new RectF((float) l[0], (float) l[1], (float) (l[0] + childV.getWidth()), (float) (l[1] + childV.getHeight())).contains(event.getX(), event.getY())) {
                        childV.getParent().requestDisallowInterceptTouchEvent(false);
                        childV.onTouchEvent(event);
                        return true;
                    }
                }
                childV.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreenActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreenActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreenActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartScreenActivity.this, FreshdeskMainListActivity.class));
//                startActivity(new Intent(StartScreenActivity.this, HelpActivity.class));
                finish();
            }
        });
        circleImg = (ImageView) findViewById(R.id.circleImg);
        selectedTextView = (TextView) findViewById(R.id.selected_textView);
        item_text = (TextView) findViewById(R.id.item_text);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected_item.equalsIgnoreCase("prescription")) {
                    startActivity(new Intent(StartScreenActivity.this, PrescriptionSearchActivity.class));
                } else if (selected_item.equalsIgnoreCase("medical")) {
                    startActivity(new Intent(StartScreenActivity.this, MedicalScreenActivity.class));
                } else if (selected_item.equalsIgnoreCase("dental")) {
                    startActivity(new Intent(StartScreenActivity.this, DentalScreenActivity.class));
                } else if (selected_item.equalsIgnoreCase("telemed")) {
                    startActivity(new Intent(StartScreenActivity.this, AltHealthScreenActivity.class));
                } else if (selected_item.equalsIgnoreCase("vision")) {
                    startActivity(new Intent(StartScreenActivity.this, VisionScreenActivity.class));
                }else if (selected_item.equalsIgnoreCase("telemedicine")){
                    //Toast.makeText(StartScreenActivity.this, "click tele medicine icon", Toast.LENGTH_SHORT).show();
                    /*String packageName = "com.dialcare.app";
                    Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                    if(intent == null) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packageName));
                    }*/

                    startActivity(new Intent(StartScreenActivity.this, TeleMedicineActivity.class));
                    /*Uri uri = Uri.parse("https://member.dialcare.com/login"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);*/
                }
            }
        });
        View view = circleLayout.getSelectedItem();
        if (view instanceof CircleImageView) {
            String name = ((CircleImageView) view).getName();
        }
        selectedTextView.setText(getResources().getString(R.string.prescription_caps));
        item_text.setText(R.string.prescription_txt);
    }

    public void onItemSelected(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        int i;
        switch (linearLayout.getChildAt(0).getId()) {
            case R.id.main_rx_image:
                selected_item = "prescription";
                main_rx_image = (ImageView) linearLayout.findViewById(R.id.main_rx_image);
                main_rx_image.setImageResource(R.drawable.selected_ic_rx);
                tvRx = (TextView) linearLayout.findViewById(R.id.tvRx);
                tvRx.setVisibility(View.GONE);
                selectedTextView.setText(getResources().getString(R.string.prescription_caps));
                selectedTextView.setVisibility(View.VISIBLE);
                item_text.setText(R.string.prescription_txt);
                item_text.setVisibility(View.VISIBLE);
                if (circleLayout != null) {
                    for (i = 0; i < circleLayout.getChildCount(); i++) {
                        main_medical_image = (ImageView) circleLayout.getChildAt(1).findViewById(R.id.main_medical_image);
                        main_medical_image.setImageResource(R.drawable.category_ic_medical);
                        tvMedical = (TextView) circleLayout.getChildAt(1).findViewById(R.id.tvMedical);
                        tvMedical.setVisibility(View.VISIBLE);
                        main_dental_image = (ImageView) circleLayout.getChildAt(2).findViewById(R.id.main_dental_image);
                        main_dental_image.setImageResource(R.drawable.category_ic_dental);
                        tvDental = (TextView) circleLayout.getChildAt(2).findViewById(R.id.tvDental);
                        tvDental.setVisibility(View.VISIBLE);
                        main_telemed_image = (ImageView) circleLayout.getChildAt(3).findViewById(R.id.main_telemed_image);
                        main_telemed_image.setImageResource(R.drawable.category_ic_alt);
                        tvTelemed = (TextView) circleLayout.getChildAt(3).findViewById(R.id.tvTelemed);
                        tvTelemed.setVisibility(View.VISIBLE);
                        main_optical_image = (ImageView) circleLayout.getChildAt(4).findViewById(R.id.main_optical_image);
                        main_optical_image.setImageResource(R.drawable.category_ic_vision);
                        tvOptical = (TextView) circleLayout.getChildAt(4).findViewById(R.id.tvOptical);
                        tvOptical.setVisibility(View.VISIBLE);
                        main_telemedicince_image = (ImageView) circleLayout.getChildAt(5).findViewById(R.id.main_telemedicince_image);
                        main_telemedicince_image.setImageResource(R.drawable.telemednew);
                        tvTelemedicine = (TextView) circleLayout.getChildAt(5).findViewById(R.id.tvTelemedicine);
                        tvTelemedicine.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                return;
            case R.id.main_medical_image:
                selected_item = "medical";
                main_medical_image = (ImageView) linearLayout.findViewById(R.id.main_medical_image);
                main_medical_image.setImageResource(R.drawable.selected_ic_doctors);
                tvMedical = (TextView) linearLayout.findViewById(R.id.tvMedical);
                tvMedical.setVisibility(View.GONE);
                selectedTextView.setText(getResources().getString(R.string.medical_caps));
                selectedTextView.setVisibility(View.VISIBLE);
                item_text.setText(R.string.medical_txt);
                item_text.setVisibility(View.VISIBLE);
                if (circleLayout != null) {
                    for (i = 0; i < circleLayout.getChildCount(); i++) {
                        main_rx_image = (ImageView) circleLayout.getChildAt(0).findViewById(R.id.main_rx_image);
                        main_rx_image.setImageResource(R.drawable.category_ic_rx);
                        tvRx = (TextView) circleLayout.getChildAt(0).findViewById(R.id.tvRx);
                        tvRx.setVisibility(View.VISIBLE);
                        main_dental_image = (ImageView) circleLayout.getChildAt(2).findViewById(R.id.main_dental_image);
                        main_dental_image.setImageResource(R.drawable.category_ic_dental);
                        tvDental = (TextView) circleLayout.getChildAt(2).findViewById(R.id.tvDental);
                        tvDental.setVisibility(View.VISIBLE);
                        main_telemed_image = (ImageView) circleLayout.getChildAt(3).findViewById(R.id.main_telemed_image);
                        main_telemed_image.setImageResource(R.drawable.category_ic_alt);
                        tvTelemed = (TextView) circleLayout.getChildAt(3).findViewById(R.id.tvTelemed);
                        tvTelemed.setVisibility(View.VISIBLE);
                        main_optical_image = (ImageView) circleLayout.getChildAt(4).findViewById(R.id.main_optical_image);
                        main_optical_image.setImageResource(R.drawable.category_ic_vision);
                        tvOptical = (TextView) circleLayout.getChildAt(4).findViewById(R.id.tvOptical);
                        tvOptical.setVisibility(View.VISIBLE);
                        main_telemedicince_image = (ImageView) circleLayout.getChildAt(5).findViewById(R.id.main_telemedicince_image);
                        main_telemedicince_image.setImageResource(R.drawable.telemednew);
                        tvTelemedicine = (TextView) circleLayout.getChildAt(5).findViewById(R.id.tvTelemedicine);
                        tvTelemedicine.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                return;
            case R.id.main_dental_image:
                selected_item = "dental";
                main_dental_image = (ImageView) linearLayout.findViewById(R.id.main_dental_image);
                main_dental_image.setImageResource(R.drawable.selected_ic_dental);
                tvDental = (TextView) linearLayout.findViewById(R.id.tvDental);
                tvDental.setVisibility(View.GONE);
                selectedTextView.setText(getResources().getString(R.string.dental_caps));
                selectedTextView.setVisibility(View.VISIBLE);
                item_text.setText(R.string.dental_txt);
                item_text.setVisibility(View.VISIBLE);
                if (circleLayout != null) {
                    for (i = 0; i < circleLayout.getChildCount(); i++) {
                        main_rx_image = (ImageView) circleLayout.getChildAt(0).findViewById(R.id.main_rx_image);
                        main_rx_image.setImageResource(R.drawable.category_ic_rx);
                        tvRx = (TextView) circleLayout.getChildAt(0).findViewById(R.id.tvRx);
                        tvRx.setVisibility(View.VISIBLE);
                        main_medical_image = (ImageView) circleLayout.getChildAt(1).findViewById(R.id.main_medical_image);
                        main_medical_image.setImageResource(R.drawable.category_ic_medical);
                        tvMedical = (TextView) circleLayout.getChildAt(1).findViewById(R.id.tvMedical);
                        tvMedical.setVisibility(View.VISIBLE);
                        main_telemed_image = (ImageView) circleLayout.getChildAt(3).findViewById(R.id.main_telemed_image);
                        main_telemed_image.setImageResource(R.drawable.category_ic_alt);
                        tvTelemed = (TextView) circleLayout.getChildAt(3).findViewById(R.id.tvTelemed);
                        tvTelemed.setVisibility(View.VISIBLE);
                        main_optical_image = (ImageView) circleLayout.getChildAt(4).findViewById(R.id.main_optical_image);
                        main_optical_image.setImageResource(R.drawable.category_ic_vision);
                        tvOptical = (TextView) circleLayout.getChildAt(4).findViewById(R.id.tvOptical);
                        tvOptical.setVisibility(View.VISIBLE);
                        main_telemedicince_image = (ImageView) circleLayout.getChildAt(5).findViewById(R.id.main_telemedicince_image);
                        main_telemedicince_image.setImageResource(R.drawable.telemednew);
                        tvTelemedicine = (TextView) circleLayout.getChildAt(5).findViewById(R.id.tvTelemedicine);
                        tvTelemedicine.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                return;
            case R.id.main_telemed_image:
                selected_item = "telemed";
                main_telemed_image = (ImageView) linearLayout.findViewById(R.id.main_telemed_image);
                main_telemed_image.setImageResource(R.drawable.selected_ic_alt);
                tvTelemed = (TextView) linearLayout.findViewById(R.id.tvTelemed);
                tvTelemed.setVisibility(View.GONE);
                selectedTextView.setText(getResources().getString(R.string.alt_caps));
                selectedTextView.setVisibility(View.VISIBLE);
                item_text.setText(R.string.telemed_txt1);
                item_text.setVisibility(View.VISIBLE);
                if (circleLayout != null) {
                    for (i = 0; i < circleLayout.getChildCount(); i++) {
                        main_rx_image = (ImageView) circleLayout.getChildAt(0).findViewById(R.id.main_rx_image);
                        main_rx_image.setImageResource(R.drawable.category_ic_rx);
                        tvRx = (TextView) circleLayout.getChildAt(0).findViewById(R.id.tvRx);
                        tvRx.setVisibility(View.VISIBLE);
                        main_medical_image = (ImageView) circleLayout.getChildAt(1).findViewById(R.id.main_medical_image);
                        main_medical_image.setImageResource(R.drawable.category_ic_medical);
                        tvMedical = (TextView) circleLayout.getChildAt(1).findViewById(R.id.tvMedical);
                        tvMedical.setVisibility(View.VISIBLE);
                        main_dental_image = (ImageView) circleLayout.getChildAt(2).findViewById(R.id.main_dental_image);
                        main_dental_image.setImageResource(R.drawable.category_ic_dental);
                        tvDental = (TextView) circleLayout.getChildAt(2).findViewById(R.id.tvDental);
                        tvDental.setVisibility(View.VISIBLE);
                        main_optical_image = (ImageView) circleLayout.getChildAt(4).findViewById(R.id.main_optical_image);
                        main_optical_image.setImageResource(R.drawable.category_ic_vision);
                        tvOptical = (TextView) circleLayout.getChildAt(4).findViewById(R.id.tvOptical);
                        tvOptical.setVisibility(View.VISIBLE);
                        main_telemedicince_image = (ImageView) circleLayout.getChildAt(5).findViewById(R.id.main_telemedicince_image);
                        main_telemedicince_image.setImageResource(R.drawable.telemednew);
                        tvTelemedicine = (TextView) circleLayout.getChildAt(5).findViewById(R.id.tvTelemedicine);
                        tvTelemedicine.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                return;
            case R.id.main_optical_image:
                selected_item = "vision";
                main_optical_image = (ImageView) linearLayout.findViewById(R.id.main_optical_image);
                main_optical_image.setImageResource(R.drawable.selected_ic_vision);
                tvOptical = (TextView) linearLayout.findViewById(R.id.tvOptical);
                tvOptical.setVisibility(View.GONE);
                selectedTextView.setText(getResources().getString(R.string.vision_caps));
                selectedTextView.setVisibility(View.VISIBLE);
                item_text.setText(R.string.vision_txt);
                item_text.setVisibility(View.VISIBLE);
                if (circleLayout != null) {
                    for (i = 0; i < circleLayout.getChildCount(); i++) {
                        main_rx_image = (ImageView) circleLayout.getChildAt(0).findViewById(R.id.main_rx_image);
                        main_rx_image.setImageResource(R.drawable.category_ic_rx);
                        tvRx = (TextView) circleLayout.getChildAt(0).findViewById(R.id.tvRx);
                        tvRx.setVisibility(View.VISIBLE);
                        main_medical_image = (ImageView) circleLayout.getChildAt(1).findViewById(R.id.main_medical_image);
                        main_medical_image.setImageResource(R.drawable.category_ic_medical);
                        tvMedical = (TextView) circleLayout.getChildAt(1).findViewById(R.id.tvMedical);
                        tvMedical.setVisibility(View.VISIBLE);
                        main_dental_image = (ImageView) circleLayout.getChildAt(2).findViewById(R.id.main_dental_image);
                        main_dental_image.setImageResource(R.drawable.category_ic_dental);
                        tvDental = (TextView) circleLayout.getChildAt(2).findViewById(R.id.tvDental);
                        tvDental.setVisibility(View.VISIBLE);
                        main_telemed_image = (ImageView) circleLayout.getChildAt(3).findViewById(R.id.main_telemed_image);
                        main_telemed_image.setImageResource(R.drawable.category_ic_alt);
                        tvTelemed = (TextView) circleLayout.getChildAt(3).findViewById(R.id.tvTelemed);
                        tvTelemed.setVisibility(View.VISIBLE);
                        main_telemedicince_image = (ImageView) circleLayout.getChildAt(5).findViewById(R.id.main_telemedicince_image);
                        main_telemedicince_image.setImageResource(R.drawable.telemednew);
                        tvTelemedicine = (TextView) circleLayout.getChildAt(5).findViewById(R.id.tvTelemedicine);
                        tvTelemedicine.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                return;
            case R.id.main_telemedicince_image:
                selected_item = "telemedicine";
                main_telemedicince_image = (ImageView) linearLayout.findViewById(R.id.main_telemedicince_image);
                main_telemedicince_image.setImageResource(R.drawable.telemedblue1);
                tvTelemedicine = (TextView) linearLayout.findViewById(R.id.tvTelemedicine);
                tvTelemedicine.setVisibility(View.GONE);
                selectedTextView.setText(getResources().getString(R.string.telemedecine2));
                selectedTextView.setVisibility(View.VISIBLE);
                item_text.setText(R.string.telemedecine1_txt);
                item_text.setVisibility(View.VISIBLE);
                if (circleLayout != null) {
                    for (i = 0; i < circleLayout.getChildCount(); i++) {
                        main_rx_image = (ImageView) circleLayout.getChildAt(0).findViewById(R.id.main_rx_image);
                        main_rx_image.setImageResource(R.drawable.category_ic_rx);
                        tvRx = (TextView) circleLayout.getChildAt(0).findViewById(R.id.tvRx);
                        tvRx.setVisibility(View.VISIBLE);
                        main_medical_image = (ImageView) circleLayout.getChildAt(1).findViewById(R.id.main_medical_image);
                        main_medical_image.setImageResource(R.drawable.category_ic_medical);
                        tvMedical = (TextView) circleLayout.getChildAt(1).findViewById(R.id.tvMedical);
                        tvMedical.setVisibility(View.VISIBLE);
                        main_dental_image = (ImageView) circleLayout.getChildAt(2).findViewById(R.id.main_dental_image);
                        main_dental_image.setImageResource(R.drawable.category_ic_dental);
                        tvDental = (TextView) circleLayout.getChildAt(2).findViewById(R.id.tvDental);
                        tvDental.setVisibility(View.VISIBLE);
                        main_telemed_image = (ImageView) circleLayout.getChildAt(3).findViewById(R.id.main_telemed_image);
                        main_telemed_image.setImageResource(R.drawable.category_ic_alt);
                        tvTelemed = (TextView) circleLayout.getChildAt(3).findViewById(R.id.tvTelemed);
                        tvTelemed.setVisibility(View.VISIBLE);
                        main_optical_image = (ImageView) circleLayout.getChildAt(4).findViewById(R.id.main_optical_image);
                        main_optical_image.setImageResource(R.drawable.category_ic_vision);
                        tvOptical = (TextView) circleLayout.getChildAt(4).findViewById(R.id.tvOptical);
                        tvOptical.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onItemClick(View view) {
        if (view instanceof CircleImageView) {
            String name = ((CircleImageView) view).getName();
        }
        switch (view.getId()) {
        }
    }

    public void onAddClick(View view) {
        CircleImageView newMenu = new CircleImageView(StartScreenActivity.this);
        newMenu.setBackgroundResource(R.drawable.selected_circle);
        newMenu.setImageResource(R.drawable.selected_ic_dental);
        circleLayout.addView(newMenu);
    }

    public void onRemoveClick(View view) {
        if (circleLayout.getChildCount() > 0) {
            circleLayout.removeViewAt(circleLayout.getChildCount() - 1);
        }
    }

    private void myUserDetailsService() {
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(StartScreenActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> myMemberListPayloadArrayList = new ArrayList();
        MyMemberListPayload myMemberListPayload = new MyMemberListPayload();
        myMemberListPayload.setUserId(String.valueOf(prefsLoginData.getInt("userId", 1)));
        myMemberListPayloadArrayList.add(myMemberListPayload);
        apiService.getUserDetailData(myMemberListPayloadArrayList).enqueue(new Callback<ArrayList<ArrayList<UserDetailDataResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<UserDetailDataResPayload>>> call, Response<ArrayList<ArrayList<UserDetailDataResPayload>>> response) {
                if (response.isSuccessful()) {
                    userInfoResPayload = (ArrayList) response.body();
                    if (userInfoResPayload.size() != 0) {
                        String str;
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
                        loginEditor.putString("subPackageId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubPackageId());
                        loginEditor.putString("post_title", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPostTitle());
                        loginEditor.putString("orderId", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getOrderId());
                        loginEditor.putString("packageName", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getName());
                        loginEditor.putString(Param.PRICE, ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getPrice());
                        loginEditor.putString("childCount", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getChild_count());
                        loginEditor.putString("search_type", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSearch_type());
                        if (((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubscriptionEndDate() != null) {
                            loginEditor.putString("subscription_end_date", ((UserDetailDataResPayload) ((ArrayList) userInfoResPayload.get(0)).get(0)).getSubscriptionEndDate());
                        }
                        loginEditor.putString("logout", "");
                        loginEditor.commit();
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

    public void onStart() {
        super.onStart();
        client.connect();
    }

    private void welComeDialog() {
        final Dialog customDialog = new Dialog(StartScreenActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.confirmation_popup);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        Button btn_ok = (Button) customDialog.findViewById(R.id.okBtn);
        TextView name = (TextView) customDialog.findViewById(R.id.name);
        TextView vurvId = (TextView) customDialog.findViewById(R.id.vurv_id);
        ((TextView) customDialog.findViewById(R.id.tvName)).setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + firstName + "!");
        name.setText(firstName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastName);
        // vurvId.setText("VURV ID: " + vurvId);



        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvId.getText().toString().substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvId.getText().toString().substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvId.getText().toString().substring(7, 11));
            vurvId.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            vurvId.setText("VURV ID: " + vurvId);
        }


        Application_holder.userName = firstName;
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        btn_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
                Intent i = new Intent(StartScreenActivity.this, StartScreenActivity.class);
                i.putExtra("firstName", firstName);
                i.putExtra("activity", "VURVHealthIDCreateActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    private void thanksDialog() {
        final Dialog customDialog = new Dialog(StartScreenActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(1);
        customDialog.setContentView(R.layout.confirmation_popup);
        LayoutParams lp = new LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -2;
        lp.gravity = 17;
        Button btn_ok = (Button) customDialog.findViewById(R.id.okBtn);
        Button addMemberBtn = (Button) customDialog.findViewById(R.id.addMemberBtn);
        TextView name = (TextView) customDialog.findViewById(R.id.name);
        TextView vurv_id = (TextView) customDialog.findViewById(R.id.vurv_id);
        TextView tvWelcome = (TextView) customDialog.findViewById(R.id.tvWelcome);
        TextView tvMemberId = (TextView) customDialog.findViewById(R.id.tvMemberId);
        TextView expires = (TextView) customDialog.findViewById(R.id.expires);
        TextView tvpresentId = (TextView) customDialog.findViewById(R.id.tvpresentId);
        FrameLayout cardImg = (FrameLayout) customDialog.findViewById(R.id.cardImg);
        ((TextView) customDialog.findViewById(R.id.tvName)).setText(prefsLoginData.getString("firstName", "No name") + "!");
        vurv_id.setText("VURV ID: " + prefsLoginData.getString("vurvId", "926View.GONE"));
        tvWelcome.setText("Thanks, ");
        expires.setVisibility(View.VISIBLE);
        expires.setText("Expires: " + prefsLoginData.getString("subscription_end_date", "12/12/2017"));
        name.setText(prefsLoginData.getString("fullName", ""));
        tvMemberId.setText("Your upgrade is complete.");
        tvpresentId.setText("Present your VURV ID at a participating health provider to receive your discount.");
        SharedPreferences preferences = getSharedPreferences("paymentOptions", 0);
        if (preferences.getString("planName", "").contains("Pulse")) {
            cardImg.setBackgroundResource(R.drawable.package_card_pulse);
        } else if (preferences.getString("planName", "").contains("Care")) {
            cardImg.setBackgroundResource(R.drawable.package_card_care);
        } else if (preferences.getString("planName", "").contains("360")) {
            cardImg.setBackgroundResource(R.drawable.package_card_360);
        }
        if (preferences.getString("planName", "").contains("Spouse") && Integer.parseInt(prefsLoginData.getString("childCount", "1")) < 1) {
            addMemberBtn.setVisibility(View.VISIBLE);
        } else if (!preferences.getString("planName", "").contains("Family") || Integer.parseInt(prefsLoginData.getString("childCount", "1")) >= 3) {
            addMemberBtn.setVisibility(View.GONE);
        } else {
            addMemberBtn.setVisibility(View.VISIBLE);
        }
        addMemberBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myUserDetailsService();
                Intent intent = new Intent(StartScreenActivity.this, MyMembersActivity.class);
                intent.putExtra("activity", "PulsePaymentOptionsActivity");
                startActivity(intent);
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        btn_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }

    public void onStop() {
        super.onStop();
        client.disconnect();
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
}
