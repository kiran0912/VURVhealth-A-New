package com.VURVhealth.vurvhealth.althealth.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AHSProviderDetailsRespayload implements Parcelable {
    public static final Creator<AHSProviderDetailsRespayload> CREATOR = new Creator<AHSProviderDetailsRespayload>() {
        public AHSProviderDetailsRespayload createFromParcel(Parcel in) {
            return new AHSProviderDetailsRespayload(in);
        }

        public AHSProviderDetailsRespayload[] newArray(int size) {
            return new AHSProviderDetailsRespayload[size];
        }
    };
    @SerializedName("AHSProviderId")
    @Expose
    private String aHSProviderId;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("Degree")
    @Expose
    private String degree;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("ProgramDegree")
    @Expose
    private String programDegree;
    @SerializedName("Specialty")
    @Expose
    private String specialty;
    @SerializedName("State")
    @Expose
    private String state;

    protected AHSProviderDetailsRespayload(Parcel in) {
        this.aHSProviderId = in.readString();
        this.name = in.readString();
        this.specialty = in.readString();
        this.degree = in.readString();
        this.programDegree = in.readString();
        this.clinicName = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.county = in.readString();
        this.phoneNumber = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.aHSProviderId);
        dest.writeString(this.name);
        dest.writeString(this.specialty);
        dest.writeString(this.degree);
        dest.writeString(this.programDegree);
        dest.writeString(this.clinicName);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.county);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    public int describeContents() {
        return 0;
    }

    public String getAHSProviderId() {
        return this.aHSProviderId;
    }

    public void setAHSProviderId(String aHSProviderId) {
        this.aHSProviderId = aHSProviderId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getProgramDegree() {
        return this.programDegree;
    }

    public void setProgramDegree(String programDegree) {
        this.programDegree = programDegree;
    }

    public String getClinicName() {
        return this.clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
