package com.VURVhealth.vurvhealth.medical.aboutDoctorPojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutDoctorResPayLoad implements Parcelable {
    public static final Creator<AboutDoctorResPayLoad> CREATOR = new Creator<AboutDoctorResPayLoad>() {
        public AboutDoctorResPayLoad createFromParcel(Parcel in) {
            return new AboutDoctorResPayLoad(in);
        }

        public AboutDoctorResPayLoad[] newArray(int size) {
            return new AboutDoctorResPayLoad[size];
        }
    };
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("addline1")
    @Expose
    private String addline1;
    @SerializedName("addline2")
    @Expose
    private String addline2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("ClinicalEducation")
    @Expose
    private String clinicalEducation;
    @SerializedName("FriDay")
    @Expose
    private String friDay;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("HospitalAffiliation")
    @Expose
    private String hospitalAffiliation;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Monday")
    @Expose
    private String monday;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Prac_ADAaccessibilityFlag")
    @Expose
    private String pracADAaccessibilityFlag;
    @SerializedName("Prac_AcceptNewPatients")
    @Expose
    private String pracAcceptNewPatients;
    @SerializedName("Prac_PrimaryServiceAddressIndicator")
    @Expose
    private String pracPrimaryServiceAddressIndicator;
    @SerializedName("ProviderID")
    @Expose
    private String providerID;
    @SerializedName("SaturDay")
    @Expose
    private String saturDay;
    @SerializedName("Speciality")
    @Expose
    private String speciality;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("SunDay")
    @Expose
    private String sunDay;
    @SerializedName("ThursDay")
    @Expose
    private String thursDay;
    @SerializedName("Tuesday")
    @Expose
    private String tuesday;
    @SerializedName("WaitTimeNewPatients")
    @Expose
    private String waitTimeNewPatients;
    @SerializedName("WaitTimeRoutineVisit")
    @Expose
    private String waitTimeRoutineVisit;
    @SerializedName("WaitTimeUrgentCare")
    @Expose
    private String waitTimeUrgentCare;
    @SerializedName("WednesDay")
    @Expose
    private String wednesDay;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("zipextension")
    @Expose
    private String zipextension;

    protected AboutDoctorResPayLoad(Parcel in) {
        this.providerID = in.readString();
        this.name = in.readString();
        this.speciality = in.readString();
        this.addline1 = in.readString();
        this.addline2 = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.zipcode = in.readString();
        this.zipextension = in.readString();
        this.pracPrimaryServiceAddressIndicator = in.readString();
        this.mobileNo = in.readString();
        this.gender = in.readString();
        this.language = in.readString();
        this.clinicalEducation = in.readString();
        this.hospitalAffiliation = in.readString();
        this.lat = in.readString();
        this._long = in.readString();
        this.pracADAaccessibilityFlag = in.readString();
        this.pracAcceptNewPatients = in.readString();
        this.monday = in.readString();
        this.tuesday = in.readString();
        this.wednesDay = in.readString();
        this.thursDay = in.readString();
        this.friDay = in.readString();
        this.saturDay = in.readString();
        this.sunDay = in.readString();
        this.waitTimeRoutineVisit = in.readString();
        this.waitTimeUrgentCare = in.readString();
        this.waitTimeNewPatients = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.providerID);
        dest.writeString(this.name);
        dest.writeString(this.speciality);
        dest.writeString(this.addline1);
        dest.writeString(this.addline2);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.zipcode);
        dest.writeString(this.zipextension);
        dest.writeString(this.pracPrimaryServiceAddressIndicator);
        dest.writeString(this.mobileNo);
        dest.writeString(this.gender);
        dest.writeString(this.language);
        dest.writeString(this.clinicalEducation);
        dest.writeString(this.hospitalAffiliation);
        dest.writeString(this.lat);
        dest.writeString(this._long);
        dest.writeString(this.pracADAaccessibilityFlag);
        dest.writeString(this.pracAcceptNewPatients);
        dest.writeString(this.monday);
        dest.writeString(this.tuesday);
        dest.writeString(this.wednesDay);
        dest.writeString(this.thursDay);
        dest.writeString(this.friDay);
        dest.writeString(this.saturDay);
        dest.writeString(this.sunDay);
        dest.writeString(this.waitTimeRoutineVisit);
        dest.writeString(this.waitTimeUrgentCare);
        dest.writeString(this.waitTimeNewPatients);
    }

    public int describeContents() {
        return 0;
    }

    public String getProviderID() {
        return this.providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return this.speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAddline1() {
        return this.addline1;
    }

    public void setAddline1(String addline1) {
        this.addline1 = addline1;
    }

    public String getAddline2() {
        return this.addline2;
    }

    public void setAddline2(String addline2) {
        this.addline2 = addline2;
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

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipextension() {
        return this.zipextension;
    }

    public void setZipextension(String zipextension) {
        this.zipextension = zipextension;
    }

    public String getPracPrimaryServiceAddressIndicator() {
        return this.pracPrimaryServiceAddressIndicator;
    }

    public void setPracPrimaryServiceAddressIndicator(String pracPrimaryServiceAddressIndicator) {
        this.pracPrimaryServiceAddressIndicator = pracPrimaryServiceAddressIndicator;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getClinicalEducation() {
        return this.clinicalEducation;
    }

    public void setClinicalEducation(String clinicalEducation) {
        this.clinicalEducation = clinicalEducation;
    }

    public String getHospitalAffiliation() {
        return this.hospitalAffiliation;
    }

    public void setHospitalAffiliation(String hospitalAffiliation) {
        this.hospitalAffiliation = hospitalAffiliation;
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

    public String getPracADAaccessibilityFlag() {
        return this.pracADAaccessibilityFlag;
    }

    public void setPracADAaccessibilityFlag(String pracADAaccessibilityFlag) {
        this.pracADAaccessibilityFlag = pracADAaccessibilityFlag;
    }

    public String getPracAcceptNewPatients() {
        return this.pracAcceptNewPatients;
    }

    public void setPracAcceptNewPatients(String pracAcceptNewPatients) {
        this.pracAcceptNewPatients = pracAcceptNewPatients;
    }

    public String getMonday() {
        return this.monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return this.tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesDay() {
        return this.wednesDay;
    }

    public void setWednesDay(String wednesDay) {
        this.wednesDay = wednesDay;
    }

    public String getThursDay() {
        return this.thursDay;
    }

    public void setThursDay(String thursDay) {
        this.thursDay = thursDay;
    }

    public String getFriDay() {
        return this.friDay;
    }

    public void setFriDay(String friDay) {
        this.friDay = friDay;
    }

    public String getSaturDay() {
        return this.saturDay;
    }

    public void setSaturDay(String saturDay) {
        this.saturDay = saturDay;
    }

    public String getSunDay() {
        return this.sunDay;
    }

    public void setSunDay(String sunDay) {
        this.sunDay = sunDay;
    }

    public String getWaitTimeRoutineVisit() {
        return this.waitTimeRoutineVisit;
    }

    public void setWaitTimeRoutineVisit(String waitTimeRoutineVisit) {
        this.waitTimeRoutineVisit = waitTimeRoutineVisit;
    }

    public String getWaitTimeUrgentCare() {
        return this.waitTimeUrgentCare;
    }

    public void setWaitTimeUrgentCare(String waitTimeUrgentCare) {
        this.waitTimeUrgentCare = waitTimeUrgentCare;
    }

    public String getWaitTimeNewPatients() {
        return this.waitTimeNewPatients;
    }

    public void setWaitTimeNewPatients(String waitTimeNewPatients) {
        this.waitTimeNewPatients = waitTimeNewPatients;
    }
}
