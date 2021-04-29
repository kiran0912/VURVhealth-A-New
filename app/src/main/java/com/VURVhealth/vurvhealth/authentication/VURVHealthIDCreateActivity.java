package com.VURVhealth.vurvhealth.authentication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.authentication.registrationPojo.RegistrationResPayLoad;
import com.VURVhealth.vurvhealth.authentication.vurvIdPojos.VurvIdReqPayLoad;
import com.VURVhealth.vurvhealth.authentication.vurvIdPojos.VurvIdResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 1/12/16.
 */

public class VURVHealthIDCreateActivity extends SuperAppCompactActivity {
    private static final String TAG = VURVHealthIDCreateActivity.class.getSimpleName();
    private EditText tvFirstName, tvMiddleName, tvlastName;
    private TextView text_hint;
    private Button btn_done, btn_done_active, tvDoB;
    private CheckBox chkMale, chkFemale;
    private ImageView backBtn;
    private String firstName, lastName, dob, middlename;
    private String email,userName, password, nonceValue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public RegistrationResPayLoad registrationResPayLoad;
    private Calendar myCalendar;
    private VurvIdResPayLoad vurvIdResPayLoads;
    String dobFormat;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vurvhealthid_create_screen);

        tvFirstName = (EditText) findViewById(R.id.tvFirstName);
        tvMiddleName = (EditText) findViewById(R.id.tvMiddleName);
        tvlastName = (EditText) findViewById(R.id.tvlastName);
        tvDoB = (Button) findViewById(R.id.tvDoB);
        btn_done = (Button) findViewById(R.id.btn_done);
        btn_done_active = (Button) findViewById(R.id.btn_done_active);
        chkMale = (CheckBox) findViewById(R.id.chkMale);
        chkFemale = (CheckBox) findViewById(R.id.chkFemale);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        text_hint = (TextView) findViewById(R.id.text_hint);

        //adding text watcher for the fields
        tvFirstName.addTextChangedListener(textWatcher);
        tvlastName.addTextChangedListener(textWatcher);
        tvDoB.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();
        //get the calender instance
        myCalendar = Calendar.getInstance();
        //add text watcher for middle name for optional text
        tvMiddleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (tvMiddleName.getText().toString().length() == 0) {
                    text_hint.setVisibility(View.VISIBLE);
                } else
                    text_hint.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // setting the date picker dialog here
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
                    tvDoB.setText("");
                    Toast.makeText(getApplicationContext(), "Invalid date, please try again", Toast.LENGTH_LONG).show();

                } else {
                    updateLabel();
                }

            }

        };

        if (getIntent() != null) {
            if (getIntent().getStringExtra("mobile") != null){
                userName = getIntent().getStringExtra("mobile");
                email = getIntent().getStringExtra("mobile") + "@vurvhealth.com";
            }else {
                email = getIntent().getStringExtra("email");
                String[] usernameEmail = email.split("@");
                userName = usernameEmail[0];
                Application_holder.userEmail = email;
            }

            password = getIntent().getStringExtra("password");
            nonceValue = getIntent().getStringExtra("nonceValue");

        }

        chkMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkFemale.setChecked(false);
                    SharedPreferences settings = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, 0);
                    settings.edit().putBoolean("check",true).commit();
                }

            }
        });
        chkFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkMale.setChecked(false);
                    SharedPreferences settings = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, 0);
                    settings.edit().putBoolean("check",true).commit();
                }

            }
        });
        //When button is clicked
        tvDoB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(VURVHealthIDCreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
//                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        //When button is clicked
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        //When button is clicked
        btn_done_active.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                firstName = tvFirstName.getText().toString().trim();
                middlename = tvMiddleName.getText().toString().trim();
                lastName = tvlastName.getText().toString().trim();

                dob = tvDoB.getText().toString();
                try {
                    SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = dt.parse(dob);
                    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                    dobFormat = sm.format(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                StringBuffer result = new StringBuffer();

                if (firstName.length() == 0) {
                    Toast.makeText(VURVHealthIDCreateActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();

                } else if (tvFirstName.getText().toString().trim().length() < 3 || tvFirstName.getText().toString().trim().length() > 25) {
                    Toast.makeText(VURVHealthIDCreateActivity.this, "Please enter minimum 3 and maximum 25 characters", Toast.LENGTH_SHORT).show();
                } else if (lastName.length() == 0) {
                    Toast.makeText(VURVHealthIDCreateActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();

                } else if (dob.length() == 0) {
                    Toast.makeText(VURVHealthIDCreateActivity.this, "Please enter Date of Birth (MM/ DD/ YY)", Toast.LENGTH_SHORT).show();

                } else if ((!chkMale.isChecked() && !chkFemale.isChecked()) || (chkFemale.isChecked() && chkMale.isChecked())) {

                    Toast.makeText(VURVHealthIDCreateActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();

                } else {

                    if (checkInternet()) {
                        //Here, we are calling register service
                        getRegistrationService(userName, password, email, nonceValue, firstName, "yes", "cool");
                    } else {
                        Toast.makeText(VURVHealthIDCreateActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }

    //set the date format here
    private void updateLabel() {

        String myFormat = "MM-dd-yyyy"; //In which format need to put here
//        String myFormat = "yyyy-MM-dd"; //In which format need to put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tvDoB.setText(sdf.format(myCalendar.getTime()));
    }

    // Consuming Register service here
    private void getRegistrationService(String username, final String password,final String email, final String nonceValue,
                                        String firstName, String notify, String insecure) {
        try {
            showProgressDialog(VURVHealthIDCreateActivity.this);
            ApiInterface apiService =
                    ApiClient.getClient(VURVHealthIDCreateActivity.this).create(ApiInterface.class);

            Call<RegistrationResPayLoad> call = apiService.getRegistration(username, password, email, nonceValue, firstName/*, notify, insecure*/);
            call.enqueue(new Callback<RegistrationResPayLoad>() {
                @Override
                public void onResponse(Call<RegistrationResPayLoad> call, Response<RegistrationResPayLoad> response) {

                    if (response.isSuccessful()) {
                        registrationResPayLoad = response.body();
//                        dismissProgressDialog();

                        if (checkInternet()) {
                            //Calling the VURVID service for vurvid creation
                            getVURVIDService();
                        } else {
                            Toast.makeText(VURVHealthIDCreateActivity.this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        dismissProgressDialog();
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());

                            if (jObjError.getString("error").equalsIgnoreCase("Username already exists.")){
                                if (email.equalsIgnoreCase(getIntent().getStringExtra("mobile") + "@vurvhealth.com"))
                                    Toast.makeText(getApplicationContext(), "Mobile Number already exists.", Toast.LENGTH_LONG).show();

                                else
                                    Toast.makeText(getApplicationContext(), "Email already exists.", Toast.LENGTH_LONG).show();


                            }else {
                                Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }
                @Override
                public void onFailure(Call<RegistrationResPayLoad> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    dismissProgressDialog();
                    Toast.makeText(VURVHealthIDCreateActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }
    }

    // Consuming VURVID service here
    private void getVURVIDService() {

        try {
//            showProgressDialog(VURVHealthIDCreateActivity.this);

            String user_Id = registrationResPayLoad.getUserId();
            ApiInterface apiService =
                    ApiClient.getClient(VURVHealthIDCreateActivity.this).create(ApiInterface.class);
            VurvIdReqPayLoad vurvIdReqPayLoad = new VurvIdReqPayLoad();
            vurvIdReqPayLoad.setId(user_Id);
            vurvIdReqPayLoad.setDisplayName(firstName + " " + lastName);
            vurvIdReqPayLoad.setMiddleName(middlename);
            vurvIdReqPayLoad.setDateOfBirth(dobFormat);
            vurvIdReqPayLoad.setGender(chkMale.isChecked() ? "M" : "F");
            vurvIdReqPayLoad.setUserNicename(firstName);
            vurvIdReqPayLoad.setFirstName(firstName);
            vurvIdReqPayLoad.setLastName(lastName);
            ArrayList<VurvIdReqPayLoad> listVurv = new ArrayList<>();
            listVurv.add(vurvIdReqPayLoad);

            Call<VurvIdResPayLoad> call = apiService.getVURVIDService(listVurv);
            call.enqueue(new Callback<VurvIdResPayLoad>() {
                @Override
                public void onResponse(Call<VurvIdResPayLoad> call, Response<VurvIdResPayLoad> response) {
                    if (response.isSuccessful()) {
                        vurvIdResPayLoads = response.body();

                        dismissProgressDialog();
                        sharedPreferences = getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("firstName", vurvIdResPayLoads.getSubscriberDetail().get(0).getFirstName());
                        editor.putString("lastName",vurvIdResPayLoads.getSubscriberDetail().get(0).getLastName());
                        editor.putString("fullName", vurvIdResPayLoads.getSubscriberDetail().get(0).getFirstName()+" "+vurvIdResPayLoads.getSubscriberDetail().get(0).getLastName());
                        editor.putString("email", vurvIdResPayLoads.getUserDetail().get(0).getUserEmail());
                        editor.putString("vurvId", vurvIdResPayLoads.getSubscriberDetail().get(0).getMemberId());
                        editor.putInt("userId", Integer.parseInt(vurvIdResPayLoads.getSubscriberDetail().get(0).getUserId()));
                        if(vurvIdResPayLoads.getSubscriberDetail().get(0).getMobileNo()!=null) {
                            editor.putString("mobileNo", vurvIdResPayLoads.getSubscriberDetail().get(0).getMobileNo().equals("0") ? vurvIdResPayLoads.getUserDetail().get(0).getUserLogin() : vurvIdResPayLoads.getSubscriberDetail().get(0).getMobileNo());
                        }else {
                            editor.putString("mobileNo", vurvIdResPayLoads.getSubscriberDetail().get(0).getMobileNo() == null ? vurvIdResPayLoads.getUserDetail().get(0).getUserLogin() : vurvIdResPayLoads.getSubscriberDetail().get(0).getMobileNo());
                        }
                        editor.putString("dob", vurvIdResPayLoads.getSubscriberDetail().get(0).getDateOfBirth());
                        editor.putString("gender", vurvIdResPayLoads.getSubscriberDetail().get(0).getGender());
                        editor.putString("address1", vurvIdResPayLoads.getSubscriberDetail().get(0).getAddress1());
                        editor.putString("address2", vurvIdResPayLoads.getSubscriberDetail().get(0).getAddress2());
                        editor.putString("city", vurvIdResPayLoads.getSubscriberDetail().get(0).getCity());
                        editor.putString("stateName", vurvIdResPayLoads.getSubscriberDetail().get(0).getState());
                        editor.putString("zipCode", vurvIdResPayLoads.getSubscriberDetail().get(0).getZipcode());
                        editor.putString("zip4Code", vurvIdResPayLoads.getSubscriberDetail().get(0).getZipFourCode());
                        editor.putString("country", vurvIdResPayLoads.getSubscriberDetail().get(0).getCountry());
                        editor.putString("packageId", vurvIdResPayLoads.getSubscriberDetail().get(0).getPackageId());
                        editor.putString("packageName", vurvIdResPayLoads.getSubscriberDetail().get(0).getName());
                        editor.putString("subPackageId", vurvIdResPayLoads.getSubscriberDetail().get(0).getSubPackageId());
                        if(vurvIdResPayLoads.getSubscriberDetail().get(0).getOrderId()!=null)
                            editor.putString("orderId", vurvIdResPayLoads.getSubscriberDetail().get(0).getOrderId());

                        editor.putString("logout", "");
                        editor.commit();

                        welComeDialog();
                    } else {
                        dismissProgressDialog();
                        Toast.makeText(VURVHealthIDCreateActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                    }

                    Log.d(TAG, "Number of movies received: ");
                }

                @Override
                public void onFailure(Call<VurvIdResPayLoad> call, Throwable t) {

                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    dismissProgressDialog();
                    Toast.makeText(VURVHealthIDCreateActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }


    }

    //TextWatcher to handle the empty fields
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

    //checking the empty fields here
    private void checkFieldsForEmptyValues() {

        firstName = tvFirstName.getText().toString();
        lastName = tvlastName.getText().toString();
        dob = tvDoB.getText().toString();

        if (!firstName.equals("") && !lastName.equals("") && !dob.equals("")) {

            btn_done_active.setVisibility(View.VISIBLE);
            btn_done.setVisibility(View.GONE);

            btn_done.setEnabled(false);
            btn_done_active.setEnabled(true);

        } else {

            btn_done_active.setVisibility(View.GONE);
            btn_done.setVisibility(View.VISIBLE);

            btn_done_active.setEnabled(false);
            btn_done.setEnabled(true);
        }
    }

    //show the confirmation popup
    private void welComeDialog() {
        final Dialog customDialog = new Dialog(VURVHealthIDCreateActivity.this);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.confirmation_popup);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        final Button btn_ok = (Button) customDialog.findViewById(R.id.okBtn);
        final TextView tvName = (TextView) customDialog.findViewById(R.id.tvName);
        final TextView name = (TextView) customDialog.findViewById(R.id.name);
        final TextView vurvId = (TextView) customDialog.findViewById(R.id.vurv_id);
        tvName.setText(" "+firstName+"!");
        name.setText(firstName +" "+lastName);
        //vurvId.setText("VURV ID: "+vurvIdResPayLoads.getSubscriberDetail().get(0).getMemberId());

        /*srikanth*/
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(vurvIdResPayLoads.getSubscriberDetail().get(0).getMemberId().substring(0, 4));
            stringBuilder.append("-");
            stringBuilder.append(vurvIdResPayLoads.getSubscriberDetail().get(0).getMemberId().substring(4, 7));
            stringBuilder.append("-");
            stringBuilder.append(vurvIdResPayLoads.getSubscriberDetail().get(0).getMemberId().substring(7, 11));
            vurvId.setText("VURV ID: " + stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e2) {
            vurvId.setText("VURV ID: " + vurvIdResPayLoads.getSubscriberDetail().get(0).getMemberId());
        }



       /* SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("emailId", vurvIdResPayLoads.getUserDetail().get(0).getUserEmail());
        editor.putString("firstName", vurvIdResPayLoads.getSubscriberDetail().get(0).getFirstName());
        editor.putString("gender", vurvIdResPayLoads.getSubscriberDetail().get(0).getGender());
        editor.commit();*/

        Application_holder.userName = firstName;

        customDialog.getWindow().setAttributes(lp);

        customDialog.show();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customDialog.dismiss();
                customDialog.cancel();

                Intent i = new Intent(VURVHealthIDCreateActivity.this, StartScreenActivity.class);
                i.putExtra("firstName", firstName);
                i.putExtra("activity","VURVHealthIDCreateActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
