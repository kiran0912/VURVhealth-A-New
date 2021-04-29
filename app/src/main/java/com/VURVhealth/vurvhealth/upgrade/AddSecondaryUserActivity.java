package com.VURVhealth.vurvhealth.upgrade;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.database.SqLiteDbHelper;
import com.VURVhealth.vurvhealth.myProfile.MyMembersActivity;
import com.VURVhealth.vurvhealth.myProfile.pojos.AddMemberReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.AddMemberResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 21/2/17.
 */

public class AddSecondaryUserActivity extends SuperAppCompactActivity {

    private EditText etFirstName, etMiddleName, etLastName, etDoB, etEmailAddress,etMobileNo;
    private Button btn_done_inactive, btn_done_active;
    private ImageView backBtn;
    private TextView text_hint, text_email_hint, mobile_hint;
    private CheckBox chkMale, chkFemale;
    private Calendar myCalendar;
    public final Pattern pattern = Pattern
            .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");


    private SharedPreferences sharedPreferences;
    private SqLiteDbHelper recentDbHelper;
    private int userId;
    private String dob,dobFormat;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_secondary_user);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etDoB = (EditText) findViewById(R.id.etDoB);
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        text_hint = (TextView) findViewById(R.id.text_hint);
        text_email_hint = (TextView) findViewById(R.id.text_email_hint);
        mobile_hint = (TextView) findViewById(R.id.text_mobile_hint);
        chkMale = (CheckBox) findViewById(R.id.chkMale);
        chkFemale = (CheckBox) findViewById(R.id.chkFemale);

        btn_done_inactive = (Button) findViewById(R.id.btn_done_inactive);
        btn_done_active = (Button) findViewById(R.id.btn_done_active);

        etFirstName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etDoB.addTextChangedListener(textWatcher);
//        etMobileNo.addTextChangedListener(textWatcher);
        myCalendar = Calendar.getInstance();

        recentDbHelper = new SqLiteDbHelper(AddSecondaryUserActivity.this);

        SharedPreferences prefsData = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        userId = prefsData.getInt("userId", 1);

        chkMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkFemale.setChecked(false);
                }

            }
        });
        chkFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkMale.setChecked(false);
                }

            }
        });

        //add textwatcher to view
        etMiddleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etMiddleName.getText().toString().equals("")) {
                    text_hint.setVisibility(View.VISIBLE);
                } else
                    text_hint.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etMobileNo.getText().toString().equals("")) {
                    mobile_hint.setVisibility(View.VISIBLE);
                } else
                    mobile_hint.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //add textwatcher to view
        etEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etEmailAddress.getText().toString().equals("")) {
                    text_email_hint.setVisibility(View.VISIBLE);
                } else {
                    text_email_hint.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        checkFieldsForEmptyValues();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            final long today = System.currentTimeMillis() - 1000;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (myCalendar.getTimeInMillis() >= today) {
                    //Make them try again
                    etDoB.setText("");
                    Toast.makeText(getApplicationContext(), "Invalid date, please try again", Toast.LENGTH_LONG).show();

                } else {
                    updateLabel();
                }

            }

        };
        etDoB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddSecondaryUserActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
//                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });


        btn_done_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmailAddress.getText().toString();

                dob = etDoB.getText().toString();

                try {
                    SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = dt.parse(dob);
                    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                    dobFormat = sm.format(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Matcher m = pattern.matcher(email);
                boolean emailFormat = m.matches();
                if ((!chkMale.isChecked() && !chkFemale.isChecked()) || (chkFemale.isChecked() && chkMale.isChecked())) {

                    Toast.makeText(AddSecondaryUserActivity.this, "Please select any one gender", Toast.LENGTH_SHORT).show();

                }else if(!email.isEmpty()){
                    if (!emailFormat || !email.contains(".com"))
                        Toast.makeText(AddSecondaryUserActivity.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                    else{

                        Intent i = new Intent(AddSecondaryUserActivity.this, MyMembersActivity.class);
                        startActivity(i);
                    }
                    
                }else {
                    if(checkInternet()){
                        addMember();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //add text watcher for fields
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    //set the date format here
    private void updateLabel() {

        String myFormat = "MM-dd-yyyy"; //In which format need to put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDoB.setText(sdf.format(myCalendar.getTime()));
    }

    //checking the empty fields
    private void checkFieldsForEmptyValues() {

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        dob = etDoB.getText().toString();
//        String mobileNo = etMobileNo.getText().toString();

        if (!firstName.equals("") && !lastName.equals("") && !dob.equals("") /*&& !mobileNo.equals("")*/) {

            btn_done_inactive.setVisibility(View.GONE);
            btn_done_active.setVisibility(View.VISIBLE);

        } else {
            btn_done_inactive.setVisibility(View.VISIBLE);
            btn_done_active.setVisibility(View.GONE);
        }
    }

    private void addMember() {
        showProgressDialog(AddSecondaryUserActivity.this);
        ApiInterface apiService =
                ApiClient.getClient(AddSecondaryUserActivity.this).create(ApiInterface.class);
        ArrayList<AddMemberReqPayload> addMemberReqPayloads = new ArrayList<AddMemberReqPayload>();
        final AddMemberReqPayload addMemberReqPayload = new AddMemberReqPayload();
        addMemberReqPayload.setParentUserId(userId);
        addMemberReqPayload.setChildFirstName(etFirstName.getText().toString());
        addMemberReqPayload.setMiddleName(etMiddleName.getText().toString());
        addMemberReqPayload.setChildLastName(etLastName.getText().toString());
        addMemberReqPayload.setChildBirthdate(dobFormat);
        addMemberReqPayload.setEmail(etEmailAddress.getText().toString());
        addMemberReqPayload.setMobile(etMobileNo.getText().toString());
        addMemberReqPayload.setGender(chkMale.isChecked() ? "M" : "F");

        addMemberReqPayloads.add(addMemberReqPayload);

        Call<AddMemberResPayload> call = apiService.addMember(addMemberReqPayloads);
        call.enqueue(new Callback<AddMemberResPayload>() {
            @Override
            public void onResponse(Call<AddMemberResPayload> call, Response<AddMemberResPayload> response) {

                if (response.isSuccessful()) {
                    AddMemberResPayload addMemberResPayload = response.body();

                    if(addMemberResPayload.getMemberUserID().equals(0)){
                        Toast.makeText(getApplicationContext(),"No user found",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent i = new Intent(AddSecondaryUserActivity.this, MyMembersActivity.class);
                        startActivity(i);
                        finish();
                    }

                    dismissProgressDialog();

                } else {
                    dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<AddMemberResPayload> call, Throwable t) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();

//                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddSecondaryUserActivity.this, MyMembersActivity.class);
        startActivity(intent);
        finish();
    }
}
