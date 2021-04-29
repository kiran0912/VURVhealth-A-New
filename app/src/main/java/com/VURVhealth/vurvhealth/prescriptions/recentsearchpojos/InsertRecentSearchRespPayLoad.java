package com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertRecentSearchRespPayLoad {
    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
