package com.VURVhealth.vurvhealth.myProfile;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MyMembersAdapter extends Adapter<MyMembersAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyMembersAdapter";
    private static MyClickListener myClickListener;
    private String activity;
    private Context context;
    private ArrayList<MyMembersResponse> mDataset;

    public static class DataObjectHolder extends ViewHolder {
        private CardView cardView;
        ImageView img_delete;
        ImageView img_prf;
        TextView tvUserName;
        TextView tvVURVID;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvVURVID = (TextView) itemView.findViewById(R.id.tvVURVID);
            img_prf = (ImageView) itemView.findViewById(R.id.img_prf);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public interface MyClickListener {
        void onItemClick(int i, View view);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyMembersAdapter(Context context, ArrayList<MyMembersResponse> myDataset, String activity) {
        mDataset = myDataset;
        this.context = context;
        this.activity = activity;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_members_inflator, parent, false));
    }

    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.tvUserName.setText(((MyMembersResponse) mDataset.get(position)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((MyMembersResponse) mDataset.get(position)).getLastName());
        holder.tvVURVID.setText(((MyMembersResponse) mDataset.get(position)).getMemberId());
        if (activity.equalsIgnoreCase("VurvPackageActivity")) {
            holder.img_delete.setVisibility(View.GONE);
        } else {
            holder.img_delete.setVisibility(View.VISIBLE);
        }
        Picasso.with(context).load(((MyMembersResponse) mDataset.get(position)).getImagePath() == null ? "http://" : "https://www.vurvhealth.com/wp-content/uploads" + ((MyMembersResponse) mDataset.get(position)).getImagePath()).placeholder(R.drawable.profile_noimage_ic).error(R.drawable.profile_noimage_ic).networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).into(holder.img_prf);
        holder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((MyMembersActivity) context).moveToNextActivity((MyMembersResponse) mDataset.get(position));
            }
        });
        holder.img_delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final Dialog customDialog = new Dialog(context);
                customDialog.setCancelable(false);
                customDialog.requestWindowFeature(1);
                customDialog.setContentView(R.layout.custom_alert);
                LayoutParams lp = new LayoutParams();
                lp.copyFrom(customDialog.getWindow().getAttributes());
                lp.width = -1;
                lp.height = -2;
                lp.gravity = 17;
                TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
                Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
                Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);
                ((TextView) customDialog.findViewById(R.id.tv_title)).setVisibility(View.GONE);
                info_heading.setText(context.getResources().getString(R.string.delete_member));
                customDialog.getWindow().setAttributes(lp);
                customDialog.show();
                yesBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ((MyMembersActivity) context).removeMember(((MyMembersResponse) mDataset.get(position)).getUserId(), ((MyMembersResponse) mDataset.get(position)).getMemberId());
                        mDataset.remove(position);
                        notifyDataSetChanged();
                        customDialog.dismiss();
                        customDialog.cancel();
                    }
                });
                cancelBtn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        customDialog.dismiss();
                        customDialog.cancel();
                    }
                });
            }
        });
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public int getItemCount() {
        return mDataset.size();
    }
}
