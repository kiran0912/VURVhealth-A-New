package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailDataResPayload {
    @SerializedName("IsPasswordResetFlag")
    @Expose
    private String IsPasswordResetFlag;
    @SerializedName("OTP")
    @Expose
    private String OTP;
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
    @SerializedName("child_count")
    @Expose
    private String child_count;
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
    @SerializedName("name")
    @Expose
    private String name;
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
    private String paused_wef;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("search_type")
    @Expose
    private String search_type;
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
    @SerializedName("user_login")
    @Expose
    private String userLogin;
    @SerializedName("zip_four_code")
    @Expose
    private String zipFourCode;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public String getOTP() {
        return this.OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getPaused_wef() {
        return this.paused_wef;
    }

    public void setPaused_wef(String paused_wef) {
        this.paused_wef = paused_wef;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChild_count() {
        return this.child_count;
    }

    public void setChild_count(String child_count) {
        this.child_count = child_count;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostTitle() {
        return this.postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

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
        return this.IsPasswordResetFlag;
    }

    public void setIsPasswordResetFlag(String IsPasswordResetFlag) {
        this.IsPasswordResetFlag = IsPasswordResetFlag;
    }

    public String getSearch_type() {
        return this.search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }
}
