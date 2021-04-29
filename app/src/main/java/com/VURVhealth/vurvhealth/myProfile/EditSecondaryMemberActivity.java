package com.VURVhealth.vurvhealth.myProfile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.medical.pojos.StateReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.StateResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPersonalInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPersonalInfoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPhotoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EmailStatusReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoResPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.retrofit.Application_holder;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.PhoneNumberTextWatcher;
import com.VURVhealth.vurvhealth.utilities.Utility;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditSecondaryMemberActivity extends SuperAppCompactActivity {
    private static final String TAG = "EditProfileActivity";
    private static MyMembersResponse mData;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private CheckBox chkFemale;
    private CheckBox chkMale;
    private String dob;
    private String dobFormat;
    private String dobFormat1;
    private EditText etAdd1;
    private EditText etAdd2;
    private EditText etCity;
    private EditText etCountry;
    private EditText etZip4;
    private EditText etZipcode;
    private ImageView img_prf;
    private Calendar myCalendar;
    public final Pattern pattern = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9-]{0,64}(.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+");
    private Uri profileImageUril;
    private Spinner spState;
    private ArrayList<StateResPayload> stateResPayload;
    private TextView tb_title;
    private TextView tvCancel;
    private Button tvDoB;
    private TextView tvDone;
    private TextView tvEditPhoto;
    private EditText tvEmail;
    private EditText tvFirstName;
    private EditText tvMobile;
    private EditText tvlastName;
    private String userChoosenTask;
    private int userId;
    
    

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String str;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_screen);
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvEditPhoto = (TextView) findViewById(R.id.tvEditPhoto);
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        tvFirstName = (EditText) findViewById(R.id.tvFirstName);
        tvlastName = (EditText) findViewById(R.id.tvlastName);
        tvMobile = (EditText) findViewById(R.id.tvMobile);
        tvMobile.addTextChangedListener(new PhoneNumberTextWatcher(tvMobile));
        etAdd1 = (EditText) findViewById(R.id.etAdd1);
        etAdd2 = (EditText) findViewById(R.id.etAdd2);
        etCity = (EditText) findViewById(R.id.etCity);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etZipcode = (EditText) findViewById(R.id.etZipcode);
        etZip4 = (EditText) findViewById(R.id.etZip4);
        spState = (Spinner) findViewById(R.id.spState);
        tvDoB = (Button) findViewById(R.id.tvDoB);
        tb_title = (TextView) findViewById(R.id.tb_title);
        chkMale = (CheckBox) findViewById(R.id.chkMale);
        chkFemale = (CheckBox) findViewById(R.id.chkFemale);
        img_prf = (ImageView) findViewById(R.id.img_prf);
        etAdd1.setVisibility(View.VISIBLE);
        etAdd2.setVisibility(View.VISIBLE);
        etCity.setVisibility(View.VISIBLE);
        etCountry.setVisibility(View.VISIBLE);
        etZipcode.setVisibility(View.VISIBLE);
        etZip4.setVisibility(View.VISIBLE);
        spState.setVisibility(View.VISIBLE);
        if (checkInternet()) {
            getStateService();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
        spState.setAdapter(dataAdapter);
        Picasso with = Picasso.with(EditSecondaryMemberActivity.this);
        if (mData.getImagePath() == null) {
            str = "htttp://";
        } else {
            str = "https://www.vurvhealth.com/wp-content/uploads" + mData.getImagePath();
        }
        with.load(str).error(R.drawable.profile_noimage_ic).placeholder(R.drawable.profile_noimage_ic).networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).resize(200, 200).centerCrop().into(img_prf);
        try {
            dobFormat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM-dd-yyyy").parse(tvDoB.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long number = Long.parseLong(mData.getUserEmail().split("@")[0]);
            tvEmail.setText("");
            tvEmail.setEnabled(true);
        } catch (NumberFormatException e2) {
            tvEmail.setText(mData.getUserEmail());
            tvEmail.setEnabled(false);
        }
        if (mData.getMobileNo() == null || mData.getMobileNo().equalsIgnoreCase("0") || mData.getMobileNo().isEmpty()) {
            tvMobile.setText("");
            tvMobile.setEnabled(true);
            tvMobile.setFocusable(true);
        } else {
            tvMobile.setText(mData.getMobileNo());
            tvMobile.setEnabled(false);
            tvMobile.setFocusable(false);
        }
        tvFirstName.setText(mData.getFirstName());
        tvlastName.setText(mData.getLastName());
        dob = mData.getDateOfBirth();
        try {
            dobFormat = new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
            tvDoB.setText(dobFormat);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (mData.getGender().equalsIgnoreCase("M")) {
            chkMale.setChecked(true);
            chkFemale.setChecked(false);
        } else {
            chkMale.setChecked(false);
            chkFemale.setChecked(true);
        }
        myCalendar = Calendar.getInstance();
        etAdd1.setText(mData.getAddress1());
        etAdd2.setText(mData.getAddress2());
        etCity.setText(mData.getCity());
        etCountry.setText(mData.getCountry());
        etZipcode.setText(mData.getZipcode());
        etZip4.setText(mData.getZipFourCode());
        tvDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = tvEmail.getText().toString();
                    boolean emailFormat = pattern.matcher(email).matches();
                    if (mData.getMobileNo() == null) {
                        mData.setMobileNo("");
                    }
                    if (mData.getFirstName().equals(tvFirstName.getText().toString()) && mData.getLastName().equals(tvlastName.getText().toString()) && mData.getUserEmail().equals(tvEmail.getText().toString()) && mData.getMobileNo().equals(tvMobile.getText().toString()) && mData.getAddress1().equals(etAdd1.getText().toString()) && mData.getCity().equals(etCity.getText().toString()) && mData.getState().equals(spState.getSelectedItem().toString()) && mData.getZipcode().equals(etZipcode.getText().toString())) {
                        Intent intent = new Intent(EditSecondaryMemberActivity.this, MyMembersActivity.class);
                        intent.putExtra("activity", "EditSecondaryMemberActivity");
                        startActivity(intent);
                        finish();
                    } else if ((email.length() > 0 && !emailFormat) || (email.length() > 0 && !email.contains(".com"))) {
                        Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
                    } else if (etAdd1.getText().toString().length() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, R.string.enter_adrs1, Toast.LENGTH_SHORT).show();
                    } else if (etCity.getText().toString().length() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, R.string.enter_city, Toast.LENGTH_SHORT).show();
                    } else if (spState.getSelectedItemPosition() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, R.string.enter_state, Toast.LENGTH_SHORT).show();
                    } else if (etZipcode.getText().toString().length() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, R.string.enter_zip, Toast.LENGTH_SHORT).show();
                    } else if (etZipcode.getText().toString().trim().length() < 3) {
                        Toast.makeText(getApplicationContext(), R.string.enter_valid_zip, Toast.LENGTH_SHORT).show();
                    } else if (tvFirstName.getText().toString().length() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.enter_fname), Toast.LENGTH_SHORT).show();
                    } else if (tvlastName.getText().toString().length() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.enter_lname), Toast.LENGTH_SHORT).show();
                    } else if (!isMobileNumberValid(tvMobile.getText().toString()) && tvMobile.getText().toString().length() != 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.valid_mobile), Toast.LENGTH_SHORT).show();
                    } else if (tvDoB.getText().toString().length() == 0) {
                        Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.enter_dob), Toast.LENGTH_SHORT).show();
                    } else if (!checkInternet()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                    } else if (tvEmail.getText().toString().contains(".com") && !tvEmail.getText().toString().equals(mData.getUserEmail())) {
                        getEmailStatus();
                    } else if (!tvMobile.getText().toString().contains("1234567890") || tvMobile.getText().toString().equals(mData.getMobileNo())) {
                        editPersonalInfoService();
                    } else {
                        getMobileStatus();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plese_try_again), Toast.LENGTH_SHORT).show();
                }
            }
        });
        prefsLoginData = getSharedPreferences("VURVProfileDetails", 0);
        userId = prefsLoginData.getInt("userId", 1);
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvEditPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void getStateService() {
        showProgressDialog(EditSecondaryMemberActivity.this);
        ApiInterface apiService = (ApiInterface) new Builder().baseUrl(Application_holder.BASE_URL).client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build().create(ApiInterface.class);
        ArrayList<StateReqPayload> stateReqPayloads = new ArrayList();
        StateReqPayload stateReqPayload = new StateReqPayload();
        stateReqPayload.setType("UA");
        stateReqPayloads.add(stateReqPayload);
        apiService.getState(stateReqPayloads).enqueue(new Callback<ArrayList<StateResPayload>>() {
            @Override
            public void onResponse(Call<ArrayList<StateResPayload>> call, Response<ArrayList<StateResPayload>> response) {
                if (response.isSuccessful()) {
                    stateResPayload = (ArrayList) response.body();
                    fullFormList = new ArrayList();
                    fullFormList.add(0, getResources().getString(R.string.state));
                    for (int i = 1; i < stateResPayload.size(); i++) {
                        fullFormList.add(((StateResPayload) stateResPayload.get(i)).getFacState());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditSecondaryMemberActivity.this, android.R.layout.simple_selectable_list_item, fullFormList) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            if (position == 0) {
                                textView.setTextColor(Color.parseColor("#42000000"));
                                textView.setTextSize(18.0f);
                            } else {
                                textView.setTextColor(Color.parseColor("#005FB6"));
                                textView.setTextSize(18.0f);
                            }
                            return textView;
                        }
                    };
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spState.setAdapter(dataAdapter);
                    spState.setSelection(fullFormList.indexOf(mData.getState()));
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StateResPayload>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editPersonalInfoService() {
        String mobileNo = tvMobile.getText().toString().trim().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "").replace("(", "").replace(")", "").replace("-", "");
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditSecondaryMemberActivity.this).create(ApiInterface.class);
        EditPersonalInfoReqPayload editPersonalInfoReqPayload = new EditPersonalInfoReqPayload();
        editPersonalInfoReqPayload.setUserId(mData.getUserId());
        editPersonalInfoReqPayload.setFirstName(tvFirstName.getText().toString());
        editPersonalInfoReqPayload.setLastName(tvlastName.getText().toString());
        editPersonalInfoReqPayload.setEmail(tvEmail.getText().toString());
        editPersonalInfoReqPayload.setMobileNo(mobileNo);
        ArrayList<EditPersonalInfoReqPayload> editProfileRequestPojos = new ArrayList();
        editProfileRequestPojos.add(editPersonalInfoReqPayload);
        apiService.personalInfoService(editProfileRequestPojos).enqueue(new Callback<ArrayList<ArrayList<EditPersonalInfoResPayload>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<EditPersonalInfoResPayload>>> call, Response<ArrayList<ArrayList<EditPersonalInfoResPayload>>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ArrayList<EditPersonalInfoResPayload>> personalInfoResPayload = (ArrayList) response.body();
                    dismissProgressDialog();
                    updateBillingInfoService();
                    finish();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArrayList<ArrayList<EditPersonalInfoResPayload>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void getMobileStatus() {
        showProgressDialog(EditSecondaryMemberActivity.this);
        String mobileNo = tvMobile.getText().toString().trim().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "").replace("(", "").replace(")", "").replace("-", "");
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditSecondaryMemberActivity.this).create(ApiInterface.class);
        MobileStatusReqPayload mobileStatusReqPayload = new MobileStatusReqPayload();
        mobileStatusReqPayload.setMobileNo(mobileNo);
        ArrayList<MobileStatusReqPayload> editProfileRequestPojos = new ArrayList();
        editProfileRequestPojos.add(mobileStatusReqPayload);
        apiService.getMobileStatus(editProfileRequestPojos).enqueue(new Callback<MobileStatusResPayload>() {
            @Override
            public void onResponse(Call<MobileStatusResPayload> call, Response<MobileStatusResPayload> response) {
                if (response.isSuccessful()) {
                    MobileStatusResPayload mobileStatusResPayload = (MobileStatusResPayload) response.body();
                    if (mobileStatusResPayload.getCode().equalsIgnoreCase("success")) {
                        editPersonalInfoService();
                        return;
                    }
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), mobileStatusResPayload.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<MobileStatusResPayload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void getEmailStatus() {
        showProgressDialog(EditSecondaryMemberActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditSecondaryMemberActivity.this).create(ApiInterface.class);
        EmailStatusReqPayload emailStatusReqPayload = new EmailStatusReqPayload();
        emailStatusReqPayload.setUserEmail(tvEmail.getText().toString());
        ArrayList<EmailStatusReqPayload> emailStatusReqPayloads = new ArrayList();
        emailStatusReqPayloads.add(emailStatusReqPayload);
        apiService.getEmailStatus(emailStatusReqPayloads).enqueue(new Callback<MobileStatusResPayload>() {
            @Override
            public void onResponse(Call<MobileStatusResPayload> call, Response<MobileStatusResPayload> response) {
                if (response.isSuccessful()) {
                    MobileStatusResPayload mobileStatusResPayload = (MobileStatusResPayload) response.body();
                    if (mobileStatusResPayload.getCode().equalsIgnoreCase("success")) {
                        editPersonalInfoService();
                        return;
                    }
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), mobileStatusResPayload.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<MobileStatusResPayload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    private void updateLabel() {
        tvDoB.setText(new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(myCalendar.getTime()));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE /*123*/:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    if (userChoosenTask.equals(getResources().getString(R.string.take_photo))) {
                        cameraIntent();
                        return;
                    } else if (userChoosenTask.equals(getResources().getString(R.string.choose_library))) {
                        galleryIntent();
                        return;
                    } else {
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    private void selectImage() {
        final CharSequence[] items = new CharSequence[]{getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_library), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditSecondaryMemberActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditSecondaryMemberActivity.this);
                if (items[item].equals(getResources().getString(R.string.take_photo))) {
                    userChoosenTask = getResources().getString(R.string.take_photo);
                    if (result) {
                        cameraIntent();
                    }
                } else if (items[item].equals(getResources().getString(R.string.choose_library))) {
                    userChoosenTask = getResources().getString(R.string.choose_library);
                    if (result) {
                        galleryIntent();
                    }
                } else if (items[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction("android.intent.action.PICK");
        startActivityForResult(galleryIntent, 1010);
    }

    private void cameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        profileImageUril = Uri.fromFile(getOutputMediaFile(1));
        intent.putExtra("output", profileImageUril);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Nullable
    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApplication");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (type == 1) {
            return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");
        }
        return null;
    }

    private String getRealPathFromURI(Context context, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor;
        if (VERSION.SDK_INT < 19) {
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            }
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("_data"));
        }
        try {
            String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
            String[] column = new String[]{"_data"};
            cursor = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, column, "_id=?", new String[]{id}, null);
            String filePath = "";
            if (cursor == null) {
                return contentUri.getPath();
            }
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(column[0]));
            cursor.close();
            return filePath;
        } catch (Exception e) {
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            }
            cursor.moveToFirst();
            String string = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();
            return string;
        }
    }

    private void uploadImage(String filePath) {
        showProgressDialog(EditSecondaryMemberActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditSecondaryMemberActivity.this).create(ApiInterface.class);
        File file = new File(filePath);
        apiService.editPhotoService(Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)), RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mData.getUserId()))).enqueue(new Callback<EditPhotoResPayload>() {
            public void onResponse(Call<EditPhotoResPayload> call, Response<EditPhotoResPayload> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    Picasso.with(EditSecondaryMemberActivity.this).load(((EditPhotoResPayload) response.body()).getImageLink().replace("http://", "https://")).error(R.drawable.profilepic_ic).placeholder(R.drawable.profilepic_ic).networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).resize(200, 200).centerCrop().into(img_prf);
                    Toast.makeText(EditSecondaryMemberActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    return;
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<EditPhotoResPayload> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == -1 && requestCode == 1010) {
                uploadImage(compressImage(getRealPathFromURI(EditSecondaryMemberActivity.this, data.getData().toString())));
            }
        } else if (resultCode == -1 && requestCode == REQUEST_CAMERA) {
            uploadImage(compressImage(getRealPathFromURI(EditSecondaryMemberActivity.this, profileImageUril.toString())));
        }
    }

    public String compressImage(String imageUri) {
        OutputStream outputStream;
        FileNotFoundException e;
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) (actualWidth / actualHeight);
        float maxRatio = 612.0f / 816.0f;
        if (((float) actualHeight) > 816.0f || ((float) actualWidth) > 612.0f) {
            if (imgRatio < maxRatio) {
                actualWidth = (int) (((float) actualWidth) * (816.0f / ((float) actualHeight)));
                actualHeight = (int) 816.0f;
            } else if (imgRatio > maxRatio) {
                actualHeight = (int) (((float) actualHeight) * (612.0f / ((float) actualWidth)));
                actualWidth = (int) 612.0f;
            } else {
                actualHeight = (int) 816.0f;
                actualWidth = (int) 612.0f;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16384];
        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Config.ARGB_8888);
        } catch (OutOfMemoryError exception2) {
            exception2.printStackTrace();
        }
        float ratioX = ((float) actualWidth) / ((float) options.outWidth);
        float ratioY = ((float) actualHeight) / ((float) options.outHeight);
        float middleX = ((float) actualWidth) / 2.0f;
        float middleY = ((float) actualHeight) / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - ((float) (bmp.getWidth() / 2)), middleY - ((float) (bmp.getHeight() / 2)), new Paint(2));
        try {
            int orientation = new ExifInterface(filePath).getAttributeInt("Orientation", 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90.0f);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180.0f);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270.0f);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        String filename = getFilename();
        try {
            OutputStream fileOutputStream = new FileOutputStream(filename);
            scaledBitmap.compress(CompressFormat.JPEG, 80, fileOutputStream);
            outputStream = fileOutputStream;
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            return filename;
        }
        return filename;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("_data"));
    }

    public int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }
        while (((float) (width * height)) / ((float) (inSampleSize * inSampleSize)) > ((float) ((reqWidth * reqHeight) * 2))) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public boolean isMobileNumberValid(String phoneNumber) {
        if (Pattern.compile("[0-9*#+() -]*").matcher(phoneNumber).matches()) {
            return true;
        }
        return false;
    }

    private void updateBillingInfoService() {
        ApiInterface apiService = (ApiInterface) new Builder().baseUrl(Application_holder.AUTH_BASE_URL).client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build().create(ApiInterface.class);
        ArrayList<UpdateBillingInfoReqPayload> billingInfoReqPayloads = new ArrayList();
        UpdateBillingInfoReqPayload updateBillingInfoReqPayload = new UpdateBillingInfoReqPayload();
        updateBillingInfoReqPayload.setUserId(mData.getUserId());
        updateBillingInfoReqPayload.setAddress1(etAdd1.getText().toString());
        updateBillingInfoReqPayload.setAddress2(etAdd2.getText().toString());
        updateBillingInfoReqPayload.setCity(etCity.getText().toString());
        updateBillingInfoReqPayload.setState(spState.getSelectedItemPosition() == 0 ? "" : spState.getSelectedItem().toString());
        updateBillingInfoReqPayload.setZipcode(etZipcode.getText().toString());
        updateBillingInfoReqPayload.setZipFourCode(etZip4.getText().toString());
        updateBillingInfoReqPayload.setCountry(etCountry.getText().toString());
        billingInfoReqPayloads.add(updateBillingInfoReqPayload);
        apiService.updateBillingInfo(billingInfoReqPayloads).enqueue(new Callback<ArrayList<ArrayList<UpdateBillingInfoResPayload>>>() {
            public void onResponse(Call<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> call, Response<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> response) {
                if (response.isSuccessful()) {
                    dismissProgressDialog();
                    ArrayList<ArrayList<UpdateBillingInfoResPayload>> updateBillingInfoResPayload = (ArrayList) response.body();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    return;
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
    final OnDateSetListener date = new OnDateSetListener() {
        final long today = System.currentTimeMillis() - 1000;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (myCalendar.getTimeInMillis() >= today) {
                //Make them try again
                tvDoB.setText("");
                Toast.makeText(getApplicationContext(), "Invalid date, please try again", Toast.LENGTH_LONG).show();

            } else {
                updateLabel();
            }

        }

    };
    public static void startActivityWithConstractor(Context context, MyMembersResponse addMemberDataObject) {
        mData = addMemberDataObject;
        context.startActivity(new Intent(context, EditSecondaryMemberActivity.class));
    }
}
