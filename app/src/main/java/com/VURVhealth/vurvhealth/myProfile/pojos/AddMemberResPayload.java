package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMemberResPayload {
    @SerializedName("Member_user_ID")
    @Expose
    private Integer memberUserID;
    @SerializedName("next_membership_Id")
    @Expose
    private String nextMembershipId;

    public Integer getMemberUserID() {
        return this.memberUserID;
    }

    public void setMemberUserID(Integer memberUserID) {
        this.memberUserID = memberUserID;
    }

    public String getNextMembershipId() {
        return this.nextMembershipId;
    }

    public void setNextMembershipId(String nextMembershipId) {
        this.nextMembershipId = nextMembershipId;
    }
}
