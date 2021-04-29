package com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionSavedDataResponse {
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Drug_Type")
    @Expose
    private String drugType;
    @SerializedName("GNRC_ALT_NDC")
    @Expose
    private String gNRCALTNDC;
    @SerializedName("LBL_NME")
    @Expose
    private String lBLNME;
    @SerializedName("NCPDP")
    @Expose
    private String nCPDP;
    @SerializedName("NDC")
    @Expose
    private String nDC;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNCPDP() {
        return this.nCPDP;
    }

    public void setNCPDP(String nCPDP) {
        this.nCPDP = nCPDP;
    }

    public String getNDC() {
        return this.nDC;
    }

    public void setNDC(String nDC) {
        this.nDC = nDC;
    }

    public String getLBLNME() {
        return this.lBLNME;
    }

    public void setLBLNME(String lBLNME) {
        this.lBLNME = lBLNME;
    }

    public String getGNRCALTNDC() {
        return this.gNRCALTNDC;
    }

    public void setGNRCALTNDC(String gNRCALTNDC) {
        this.gNRCALTNDC = gNRCALTNDC;
    }

    public String getDrugType() {
        return this.drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
