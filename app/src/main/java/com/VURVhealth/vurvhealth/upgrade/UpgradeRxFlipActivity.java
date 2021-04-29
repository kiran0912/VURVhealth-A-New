package com.VURVhealth.vurvhealth.upgrade;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.Locale;

public class UpgradeRxFlipActivity extends AppCompatActivity implements OnBackStackChangedListener {
    public static boolean flipClick = true;
    static SharedPreferences prefsData;
    String activity = "";
    private ImageView cardClose;
    private ImageView cardFlip;
    String fullname;
    private boolean mShowingBack = false;
    UserSharedPreferences userSharedPreferences;
    String vurvId;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_rx_flip_screen);

       //setLanguage();


        cardClose = (ImageView) findViewById(R.id.cardClose);
        cardFlip = (ImageView) findViewById(R.id.cardFlip);
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        userSharedPreferences = UserSharedPreferences.getInstance(this);
        fullname = userSharedPreferences.getData("secondaryUserName");
        vurvId = userSharedPreferences.getData("secondaryUserVurvId");



        getIntentData();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new CardFrontFragment(activity, fullname, vurvId)).commit();
        } else {
            mShowingBack = getFragmentManager().getBackStackEntryCount() > 0;
        }
        cardClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                flipClick = true;
                finish();
            }
        });
        cardFlip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    private void getIntentData() {
        try {
            activity = getIntent().getStringExtra("activity");
        } catch (Exception e) {
            activity = "";
        }
    }

    public void onBackStackChanged() {
        mShowingBack = getFragmentManager().getBackStackEntryCount() > 0;
        invalidateOptionsMenu();
    }

    public void onBackPressed() {
        super.onBackPressed();
        flipClick = true;
        finish();
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out).replace(R.id.container, new CardBackFragment()).addToBackStack(null).commit();
    }

    public static class CardBackFragment extends Fragment {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_card_back, container, false);
            ((FrameLayout) v.findViewById(R.id.cardImg)).setBackgroundResource(R.drawable.card_rxback1);
            return v;
        }
    }

    @SuppressLint("ValidFragment")
    public static class CardFrontFragment extends Fragment {
        String activity;
        String fullName;
        String vurvId;

        public CardFrontFragment(String activity, String fullname, String vurvId) {
            this.fullName = fullname;
            this.vurvId = vurvId;
            this.activity = activity;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_card_front, container, false);
            if (UpgradeRxFlipActivity.flipClick) {
                Animation animationScaleUp = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
                AnimationSet growShrink = new AnimationSet(true);
                growShrink.addAnimation(animationScaleUp);
                v.startAnimation(growShrink);
                UpgradeRxFlipActivity.flipClick = false;
            }
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView tvRxBin = (TextView) v.findViewById(R.id.tvRxBin);
            TextView vurv_id = (TextView) v.findViewById(R.id.vurv_id);
            TextView expires = (TextView) v.findViewById(R.id.expires);
            TextView rxPcNum = (TextView) v.findViewById(R.id.rxPcNum);
            TextView rxGRP = (TextView) v.findViewById(R.id.rxGRP);
            TextView tvMemberId = (TextView) v.findViewById(R.id.tvMemberId);
            TextView plan = (TextView) v.findViewById(R.id.plan);
            FrameLayout cardImg = (FrameLayout) v.findViewById(R.id.cardImg);
            TextView card_data = (TextView)v.findViewById(R.id.card_data);
            card_data.setVisibility(View.VISIBLE);
            LinearLayout ll_auth = (LinearLayout)v.findViewById(R.id.ll_auth);
            ll_auth.setVisibility(View.VISIBLE);
            /*TextView tvCardProvider = (TextView) v.findViewById(R.id.tvCardProvider);
            tvCardProvider.setVisibility(View.VISIBLE);
            customTextView(tvCardProvider);*/
            plan.setVisibility(View.INVISIBLE);
            expires.setVisibility(View.GONE);
            vurv_id.setVisibility(View.INVISIBLE);
            cardImg.setBackgroundResource(R.drawable.card_rx_large1);
            if (this.activity.equalsIgnoreCase("MyMembersActivity")) {
                name.setText(this.fullName);

                /*srikanth*/
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(this.vurvId.substring(0, 4));
                    stringBuilder.append("-");
                    stringBuilder.append(this.vurvId.substring(4, 7));
                    stringBuilder.append("-");
                    stringBuilder.append(this.vurvId.substring(7, 11));
                    vurv_id.setText("VURV ID:"+stringBuilder);
                } catch (ArrayIndexOutOfBoundsException e2) {
                    vurv_id.setText("VURV ID:" + this.vurvId);
                }

//                vurv_id.setText("VURV ID: " + this.vurvId);
            } else {
                name.setText(UpgradeRxFlipActivity.prefsData.getString("fullName", ""));
//                vurv_id.setText("VURV ID" + UpgradeRxFlipActivity.prefsData.getString("vurvId", ""));

                /*srikanth*/
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(UpgradeRxFlipActivity.prefsData.getString("vurvId", "").substring(0, 4));
                    stringBuilder.append("-");
                    stringBuilder.append(UpgradeRxFlipActivity.prefsData.getString("vurvId", "").substring(4, 7));
                    stringBuilder.append("-");
                    stringBuilder.append(UpgradeRxFlipActivity.prefsData.getString("vurvId", "").substring(7, 11));
                    vurv_id.setText("VURV ID:"+stringBuilder);
                } catch (ArrayIndexOutOfBoundsException e2) {
                    vurv_id.setText("VURV ID:"+UpgradeRxFlipActivity.prefsData.getString("vurvId", ""));
                }
            }
            return v;
        }
        private void customTextView(TextView view) {
            view.setText(Html.fromHtml(getString(R.string.provider) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.visit) + " <b> <font color=\"#005fb6\"> https://www.vurvhealth.com/validate</font></b>"));
//            view.setText(Html.fromHtml(Html.fromHtml(getString(R.string.provider)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.visit) + " <b> <font color=\"#005fb6\"> https://www.vurvhealth.com/validate</font></b>"));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent("android.intent.action.VIEW");
                    i.setData(Uri.parse("https://www.vurvhealth.com/validate"));
                    CardFrontFragment.this.startActivity(i);
                }
            });
        }
    }



  /*  private void setLanguage() {
        String selectedLanguage = getSharedPreferences("languagePref", 0).getString(MedicalScreenActivity.languageTitle, "");
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
    }*/
}
