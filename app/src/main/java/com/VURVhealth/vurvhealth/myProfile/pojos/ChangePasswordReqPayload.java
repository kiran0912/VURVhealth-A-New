package com.VURVhealth.vurvhealth.myProfile.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordReqPayload {
    @SerializedName("loginpass")
    @Expose
    private String loginpass;
    @SerializedName("loginvariable")
    @Expose
    private String loginvariable;
    @SerializedName("new_pass")
    @Expose
    private String newPass;

    public String getLoginvariable() {
        return this.loginvariable;
    }

    public void setLoginvariable(String loginvariable) {
        this.loginvariable = loginvariable;
    }

    public String getLoginpass() {
        return this.loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

    public String getNewPass() {
        return this.newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
