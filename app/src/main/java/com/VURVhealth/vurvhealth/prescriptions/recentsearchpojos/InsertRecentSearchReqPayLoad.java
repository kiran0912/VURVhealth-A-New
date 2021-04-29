package com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertRecentSearchReqPayLoad {
    @SerializedName("Browser")
    @Expose
    private String Browser;
    @SerializedName("DeviceType")
    @Expose
    private String DeviceType;
    @SerializedName("IP")
    @Expose
    private String IP;
    @SerializedName("OperatingSystem")
    @Expose
    private String OperatingSystem;
    @SerializedName("DrugName")
    @Expose
    private String drugName;
    @SerializedName("SearchedLocation")
    @Expose
    private String searchedLocation;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("VurvId")
    @Expose
    private String vurvId;

    public String getIP() {
        return this.IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getDeviceType() {
        return this.DeviceType;
    }

    public void setDeviceType(String deviceType) {
        this.DeviceType = deviceType;
    }

    public String getOperatingSystem() {
        return this.OperatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.OperatingSystem = operatingSystem;
    }

    public String getBrowser() {
        return this.Browser;
    }

    public void setBrowser(String browser) {
        this.Browser = browser;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVurvId() {
        return this.vurvId;
    }

    public void setVurvId(String vurvId) {
        this.vurvId = vurvId;
    }

    public String getDrugName() {
        return this.drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSearchedLocation() {
        return this.searchedLocation;
    }

    public void setSearchedLocation(String searchedLocation) {
        this.searchedLocation = searchedLocation;
    }
}
