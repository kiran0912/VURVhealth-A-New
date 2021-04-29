package com.VURVhealth.vurvhealth.vurvidpackages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.myProfile.MyMembersActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.CurrentPackageResPayload;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.help.HelpActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeAltHealthFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeDentalFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeMedicalFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeRxFlipActivity;
import com.VURVhealth.vurvhealth.upgrade.UpgradeVisionFlipActivity;
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.VURVhealth.vurvhealth.viewpagerindicator.CirclePageIndicator;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by yqlabs on 18/1/17.
 */

public class VurvPackageActivity extends SuperAppCompactActivity {
    private static int currentPage = 0;
    private PagerAdapter adapter;
    private Button button;
    private ArrayList<CurrentPackageResPayload> currentPackageResPayload;
    private String firstName;
    private TextView heading1;
    private TextView heading2;
    String[] headingArray1;
    String[] headingArray2;
    private String lastName;
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llSearch;
    private PagerContainer mContainer;
    private String package_id;
    private ArrayList<PackageData> packagesList;
    private ViewPager pager;
    SharedPreferences prefsData;
    private TextView primaryName;
    private String search_type, packageName;
    private TextView tvVurvId;
    private int userId;
    private UserSharedPreferences userSharedPreferences;
    private int viewpagerPosition;
    private String vurvId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurvid_packages_screen);
        initializeviews();
        userSharedPreferences = UserSharedPreferences.getInstance(VurvPackageActivity.this);
    }

    private void initializeviews() {
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        vurvId = prefsData.getString("vurvId", "0");
        firstName = prefsData.getString("firstName", "0");
        lastName = prefsData.getString("lastName", "0");
        package_id = prefsData.getString("packageId", "");
        search_type = prefsData.getString("search_type", "");
        packageName = prefsData.getString("packageName", "");
        userId = prefsData.getInt("userId", 1);
        mContainer = (PagerContainer) findViewById(R.id.pager_container);
        button = (Button) findViewById(R.id.openAndStartButton);
        heading1 = (TextView) findViewById(R.id.heading1);
        heading2 = (TextView) findViewById(R.id.heading2);
        tvVurvId = (TextView) findViewById(R.id.vurvId);
        primaryName = (TextView) findViewById(R.id.primaryName);


        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvId.substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvId.substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvId.substring(7, 11));
            tvVurvId.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            tvVurvId.setText("VURV ID: " + vurvId);
        }

        // tvVurvId.setText("VURV ID : " + vurvId);
        primaryName.setText(firstName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastName);
        pager = mContainer.getViewPager();
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        heading1.setText(fromHtml(getResources().getString(R.string.prescription_id1)));
        heading2.setText(fromHtml(getResources().getString(R.string.prescrption_id2)));

        prepareDataForPackage();

        adapter = new MyPagerAdapter(VurvPackageActivity.this, packagesList);
        pager.setAdapter(adapter);
        ((CirclePageIndicator) findViewById(R.id.indicator)).setViewPager(pager);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setPageMargin(15);
        pager.setClipChildren(false);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            Boolean first = Boolean.valueOf(true);

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (first.booleanValue() && positionOffset == 0.0f && positionOffsetPixels == 0) {
                    onPageSelected(0);
                    first = Boolean.valueOf(false);
                }
            }

            @Override
            public void onPageSelected(final int position) {
                button.setVisibility(View.VISIBLE);
                if (packagesList.get(position).isActiveStatus()) {
                    button.setText(getResources().getString(R.string.open));
                    heading1.setTextSize(14.0f);
                    //heading1.setGravity(3);
                    //heading2.setGravity(3);
                    heading2.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    heading1.setTypeface(null, Typeface.NORMAL);
                    heading1.setText(fromHtml(((PackageData) packagesList.get(position)).getHeading()));
//                    heading2.setText(fromHtml(((PackageData) packagesList.get(position)).getDescription()));
                    customTextView1(heading2, ((PackageData) packagesList.get(position)).getDescription(), ((PackageData) packagesList.get(position)).getCardName());


                    //customTextView(heading2);
                } else {
                    //heading1.setTypeface(null, Typeface.BOLD);
                    heading1.setTextSize(14.0f);
                    heading1.setText(fromHtml(((PackageData) packagesList.get(position)).getHeading()));
                    //heading1.setGravity(17);
                    //heading2.setGravity(17);
                    heading2.setVisibility(View.VISIBLE);
//                    heading2.setText(fromHtml(((PackageData) packagesList.get(position)).getDescription()));
                    customTextView1(heading2, ((PackageData) packagesList.get(position)).getDescription(), ((PackageData) packagesList.get(position)).getCardName());

                    //   customTextView(heading2);

                    button.setVisibility(View.INVISIBLE);
                }
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Prescriptions")) {
                            userSharedPreferences.SaveData("packageName", "Rx");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToRXActivity();
                            } else {
                                moveToNextActivity();
                            }*/
                            moveToRXActivity();
                        } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Medical")) {
                            userSharedPreferences.SaveData("packageName", "medical");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToMedicalActivity();
                            } else {
                                moveToNextActivity();
                            }*/
                            moveToMedicalActivity();
                        } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Dental")) {
                            userSharedPreferences.SaveData("packageName", "dental");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToDentalActivity();
                            } else {
                                moveToNextActivity();
                            }*/
                            moveToDentalActivity();
                        } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Vision")) {
                            userSharedPreferences.SaveData("packageName", "vision");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToVisionActivity();
                            } else {
                                moveToNextActivity();
                            }*/
                            moveToVisionActivity();
                        } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("AltHealth")) {
                            userSharedPreferences.SaveData("packageName", "AltHealth");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToAltHealthActvity();
                            } else {
                                moveToNextActivity();
                            }*/
                            moveToAltHealthActvity();
                        } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("TeleMedicine")) {
                            userSharedPreferences.SaveData("packageName", "TeleMedicine");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToAltHealthActvity();
                            } else {
                                moveToNextActivity();
                            }*/
                            moveToAltHealthActvity();
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VurvPackageActivity.this, StartScreenActivity.class));
                finish();
            }
        });
        llSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VurvPackageActivity.this, NoSavedItemActivity.class));
                finish();
            }
        });
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VurvPackageActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VurvPackageActivity.this, FreshdeskMainListActivity.class));
//                startActivity(new Intent(VurvPackageActivity.this, HelpActivity.class));
                finish();
            }
        });
    }


    public static Spanned fromHtml(String html) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    /*srikanth  images replace*/

    private void prepareDataForPackage() {
        PackageData packageData;
        packagesList = new ArrayList();
        if (search_type.contains("Prescriptions")) {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_rx_new);
            packageData.setCardName("Prescriptions");
            packageData.setHeading(getResources().getString(R.string.prescription_id1));
            packageData.setDescription(getResources().getString(R.string.prescrption_id2));
            packageData.setActiveStatus(true);
//            heading2 = (TextView) findViewById(R.id.heading2);
            // customTextView1(heading2,getResources().getString(R.string.prescrption_id2));
            packagesList.add(packageData);
        } else {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_rx_new);
            packageData.setHeading(getResources().getString(R.string.prescription_id1));
            packageData.setDescription(getResources().getString(R.string.prescrption_id2));
