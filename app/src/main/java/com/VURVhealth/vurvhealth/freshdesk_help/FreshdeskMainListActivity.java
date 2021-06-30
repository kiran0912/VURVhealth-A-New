package com.VURVhealth.vurvhealth.freshdesk_help;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.althealth.AltHealthScreenActivity;
import com.VURVhealth.vurvhealth.dental.DentalScreenActivity;
import com.VURVhealth.vurvhealth.freshdesk_help.pojos.FreshDeskMainListRes;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.save.SaveItemActivity;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.telemed.TeleMedicineActivity;
import com.VURVhealth.vurvhealth.telemed.TeleMedicineActivity1;
import com.VURVhealth.vurvhealth.vision.VisionScreenActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yqlabs on 18/3/17.
 */

public class FreshdeskMainListActivity extends SuperAppCompactActivity {
    private static int REQUEST_CODE = 1000;
    private ImageView backBtn;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView no_data;
    private RecyclerView recycler_view;
    private TextView tbName, tvInquiry;

    private LinearLayout llSearch, llSaved, llProfile, llVurv;
    private FloatingActionButton freshChatbtn;
    private SharedPreferences prefsData;
    private WebView webView;
    private Context context = FreshdeskMainListActivity.this;
    private String url = "https://vurvhealth.com/help/";
    private String move = "";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshdesk_help_main);
        tbName = (TextView) findViewById(R.id.tbName);
        tvInquiry = (TextView) findViewById(R.id.tvInquiry);
        no_data = (TextView) findViewById(R.id.no_data);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        freshChatbtn = (FloatingActionButton) findViewById(R.id.fab_freshchat);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);

        if (getIntent()!=null);
            move = getIntent().getStringExtra("move");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FreshdeskMainListActivity.this, StartScreenActivity.class));
            }
        });

        prefsData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);


        //getFreshdeskMainhelp();

        //customTextView(tvInquiry);
        //getFreshchat();


        //When button is clicked
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreshdeskMainListActivity.this, PrimaryAcntHolderActivity.class));
                finish();
