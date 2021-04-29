package com.VURVhealth.vurvhealth.prescriptions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.List;

public class BestPriceNearbyAdapter extends Adapter<BestPriceNearbyAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private String distance;
    private List<DrugSearchResultResPayLoad.Datum> drugSearchResultList;
    private String price;

    public static class DataObjectHolder extends ViewHolder implements OnClickListener {
        private CardView cardView;
        ImageView saved_item;
        TextView tvAddress;
        TextView tvPharmacy;
        TextView tvPrice;
        TextView tvSaving;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tvPharmacy = (TextView) itemView.findViewById(R.id.tvPharmacy);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvSaving = (TextView) itemView.findViewById(R.id.tvSaving);
            saved_item = (ImageView) itemView.findViewById(R.id.saved_item);
            Log.i(BestPriceNearbyAdapter.LOG_TAG, "Adding Listener");
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

    public BestPriceNearbyAdapter(Context context, List<DrugSearchResultResPayLoad.Datum> drugSearchResultList, String txt) {
        this.drugSearchResultList = drugSearchResultList;
        this.context = context;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_list_inflator, parent, false));
    }

    public void onBindViewHolder(DataObjectHolder holder, final int position) {
            if ((drugSearchResultList.get(position)).isSavedItem()) {
                holder.saved_item.setVisibility(View.VISIBLE);
            } else {
                holder.saved_item.setVisibility(View.GONE);
            }
            holder.tvPharmacy.setText((drugSearchResultList.get(position)).getPharmacyName().split("\\#")[0].toString().trim());
//        holder.tvPharmacy.setText((drugSearchResultList.get(position)).getPharmacyName().toString().trim());
            holder.tvPrice.setText("$" + String.valueOf(drugSearchResultList.get(position).getPrice()));
            /*srikanth*/
//            holder.tvAddress.setText(
//                    (drugSearchResultList.get(position)).getAddress1() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR +
//                    (drugSearchResultList.get(position)).getAddress2()
//                    + (drugSearchResultList.get(position)).getCity() + ", " +
//                    (drugSearchResultList.get(position)).getState()
//                    + ", " + (drugSearchResultList.get(position)).getZIP());

            holder.tvAddress.setText((drugSearchResultList.get(position)).getCity() + ", " +
                    (drugSearchResultList.get(position)).getState()
                    + " "+ (drugSearchResultList.get(position)).getZIP());

            price = holder.tvPrice.getText().toString();
            distance = (drugSearchResultList.get(position)).getDistance();
            Editor editor = context.getSharedPreferences("prescFilterData", 0).edit();
            editor.putString("distance", distance);
            editor.putString(Param.PRICE, price);
            editor.commit();
       /* if (drugSearchResultList.get(position).getDiscountPercent() == null || Double.parseDouble(drugSearchResultList.get(position).getDiscountPercent()) == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            holder.tvSaving.setText(String.valueOf(Math.round(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) + context.getResources().getString(R.string.savings));
            holder.tvSaving.setVisibility(View.GONE);
        } else {
            holder.tvSaving.setText(String.valueOf(Math.round((Double.parseDouble(drugSearchResultList.get(position).getDiscountPercent()) / (Double.parseDouble(drugSearchResultList.get(position).getPrice()) + Double.parseDouble(drugSearchResultList.get(position).getDiscountPercent()))) * 100.0d)) + context.getResources().getString(R.string.savings));
            holder.tvSaving.setVisibility(View.GONE);
        }*/
            holder.tvSaving.setVisibility(View.GONE);
            holder.cardView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, PrescriptionResultsDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", drugSearchResultList.get(position));
                    b.putInt("position", position);
                    b.putString("activity", "BestPricesNearbyActivity");
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });

    }

    public void addItem(DrugSearchResultResPayLoad.Datum dataObj, int index) {
        drugSearchResultList.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        drugSearchResultList.remove(index);
        notifyItemRemoved(index);
    }

    public int getItemCount() {
        return drugSearchResultList.size();
    }


}
