package com.VURVhealth.vurvhealth.authentication.loginPojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 1/3/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class LoginResPayLoad {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("cookie_name")
    @Expose
    private String cookieName;
    @SerializedName("package_info")
    @Expose
    private PackageInfo packageInfo;
    @SerializedName("user")
    @Expose
    private User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class PackageInfo {

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

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedDatetime() {
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

    }

    public static class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("nicename")
        @Expose
        private String nicename;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("registered")
        @Expose
        private String registered;
        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("nickname")
        @Expose
        private String nickname;
        @SerializedName("description")
        @Expose
        private String description;
//        @SerializedName("capabilities")
//        @Expose
//        private Capabilities capabilities;
        @SerializedName("avatar")
        @Expose
        private String avatar;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNicename() {
            return nicename;
        }

        public void setNicename(String nicename) {
            this.nicename = nicename;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

//        public Capabilities getCapabilities() {
//            return capabilities;
//        }
//
//        public void setCapabilities(Capabilities capabilities) {
//            this.capabilities = capabilities;
//        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

    }
//    public static class Capabilities {
//
//        @SerializedName("subscriber")
//        @Expose
//        private Boolean subscriber;
//
//        public Boolean getSubscriber() {
//            return subscriber;
//        }
//
//        public void setSubscriber(Boolean subscriber) {
//            this.subscriber = subscriber;
//        }
//
//    }
}