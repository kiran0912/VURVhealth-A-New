package com.VURVhealth.vurvhealth.retrofit;

import com.VURVhealth.vurvhealth.althealth.pojos.AHSPastSearchesReqPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSProviderDetailsRespayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSearchReqPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSearchResPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSpecialtyReqPayload;
import com.VURVhealth.vurvhealth.althealth.pojos.AHSSpecialtyResPayload;
import com.VURVhealth.vurvhealth.authentication.ForgotPasswordReqPayLoad;
import com.VURVhealth.vurvhealth.authentication.ForgotPasswordReqSendOtpPayLoad;
import com.VURVhealth.vurvhealth.authentication.ForgotPasswordResPayLoad;
import com.VURVhealth.vurvhealth.authentication.getpagepojo.GetPageResPayload;
import com.VURVhealth.vurvhealth.authentication.loginPojos.LoginResPayLoad;
import com.VURVhealth.vurvhealth.authentication.noncevaluepojo.NonceResPayLoad;
import com.VURVhealth.vurvhealth.authentication.registrationPojo.RegistrationResPayLoad;
import com.VURVhealth.vurvhealth.authentication.vurvIdPojos.VurvIdReqPayLoad;
import com.VURVhealth.vurvhealth.authentication.vurvIdPojos.VurvIdResPayLoad;
import com.VURVhealth.vurvhealth.authorize.netpojos.UpgradePackageReqPayload;
import com.VURVhealth.vurvhealth.authorize.netpojos.UpgradePackageResPayload;
import com.VURVhealth.vurvhealth.dental.pojos.DentalLangResPayLoad;
import com.VURVhealth.vurvhealth.dental.pojos.DentalPastSearchReqPayload;
import com.VURVhealth.vurvhealth.dental.pojos.DentalProviderDetailsResPayload;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalReqPayload;
import com.VURVhealth.vurvhealth.dental.pojos.SearchForDentalResPayLoad;
import com.VURVhealth.vurvhealth.dental.pojos.SpecialtyDentalResPayload;
import com.VURVhealth.vurvhealth.freshdesk_help.pojos.FreshDeskMainListRes;
import com.VURVhealth.vurvhealth.freshdesk_help.pojos.FreshDeskSubListRes;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorReqPayLoad;
import com.VURVhealth.vurvhealth.medical.aboutDoctorPojos.AboutDoctorResPayLoad;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacPastSearchReqPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDeatilResPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityDetailReqPayload;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityTypeReqPayLoad;
import com.VURVhealth.vurvhealth.medical.facilityTypePojos.FacilityTypeResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.DoctorPastSearchReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.FacilitySubTypeResPayload;
import com.VURVhealth.vurvhealth.medical.pojos.HospitalAffiliationReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.HospitalAffiliationResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.LanguageResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.LanguagesReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterDoctors;
import com.VURVhealth.vurvhealth.medical.pojos.SaveForLaterFacility;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchFacilitiesResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SearchPractitionerResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SpecialtyReqPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.SpecialtyResPayLoad;
import com.VURVhealth.vurvhealth.medical.pojos.StateReqPayload;
import com.VURVhealth.vurvhealth.medical.pojos.StateResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.AddMemberReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.AddMemberResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.CancelSubscriptionReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.CancelSubscriptionResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.ChangePasswordReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.ChangePasswordResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.CurrentPackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPersonalInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPersonalInfoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditPhotoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditProfileRequestPojo;
import com.VURVhealth.vurvhealth.myProfile.pojos.EditProfileResponsePojo;
import com.VURVhealth.vurvhealth.myProfile.pojos.EmailStatusReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.GetPackageResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MobileStatusResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMemberListPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.MyMembersResponse;
import com.VURVhealth.vurvhealth.myProfile.pojos.PrimaryMemberTypeResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoReqPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UpdateBillingInfoResPayload;
import com.VURVhealth.vurvhealth.myProfile.pojos.UserDetailDataResPayload;
import com.VURVhealth.vurvhealth.prescriptions.DrugSearchResultReqPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.DrugSearchResultResPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.DrugSearchResultResPayLoad1;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugInfoReqPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugInfoResponse;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNameRes1;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNdcReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNdcRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugNdcRes1;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugQntReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugQntRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugStrengthReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.DrugStrengthRes;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.GenerateTokenReq;
import com.VURVhealth.vurvhealth.prescriptions.drugpojo.GenerateTokenRes;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchReqPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.InsertRecentSearchRespPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.PrescriptionSavedData;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.PrescriptionSavedDataResponse;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.RecentSearchRequestPayLoad;
import com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos.RecentSearchResponcePayLoad;
import com.VURVhealth.vurvhealth.save.pojos.PrescriptionDetailsRequestPojo;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterAllRequestPojo;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterAllResponsePojo;
import com.VURVhealth.vurvhealth.save.pojos.SaveForLaterRequestPojo;
import com.VURVhealth.vurvhealth.save.pojos.SavedItemDetailReqPayload;
import com.VURVhealth.vurvhealth.save.pojos.SavedItemDetailResPayload;
import com.VURVhealth.vurvhealth.upgrade.pojos.DeleteMemberRequest;
import com.VURVhealth.vurvhealth.upgrade.pojos.GetSubPackageReqPayload;
import com.VURVhealth.vurvhealth.upgrade.pojos.GetSubPackageResPayload;
import com.VURVhealth.vurvhealth.upgrade.pojos.SubPackageDetailReqPayload;
import com.VURVhealth.vurvhealth.upgrade.pojos.SubPackageDetailResPayload;
import com.VURVhealth.vurvhealth.utilities.StatusResponseForTotalProject;
import com.VURVhealth.vurvhealth.vision.pojos.DoctorSpecialtyResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.OfficeSpecialtyReqPayLoad;
import com.VURVhealth.vurvhealth.vision.pojos.OfficeSpecialtyResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.SaveForLaterVision;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionReqPayload;
import com.VURVhealth.vurvhealth.vision.pojos.SearchForVisionResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionInsertPastSearchReqPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionLangReqPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionLangResPayload;
import com.VURVhealth.vurvhealth.vision.pojos.VisionProviderIdResPayload;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {


    @POST("user/public/addMember")
    Call<AddMemberResPayload> addMember(@Body ArrayList<AddMemberReqPayload> arrayList);

    @POST("alternativeMedicine/AHSinsertPastSearches")
    Call<ArrayList<RecentSearchResponcePayLoad>> ahsPastSearch(@Body ArrayList<AHSPastSearchesReqPayload> arrayList);

    @POST("user/public/cancelSubscription")
    Call<CancelSubscriptionResPayload> cancelSubscription(@Body ArrayList<CancelSubscriptionReqPayload> arrayList);

    @POST("user/public/changeCurrentPassword")
    Call<ChangePasswordResPayload> changePassword(@Body ArrayList<ChangePasswordReqPayload> arrayList);

    @POST("v1/request.api/")
    Call<String> chargeCreditCard(@Body String str);

    //Generate Token
    @POST("account/token")
    Call<GenerateTokenRes> getToken(@Body GenerateTokenReq tokenReq);

    //Prescriptions

    @GET("drug/query-ordered")
    Call<ArrayList<DrugNameRes1>> getDrugName1(@Header("Authorizer") String token, @Query(value = "query", encoded = true) String drugName);

    @GET("drug/get-drug-structure-data-sorted")
    Call<DrugNdcRes1> getDrugNdc1(@Header("Authorizer") String token, @Query(value = "seoname", encoded = true) String seoName, @Query(value = "resultlimit", encoded = true) int linit);

    @GET("pricing/get-lowest-tiered-price")
    Call<DrugSearchResultResPayLoad1> getPrice1(@Header("Authorizer") String token,
                                                @Query(value = "ndc", encoded = true) String ndc,
                                                @Query(value = "zipcode", encoded = true) String zipcode,
                                                @Query(value = "quantity", encoded = true) String quantity);

    @POST("prescriptions/getPrescriptionPastSearch")
    Call<ArrayList<RecentSearchResponcePayLoad>> getPastSearchPriscription(@Body ArrayList<RecentSearchRequestPayLoad> arrayList);

    @POST("prescriptions/getSavedData")
    Call<ArrayList<PrescriptionSavedDataResponse>> getSavedData(@Body ArrayList<PrescriptionSavedData> arrayList);

    @POST("prescriptions/getDetailsForSavedItem")
    Call<ArrayList<SavedItemDetailResPayload>> getSavedItemDetails(@Body ArrayList<SavedItemDetailReqPayload> arrayList);

    @POST("prescriptions/insertPrescriptionPastSearch")
    Call<ArrayList<InsertRecentSearchRespPayLoad>> insertRecentSearch(@Body ArrayList<InsertRecentSearchReqPayLoad> arrayList);

    @POST("prescriptions/getPharmacyDetails")
    Call<ArrayList<DrugSearchResultResPayLoad.Datum>> prescriptionDeatails(@Body ArrayList<PrescriptionDetailsRequestPojo> arrayList);

    @POST("prescriptions/saveForLater")
    Call<ArrayList<StatusResponseForTotalProject>> saveForLater(@Body ArrayList<SaveForLaterRequestPojo> arrayList);

    @POST("prescriptions/saveForLaterAll")
    Call<ArrayList<SaveForLaterAllResponsePojo>> saveForLaterAll(@Body ArrayList<SaveForLaterAllRequestPojo> arrayList);

    @POST("prescriptions/getPrescriptionPastSearchClear")
    Call<ArrayList<RecentSearchResponcePayLoad>> clearPastSearchPriscription(@Body ArrayList<RecentSearchRequestPayLoad> arrayList);

    @POST("prescriptions/getDrugNameOnPrescription")
    Call<ArrayList<DrugInfoResponse>> getDrugInfo(@Body ArrayList<DrugInfoReqPayLoad> arrayList);

    @POST("prescriptions/getDrugNameOnPrescriptionDistinct")
    Call<DrugNameRes> getDrugName(@Body ArrayList<DrugNameReq> arrayList);  //new service

    @POST("prescriptions/getDrugStrengthDistinct")
    Call<DrugStrengthRes> getDrugStrength(@Body ArrayList<DrugStrengthReq> arrayList);

    @POST("prescriptions/getDrugQuantityDistinct")
    Call<DrugQntRes> getDrugQnt(@Body ArrayList<DrugQntReq> arrayList);

    @POST("prescriptions/getNDCFromObj")
    Call<DrugNdcRes> getDrugNdc(@Body ArrayList<DrugNdcReq> arrayList); //new service

    @POST("prescriptions/getPrice")
    Call<DrugSearchResultResPayLoad> getDrugSearchResult(@Body DrugSearchResultReqPayLoad reqPayLoad);


    Call<LanguageResPayLoad> createUser(@Body LanguagesReqPayLoad languagesReqPayLoad);

    @POST("v1/request.api/")
    Call<String> createUserProfile(@Body String str);

    @POST("dental/dentalProviderDetails")
    Call<ArrayList<SearchForDentalResPayLoad>> dentalDetails(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);

    @POST("dental/insertDentalPastSearches")
    Call<ArrayList<InsertRecentSearchRespPayLoad>> dentalPastSearch(@Body ArrayList<DentalPastSearchReqPayload> arrayList);

    @POST("user/public/DisplayPhoto")
    Call<EditPhotoResPayload> displayPhotoService(@Body ArrayList<MyMemberListPayload> arrayList);

    @POST("master/insertPracPastSearches")
    Call<ArrayList<InsertRecentSearchRespPayLoad>> docPastSearch(@Body ArrayList<DoctorPastSearchReqPayload> arrayList);

    @POST("master/getAboutDataForDoctors")
    Call<ArrayList<SearchPractitionerResPayLoad>> doctorDetails(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);

    @POST("user/public/EditPhoto")
    @Multipart
    Call<EditPhotoResPayload> editPhotoService(@Part MultipartBody.Part part, @Part("user_id") RequestBody requestBody);

    @POST("public/EditPhoto")
    Call<ArrayList<EditProfileResponsePojo>> editProfileServiceCall(@Body ArrayList<EditProfileRequestPojo> arrayList);

    @POST("master/insertFacPastSearches")
    Call<ArrayList<InsertRecentSearchRespPayLoad>> facPastSearch(@Body ArrayList<FacPastSearchReqPayload> arrayList);

    @POST("alternativeMedicine/AHSProviderDetails")
    Call<ArrayList<AHSProviderDetailsRespayload>> getAHSProviderDetails(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);

    @POST("alternativeMedicine/AHSspecialty")
    Call<ArrayList<AHSSpecialtyResPayload>> getAHSSpecialty(@Body ArrayList<AHSSpecialtyReqPayload> arrayList);

    @POST("master/getAboutDataForDoctors")
    Call<ArrayList<AboutDoctorResPayLoad>> getAboutDoctorsService(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);

    @POST("user/public/getCurrentPackage")
    Call<ArrayList<CurrentPackageResPayload>> getCurrentPackage(@Body ArrayList<MyMemberListPayload> arrayList);

    @POST("v1/request.api/")
    Call<String> getCustomerShippingAddress(@Body String str);

    @POST("user/public/deleteMember")
    Call<MobileStatusResPayload> getDeleteMember(@Body ArrayList<DeleteMemberRequest> arrayList);

    @POST("dental/languages")
    Call<ArrayList<DentalLangResPayLoad>> getDentalLanguages(@Body ArrayList<LanguagesReqPayLoad> arrayList);

    @POST("dental/dentalProviderDetails")
    Call<ArrayList<DentalProviderDetailsResPayload>> getDentalProviderDetails(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);

    @POST("dental/specialty")
    Call<ArrayList<SpecialtyDentalResPayload>> getDentalSpecialty(@Body ArrayList<SpecialtyReqPayLoad> arrayList);

    @POST("vision/doctorSpecialty")
    Call<ArrayList<DoctorSpecialtyResPayload>> getDoctorSpecialtyService(@Body ArrayList<DoctorSpecialtyResPayload> arrayList);

    @GET("getDrug")
    Call<ArrayList<DrugInfoResponse>> getDrugInfo(@Query("drugName") String str);

    @POST("user/public/getEmailStatus")
    Call<MobileStatusResPayload> getEmailStatus(@Body ArrayList<EmailStatusReqPayload> arrayList);

    @POST("master/getFacilityDetails")
    Call<ArrayList<FacilityDeatilResPayload>> getFacilityDetailsService(@Body ArrayList<FacilityDetailReqPayload> arrayList);

    @POST("master/getFacilitySubType")
    Call<ArrayList<FacilitySubTypeResPayload>> getFacilitySubTypeService(@Body ArrayList<FacilityTypeReqPayLoad> arrayList);

    @POST("master/getFacilityType")
    Call<ArrayList<FacilityTypeResPayLoad>> getFacilityTypeService(@Body ArrayList<FacilityTypeReqPayLoad> arrayList);

    @POST("user/public/forgotPwdSendEmail")
    Call<ForgotPasswordResPayLoad> getForgotPasswordService(@Body ArrayList<ForgotPasswordReqPayLoad> arrayList);

    @POST("user/public/forgotPwdSendOTP")
    Call<ForgotPasswordResPayLoad> getForgotPasswordSendOtp(@Body ArrayList<ForgotPasswordReqSendOtpPayLoad> arrayList);

    @POST("master/hospitalAffiliation")
    Call<ArrayList<HospitalAffiliationResPayLoad>> getHospitalAffiliation(@Body ArrayList<HospitalAffiliationReqPayLoad> arrayList);

    @POST("master/languages")
    Call<ArrayList<LanguageResPayLoad>> getLanguages(@Body ArrayList<LanguagesReqPayLoad> arrayList);

    @GET("user/generate_auth_cookie")
     Call<LoginResPayLoad> getLoginWithUSerNameService(@Query("username") String str,
                                                       @Query("password") String str2,
                                                       @Query("insecure") String str3);

    @POST("user/public/getMemberTypePrimary")
    Call<ArrayList<PrimaryMemberTypeResPayload>> getMemberTypePrimary(@Body ArrayList<MyMemberListPayload> arrayList);

    @POST("user/public/getMobileStatus")
    Call<MobileStatusResPayload> getMobileStatus(@Body ArrayList<MobileStatusReqPayload> arrayList);

    @POST("user/public/getMyMemberList")
    Call<ArrayList<MyMembersResponse>> getMyMemberList(@Body ArrayList<MyMemberListPayload> arrayList);

    @GET("get_nonce")
    Call<NonceResPayLoad> getNonce(@Query("controller") String str, @Query("method") String str2);

    @POST("vision/officeSpecialty")
    Call<ArrayList<OfficeSpecialtyResPayload>> getOfficeSpecialtyService(@Body ArrayList<OfficeSpecialtyReqPayLoad> arrayList);

    @GET("user/public/getPackage")
    Call<ArrayList<GetPackageResPayload>> getPackageInfo();

    @POST("user/public/getNewFaqPageContent")
    @Headers({"Content-type: application/json"})
    Call<String> getPageContent(@Body String str);

    @GET("privacy")
    Call<GetPageResPayload> getPrivacy(@Query("json") String str);

    @GET("user/register")
    Call<RegistrationResPayLoad> getRegistration(@Query("username") String str, @Query("user_pass") String str2, @Query("email") String str3, @Query("nonce") String str4, @Query("display_name") String str5);

    @POST("master/searchForFacilities")
    Call<ArrayList<SearchFacilitiesResPayLoad>> getSearchFacilities(@Body ArrayList<SearchFacilitiesReqPayLoad> arrayList);

    @POST("dental/SearchForDental")
    Call<SearchForDentalResPayLoad> getSearchForDental(@Body ArrayList<SearchForDentalReqPayload> arrayList);

    @POST("vision/SearchForVision")
    Call<SearchForVisionResPayload> getSearchForVisionService(@Body ArrayList<SearchForVisionReqPayload> arrayList);

    @POST("master/searchForPractitioner")
    Call<ArrayList<SearchPractitionerResPayLoad>> getSearchPractitioner(@Body ArrayList<SearchPractitionerReqPayLoad> arrayList);

    @POST("master/specialty")
    Call<ArrayList<SpecialtyResPayLoad>> getSpecialty(@Body ArrayList<SpecialtyReqPayLoad> arrayList);

    @POST("master/state")
    Call<ArrayList<StateResPayload>> getState(@Body ArrayList<StateReqPayload> arrayList);

    @POST("user/public/getSubPackage")
    Call<ArrayList<GetSubPackageResPayload>> getSubPackage(@Body ArrayList<GetSubPackageReqPayload> arrayList);

    @POST("user/public/getSubPackageDetail")
    Call<ArrayList<SubPackageDetailResPayload>> getSubPackageDetails(@Body ArrayList<SubPackageDetailReqPayload> arrayList);

    @GET("terms-of-service")
    Call<GetPageResPayload> getTermsandConditions(@Query("json") String str);

    @POST("user/public/orders/create")
    Call<UpgradePackageResPayload> getUpgradePackage(@Body ArrayList<UpgradePackageReqPayload> arrayList);

    @POST("user/public/userDetailData")
    Call<ArrayList<ArrayList<UserDetailDataResPayload>>> getUserDetailData(@Body ArrayList<MyMemberListPayload> arrayList);

    @POST("user/public/vurvUpdate")
    Call<VurvIdResPayLoad> getVURVIDService(@Body ArrayList<VurvIdReqPayLoad> arrayList);

    @POST("vision/visionLanguages")
    Call<ArrayList<VisionLangResPayload>> getVisionLanguage(@Body ArrayList<VisionLangReqPayload> arrayList);

    @POST("vision/visionProviderDetails")
    Call<ArrayList<VisionProviderIdResPayload>> getVisionProviderDetails(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);

    @POST("vision/insertPastSearches")
    Call<ArrayList<InsertRecentSearchRespPayLoad>> insertPastSearchesVision(@Body ArrayList<VisionInsertPastSearchReqPayload> arrayList);


    @POST("user/public/updatePersonalInfo")
    Call<ArrayList<ArrayList<EditPersonalInfoResPayload>>> personalInfoService(@Body ArrayList<EditPersonalInfoReqPayload> arrayList);

    @POST("alternativeMedicine/AHSsaveForLater")
    Call<ArrayList<StatusResponseForTotalProject>> saveForLaterAHS(@Body ArrayList<SaveForLaterVision> arrayList);

    @POST("dental/dentalSaveForLater")
    Call<ArrayList<StatusResponseForTotalProject>> saveForLaterDental(@Body ArrayList<SaveForLaterVision> arrayList);

    @POST("master/PracSaveForLater")
    Call<ArrayList<StatusResponseForTotalProject>> saveForLaterDoctor(@Body ArrayList<SaveForLaterDoctors> arrayList);

    @POST("master/FacSaveForLater")
    Call<ArrayList<StatusResponseForTotalProject>> saveForLaterFacility(@Body ArrayList<SaveForLaterFacility> arrayList);

    @POST("vision/visionSaveForLater")
    Call<ArrayList<StatusResponseForTotalProject>> saveForLaterVision(@Body ArrayList<SaveForLaterVision> arrayList);

    @POST("alternativeMedicine/SearchForAHSProvider")
    Call<AHSSearchResPayload> searchForAHSProvider(@Body ArrayList<AHSSearchReqPayload> arrayList);

    @POST("user/public/updateBillingInfo")
    Call<ArrayList<ArrayList<UpdateBillingInfoResPayload>>> updateBillingInfo(@Body ArrayList<UpdateBillingInfoReqPayload> arrayList);

    @POST("vision/visionProviderDetails")
    Call<ArrayList<SearchForVisionResPayload>> visionDetails(@Body ArrayList<AboutDoctorReqPayLoad> arrayList);





    @GET("categories/44000315993/folders")
    @Headers({
            "Accept: application/json",
            "Authorization: Basic d3dHWk93NFkxY0pDUndabUhRVmk6WA=="
    })
    Call<ArrayList<FreshDeskMainListRes>> getFreshdeskhelp();




    @GET("folders/{user_id}/articles")
    @Headers({
            "Accept: application/json",
            "Authorization: Basic d3dHWk93NFkxY0pDUndabUhRVmk6WA=="
    })
    Call<ArrayList<FreshDeskSubListRes>> getFreshdeskSubList(@Path(value = "user_id", encoded = true) String userId);

}
