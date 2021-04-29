package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugStrengthReq {
    @SerializedName("drugName")
    @Expose
    private String drugName;

    public String getDrugName() {
        return this.drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public DrugStrengthReq(String drugName) {
        this.drugName = drugName;
    }
}
