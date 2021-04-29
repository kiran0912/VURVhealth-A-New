package com.VURVhealth.vurvhealth.vision.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfficeSpecialtyReqPayLoad {
    @SerializedName("officeSpecialty")
    @Expose
    private String officeSpecialty;

    public String getOfficeSpecialty() {
        return this.officeSpecialty;
    }

    public void setOfficeSpecialty(String officeSpecialty) {
        this.officeSpecialty = officeSpecialty;
    }
}
