package com.VURVhealth.vurvhealth.medical.facilityTypePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacPastSearchReqPayload {
    @SerializedName("Browser")
    @Expose
    private String browser;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("DeviceType")
    @Expose
    private String deviceType;
    @SerializedName("FacilityName")
    @Expose
    private String facilityName;
    @SerializedName("FacilitySubType")
    @Expose
    private String facilitySubType;
    @SerializedName("FacilityType")
    @Expose
    private String facilityType;
    @SerializedName("IP")
    @Expose
    private String iP;
    @SerializedName("OperatingSystem")
    @Expose
    private String operatingSystem;
    @SerializedName("Ranges")
    @Expose
    private String ranges;
    @SerializedName("State")
    @Expose
    private String state;
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

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRanges() {
        return this.ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
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

    public String getFacilityName() {
        return this.facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityType() {
        return this.facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilitySubType() {
        return this.facilitySubType;
    }

    public void setFacilitySubType(String facilitySubType) {
        this.facilitySubType = facilitySubType;
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
