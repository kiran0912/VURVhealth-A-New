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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yqlabs on 28/12/16.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<String> searchList = new ArrayList<>();
    private List<String> filterList;
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

    public CustomAdapter(Context context,List<String> searchList,String fieldName,String page) {
        this.context = context;
        this.filterList = new ArrayList<String>();
        this.filterList.addAll(searchList);
        this.fieldName = fieldName;
        this.page = page;
        for (int i=0;i<searchList.size();i++){
            this.searchList.add(searchList.get(i));
        }
    }
    public CustomAdapter(Context context,List<String> searchList,String fieldName,String page,String recentList) {
//        this.searchList = searchList;
        this.context = context;
        this.filterList = new ArrayList<String>();
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
        holder.tvItemName.setText(searchList.get(position));

        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page.equalsIgnoreCase("prescription")) {
                    if(recentlist != null && recentlist.equalsIgnoreCase("recentlist")) {
                        recentlist = null;
                        ((PrescriptionSearchActivity) context).recentDrugList(holder.tvItemName.getText().toString().trim(), position, true);
                    }else {
                        ((PrescriptionSearchActivity) context).recentDrugList(holder.tvItemName.getText().toString().trim(), position, false);
                    }
//                    ((PrescriptionSearchActivity) context).setValueToEditText(searchList.get(position), fieldName,position);
                }else if(page.equalsIgnoreCase("medical")){
                    ((MedicalScreenActivity) context).setValueToMedical(searchList.get(position), fieldName);
                } else {
                    ((SpecialityScreenActivity) context).setValueToSpecialty(searchList.get(position).trim(), fieldName,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return searchList.size();
    }

    // Filter Class
    public List<String> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchList.clear();
        if (charText.length() == 0) {
            searchList.addAll(filterList);
        }
        else
        {
            for (String wp : filterList)
            {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    searchList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
        return  searchList;
    }

    //clear recyclerView item data from the search list
    public void clearData() {
        int size = this.searchList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.searchList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
