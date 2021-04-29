package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugNameReq {
    @SerializedName("drugInitials")
    @Expose
    private String drugInitials;

    public String getDrugInitials() {
        return this.drugInitials;
    }

    public void setDrugInitials(String drugInitials) {
        this.drugInitials = drugInitials;
    }

    public DrugNameReq(String drugInitials) {
        this.drugInitials = drugInitials;
    }
}
