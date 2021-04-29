package com.VURVhealth.vurvhealth.upgrade;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;

import java.util.ArrayList;

/**
 * Created by test on 3/19/2017.
 */

public class AddMemberAdapter extends RecyclerView.Adapter<AddMemberAdapter.DataObjectHolder> {
    private static String LOG_TAG = "AddMemberAdapter";
    private ArrayList<MyMembersResponse> mDataset;
    private static MyClickListener myClickListener;
    private Context context;
    private static SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String userName = "";
    private String dob = "";
    private String gender = "";
    private String mail = "";

    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        TextView tvSecondaryUserName, tvDob, tvMail, tvGender, tvSendMail, sent_email;
        CheckBox checkEmail;
        ImageView img_delete;
        private CardView cardView;
        LinearLayout ll_check_email;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cvMember);

            tvSecondaryUserName = (TextView) itemView.findViewById(R.id.tvSecondaryUserName);
            tvDob = (TextView) itemView.findViewById(R.id.tvDob);
            tvMail = (TextView) itemView.findViewById(R.id.tvMail);
            sent_email = (TextView) itemView.findViewById(R.id.sent_email);
            tvGender = (TextView) itemView.findViewById(R.id.tvGender);
            tvSendMail = (TextView) itemView.findViewById(R.id.tvSendMail);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
            checkEmail = (CheckBox) itemView.findViewById(R.id.checkEmail);
            ll_check_email = (LinearLayout) itemView.findViewById(R.id.ll_check_email);
            Log.i(LOG_TAG, "Adding Listener");

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public AddMemberAdapter(Context context,
                            ArrayList<MyMembersResponse> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    public void setGridData(ArrayList<MyMembersResponse> myDataset2)
    {
        this.mDataset=myDataset2;
        notifyDataSetChanged();
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_member_inflator, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {


        holder.tvSecondaryUserName.setText(mDataset.get(position).getFirstName()+" "+mDataset.get(position).getLastName());
        holder.tvDob.setText(mDataset.get(position).getDateOfBirth());
        if(mDataset.get(position).getUserEmail().trim().length()>0){
            holder.ll_check_email.setVisibility(View.VISIBLE);
            holder.tvMail.setVisibility(View.VISIBLE);
        }else {
            holder.ll_check_email.setVisibility(View.INVISIBLE);
            holder.tvMail.setVisibility(View.GONE);
        }
        holder.tvMail.setText(mDataset.get(position).getUserEmail());
        holder.tvGender.setText(mDataset.get(position).getGender().equalsIgnoreCase("m") ? "Male" : "Female");
        holder.tvSendMail.setText(mDataset.get(position).getUserEmail());
        holder.sent_email.setText("VURV ID sent on "+mDataset.get(position).getUserEmail());

       /* holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,BillingInfoActivity.class);
                context.startActivity(intent);
                //implement onClick
                //   Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(context,FacilityResultDetailsActivity.class);
//                Bundle b = new Bundle();
//                b.putParcelable("SearchResultObject", mDataset.get(position));
//                intent.putExtras(b);
//                context.startActivity(intent);
            }
        });*/
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog customDialog = new Dialog(context);
                customDialog.setCancelable(false);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_alert);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(customDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                TextView tv_title = (TextView) customDialog.findViewById(R.id.tv_title);
                TextView info_heading = (TextView) customDialog.findViewById(R.id.info_heading);
                Button cancelBtn = (Button) customDialog.findViewById(R.id.cancelBtn);
                Button yesBtn = (Button) customDialog.findViewById(R.id.yesBtn);

                tv_title.setVisibility(View.GONE);

                info_heading.setText("Are you sure if you want to delete the member?");

                customDialog.getWindow().setAttributes(lp);

                customDialog.show();

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AddMemberDataActivity)context).removeMember(mDataset.get(position).getUserId(), mDataset.get(position).getMemberId());
                        mDataset.remove(position);
                        notifyDataSetChanged();
                        customDialog.dismiss();
                        customDialog.cancel();


                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        customDialog.cancel();

                    }
                });

                /*((AddMemberDataActivity)context).removeMember(mDataset.get(position).getId());
                mDataset.remove(position);
                notifyDataSetChanged();*/
            }
        });



    }

    public void deleteItem(int index) {
        mDataset.remove(index);
//        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
