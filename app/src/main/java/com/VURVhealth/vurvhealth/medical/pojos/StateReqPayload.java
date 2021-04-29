package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateReqPayload {
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
