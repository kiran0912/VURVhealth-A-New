package com.VURVhealth.vurvhealth.dental;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.dental.pojos.DentalProviderDetailsResPayload;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 18/3/17.
 */

public class DentalListAdapter extends RecyclerView.Adapter<DentalListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private List<SearchForDentalResPayLoad.Datum> mDataset;
    private ProgressDialog pDialog;
    private SharedPreferences prefsLoginData;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ImageView saved_item;
        private TextView tvAddress;
        private TextView tvDoctorName;
        private TextView tvGender;
        private TextView tvLanguage;
        private TextView tvSpecialty;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view1);
            this.tvDoctorName = (TextView) itemView.findViewById(R.id.tvDoctorName);
            this.tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            this.tvSpecialty = (TextView) itemView.findViewById(R.id.tvSpecialty);
            this.tvLanguage = (TextView) itemView.findViewById(R.id.tvLanguage);
            this.tvGender = (TextView) itemView.findViewById(R.id.tvGender);
            this.saved_item = (ImageView) itemView.findViewById(R.id.saved_item);
            Log.i(DentalListAdapter.LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
        }
    }

    public interface MyClickListener {
        void onItemClick(int i, View view);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public DentalListAdapter(Context context, List<SearchForDentalResPayLoad.Datum> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_seach_list_inflator, parent, false));
    }

    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.tvGender.setVisibility(View.GONE);
        holder.tvLanguage.setVisibility(View.GONE);
        if (((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).isSavedStatus() == 1) {
            holder.saved_item.setVisibility(View.GONE);
        } else {
            holder.saved_item.setVisibility(View.GONE);
        }
        holder.tvDoctorName.setText(((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getMidInitName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getLastName());
//        holder.tvAddress.setText(((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getAdd1() + ", " + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getCity() + ", " + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getState() + ", " + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getZipCode());

        holder.tvAddress.setText(((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getCity()
                + ", " + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getState()
               +" " + ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getZipCode());




        holder.tvSpecialty.setText(((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getSpec());
//        holder.tvSpecialty.setText(((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getLanguage());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DentalListAdapter.this.getDentalProviderDetails(position);
            }
        });
    }

    public void addItem(SearchForDentalResPayLoad.Datum dataObj, int index) {
        this.mDataset.add(dataObj);
        notifyItemInserted(index);
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

    private void getDentalProviderDetails(final int position) {
        showProgressDialog(this.context);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this.context).create(ApiInterface.class);
        AboutDoctorReqPayLoad aboutDoctorReqPayLoad = new AboutDoctorReqPayLoad();
        aboutDoctorReqPayLoad.setProviderId(((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getProviderId());
        Application_holder.providerId = ((SearchForDentalResPayLoad.Datum) this.mDataset.get(position)).getProviderId();
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(aboutDoctorReqPayLoad);
        apiService.getDentalProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<DentalProviderDetailsResPayload>>() {
            public void onResponse(Call<ArrayList<DentalProviderDetailsResPayload>> call, Response<ArrayList<DentalProviderDetailsResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DentalProviderDetailsResPayload> facilityDeatilResPayloads = (ArrayList) response.body();
                    DentalListAdapter.this.dismissProgressDialog();
                    Intent intent = new Intent(DentalListAdapter.this.context, DentalSearchDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) facilityDeatilResPayloads.get(0));
                    b.putInt("position", position);
                    b.putString("activity", "DentalListActivity");
                    b.putInt("savedItem", ((SearchForDentalResPayLoad.Datum) DentalListAdapter.this.mDataset.get(position)).isSavedStatus());
                    intent.putExtras(b);
                    DentalListAdapter.this.context.startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<DentalProviderDetailsResPayload>> call, Throwable t) {
                Toast.makeText(DentalListAdapter.this.context, DentalListAdapter.this.context.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                DentalListAdapter.this.dismissProgressDialog();
            }
        });
    }
}
