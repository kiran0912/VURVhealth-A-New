package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrimaryMemberTypeResPayload {
    @SerializedName("memberType")
    @Expose
    private String memberType;

    public String getMemberType() {
        return this.memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
