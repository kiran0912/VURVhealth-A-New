package com.VURVhealth.vurvhealth.save;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.StartScreenActivity;
import com.VURVhealth.vurvhealth.althealth.AltHealthSearchDetailsActivity;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSProviderDetailsRespayload;
import com.VURVhealth.vurvhealth.dental.DentalSearchDetailActivity;
import com.VURVhealth.vurvhealth.dental.pojos.DentalProviderDetailsResPayload;
import com.VURVhealth.vurvhealth.freshdesk_help.FreshdeskMainListActivity;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorResPayLoad;
import com.VURVhealth.vurvhealth.medical.doctors.DoctorsResultDetailsActivity;
import com.VURVhealth.vurvhealth.medical.facilities.FacilityResultDetailsActivity;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDeatilResPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDetailReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterDoctors;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterFacility;
import com.VURVhealth.vurvhealth.myProfile.PrimaryAcntHolderActivity;
import com.VURVhealth.vurvhealth.prescriptions.DrugSearchResultResPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.PrescriptionResultsDetailsActivity;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.save.pojos.PrescriptionDetailsRequestPojo;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterAllResponsePojo;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterRequestPojo;
import com.VURVhealth.vurvhealth.save.pojos.SavedItemDetailReqPayload;
import com.VURVhealth.vurvhealth.save.pojos.SavedItemDetailResPayload;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.VisionSearchDetailsActivity;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.VURVhealth.vurvhealth.vision.pojos.VisionProviderIdResPayload;
import com.VURVhealth.vurvhealth.vurvidpackages.VurvPackageActivity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveItemActivity extends SuperAppCompactActivity {
    private LinearLayout llHelp;
    private LinearLayout llProfile;
    private LinearLayout llSearch;
    private LinearLayout llVurv;
    private Adapter<SavedItemAdapter.DataObjectHolder> mAdapter;
    private LayoutManager mLayoutManager;
    private TextView name;
    private ArrayList<SavedItemDetailResPayload> resPayload;
    RecyclerView rv_saved_items;

    
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_items_screen);
       llSearch = (LinearLayout) findViewById(R.id.llSearch);
       llVurv = (LinearLayout) findViewById(R.id.llVurv);
       llProfile = (LinearLayout) findViewById(R.id.llProfile);
       llHelp = (LinearLayout) findViewById(R.id.llHelp);
       name = (TextView) findViewById(R.id.name);
       rv_saved_items = (RecyclerView) findViewById(R.id.rv_saved_items);
       mLayoutManager = new LinearLayoutManager(getApplicationContext());
       rv_saved_items.setLayoutManager(this.mLayoutManager);
       rv_saved_items.setItemAnimator(new DefaultItemAnimator());
       mAdapter = new SavedItemAdapter(SaveItemActivity.this, NoSavedItemActivity.saveForLaterAllResponsePojos);
       name.setText(getResources().getString(R.string.saved));
       rv_saved_items.setAdapter(this.mAdapter);
       llSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaveItemActivity.this, StartScreenActivity.class));
                finish();
            }
        });
       llVurv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaveItemActivity.this, VurvPackageActivity.class));
                finish();
            }
        });
       llProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaveItemActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
       llHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaveItemActivity.this, FreshdeskMainListActivity.class));
