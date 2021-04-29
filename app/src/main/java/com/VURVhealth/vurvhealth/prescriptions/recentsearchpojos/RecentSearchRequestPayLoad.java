package com.VURVhealth.vurvhealth.prescriptions.recentsearchpojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentSearchRequestPayLoad {
    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
