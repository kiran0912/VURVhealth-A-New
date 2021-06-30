package com.VURVhealth.vurvhealth.upgrade;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.text.SimpleDateFormat;

public class UpgradeDentalFlipActivity extends AppCompatActivity implements OnBackStackChangedListener {
    public static boolean flipClick = true;
    static SharedPreferences prefsData;
    private String activity;
    private ImageView cardClose;
    private ImageView cardFlip;
    private boolean mShowingBack = false;
    private UserSharedPreferences userSharedPreferences;


    public static class CardBackFragment extends Fragment {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_card_back, container, false);
            ((FrameLayout) v.findViewById(R.id.cardImg)).setBackgroundResource(R.drawable.dental_back1);
            return v;
        }
    }

    @SuppressLint("ValidFragment")
    public static class CardFrontFragment extends Fragment {
        private String activity;
        private String expiresDate;
        private String secondaryUserName;
        private String secondaryUserVurvId;

        public CardFrontFragment(String activity, String secondaryUserName, String secondaryUserVurvId, String expiresDate) {
            this.activity = activity;
            this.secondaryUserName = secondaryUserName;
            this.secondaryUserVurvId = secondaryUserVurvId;
            this.expiresDate = expiresDate;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_card_front, container, false);
            if (UpgradeDentalFlipActivity.flipClick) {
                Animation animationScaleUp = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
                AnimationSet growShrink = new AnimationSet(true);
                growShrink.addAnimation(animationScaleUp);
                v.startAnimation(growShrink);
                UpgradeDentalFlipActivity.flipClick = false;
            }
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView tvRxBin = (TextView) v.findViewById(R.id.tvRxBin);
            TextView vurv_id = (TextView) v.findViewById(R.id.vurv_id);
            TextView rxPcNum = (TextView) v.findViewById(R.id.rxPcNum);
            TextView rxGRP = (TextView) v.findViewById(R.id.rxGRP);
            TextView tvMemberId = (TextView) v.findViewById(R.id.tvMemberId);
            FrameLayout cardImg = (FrameLayout) v.findViewById(R.id.cardImg);
            TextView expires = (TextView) v.findViewById(R.id.expires);
            TextView plan = (TextView) v.findViewById(R.id.plan);
            TextView tvCardProvider = (TextView) v.findViewById(R.id.tvCardProvider);
            tvCardProvider.setVisibility(View.VISIBLE);
            /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(60,20,0,0);
            tvCardProvider.setLayoutParams(params);*/
            expires.setVisibility(View.VISIBLE);
            customTextView(tvCardProvider);
            plan.setText(getString(R.string.plan) + " "+ prefsData.getString("packageName", ""));
            //            plan.setText(getString(R.string.plan) + " "+ prefsData.getString("post_title", ""));

//            plan.setText(getString(R.string.plan) + " CARE");
            plan.setVisibility(View.VISIBLE);
            cardImg.setBackgroundResource(R.drawable.card_dental1);
            tvRxBin.setVisibility(View.GONE);
            rxPcNum.setVisibility(View.GONE);
            rxGRP.setVisibility(View.GONE);
            tvMemberId.setVisibility(View.GONE);
            String dobFormat;
            if (this.activity == null || !this.activity.equalsIgnoreCase("MyMembersActivity")) {
                dobFormat = null;
                try {
                    dobFormat = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(UpgradeDentalFlipActivity.prefsData.getString("vurv_mem_exp_date", "12-12-2017")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                name.setText(UpgradeDentalFlipActivity.prefsData.getString("fullName", ""));
              //  vurv_id.setText("VURV  ID: " + UpgradeDentalFlipActivity.prefsData.getString("vurvId", ""));

                /*srikanth*/
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(UpgradeDentalFlipActivity.prefsData.getString("vurvId", "").substring(0, 4));
                    stringBuilder.append("-");
                    stringBuilder.append(UpgradeDentalFlipActivity.prefsData.getString("vurvId", "").substring(4, 7));
                    stringBuilder.append("-");
                    stringBuilder.append(UpgradeDentalFlipActivity.prefsData.getString("vurvId", "").substring(7, 11));
                    vurv_id.setText("VURV ID: " + stringBuilder);
                } catch (ArrayIndexOutOfBoundsException e2) {
                    vurv_id.setText("VURV ID: " + UpgradeDentalFlipActivity.prefsData.getString("vurvId", ""));
                }

               expires.setText(getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
            } else {
                dobFormat = null;
                try {
                    dobFormat = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(this.expiresDate));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                name.setText(this.secondaryUserName);
              //  vurv_id.setText("VURV  ID: " + this.secondaryUserVurvId);

                /*srikanth*/
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(this.secondaryUserVurvId.substring(0, 4));
                    stringBuilder.append("-");
                    stringBuilder.append(this.secondaryUserVurvId.substring(4, 7));
                    stringBuilder.append("-");
                    stringBuilder.append(this.secondaryUserVurvId.substring(7, 11));
                    vurv_id.setText("VURV ID: " + stringBuilder);
                } catch (ArrayIndexOutOfBoundsException e2) {
                    vurv_id.setText("VURV ID: " + this.secondaryUserVurvId);
                }

                expires.setText(getString(R.string.expires) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dobFormat);
            }
            return v;
        }

        private void customTextView(TextView view) {
            view.setText(Html.fromHtml(getString(R.string.provider) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.visit) + " <b> <font color=\"#005fb6\"> https://www.vurvhealth.com/validate</font></b>"));
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

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_rx_flip_screen);
        cardClose = (ImageView) findViewById(R.id.cardClose);
        cardFlip = (ImageView) findViewById(R.id.cardFlip);
        userSharedPreferences = UserSharedPreferences.getInstance(this);
        getIntentData();
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new CardFrontFragment(activity, userSharedPreferences.getData("secondaryUserName"), userSharedPreferences.getData("secondaryUserVurvId"), userSharedPreferences.getData("expiresDate"))).commit();
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
}
