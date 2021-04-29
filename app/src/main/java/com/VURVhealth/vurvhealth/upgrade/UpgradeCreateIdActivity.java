package com.VURVhealth.vurvhealth.upgrade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.pojos.StateReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.StateResPayload;
import com.VURVhealth.vurvhealth.myProfile.MyMembersActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.utilities.PhoneNumberTextWatcher;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 21/2/17.
 */

public class UpgradeCreateIdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static ArrayList<String> fullFormList;
    private ImageView backBtn;
    private Button btn_next_active;
    private Button btn_next_inactive;
    private EditText etCity;
    private EditText etHomeAddrs;
    private EditText etPhno;
    private EditText etZip;
    private boolean fieldsOK;
    private ProgressDialog pDialog;
    private Spinner spState;
    int len = 0;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            UpgradeCreateIdActivity.this.checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_create_id);
        this.etHomeAddrs = (EditText) findViewById(R.id.etHomeAddrs);
        this.etCity = (EditText) findViewById(R.id.etCity);
        this.spState = (Spinner) findViewById(R.id.spState);
        this.etZip = (EditText) findViewById(R.id.etZip);
        this.etPhno = (EditText) findViewById(R.id.etPhno);
        this.btn_next_inactive = (Button) findViewById(R.id.btn_next_inactive);
        this.btn_next_active = (Button) findViewById(R.id.btn_next_active);
        this.backBtn = (ImageView) findViewById(R.id.backBtn);
        this.spState.setOnItemSelectedListener(this);
        if (checkInternet()) {
            getStateService();
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        this.etPhno.addTextChangedListener(new PhoneNumberTextWatcher(etPhno));
        this.etHomeAddrs.addTextChangedListener(this.textWatcher);
        this.etCity.addTextChangedListener(this.textWatcher);
        this.etZip.addTextChangedListener(this.textWatcher);
        this.etPhno.addTextChangedListener(this.textWatcher);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpgradeCreateIdActivity.this.startActivity(new Intent(UpgradeCreateIdActivity.this, PulsePaymentOptionsActivity.class));
                UpgradeCreateIdActivity.this.finish();
            }
        });
        this.etPhno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = UpgradeCreateIdActivity.this.etPhno.getText().toString();
                if (str.length() == 4 && len < str.length()) {
                    UpgradeCreateIdActivity.this.etPhno.append("-");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                len = UpgradeCreateIdActivity.this.etPhno.getText().toString().length();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkFieldsForEmptyValues();
        this.btn_next_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = UpgradeCreateIdActivity.this.etPhno.getText().toString().trim();
                if (UpgradeCreateIdActivity.this.etZip.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpgradeCreateIdActivity.this.getApplicationContext(), "Please enter zip code", Toast.LENGTH_SHORT).show();
                } else if (UpgradeCreateIdActivity.this.etZip.getText().toString().trim().length() < 3) {
                    Toast.makeText(UpgradeCreateIdActivity.this.getApplicationContext(), "Please enter valid zip code", Toast.LENGTH_SHORT).show();
                } else if (phoneNo.length() < 10) {
                    Toast.makeText(UpgradeCreateIdActivity.this.getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(UpgradeCreateIdActivity.this, MyMembersActivity.class);
                    intent.putExtra("activity", "UpgradeCreateIdActivity");
                    UpgradeCreateIdActivity.this.startActivity(intent);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    protected boolean checkInternet() {
        getBaseContext();
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    protected void showProgressDialog(Context context) {
        this.pDialog = new ProgressDialog(context);
        this.pDialog.setMessage(getResources().getString(R.string.please_wait));
        this.pDialog.setCancelable(false);
        this.pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (this.pDialog != null) {
            this.pDialog.dismiss();
        }
    }

    private void getStateService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<StateReqPayload> stateReqPayloads = new ArrayList();
        StateReqPayload stateReqPayload = new StateReqPayload();
        stateReqPayload.setType("UA");
        stateReqPayloads.add(stateReqPayload);
        apiService.getState(stateReqPayloads).enqueue(new Callback<ArrayList<StateResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<StateResPayload>> call, Response<ArrayList<StateResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<StateResPayload> stateResPayload = (ArrayList) response.body();
                    UpgradeCreateIdActivity.fullFormList = new ArrayList();
                    UpgradeCreateIdActivity.fullFormList.add(0, "State");
                    for (int i = 1; i < stateResPayload.size(); i++) {
                        UpgradeCreateIdActivity.fullFormList.add(((StateResPayload) stateResPayload.get(i)).getFacState());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UpgradeCreateIdActivity.this, android.R.layout.simple_selectable_list_item, UpgradeCreateIdActivity.fullFormList) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            if (position == 0) {
                                textView.setTextColor(Color.parseColor("#42000000"));
                                textView.setTextSize(18.0f);
                            } else {
                                textView.setTextColor(Color.parseColor("#005FB6"));
                                textView.setTextSize(18.0f);
                            }
                            return textView;
                        }
                    };
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    UpgradeCreateIdActivity.this.spState.setAdapter(dataAdapter);
                    UpgradeCreateIdActivity.this.dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StateResPayload>> call, Throwable t) {
                UpgradeCreateIdActivity.this.dismissProgressDialog();
                Toast.makeText(UpgradeCreateIdActivity.this, UpgradeCreateIdActivity.this.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkFieldsForEmptyValues() {
        String address = this.etHomeAddrs.getText().toString().trim();
        String city = this.etCity.getText().toString().trim();
        String zip = this.etZip.getText().toString().trim();
        String phno = this.etPhno.getText().toString().trim();
        if (address.equals("") || city.equals("") || zip.equals("") || phno.equals("")) {
            this.btn_next_inactive.setVisibility(View.VISIBLE);
            this.btn_next_active.setVisibility(View.GONE);
            this.btn_next_inactive.setEnabled(true);
            this.btn_next_active.setEnabled(false);
            return;
        }
        this.btn_next_inactive.setVisibility(View.VISIBLE);
        this.btn_next_active.setVisibility(View.GONE);
        this.btn_next_inactive.setEnabled(false);
        this.btn_next_active.setEnabled(true);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
