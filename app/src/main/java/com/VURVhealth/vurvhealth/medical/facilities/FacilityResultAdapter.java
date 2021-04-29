package com.VURVhealth.vurvhealth.medical.facilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDeatilResPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDetailReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 25/1/17.
 */

public class FacilityResultAdapter extends RecyclerView.Adapter<FacilityResultAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private ArrayList<SearchFacilitiesResPayLoad> mDataset;
    private ProgressDialog pDialog;
    private String subFacility;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        ImageView saved_item;
        TextView tvAddress;
        TextView tvDoctorName;
        TextView tvGender;
        TextView tvLanguage;
        TextView tvSpecialty;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view1);
            this.tvDoctorName = (TextView) itemView.findViewById(R.id.tvDoctorName);
            this.tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            this.tvSpecialty = (TextView) itemView.findViewById(R.id.tvSpecialty);
            this.tvLanguage = (TextView) itemView.findViewById(R.id.tvLanguage);
            this.tvGender = (TextView) itemView.findViewById(R.id.tvGender);
            this.saved_item = (ImageView) itemView.findViewById(R.id.saved_item);
            Log.i(FacilityResultAdapter.LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            FacilityResultAdapter.myClickListener.onItemClick(getPosition(), v);
        }
    }

    public interface MyClickListener {
        void onItemClick(int i, View view);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public FacilityResultAdapter(Context context, ArrayList<SearchFacilitiesResPayLoad> myDataset, String subFacility) {
        this.mDataset = myDataset;
        this.context = context;
        this.subFacility = subFacility;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_seach_list_inflator, parent, false));
    }

    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        if (((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getSavedItem() == 1) {
            holder.saved_item.setVisibility(View.VISIBLE);
        } else {
            holder.saved_item.setVisibility(View.GONE);
        }
        holder.tvSpecialty.setText(((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getFacilityType());
        holder.tvDoctorName.setText(((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getFacilityName());
//        holder.tvAddress.setText(((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getAddress().substring(0, ((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getAddress().indexOf(",")) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getAddress().substring(((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getAddress().indexOf(",") + 1));
//


        String[] namesList = mDataset.get(position).getAddress().split(",");

        String address1 = namesList [0];
        String city = namesList [1];
        String state = namesList [2];
        String statezip = namesList [3];

        holder.tvAddress.setText(city + "," + state +statezip);



        holder.tvLanguage.setVisibility(View.GONE);
        holder.tvGender.setVisibility(View.GONE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FacilityResultAdapter.this.getFacilityDetails(position);
            }
        });
    }

    public void deleteItem(int index) {
        this.mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public int getItemCount() {
        return this.mDataset.size();
    }

    protected void showProgressDialog(Context context) {
        this.pDialog = new ProgressDialog(context);
        this.pDialog.setMessage(context.getResources().getString(R.string.please_wait));
        this.pDialog.setCancelable(false);
        this.pDialog.show();
    }

    protected void dismissProgressDialog() {
        if (this.pDialog != null) {
            this.pDialog.dismiss();
        }
    }

    private void getFacilityDetails(final int position) {
        showProgressDialog(this.context);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this.context).create(ApiInterface.class);
        FacilityDetailReqPayload facilityDetailReqPayload = new FacilityDetailReqPayload();
        facilityDetailReqPayload.setFacilityProviderId(((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getProviderID());
        Application_holder.providerId = ((SearchFacilitiesResPayLoad) this.mDataset.get(position)).getProviderID();
        ArrayList<FacilityDetailReqPayload> reqPayLoads = new ArrayList();
        reqPayLoads.add(facilityDetailReqPayload);
        apiService.getFacilityDetailsService(reqPayLoads).enqueue(new Callback<ArrayList<FacilityDeatilResPayload>>() {
            public void onResponse(Call<ArrayList<FacilityDeatilResPayload>> call, Response<ArrayList<FacilityDeatilResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<FacilityDeatilResPayload> facilityDeatilResPayloads = (ArrayList) response.body();
                    FacilityResultAdapter.this.dismissProgressDialog();
                    Intent intent = new Intent(FacilityResultAdapter.this.context, FacilityResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    intent.putExtra("facilityName", ((SearchFacilitiesResPayLoad) FacilityResultAdapter.this.mDataset.get(position)).getFacilityName());
                    b.putParcelable("SearchResultObject", (Parcelable) facilityDeatilResPayloads.get(0));
                    b.putInt("position", position);
                    b.putString("activity", "FacilitySearchResults");
                    b.putInt("savedItem", ((SearchFacilitiesResPayLoad) FacilityResultAdapter.this.mDataset.get(position)).isSavedStatus());
                    intent.putExtras(b);
                    FacilityResultAdapter.this.context.startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<FacilityDeatilResPayload>> call, Throwable t) {
                Toast.makeText(FacilityResultAdapter.this.context, FacilityResultAdapter.this.context.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                FacilityResultAdapter.this.dismissProgressDialog();
            }
        });
    }
}
