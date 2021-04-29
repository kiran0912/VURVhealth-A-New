package com.VURVhealth.vurvhealth.vision.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisionLangReqPayload {
    @SerializedName("visionLangInit")
    @Expose
    private String visionLangInit;

    public String getVisionLangInit() {
        return this.visionLangInit;
    }

    public void setVisionLangInit(String visionLangInit) {
        this.visionLangInit = visionLangInit;
    }
}
