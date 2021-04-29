package com.VURVhealth.vurvhealth.prescriptions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugSearchResultReqPayLoad {

    @SerializedName("ndc_code")
    @Expose
    private String ndcCode;

    @SerializedName("zip_code")
    @Expose
    private String zipCode;

    @SerializedName("quantity")
    @Expose
    private String quantity;


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNdcCode() {
        return ndcCode;
    }

    public void setNdcCode(String ndcCode) {
        this.ndcCode = ndcCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
/* @SerializedName("drug_code")
    @Expose
    private String drugCode;
    @SerializedName("DrugName")
    @Expose
    private String drugName;
    @SerializedName("drug_type")
    @Expose
    private String drugType;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("ndc_code")
    @Expose
    private String ndcCode;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getDrugType() {
        return this.drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getNdcCode() {
        return this.ndcCode;
    }

    public void setNdcCode(String ndcCode) {
        this.ndcCode = ndcCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDrugCode() {
        return this.drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return this.drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }*/
}
