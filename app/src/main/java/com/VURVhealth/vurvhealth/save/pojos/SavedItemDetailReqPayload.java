package com.VURVhealth.vurvhealth.save.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedItemDetailReqPayload {
    @SerializedName("saveForLaterId")
    @Expose
    private String saveForLaterId;

    public String getSaveForLaterId() {
        return this.saveForLaterId;
    }

    public void setSaveForLaterId(String saveForLaterId) {
        this.saveForLaterId = saveForLaterId;
    }
}
