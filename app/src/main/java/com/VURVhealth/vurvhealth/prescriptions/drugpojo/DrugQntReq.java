package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugQntReq {
    @SerializedName("drugName")
    @Expose
    private String drugName;
    @SerializedName("drugStrength")
    @Expose
    private String drugStrength;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugStrength() {
        return drugStrength;
    }

    public void setDrugStrength(String drugStrength) {
        this.drugStrength = drugStrength;
    }

    public DrugQntReq(String drugName,String drugStrength) {
        this.drugName = drugName;
        this.drugStrength = drugStrength;
    }
}
