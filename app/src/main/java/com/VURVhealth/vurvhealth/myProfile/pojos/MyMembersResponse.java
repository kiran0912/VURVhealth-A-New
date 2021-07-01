package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyMembersResponse {
    @SerializedName("active_user")
    @Expose
    private String activeUser;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_datetime")
    @Expose
    private String createdDatetime;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("IsPasswordResetFlag")
    @Expose
    private String isPasswordResetFlag;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("paused_wef")
    @Expose
    private String pausedWef;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("sub_package_id")
    @Expose
    private String subPackageId;
    @SerializedName("subscription_actual_startdate")
    @Expose
    private String subscriptionActualStartdate;
    @SerializedName("subscription_end_date")
    @Expose
    private String subscriptionEndDate;
    @SerializedName("subscription_start_date")
    @Expose
    private String subscriptionStartDate;
    @SerializedName("enrolled_date")
    @Expose
    private String enrolledDate;
    @SerializedName("cc_last_run_date")
    @Expose
    private String ccLastRunDate;
    @SerializedName("cc_next_run_date")
    @Expose
    private String ccNextRunDate;
    @SerializedName("vurv_mem_exp_date")
    @Expose
    private String vurvMemExpDate;
    @SerializedName("subscription_status")
    @Expose
    private String subscriptionStatus;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("updated_datetime")
    @Expose
    private String updatedDatetime;
    @SerializedName("user_code")
    @Expose
    private String userCode;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("zip_four_code")
    @Expose
    private String zipFourCode;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPackageId() {
        return this.packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getSubPackageId() {
        return this.subPackageId;
    }

    public void setSubPackageId(String subPackageId) {
        this.subPackageId = subPackageId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSubscriptionActualStartdate() {
        return this.subscriptionActualStartdate;
    }

    public void setSubscriptionActualStartdate(String subscriptionActualStartdate) {
        this.subscriptionActualStartdate = subscriptionActualStartdate;
    }

    public String getSubscriptionStartDate() {
        return this.subscriptionStartDate;
    }

    public void setSubscriptionStartDate(String subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public String getSubscriptionEndDate() {
        return this.subscriptionEndDate;
    }

    public void setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(String enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public String getCcLastRunDate() {
        return ccLastRunDate;
    }

    public void setCcLastRunDate(String ccLastRunDate) {
        this.ccLastRunDate = ccLastRunDate;
    }

    public String getCcNextRunDate() {
        return ccNextRunDate;
    }

    public void setCcNextRunDate(String ccNextRunDate) {
        this.ccNextRunDate = ccNextRunDate;
    }

    public String getVurvMemExpDate() {
        return vurvMemExpDate;
    }

    public void setVurvMemExpDate(String vurvMemExpDate) {
        this.vurvMemExpDate = vurvMemExpDate;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getActiveUser() {
        return this.activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDatetime() {
        return this.updatedDatetime;
    }

    public void setUpdatedDatetime(String updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    public String getSubscriptionStatus() {
        return this.subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipFourCode() {
        return this.zipFourCode;
    }

    public void setZipFourCode(String zipFourCode) {
        this.zipFourCode = zipFourCode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsPasswordResetFlag() {
        return this.isPasswordResetFlag;
    }

    public void setIsPasswordResetFlag(String isPasswordResetFlag) {
        this.isPasswordResetFlag = isPasswordResetFlag;
    }

    public String getOTP() {
        return this.oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public String getPausedWef() {
        return this.pausedWef;
    }

    public void setPausedWef(String pausedWef) {
        this.pausedWef = pausedWef;
    }

    public MyMembersResponse(String id, String memberId, String first_name, String mem_l_name, String mem_dob, String mem_email, String mem_gen) {
        this.userId = id;
        this.firstName = first_name;
        this.lastName = mem_l_name;
        this.dateOfBirth = mem_dob;
        this.userEmail = mem_email;
        this.gender = mem_gen;
    }
}
