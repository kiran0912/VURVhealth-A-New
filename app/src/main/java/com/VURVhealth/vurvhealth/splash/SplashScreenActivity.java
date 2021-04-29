package com.VURVhealth.vurvhealth.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.authentication.LoginActivity;
import com.VURVhealth.vurvhealth.onboarding.MainActivity;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;

import java.util.Locale;

/**
 * Created by yqlabs on 29/11/16.
 */

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private SplitInstallManager splitInstallManager;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        splitInstallManager = SplitInstallManagerFactory.create(this);
        SplitInstallRequest request =
                SplitInstallRequest.newBuilder()
                        // Uses the addLanguage() method to include French language resources in the request.
                        // Note that country codes are ignored. That is, if your app
                        // includes resources for “fr-FR” and “fr-CA”, resources for both
                        // country codes are downloaded when requesting resources for "fr".
                        .addLanguage(Locale.forLanguageTag("zh"))
                        .build();

// Submits the request to install the additional language resources.
        splitInstallManager.startInstall(request);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences prefsData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
                if(prefsData.getString("emailId","").length()>0){
                    setLanguage();
                    Intent i = new Intent(SplashScreenActivity.this, StartScreenActivity.class);
                    startActivity(i);
                    finish();
                }else if(prefsData.getString("logout","").length()>0){
                    setLanguage();
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    setLanguage();
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

                // close this activity

            }
        }, SPLASH_TIME_OUT);
    }

    

    private void setLanguage() {
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
    }
}
