package com.VURVhealth.vurvhealth.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.VURVhealth.vurvhealth.prescriptions.RecentDrugPojo;

import java.util.ArrayList;

public class SqLiteDbHelper extends SQLiteOpenHelper {
    private static final String ACCEPT_PATIENTS = "accept_new_patients";
    private static final String ADA_ACCESSIBLE = "ada_accesible";
    private static final String ADD1 = "add1";
    private static final String ADD2 = "add2";
    private static final String ADDRESS = "address";
    private static final String ADDRESS_ID = "pracAddressesId";
    private static final String CENTER = "center";
    private static final String CITY = "city";
    private static final String CLINICAL_EDUCATION = "clinicalEducation";
    private static final String CONTRACT_DATE = "contractDate";
    private static final String CREATE_ADD_MEMBERS = "CREATE TABLE add_members ( id INTEGER PRIMARY KEY AUTOINCREMENT, mem_type TEXT, mem_f_name TEXT, mem_l_name TEXT, mem_dob TEXT, mem_email TEXT, mem_gen TEXT )";
    private static final String CREATE_TABLE_DRUGS = "CREATE TABLE drugs ( id INTEGER PRIMARY KEY AUTOINCREMENT, lbl_name TEXT, drug_strength TEXT, drug_form TEXT, brand_name TEXT, zip_code TEXT )";
    private static final String CREATE_TABLE_FILTER_DENTAL = "CREATE TABLE filter_dental ( id INTEGER PRIMARY KEY AUTOINCREMENT, providerID TEXT, zip_code TEXT, last_name TEXT, first_name TEXT, middle_name TEXT, post_name TEXT, mobileNo TEXT, language TEXT, center TEXT, add1 TEXT, add2 TEXT, city TEXT, state TEXT, ssnOrTin TEXT, gdOrSpCode TEXT, spec TEXT, status TEXT, practiceType TEXT, contractDate TEXT, npid TEXT, dedicatedFlag TEXT, latitude TEXT, longitude TEXT, savedStatus integer, Dist TEXT )";
    private static final String CREATE_TABLE_FILTER_DOCTOR = "CREATE TABLE filter_doctor ( id INTEGER PRIMARY KEY AUTOINCREMENT, open_on_weekends TEXT, ada_accesible TEXT, accept_new_patients TEXT, address TEXT, clinicalEducation TEXT, gender TEXT, language TEXT, latitude TEXT, longitude TEXT, mobileNo TEXT, name TEXT, pracAddressesId TEXT, providerID TEXT, radius TEXT, speciality TEXT, savedStatus integer )";
    private static final String CREATE_TABLE_FILTER_FACILITY = "CREATE TABLE filter_facility ( id INTEGER PRIMARY KEY AUTOINCREMENT, facilityName TEXT, address TEXT, providerID TEXT, mobileNo TEXT, facilityType TEXT, latitude TEXT, longitude TEXT, radius TEXT, handicapped_Flag TEXT, fac_ECPProvider TEXT, Fac_JCAHOAccrediated TEXT, savedStatus integer )";
    private static final String DATABASE_NAME = "recent_drugs";
    private static final int DATABASE_VERSION = 1;
    private static final String DEDICATED_FLAG = "dedicatedFlag";
    private static final String DIST = "Dist";
    private static final String EC_PROVIDER = "fac_ECPProvider";
    private static final String FACILITY_NAME = "facilityName";
    private static final String FACILITY_TYPE = "facilityType";
    private static final String FILTER_DENTAL = "filter_dental";
    private static final String FILTER_DOCTOR = "filter_doctor";
    private static final String FILTER_FACILITY = "filter_facility";
    private static final String FIRST_NAME = "first_name";
    private static final String GD_OR_SP_CODE = "gdOrSpCode";
    private static final String GENDER = "gender";
    private static final String HANDICAPPED = "handicapped_Flag";
    private static final String JCAHO_ACCREDIATED = "Fac_JCAHOAccrediated";
    private static final String KEY_BRAND_NAME = "brand_name";
    private static final String KEY_DRUG_FORM = "drug_form";
    private static final String KEY_DRUG_STRENGTH = "drug_strength";
    private static final String KEY_ID = "id";
    private static final String KEY_LBL_NAME = "lbl_name";
    private static final String KEY_ZIP = "zip_code";
    private static final String LANGUAGE = "language";
    private static final String LAST_NAME = "last_name";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String MOBILE_NO = "mobileNo";
    private static final String NAME = "name";
    private static final String NPID = "npid";
    private static final String OPEN_WEEKENDS = "open_on_weekends";
    private static final String POST_NAME = "post_name";
    private static final String PRACTICE_TYPE = "practiceType";
    private static final String PROVIDER_ID = "providerID";
    private static final String RADIUS = "radius";
    private static final String SAVEDSTATUS = "savedStatus";
    private static final String SPEC = "spec";
    private static final String SPECIALITY = "speciality";
    private static final String SSN_OR_TIN = "ssnOrTin";
    private static final String STATE = "state";
    private static final String STATUS = "status";
    private static final String TABLE_DRUGS = "drugs";
    static Context ctx;
    ArrayList<SearchFacilitiesResPayLoad> searchFacilityResPayLoads = new ArrayList();
    ArrayList<SearchForDentalResPayLoad.Datum> searchForDentalResPayLoads = new ArrayList();
    ArrayList<SearchPractitionerResPayLoad> searchPractitionerResPayLoads = new ArrayList();
    SQLiteDatabase sqLiteDatabase;

