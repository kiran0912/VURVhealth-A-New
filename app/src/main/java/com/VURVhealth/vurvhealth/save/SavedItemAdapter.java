package com.VURVhealth.vurvhealth.save;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterAllResponsePojo;

import java.util.ArrayList;
import java.util.List;

public class SavedItemAdapter extends Adapter<SavedItemAdapter.DataObjectHolder> {
    private static String LOG_TAG = "SavedItemAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private List<SaveForLaterAllResponsePojo> mDataset;

    public static class DataObjectHolder extends ViewHolder implements OnClickListener {
        private CardView cardView;
        ImageView img_icon;
        LinearLayout llRemove;
        TextView tvAddress;
        TextView tvName;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
           llRemove = (LinearLayout) itemView.findViewById(R.id.llRemove);
            Log.i(SavedItemAdapter.LOG_TAG, "Adding Listener");
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

    public
    SavedItemAdapter(Context context, ArrayList<SaveForLaterAllResponsePojo> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataObjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item_inflator, parent, false));
    }

    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.tvName.setText(((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getName());
        holder.tvAddress.setText(((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getDetail());
        holder.llRemove.setTag(Integer.valueOf(position));
        holder.llRemove.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SavedItemAdapter.this.customAlertDialog(((Integer) holder.llRemove.getTag()).intValue());
            }
        });
        if (((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getType().equalsIgnoreCase("PRA")) {
            holder.img_icon.setImageResource(R.drawable.saved_ic_doctor);
        } else if (((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getType().equalsIgnoreCase("PRI")) {
            holder.img_icon.setImageResource(R.drawable.saved_ic_rx);
        } else if (((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getType().equalsIgnoreCase("FAC")) {
            holder.img_icon.setImageResource(R.drawable.saved_ic_facilities);
        } else if (((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getType().equalsIgnoreCase("Vision")) {
            holder.img_icon.setImageResource(R.drawable.saved_ic_vision);
        } else if (((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getType().equalsIgnoreCase("Dental")) {
            holder.img_icon.setImageResource(R.drawable.dental_ic);
        } else if (((SaveForLaterAllResponsePojo) this.mDataset.get(position)).getType().equalsIgnoreCase("Ahs")) {
            holder.img_icon.setImageResource(R.drawable.saved_ic_alt);
        }
        holder.cardView.setTag(Integer.valueOf(position));
        holder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int pos = ((Integer) holder.cardView.getTag()).intValue();
                if (pos != position) {
                    return;
                }
                if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("PRA")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).viewSaveForLaterDoctors(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("FAC")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).viewSaveForLaterFacility(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("Dental")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).viewSaveForLaterDental(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("Vision")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).viewSaveForLaterVisionService(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("PRI")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).viewSaveForLaterPrescription1(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("Ahs")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).viewSaveForLaterAHSService(pos);
                }
            }
        });
    }

    public void addItem(SaveForLaterAllResponsePojo dataObj, int index) {
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

    private void customAlertDialog(final int pos) {
        final Dialog customDialog = new Dialog(this.context);
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
        info_heading.setText(R.string.remove_qtn);
        yesBtn.setText(R.string.yes_remove);
        customDialog.getWindow().setAttributes(lp);
        customDialog.show();
        yesBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
                if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("PRA")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).deleteSaveForLaterDoctors(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("FAC")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).deleteSaveForLaterFacility(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("Vision")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).deleteSaveForLaterVisionService(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("Dental")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).deleteSaveForLaterDentalService(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("PRI")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).deleteSaveForLaterPrescriptionService(pos);
                } else if (((SaveForLaterAllResponsePojo) SavedItemAdapter.this.mDataset.get(pos)).getType().equalsIgnoreCase("Ahs")) {
                    ((SaveItemActivity) SavedItemAdapter.this.context).deleteSaveForLaterAHSService(pos);
                }
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                customDialog.cancel();
            }
        });
    }
}
