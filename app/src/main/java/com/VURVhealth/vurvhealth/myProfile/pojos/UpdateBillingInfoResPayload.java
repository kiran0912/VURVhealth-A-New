package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateBillingInfoResPayload {
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
    @SerializedName("isPasswordReset")
    @Expose
    private String isPasswordReset;
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
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("sub_package_id")
    @Expose
    private Object subPackageId;
    @SerializedName("subscription_actual_startdate")
    @Expose
    private String subscriptionActualStartdate;
    @SerializedName("subscription_end_date")
    @Expose
    private Object subscriptionEndDate;
    @SerializedName("subscription_start_date")
    @Expose
    private String subscriptionStartDate;
    @SerializedName("subscription_status")
    @Expose
    private String subscriptionStatus;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("updated_datetime")
    @Expose
    private Object updatedDatetime;
    @SerializedName("user_code")
    @Expose
    private Object userCode;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("zip_four_code")
    @Expose
    private String zipFourCode;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public Object getSubPackageId() {
        return this.subPackageId;
    }

    public void setSubPackageId(Object subPackageId) {
        this.subPackageId = subPackageId;
    }

    public Object getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Object orderId) {
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

    public Object getSubscriptionEndDate() {
        return this.subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Object subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
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

    public Object getUserCode() {
        return this.userCode;
    }

    public void setUserCode(Object userCode) {
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

    public Object getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdatedDatetime() {
        return this.updatedDatetime;
    }

    public void setUpdatedDatetime(Object updatedDatetime) {
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

    public String getIsPasswordReset() {
        return this.isPasswordReset;
    }

    public void setIsPasswordReset(String isPasswordReset) {
        this.isPasswordReset = isPasswordReset;
    }
}
