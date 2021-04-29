package com.VURVhealth.vurvhealth.medical.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchFacilitiesReqPayLoad {
    @SerializedName("browser")
    @Expose
    private String browser;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("FacilityName")
    @Expose
    private String facilityName;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Hospitals")
    @Expose
    private String hospitals;
    @SerializedName("IPAddr")
    @Expose
    private String iPAddr;
    @SerializedName("Languages")
    @Expose
    private String languages;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("OS")
    @Expose
    private String oS;
    @SerializedName("Radius")
    @Expose
    private String radius;
    @SerializedName("Speciality")
    @Expose
    private String speciality;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("SubTypeOfFacility")
    @Expose
    private String subTypeOfFacility;
    @SerializedName("TypeOfFacility")
    @Expose
    private String typeOfFacility;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFacilityName() {
        return this.facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getTypeOfFacility() {
        return this.typeOfFacility;
    }

    public void setTypeOfFacility(String typeOfFacility) {
        this.typeOfFacility = typeOfFacility;
    }

    public String getSubTypeOfFacility() {
        return this.subTypeOfFacility;
    }

    public void setSubTypeOfFacility(String subTypeOfFacility) {
        this.subTypeOfFacility = subTypeOfFacility;
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

    public String getRadius() {
        return this.radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
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

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSpeciality() {
        return this.speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguages() {
        return this.languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getHospitals() {
        return this.hospitals;
    }

    public void setHospitals(String hospitals) {
        this.hospitals = hospitals;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIPAddr() {
        return this.iPAddr;
    }

    public void setIPAddr(String iPAddr) {
        this.iPAddr = iPAddr;
    }

    public String getOS() {
        return this.oS;
    }

    public void setOS(String oS) {
        this.oS = oS;
    }

    public String getBrowser() {
        return this.browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
