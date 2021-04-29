package com.VURVhealth.vurvhealth.dental.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialtyDentalResPayload {
    @SerializedName("specialty")
    @Expose
    private String specialty;
    @SerializedName("specialtyCode")
    @Expose
    private String specialtyCode;

    public String getSpecialtyCode() {
        return this.specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
