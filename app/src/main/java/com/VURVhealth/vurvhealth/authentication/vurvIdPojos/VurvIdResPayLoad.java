package com.VURVhealth.vurvhealth.authentication.vurvIdPojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yqlabs on 1/3/17.
 */

public class VurvIdResPayLoad {
    @SerializedName("userDetail:")
    @Expose
    private List<UserDetail> userDetail = null;
    @SerializedName("subscriberDetail:")
    @Expose
    private List<SubscriberDetail> subscriberDetail = null;

    public List<UserDetail> getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(List<UserDetail> userDetail) {
        this.userDetail = userDetail;
    }

    public List<SubscriberDetail> getSubscriberDetail() {
        return subscriberDetail;
    }

    public void setSubscriberDetail(List<SubscriberDetail> subscriberDetail) {
        this.subscriberDetail = subscriberDetail;
    }
    public static class SubscriberDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("parent_id")
        @Expose
        private String parentId;
        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("middle_name")
        @Expose
        private String middleName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("date_of_birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("sub_package_id")
        @Expose
        private String subPackageId;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("mobile_no")
        @Expose
        private String mobileNo;
        @SerializedName("subscription_actual_startdate")
        @Expose
        private String subscriptionActualStartdate;
        @SerializedName("subscription_start_date")
        @Expose
        private String subscriptionStartDate;
        @SerializedName("subscription_end_date")
        @Expose
        private String subscriptionEndDate;
        @SerializedName("role_id")
        @Expose
        private String roleId;
        @SerializedName("business_id")
        @Expose
        private String businessId;
        @SerializedName("user_code")
        @Expose
        private String userCode;
        @SerializedName("active_user")
        @Expose
        private String activeUser;
        @SerializedName("image_path")
        @Expose
        private String imagePath;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_datetime")
        @Expose
        private String createdDatetime;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("updated_datetime")
        @Expose
        private String updatedDatetime;
        @SerializedName("subscription_status")
        @Expose
        private String subscriptionStatus;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("zip_four_code")
        @Expose
        private String zipFourCode;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("term_id")
        @Expose
        private String termId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("term_group")
        @Expose
        private String termGroup;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getSubPackageId() {
            return subPackageId;
        }

        public void setSubPackageId(String subPackageId) {
            this.subPackageId = subPackageId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getSubscriptionActualStartdate() {
            return subscriptionActualStartdate;
        }

        public void setSubscriptionActualStartdate(String subscriptionActualStartdate) {
            this.subscriptionActualStartdate = subscriptionActualStartdate;
        }

        public String getSubscriptionStartDate() {
            return subscriptionStartDate;
        }

        public void setSubscriptionStartDate(String subscriptionStartDate) {
            this.subscriptionStartDate = subscriptionStartDate;
        }

        public String getSubscriptionEndDate() {
            return subscriptionEndDate;
        }

        public void setSubscriptionEndDate(String subscriptionEndDate) {
            this.subscriptionEndDate = subscriptionEndDate;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getActiveUser() {
            return activeUser;
        }

        public void setActiveUser(String activeUser) {
            this.activeUser = activeUser;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDatetime() {
            return createdDatetime;
        }

        public void setCreatedDatetime(String createdDatetime) {
            this.createdDatetime = createdDatetime;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getUpdatedDatetime() {
            return updatedDatetime;
        }

        public void setUpdatedDatetime(String updatedDatetime) {
            this.updatedDatetime = updatedDatetime;
        }

        public String getSubscriptionStatus() {
            return subscriptionStatus;
        }

        public void setSubscriptionStatus(String subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getZipFourCode() {
            return zipFourCode;
        }

        public void setZipFourCode(String zipFourCode) {
            this.zipFourCode = zipFourCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTermId() {
            return termId;
        }

        public void setTermId(String termId) {
            this.termId = termId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getTermGroup() {
            return termGroup;
        }

        public void setTermGroup(String termGroup) {
            this.termGroup = termGroup;
        }

    }

    public static class UserDetail {

        @SerializedName("ID")
        @Expose
        private String iD;
        @SerializedName("user_login")
        @Expose
        private String userLogin;
        @SerializedName("user_pass")
        @Expose
        private String userPass;
        @SerializedName("user_nicename")
        @Expose
        private String userNicename;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_url")
        @Expose
        private String userUrl;
        @SerializedName("user_registered")
        @Expose
        private String userRegistered;
        @SerializedName("user_activation_key")
        @Expose
        private String userActivationKey;
        @SerializedName("user_status")
        @Expose
        private String userStatus;
        @SerializedName("display_name")
        @Expose
        private String displayName;

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getUserLogin() {
            return userLogin;
        }

        public void setUserLogin(String userLogin) {
            this.userLogin = userLogin;
        }

        public String getUserPass() {
            return userPass;
        }

        public void setUserPass(String userPass) {
            this.userPass = userPass;
        }

        public String getUserNicename() {
            return userNicename;
        }

        public void setUserNicename(String userNicename) {
            this.userNicename = userNicename;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserUrl() {
            return userUrl;
        }

        public void setUserUrl(String userUrl) {
            this.userUrl = userUrl;
        }

        public String getUserRegistered() {
            return userRegistered;
        }

        public void setUserRegistered(String userRegistered) {
            this.userRegistered = userRegistered;
        }

        public String getUserActivationKey() {
            return userActivationKey;
        }

        public void setUserActivationKey(String userActivationKey) {
            this.userActivationKey = userActivationKey;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

    }
}
