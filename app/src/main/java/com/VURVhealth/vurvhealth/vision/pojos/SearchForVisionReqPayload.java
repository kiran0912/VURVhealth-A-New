package com.VURVhealth.vurvhealth.vision.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchForVisionReqPayload {
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("DocSpecialty")
    @Expose
    private String DocSpecialty;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Language")
    @Expose
    private String Language;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Lat")
    @Expose
    private String Lat;
    @SerializedName("Long")
    @Expose
    private String Long;
    @SerializedName("OfficeSpecialty")
    @Expose
    private String OfficeSpecialty;
    @SerializedName("Range")
    @Expose
    private String Range;
    @SerializedName("State")
    @Expose
    private String State;
    @SerializedName("ZipCode")
    @Expose
    private String ZipCode;
    @SerializedName("type")
    @Expose
    private String type;

    public String getLat() {
        return this.Lat;
    }

    public void setLat(String lat) {
        this.Lat = lat;
    }

    public String getLong() {
        return this.Long;
    }

    public void setLong(String aLong) {
        this.Long = aLong;
    }

    public String getRange() {
        return this.Range;
    }

    public void setRange(String range) {
        this.Range = range;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getZipCode() {
        return this.ZipCode;
    }

    public void setZipCode(String zipCode) {
        this.ZipCode = zipCode;
    }

    public String getCity() {
        return this.City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getState() {
        return this.State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getGender() {
        return this.Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getLanguage() {
        return this.Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }

    public String getDocSpecialty() {
        return this.DocSpecialty;
    }

    public void setDocSpecialty(String docSpecialty) {
        this.DocSpecialty = docSpecialty;
    }

    public String getOfficeSpecialty() {
        return this.OfficeSpecialty;
    }

    public void setOfficeSpecialty(String officeSpecialty) {
        this.OfficeSpecialty = officeSpecialty;
    }
}
