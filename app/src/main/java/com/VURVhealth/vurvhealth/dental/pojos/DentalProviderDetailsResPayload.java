package com.VURVhealth.vurvhealth.dental.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DentalProviderDetailsResPayload implements Parcelable {
    public static final Creator<DentalProviderDetailsResPayload> CREATOR = new Creator<DentalProviderDetailsResPayload>() {
        public DentalProviderDetailsResPayload createFromParcel(Parcel in) {
            return new DentalProviderDetailsResPayload(in);
        }

        public DentalProviderDetailsResPayload[] newArray(int size) {
            return new DentalProviderDetailsResPayload[size];
        }
    };
    @SerializedName("Addr1")
    @Expose
    private String addr1;
    @SerializedName("Addr2")
    @Expose
    private String addr2;
    @SerializedName("Center")
    @Expose
    private String center;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("ContractDate")
    @Expose
    private String contractDate;
    @SerializedName("DedicatedFlag")
    @Expose
    private String dedicatedFlag;
    @SerializedName("First_Name")
    @Expose
    private String firstName;
    @SerializedName("GdOrSpCode")
    @Expose
    private String gdOrSpCode;
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
    @SerializedName("MidInitName")
    @Expose
    private String midInitName;
    @SerializedName("Npid")
    @Expose
    private String npid;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("PostName")
    @Expose
    private String postName;
    @SerializedName("PracticeType")
    @Expose
    private String practiceType;
    @SerializedName("ProviderId")
    @Expose
    private String providerId;
    @SerializedName("Spec")
    @Expose
    private String spec;
    @SerializedName("SsnOrTin")
    @Expose
    private String ssnOrTin;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;


    protected DentalProviderDetailsResPayload(Parcel in) {
        this.providerId = in.readString();
        this.lastName = in.readString();
        this.firstName = in.readString();
        this.midInitName = in.readString();
        this.postName = in.readString();
        this.center = in.readString();
        this.addr1 = in.readString();
        this.addr2 = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.zipCode = in.readString();
        this.phoneNo = in.readString();
        this.ssnOrTin = in.readString();
        this.gdOrSpCode = in.readString();
        this.spec = in.readString();
        this.language = in.readString();
        this.status = in.readString();
        this.practiceType = in.readString();
        this.contractDate = in.readString();
        this.npid = in.readString();
        this.dedicatedFlag = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidInitName() {
        return this.midInitName;
    }

    public void setMidInitName(String midInitName) {
        this.midInitName = midInitName;
    }

    public String getPostName() {
        return this.postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getCenter() {
        return this.center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getAddr1() {
        return this.addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return this.addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
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

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSsnOrTin() {
        return this.ssnOrTin;
    }

    public void setSsnOrTin(String ssnOrTin) {
        this.ssnOrTin = ssnOrTin;
    }

    public String getGdOrSpCode() {
        return this.gdOrSpCode;
    }

    public void setGdOrSpCode(String gdOrSpCode) {
        this.gdOrSpCode = gdOrSpCode;
    }

    public String getSpec() {
        return this.spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPracticeType() {
        return this.practiceType;
    }

    public void setPracticeType(String practiceType) {
        this.practiceType = practiceType;
    }

    public String getContractDate() {
        return this.contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getNpid() {
        return this.npid;
    }

    public void setNpid(String npid) {
        this.npid = npid;
    }

    public String getDedicatedFlag() {
        return this.dedicatedFlag;
    }

    public void setDedicatedFlag(String dedicatedFlag) {
        this.dedicatedFlag = dedicatedFlag;
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.providerId);
        parcel.writeString(this.lastName);
        parcel.writeString(this.firstName);
        parcel.writeString(this.midInitName);
        parcel.writeString(this.postName);
        parcel.writeString(this.center);
        parcel.writeString(this.addr1);
        parcel.writeString(this.addr2);
        parcel.writeString(this.city);
        parcel.writeString(this.state);
        parcel.writeString(this.zipCode);
        parcel.writeString(this.phoneNo);
        parcel.writeString(this.ssnOrTin);
        parcel.writeString(this.gdOrSpCode);
        parcel.writeString(this.spec);
        parcel.writeString(this.language);
        parcel.writeString(this.status);
        parcel.writeString(this.practiceType);
        parcel.writeString(this.contractDate);
        parcel.writeString(this.npid);
        parcel.writeString(this.dedicatedFlag);
        parcel.writeString(this.latitude);
        parcel.writeString(this.longitude);
    }
}
