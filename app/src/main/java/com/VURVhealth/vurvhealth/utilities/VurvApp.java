package com.VURVhealth.vurvhealth.utilities;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by yqlabs on 11/5/17.
 */

public class VurvApp extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
