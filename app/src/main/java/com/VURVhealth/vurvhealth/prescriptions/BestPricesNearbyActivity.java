package com.VURVhealth.vurvhealth.prescriptions;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.DialogClass;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.UserSharedPreferences;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.VURVhealth.vurvhealth.prescriptions.PrescriptionSearchActivity.isGeneric;

public class BestPricesNearbyActivity extends SuperAppCompactActivity {
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
    public static List<DrugSearchResultResPayLoad1.Result.PharmacyPricing> brandsearchResultResPayLoads1;
    public static List<DrugSearchResultResPayLoad1.Result.PharmacyPricing> genericsearchResultResPayLoads1;

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
    private String drugNDC, drugNDC1, zipCode, quantity, token;
    private Context context = BestPricesNearbyActivity.this;
    private ProgressDialog pDialog1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisciption);
        pDialog1 = new ProgressDialog(context);
        pDialog1.setMessage(getString(R.string.please_wait));
        pDialog1.setCancelable(false);
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

        SharedPreferences getSharedData = getSharedPreferences("SearchData", 0);
        if (getSharedData != null) {
            token = getSharedData.getString("token", "");
            drugNDC = getSharedData.getString("drugNDC", "");
            drugNDC1 = getSharedData.getString("drugNDC1", "");
            zipCode = getSharedData.getString("zipCode", "");
            quantity = getSharedData.getString("quantity", "");

            Log.v("BestPrice", "NDCData: " + drugNDC);
            Log.v("BestPrice", "NDCData1: " + drugNDC1);
        }

        brandsearchResultResPayLoads1 = new ArrayList<>();
        brandsearchResultResPayLoads1.clear();
        genericsearchResultResPayLoads1 = new ArrayList<>();
        genericsearchResultResPayLoads1.clear();

        if (checkInternet()) {
            if (isGeneric == false) {
                getBrandSearchResultService1(token, drugNDC, zipCode, quantity);
                if (drugNDC1 != null && drugNDC1.length() != 0) {
                    getGenericSearchResultService1(token, drugNDC1, zipCode, quantity);
                }else {
                    txt_generic.setVisibility(View.GONE);
                    rv_place.setVisibility(View.GONE);
                    no_data_generic.setVisibility(View.GONE);
                }
            } else {
                if (switchGeneric == true) {
                    getGenericSearchResultService1(token, drugNDC1, zipCode, quantity);
                }
            }
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }


        if (userSharedPreferences.getData("zipCode").matches("[0-9]+")) {
            tvZipcode.setText(userSharedPreferences.getData("zipCode") == null ? " " : userSharedPreferences.getData("zipCode"));
        } else if (brandsearchResultResPayLoads1 != null) {
            tvZipcode.setText(userSharedPreferences.getData("zipCode") == null ? " " : userSharedPreferences.getData("zipCode") + ", " + (brandsearchResultResPayLoads1.get(0).getPharmacy().getAddress().getState()));
        } else if (genericsearchResultResPayLoads1 != null) {
            tvZipcode.setText(userSharedPreferences.getData("zipCode") == null ? " " : userSharedPreferences.getData("zipCode") + ", " + (genericsearchResultResPayLoads1.get(0).getPharmacy().getAddress().getState()));
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
                if (rv_place.getVisibility() == View.VISIBLE || rv_brand.getVisibility() == View.VISIBLE) {
                    Intent intent = new Intent(BestPricesNearbyActivity.this, PrescriptionMapActivity.class);
                    intent.putExtra("switchGeneric", switchGeneric);
                    startActivity(intent);
                    finish();
                } else {
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

    private void getBrandSearchResultService1(final String token, String ndc, final String zipCode, final String quantity) {
        showProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getPrice1(token, ndc, zipCode, quantity).enqueue(new Callback<DrugSearchResultResPayLoad1>() {
            @Override
            public void onResponse(Call<DrugSearchResultResPayLoad1> call, Response<DrugSearchResultResPayLoad1> response) {
                if (response.isSuccessful()) {
                    if (brandsearchResultResPayLoads1 != null && response.body().getResult().getPharmacyPricings().size() > 0) {
                        for (int i = 0; i < response.body().getResult().getPharmacyPricings().size(); i++) {
                            DrugSearchResultResPayLoad1.Result.PharmacyPricing pharmacyPricing = new DrugSearchResultResPayLoad1.Result.PharmacyPricing();

                            DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy pharmacy = new DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy();
                            pharmacy.setName(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getName());
                            pharmacy.setDistance(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getDistance());

                            DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy.Address address = new DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy.Address();
                            address.setCity(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getCity());
                            address.setState(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getState());
                            address.setPostalCode(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getPostalCode());
                            address.setLatitude(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getLatitude());
                            address.setLongitude(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getLongitude());
                            address.setFullAddress(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getFullAddress());
                            address.setAddress1(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getAddress1());
                            pharmacy.setAddress(address);

                            pharmacyPricing.setDrugType("Brand");
                            pharmacyPricing.setPrices(response.body().getResult().getPharmacyPricings().get(i).getPrices());
                            pharmacyPricing.setPharmacy(pharmacy);
                            brandsearchResultResPayLoads1.add(pharmacyPricing);

                        }
                        if (brandsearchResultResPayLoads1 != null && brandsearchResultResPayLoads1.size() > 0) {
                            //Collections.sort(brandsearchResultResPayLoads1, priceSort1);
                            rv_brand.setVisibility(View.VISIBLE);
                            no_data_brand.setVisibility(View.GONE);
                            mAdapter = new BestPriceNearbyAdapter1(context, brandsearchResultResPayLoads1, "");
                            rv_brand.setAdapter(mAdapter);
                            UserSharedPreferences.getInstance(context).SaveData("zipCode", zipCode);
                            //filterByPrice();

                        } else {
                            tv_brand.setVisibility(View.VISIBLE);
                            no_data_brand.setVisibility(View.VISIBLE);
                            rv_brand.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<DrugSearchResultResPayLoad1> call, Throwable t) {
                dismissProgressDialog();
                DialogClass.createDActionAlertDialog(context, "Server not found");
            }
        });
    }

    private void getGenericSearchResultService1(String token, String ndc, final String zipCode, String quantity) {
        if (pDialog1 != null&&!pDialog1.isShowing()){
            pDialog1.show();
        }
        ApiInterface apiService = ApiClient.getClient1(context).create(ApiInterface.class);
        apiService.getPrice1(token, ndc, zipCode, quantity).enqueue(new Callback<DrugSearchResultResPayLoad1>() {
            @Override
            public void onResponse(Call<DrugSearchResultResPayLoad1> call, Response<DrugSearchResultResPayLoad1> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult() != null) {
                        if (genericsearchResultResPayLoads1 != null && response.body().getResult().getPharmacyPricings().size() > 0) {
                            for (int i = 0; i < response.body().getResult().getPharmacyPricings().size(); i++) {
                                DrugSearchResultResPayLoad1.Result.PharmacyPricing pharmacyPricing = new DrugSearchResultResPayLoad1.Result.PharmacyPricing();

                                DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy pharmacy = new DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy();
                                pharmacy.setName(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getName());
                                pharmacy.setDistance(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getDistance());

                                DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy.Address address = new DrugSearchResultResPayLoad1.Result.PharmacyPricing.Pharmacy.Address();
                                address.setCity(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getCity());
                                address.setState(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getState());
                                address.setPostalCode(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getPostalCode());
                                address.setLatitude(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getLatitude());
                                address.setLongitude(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getLongitude());
                                address.setFullAddress(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getFullAddress());
                                address.setAddress1(response.body().getResult().getPharmacyPricings().get(i).getPharmacy().getAddress().getAddress1());
                                pharmacy.setAddress(address);

                                pharmacyPricing.setDrugType("Generic");
                                pharmacyPricing.setPrices(response.body().getResult().getPharmacyPricings().get(i).getPrices());
                                pharmacyPricing.setPharmacy(pharmacy);
                                genericsearchResultResPayLoads1.add(pharmacyPricing);
                            }
                            if (genericsearchResultResPayLoads1.size() > 0) {
                                UserSharedPreferences.getInstance(context).SaveData("zipCode", zipCode);
                                //insertRecentSeach();
                                if (switchGeneric == true) {
                                    if (genericsearchResultResPayLoads1 == null || genericsearchResultResPayLoads1.size() <= 0) {
                                        no_data_generic.setVisibility(View.VISIBLE);
                                        txt_generic.setVisibility(View.VISIBLE);
                                        rv_place.setVisibility(View.GONE);
                                    } else {
                                        //Collections.sort(genericsearchResultResPayLoads1, priceSort1);
                                        mAdapterGeneric = new BestPriceNearbyAdapter1(context, genericsearchResultResPayLoads1, "");
                                        rv_place.setAdapter(mAdapterGeneric);
                                        rv_place.setVisibility(View.VISIBLE);
                                        no_data_generic.setVisibility(View.GONE);
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
                                no_data_generic.setVisibility(View.VISIBLE);
                                txt_generic.setVisibility(View.VISIBLE);
                                rv_place.setVisibility(View.GONE);
                                //DialogClass.createDAlertDialog(context, drugNDC == "" ? getResources().getString(R.string.drug_not_found) : getResources().getString(R.string.no_pharma));
                            }
                        } else {
                            DialogClass.createDAlertDialog(context, drugNDC == "" ? getResources().getString(R.string.drug_not_found) : getResources().getString(R.string.no_pharma));
                        }
                    } else {
                        Toast.makeText(context, "Zip Code not correct", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();

                }
                if (pDialog1 != null && pDialog1.isShowing()) {
                    pDialog1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DrugSearchResultResPayLoad1> call, Throwable t) {
                if (pDialog1 != null && pDialog1.isShowing()) {
                    pDialog1.dismiss();
                }
                DialogClass.createDActionAlertDialog(context, "Server not found");
            }
        });
    }

    private void filterByPrice() {
        if (isGeneric == true) {
            if (switchGeneric == true) {
                if (genericsearchResultResPayLoads1 != null && genericsearchResultResPayLoads1.size() > 0) {
                    Collections.sort(genericsearchResultResPayLoads1, priceSort1);
                    mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads1, "");
                    rv_place.setAdapter(mAdapterGeneric);
                    rv_place.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.GONE);
                    rv_brand.setVisibility(View.GONE);
                    no_data_brand.setVisibility(View.GONE);
                } else {
                    no_data_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                }
            } else {
                rv_place.setVisibility(View.GONE);
                txt_generic.setVisibility(View.GONE);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);

            }

            /*if (brandsearchResultResPayLoads1 != null) {
                Collections.sort(brandsearchResultResPayLoads1, priceSort);
                mAdapter = new BestPriceNearbyAdapter(this, brandsearchResultResPayLoads1, "");
                rv_brand.setAdapter(mAdapter);
                no_data_brand.setVisibility(View.GONE);
                rv_brand.setVisibility(View.VISIBLE);
                return;
            }*/
        } else {
            if (brandsearchResultResPayLoads1 != null && brandsearchResultResPayLoads1.size() > 0) {
                Collections.sort(brandsearchResultResPayLoads1, priceSort);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.GONE);
                mAdapter = new BestPriceNearbyAdapter1(this, brandsearchResultResPayLoads1, "");
                rv_brand.setAdapter(mAdapter);
                if (genericsearchResultResPayLoads1 != null && genericsearchResultResPayLoads1.size() > 0) {
                    if (switchGeneric == true) {
                        Collections.sort(genericsearchResultResPayLoads1, priceSort1);
                        rv_place.setVisibility(View.VISIBLE);
                        no_data_generic.setVisibility(View.GONE);
                        mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads1, "");
                        rv_place.setAdapter(mAdapterGeneric);
                    } else {
                        rv_place.setVisibility(View.GONE);
                        no_data_generic.setVisibility(View.GONE);
                        txt_generic.setVisibility(View.GONE);
                    }

                } else {
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
                if (genericsearchResultResPayLoads1 == null || genericsearchResultResPayLoads1.size() <= 0) {
                    no_data_generic.setVisibility(View.VISIBLE);
                    rv_place.setVisibility(View.GONE);
                } else {
                    //Collections.sort(genericsearchResultResPayLoads1, priceSort1);
                    mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads1, "");
                    rv_place.setAdapter(mAdapterGeneric);
                    rv_place.setVisibility(View.VISIBLE);
                    no_data_generic.setVisibility(View.GONE);
                    rv_brand.setVisibility(View.GONE);
                    no_data_brand.setVisibility(View.GONE);
                    PrescriptionFilterActivity.checkedItem = Param.PRICE;
                    filterByPrice();
                }
            } else {
                rv_place.setVisibility(View.GONE);
                txt_generic.setVisibility(View.GONE);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.VISIBLE);
            }
        } else {
            if (brandsearchResultResPayLoads1 != null && brandsearchResultResPayLoads1.size() > 0) {
                //Collections.sort(brandsearchResultResPayLoads1, priceSort1);
                rv_brand.setVisibility(View.VISIBLE);
                no_data_brand.setVisibility(View.GONE);
                mAdapter = new BestPriceNearbyAdapter1(this, brandsearchResultResPayLoads1, "");
                rv_brand.setAdapter(mAdapter);
                if (genericsearchResultResPayLoads1 != null && genericsearchResultResPayLoads1.size() > 0) {
                    if (switchGeneric == true) {
                        rv_place.setVisibility(View.VISIBLE);
                        no_data_generic.setVisibility(View.GONE);
                        mAdapterGeneric = new BestPriceNearbyAdapter1(this, genericsearchResultResPayLoads1, "");
                        rv_place.setAdapter(mAdapterGeneric);
                    } else {
                        rv_place.setVisibility(View.GONE);
                        no_data_generic.setVisibility(View.GONE);
                        txt_generic.setVisibility(View.GONE);
                    }

                } else {
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
