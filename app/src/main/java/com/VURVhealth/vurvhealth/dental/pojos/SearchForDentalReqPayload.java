package com.VURVhealth.vurvhealth.dental.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchForDentalReqPayload {
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Range")
    @Expose
    private String range;
    @SerializedName("Specialty")
    @Expose
    private String specialty;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;

    public String getRange() {
        return this.range;
    }

    public void setRange(String range) {
        this.range = range;
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

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
