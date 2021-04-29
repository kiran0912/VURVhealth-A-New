package com.VURVhealth.vurvhealth.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 25/7/17.
 */

public class ForgotPasswordReqSendOtpPayLoad {

    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