//                startActivity(new Intent(SaveItemActivity.this, HelpActivity.class));
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
    }

    public void deleteSaveForLaterDoctors(final int position) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        String[] address = ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getDetail().split(",");
        ArrayList<SaveForLaterDoctors> saveForLaterRequestList = new ArrayList();
        SaveForLaterDoctors saveForLaterRequest = new SaveForLaterDoctors();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setPractitionerProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getId());
        saveForLaterRequest.setFlag("0");
        if (address.length > 3) {
            saveForLaterRequest.setZipCode(address[3].trim());
            saveForLaterRequest.setCity(address[1].trim());
            saveForLaterRequest.setState(address[2].trim());
        } else {
            saveForLaterRequest.setZipCode("");
            saveForLaterRequest.setCity("");
            saveForLaterRequest.setState("");
        }
        saveForLaterRequest.setLocationKey(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getDetail1());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDoctor(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                if (response.isSuccessful()) {
                    ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                    NoSavedItemActivity.saveForLaterAllResponsePojos.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() == 0) {
                        startActivity(new Intent(SaveItemActivity.this, NoSavedItemActivity.class));
                        finish();
                    }
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterFacility(final int position) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterFacility> saveForLaterRequestList = new ArrayList();
        SaveForLaterFacility saveForLaterRequest = new SaveForLaterFacility();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setFacilityProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterFacility(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                NoSavedItemActivity.saveForLaterAllResponsePojos.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterVisionService(final int position) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterVision(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                NoSavedItemActivity.saveForLaterAllResponsePojos.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterAHSService(final int position) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterAHS(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                NoSavedItemActivity.saveForLaterAllResponsePojos.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterDentalService(final int position) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterVision> saveForLaterRequestList = new ArrayList();
        SaveForLaterVision saveForLaterRequest = new SaveForLaterVision();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(position)).getId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLaterDental(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                NoSavedItemActivity.saveForLaterAllResponsePojos.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
                ArrayList<StatusResponseForTotalProject> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void deleteSaveForLaterPrescriptionService(final int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SaveForLaterRequestPojo> saveForLaterRequestList = new ArrayList();
        SaveForLaterRequestPojo saveForLaterRequest = new SaveForLaterRequestPojo();
        saveForLaterRequest.setUserId(String.valueOf(this.prefsLoginData.getInt("userId", 1)));
        saveForLaterRequest.setFlag("0");
        saveForLaterRequest.setNCPDP(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        saveForLaterRequest.setPrice("");
        saveForLaterRequest.setNDC("0");
        saveForLaterRequest.setGNRCALTNDC("");
        saveForLaterRequest.setIsLeast("");
        saveForLaterRequest.setDrugType("");
        saveForLaterRequest.setDrugName("");
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.saveForLater(saveForLaterRequestList).enqueue(new Callback<ArrayList<StatusResponseForTotalProject>>() {
            public void onResponse(Call<ArrayList<StatusResponseForTotalProject>> call, Response<ArrayList<StatusResponseForTotalProject>> response) {
                if (response.isSuccessful()) {
                    NoSavedItemActivity.saveForLaterAllResponsePojos.remove(pos);
                    mAdapter.notifyItemRemoved(pos);
                    mAdapter.notifyDataSetChanged();
                    ArrayList arrayList = (ArrayList) response.body();
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<StatusResponseForTotalProject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void viewSaveForLaterDental(int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        Application_holder.providerId = ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getDentalProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<DentalProviderDetailsResPayload>>() {
            public void onResponse(Call<ArrayList<DentalProviderDetailsResPayload>> call, Response<ArrayList<DentalProviderDetailsResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DentalProviderDetailsResPayload> facilityDeatilResPayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(SaveItemActivity.this, DentalSearchDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) facilityDeatilResPayloads.get(0));
                    b.putString("activity", "SavedItemActivity");
                    b.putInt("savedItem", 1);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<DentalProviderDetailsResPayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void viewSaveForLaterFacility(final int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        FacilityDetailReqPayload facilityDetailReqPayload = new FacilityDetailReqPayload();
        facilityDetailReqPayload.setFacilityProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        Application_holder.providerId = ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId();
        ArrayList<FacilityDetailReqPayload> reqPayLoads = new ArrayList();
        reqPayLoads.add(facilityDetailReqPayload);
        apiService.getFacilityDetailsService(reqPayLoads).enqueue(new Callback<ArrayList<FacilityDeatilResPayload>>() {
            public void onResponse(Call<ArrayList<FacilityDeatilResPayload>> call, Response<ArrayList<FacilityDeatilResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<FacilityDeatilResPayload> facilityDeatilResPayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(SaveItemActivity.this, FacilityResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    intent.putExtra("facilityName", ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getName());
                    b.putParcelable("SearchResultObject", (Parcelable) facilityDeatilResPayloads.get(0));
                    b.putString("activity", "SavedItemActivity");
                    b.putInt("savedItem", 1);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<FacilityDeatilResPayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void viewSaveForLaterVisionService(int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad searchForVisionReqPayload = new AboutDoctorReqPayLoad();
        searchForVisionReqPayload.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        Application_holder.providerId = ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(searchForVisionReqPayload);
        apiService.getVisionProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<VisionProviderIdResPayload>>() {
            public void onResponse(Call<ArrayList<VisionProviderIdResPayload>> call, Response<ArrayList<VisionProviderIdResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VisionProviderIdResPayload> visionProviderIdResPayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(SaveItemActivity.this, VisionSearchDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) visionProviderIdResPayloads.get(0));
                    b.putInt("savedItem", 1);
                    b.putString("activity", "savedItemActivity");
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<VisionProviderIdResPayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void viewSaveForLaterDoctors(final int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        Application_holder.providerId = ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getAboutDoctorsService(reqPayLoads).enqueue(new Callback<ArrayList<AboutDoctorResPayLoad>>() {
            public void onResponse(Call<ArrayList<AboutDoctorResPayLoad>> call, Response<ArrayList<AboutDoctorResPayLoad>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AboutDoctorResPayLoad> aboutDoctorResPayLoads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(SaveItemActivity.this, DoctorsResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) aboutDoctorResPayLoads.get(0));
                    b.putInt("savedItem", 1);
                    b.putString("activity", "savedItemActivity");
                    b.putString("locationKey", ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getDetail1());
                    b.putString("providerId", ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<AboutDoctorResPayLoad>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void viewSaveForLaterPrescription1(final int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<SavedItemDetailReqPayload> savedItemDetailReqPayloads = new ArrayList();
        SavedItemDetailReqPayload saveForLaterRequest = new SavedItemDetailReqPayload();
        saveForLaterRequest.setSaveForLaterId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getDetail1());
        savedItemDetailReqPayloads.add(saveForLaterRequest);
        apiService.getSavedItemDetails(savedItemDetailReqPayloads).enqueue(new Callback<ArrayList<SavedItemDetailResPayload>>() {
            public void onResponse(Call<ArrayList<SavedItemDetailResPayload>> call, Response<ArrayList<SavedItemDetailResPayload>> response) {
                if (response.isSuccessful()) {
                    resPayload = (ArrayList) response.body();
                    dismissProgressDialog();
                    viewSaveForLaterPrescription(pos);
                }
            }

            public void onFailure(Call<ArrayList<SavedItemDetailResPayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void viewSaveForLaterPrescription(int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        ArrayList<PrescriptionDetailsRequestPojo> saveForLaterRequestList = new ArrayList();
        PrescriptionDetailsRequestPojo saveForLaterRequest = new PrescriptionDetailsRequestPojo();
        saveForLaterRequest.setPharmacyId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        saveForLaterRequestList.add(saveForLaterRequest);
        apiService.prescriptionDeatails(saveForLaterRequestList).enqueue(new Callback<ArrayList<DrugSearchResultResPayLoad.Datum>>() {
            public void onResponse(Call<ArrayList<DrugSearchResultResPayLoad.Datum>> call, Response<ArrayList<DrugSearchResultResPayLoad.Datum>> response) {
                ArrayList<DrugSearchResultResPayLoad.Datum> insertRecentSearchRespPayLoad = (ArrayList) response.body();
                ( insertRecentSearchRespPayLoad.get(0)).setDrugType(((SavedItemDetailResPayload) resPayload.get(0)).getDrugType());
//   srikanth             ( insertRecentSearchRespPayLoad.get(0)).setPrice("" + Double.parseDouble(((SavedItemDetailResPayload) resPayload.get(0)).getPrice()));
                ( insertRecentSearchRespPayLoad.get(0)).setPrice("" + ((SavedItemDetailResPayload) resPayload.get(0)).getPrice());
                ( insertRecentSearchRespPayLoad.get(0)).setNCPDP(((SavedItemDetailResPayload) resPayload.get(0)).getNCPDP());
                (insertRecentSearchRespPayLoad.get(0)).setSavedItem(true);
                dismissProgressDialog();
                Intent intent = new Intent(SaveItemActivity.this, PrescriptionResultsDetailsActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("SearchResultObject", (Parcelable) insertRecentSearchRespPayLoad.get(0));
                b.putInt("position", -1);
                b.putString("activity", "SavedItemActivity");
                intent.putExtras(b);
                startActivity(intent);
            }

            public void onFailure(Call<ArrayList<DrugSearchResultResPayLoad.Datum>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void viewSaveForLaterAHSService(int pos) {
        showProgressDialog(this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this).create(ApiInterface.class);
        AboutDoctorReqPayLoad searchForVisionReqPayload = new AboutDoctorReqPayLoad();
        searchForVisionReqPayload.setProviderId(((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId());
        Application_holder.providerId = ((SaveForLaterAllResponsePojo) NoSavedItemActivity.saveForLaterAllResponsePojos.get(pos)).getId();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(searchForVisionReqPayload);
        apiService.getAHSProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<AHSProviderDetailsRespayload>>() {
            public void onResponse(Call<ArrayList<AHSProviderDetailsRespayload>> call, Response<ArrayList<AHSProviderDetailsRespayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AHSProviderDetailsRespayload> visionProviderIdResPayloads = (ArrayList) response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(SaveItemActivity.this, AltHealthSearchDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) visionProviderIdResPayloads.get(0));
                    b.putInt("savedItem", 1);
                    b.putString("activity", "savedItemActivity");
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<AHSProviderDetailsRespayload>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon((int) R.drawable.vurv_logo_r).setTitle((CharSequence) getString(R.string.app_name)).setMessage((CharSequence) "Are you sure you want to close this App?").setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) null).show();
    }
}
