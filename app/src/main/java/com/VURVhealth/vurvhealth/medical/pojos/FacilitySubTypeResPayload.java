package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilitySubTypeResPayload {
    @SerializedName("FacilitySubType")
    @Expose
    private String facilitySubType;

    public String getFacilitySubType() {
        return this.facilitySubType;
    }

    public void setFacilitySubType(String facilitySubType) {
        this.facilitySubType = facilitySubType;
    }
}
