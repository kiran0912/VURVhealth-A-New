package com.VURVhealth.vurvhealth.retrofit;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yqlabs on 24/1/17.
 */
public class ApiClient {

    public static String userId;

    private static Retrofit retrofit = null, retrofit1=null;

    public static Retrofit getClient(Context context) {
        Activity activity = (Activity) context;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (activity.getClass().getSimpleName().equalsIgnoreCase("RegistrationActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("LoginActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("VURVHealthIDCreateActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("ForgotPasswordActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("PrimaryAcntHolderActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("EditProfileActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("UpgradeSubscriptionActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("PulseAnnualConfPaymentActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("AddSecondaryUserActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("AddMemberDataActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("StopSubscriptionActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("ChangePasswordScreen")
                || activity.getClass().getSimpleName().equalsIgnoreCase("EditSecondaryMemberActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("StartScreenActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("MyMembersActivity")) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Application_holder.AUTH_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        } else if (activity.getClass().getSimpleName().equalsIgnoreCase("PrivacyPolicyActivity")
                || activity.getClass().getSimpleName().equalsIgnoreCase("TermsandConditionActivity")) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Application_holder.DRUG_ARGUS)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Application_holder.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

        public static Retrofit getClient1(Context context) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit1 = new Retrofit.Builder()
                    .baseUrl(Application_holder.BASE_URL1)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit1;
        }

}
