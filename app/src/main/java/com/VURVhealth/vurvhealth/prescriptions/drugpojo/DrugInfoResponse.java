package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugInfoResponse {
    @SerializedName("DOS_FRM_DESCN")
    @Expose
    private String dOSFRMDESCN;
    @SerializedName("DRG_STRENGTH")
    @Expose
    private String dRGSTRENGTH;
    @SerializedName("DRUG_TYPE")
    @Expose
    private String dRUGTYPE;
    @SerializedName("DrugId")
    @Expose
    private String drugId;
    @SerializedName("DrugName")
    @Expose
    private String drugName;
    @SerializedName("GNRC_ALT_NDC")
    @Expose
    private String gNRCALTNDC;
    @SerializedName("NDC")
    @Expose
    private String nDC;

    public String getDrugId() {
        return this.drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return this.drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDOSFRMDESCN() {
        return this.dOSFRMDESCN;
    }

    public void setDOSFRMDESCN(String dOSFRMDESCN) {
        this.dOSFRMDESCN = dOSFRMDESCN;
    }

    public String getDRGSTRENGTH() {
        return this.dRGSTRENGTH;
    }

    public void setDRGSTRENGTH(String dRGSTRENGTH) {
        this.dRGSTRENGTH = dRGSTRENGTH;
    }

    public String getNDC() {
        return this.nDC;
    }

    public void setNDC(String nDC) {
        this.nDC = nDC;
    }

    public String getDRUGTYPE() {
        return this.dRUGTYPE;
    }

    public void setDRUGTYPE(String dRUGTYPE) {
        this.dRUGTYPE = dRUGTYPE;
    }

    public String getGNRCALTNDC() {
        return this.gNRCALTNDC;
    }

    public void setGNRCALTNDC(String gNRCALTNDC) {
        this.gNRCALTNDC = gNRCALTNDC;
    }
}
