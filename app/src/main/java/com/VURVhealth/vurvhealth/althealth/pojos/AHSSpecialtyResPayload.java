package com.VURVhealth.vurvhealth.althealth.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AHSSpecialtyResPayload {
    @SerializedName("Specialty")
    @Expose
    private String specialty;

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
