package com.VURVhealth.vurvhealth.medical.aboutDoctorPojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutDoctorReqPayLoad {
    @SerializedName("ProviderId")
    @Expose
    private String providerId;

    public String getProviderId() {
        return this.providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
