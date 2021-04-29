package com.VURVhealth.vurvhealth.upgrade;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.PhoneNumberTextWatcher;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillingInfoActivity extends SuperAppCompactActivity {
    private ImageView backBtn;
    private Button btn_save_info_active;
    private Button btn_save_info_inactive;
    private EditText etAdd1;
    private EditText etAdd2;
    private EditText etCity;
    private EditText etContact;
    private EditText etZipcode;
    private ArrayList<String> fullFormList;
    private SharedPreferences prefsData;
    private Spinner spCountry;
    private Spinner spState;
    private ArrayList<StateResPayload> stateResPayload;
    private TextWatcher textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private ArrayList<ArrayList<UpdateBillingInfoResPayload>> updateBillingInfoResPayload;
    private int userId;
    private String zipFourCode;
    int len = 0;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_info_screen);
        etAdd1 = (EditText) findViewById(R.id.etAdd1);
        etAdd2 = (EditText) findViewById(R.id.etAdd2);
        etCity = (EditText) findViewById(R.id.etCity);
        etContact = (EditText) findViewById(R.id.etContact);
        spState = (Spinner) findViewById(R.id.spState);
        spCountry = (Spinner) findViewById(R.id.spCountry);
        etZipcode = (EditText) findViewById(R.id.etZipcode);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        btn_save_info_inactive = (Button) findViewById(R.id.btn_save_info_inactive);
        btn_save_info_active = (Button) findViewById(R.id.btn_save_info_active);
        etCity.addTextChangedListener(textwatcher);
        etAdd1.addTextChangedListener(textwatcher);
        etAdd2.addTextChangedListener(textwatcher);
        etZipcode.addTextChangedListener(textwatcher);
        etContact.addTextChangedListener(textwatcher);
        etContact.addTextChangedListener(new PhoneNumberTextWatcher(etContact));
        if (checkInternet()) {
            getStateService();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        userId = prefsData.getInt("userId", 1);
        etAdd1.setText(prefsData.getString("address1", ""));
        etAdd2.setText(prefsData.getString("address2", ""));
        etCity.setText(prefsData.getString("city", ""));
        etZipcode.setText(prefsData.getString("zipCode", ""));
        zipFourCode = prefsData.getString("zip4Code", "");
        etContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String str = etContact.getText().toString();
                if (str.length() == 4 && len < str.length()) {
                    etContact.append("-");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                len = etContact.getText().toString().length();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ArrayList<String> countryList = new ArrayList();
        countryList.add(0, "Country");
        countryList.add(1, "United States");
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, 17367048, 16908308, countryList) {
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
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCountry.setAdapter(countryAdapter);
        btn_save_info_active.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etZipcode.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter zip code", Toast.LENGTH_SHORT).show();
                } else if (etZipcode.getText().toString().trim().length() < 3) {
                    Toast.makeText(getApplicationContext(), "Please enter valid zip code", Toast.LENGTH_SHORT).show();
                } else if (checkInternet()) {
                    updateBillingInfoService();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkFieldsForEmptyValues() {
        String add1 = etAdd1.getText().toString();
        String city = etCity.getText().toString();
        String zipCode = etZipcode.getText().toString();
        if (add1.equals("") || city.equals("") || zipCode.equals("")) {
            btn_save_info_inactive.setVisibility(View.VISIBLE);
            btn_save_info_active.setVisibility(View.GONE);
            return;
        }
        btn_save_info_inactive.setVisibility(View.GONE);
        btn_save_info_active.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                    stateResPayload = (ArrayList) response.body();
                    fullFormList = new ArrayList();
                    fullFormList.add(0, "State");
                    for (int i = 1; i < stateResPayload.size(); i++) {
                        fullFormList.add(((StateResPayload) stateResPayload.get(i)).getFacState());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BillingInfoActivity.this, android.R.layout.simple_selectable_list_item, fullFormList) {
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
                    spState.setAdapter(dataAdapter);
                    spState.setSelection(prefsData.getInt("state", 0));
                    spState.setSelection(fullFormList.indexOf(prefsData.getString("stateName", "")));
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StateResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(BillingInfoActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateBillingInfoService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) new Builder().baseUrl(Application_holder.AUTH_BASE_URL).client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build().create(ApiInterface.class);
        ArrayList<UpdateBillingInfoReqPayload> billingInfoReqPayloads = new ArrayList();
        UpdateBillingInfoReqPayload updateBillingInfoReqPayload = new UpdateBillingInfoReqPayload();
        updateBillingInfoReqPayload.setUserId(String.valueOf(userId));
        updateBillingInfoReqPayload.setAddress1(etAdd1.getText().toString());
        updateBillingInfoReqPayload.setAddress2(etAdd2.getText().toString());
        updateBillingInfoReqPayload.setCity(etCity.getText().toString());
        updateBillingInfoReqPayload.setState(spState.getSelectedItemPosition() == 0 ? "" : spState.getSelectedItem().toString());
        updateBillingInfoReqPayload.setZipcode(etZipcode.getText().toString());
        updateBillingInfoReqPayload.setZipFourCode(zipFourCode);
        updateBillingInfoReqPayload.setCountry(spCountry.getSelectedItemPosition() == 0 ? "" : spCountry.getSelectedItem().toString());
        billingInfoReqPayloads.add(updateBillingInfoReqPayload);
        apiService.updateBillingInfo(billingInfoReqPayloads).enqueue(new Callback<ArrayList<ArrayList<UpdateBillingInfoResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> call, Response<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    updateBillingInfoResPayload = (ArrayList) response.body();
                    Editor editor = prefsData.edit();
                    editor.putString("address1", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getAddress1());
                    editor.putString("address2", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getAddress2());
                    editor.putString("city", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getCity());
                    editor.putInt("state", spState.getSelectedItemPosition());
                    editor.putString("stateName", spState.getSelectedItem().toString());
                    editor.putString("zipCode", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getZipcode());
                    editor.putString("zip4Code", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getZipFourCode());
                    editor.putString("country", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getCountry());
                    editor.commit();
                    startActivity(new Intent(BillingInfoActivity.this, SubscriptionActivity.class));
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }
}
