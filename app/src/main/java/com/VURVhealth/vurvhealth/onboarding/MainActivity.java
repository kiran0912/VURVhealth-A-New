package com.VURVhealth.vurvhealth.onboarding;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.adapters.IntroductoryImgAdapter;
import com.VURVhealth.vurvhealth.authentication.LoginActivity;
import com.VURVhealth.vurvhealth.authentication.RegistrationActivity;
import com.VURVhealth.vurvhealth.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends AppCompatActivity {

    private int viewpagerPosition;
    private Button getStartedBtn;
    private static ViewPager viewPagerWithImg;
    private TextView login;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    public static final String INTRODUCTORY_VALUE = "AppOpeningFirstTime";

    int[] viewpager_imgary = {R.drawable.ic_vurv_logo, R.drawable.onboarding_step01, R.drawable.onboarding_step02, R.drawable.onboarding_step03};

    private LinearLayout splasLayoutId,ll_viewPager;
    private LinearLayout introFrameLayoutId;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);

        login = (TextView) findViewById(R.id.login_button);
//        gradient = (Button) findViewById(R.id.gradient);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent introductoryIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(introductoryIntent);
                finish();
            }
        });


        String[] introHeadAry = {"" + getApplicationContext().getResources().getString(R.string.welcome_to_vurvhealth), "" + getApplicationContext().getResources().getString(R.string.step_01), "" + getApplicationContext().getResources().getString(R.string.step_02),
                "" + getApplicationContext().getResources().getString(R.string.step_03)};


        String[] introParaAray = {getApplicationContext().getResources().getString(R.string.onboarding1), "" + getApplicationContext().getResources().getString(R.string.onboarding2),
                "" + getApplicationContext().getResources().getString(R.string.onboarding3),
                "" + getApplicationContext().getResources().getString(R.string.onboarding4)};


        String[] introTextAry = {"" , "" + getApplicationContext().getResources().getString(R.string.step_txt1), "" + getApplicationContext().getResources().getString(R.string.step_txt2),
                "" + getApplicationContext().getResources().getString(R.string.step_txt3)};

        introFrameLayoutId = (LinearLayout) findViewById(R.id.intro_frame_layout_id);
        ll_viewPager = (LinearLayout) findViewById(R.id.ll_viewPager);

        getStartedBtn = (Button) findViewById(R.id.getStartedBtn);
        viewPagerWithImg = (ViewPager) findViewById(R.id.introductory_viewpager_id);

//        ImagePagerAdapter imgAdapter = new ImagePagerAdapter();
//        viewPagerWithImg.setAdapter(imgAdapter);


        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });


        viewPagerWithImg.setAdapter(new IntroductoryImgAdapter(getApplicationContext(), viewpager_imgary, introHeadAry, introTextAry, introParaAray));
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(viewPagerWithImg);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = viewpager_imgary.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPagerWithImg.setCurrentItem(currentPage++, true);
            }
        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
                viewpagerPosition = pos;
                if(pos == 0){
                    getStartedBtn.setVisibility(View.GONE);
                    ll_viewPager.setBackgroundColor(getResources().getColor(R.color.view_bg));
                    /*gradient.setVisibility(View.VISIBLE);
                    gradient.setBackground(getResources().getDrawable(R.drawable.gradient_vurv));*/
                }else if(pos == 1){
                    getStartedBtn.setVisibility(View.GONE);
                  /*  gradient.setVisibility(View.VISIBLE);
                    gradient.setBackground(null);*/
                    ll_viewPager.setBackground(getResources().getDrawable(R.drawable.gradient_background));
                }else if(pos == 2){
                    ll_viewPager.setBackground(getResources().getDrawable(R.drawable.gradient_background));
                    getStartedBtn.setVisibility(View.GONE);
//                    gradient.setVisibility(View.GONE);
                }else if (pos == 3) {
                    ll_viewPager.setBackground(getResources().getDrawable(R.drawable.gradient_background));
                    getStartedBtn.setVisibility(View.VISIBLE);
//                    gradient.setVisibility(View.GONE);
                    getStartedBtn.setText("Get Started");
                    getStartedBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    getStartedBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

        new CountDownTimer(3000, 500) {
            public void onFinish() {
                introFrameLayoutId.setVisibility(View.VISIBLE);
            }

            public void onTick(long millisUntilFinished) {

            }

        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
