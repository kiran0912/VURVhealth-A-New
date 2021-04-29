package com.VURVhealth.vurvhealth.authentication.registrationPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yqlabs on 27/1/17.
 */

public class RegistrationResPayLoad {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("error")
    @Expose
    private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
