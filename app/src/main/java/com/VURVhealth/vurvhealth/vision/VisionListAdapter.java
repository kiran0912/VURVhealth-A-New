package com.VURVhealth.vurvhealth.vision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;

import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionProviderIdResPayload;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionListAdapter extends Adapter<VisionListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private List<SearchForVisionResPayload.Datum> mDataset;
    private ProgressDialog pDialog;

    public static class DataObjectHolder extends ViewHolder implements OnClickListener {
        private CardView cardView;
        ImageView saved_item;
        TextView tvAddress;
        TextView tvPharmacy;
        TextView tvPrice;
        TextView tvSaving;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.tvPharmacy = (TextView) itemView.findViewById(R.id.tvPharmacy);
            this.tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            this.tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            this.tvSaving = (TextView) itemView.findViewById(R.id.tvSaving);
            this.saved_item = (ImageView) itemView.findViewById(R.id.saved_item);
            Log.i(VisionListAdapter.LOG_TAG, "Adding Listener");
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

    public VisionListAdapter(Context context, ArrayList<SearchForVisionResPayload.Datum> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_list_inflator, parent, false));
    }



    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        if (((SearchForVisionResPayload.Datum) this.mDataset.get(position)).isSavedStatus() == 1) {
            holder.saved_item.setVisibility(View.VISIBLE);
        } else {
            holder.saved_item.setVisibility(View.GONE);
        }
        holder.tvPrice.setVisibility(View.GONE);
        holder.tvSaving.setVisibility(View.GONE);


        holder.tvPrice.setText(((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getDoctarLanguage());
        holder.tvSaving.setText(((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getGender());



        holder.tvPharmacy.setText(((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getFullName());
//        holder.tvAddress.setText(((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getAddressLine1() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getCity() + ", " + ((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getState() + ", " + ((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getZipCode());
        holder.tvAddress.setText(((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getCity()
                + ", " + ((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getState()
                + " " + ((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getZipCode());
        holder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VisionListAdapter.this.getProviderDetails(position);
            }
        });
    }

    public void addItem(SearchForVisionResPayload.Datum dataObj, int index) {
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

    private void getProviderDetails(final int position) {
        showProgressDialog(this.context);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(this.context).create(ApiInterface.class);
        AboutDoctorReqPayLoad searchForVisionReqPayload = new AboutDoctorReqPayLoad();
        searchForVisionReqPayload.setProviderId(((SearchForVisionResPayload.Datum) this.mDataset.get(position)).getVisProviderId());
        ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList();
        reqPayLoads.add(searchForVisionReqPayload);
        apiService.getVisionProviderDetails(reqPayLoads).enqueue(new Callback<ArrayList<VisionProviderIdResPayload>>() {
            public void onResponse(Call<ArrayList<VisionProviderIdResPayload>> call, Response<ArrayList<VisionProviderIdResPayload>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VisionProviderIdResPayload> visionProviderIdResPayloads = (ArrayList) response.body();
                    VisionListAdapter.this.dismissProgressDialog();
                    Intent intent = new Intent(VisionListAdapter.this.context, VisionSearchDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", (Parcelable) visionProviderIdResPayloads.get(0));
                    b.putInt("position", position);
                    b.putString("activity", "VisionListActivity");
                    b.putInt("savedItem", ((SearchForVisionResPayload.Datum) VisionListAdapter.this.mDataset.get(position)).isSavedStatus());
                    b.putDouble("latitude", mDataset.get(position).getLatitude());
                    b.putDouble("longitude", mDataset.get(position).getLongitude());
                    intent.putExtras(b);
                    VisionListAdapter.this.context.startActivity(intent);
                }
            }

            public void onFailure(Call<ArrayList<VisionProviderIdResPayload>> call, Throwable t) {
                Toast.makeText(VisionListAdapter.this.context, VisionListAdapter.this.context.getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                Log.e("", t.toString());
                VisionListAdapter.this.dismissProgressDialog();
            }
        });
    }
}
