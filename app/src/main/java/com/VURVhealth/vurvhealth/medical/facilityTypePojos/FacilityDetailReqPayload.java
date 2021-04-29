package com.VURVhealth.vurvhealth.medical.facilityTypePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityDetailReqPayload {
    @SerializedName("FacilityProviderId")
    @Expose
    private String facilityProviderId;

    public String getFacilityProviderId() {
        return this.facilityProviderId;
    }

    public void setFacilityProviderId(String facilityProviderId) {
        this.facilityProviderId = facilityProviderId;
    }
}
