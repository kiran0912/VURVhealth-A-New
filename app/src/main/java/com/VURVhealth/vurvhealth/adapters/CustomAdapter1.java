package com.VURVhealth.vurvhealth.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.MedicalScreenActivity;
import com.VURVhealth.vurvhealth.medical.doctors.SpecialityScreenActivity;
import com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameRes1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yqlabs on 28/12/16.
 */
public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.MyViewHolder> {

    private List<DrugNameRes1> searchList = new ArrayList<>();
    private List<DrugNameRes1> filterList;
    private Context context;
    private String fieldName;
    private String page,recentlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName;

        public MyViewHolder(View view) {
            super(view);
            tvItemName = (TextView) view.findViewById(R.id.tvItemName);
        }
    }

    public CustomAdapter1(Context context, List<DrugNameRes1> searchList, String fieldName, String page) {
        this.context = context;
        this.filterList = new ArrayList<DrugNameRes1>();
        this.filterList.addAll(searchList);
        this.fieldName = fieldName;
        this.page = page;
        for (int i=0;i<searchList.size();i++){
            this.searchList.add(searchList.get(i));
        }
    }
    public CustomAdapter1(Context context, List<DrugNameRes1> searchList, String fieldName, String page, String recentList) {
//        this.searchList = searchList;
        this.context = context;
        this.filterList = new ArrayList<DrugNameRes1>();
        this.filterList.addAll(searchList);
        this.fieldName = fieldName;
        this.recentlist = recentList;
        this.page = page;
        for (int i=0;i<searchList.size();i++){
            this.searchList.add(searchList.get(i));
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.tvItemName.setText(searchList.get(position).getDisplayName());

        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page.equalsIgnoreCase("prescription")) {
                    if(recentlist != null && recentlist.equalsIgnoreCase("recentlist")) {
                        recentlist = null;

                        ((PrescriptionSearchActivity) context).recentDrugList(searchList.get(position).getSeoName(), position, true);
                    }else {
                        ((PrescriptionSearchActivity) context).recentDrugList(searchList.get(position).getSeoName(), position, false);
                    }
//                    ((PrescriptionSearchActivity) context).setValueToEditText(searchList.get(position), fieldName,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return searchList.size();
    }



}
