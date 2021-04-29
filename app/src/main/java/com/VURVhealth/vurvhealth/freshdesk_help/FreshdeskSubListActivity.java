package com.VURVhealth.vurvhealth.freshdesk_help;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.freshdesk_help.pojos.FreshDeskSubListRes;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.VURVhealth.vurvhealth.retrofit.Application_holder.*;

/**
 * Created by yqlabs on 18/3/17.
 */

public class FreshdeskSubListActivity extends SuperAppCompactActivity {
    private static int REQUEST_CODE = 1000;
    private ImageView backBtn;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView no_data;
    private RecyclerView recycler_view;
    private TextView tbName;

    public static String str_id,str_name;





    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshdesk_sub_main);
        tbName = (TextView) findViewById(R.id.tbName);
        no_data = (TextView) findViewById(R.id.no_data);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();

        if (bundle != null) {
            str_id = bundle.getString("str_id");
            str_name = bundle.getString("str_name");
        }

        tbName.setText(str_name);

        getFreshdeskMainhelp(str_id);


    }


    protected void onResume() {
        super.onResume();
//        mAdapter.notifyDataSetChanged();
    }


    public void onBackPressed() {
        super.onBackPressed();
      //  finish();
    }


    public static class ApiClient {

        public  String userId;

        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
                retrofit = new Retrofit.Builder()
                        .baseUrl(FRESHDESkBASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

            return retrofit;
        }
    }


    private void getFreshdeskMainhelp(String str_id) {

        try {


            showProgressDialog(FreshdeskSubListActivity.this);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ArrayList<FreshDeskSubListRes>> call = apiService.getFreshdeskSubList(str_id);
            call.enqueue(new Callback<ArrayList<FreshDeskSubListRes>>() {
                @Override
                public void onResponse(Call<ArrayList<FreshDeskSubListRes>> call, Response<ArrayList<FreshDeskSubListRes>> response) {

                    if (response.isSuccessful()) {

                        List<FreshDeskSubListRes> responsePayLoad = response.body();

                        if (responsePayLoad.size() > 0){
                            loadHistoryData(responsePayLoad);
                        }else {
                            no_data.setVisibility(View.VISIBLE);
                            no_data.setText("No related articles found.");
                          //  Toast.makeText(FreshdeskSubListActivity.this, "No related articles found.", Toast.LENGTH_SHORT).show();
                        }



                       dismissProgressDialog();
                    }else {
                        dismissProgressDialog();
                        Toast.makeText(FreshdeskSubListActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<ArrayList<FreshDeskSubListRes>> call, Throwable t) {

                    dismissProgressDialog();
                    Toast.makeText(FreshdeskSubListActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                }

            });

        }catch (Exception e){
            e.getMessage();
        }

    }

    private void loadHistoryData(List<FreshDeskSubListRes> responsePayLoad) {
        if (responsePayLoad != null) {
            recycler_view.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            ProductAdapter ca = new ProductAdapter(responsePayLoad, getApplicationContext());
            recycler_view.setAdapter(ca);
        } else {
            recycler_view.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        Context mContext;
        List<FreshDeskSubListRes> holdHistoryResPayLoad;

        public ProductAdapter(List<FreshDeskSubListRes> holdHistoryResPayLoad, Context context) {
            this.holdHistoryResPayLoad = holdHistoryResPayLoad;
            mContext = context;
        }

        @Override
        public int getItemCount() {

            return holdHistoryResPayLoad.size();

        }


        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.freshdesk_help_sub_inflator, viewGroup, false);

            return new ProductViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder productViewHolder, final int position) {



                productViewHolder.tvfolderName.setText(
                        holdHistoryResPayLoad.get(position).getTitle() != null ? holdHistoryResPayLoad.get(position).getTitle().toString() : "");


                productViewHolder.card_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(FreshdeskSubListActivity.this, FreshdeskDetailsActivity.class);
                        intent.putExtra("str_description", holdHistoryResPayLoad.get(position).getDescription());
                        intent.putExtra("str_title", holdHistoryResPayLoad.get(position).getTitle());
                        startActivity(intent);

                    }
                });


        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {

            protected TextView tvfolderName;
            protected CardView card_view1;


            public ProductViewHolder(View v) {
                super(v);
                tvfolderName = (TextView) v.findViewById(R.id.tvfolderName);
                card_view1 = (CardView) v.findViewById(R.id.card_view1);
            }
        }


    }

}

