package com.VURVhealth.vurvhealth.prescriptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity.brandsearchResultResPayLoads1;
import static com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity.genericSearchResultResPayLoads;
import static com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity.genericsearchResultResPayLoads1;
import static com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity.isGeneric;

public class BestPricesNearbyActivity extends AppCompatActivity {
    private static final int FILTER_CODE = 100;
    private static String LOG_TAG = "BestPricesNearbyActivity";

    private ImageView backBtn;
    private String drugDose;
    private String drugForm;
    private String drugName;
    private String drugType;
    private ImageView filter_btn;
    private String location;
    private Adapter<BestPriceNearbyAdapter1.DataObjectHolder> mAdapter;
    private Adapter<BestPriceNearbyAdapter1.DataObjectHolder> mAdapterGeneric;
    public List<DrugSearchResultResPayLoad1.Result.PharmacyPricing> brandsearchResultResPayLoads;
    public List<DrugSearchResultResPayLoad1.Result.PharmacyPricing> genericsearchResultResPayLoads;
    private LayoutManager mLayoutManager;
    private LayoutManager mLayoutManagerBrand;
    private ImageView mapBtn;
    private TextView no_data_brand;
    private TextView no_data_generic;
    private RecyclerView rv_brand;
    private RecyclerView rv_place;
    private NestedScrollView scrollView;
    private TextView tbName;
    private TextView tvZipcode;
    private TextView tvTabletName;
    private TextView txt_generic, tv_brand;
    private UserSharedPreferences userSharedPreferences;
    private boolean switchGeneric;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisciption);
        userSharedPreferences = UserSharedPreferences.getInstance(this);
        tvTabletName = (TextView) findViewById(R.id.tvTabletName);
        txt_generic = (TextView) findViewById(R.id.txt_generic);
        tv_brand = (TextView) findViewById(R.id.tv_brand);
        tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        tbName = (TextView) findViewById(R.id.tbName);
        no_data_generic = (TextView) findViewById(R.id.no_data_generic);
        no_data_brand = (TextView) findViewById(R.id.no_data_brand);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        mapBtn = (ImageView) findViewById(R.id.mapBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        rv_place = (RecyclerView) findViewById(R.id.rv_place);
        rv_brand = (RecyclerView) findViewById(R.id.rv_brand);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_place.setLayoutManager(mLayoutManager);
        rv_place.setItemAnimator(new DefaultItemAnimator());
        mLayoutManagerBrand = new LinearLayoutManager(getApplicationContext());
        rv_brand.setLayoutManager(mLayoutManagerBrand);
        rv_brand.setItemAnimator(new DefaultItemAnimator());
        scrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        if (getIntent() != null) {
            switchGeneric = getIntent().getBooleanExtra("switchGeneric", true);
        }

        //switchGeneric = UserSharedPreferences.getInstance(BestPricesNearbyActivity.this).getData("")
        if (brandsearchResultResPayLoads1!=null&& brandsearchResultResPayLoads1.size()>0){
            brandsearchResultResPayLoads = new ArrayList<>();
            brandsearchResultResPayLoads.clear();
            brandsearchResultResPayLoads = brandsearchResultResPayLoads1;
            HashSet<DrugSearchResultResPayLoad1.Result.PharmacyPricing> hashSet = new HashSet<>();
            hashSet.addAll(brandsearchResultResPayLoads);
            brandsearchResultResPayLoads.clear();
            brandsearchResultResPayLoads.addAll(hashSet);
            Collections.sort(brandsearchResultResPayLoads, new Comparator<DrugSearchResultResPayLoad1.Result.PharmacyPricing>() {
                @Override
                public int compare(DrugSearchResultResPayLoad1.Result.PharmacyPricing pharmacyPricing, DrugSearchResultResPayLoad1.Result.PharmacyPricing t1) {
                    return pharmacyPricing.getPrices().get(0).getPrice().compareTo(t1.getPrices().get(0).getPrice());
                }
            });

        }

        if (genericsearchResultResPayLoads1!=null && genericsearchResultResPayLoads1.size()>0){
            genericsearchResultResPayLoads = new ArrayList<>();
            genericsearchResultResPayLoads.clear();
            genericsearchResultResPayLoads = genericsearchResultResPayLoads1;
        }



        if (userSharedPreferences.getData("zipCode").matches("[0-9]+")) {
            tvZipcode.setText(userSharedPreferences.getData("zipCode") == null ? " " : userSharedPreferences.getData("zipCode"));
        } else if (brandsearchResultResPayLoads != null) {
            tvZipcode.setText(userSharedPreferences.getData("zipCode") == null ? " " : userSharedPreferences.getData("zipCode") + ", " + (brandsearchResultResPayLoads.get(0).getPharmacy().getAddress().getState()));
        } else if (genericsearchResultResPayLoads != null) {
            tvZipcode.setText(userSharedPreferences.getData("zipCode") == null ? " " : userSharedPreferences.getData("zipCode") + ", " + (genericsearchResultResPayLoads.get(0).getPharmacy().getAddress().getState()));
        }
        SharedPreferences prefs = getSharedPreferences("SearchData", 0);
        if (prefs.getString("DrugName", null) != null) {
            drugName = prefs.getString("DrugName", "");
            drugForm = prefs.getString("drugForm", "");
            drugDose = prefs.getString("drugDose", "");
            location = prefs.getString(Param.LOCATION, "");
            drugType = prefs.getString("drugType", "");
        }
        tvTabletName.setText(drugName);

        if (isGeneric == true) {
            if (switchGeneric == true) {
                if (genericsearchResultResPayLoads == null || genericsearchResultResPayLoads.size() <= 0) {
                    no_data_generic.setVisibility(View.VISIBLE);
                    txt_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                } else {
                    //Collections.sort(genericSearchResultResPayLoads, priceSort1);
                    mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads, "");
                    rv_place.setAdapter(mAdapterGeneric);
                    mAdapterGeneric.notifyDataSetChanged();
                    rv_place.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.GONE);
                    rv_brand.setVisibility(View.GONE);
                    no_data_brand.setVisibility(View.GONE);
                    PrescriptionFilterActivity.checkedItem = Param.PRICE;
                    //filterByPrice();
                }
            } else {
                txt_generic.setVisibility(View.GONE);
                rv_place.setVisibility(View.GONE);
                rv_brand.setVisibility(View.GONE);
                tv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);
            }
        } else {
            if (brandsearchResultResPayLoads != null && brandsearchResultResPayLoads.size() > 0) {
                //Collections.sort(brandSearchResultResPayLoads, priceSort1);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.GONE);
                mAdapter = new BestPriceNearbyAdapter1(this, brandsearchResultResPayLoads, "");
                rv_brand.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                if (switchGeneric == false){
                    rv_place.setVisibility(View.GONE);
                    txt_generic.setVisibility(View.GONE);
                }else {
                    if (genericsearchResultResPayLoads!=null && genericsearchResultResPayLoads.size() > 0) {
                            rv_place.setVisibility(View.VISIBLE);
                            no_data_generic.setVisibility(View.GONE);
                            mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads, "");
                            rv_place.setAdapter(mAdapterGeneric);
                            mAdapterGeneric.notifyDataSetChanged();

                    }else {
                        txt_generic.setVisibility(View.VISIBLE);
                        no_data_generic.setVisibility(View.VISIBLE);
                        rv_place.setVisibility(View.GONE);
                    }
                }

                PrescriptionFilterActivity.checkedItem = Param.PRICE;
                //filterByPrice();
            }else {
                tv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);
                rv_brand.setVisibility(View.GONE);
            }
        }


        if (isGeneric == true) {
            if (switchGeneric == true) {
                no_data_brand.setVisibility(View.GONE);
                rv_brand.setVisibility(View.GONE);
                tv_brand.setVisibility(View.GONE);
                rv_place.setVisibility(View.VISIBLE);
            } else {
                no_data_generic.setVisibility(View.GONE);
                rv_place.setVisibility(View.GONE);
                txt_generic.setVisibility(View.GONE);
                tv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);
            }
        }
        filter_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(BestPricesNearbyActivity.this, PrescriptionFilterActivity.class), 100);
            }
        });
        mapBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(BestPricesNearbyActivity.this, PrescriptionMapActivity.class));
                if (rv_place.getVisibility() == View.VISIBLE || rv_brand.getVisibility() ==View.VISIBLE) {
                    Intent intent = new Intent(BestPricesNearbyActivity.this, PrescriptionMapActivity.class);
                    intent.putExtra("switchGeneric", switchGeneric);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(BestPricesNearbyActivity.this, "No Details Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BestPricesNearbyActivity.this, PrescriptionSearchActivity.class));
                finish();

            }
        });
    }

    private void filterByPrice() {
        if (isGeneric == true) {
            if (switchGeneric == true) {
                if (genericsearchResultResPayLoads != null && genericsearchResultResPayLoads.size() > 0) {
                    Collections.sort(genericsearchResultResPayLoads, priceSort1);
                    mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads, "");
                    rv_place.setAdapter(mAdapterGeneric);
                    rv_place.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.GONE);
                    rv_brand.setVisibility(View.GONE);
                    no_data_brand.setVisibility(View.GONE);
                } else {
                    no_data_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                }
            }else {
                rv_place.setVisibility(View.GONE);
                txt_generic.setVisibility(View.GONE);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);

            }

            /*if (brandSearchResultResPayLoads != null) {
                Collections.sort(brandSearchResultResPayLoads, priceSort);
                mAdapter = new BestPriceNearbyAdapter(this, brandSearchResultResPayLoads, "");
                rv_brand.setAdapter(mAdapter);
                no_data_brand.setVisibility(View.GONE);
                rv_brand.setVisibility(View.VISIBLE);
                return;
            }*/
        } else {
            if (brandsearchResultResPayLoads != null && brandsearchResultResPayLoads.size() > 0) {
                Collections.sort(brandsearchResultResPayLoads, priceSort);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.GONE);
                mAdapter = new BestPriceNearbyAdapter1(this, brandsearchResultResPayLoads, "");
                rv_brand.setAdapter(mAdapter);
                if (genericsearchResultResPayLoads!=null && genericsearchResultResPayLoads.size() > 0) {
                    if (switchGeneric == true) {
                        Collections.sort(genericsearchResultResPayLoads, priceSort1);
                        rv_place.setVisibility(View.VISIBLE);
                        no_data_generic.setVisibility(View.GONE);
                        mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads, "");
                        rv_place.setAdapter(mAdapterGeneric);
                    } else {
                        rv_place.setVisibility(View.GONE);
                        no_data_generic.setVisibility(View.GONE);
                        txt_generic.setVisibility(View.GONE);
                    }

                }else {
                    txt_generic.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                }
            }
        }

       /* no_data_brand.setVisibility(View.VISIBLE);
        rv_brand.setVisibility(View.GONE);*/
    }

    private void filterByDistance() {
        if (isGeneric == true) {
            if (switchGeneric == true) {
                if (genericSearchResultResPayLoads == null || genericSearchResultResPayLoads.size() <= 0) {
                    no_data_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                } else {
                    //Collections.sort(genericSearchResultResPayLoads, priceSort1);
                    mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads, "");
                    rv_place.setAdapter(mAdapterGeneric);
                    rv_place.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.GONE);
                    rv_brand.setVisibility(View.GONE);
                    no_data_brand.setVisibility(View.GONE);
                    PrescriptionFilterActivity.checkedItem = Param.PRICE;
                    filterByPrice();
                }
            }else {
                rv_place.setVisibility(View.GONE);
                txt_generic.setVisibility(View.GONE);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);
            }
        } else {
            if (brandsearchResultResPayLoads != null && brandsearchResultResPayLoads.size() > 0) {
                //Collections.sort(brandSearchResultResPayLoads, priceSort1);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.GONE);
                mAdapter = new BestPriceNearbyAdapter1(this, brandsearchResultResPayLoads, "");
                rv_brand.setAdapter(mAdapter);
                if (genericsearchResultResPayLoads!=null && genericsearchResultResPayLoads.size() > 0) {
                    if (switchGeneric == true) {
                        rv_place.setVisibility(View.VISIBLE);
                        no_data_generic.setVisibility(View.GONE);
                        mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads, "");
                        rv_place.setAdapter(mAdapterGeneric);
                    }else {
                        rv_place.setVisibility(View.GONE);
                        no_data_generic.setVisibility(View.GONE);
                        txt_generic.setVisibility(View.GONE);
                    }

                }else {
                    txt_generic.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                }

            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 100 || resultCode != 101) {
            return;
        }
        if (data.getStringExtra("distance").equalsIgnoreCase("y")) {
            filterByDistance();
        } else if (data.getStringExtra(Param.PRICE).equalsIgnoreCase("y")) {
            filterByPrice();
        }
    }

    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BestPricesNearbyActivity.this, PrescriptionSearchActivity.class));
        finish();
    }

    public static Comparator<DrugSearchResultResPayLoad1.Result.PharmacyPricing> priceSort = new Comparator<DrugSearchResultResPayLoad1.Result.PharmacyPricing>() {
        @Override
        public int compare(DrugSearchResultResPayLoad1.Result.PharmacyPricing priceobj1, DrugSearchResultResPayLoad1.Result.PharmacyPricing priceobj2) {
            if (Double.parseDouble(priceobj1.getPrices().get(0).getPrice()) < Double.parseDouble(priceobj2.getPrices().get(0).getPrice())) {
                return -1;
            }
            if (Double.parseDouble(priceobj1.getPrices().get(0).getPrice()) > Double.parseDouble(priceobj2.getPrices().get(0).getPrice())) {
                return 1;
            }
            return 0;
        }
    };

    public static Comparator<DrugSearchResultResPayLoad1.Result.PharmacyPricing> priceSort1 = new Comparator<DrugSearchResultResPayLoad1.Result.PharmacyPricing>() {
        @Override
        public int compare(DrugSearchResultResPayLoad1.Result.PharmacyPricing priceobj3, DrugSearchResultResPayLoad1.Result.PharmacyPricing priceobj4) {
            if (Double.parseDouble(priceobj3.getPrices().get(0).getPrice()) < Double.parseDouble(priceobj4.getPrices().get(0).getPrice())) {
                return -1;
            }
            if (Double.parseDouble(priceobj3.getPrices().get(0).getPrice()) > Double.parseDouble(priceobj4.getPrices().get(0).getPrice())) {
                return 1;
            }
            return 0;
        }
    };


}
