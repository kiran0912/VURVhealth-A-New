package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugNdcReq {

    @SerializedName("drugName")
    @Expose
    private String drugName;
    @SerializedName("drugStrength")
    @Expose
    private String drugStrength;
    @SerializedName("drugQuantity")
    @Expose
    private String drugQuantity;

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

    public String getDrugQuantity() {
        return drugQuantity;
    }

    public void setDrugQuantity(String drugQuantity) {
        this.drugQuantity = drugQuantity;
    }

    public DrugNdcReq(String drugName, String drugStrength,String drugQuantity) {
        this.drugName = drugName;
        this.drugStrength = drugStrength;
        this.drugQuantity = drugQuantity;
    }
}
