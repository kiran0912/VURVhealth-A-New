package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelSubscriptionResPayload {
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
