package com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentSearchResponcePayLoad {
    @SerializedName("PastData")
    @Expose
    private String pastData;

    public String getPastData() {
        return this.pastData;
    }

    public void setPastData(String pastData) {
        this.pastData = pastData;
    }
}
