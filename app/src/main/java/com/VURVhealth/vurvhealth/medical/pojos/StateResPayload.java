package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResPayload {
    @SerializedName("State")
    @Expose
    private String facState;

    public String getFacState() {
        return this.facState;
    }

    public void setFacState(String facState) {
        this.facState = facState;
    }
}
