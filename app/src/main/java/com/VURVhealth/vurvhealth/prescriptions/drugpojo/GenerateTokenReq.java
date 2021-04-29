package com.VURVhealth.vurvhealth.prescriptions.drugpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateTokenReq {
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("client_secret")
    @Expose
    private String clientSecret;

    public GenerateTokenReq(Integer clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


}
