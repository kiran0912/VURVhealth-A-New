package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialtyReqPayLoad {
    @SerializedName("specialty")
    @Expose
    private String specialty;

    public SpecialtyReqPayLoad(String specaltyText) {
        this.specialty = specaltyText;
    }
}
