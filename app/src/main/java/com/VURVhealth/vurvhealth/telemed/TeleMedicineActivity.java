package com.VURVhealth.vurvhealth.telemed;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.NoSavedItemActivity;
import com.VURVhealth.vurvhealth.save.SaveItemActivity;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;

public class TeleMedicineActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSaved;
    private LinearLayout llVurv;
    private LinearLayout llSearch;
    private Button btnLogin;
    private ImageView backBtn;
    private Context context = TeleMedicineActivity.this;
    private String move = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_medicine2);

       /* if (getIntent()!=null){
            move = getIntent().getStringExtra("move");
        }*/

        btnLogin = (Button)findViewById(R.id.btn_login);
        backBtn = (ImageView)findViewById(R.id.backBtn);

        llSaved = (LinearLayout) findViewById(R.id.llSaved);
        llVurv = (LinearLayout) findViewById(R.id.llVurv);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSaved.setOnClickListener(this);
        llVurv.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llSaved:
                startActivity(new Intent(context, NoSavedItemActivity.class));
                finish();
                break;
            case R.id.llVurv:
                startActivity(new Intent(context, VurvPackageActivity.class));
                finish();
                break;
            case R.id.llProfile:
                startActivity(new Intent(context, PrimaryAcntHolderActivity.class));
                finish();
                break;
            case R.id.llHelp:
                Uri uri = Uri.parse(Application_holder.help_url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.llSearch:
                startActivity(new Intent(context, StartScreenActivity.class));
                finish();
                break;
            case R.id.btn_login:
                Uri uri1 = Uri.parse("https://member.dialcare.com/login"); // missing 'http://' will cause crashed
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);
                break;
            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        if (move.equalsIgnoreCase("VurvPackage")){
            startActivity(new Intent(context, VurvPackageActivity.class));
            finish();
        }else if(move.equalsIgnoreCase("StartScreenActivity")){
            startActivity(new Intent(context, StartScreenActivity.class));
            finish();
        }
    }*/
}