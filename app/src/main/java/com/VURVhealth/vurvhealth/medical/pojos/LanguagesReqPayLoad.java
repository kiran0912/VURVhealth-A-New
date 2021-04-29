package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguagesReqPayLoad {
    @SerializedName("lang")
    @Expose
    public String lang;

    public LanguagesReqPayLoad(String lang) {
        this.lang = lang;
    }
}
