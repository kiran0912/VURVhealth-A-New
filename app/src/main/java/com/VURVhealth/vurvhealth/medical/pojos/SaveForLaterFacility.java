package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveForLaterFacility {
    @SerializedName("FacilityProviderId")
    @Expose
    private String facilityProviderId;
    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFacilityProviderId() {
        return this.facilityProviderId;
    }

    public void setFacilityProviderId(String facilityProviderId) {
        this.facilityProviderId = facilityProviderId;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
