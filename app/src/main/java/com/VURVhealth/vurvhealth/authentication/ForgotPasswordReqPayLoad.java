package com.VURVhealth.vurvhealth.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 25/7/17.
 */

public class ForgotPasswordReqPayLoad {

    @SerializedName("email_Id")
    @Expose
    private String emailId;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
