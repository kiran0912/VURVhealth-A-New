package com.VURVhealth.vurvhealth.myProfile;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.BuildConfig;
import com.VURVhealth.vurvhealth.R;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPersonalInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPersonalInfoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPhotoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EmailStatusReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.retrofit.ApiClient;
import com.VURVhealth.vurvhealth.retrofit.ApiInterface;
import com.VURVhealth.vurvhealth.superappcompact.SuperAppCompactActivity;
import com.VURVhealth.vurvhealth.utilities.FileUtils;
import com.VURVhealth.vurvhealth.utilities.PhoneNumberTextWatcher;
import com.VURVhealth.vurvhealth.utilities.Utility;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
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
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class EditProfileActivity extends SuperAppCompactActivity {
    private static final String TAG = "EditProfileActivity";
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private CheckBox chkFemale;
    private CheckBox chkMale;
    private String dob;
    private String dobFormat;
    private Editor editor;
    private ImageView img_prf;
    private Calendar myCalendar;
    public final Pattern pattern = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9-]{0,64}(.[a-zA-Z0-9][a-zA-Z0-9-]{0,25})+");
    private ArrayList<ArrayList<EditPersonalInfoResPayload>> personalInfoResPayload;
    private SharedPreferences prefsData;
    private Uri profileImageUril;
    private SharedPreferences sharedPreferences;
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
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_screen);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
        sharedPreferences = getSharedPreferences("VURVProfileDetails", 0);
        editor = sharedPreferences.edit();
        profileImageUril = Uri.parse(sharedPreferences.getString("ProfileImageURl", ""));
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvEditPhoto = (TextView) findViewById(R.id.tvEditPhoto);
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        tvFirstName = (EditText) findViewById(R.id.tvFirstName);
        tvlastName = (EditText) findViewById(R.id.tvlastName);
        tvMobile = (EditText) findViewById(R.id.tvMobile);
        tvMobile.addTextChangedListener(new PhoneNumberTextWatcher(tvMobile));
        tvDoB = (Button) findViewById(R.id.tvDoB);
        chkMale = (CheckBox) findViewById(R.id.chkMale);
        chkFemale = (CheckBox) findViewById(R.id.chkFemale);
        img_prf = (ImageView) findViewById(R.id.img_prf);

        //Picasso.with(this).load(profileImageUril).error(R.drawable.profile_noimage_ic).placeholder(R.drawable.profile_noimage_ic).networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).resize(200, 200).centerCrop().into(img_prf);
        prefsData = getSharedPreferences("VURVProfileDetails", 0);
        if (prefsData.getString("gender", "").equalsIgnoreCase("M")) {
            chkMale.setChecked(true);
            chkFemale.setChecked(false);
        } else if (prefsData.getString("gender", "").equalsIgnoreCase("F")) {
            chkMale.setChecked(false);
            chkFemale.setChecked(true);
        }
        userId = prefsData.getInt("userId", 1);
        tvFirstName.setText(prefsData.getString("firstName", ""));
        tvlastName.setText(prefsData.getString("lastName", ""));
        dob = prefsData.getString("dob", "");
        try {
            dobFormat = new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
            tvDoB.setText(dobFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long number = Long.parseLong(prefsData.getString("email", "").split("@")[0]);
            tvEmail.setText("");
            tvEmail.setEnabled(true);
        } catch (NumberFormatException e2) {
            tvEmail.setText(prefsData.getString("email", ""));
            tvEmail.setEnabled(false);
        }
        if (prefsData.getString("mobileNo", "") == null || prefsData.getString("mobileNo", "").equalsIgnoreCase("0") || prefsData.getString("mobileNo", "").isEmpty()) {
            tvMobile.setText("");
            tvMobile.setEnabled(true);
            tvMobile.setFocusable(true);
        } else {
            tvMobile.setText(prefsData.getString("mobileNo", ""));
            tvMobile.setEnabled(false);
            tvMobile.setFocusable(false);
        }
        myCalendar = Calendar.getInstance();
        tvDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tvEmail.getText().toString();
                boolean emailFormat = pattern.matcher(email).matches();
                if (prefsData.getString("firstName", "").equals(tvFirstName.getText().toString()) && prefsData.getString("lastName", "").equals(tvlastName.getText().toString()) && prefsData.getString("email", "").equals(tvEmail.getText().toString()) && prefsData.getString("mobileNo", "").equals(tvMobile.getText().toString())) {
                    startActivity(new Intent(EditProfileActivity.this, PrimaryAcntHolderActivity.class));
                    finish();
                } else if ((email.length() > 0 && !emailFormat) || (email.length() > 0 && !email.contains(".com"))) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
                } else if (tvFirstName.getText().toString().length() == 0) {
                    Toast.makeText(EditProfileActivity.this, R.string.enter_fname, Toast.LENGTH_SHORT).show();
                } else if (tvlastName.getText().toString().length() == 0) {
                    Toast.makeText(EditProfileActivity.this, R.string.enter_lname, Toast.LENGTH_SHORT).show();
                } else if (!isMobileNumberValid(tvMobile.getText().toString()) && tvMobile.getText().toString().length() != 0) {
                    Toast.makeText(EditProfileActivity.this, R.string.valid_mobile, Toast.LENGTH_SHORT).show();
                } else if (tvDoB.getText().toString().length() == 0) {
                    Toast.makeText(EditProfileActivity.this, R.string.enter_dob, Toast.LENGTH_SHORT).show();
                } else if ((!chkMale.isChecked() && !chkFemale.isChecked()) || (chkFemale.isChecked() && chkMale.isChecked())) {
                    Toast.makeText(EditProfileActivity.this, "Please select any one gender", Toast.LENGTH_SHORT).show();
                } else if (!checkInternet()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                } else if (tvEmail.getText().toString().contains(".com") && !tvEmail.getText().toString().equals(prefsData.getString("email", ""))) {
                    getEmailStatus();
                } else if (!tvMobile.getText().toString().contains("0123456789") || tvMobile.getText().toString().equals(prefsData.getString("mobileNo", ""))) {
                    editPersonalInfoService();
                } else {
                    getMobileStatus();
                }
            }
        });
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, PrimaryAcntHolderActivity.class));
                finish();
            }
        });
        tvEditPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        if (checkInternet()) {
            displayPhoto();
        } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
        }

    }

    private void editPersonalInfoService() {
        String mobileNo = tvMobile.getText().toString().trim().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "").replace("(", "").replace(")", "").replace("-", "");
        showProgressDialog(EditProfileActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
        EditPersonalInfoReqPayload editPersonalInfoReqPayload = new EditPersonalInfoReqPayload();
        editPersonalInfoReqPayload.setUserId(String.valueOf(userId));
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
                    personalInfoResPayload = (ArrayList) response.body();
                    Editor editor = prefsData.edit();
                    editor.putString("firstName", ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getFirstName());
                    editor.putString("lastName", ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getLastName());
                    editor.putString("fullName", ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getFirstName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getLastName());
                    editor.putString("email", ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getUserEmail());
                    if (((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getMobileNo() != null) {
                        editor.putString("mobileNo", ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getMobileNo().equals("0") ? ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getUserLogin() : ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getMobileNo());
                    } else {
                        editor.putString("mobileNo", ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getMobileNo() == null ? ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getUserLogin() : ((EditPersonalInfoResPayload) ((ArrayList) personalInfoResPayload.get(0)).get(0)).getMobileNo());
                    }
                    editor.commit();
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), R.string.profile_updated, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfileActivity.this, PrimaryAcntHolderActivity.class));
                    finish();
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
        showProgressDialog(EditProfileActivity.this);
        String mobileNo = tvMobile.getText().toString().trim().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "").replace("(", "").replace(")", "").replace("-", "");
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
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
        showProgressDialog(EditProfileActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
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
                    if (userChoosenTask.equals(getString(R.string.take_photo))) {
                        cameraIntent();
                        return;
                    } else if (userChoosenTask.equals(getString(R.string.choose_library))) {
                        galleryIntent();
                        return;
                    } else {
                        return;
                    }
                }
                if (requestCode == MY_CAMERA_REQUEST_CODE) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

                    }
                }
                return;
            default:
                return;
        }
    }

    private void selectImage() {
        final CharSequence[] items = new CharSequence[]{getString(R.string.take_photo), getString(R.string.choose_library), getString(R.string.cancel)};
        Builder builder = new Builder(EditProfileActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditProfileActivity.this);
                if (items[item].equals(getString(R.string.take_photo))) {
                    userChoosenTask = getString(R.string.take_photo);
                    if (result) {
                        cameraIntent();
                    }
                } else if (items[item].equals(getString(R.string.choose_library))) {
                    userChoosenTask = getString(R.string.choose_library);
                    if (result) {
                        galleryIntent();
                    }
                } else if (items[item].equals(getString(R.string.cancel))) {
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
       /* Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        profileImageUril = Uri.fromFile(getOutputMediaFile(1));
        intent.putExtra("output", profileImageUril);
        startActivityForResult(intent, REQUEST_CAMERA);*/
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            try {
                profileImageUril = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageUril);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {

// cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", cameraId);
            profileImageUril = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "vurv_" +
                    String.valueOf(System.currentTimeMillis()) + ".png"));
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, profileImageUril);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    private File createImageFile() throws IOException {
// Create an image file name
        File fileNougat = new File(Environment.getExternalStorageDirectory(), "vurv_" +
                System.currentTimeMillis() + ".jpeg");
//imageUriNouGat = Uri.parse(file.getAbsolutePath());
        return fileNougat;
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
        Cursor cursor;
        Uri contentUri = Uri.parse(contentURI);
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
            } else {
                return contentUri.getPath();
            }
            /*cursor.moveToFirst();
            String string = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();*/
