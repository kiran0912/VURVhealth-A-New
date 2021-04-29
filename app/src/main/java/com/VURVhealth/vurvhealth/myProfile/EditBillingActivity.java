package com.VURVhealth.vurvhealth.myProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.medical.pojos.StateReqPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.pojos.StateResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yqlabs on 1/3/17.
 */

public class EditBillingActivity extends SuperAppCompactActivity {
    private EditText etAdd1;
    private EditText etAdd2;
    private EditText etCity;
    private EditText etCountry;
    private EditText etZip4;
    private EditText etZipcode;
    private SharedPreferences prefsData;
    private Spinner spState;
    private ArrayList<StateResPayload> stateResPayload;
    private TextView tvCancel;
    private TextView tvDone;
    private ArrayList<ArrayList<UpdateBillingInfoResPayload>> updateBillingInfoResPayload;
    private int userId;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_billing_info);
       tvDone = (TextView) findViewById(R.id.tvDone);
       tvCancel = (TextView) findViewById(R.id.tvCancel);
       etAdd1 = (EditText) findViewById(R.id.etAdd1);
       etAdd2 = (EditText) findViewById(R.id.etAdd2);
       etCity = (EditText) findViewById(R.id.etCity);
       spState = (Spinner) findViewById(R.id.spState);
       etZipcode = (EditText) findViewById(R.id.etZipcode);
       etZip4 = (EditText) findViewById(R.id.etZip4);
       etCountry = (EditText) findViewById(R.id.etCountry);
        if (checkInternet()) {
            getStateService();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
       spState.setAdapter(dataAdapter);
       prefsData = getSharedPreferences("VURVProfileDetails", 0);
       userId =prefsData.getInt("userId", 1);
       etAdd1.setText(prefsData.getString("address1", ""));
       etAdd2.setText(prefsData.getString("address2", ""));
       etCity.setText(prefsData.getString("city", ""));
       etZipcode.setText(prefsData.getString("zipCode", ""));
       etZip4.setText(prefsData.getString("zip4Code", ""));
       tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAdd1.getText().toString().length() == 0) {
                    Toast.makeText(EditBillingActivity.this, R.string.enter_adrs1, Toast.LENGTH_SHORT).show();
                } else if (etCity.getText().toString().length() == 0) {
                    Toast.makeText(EditBillingActivity.this, R.string.enter_city, Toast.LENGTH_SHORT).show();
                } else if (spState.getSelectedItemPosition() == 0) {
                    Toast.makeText(EditBillingActivity.this, R.string.enter_state, Toast.LENGTH_SHORT).show();
                } else if (etZipcode.getText().toString().length() == 0) {
                    Toast.makeText(EditBillingActivity.this, R.string.enter_zip, Toast.LENGTH_SHORT).show();
                } else if (etZipcode.getText().toString().trim().length() < 3) {
                    Toast.makeText(getApplicationContext(), R.string.enter_valid_zip, Toast.LENGTH_SHORT).show();
                } else if (checkInternet()) {
                    updateBillingInfoService();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                }
            }
        });
       tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditBillingActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
    }

    private void updateBillingInfoService() {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) new Retrofit.Builder().baseUrl(Application_holder.AUTH_BASE_URL).client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build().create(ApiInterface.class);
        ArrayList<UpdateBillingInfoReqPayload> billingInfoReqPayloads = new ArrayList();
        UpdateBillingInfoReqPayload updateBillingInfoReqPayload = new UpdateBillingInfoReqPayload();
        updateBillingInfoReqPayload.setUserId(String.valueOf(userId));
        updateBillingInfoReqPayload.setAddress1(etAdd1.getText().toString());
        updateBillingInfoReqPayload.setAddress2(etAdd2.getText().toString());
        updateBillingInfoReqPayload.setCity(etCity.getText().toString());
        updateBillingInfoReqPayload.setState(spState.getSelectedItemPosition() == 0 ? "" :spState.getSelectedItem().toString());
        updateBillingInfoReqPayload.setZipcode(etZipcode.getText().toString());
        updateBillingInfoReqPayload.setZipFourCode(etZip4.getText().toString());
        updateBillingInfoReqPayload.setCountry(etCountry.getText().toString());
        billingInfoReqPayloads.add(updateBillingInfoReqPayload);
        apiService.updateBillingInfo(billingInfoReqPayloads).enqueue(new Callback<ArrayList<ArrayList<UpdateBillingInfoResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> call, Response<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    updateBillingInfoResPayload = (ArrayList) response.body();
                    SharedPreferences.Editor editor = prefsData.edit();
                    editor.putString("address1", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getAddress1());
                    editor.putString("address2", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getAddress2());
                    editor.putString("city", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getCity());
                    editor.putInt("state", spState.getSelectedItemPosition());
                    editor.putString("stateName", spState.getSelectedItem().toString());
                    editor.putString("zipCode", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getZipcode());
                    editor.putString("zip4Code", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getZipFourCode());
                    editor.putString("country", ((UpdateBillingInfoResPayload) ((ArrayList) updateBillingInfoResPayload.get(0)).get(0)).getCountry());
                    editor.commit();
                    startActivity(new Intent(EditBillingActivity.this, PrimaryAcntHolderActivity.class));
                    finish();
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
                stateResPayload = (ArrayList) response.body();
                fullFormList = new ArrayList();
                fullFormList.add(0, getResources().getString(R.string.state));
                for (int i = 1; i < stateResPayload.size(); i++) {
                    fullFormList.add(((StateResPayload) stateResPayload.get(i)).getFacState());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditBillingActivity.this, android.R.layout.simple_selectable_list_item, fullFormList) {
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

            @Override
            public void onFailure(Call<ArrayList<StateResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(EditBillingActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
