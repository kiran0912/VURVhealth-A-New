package com.VURVhealth.vurvhealth.althealth.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AHSSearchReqPayload {
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Range")
    @Expose
    private String range;
    @SerializedName("Specialty")
    @Expose
    private String specialty;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return this._long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getRange() {
        return this.range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