    public SqLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        ctx = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DRUGS);
        db.execSQL(CREATE_ADD_MEMBERS);
        db.execSQL(CREATE_TABLE_FILTER_DOCTOR);
        db.execSQL(CREATE_TABLE_FILTER_FACILITY);
        db.execSQL(CREATE_TABLE_FILTER_DENTAL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS CREATE TABLE drugs ( id INTEGER PRIMARY KEY AUTOINCREMENT, lbl_name TEXT, drug_strength TEXT, drug_form TEXT, brand_name TEXT, zip_code TEXT )");
        db.execSQL("DROP TABLE IF EXISTS CREATE TABLE add_members ( id INTEGER PRIMARY KEY AUTOINCREMENT, mem_type TEXT, mem_f_name TEXT, mem_l_name TEXT, mem_dob TEXT, mem_email TEXT, mem_gen TEXT )");
        db.execSQL("DROP TABLE IF EXISTS CREATE TABLE filter_doctor ( id INTEGER PRIMARY KEY AUTOINCREMENT, open_on_weekends TEXT, ada_accesible TEXT, accept_new_patients TEXT, address TEXT, clinicalEducation TEXT, gender TEXT, language TEXT, latitude TEXT, longitude TEXT, mobileNo TEXT, name TEXT, pracAddressesId TEXT, providerID TEXT, radius TEXT, speciality TEXT, savedStatus integer )");
        db.execSQL("DROP TABLE IF EXISTS CREATE TABLE filter_facility ( id INTEGER PRIMARY KEY AUTOINCREMENT, facilityName TEXT, address TEXT, providerID TEXT, mobileNo TEXT, facilityType TEXT, latitude TEXT, longitude TEXT, radius TEXT, handicapped_Flag TEXT, fac_ECPProvider TEXT, Fac_JCAHOAccrediated TEXT, savedStatus integer )");
        db.execSQL("DROP TABLE IF EXISTS CREATE TABLE filter_dental ( id INTEGER PRIMARY KEY AUTOINCREMENT, providerID TEXT, zip_code TEXT, last_name TEXT, first_name TEXT, middle_name TEXT, post_name TEXT, mobileNo TEXT, language TEXT, center TEXT, add1 TEXT, add2 TEXT, city TEXT, state TEXT, ssnOrTin TEXT, gdOrSpCode TEXT, spec TEXT, status TEXT, practiceType TEXT, contractDate TEXT, npid TEXT, dedicatedFlag TEXT, latitude TEXT, longitude TEXT, savedStatus integer, Dist TEXT )");*/
    }

    public void CreateDrug(String lbl_name, String drug_strength, String drug_form, String brand_name, String zip_code) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LBL_NAME, lbl_name);
        values.put(KEY_DRUG_STRENGTH, drug_strength);
        values.put(KEY_DRUG_FORM, drug_form);
        values.put(KEY_BRAND_NAME, brand_name);
        values.put(KEY_ZIP, zip_code);
        db.insert(TABLE_DRUGS, null, values);
        db.close();
    }

    public ArrayList<RecentDrugPojo> getAllRecentDrugs() {
        ArrayList<RecentDrugPojo> drugs = new ArrayList();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM drugs ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                drugs.add(new RecentDrugPojo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        Log.d("getAllDrugs()", drugs.toString());
        return drugs;
    }

    public ArrayList<MyMembersResponse> getMembersList(String mem_type) {
        ArrayList<MyMembersResponse> addMemberDataObjects = new ArrayList();
        addMemberDataObjects.clear();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM add_members WHERE mem_type = '" + mem_type + "'", null);
        if (cursor.moveToFirst()) {
            do {
                addMemberDataObjects.add(new MyMembersResponse(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        Log.d("show mem list", addMemberDataObjects.toString());
        return addMemberDataObjects;
    }

    public void removeOldMembers(String mem_type) {
        String countQuery = "DELETE  FROM  add_members  WHERE mem_type='" + mem_type + "'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(countQuery);
        db.close();
    }

    public void CreateFilter(String accept_new_patients, String open_on_weekends, String ada_accessible) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCEPT_PATIENTS, accept_new_patients);
        values.put(OPEN_WEEKENDS, open_on_weekends);
        values.put(ADA_ACCESSIBLE, ada_accessible);
        db.insert("add_members", null, values);
        db.close();
    }

    public void insertDoctorsResult(ArrayList<SearchPractitionerResPayLoad> searchPractitionerResPayLoads) {
        openDatabase();
        for (int i = 0; i < searchPractitionerResPayLoads.size(); i++) {
            String str;
            ContentValues contentValues = new ContentValues();
            String str2 = OPEN_WEEKENDS;
            if (((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getOpenOnWeekend() == null) {
                str = "N";
            } else {
                str = ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getOpenOnWeekend();
            }
            contentValues.put(str2, str);
            contentValues.put(ADA_ACCESSIBLE, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getPrac_ADAaccessibilityFlag());
            contentValues.put(ACCEPT_PATIENTS, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getPrac_AcceptNewPatients());
            contentValues.put(ADDRESS, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getAddress());
            contentValues.put(CLINICAL_EDUCATION, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getClinicalEducation());
            contentValues.put(GENDER, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getGender());
            contentValues.put("language", ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getLanguage());
            contentValues.put(LATITUDE, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getLat());
            contentValues.put(LONGITUDE, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getLng());
            contentValues.put(MOBILE_NO, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getMobileNo());
            contentValues.put(NAME, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getName());
            contentValues.put(ADDRESS_ID, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getPracAddressesId());
            contentValues.put(PROVIDER_ID, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getProviderID());
            contentValues.put(RADIUS, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getRadius());
            contentValues.put(SPECIALITY, ((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).getSpeciality());
            contentValues.put(SAVEDSTATUS, Integer.valueOf(((SearchPractitionerResPayLoad) searchPractitionerResPayLoads.get(i)).isSavedStatus()));
            this.sqLiteDatabase.insert(FILTER_DOCTOR, null, contentValues);
        }
        int count = this.sqLiteDatabase.rawQuery("SELECT  * FROM filter_doctor", null).getCount();
        closeDatabase();
    }

    public void insertFacilityResult(ArrayList<SearchFacilitiesResPayLoad> searchFacilitiesResPayLoads) {
        openDatabase();
        for (int i = 0; i < searchFacilitiesResPayLoads.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROVIDER_ID, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getProviderID());
            contentValues.put(FACILITY_NAME, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getFacilityName());
            contentValues.put(ADDRESS, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getAddress());
            contentValues.put(MOBILE_NO, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getMobileNo());
            contentValues.put(FACILITY_TYPE, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getFacilityType());
            contentValues.put(LATITUDE, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getLat());
            contentValues.put(LONGITUDE, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getLng());
            contentValues.put(RADIUS, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getRadius());
            contentValues.put(HANDICAPPED, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getHandicapped_Flag().length() > 0 ? ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getHandicapped_Flag() : "N");
            contentValues.put(EC_PROVIDER, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getFac_ECPProvider());
            contentValues.put(JCAHO_ACCREDIATED, ((SearchFacilitiesResPayLoad) searchFacilitiesResPayLoads.get(i)).getFac_JCAHOAccrediated());
            this.sqLiteDatabase.insert(FILTER_FACILITY, null, contentValues);
        }
        int count = this.sqLiteDatabase.rawQuery("SELECT  * FROM filter_facility", null).getCount();
        closeDatabase();
    }

    public void insertDentalResult(ArrayList<SearchForDentalResPayLoad.Datum> searchForDentalResPayLoads) {
        openDatabase();
        for (int i = 0; i < searchForDentalResPayLoads.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROVIDER_ID, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getProviderId());
            contentValues.put(FIRST_NAME, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getFirstName());
            contentValues.put(MIDDLE_NAME, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getMidInitName());
            contentValues.put(LAST_NAME, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getMidInitName());
            contentValues.put(POST_NAME, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getPostName());
            contentValues.put(CENTER, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getCenter());
            contentValues.put(ADD1, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getAdd1());
            contentValues.put(ADD2, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getAdd2());
            contentValues.put(CITY, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getCity());
            contentValues.put(STATE, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getState());
            contentValues.put(KEY_ZIP, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getZipCode());
            contentValues.put(MOBILE_NO, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getPhoneNo());
            contentValues.put(SSN_OR_TIN, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getSsnOrTin());
            contentValues.put(GD_OR_SP_CODE, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getGdOrSpCode());
            contentValues.put(SPEC, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getSpec());
            contentValues.put("language", ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getLanguage());
            contentValues.put("status", ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getStatus());
            contentValues.put(PRACTICE_TYPE, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getPracticeType());
            contentValues.put(CONTRACT_DATE, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getContractDate());
            contentValues.put(NPID, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getNpid());
            contentValues.put(LATITUDE, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getLatitude());
            contentValues.put(LONGITUDE, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getLongitude());
            contentValues.put(SAVEDSTATUS, Integer.valueOf(((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).isSavedStatus()));
            contentValues.put(DIST, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getDist());
            contentValues.put(DEDICATED_FLAG, ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getDedicatedFlag().length() > 0 ? ((SearchForDentalResPayLoad.Datum) searchForDentalResPayLoads.get(i)).getDedicatedFlag() : "N");
            this.sqLiteDatabase.insert(FILTER_DENTAL, null, contentValues);
        }
        int count = this.sqLiteDatabase.rawQuery("SELECT  * FROM filter_dental", null).getCount();
        closeDatabase();
    }

    public ArrayList<SearchPractitionerResPayLoad> filterDoctorsData(String newPatient, String openWeekends, String adaAccess, String male, String female) {
        openDatabase();
        this.searchPractitionerResPayLoads.clear();
        String query = "Select * FROM filter_doctor where " + newPatient + openWeekends + adaAccess + male + female;
        int count = this.sqLiteDatabase.rawQuery("SELECT  * FROM filter_doctor", null).getCount();
        Cursor cursor = this.sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SearchPractitionerResPayLoad searchPractitionerResPayLoad = new SearchPractitionerResPayLoad();
                searchPractitionerResPayLoad.setOpenOnWeekend(cursor.getString(cursor.getColumnIndex(OPEN_WEEKENDS)));
                searchPractitionerResPayLoad.setPrac_ADAaccessibilityFlag(cursor.getString(cursor.getColumnIndex(ADA_ACCESSIBLE)));
                searchPractitionerResPayLoad.setPrac_AcceptNewPatients(cursor.getString(cursor.getColumnIndex(ACCEPT_PATIENTS)));
                searchPractitionerResPayLoad.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                searchPractitionerResPayLoad.setClinicalEducation(cursor.getString(cursor.getColumnIndex(CLINICAL_EDUCATION)));
                searchPractitionerResPayLoad.setGender(cursor.getString(cursor.getColumnIndex(GENDER)));
                searchPractitionerResPayLoad.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
                searchPractitionerResPayLoad.setLat(Double.valueOf(cursor.getString(cursor.getColumnIndex(LATITUDE))));
                searchPractitionerResPayLoad.setLng(Double.valueOf(cursor.getString(cursor.getColumnIndex(LONGITUDE))));
                searchPractitionerResPayLoad.setMobileNo(cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
                searchPractitionerResPayLoad.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                searchPractitionerResPayLoad.setPracAddressesId(cursor.getString(cursor.getColumnIndex(ADDRESS_ID)));
                searchPractitionerResPayLoad.setProviderID(cursor.getString(cursor.getColumnIndex(PROVIDER_ID)));
                searchPractitionerResPayLoad.setRadius(cursor.getString(cursor.getColumnIndex(RADIUS)));
                searchPractitionerResPayLoad.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
                this.searchPractitionerResPayLoads.add(searchPractitionerResPayLoad);
            } while (cursor.moveToNext());
        }
        return this.searchPractitionerResPayLoads;
    }

    public ArrayList<SearchFacilitiesResPayLoad> filterFacilitysData(String handicapped, String communityProvider, String accredited) {
        openDatabase();
        this.searchFacilityResPayLoads.clear();
        String query = "Select * FROM filter_facility where " + handicapped + communityProvider + accredited;
        int count = this.sqLiteDatabase.rawQuery("SELECT  * FROM filter_facility", null).getCount();
        Cursor cursor = this.sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SearchFacilitiesResPayLoad searchFacilitiesResPayLoad = new SearchFacilitiesResPayLoad();
                searchFacilitiesResPayLoad.setFacilityName(cursor.getString(cursor.getColumnIndex(FACILITY_NAME)));
                searchFacilitiesResPayLoad.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                searchFacilitiesResPayLoad.setProviderID(cursor.getString(cursor.getColumnIndex(PROVIDER_ID)));
                searchFacilitiesResPayLoad.setMobileNo(cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
                searchFacilitiesResPayLoad.setFacilityType(cursor.getString(cursor.getColumnIndex(FACILITY_TYPE)));
                searchFacilitiesResPayLoad.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LATITUDE))));
                searchFacilitiesResPayLoad.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LONGITUDE))));
                searchFacilitiesResPayLoad.setRadius(cursor.getString(cursor.getColumnIndex(RADIUS)));
                searchFacilitiesResPayLoad.setHandicapped_Flag(cursor.getString(cursor.getColumnIndex(HANDICAPPED)));
                searchFacilitiesResPayLoad.setFac_ECPProvider(cursor.getString(cursor.getColumnIndex(EC_PROVIDER)));
                searchFacilitiesResPayLoad.setFac_JCAHOAccrediated(cursor.getString(cursor.getColumnIndex(JCAHO_ACCREDIATED)));
                searchFacilitiesResPayLoad.setSavedStatus(cursor.getInt(cursor.getColumnIndex(SAVEDSTATUS)));
                this.searchFacilityResPayLoads.add(searchFacilitiesResPayLoad);
            } while (cursor.moveToNext());
        }
        return this.searchFacilityResPayLoads;
    }

    public ArrayList<SearchForDentalResPayLoad.Datum> filterDentalData(String generalDentistry, String specialist, String group, String solo_practitioner) {
        openDatabase();
        this.searchForDentalResPayLoads.clear();
        String query = "Select * FROM filter_dental where " + generalDentistry + specialist + group + solo_practitioner;
        int count = this.sqLiteDatabase.rawQuery("SELECT  * FROM filter_dental", null).getCount();
        Cursor cursor = this.sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SearchForDentalResPayLoad.Datum searchForDentalResPayLoad = new SearchForDentalResPayLoad.Datum();
                searchForDentalResPayLoad.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
                searchForDentalResPayLoad.setMidInitName(cursor.getString(cursor.getColumnIndex(MIDDLE_NAME)));
                searchForDentalResPayLoad.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME)));
                searchForDentalResPayLoad.setProviderId(cursor.getString(cursor.getColumnIndex(PROVIDER_ID)));
                searchForDentalResPayLoad.setPhoneNo(cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
                searchForDentalResPayLoad.setPostName(cursor.getString(cursor.getColumnIndex(POST_NAME)));
                searchForDentalResPayLoad.setCenter(cursor.getString(cursor.getColumnIndex(CENTER)));
                searchForDentalResPayLoad.setAdd1(cursor.getString(cursor.getColumnIndex(ADD1)));
                searchForDentalResPayLoad.setAdd2(cursor.getString(cursor.getColumnIndex(ADD2)));
                searchForDentalResPayLoad.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
                searchForDentalResPayLoad.setState(cursor.getString(cursor.getColumnIndex(STATE)));
                searchForDentalResPayLoad.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LATITUDE))));
                searchForDentalResPayLoad.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(LONGITUDE))));
                searchForDentalResPayLoad.setSsnOrTin(cursor.getString(cursor.getColumnIndex(SSN_OR_TIN)));
                searchForDentalResPayLoad.setGdOrSpCode(cursor.getString(cursor.getColumnIndex(GD_OR_SP_CODE)));
                searchForDentalResPayLoad.setSpec(cursor.getString(cursor.getColumnIndex(SPEC)));
                searchForDentalResPayLoad.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                searchForDentalResPayLoad.setPracticeType(cursor.getString(cursor.getColumnIndex(PRACTICE_TYPE)));
                searchForDentalResPayLoad.setContractDate(cursor.getString(cursor.getColumnIndex(CONTRACT_DATE)));
                searchForDentalResPayLoad.setNpid(cursor.getString(cursor.getColumnIndex(NPID)));
                searchForDentalResPayLoad.setDedicatedFlag(cursor.getString(cursor.getColumnIndex(DEDICATED_FLAG)));
                searchForDentalResPayLoad.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
                searchForDentalResPayLoad.setZipCode(cursor.getString(cursor.getColumnIndex(KEY_ZIP)));
                searchForDentalResPayLoad.setDist(cursor.getString(cursor.getColumnIndex(DIST)));
                searchForDentalResPayLoad.setSavedStatus(cursor.getInt(cursor.getColumnIndex(SAVEDSTATUS)));
                this.searchForDentalResPayLoads.add(searchForDentalResPayLoad);
            } while (cursor.moveToNext());
        }
        return this.searchForDentalResPayLoads;
    }

    public void removeRecordsFromDoctorFilter() {
        openDatabase();
        this.sqLiteDatabase.execSQL("delete from filter_doctor");
        closeDatabase();
    }

    public void removeRecordsFromFacilityFilter() {
        openDatabase();
        this.sqLiteDatabase.execSQL("delete from filter_facility");
        closeDatabase();
    }

    public void removeRecordsFromDentalFilter() {
        openDatabase();
        this.sqLiteDatabase.execSQL("delete from filter_dental");
        closeDatabase();
    }

    public void updateSavedStatusFlag(String tableName, int savedStatus, String providerId) {
        openDatabase();
        this.sqLiteDatabase.execSQL("UPDATE " + tableName + " SET savedStatus = " + savedStatus + " WHERE providerID = '" + providerId + "'");
        closeDatabase();
    }

    public void openDatabase() {
        this.sqLiteDatabase = getWritableDatabase();
    }

    public void closeDatabase() {
        this.sqLiteDatabase.close();
    }
}