//            heading2 = (TextView) findViewById(R.id.heading2);
//            customTextView1(heading2,getResources().getString(R.string.prescrption_id2));

            packageData.setCardName("Prescriptions");
            packageData.setActiveStatus(false);
            packagesList.add(packageData);
        }
        if (search_type.contains("Doctors")) {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_medical_new);
            packageData.setCardName("Medical");
            packageData.setHeading(getResources().getString(R.string.dental_txt_card));
            packageData.setDescription(getResources().getString(R.string.dental_txt_card2));
            packageData.setActiveStatus(true);
            packagesList.add(packageData);
        } else {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_noplan_medical);
            packageData.setHeading(getResources().getString(R.string.medical_no_plan));
            packageData.setDescription(getResources().getString(R.string.medical_txt));
            packageData.setCardName("Medical");
            packageData.setActiveStatus(false);
            packagesList.add(packageData);
        }
        if (search_type.contains("Dental")) {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_dental_new);
            packageData.setHeading(getResources().getString(R.string.dental_vurv_txt));
            packageData.setDescription(getResources().getString(R.string.dental_vurv_txt1));
            packageData.setCardName("Dental");
            packageData.setActiveStatus(true);
            packagesList.add(packageData);
        } else {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_noplan_dental);
            packageData.setHeading(getResources().getString(R.string.dental_plan));
            packageData.setDescription(getResources().getString(R.string.dental_txt1));
            packageData.setCardName("Dental");
            packageData.setActiveStatus(false);
            packagesList.add(packageData);
        }
        if (search_type.contains("Vision")) {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_vision_new);
            packageData.setHeading(getResources().getString(R.string.vision_vurv_txt));
            packageData.setDescription(getResources().getString(R.string.vision_vurv_text1));
            packageData.setCardName("Vision");
            packageData.setActiveStatus(true);
            packagesList.add(packageData);
        } else {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_noplan_vision);
            packageData.setHeading(getResources().getString(R.string.vision_plan));
            packageData.setDescription(getResources().getString(R.string.vision_txt1));
            packageData.setCardName("Vision");
            packageData.setActiveStatus(false);
            packagesList.add(packageData);
        }
        if (search_type.contains("Alt Health")) {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_alt_new);
            packageData.setHeading(getResources().getString(R.string.alt_vurv_txt));
            packageData.setDescription(getResources().getString(R.string.alt_vurv_txt1));
            packageData.setCardName("AltHealth");
            packageData.setActiveStatus(true);
            packagesList.add(packageData);
        } else {
            packageData = new PackageData();
            packageData.setImages(R.drawable.card_noplan_alt);
            packageData.setHeading(getResources().getString(R.string.alt_plan));
            packageData.setDescription(getResources().getString(R.string.medical_txt));
            packageData.setCardName("AltHealth");
            packageData.setActiveStatus(false);
            packagesList.add(packageData);
        }
        if (search_type.contains("TeleMedicine")) {
            packageData = new PackageData();
            packageData.setImages(R.drawable.small_tele_medican);
            packageData.setHeading(getResources().getString(R.string.telemedican_txt));
            packageData.setDescription(getResources().getString(R.string.telemedican_txt1));
            packageData.setCardName("TeleMedicine");
            packageData.setActiveStatus(true);
            packagesList.add(packageData);
        } else {
            packageData = new PackageData();
            packageData.setImages(R.drawable.small_tele_medican);
            packageData.setHeading(getResources().getString(R.string.telemedican_txt));
            packageData.setDescription(getResources().getString(R.string.telemedican_txt1));
            packageData.setCardName("TeleMedicine");
            packageData.setActiveStatus(false);
            packagesList.add(packageData);
        }

        Collections.sort(packagesList, new Comparator<PackageData>() {
            @Override
            public int compare(PackageData packageData1, PackageData packageData2) {
                if (Build.VERSION.SDK_INT >= 19) {
                    return Boolean.compare(packageData2.isActiveStatus(), packageData1.isActiveStatus());
                }
                boolean b1 = packageData2.isActiveStatus();
                if (b1 != packageData2.isActiveStatus()) {
                    return b1 ? -1 : 1;
                } else {
                    return 0;
                }
            }
        });
    }

    private void moveToMedicalActivity() {
        Intent intent = new Intent(VurvPackageActivity.this, UpgradeMedicalFlipActivity.class);
        intent.putExtra("activity", "VurvPackageActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void moveToDentalActivity() {
        Intent intent = new Intent(VurvPackageActivity.this, UpgradeDentalFlipActivity.class);
        intent.putExtra("activity", "VurvPackageActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void moveToVisionActivity() {
        Intent intent = new Intent(VurvPackageActivity.this, UpgradeVisionFlipActivity.class);
        intent.putExtra("activity", "VurvPackageActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void moveToAltHealthActvity() {
        Intent intent = new Intent(VurvPackageActivity.this, UpgradeAltHealthFlipActivity.class);
        intent.putExtra("activity", "VurvPackageActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void moveToRXActivity() {
        Intent intent = new Intent(VurvPackageActivity.this, UpgradeRxFlipActivity.class);
        intent.putExtra("activity", "PrescriptionBannerActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void moveToNextActivity() {
        Intent intent = new Intent(VurvPackageActivity.this, MyMembersActivity.class);
        intent.putExtra("activity", "VurvPackageActivity");
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private class MyPagerAdapter extends PagerAdapter {
        private Context mContext;
        LayoutInflater mLayoutInflater;
        private ArrayList<PackageData> packagesList;

        public MyPagerAdapter(Context context, ArrayList<PackageData> packagesList) {
            this.mContext = context;
            this.mLayoutInflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            this.packagesList = packagesList;
        }

        public int getCount() {
            return packagesList.size();
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
            FrameLayout cardImg = (FrameLayout) itemView.findViewById(R.id.cardImg);
            TextView name = (TextView) itemView.findViewById(R.id.name);
            TextView vurv_id = (TextView) itemView.findViewById(R.id.vurv_id);
            TextView plan = (TextView) itemView.findViewById(R.id.plan);
            TextView expires = (TextView) itemView.findViewById(R.id.expires);
            TextView rxBinNum = (TextView) itemView.findViewById(R.id.rxBinNum);
            TextView rxPcnNum = (TextView) itemView.findViewById(R.id.rxPcnNum);
            TextView rxGRP = (TextView) itemView.findViewById(R.id.rxGRP);
            TextView raMemID = (TextView) itemView.findViewById(R.id.raMemID);
            TextView tvInsure = (TextView) itemView.findViewById(R.id.tvInsure);
            TextView tvCardProvider = (TextView) itemView.findViewById(R.id.tvCardProvider);
            name.setText(firstName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastName);

            cardImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Prescriptions")) {
                        userSharedPreferences.SaveData("packageName", "Rx");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToRXActivity();
                            } else {
                                moveToNextActivity();
                            }*/

                        if (packagesList.get(position).isActiveStatus()) {
                            moveToRXActivity();
                        }

                    } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Medical")) {
                        userSharedPreferences.SaveData("packageName", "medical");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToMedicalActivity();
                            } else {
                                moveToNextActivity();
                            }*/

                        if (packagesList.get(position).isActiveStatus()) {
                            moveToMedicalActivity();
                        }
                    } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Dental")) {
                        userSharedPreferences.SaveData("packageName", "dental");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToDentalActivity();
                            } else {
                                moveToNextActivity();
                            }*/

                        if (packagesList.get(position).isActiveStatus()) {
                            moveToDentalActivity();
                        }
                    } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("Vision")) {
                        userSharedPreferences.SaveData("packageName", "vision");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToVisionActivity();
                            } else {
                                moveToNextActivity();
                            }*/

                        if (packagesList.get(position).isActiveStatus()) {
                            moveToVisionActivity();
                        }

                    } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("AltHealth")) {
                        userSharedPreferences.SaveData("packageName", "AltHealth");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToAltHealthActvity();
                            } else {
                                moveToNextActivity();
                            }*/

                        if (packagesList.get(position).isActiveStatus()) {
                            moveToAltHealthActvity();
                        }
                    } else if (((PackageData) packagesList.get(position)).getCardName().equalsIgnoreCase("TeleMedicine")) {
                        userSharedPreferences.SaveData("packageName", "TeleMedicine");
                            /*if (Integer.parseInt(prefsLoginData.getString("childCount", "0")) == 0) {
                                moveToAltHealthActvity();
                            } else {
                                moveToNextActivity();
                            }*/
//                        moveToAltHealthActvity();
                        Uri uri = Uri.parse("https://member.dialcare.com/login"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            });


            /*srikanth*/
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(vurvId.substring(0, 4));
                stringBuilder.append("-");
                stringBuilder.append(vurvId.substring(4, 7));
                stringBuilder.append("-");
                stringBuilder.append(vurvId.substring(7, 11));
                vurv_id.setText("VURV ID: " + stringBuilder);
            } catch (ArrayIndexOutOfBoundsException e2) {
                vurv_id.setText("VURV ID: " + vurvId);
            }

