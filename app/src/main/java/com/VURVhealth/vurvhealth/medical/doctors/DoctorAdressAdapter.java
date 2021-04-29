package com.VURVhealth.vurvhealth.medical.doctors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorResPayLoad;

import java.util.ArrayList;

public class DoctorAdressAdapter extends Adapter<DoctorAdressAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private Context context;
    private ArrayList<AboutDoctorResPayLoad> mCurrentListing;
    public SharedPreferences prefsLoginData;

    /* renamed from: com.VURVhealth.VURVhealth.medical.doctors.DoctorAdressAdapter$1 */
    class C04371 implements OnClickListener {
        C04371() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(DoctorAdressAdapter.this.context, DoctorVURVBannerActivity.class);
            intent.putExtra("activity", "medicalScreen");
            DoctorAdressAdapter.this.context.startActivity(intent);
        }
    }

    public static class DataObjectHolder extends ViewHolder implements OnClickListener {
        private FrameLayout flBanner2;
        private TextView tvCity;
        private TextView tvFri;
        private TextView tvMon;
        private TextView tvPatientVisit;
        private TextView tvPhoneNumber;
        private TextView tvRoutinevisits;
        private TextView tvSat;
        private TextView tvStreet;
        private TextView tvSun;
        private TextView tvThu;
        private TextView tvTue;
        private TextView tvUrgentCare;
        private TextView tvWaiTime;
        private TextView tvWed;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.tvStreet = (TextView) itemView.findViewById(R.id.tvStreet);
            this.tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            this.tvMon = (TextView) itemView.findViewById(R.id.tvMon);
            this.tvTue = (TextView) itemView.findViewById(R.id.tvTue);
            this.tvWed = (TextView) itemView.findViewById(R.id.tvWed);
            this.tvThu = (TextView) itemView.findViewById(R.id.tvThu);
            this.tvFri = (TextView) itemView.findViewById(R.id.tvFri);
            this.tvSat = (TextView) itemView.findViewById(R.id.tvSat);
            this.tvSun = (TextView) itemView.findViewById(R.id.tvSun);
            this.tvWaiTime = (TextView) itemView.findViewById(R.id.tvWaiTime);
            this.tvRoutinevisits = (TextView) itemView.findViewById(R.id.tvRoutinevisits);
            this.tvUrgentCare = (TextView) itemView.findViewById(R.id.tvUrgentCare);
            this.tvPatientVisit = (TextView) itemView.findViewById(R.id.tvPatientVisit);
            this.tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            this.flBanner2 = (FrameLayout) itemView.findViewById(R.id.flBanner2);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
        }
    }

    public DoctorAdressAdapter(Context context, ArrayList<AboutDoctorResPayLoad> myDataset) {
        this.mCurrentListing = myDataset;
        this.context = context;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_adrees_adapter, parent, false));
    }

    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.tvStreet.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getAddline1());
        holder.tvCity.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getCity() + ", " + ((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getState() + ", " + ((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getZipcode());
        holder.tvMon.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getMonday());
        holder.tvTue.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getTuesday());
        holder.tvWed.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getWednesDay());
        holder.tvThu.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getThursDay());
        holder.tvFri.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getFriDay());
        holder.tvSat.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getSaturDay());
        holder.tvSun.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getSunDay());
        holder.tvWaiTime.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getWaitTimeRoutineVisit());
        holder.tvRoutinevisits.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getWaitTimeRoutineVisit());
        holder.tvUrgentCare.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getWaitTimeUrgentCare());
        holder.tvPatientVisit.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getWaitTimeNewPatients());
        this.prefsLoginData = this.context.getSharedPreferences("VURVProfileDetails", 0);
        if (this.prefsLoginData.getString("search_type", "").contains("Doctors")) {
            holder.flBanner2.setVisibility(View.VISIBLE);
        } else {
            holder.flBanner2.setVisibility(View.GONE);
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getMobileNo().substring(0, 3));
            stringBuilder.append(") ");
            stringBuilder.append(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getMobileNo().substring(3, 6));
            stringBuilder.append("-");
            stringBuilder.append(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getMobileNo().substring(6, 10));
            holder.tvPhoneNumber.setText(stringBuilder);
        } catch (ArrayIndexOutOfBoundsException e) {
            holder.tvPhoneNumber.setText(((AboutDoctorResPayLoad) this.mCurrentListing.get(position)).getMobileNo());
        }
        if (this.mCurrentListing.size() - 1 == position) {
            holder.flBanner2.setVisibility(View.VISIBLE);
        } else {
            holder.flBanner2.setVisibility(View.GONE);
        }
        holder.flBanner2.setOnClickListener(new C04371());
    }

    public void deleteItem(int index) {
        this.mCurrentListing.remove(index);
        notifyItemRemoved(index);
    }

    public int getItemCount() {
        return this.mCurrentListing.size();
    }
}
