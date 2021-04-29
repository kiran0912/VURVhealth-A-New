package com.VURVhealth.vurvhealth.help;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;

/**
 * Created by yqlabs on 2/3/17.
 */

public class HelpActivity extends AppCompatActivity {

    private TextView tvInquiry;
    private Button btnNumber;
    private FloatingActionButton freshChatbtn;
    private LinearLayout llGenQtn, llAppMang, llPackage, llBillingPay, llPresPham, llDocMedFac, llDen, llVis, llAltHealth, llMoreQtn,
            llSearch, llSaved, llVurav, llProfile, llothers, llHealthRx;
    private String number = "8667704151";
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private LinearLayout llVurv;
    private SharedPreferences prefsData;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_screen);

        tvInquiry = (TextView) findViewById(R.id.tvInquiry);
        btnNumber = (Button) findViewById(R.id.btnNumber);
        freshChatbtn = (FloatingActionButton) findViewById(R.id.fab_freshchat);
        llGenQtn = (LinearLayout) findViewById(R.id.llGenQtn);
        llAppMang = (LinearLayout) findViewById(R.id.llAppMang);
        llPackage = (LinearLayout) findViewById(R.id.llPackage);
        llBillingPay = (LinearLayout) findViewById(R.id.llBillingPay);
        llPresPham = (LinearLayout) findViewById(R.id.llPresPham);
        llDocMedFac = (LinearLayout) findViewById(R.id.llDocMedFac);
        llDen = (LinearLayout) findViewById(R.id.llDen);
        llVis = (LinearLayout) findViewById(R.id.llVis);
        llAltHealth = (LinearLayout) findViewById(R.id.llAltHealth);
        llMoreQtn = (LinearLayout) findViewById(R.id.llMoreQtn);
        llothers = (LinearLayout) findViewById(R.id.llOthers);
        llHealthRx = (LinearLayout) findViewById(R.id.llHealthRx);

        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);

        prefsData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);

        /*if (ContextCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HelpActivity.this, Manifest.permission.CAMERA)) {

                alertDialog();
            }
            else{
                ActivityCompat.requestPermissions(HelpActivity.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
            }
        }*/

        //getFreshchat();

        customTextView(tvInquiry);

        //tvInquiry.setText(Html.fromHtml(getResources().getString(R.string.general_inquiries)));

        //When button is clicked
        llVurv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
        //When button is clicked
        llSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, NoSavedItemActivity.class));
            }
        });
        llBillingPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, BillingandPaymentActivity.class));
            }
        });
        llPresPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, AppManagmentActivity.class));
            }
        });
        llDocMedFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, HelpDocFacActivity.class));
            }
        });

        llDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, HelpDentalActivity.class));
            }
        });
        llVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, HelpVisionActivity.class));

            }
        });

        llGenQtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, GeneralQtnActivity.class));
            }
        });
        llAppMang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, AppManagmentActivity.class));
            }
        });
        llPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, HelpPackagesActivity.class));
            }
        });
        llAltHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, HelpAltHealthActivity.class));
            }
        });
        llMoreQtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, NetworkProviders.class));
            }
        });
        llothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, OthersQtnActivity.class));
            }
        });
        llHealthRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, HelpPrescriptionActivity.class));
            }
        });
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                try {
                    onCall();
                    /*Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mCurrentListing.getPhone()));

                    startActivity(callIntent);*/
                } catch (Exception e) {
                    Log.v("Call>>", e.getMessage());
                }
            }
        });
        //When button is clicked
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, PrimaryAcntHolderActivity.class));
                finish();
//                Toast.makeText(StartScreenActivity.this, "In progress....", Toast.LENGTH_SHORT).show();

            }
        });
        //When button is clicked
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, StartScreenActivity.class));
                finish();
            }
        });

        freshChatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Freshchat.showConversations(HelpActivity.this);
            }
        });
    }

    private void customTextView(TextView view) {
        final String url = "www.vurvhealth.com/home/help";
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
        spanTxt.append(Html.fromHtml(" <b>www.vurvhealth.com/home/help</b>"));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://vurvhealth.com/help/"));
                startActivity(i);
            }
        }, spanTxt.length() - url.length(), spanTxt.length(), 0);
        spanTxt.append(".");
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

   /* private void customTextView(TextView view) {
        final String url = "<b>www.VURVhealth.com</b>";

        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "For general inquiries or billing please contact VURVhealth at ");
        spanTxt.append("(866)770-4151");
        spanTxt.append(" or visit ");
        spanTxt.append(Html.fromHtml(url));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
              *//* Intent intent = new Intent(HelpActivity.this,WebViewActivity.class);
                startActivity(intent);*//*
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://vurvhealth.com/"));
                startActivity(i);
            }
        }, spanTxt.length() - url.length(), spanTxt.length(), 0);
        spanTxt.append(".");

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }*/

    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + number)));
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

        Freshchat.getInstance(HelpActivity.this).setUser(user);


    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                //getFreshchat();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }}*/


    private void alertDialog() {
        CharSequence menu[] = new CharSequence[]{"Take From Galery", "Open Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a Picture");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "galery", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                }
            }
        });
        builder.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //    Intent i = new Intent(Intent.ACTION_VIEW);
//    i.setData(Uri.parse(url));
//    startActivity(i);
}