//            vurv_id.setText("VURV ID: " + vurvId);
            tvCardProvider.setVisibility(View.GONE);
            String dobFormat = null;
            try {
                dobFormat = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(prefsLoginData.getString("subscription_end_date", "12/12/2017")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            PackageData packageData = packagesList.get(position);
            if (packageData.getCardName().equalsIgnoreCase("Prescriptions")) {
                if (packageData.isActiveStatus()) {
                    cardImg.setBackgroundResource(packageData.getImages());
                    name.setVisibility(View.VISIBLE);
                    vurv_id.setVisibility(View.VISIBLE);
                    plan.setVisibility(View.INVISIBLE);
                    expires.setVisibility(View.INVISIBLE);
                    rxBinNum.setVisibility(View.VISIBLE);
                    rxPcnNum.setVisibility(View.VISIBLE);
                    rxGRP.setVisibility(View.VISIBLE);
                    raMemID.setVisibility(View.VISIBLE);
                    tvInsure.setVisibility(View.VISIBLE);
                    tvCardProvider.setVisibility(View.GONE);
                } else {
                    name.setVisibility(View.GONE);
                    vurv_id.setVisibility(View.GONE);
                    plan.setVisibility(View.GONE);
                    expires.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.GONE);
                    tvInsure.setVisibility(View.GONE);
                    tvCardProvider.setVisibility(View.GONE);
                }
            } else if (packageData.getCardName().equalsIgnoreCase("Medical")) {
                cardImg.setBackgroundResource(packageData.getImages());
                if (packageData.isActiveStatus()) {
                    name.setVisibility(View.VISIBLE);
                    vurv_id.setVisibility(View.VISIBLE);
                    plan.setVisibility(View.VISIBLE);
                    expires.setVisibility(View.VISIBLE);
                    plan.setText(getString(R.string.plan) + packageName);
//                    plan.setText(getString(R.string.plan) + prefsData.getString("post_title", ""));
//                    plan.setText(getString(R.string.plan) + " PULSE");
                    expires.setText(getResources().getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
                    tvCardProvider.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.VISIBLE);
                    rxPcnNum.setVisibility(View.VISIBLE);
                    tvInsure.setVisibility(View.VISIBLE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);

                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.INVISIBLE);
                } else {
                    name.setVisibility(View.GONE);
                    vurv_id.setVisibility(View.GONE);
                    plan.setVisibility(View.GONE);
                    expires.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.GONE);
                    tvInsure.setVisibility(View.GONE);
                    tvCardProvider.setVisibility(View.GONE);
                }
            } else if (packageData.getCardName().equalsIgnoreCase("Dental")) {
                cardImg.setBackgroundResource(packageData.getImages());
                if (packageData.isActiveStatus()) {
                    name.setVisibility(View.VISIBLE);
                    vurv_id.setVisibility(View.VISIBLE);
                    plan.setVisibility(View.VISIBLE);
                    tvInsure.setVisibility(View.VISIBLE);
                    plan.setText(getString(R.string.plan) + packageName);
//                    plan.setText(getString(R.string.plan) + prefsData.getString("post_title", ""));

//                    plan.setText(getString(R.string.plan) + " CARE");
                    tvCardProvider.setVisibility(View.GONE);
                    expires.setVisibility(View.VISIBLE);
                    expires.setText(getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.INVISIBLE);
                } else {
                    name.setVisibility(View.GONE);
                    vurv_id.setVisibility(View.GONE);
                    plan.setVisibility(View.GONE);
                    expires.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    tvInsure.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.GONE);
                    tvCardProvider.setVisibility(View.GONE);
                }
            } else if (packageData.getCardName().equalsIgnoreCase("Vision")) {
                cardImg.setBackgroundResource(packageData.getImages());
                if (packageData.isActiveStatus()) {
                    name.setVisibility(View.VISIBLE);
                    vurv_id.setVisibility(View.VISIBLE);
                    tvInsure.setVisibility(View.VISIBLE);
                    plan.setVisibility(View.VISIBLE);
                    plan.setText(getString(R.string.plan) + packageName);
//                    plan.setText(getString(R.string.plan) + prefsData.getString("post_title", ""));


//                    plan.setText(getString(R.string.plan) + " CARE");
                    tvCardProvider.setVisibility(View.GONE);
                    expires.setVisibility(View.VISIBLE);
                    expires.setText(getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.INVISIBLE);
                } else {
                    name.setVisibility(View.GONE);
                    vurv_id.setVisibility(View.GONE);
                    plan.setVisibility(View.GONE);
                    expires.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    tvInsure.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.GONE);
                    tvCardProvider.setVisibility(View.GONE);
                }
            } else if (packageData.getCardName().equalsIgnoreCase("AltHealth")) {
                cardImg.setBackgroundResource(packageData.getImages());
                if (packageData.isActiveStatus()) {
                    name.setVisibility(View.VISIBLE);
                    vurv_id.setVisibility(View.VISIBLE);
                    plan.setVisibility(View.VISIBLE);
                    tvInsure.setVisibility(View.VISIBLE);
                    plan.setText(getString(R.string.plan) + packageName);
//                    plan.setText(getString(R.string.plan) + prefsData.getString("post_title", ""));


//                    plan.setText(getString(R.string.plan) + " CARE");
                    expires.setVisibility(View.VISIBLE);
                    expires.setText(getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
                    tvCardProvider.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.INVISIBLE);
                } else {
                    name.setVisibility(View.GONE);
                    vurv_id.setVisibility(View.GONE);
                    plan.setVisibility(View.GONE);
                    expires.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    tvInsure.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.GONE);
                    tvCardProvider.setVisibility(View.GONE);
                }
            } else if (packageData.getCardName().equalsIgnoreCase("TeleMedicine")) {
                cardImg.setBackgroundResource(packageData.getImages());
                if (packageData.isActiveStatus()) {
                    name.setVisibility(View.VISIBLE);
                    vurv_id.setVisibility(View.VISIBLE);
                    plan.setVisibility(View.VISIBLE);
                    tvInsure.setVisibility(View.VISIBLE);
                    plan.setText(getString(R.string.plan) + packageName);
//                    plan.setText(getString(R.string.plan) + prefsData.getString("post_title", ""));


//                    plan.setText(getString(R.string.plan) + " CARE");
                    expires.setVisibility(View.VISIBLE);
                    expires.setText(getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
                    tvCardProvider.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.INVISIBLE);
                } else {
                    name.setVisibility(View.GONE);
                    vurv_id.setVisibility(View.GONE);
                    plan.setVisibility(View.GONE);
                    expires.setVisibility(View.GONE);
                    rxBinNum.setVisibility(View.GONE);
                    rxPcnNum.setVisibility(View.GONE);
                    tvInsure.setVisibility(View.GONE);
                    rxGRP.setVisibility(View.GONE);
                    raMemID.setVisibility(View.GONE);
                    tvCardProvider.setVisibility(View.GONE);
                }
            }
            container.addView(itemView);
            return itemView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

        private void customTextView(TextView view) {
            String url = "https://www.vurvhealth.com/validate/";
            SpannableStringBuilder spanTxt = new SpannableStringBuilder(Html.fromHtml(getString(R.string.provider)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            spanTxt.append(getString(R.string.visit) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            spanTxt.append(Html.fromHtml("https://www.vurvhealth.com/validate/"));
            spanTxt.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent i = new Intent("android.intent.action.VIEW");
                    i.setData(Uri.parse("https://www.vurvhealth.com/validate/"));
                    startActivity(i);
                }
            }, spanTxt.length() - "https://www.vurvhealth.com/validate/".length(), spanTxt.length(), 0);
            spanTxt.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.for_member));
            view.setMovementMethod(LinkMovementMethod.getInstance());
            view.setText(spanTxt, TextView.BufferType.SPANNABLE);
        }
    }


    private void customTextView1(TextView view, String text1, String text2) {
        //    view.setText(fromHtml(text1));
        if (text2.equalsIgnoreCase("TeleMedicine")) {
            SpannableStringBuilder spanTxt = new SpannableStringBuilder(Html.fromHtml(getString(R.string.telemedican_txt2)));
            spanTxt.append(Html.fromHtml(getString(R.string.telemedican_txt4)));
            //spanTxt.append(Html.fromHtml(getString(R.string.telemedican_txt5)));
            spanTxt.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent i = new Intent("android.intent.action.VIEW");
                    i.setData(Uri.parse("https://member.dialcare.com/login"));
                    startActivity(i);
                }
            }, spanTxt.length() - "Click here".length(), spanTxt.length(), 0);
            view.setMovementMethod(LinkMovementMethod.getInstance());
            view.setText(spanTxt, TextView.BufferType.SPANNABLE);

        } else {
            SpannableStringBuilder spanTxt = new SpannableStringBuilder(fromHtml(text1));
            view.setMovementMethod(LinkMovementMethod.getInstance());
            view.setText(spanTxt, TextView.BufferType.SPANNABLE);
        }


//        if (text2.equalsIgnoreCase("TeleMedicine")) {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent("android.intent.action.VIEW");
//                    i.setData(Uri.parse("https://www.vurvhealth.com/home/help"));
//                    startActivity(i);
//                }
//            });
//        }
    }

    private void customTextView(TextView heading2) {

        SpannableStringBuilder spanTxt = new
                SpannableStringBuilder(Html.fromHtml(getString(R.string.provider))
                + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        spanTxt.append(getString(R.string.visit) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        spanTxt.append(Html.fromHtml("https://www.vurvhealth.com/validate/"));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse("https://www.vurvhealth.com/validate/"));
                startActivity(i);
            }
        }, spanTxt.length() - "https://www.vurvhealth.com/validate/".length(), spanTxt.length(), 0);
        spanTxt.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.for_member));
        heading2.setMovementMethod(LinkMovementMethod.getInstance());
        heading2.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }


}
