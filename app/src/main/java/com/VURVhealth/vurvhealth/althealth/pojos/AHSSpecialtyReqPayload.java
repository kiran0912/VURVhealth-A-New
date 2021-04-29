package com.VURVhealth.vurvhealth.althealth.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AHSSpecialtyReqPayload {
    @SerializedName("AHSspecialty")
    @Expose
    private String aHSspecialty;

    public String getAHSspecialty() {
        return this.aHSspecialty;
    }

    public void setAHSspecialty(String aHSspecialty) {
        this.aHSspecialty = aHSspecialty;
    }
}
