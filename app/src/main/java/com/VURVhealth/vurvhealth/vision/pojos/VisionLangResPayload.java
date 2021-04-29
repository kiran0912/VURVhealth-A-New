package com.VURVhealth.vurvhealth.vision.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisionLangResPayload {
    @SerializedName("visionLanguages")
    @Expose
    private String visionLanguages;

    public String getVisionLanguages() {
        return this.visionLanguages;
    }

    public void setVisionLanguages(String visionLanguages) {
        this.visionLanguages = visionLanguages;
    }
}
