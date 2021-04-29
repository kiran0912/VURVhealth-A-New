package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalAffiliationResPayLoad {
    @SerializedName("hospitalaffilation")
    @Expose
    private String hName;

    public String getHName() {
        return this.hName;
    }

    public void setHName(String hName) {
        this.hName = hName;
    }
}
