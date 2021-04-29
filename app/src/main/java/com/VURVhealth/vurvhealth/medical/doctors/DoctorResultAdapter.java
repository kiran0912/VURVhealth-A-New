package com.VURVhealth.vurvhealth.medical.doctors;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yqlabs on 24/1/17.
 */

public class DoctorResultAdapter extends RecyclerView.Adapter<DoctorResultAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private ArrayList<SearchPractitionerResPayLoad> mDataset;
    private ProgressDialog pDialog;

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
            Log.i(DoctorResultAdapter.LOG_TAG, "Adding Listener");
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

    public DoctorResultAdapter(Context context, ArrayList<SearchPractitionerResPayLoad> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_seach_list_inflator, parent, false));
    }

    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        if (((SearchPractitionerResPayLoad) this.mDataset.get(position)).getGender() != null) {
            if (((SearchPractitionerResPayLoad) this.mDataset.get(position)).isSavedStatus() == 1) {
                holder.saved_item.setVisibility(View.VISIBLE);
            } else {
                holder.saved_item.setVisibility(View.GONE);
            }
            holder.tvDoctorName.setText(((SearchPractitionerResPayLoad) this.mDataset.get(position)).getName() + ", " + ((SearchPractitionerResPayLoad) this.mDataset.get(position)).getClinicalEducation());
//            holder.tvAddress.setText(((SearchPractitionerResPayLoad) this.mDataset.get(position)).getAddress().substring(0, ((SearchPractitionerResPayLoad) this.mDataset.get(position)).getAddress().indexOf(",")) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((SearchPractitionerResPayLoad) this.mDataset.get(position)).getAddress().substring(((SearchPractitionerResPayLoad) this.mDataset.get(position)).getAddress().indexOf(",") + 1));

            String[] namesList = mDataset.get(position).getAddress().split(",");

            String address1 = namesList [0];
            String city = namesList [1];
            String state = namesList [2];
            String statezip = namesList [3];

          holder.tvAddress.setText(city + "," + state +statezip);



            holder.tvSpecialty.setText(((SearchPractitionerResPayLoad) this.mDataset.get(position)).getSpeciality() == null ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : ((SearchPractitionerResPayLoad) this.mDataset.get(position)).getSpeciality());
            holder.tvLanguage.setText(((SearchPractitionerResPayLoad) this.mDataset.get(position)).getLanguage() == null ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : ((SearchPractitionerResPayLoad) this.mDataset.get(position)).getLanguage());
            holder.tvGender.setText(((SearchPractitionerResPayLoad) this.mDataset.get(position)).getGender().equals("M") ? this.context.getResources().getString(R.string.male) : this.context.getResources().getString(R.string.female));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorResultAdapter.this.context, DoctorsResultDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("position", position);
                    b.putString("activity", "DoctorSearchResult");
                    b.putInt("savedItem", ((SearchPractitionerResPayLoad) DoctorResultAdapter.this.mDataset.get(position)).isSavedStatus());
                    b.putString("locationKey", ((SearchPractitionerResPayLoad) DoctorResultAdapter.this.mDataset.get(position)).getLocationKey());
                    b.putString("providerId", ((SearchPractitionerResPayLoad) DoctorResultAdapter.this.mDataset.get(position)).getProviderID());
                    intent.putExtras(b);
                    DoctorResultAdapter.this.context.startActivity(intent);
                    Application_holder.providerId = ((SearchPractitionerResPayLoad) DoctorResultAdapter.this.mDataset.get(position)).getProviderID();
                }
            });
        }
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
}
