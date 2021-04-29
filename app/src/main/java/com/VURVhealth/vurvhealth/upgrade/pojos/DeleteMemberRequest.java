package com.VURVhealth.vurvhealth.upgrade.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 26/7/17.
 */

public class DeleteMemberRequest {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("vurv_Id")
    @Expose
    private String vurvId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVurvId() {
        return vurvId;
    }

    public void setVurvId(String vurvId) {
        this.vurvId = vurvId;
    }

}
