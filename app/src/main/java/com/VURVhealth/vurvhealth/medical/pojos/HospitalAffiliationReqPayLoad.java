package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalAffiliationReqPayLoad {
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("hospitalName")
    @Expose
    private String hospitalName;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
