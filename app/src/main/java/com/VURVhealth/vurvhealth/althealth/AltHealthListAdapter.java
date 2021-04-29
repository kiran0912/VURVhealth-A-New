package com.VURVhealth.vurvhealth.althealth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.android.gms.maps.model.LatLng;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSProviderDetailsRespayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSearchResPayload;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by yqlabs on 22/5/17.
 */

public class AltHealthListAdapter extends RecyclerView.Adapter<AltHealthListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<AHSSearchResPayload.Datum> mDataset;
    private static MyClickListener myClickListener;
    private Context context;
    private ProgressDialog pDialog;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvPharmacy,tvPrice,tvAddress,tvSaving;
        ImageView saved_item;
        private CardView cardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            tvPharmacy = (TextView) itemView.findViewById(R.id.tvPharmacy);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvSaving = (TextView) itemView.findViewById(R.id.tvSaving);
            saved_item = (ImageView) itemView.findViewById(R.id.saved_item);
            Log.i(LOG_TAG, "Adding Listener");

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public AltHealthListAdapter(Context context, ArrayList<AHSSearchResPayload.Datum> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_list_inflator, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        if (((AHSSearchResPayload.Datum) this.mDataset.get(position)).isSavedStatus() == 1){
            holder.saved_item.setVisibility(View.VISIBLE);
        }else {
            holder.saved_item.setVisibility(View.GONE);
        }

        holder.tvSaving.setVisibility(View.GONE);

        holder.tvPharmacy.setText(mDataset.get(position).getName()+", "+mDataset.get(position).getDegree());
//        holder.tvPrice.setText(mDataset.get(position).getClinicName());
        holder.tvPrice.setText(mDataset.get(position).getSpecialty());

//        holder.tvAddress.setText(mDataset.get(position).getAddress());

        String[] namesList = mDataset.get(position).getAddress().split(",");

        String address1 = namesList [0];
        String city = namesList [1];
        String state = namesList [2];
        String statezip = namesList [3];

        holder.tvAddress.setText(city + "," + state +statezip);


        holder.tvSaving.setText(mDataset.get(position).getSpecialty());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                //   Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
                getProviderDetails(position);
//                ((Activity)context).finish();
            }
        });
    }

    public void addItem(AHSSearchResPayload.Datum dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
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

    //set progress dialog
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

    private void getProviderDetails(final int position) {
        showProgressDialog(context);
        ApiInterface apiService =
                ApiClient.getClient(context).create(ApiInterface.class);
        AboutDoctorReqPayLoad searchForVisionReqPayload = new AboutDoctorReqPayLoad();
        searchForVisionReqPayload.setProviderId(mDataset.get(position).getAHSProviderId());

        final ArrayList<AboutDoctorReqPayLoad> reqPayLoads = new ArrayList<>();
        reqPayLoads.add(searchForVisionReqPayload);
        Call<ArrayList<AHSProviderDetailsRespayload>> call = apiService.getAHSProviderDetails(reqPayLoads);
        call.enqueue(new Callback<ArrayList<AHSProviderDetailsRespayload>>() {
            @Override
            public void onResponse(Call<ArrayList<AHSProviderDetailsRespayload>> call, Response<ArrayList<AHSProviderDetailsRespayload>> response) {

                if (response.isSuccessful()) {
                    ArrayList<AHSProviderDetailsRespayload> ahsProviderDetailsRespayloads = response.body();

                    for (AHSProviderDetailsRespayload data : ahsProviderDetailsRespayloads){
                        LatLng latLng = zipToLanLong(data.getAddress());
                        data.setLatitude(String.valueOf(latLng.latitude));
                        data.setLongitude(String.valueOf(latLng.longitude));
                    }
//                    resPayloads = response.body();
                    dismissProgressDialog();
                    Intent intent = new Intent(context,AltHealthSearchDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("SearchResultObject", ahsProviderDetailsRespayloads.get(0));
                    b.putInt("position",position);
                    b.putString("activity","AHSListActivity");
                    b.putInt("savedItem", mDataset.get(position).isSavedStatus());
                    intent.putExtras(b);
                    context.startActivity(intent);

                    Log.d("", "Number of movies received: ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AHSProviderDetailsRespayload>> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(context, "Could not connect to the server. Please try again later", Toast.LENGTH_SHORT).show();

                Log.e("", t.toString());
                dismissProgressDialog();
            }
        });
    }

    private LatLng zipToLanLong(String zipcode){

        final Geocoder geocoder = new Geocoder(context);
        final String zip = "90210";
        try {
            List<Address> addresses = geocoder.getFromLocationName(zipcode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                String message = String.format("Latitude: %f, Longitude: %f",
                        address.getLatitude(), address.getLongitude());
                return new LatLng(address.getLatitude(),address.getLongitude());
//                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } else {
                // Display appropriate message when Geocoder services are not available
//                Toast.makeToast(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
                return new LatLng(0.0,0.0);
            }
        } catch (IOException e) {
            return new LatLng(0.0,0.0);
            // handle exception
        }
    }

}
