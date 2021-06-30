
package com.VURVhealth.vurvhealth.retrofit;


/**
 * Created by yqlabs on 27/1/17.
 */
public class Application_holder {

    public static String zipCode = "";
    public  static String providerId = "";

    //development
    //public static final String RX__REG_URL = "http://104.130.4.118/vurv_newdesign/individuals/#plans";

    //production
    public static final String RX__REG_URL = "https://vurvhealth.com/home/our-plans/#plans";
    public static final String SIGN_UP_URL = "https://www.vurvhealth.com/mobile-app-signup/";

    //development
    //public static final String DRUG_ARGUS = "http://104.130.4.118/vurv_server/";

    //production
    public static final String DRUG_ARGUS = "https://www.vurvhealth.com/v2/";

    //development
    //public static final String AUTH_BASE_URL = "http://104.130.4.118/v2/api/api/";

    //production
    public static final String AUTH_BASE_URL = "https://www.vurvhealth.com/v2/api/api/";

    //development
    //public static final String BASE_URL = "http://104.130.4.118/VURVHealth3/api/";

    //production
    public  static final String BASE_URL = "https://www.vurvhealth.com/VURVHealth3/api/";
    public static final String BASE_URL_VISION = "https://rx.vurvhealth.com/VURVhealth2/api/vision/";

    //development
    //public static final String BASE_URL1 = "https://uatapi-gateway.rxsense.com/";

    //production
    public static final String BASE_URL1 = "https://api-gateway.rxsense.com/";

    //development
    //public static final String client_secret = "0UWMCLWTD6F9";

    //development
    //production
    public static final int client_code = 79;

    //production
    public static final String client_secret = "INQGYV2OA9ZC";

    //help
    public static final String help_url = "https://vurvhealth.com/help/";

    //    Authorize.net URL
    public static final String PAYMENT_BASE_URL = "https://apitest.authorize.net/xml/";

    public static final String VURVID_CREATE = "user/public/vurvUpdate";

    public static final String VURVID_BASE_URL = "https://rx.vurvhealth.com/vurvhealth/api/user/public/vurvUpdate";

    public final static String MyPREFERENCES = "VURVUSerDetails";

    public final static String PROFILE_PREFERENCES = "VURVProfileDetails";

    public final static String LOGIN_PREFERENCES = "VURVProfileDetails";

    //prescription services
    public final static String GET_DRUG_NAME = "prescriptions/getDrugNameOnPrescription";

    public final static String GET_SVED_DATA_PRESCRIPTION = "prescriptions/getSavedData";

    public static final String VURVID_RELATIVE_PATH = "vurv_server/Vurvapi/public/vurvUpdate/";

    public static final String STATE = "master/state";

    public static final String LANGUAGES = "master/languages";

    public static final String HOSPITAL_AFFILIATION = "master/hospitalAffiliation";

    public static final String SPECIALTY = "master/specialty";

    public static final String SEARCH_PRACTITIONER = "master/searchForPractitioner";

    public static final String DOC_PAST_SEARCH = "master/insertPracPastSearches";

    public static final String SEARCH_FACILITIES = "master/searchForFacilities";

    public static final String FAC_PAST_SEARCH = "master/insertFacPastSearches";

    public static final String SAVE_FOR_LATER = "prescriptions/saveForLater";

    public static final String PAST_SEARCH_PRISCRIPTION = "prescriptions/getPrescriptionPastSearch";

    public static final String CLEAR_PAST_SEARCH_PRISCRIPTION = "prescriptions/getPrescriptionPastSearchClear";

//    this service chenge to post service

    public static final String FACILITYTYPE_SERVICES = "master/getFacilityType";

    public static final String FACILITYSUBTYPE_SERVICES = "master/getFacilitySubType";

    public static final String FACILITY_DETAIL_SERVICES = "master/getFacilityDetails";

    public static final String ABOUTDOCTOR_SERVICES = "master/getAboutDataForDoctors";

    public static final String DOCTOR_SPECIALITY = "vision/doctorSpecialty";
    public static final String OFFICE_SPECIALITY = "vision/officeSpecialty";
    public static final String VISION_LANGUAGE = "vision/visionLanguages";
    public static final String INSERT_PAST_SEARCHES = "vision/insertPastSearches";
    public static final String SEARCH_FOR_VISION = "vision/SearchForVision";
    public static final String PROVIDER_ID_DETAILS_VISION = "vision/visionProviderDetails";