//            return string;
        }
    }

    private void uploadImage(String selectedImage) {
        showProgressDialog(EditProfileActivity.this);
        ApiInterface apiService = ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
        Part part = null;
        if (selectedImage.contains("file://") || selectedImage.contains("content")) {
            part = prepareFilePart("image", Uri.parse(selectedImage));
        } else {
            part = prepareFilePart("image", Uri.fromFile(new File(selectedImage)));
        }

        //File file = FileUtils.getFile(EditProfileActivity.this, Uri.parse(filePath));
        //Log.v("PHOTO SERVICE", "FILE NAME->"+file.getAbsolutePath());
        apiService.editPhotoService(part, RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(userId))).enqueue(new Callback<EditPhotoResPayload>() {
            @Override
            public void onResponse(Call<EditPhotoResPayload> call, Response<EditPhotoResPayload> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    String photoUrl = (response.body()).getImageLink().replace("uploads//", "uploads/");
                    /*editor.putString("ProfileImageURl", photoUrl);
                    editor.commit();*/
                    Picasso.with(EditProfileActivity.this).load(photoUrl).error(R.drawable.profilepic_ic).placeholder(R.drawable.profilepic_ic).networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]).memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]).resize(200, 200).centerCrop().into(img_prf);
                    Toast.makeText(EditProfileActivity.this, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    return;
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<EditPhotoResPayload> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }

    @NonNull
    public Part prepareFilePart(String partName, Uri fileUri) {

        MediaType mediaType;
//String imagePath = getFilePathFromURI(NewPostActivity.this, fileUri);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = Uri.fromFile(new File(getRealPathFromURI(fileUri)));
        }
        File file = FileUtils.getFile(this, fileUri);
        try {
            mediaType = MediaType.parse(getContentResolver().getType(fileUri));
        } catch (Exception e) {
            mediaType = MediaType.parse("image/*");
        }
