package com.VURVhealth.vurvhealth.althealth.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AHSPastSearchesReqPayload {
    @SerializedName("Browser")
    @Expose
    private String browser;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("DeviceType")
    @Expose
    private String deviceType;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("IP")
    @Expose
    private String iP;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("OperatingSystem")
    @Expose
    private String operatingSystem;
    @SerializedName("Range")
    @Expose
    private String range;
    @SerializedName("Specialities")
    @Expose
    private String specialities;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRange() {
        return this.range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialities() {
        return this.specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getIP() {
        return this.iP;
    }

    public void setIP(String iP) {
        this.iP = iP;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOperatingSystem() {
        return this.operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getBrowser() {
        return this.browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
