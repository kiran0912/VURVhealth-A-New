package com.VURVhealth.vurvhealth.medical.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

public class SearchPractitionerResPayLoad implements Parcelable, ClusterItem {
    @SerializedName("OpenOnWeekend")
    @Expose
    private String OpenOnWeekend;
    @SerializedName("Prac_ADAaccessibilityFlag")
    @Expose
    private String Prac_ADAaccessibilityFlag;
    @SerializedName("Prac_AcceptNewPatients")
    @Expose
    private String Prac_AcceptNewPatients;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ClinicalEducation")
    @Expose
    private String clinicalEducation;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("LocationCount")
    @Expose
    private String locationCount;
    @SerializedName("LocationKey")
    @Expose
    private String locationKey;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Prac_AddressesId")
    @Expose
    private String pracAddressesId;
    @SerializedName("ProviderID")
    @Expose
    private String providerID;
    @SerializedName("Radius")
    @Expose
    private String radius;
    private int savedStatus;
    @SerializedName("Speciality")
    @Expose
    private String speciality;

    public SearchPractitionerResPayLoad(){};

    protected SearchPractitionerResPayLoad(Parcel in) {
        OpenOnWeekend = in.readString();
        Prac_ADAaccessibilityFlag = in.readString();
        Prac_AcceptNewPatients = in.readString();
        address = in.readString();
        clinicalEducation = in.readString();
        gender = in.readString();
        language = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        locationCount = in.readString();
        locationKey = in.readString();
        mobileNo = in.readString();
        name = in.readString();
        pracAddressesId = in.readString();
        providerID = in.readString();
        radius = in.readString();
        savedStatus = in.readInt();
        speciality = in.readString();
    }

    public static final Creator<SearchPractitionerResPayLoad> CREATOR = new Creator<SearchPractitionerResPayLoad>() {
        @Override
        public SearchPractitionerResPayLoad createFromParcel(Parcel in) {
            return new SearchPractitionerResPayLoad(in);
        }

        @Override
        public SearchPractitionerResPayLoad[] newArray(int size) {
            return new SearchPractitionerResPayLoad[size];
        }
    };

    @Override
    public LatLng getPosition() {
        return new LatLng(lat,lng);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return null;
    }



    public String getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getLocationCount() {
        return this.locationCount;
    }

    public void setLocationCount(String locationCount) {
        this.locationCount = locationCount;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPracAddressesId() {
        return this.pracAddressesId;
    }

    public void setPracAddressesId(String pracAddressesId) {
        this.pracAddressesId = pracAddressesId;
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

    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getRadius() {
        return this.radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getPrac_AcceptNewPatients() {
        return this.Prac_AcceptNewPatients;
    }

    public void setPrac_AcceptNewPatients(String prac_AcceptNewPatients) {
        this.Prac_AcceptNewPatients = prac_AcceptNewPatients;
    }

    public String getPrac_ADAaccessibilityFlag() {
        return this.Prac_ADAaccessibilityFlag;
    }

    public void setPrac_ADAaccessibilityFlag(String prac_ADAaccessibilityFlag) {
        this.Prac_ADAaccessibilityFlag = prac_ADAaccessibilityFlag;
    }

    public String getOpenOnWeekend() {
        return this.OpenOnWeekend;
    }

    public void setOpenOnWeekend(String openOnWeekend) {
        this.OpenOnWeekend = openOnWeekend;
    }

    public int isSavedStatus() {
        return this.savedStatus;
    }

    public void setSavedStatus(int savedStatus) {
        this.savedStatus = savedStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(OpenOnWeekend);
        parcel.writeString(Prac_ADAaccessibilityFlag);
        parcel.writeString(Prac_AcceptNewPatients);
        parcel.writeString(address);
        parcel.writeString(clinicalEducation);
        parcel.writeString(gender);
        parcel.writeString(language);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(locationCount);
        parcel.writeString(locationKey);
        parcel.writeString(mobileNo);
        parcel.writeString(name);
        parcel.writeString(pracAddressesId);
        parcel.writeString(providerID);
        parcel.writeString(radius);
        parcel.writeInt(savedStatus);
        parcel.writeString(speciality);
    }
}
