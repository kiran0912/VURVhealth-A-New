package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveForLaterDoctors {
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("LocationKey")
    @Expose
    private String locationKey;
    @SerializedName("PractitionerProviderId")
    @Expose
    private String practitionerProviderId;
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

    public String getPractitionerProviderId() {
        return this.practitionerProviderId;
    }

    public void setPractitionerProviderId(String practitionerProviderId) {
        this.practitionerProviderId = practitionerProviderId;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }
}
