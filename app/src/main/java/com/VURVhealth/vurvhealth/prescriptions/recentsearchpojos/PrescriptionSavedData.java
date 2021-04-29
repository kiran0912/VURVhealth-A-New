package com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionSavedData {
    @SerializedName("GNRC_ALT_NDC")
    @Expose
    private String gNRCALTNDC;
    @SerializedName("NDC")
    @Expose
    private String nDC;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNDC() {
        return this.nDC;
    }

    public void setNDC(String nDC) {
        this.nDC = nDC;
    }

    public String getGNRCALTNDC() {
        return this.gNRCALTNDC;
    }

    public void setGNRCALTNDC(String gNRCALTNDC) {
        this.gNRCALTNDC = gNRCALTNDC;
    }
}