// create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(mediaType, file);
// MultipartBody.Part is used to send also the actual file name
        return Part.createFormData(partName, file.getName(), requestFile);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == -1 && requestCode == 1010) {
                //uploadImage(compressImage(getRealPathFromURI(EditProfileActivity.this, data.getData().toString())));
                uploadImage(data.getData().toString());
            }
        } else if (resultCode == -1 && requestCode == REQUEST_CAMERA) {
            Uri path = Uri.fromFile(new File(getRealPathFromURI(EditProfileActivity.this, profileImageUril.toString())));
            uploadImage(path.toString());
        }
    }

    public String compressImage(String imageUri) {
        OutputStream outputStream;
        FileNotFoundException e;
        String filePath = getRealPathFromURI(Uri.parse(imageUri));
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
            bmp = BitmapFactory.decodeFile(imageUri, options);
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
            int orientation = new ExifInterface(imageUri).getAttributeInt("Orientation", 0);
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

    private String getRealPathFromURI(Uri contentURI) {
        /*Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("_data"));*/
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null,
                null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
// path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try {
                int idx = cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {


                result = contentURI.getPath();
            }
            cursor.close();
        }
        return result;

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

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfileActivity.this, PrimaryAcntHolderActivity.class));
        finish();
    }

    public boolean isMobileNumberValid(String phoneNumber) {
        if (Pattern.compile("[0-9*#+() -]*").matcher(phoneNumber).matches()) {
            return true;
        }
        return false;
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

    protected void onResume() {
        super.onResume();
        profileImageUril = Uri.parse(sharedPreferences.getString("ProfileImageURl", ""));
        Glide.with(EditProfileActivity.this).load(profileImageUril).error(R.drawable.profile_noimage_ic).placeholder(R.drawable.profile_noimage_ic).centerCrop().into(img_prf);

    }

    private void displayPhoto() {
        showProgressDialog(EditProfileActivity.this);
        ApiInterface apiService = (ApiInterface) ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
        ArrayList<MyMemberListPayload> displayPhotopojo = new ArrayList();
        MyMemberListPayload displayPhotopayload = new MyMemberListPayload();
        displayPhotopayload.setUserId(String.valueOf(userId));
        displayPhotopojo.add(displayPhotopayload);
        apiService.displayPhotoService(displayPhotopojo).enqueue(new Callback<EditPhotoResPayload>() {
            public void onResponse(Call<EditPhotoResPayload> call, Response<EditPhotoResPayload> response) {
                if (response.isSuccessful()) {
                    if (response.body().getImageLink() != null) {
                        String url = response.body().getImageLink().replace("https://www.vurvhealth.com/", "https://www.vurvhealth.com/v2/api/");
                        Glide.with(EditProfileActivity.this).load(url).error(R.drawable.profilepic_ic).placeholder(R.drawable.profilepic_ic).centerCrop().into(img_prf);
                        editor.putString("ProfileImageURl", url);
                        editor.commit();
                    }
                }
                dismissProgressDialog();
            }

            public void onFailure(Call<EditPhotoResPayload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_not_found), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }
}
