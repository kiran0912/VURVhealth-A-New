package com.VURVhealth.vurvhealth.save.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionDetailsRequestPojo {
    @SerializedName("PharmacyId")
    @Expose
    private String pharmacyId;

    public String getPharmacyId() {
        return this.pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
