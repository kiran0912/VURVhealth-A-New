package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMemberReqPayload {
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("child_birthdate")
    @Expose
    private String childBirthdate;
    @SerializedName("child_first_name")
    @Expose
    private String childFirstName;
    @SerializedName("child_last_name")
    @Expose
    private String childLastName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("parent_user_id")
    @Expose
    private Integer parentUserId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip_four_code")
    @Expose
    private String zipFourCode;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public Integer getParentUserId() {
        return this.parentUserId;
    }

    public void setParentUserId(Integer parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getChildFirstName() {
        return this.childFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        this.childFirstName = childFirstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getChildLastName() {
        return this.childLastName;
    }

    public void setChildLastName(String childLastName) {
        this.childLastName = childLastName;
    }

    public String getChildBirthdate() {
        return this.childBirthdate;
    }

    public void setChildBirthdate(String childBirthdate) {
        this.childBirthdate = childBirthdate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