//                Toast.makeText(StartScreenActivity.this, "In progress....", Toast.LENGTH_SHORT).show();

            }
        });
        //When button is clicked
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreshdeskMainListActivity.this, StartScreenActivity.class));
                finish();
            }
        });

        freshChatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Freshchat.showConversations(FreshdeskMainListActivity.this);
            }
        });

        llSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreshdeskMainListActivity.this, NoSavedItemActivity.class));
            }
        });

        //When button is clicked
        llVurv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreshdeskMainListActivity.this, VurvPackageActivity.class));
                finish();
            }
        });

        showProgressDialog(context);
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dismissProgressDialog();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e("Ssl Error:",handler.toString() + "error:" +  error);
            handler.proceed();
            dismissProgressDialog();
        }
    }

    private void customTextView(TextView view) {
        final String url = "www.VURVhealth.com/help";
        final String help_url = "help@VURVhealth.com";
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(getString(R.string.for_gen_info) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        spanTxt.append(Html.fromHtml(" <b>help@VURVhealth.com</b>"));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"help@VURVhealth.com"});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        }, spanTxt.length() - help_url.length(), spanTxt.length(), 0);
        spanTxt.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        spanTxt.append(getString(R.string.or_visit) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        spanTxt.append(Html.fromHtml(" <b>www.VURVhealth.com/help</b>"));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse("http://www.VURVhealth.com/help"));
                startActivity(i);
            }
        }, spanTxt.length() - url.length(), spanTxt.length(), 0);
        spanTxt.append(".");
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }


    protected void onResume() {
        super.onResume();
//        mAdapter.notifyDataSetChanged();
    }


    public static class ApiClient {

        public String userId;

        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Application_holder.FRESHDESkBASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit;
        }
    }


    private void getFreshdeskMainhelp() {

        try {


            showProgressDialog(FreshdeskMainListActivity.this);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ArrayList<FreshDeskMainListRes>> call = apiService.getFreshdeskhelp();
            call.enqueue(new Callback<ArrayList<FreshDeskMainListRes>>() {
                @Override
                public void onResponse(Call<ArrayList<FreshDeskMainListRes>> call, Response<ArrayList<FreshDeskMainListRes>> response) {

                    if (response.isSuccessful()) {
                        List<FreshDeskMainListRes> responsePayLoad = response.body();
                        loadHistoryData(responsePayLoad);

                        dismissProgressDialog();
                    } else {

                        dismissProgressDialog();
                        Toast.makeText(FreshdeskMainListActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<ArrayList<FreshDeskMainListRes>> call, Throwable t) {

                    dismissProgressDialog();
                    Toast.makeText(FreshdeskMainListActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                }

            });

        } catch (Exception e) {
            e.getMessage();
            dismissProgressDialog();

        }

    }

    private void loadHistoryData(List<FreshDeskMainListRes> responsePayLoad) {
        if (responsePayLoad != null) {
            recycler_view.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            ProductAdapter ca = new ProductAdapter(responsePayLoad, getApplicationContext());
            recycler_view.setAdapter(ca);
        } else {
            recycler_view.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        Context mContext;
        List<FreshDeskMainListRes> holdHistoryResPayLoad;

        public ProductAdapter(List<FreshDeskMainListRes> holdHistoryResPayLoad, Context context) {
            this.holdHistoryResPayLoad = holdHistoryResPayLoad;
            mContext = context;
        }

        @Override
        public int getItemCount() {

            return holdHistoryResPayLoad.size();

        }


        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.freshdesk_help_main_inflator, viewGroup, false);

            return new ProductViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder productViewHolder, final int position) {

            productViewHolder.tvfolderName.setText(
                    holdHistoryResPayLoad.get(position).getName() != null ? holdHistoryResPayLoad.get(position).getName().toString() : "");

            productViewHolder.tvfolderName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FreshdeskMainListActivity.this, FreshdeskSubListActivity.class);
                    intent.putExtra("str_id", Long.toString(holdHistoryResPayLoad.get(position).getId()));
                    intent.putExtra("str_name", holdHistoryResPayLoad.get(position).getName());
                    startActivity(intent);
                }
            });

        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {

            protected TextView tvfolderName;


            public ProductViewHolder(View v) {
                super(v);
                tvfolderName = (TextView) v.findViewById(R.id.tvfolderName);
            }
        }


    }


    private void getFreshchat() {

        FreshchatConfig freshchatConfig = new FreshchatConfig("b34e3f1f-cdb2-4e30-9627-a3d3cb898d8e", "9cb2a136-ed3a-4645-b7cf-1642de15adcb");
        freshchatConfig.setCameraCaptureEnabled(true);
        freshchatConfig.setGallerySelectionEnabled(true);
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);


        //Update user information
        FreshchatUser user = Freshchat.getInstance(getApplicationContext()).getUser();
        user.setFirstName(prefsData.getString("firstName", "")).setLastName(prefsData.getString("lastName", "")).setEmail(prefsData.getString("email", "")).setPhone("", prefsData.getString("mobileNo", ""));

        Freshchat.getInstance(FreshdeskMainListActivity.this).setUser(user);

    }

    public void onBackPressed() {
        if (move.equalsIgnoreCase("StartScreen")){
            startActivity(new Intent(context, StartScreenActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("SaveItemScreen")) {
            startActivity(new Intent(context, SaveItemActivity.class));
            finish();
        }
        else if(move.equalsIgnoreCase("VurvPackageActivity")) {
            startActivity(new Intent(context, VurvPackageActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("PrimaryAcntHolderActivity")) {
            startActivity(new Intent(context, PrimaryAcntHolderActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("AltHealthScreenActivity")) {
            startActivity(new Intent(context, AltHealthScreenActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("DentalScreenActivity")) {
            startActivity(new Intent(context, DentalScreenActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("MedicalScreenActivity")) {
            startActivity(new Intent(context, MedicalScreenActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("PrescriptionSearchActivity")) {
            startActivity(new Intent(context, PrescriptionSearchActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("NoSavedItemActivity")) {
            startActivity(new Intent(context, NoSavedItemActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("TeleMedicineActivity")) {
            startActivity(new Intent(context, TeleMedicineActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("VisionScreenActivity")) {
            startActivity(new Intent(context, VisionScreenActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("TeleMedicineActivity1")) {
            startActivity(new Intent(context, TeleMedicineActivity1.class));
            finish();
        }


    }


}