    //dental
    public static final String DENTAL_LANGUAGES = "dental/languages";
    public static final String DENTAL_SPECIALTY = "dental/specialty";
    public static final String DENTAL_PAST_SEARCH = "dental/insertDentalPastSearches";
    public static final String DENTAL_SEARCH_FOR_DENTAL = "dental/SearchForDental";
    public static final String DENTAL_PROVIDER_DETAILS = "dental/dentalProviderDetails";

    public static final String SEARCH_DRUG_RESULT = "rx_api/get_Price/";

    public static final String INSERT_RECENT_SEARCH = "prescriptions/insertPrescriptionPastSearch";

    public static final String SAVE_FOR_LATER_DOCTOR = "master/PracSaveForLater";

    public static final String SAVE_FOR_LATER_ALL = "prescriptions/saveForLaterAll";

    public static final String SAVE_FOR_LATER_FACILITY = "master/FacSaveForLater";

    public static final String SAVE_FOR_LATER_VISION = "vision/visionSaveForLater";

    public static final String SAVE_FOR_LATER_DENTAL = "dental/dentalSaveForLater";

    public static final String SAVE_FOR_LATER_DENTAL_DETAILS = "dental/dentalProviderDetails";

    public static final String SAVE_FOR_LATER_VISION_DETAILS = "vision/visionProviderDetails";

    public static final String SAVE_FOR_LATER_DOCTOR_DETAILS = "master/getAboutDataForDoctors";

    public static final String SAVE_FOR_LATER_PRESCRIPTION_DETAILS = "prescriptions/getPharmacyDetails";

    public static final String SAVE_FOR_LATER_PRESCRIPTION_DETAILS2 = "prescriptions/getDetailsForSavedItem";

    /*MY PROFILE SERVICES*/
    public static final String EDIT_PROFILE_SERVICE = "public/EditPhoto";
    public static final String DISPLAY_PHOTO = "user/public/DisplayPhoto";

    public static final String PERSONAL_INFO_SERVICE = "user/public/updatePersonalInfo";

    public static final String UPDATE_BILLING_INFO = "user/public/updateBillingInfo";

    public static final String GET_PACKAGE = "user/public/getPackage";

    public static final String GET_SUB_PACKAGE = "user/public/getSubPackage";

    public static final String GET_SUB_PACKAGE_DETAILS = "user/public/getSubPackageDetail";

    public static final String GET_MY_MEMBER_LIST = "user/public/getMyMemberList";

    public static final String GET_MEMBER_TYPE_PRIMARY = "user/public/getMemberTypePrimary";

    public static final String GET_MOBILE_STATUS = "user/public/getMobileStatus";

    public static final String GET_EMAIL_STATUS = "user/public/getEmailStatus";

    public static final String GET_CURRENT_PACKAGE = "user/public/getCurrentPackage";

    public static final String CHANGE_PASSWORD = "user/public/changeCurrentPassword";

    public static final String USER_DETAIL_DATA = "user/public/userDetailData";

    public static final String ADD_MEMBER = "user/public/addMember";

    public static final String DELETE_MEMBER = "user/public/deleteMember";

    public static final String UPGRADE_PACKAGE = "user/public/orders/create";

    public static final String FORGOT_PASSWORD = "user/public/forgotPwdSendEmail";

    public static final String CANCEL_SUBSCRIPTION = "user/public/cancelSubscription";

    /*Alt health*/
    public static final String AHS_SPECIALTY = "alternativeMedicine/AHSspecialty";

    public static final String SEARCH_FOR_AHS_PROVIDER = "alternativeMedicine/SearchForAHSProvider";

    public static final String AHS_PROVIDER_DETAILS = "alternativeMedicine/AHSProviderDetails";

    public static final String AHS_PAST_SEARCH = "alternativeMedicine/AHSinsertPastSearches";

    public static final String AHS_SAVE_FOR_LATER = "alternativeMedicine/AHSsaveForLater";

    public static String userName;
    public static String userEmail;
    public static String lastName;

    public class LOGIN_DETAILS {
    }


    public static final String FRESHDESkBASE_URL = "https://vurvhealth.freshdesk.com/api/v2/solutions/";
    public static final String FRESH_folder = "folders/";
    public static final String FRESH_articals = "articles/";
    public static final String FRESH_folderId = "";


}
