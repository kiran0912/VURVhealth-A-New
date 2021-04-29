package com.VURVhealth.vurvhealth.vision.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorSpecialtyResPayload {
    @SerializedName("doctorSpecialty")
    @Expose
    private String doctorSpecialty;

    public String getDoctorSpecialty() {
        return this.doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }
}
