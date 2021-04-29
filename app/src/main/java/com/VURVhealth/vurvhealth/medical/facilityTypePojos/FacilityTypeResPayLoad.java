package com.VURVhealth.vurvhealth.medical.facilityTypePojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityTypeResPayLoad {
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("FacilityType")
    @Expose
    private String facilityType;

    public String getFacilityType() {
        return this.facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
