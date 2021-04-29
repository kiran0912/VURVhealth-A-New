package com.VURVhealth.vurvhealth.dental.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DentalLangResPayLoad {
    @SerializedName("LanguageCode")
    @Expose
    private String languageCode;
    @SerializedName("LanguageName")
    @Expose
    private String languageName;

    public String getLanguageName() {
        return this.languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
