package com.VURVhealth.vurvhealth.upgrade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.GetPackageResPayload;

import java.util.ArrayList;

/**
 * Created by yqlabs on 7/6/17.
 */

public class UpgradeSubscriptionAdapter extends RecyclerView.Adapter<UpgradeSubscriptionAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyMembersAdapter";
    private ArrayList<GetPackageResPayload> mDataset;
    private static MyClickListener myClickListener;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String package_id;
    private ProgressDialog pDialog;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvPackageName, tvPackagePlan,tvPackageText;
        LinearLayout llPrescription,llDoctors,llFacility,llDental,llVision,llAltHealth;
        private CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            tvPackageName = (TextView) itemView.findViewById(R.id.tvPackageName);
            tvPackagePlan = (TextView) itemView.findViewById(R.id.tvPackagePlan);
            tvPackageText = (TextView) itemView.findViewById(R.id.tvPackageText);
            llPrescription = (LinearLayout) itemView.findViewById(R.id.llPrescription);
            llDoctors = (LinearLayout) itemView.findViewById(R.id.llDoctors);
            llFacility = (LinearLayout) itemView.findViewById(R.id.llFacility);
            llDental = (LinearLayout) itemView.findViewById(R.id.llDental);
            llVision = (LinearLayout) itemView.findViewById(R.id.llVision);
            llAltHealth = (LinearLayout) itemView.findViewById(R.id.llAltHealth);
            Log.i(LOG_TAG, "Adding Listener");

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public UpgradeSubscriptionAdapter(Context context,
                            ArrayList<GetPackageResPayload> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upgrade_subscription_adapter, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        sharedPreferences = context.getSharedPreferences(Application_holder.PROFILE_PREFERENCES, Context.MODE_PRIVATE);
        package_id = sharedPreferences.getString("packageId", "");

        String termId = mDataset.get(position).getTermId();
//        holder.tvPackagePlan.setText(mDataset.get(position).getName()+" Package");

        if (package_id.equalsIgnoreCase("21")){
            if (position == 0) {
                holder.tvPackageName.setText(R.string.rx_package);
                holder.cardView.setBackgroundResource(R.drawable.gradient_package);
                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.savings_up);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.GONE);

            } else if (position == 1){
                holder.tvPackageName.setText(R.string.pulse_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.instant_discount);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }else if (position == 2){
                holder.tvPackageName.setText(R.string.care_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.smile_with);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.GONE);
            }else if (position == 3){

                holder.tvPackageName.setText(R.string._360_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.enjoy_the_full_savings);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }
        }else if(package_id.equalsIgnoreCase("22")){
            if (position == 0) {
                holder.tvPackageName.setText(R.string.rx_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.savings_up);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.GONE);

            } else if (position == 1){
                holder.tvPackageName.setText(R.string.pulse_package);
                holder.cardView.setBackgroundResource(R.drawable.gradient_package);
            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.instant_discount);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }else if (position == 2){
                holder.tvPackageName.setText(R.string.care_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.smile_with);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.GONE);
            }else if (position == 3){

                holder.tvPackageName.setText(R.string._360_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.enjoy_the_full_savings);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }
        }else if (package_id.equalsIgnoreCase("23")){
            if (position == 0) {
                holder.tvPackageName.setText(R.string.rx_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.savings_up);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.GONE);

            } else if (position == 1){
                holder.tvPackageName.setText(R.string.pulse_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.instant_discount);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }else if (position == 2){
                holder.tvPackageName.setText(R.string.care_package);
                holder.cardView.setBackgroundResource(R.drawable.gradient_package);
            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.smile_with);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.GONE);
            }else if (position == 3){

                holder.tvPackageName.setText(R.string._360_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//            holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.enjoy_the_full_savings);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }
        }else if (package_id.equalsIgnoreCase("24")) {
            if (position == 0) {
                holder.tvPackageName.setText(R.string.rx_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.savings_up);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.GONE);

            } else if (position == 1){
                holder.tvPackageName.setText(R.string.pulse_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.instant_discount);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.GONE);
                holder.llVision.setVisibility(View.GONE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }else if (position == 2){
                holder.tvPackageName.setText(R.string.care_package);
                holder.cardView.setBackgroundResource(R.drawable.package_border);
//                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.smile_with);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.GONE);
                holder.llFacility.setVisibility(View.GONE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.GONE);
            }else if (position == 3){

                holder.tvPackageName.setText(R.string._360_package);
                holder.cardView.setBackgroundResource(R.drawable.gradient_package);
                holder.tvPackagePlan.setText(R.string.current_plan);
                holder.tvPackageText.setText(R.string.enjoy_the_full_savings);
                holder.llPrescription.setVisibility(View.VISIBLE);
                holder.llDoctors.setVisibility(View.VISIBLE);
                holder.llFacility.setVisibility(View.VISIBLE);
                holder.llDental.setVisibility(View.VISIBLE);
                holder.llVision.setVisibility(View.VISIBLE);
                holder.llAltHealth.setVisibility(View.VISIBLE);
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = context.getSharedPreferences(Application_holder.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("packageId",mDataset.get(position).getTermId());
                editor.commit();
                ((UpgradeSubscriptionActivity) context).getSubPackage(position, mDataset.get(position).getTermId());

            }
        });

    }

    protected void showProgressDialog(Context context){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void dismissProgressDialog(){
        if(pDialog != null)
            pDialog.dismiss();
    }


    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}