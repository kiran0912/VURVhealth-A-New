package com.VURVhealth.vurvhealth.vision.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfficeSpecialtyResPayload {
    @SerializedName("officeSpecialtyCode")
    @Expose
    private String officeSpecialtyCode;
    @SerializedName("officeSpecialtyName")
    @Expose
    private String officeSpecialtyName;

    public String getOfficeSpecialtyCode() {
        return this.officeSpecialtyCode;
    }

    public void setOfficeSpecialtyCode(String officeSpecialtyCode) {
        this.officeSpecialtyCode = officeSpecialtyCode;
    }

    public String getOfficeSpecialtyName() {
        return this.officeSpecialtyName;
    }

    public void setOfficeSpecialtyName(String officeSpecialtyName) {
        this.officeSpecialtyName = officeSpecialtyName;
    }
}
